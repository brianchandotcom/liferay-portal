/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.store.gcs;

import com.google.api.gax.paging.Page;
import com.google.api.gax.retrying.RetrySettings;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Blob.BlobSourceOption;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.CopyWriter;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.BlobWriteOption;
import com.google.cloud.storage.Storage.CopyRequest;
import com.google.cloud.storage.StorageOptions;
import com.google.common.base.Stopwatch;
import com.liferay.document.library.kernel.store.BaseStore;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.store.gcs.configuration.GCSStoreConfiguration;
import com.liferay.portal.store.gcs.key.manipulation.KeyTransformer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.threeten.bp.Duration;

/**
 * @author Shanon Mathai
 */
@Component(
    configurationPid = "com.liferay.portal.store.gcs.configuration.GCSStoreConfiguration",
    immediate = true,
    property = "store.type=com.liferay.portal.store.gcs.GCSStore",
    service = Store.class)
public class GCSStore extends BaseStore {

  @Override
  public void addDirectory(long companyId, long repositoryId, String dirName) {
    debugLog("Liferay GCS adapter does not support creating empty directory structures");
  }

  @Override
  public void addFile(long companyId, long repositoryId, String fileName, InputStream is)
      throws PortalException {

    if (_log.isDebugEnabled()) {
      String fileKey = _keyTransformer.getFileKey(companyId, repositoryId, fileName);
      debugLog("Creating file with default version for for: " + fileKey);
    }

    addFileWithVersion(companyId, repositoryId, fileName, VERSION_DEFAULT, is);
  }

  @Override
  public void checkRoot(long companyId) {
    debugLog("Liferay GCS adapter does not support \"check root\" operations");
  }

  @Override
  public void deleteDirectory(long companyId, long repositoryId, String dirName) {

    String path = _keyTransformer.getDirectoryKey(companyId, repositoryId, dirName);

    debugLog("Deleting from bucket with prefix: " + path);

    boolean traceEnabled = _log.isTraceEnabled();
    Stopwatch stopwatch = null;
    if (traceEnabled) {
      traceLog("Fetching files from directory " + path + " for delete");
      stopwatch = Stopwatch.createStarted();
    }
    Page<Blob> blobPages = _gcsStore.list(getBucketName(), BlobListOption.prefix(path));
    if (traceEnabled) {
      stopwatch.stop();
      long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
      traceLog("Listing for delete took " + elapsed + " milliseconds");
    }

    Iterable<Blob> blobs = blobPages.iterateAll();

    blobs.forEach(this::logAndDeleteBlob);
  }

  @Override
  public void deleteFile(long companyId, long repositoryId, String fileName) {
    debugLog("Deleting from bucket with fileName: " + fileName);

    deleteDirectory(companyId, repositoryId, fileName);
  }

  @Override
  public void deleteFile(long companyId, long repositoryId, String fileName, String versionLabel) {
    String filePath = determineVersionFilePath(companyId, repositoryId, fileName, versionLabel);

    deleteFile(filePath);
  }

  @Override
  public InputStream getFileAsStream(long companyId, long repositoryId, String fileName,
      String versionLabel) {

    String filePath = determineVersionFilePath(companyId, repositoryId, fileName, versionLabel);

    traceLog("Trying to get file: " + filePath);
    Blob blob = getBlob(getBlobId(filePath));
    traceLog("Got Blob: " + blob);

    ReadChannel reader = getReader(blob);

    return Channels.newInputStream(reader);
  }

  @Override
  public String[] getFileNames(long companyId, long repositoryId) {
    return getFileNames(companyId, repositoryId, StringPool.BLANK);
  }

  @Override
  public String[] getFileNames(long companyId, long repositoryId, String dirName) {
    Bucket bucket = getBucket();
    Iterable<Blob> blobs;
    String path;

    if (dirName == null || dirName.isEmpty() || dirName.equals(StringPool.FORWARD_SLASH)) {
      path = _keyTransformer.getRepositoryKey(companyId, repositoryId);
    } else {
      path = _keyTransformer.getDirectoryKey(companyId, repositoryId, dirName);
    }

    BlobListOption prefixOption = BlobListOption.prefix(path);

    Stopwatch stopwatch = null;
    boolean traceEnabled = _log.isTraceEnabled();
    if (traceEnabled) {
      stopwatch = Stopwatch.createStarted();
    }
    Page<Blob> blobPage = bucket.list(prefixOption);
    if (traceEnabled) {
      stopwatch.stop();
      long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
      traceLog("Listing " + path + " took " + elapsed + " milliseconds");
    }

    blobs = blobPage.iterateAll();

    String[] fileNames =
        StreamSupport.stream(blobs.spliterator(), false)
            .map(BlobInfo::getName)
            .toArray(String[]::new);

    if (traceEnabled) {
      traceLog("Listing content with prefix: " + path);
      String fnames = String.join("\n   ", fileNames);
      traceLog("   " + fnames);
    }

    return fileNames;
  }

  @Override
  public long getFileSize(long companyId, long repositoryId, String fileName)
      throws PortalException {

    String pathName = getHeadVersionLabel(companyId, repositoryId, fileName);

    traceLog("Getting file size for: " + pathName);

    BlobId blobId = getBlobId(pathName);

    Blob blob = getBlob(blobId);

    if (blob == null) {

      debugLog("Cannot retrieve: " + pathName);

      throw new PortalException("No such file store entry: " + pathName);
    }

    Long size = blob.getSize();

    traceLog("Size for " + pathName + " is " + size);

    return size;
  }

  @Override
  public boolean hasDirectory(long companyId, long repositoryId, String dirName) {
    debugLog("Liferay GCS adapter does not support check for directory, returning true");
    return true;
  }

  @Override
  public boolean hasFile(long companyId, long repositoryId, String fileName, String versionLabel) {
    String path = _keyTransformer.getFileVersionKey(
        companyId, repositoryId, fileName, versionLabel);

    boolean traceEnabled = _log.isTraceEnabled();

    Stopwatch stopwatch = null;
    if (traceEnabled) {
      stopwatch = Stopwatch.createStarted();
    }
    Page<Blob> blobPage =
        _gcsStore.list(getBucketName(), BlobListOption.pageSize(1), BlobListOption.prefix(path));
    if (traceEnabled) {
      stopwatch.stop();
      long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
      String traceMsg = String
          .format("Check if file %s exists took %d milliseconds", path, elapsed);
      traceLog(traceMsg);
    }

    Iterable<Blob> filesFound = blobPage.getValues();

    boolean hasFile = filesFound.iterator().hasNext();

    if (traceEnabled) {
      String key = _keyTransformer.getFileVersionKey(
          companyId, repositoryId, fileName, versionLabel);
      traceLog("Checking file presence for: " + key + ", presence " + hasFile);
    }

    return hasFile;
  }

  @Override
  public void updateFile(long companyId, long repositoryId, long newRepositoryId, String fileName) {
    String[] fileNames = getFileNames(companyId, repositoryId, fileName);

    for (String oldPath : fileNames) {
      String version = getVersionFromFullPath(oldPath);

      Blob oldBlob = getBlob(getBlobId(oldPath));

      String newPath = _keyTransformer.getFileVersionKey(
          companyId, newRepositoryId, fileName, version);

      move(oldBlob, newPath);
    }
  }

  @Override
  public void updateFile(long companyId, long repositoryId, String fileName, String newFileName) {
    String[] fileNames = getFileNames(companyId, repositoryId, fileName);

    for (String oldPath : fileNames) {
      String version = getVersionFromFullPath(oldPath);

      Blob oldBlob = getBlob(companyId, repositoryId, fileName, version);

      String newPath = _keyTransformer.getFileVersionKey(
          companyId, repositoryId, newFileName, version);

      move(oldBlob, newPath);
    }
  }

  @Override
  public void updateFile(long companyId, long repositoryId, String fileName, String versionLabel,
      InputStream is) throws PortalException {

    if (_log.isTraceEnabled()) {
      String filePath = _keyTransformer.getFileKey(companyId, repositoryId, fileName);
      String fileVersion = _keyTransformer.getFileVersionKey(
          companyId, repositoryId, fileName, versionLabel);
      String msg = String.format("Updating file \"%s\" to version \"%s\"", filePath, fileVersion);
      traceLog(msg);
    }

    addFileWithVersion(companyId, repositoryId, fileName, versionLabel, is);
  }

  @Modified
  @Activate
  protected void activate(Map<String, Object> properties) {

    _gcsStoreConfiguration = ConfigurableUtil.createConfigurable(
        GCSStoreConfiguration.class, properties);

    try {
      _gcsStore = null;

      setupEncryptedCommunication();

      setGcsStore();

    } catch (PortalException e) {
      throw new IllegalStateException("Unable to initialize GCS store", e);
    }
  }

  protected void addFileWithVersion(long companyId, long repositoryId, String fileName,
      String versionLabel, InputStream is) throws PortalException {

    String fileVersionKey = _keyTransformer.getFileVersionKey(
        companyId, repositoryId, fileName, versionLabel);

    traceLog("Attempting to create new file");
    traceLog("Constructed key: " + fileVersionKey);

    BlobInfo blobInfo = BlobInfo.newBuilder(getBucketInfo(), fileVersionKey).build();

    try (WriteChannel writer = getWriter(blobInfo)) {
      writeInputStream(is, writer);
    } catch (IOException e) {
      if (_log.isDebugEnabled()) {
        _log.debug("Unable to write out to buffer.", e);
      }
      throw new PortalException(e);
    } finally {
      traceLog("Done writing out to buffer.");
    }
    debugLog("Blob for a folder was created at: " + fileVersionKey);
  }

  protected void setCredentials() throws PortalException {

    traceLog("Initializing credentials");
    try (InputStream inputStream = getCredentialsInputStream()) {
      googleCredentials = ServiceAccountCredentials.fromStream(inputStream);
    } catch (IOException e) {
      throw new PortalException("Unable to authenticate with authentication file", e);
    }
  }

  private RetrySettings buildRetrySettings(int maxAttempts, int initialRetryDelay,
      int maxRetryDelay, double retryDelayMultiplier, int maxRpcTimeout, int initRpcTimout,
      double rpcTimeoutMultiplier, boolean jittered) {

    RetrySettings.Builder builder = RetrySettings.newBuilder();

    builder.setMaxAttempts(maxAttempts);
    builder.setInitialRetryDelay(Duration.ofMillis(initialRetryDelay));
    builder.setMaxRetryDelay(Duration.ofMillis(maxRetryDelay));
    builder.setRetryDelayMultiplier(retryDelayMultiplier);
    builder.setInitialRpcTimeout(Duration.ofMillis(initRpcTimout));
    builder.setMaxRpcTimeout(Duration.ofMillis(maxRpcTimeout));
    builder.setRpcTimeoutMultiplier(rpcTimeoutMultiplier);
    builder.setJittered(jittered);

    return builder.build();
  }

  private StorageOptions buildStorage(RetrySettings retrySettings,
      GoogleCredentials googleCredentials) {

    StorageOptions.Builder builder = StorageOptions.newBuilder();
    builder.setCredentials(googleCredentials);
    builder.setRetrySettings(retrySettings);
    return builder.build();
  }

  private void debugLog(String debugLog) {
    if (_log.isDebugEnabled()) {
      _log.debug(debugLog);
    }
  }

  private boolean deleteBlob(Blob blob) {
    boolean isDeleted;
    boolean traceEnabled = _log.isTraceEnabled();

    Stopwatch stopwatch = null;
    String blobName = null;
    if (traceEnabled) {
      blobName = blob.getName();
      stopwatch = Stopwatch.createStarted();
    }
    if (_blobDecryptSourceOption == null) {
      isDeleted = blob.delete();
    } else {
      isDeleted = blob.delete(_blobDecryptSourceOption);
    }
    if (traceEnabled) {
      stopwatch.stop();
      long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
      traceLog("Took " + elapsed + " to delete " + blobName);
    }
    return isDeleted;
  }

  private void deleteFile(String filePath) {
    BlobId blobId = getBlobId(filePath);

    boolean deleted = _gcsStore.delete(blobId);

    if (!deleted && _log.isWarnEnabled()) {
      _log.warn("Unable to delete \"" + filePath + "\" from file store");
    } else if (deleted && _log.isTraceEnabled()) {
      traceLog("Deleted \"" + filePath + "\" from file store");
    }
  }

  private String determineVersionFilePath(long companyId, long repositoryId, String fileName,
      String versionLabel) {

    String filePath;

    if (versionLabel == null || versionLabel.isEmpty()) {

      filePath = getHeadVersionLabel(companyId, repositoryId, fileName);
    } else {
      filePath = _keyTransformer.getFileVersionKey(companyId, repositoryId, fileName, versionLabel);
    }

    return filePath;
  }

  private Blob getBlob(long companyId, long repositoryId, String fileName, String versionLabel) {
    String path = _keyTransformer.getFileVersionKey(
        companyId, repositoryId, fileName, versionLabel);

    BlobId blobId = getBlobId(path);

    return getBlob(blobId);
  }

  private Blob getBlob(BlobId blobId) {

    Stopwatch stopwatch = null;
    boolean traceEnabled = _log.isTraceEnabled();
    if (traceEnabled) {
      traceLog("Fetching " + blobId);
      stopwatch = Stopwatch.createStarted();
    }
    Blob blob = _gcsStore.get(blobId);
    if (traceEnabled) {
      stopwatch.stop();
      long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
      traceLog("Spent " + elapsed + " milliseconds retrieving " + blobId);
    }
    return blob;
  }

  private BlobId getBlobId(String pathName) {
    return BlobId.of(getBucketName(), pathName);
  }

  private Bucket getBucket() {
    return _gcsStore.get(getBucketName());
  }

  private BucketInfo getBucketInfo() {
    if (_bucketInfo == null) {
      _bucketInfo = BucketInfo.newBuilder(getBucketName()).build();
    }
    return _bucketInfo;
  }

  private String getBucketName() {
    return _gcsStoreConfiguration.bucketName();
  }

  private CopyRequest getCopyRequest(BlobId newBlobId, BlobId oldBlobId,
      Storage.BlobSourceOption sourceOption, BlobTargetOption targetOption) {

    CopyRequest.Builder copyRequestBuilder = CopyRequest.newBuilder();

    copyRequestBuilder.setSource(oldBlobId);
    copyRequestBuilder.setSourceOptions(sourceOption);
    copyRequestBuilder.setTarget(newBlobId, targetOption);

    return copyRequestBuilder.build();
  }

  private InputStream getCredentialsInputStream()
      throws FileNotFoundException {

    traceLog("Using authentication file; " + _gcsStoreConfiguration.authFileLocation());

    File credentialFiles = new File(_gcsStoreConfiguration.authFileLocation());

    return new FileInputStream(credentialFiles);
  }

  private String getHeadVersionLabel(long companyId, long repositoryId, String fileName) {

    String key = _keyTransformer.getFileKey(companyId, repositoryId, fileName);

    String[] names = getFileNames(companyId, repositoryId, key);

    if (names == null || names.length == 0) {
      if (_log.isDebugEnabled()) {
        _log.debug("Unable to determine available versions for: " + key);
        _log.debug("Using default: " + VERSION_DEFAULT);
      }
      return _keyTransformer.getFileVersionKey(companyId, repositoryId, fileName, VERSION_DEFAULT);
    }

    List<String> fileNames = Arrays.asList(names);

    fileNames.sort(GcsStoreConstants.VERSION_NUMBER_COMPARATOR);

    return fileNames.get(fileNames.size() - 1);

  }

  private ReadChannel getReader(Blob blob) {
    if (_blobDecryptSourceOption == null) {
      return blob.reader();
    }
    return blob.reader(_blobDecryptSourceOption);
  }

  private String getVersionFromFullPath(String fullPath) {
    int indexOfLastSlash = fullPath.lastIndexOf(StringPool.FORWARD_SLASH);

    return fullPath.substring(indexOfLastSlash + 1);
  }

  private WriteChannel getWriter(BlobInfo blobInfo) {
    if (_blobEncryptWriteOption == null) {
      return _gcsStore.writer(blobInfo);
    }
    return _gcsStore.writer(blobInfo, _blobEncryptWriteOption);
  }

  private void logAndDeleteBlob(Blob blob) {
    boolean deleted = deleteBlob(blob);

    if (!deleted && _log.isWarnEnabled()) {
      _log.warn("Unable to delete \"" + blob.getBlobId() + "\" from file store");
    } else if (deleted && _log.isTraceEnabled()) {
      traceLog("Deleted \"" + blob.getBlobId() + "\" from file store");
    }
  }

  private void move(Blob oldBlob, String newPath) {

    BlobId newBlobId = getBlobId(newPath);

    BlobId oldBlobId = oldBlob.getBlobId();

    boolean traceEnabled = _log.isTraceEnabled();

    if (traceEnabled) {
      String msg = String.format("Updating file from (name) \"%s\" to \"%s\"",
          oldBlobId.getName(), newBlobId.getName());

      traceLog(msg);
    }

    CopyRequest copyRequest = getCopyRequest(newBlobId, oldBlobId, _storageDecryptionSourceOption,
        _blobEncryptTargetOption);

    Stopwatch stopwatch = null;
    if (traceEnabled) {
      stopwatch = Stopwatch.createStarted();
    }
    CopyWriter copyWriter = _gcsStore.copy(copyRequest);
    // block until complete
    while (!copyWriter.isDone()) {
      copyWriter.copyChunk();
    }
    if (traceEnabled) {
      stopwatch.stop();
      long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
      String traceMsg = String.format(
          "Copying of %s to %s took %d milliseconds", oldBlobId.getName(),
          newBlobId.getName(), elapsed);
      traceLog(traceMsg);
    }

    debugLog("Copied " + oldBlob + " to " + newBlobId);

    logAndDeleteBlob(oldBlob);
  }

  private void setGcsStore() throws PortalException {
    if (_gcsStore == null) {
      setCredentials();

      RetrySettings retrySettings = buildRetrySettings(_gcsStoreConfiguration.maxRetryAttempts(),
          _gcsStoreConfiguration.initialRetryDelay(), _gcsStoreConfiguration.maxRetryDelay(),
          _gcsStoreConfiguration.retryDelayMultiplier(), _gcsStoreConfiguration.maxRpcTimeout(),
          _gcsStoreConfiguration.initialRpcTimeout(), _gcsStoreConfiguration.rpcTimeoutMultiplier(),
          _gcsStoreConfiguration.retryJitter());

      StorageOptions storageOptions = buildStorage(retrySettings, googleCredentials);

      traceLog("Initializing new gcsStore component");

      _gcsStore = storageOptions.getService();
      return;
    }

    traceLog("gcsStore component already set");
  }

  private void setupEncryptedCommunication() {
    String keyValue = PropsUtil.get(GcsStoreConstants.KEY_PROPERTY);

    if (keyValue == null || keyValue.equals(StringPool.BLANK)) {

      _log.warn(
          "Property \"dl.store.gcs.aes256.key\" should be set to encrypt stored files. "
              + "Using default storage. The key must be AES 256bit key, encoded in Base64.");

      _blobDecryptSourceOption = null;

      _blobEncryptWriteOption = null;
    } else {
      _storageDecryptionSourceOption = Storage.BlobSourceOption.decryptionKey(keyValue);

      _blobDecryptSourceOption = Blob.BlobSourceOption.decryptionKey(keyValue);

      _blobEncryptWriteOption = BlobWriteOption.encryptionKey(keyValue);

      _blobEncryptTargetOption = BlobTargetOption.encryptionKey(keyValue);
    }
  }

  private void traceLog(String traceLog) {
    if (_log.isTraceEnabled()) {
      _log.trace(traceLog);
    }
  }

  private void writeInputStream(InputStream inputStream, WriteChannel writer)
      throws IOException, PortalException {
    byte[] buffer = new byte[WRITE_BUFFER_SIZE];
    int limit;

    Stopwatch outputWatch = null;
    Stopwatch overallClock = null;
    long totalWriteTimeNanoSec = 0;
    int writtenTotal = 0;

    boolean traceEnabled = _log.isTraceEnabled();
    if (traceEnabled) {
      traceLog("Writing out to buffer...");
      outputWatch = Stopwatch.createUnstarted();
      overallClock = Stopwatch.createStarted();
    }
    while ((limit = inputStream.read(buffer)) >= 0) {
      try {
        if (traceEnabled) {
          outputWatch.start();
        }
        int writtenBytes = writer.write(ByteBuffer.wrap(buffer, 0, limit));
        if (traceEnabled) {
          outputWatch.stop();
          long elapsed = outputWatch.elapsed(TimeUnit.NANOSECONDS);
          totalWriteTimeNanoSec += elapsed;
          writtenTotal += writtenBytes;
          outputWatch.reset();
        }
      } catch (IOException ex) {
        throw new PortalException(ex);
      }
    }
    if (traceEnabled) {
      overallClock.stop();
      long elapsed = overallClock.elapsed(TimeUnit.MILLISECONDS);
      String traceMsg =
          String.format("Took %d milliseconds to transferring %d bytes", elapsed, writtenTotal);
      traceLog(traceMsg);
      traceMsg = String.format("Took %d nanoseconds (%d milliseconds) writing to GCS",
          totalWriteTimeNanoSec, totalWriteTimeNanoSec / 1000000);
      traceLog(traceMsg);
    }
  }

  protected GoogleCredentials googleCredentials;

  private static final int WRITE_BUFFER_SIZE = 1024;

  private static final Log _log = LogFactoryUtil.getLog(GCSStore.class);

  private BlobSourceOption _blobDecryptSourceOption;

  private BlobTargetOption _blobEncryptTargetOption;

  private BlobWriteOption _blobEncryptWriteOption;

  private BucketInfo _bucketInfo;

  private Storage _gcsStore;

  private GCSStoreConfiguration _gcsStoreConfiguration;

  @Reference
  private KeyTransformer _keyTransformer;

  private Storage.BlobSourceOption _storageDecryptionSourceOption;
}