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
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.base.Stopwatch;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.comparator.VersionNumberComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.gcs.configuration.GCSStoreConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.nio.ByteBuffer;
import java.nio.channels.Channels;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import org.threeten.bp.Duration;

/**
 * @author Shanon Mathai
 * @author Alicia García
 */
@Component(
	configurationPid = "com.liferay.portal.store.gcs.configuration.GCSStoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "store.type=com.liferay.portal.store.gcs.GCSStore",
	service = Store.class
)
public class GCSStore implements Store {

	public static final String KEY_PROPERTY = "dl.store.gcs.aes256.key";

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream inputStream)
		throws PortalException {

		String fileVersionKey = _keyTransformer.getFileVersionKey(
			companyId, repositoryId, fileName, versionLabel);

		if (_log.isTraceEnabled()) {
			_log.trace("Attempting to create new file");
			_log.trace("Constructed key: " + fileVersionKey);
		}

		BlobInfo.Builder builder = BlobInfo.newBuilder(
			_getBucketInfo(), fileVersionKey);

		BlobInfo blobInfo = builder.build();

		try (WriteChannel writer = _getWriter(blobInfo)) {
			_writeInputStream(inputStream, writer);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to write out to buffer", ioException);
			}

			throw new PortalException(ioException);
		}
		finally {
			if (_log.isTraceEnabled()) {
				_log.trace("Done writing out to buffer");
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Blob for a folder was created at: " + fileVersionKey);
		}
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		String path = _keyTransformer.getDirectoryKey(
			companyId, repositoryId, dirName);

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting from bucket with prefix: " + path);
		}

		Stopwatch stopwatch = null;

		if (_log.isTraceEnabled()) {
			_log.trace(
				StringBundler.concat(
					"Fetching files from directory ", path, " for delete"));

			stopwatch = Stopwatch.createStarted();
		}

		Page<Blob> blobPage = _gcsStore.list(
			_getBucketName(), Storage.BlobListOption.prefix(path));

		if (_log.isTraceEnabled()) {
			stopwatch.stop();

			long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);

			_log.trace(
				StringBundler.concat(
					"Listing for delete took ", elapsed, " milliseconds"));
		}

		Iterable<Blob> blobs = blobPage.iterateAll();

		blobs.forEach(this::_logAndDeleteBlob);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String filePath = _getHeadVersionLabel(
			companyId, repositoryId, fileName, versionLabel);

		_deleteFile(filePath);
	}

	@Override
	public InputStream getFileAsStream(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String filePath = _getHeadVersionLabel(
			companyId, repositoryId, fileName, versionLabel);

		if (_log.isTraceEnabled()) {
			_log.trace("Trying to get file: " + filePath);
		}

		Blob blob = _getBlob(_getBlobId(filePath));

		if (_log.isTraceEnabled()) {
			_log.trace("Got Blob: " + blob);
		}

		return Channels.newInputStream(_getReader(blob));
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		String path = null;

		if (Validator.isNull(dirName) ||
			dirName.equals(StringPool.FORWARD_SLASH)) {

			path = _keyTransformer.getRepositoryKey(companyId, repositoryId);
		}
		else {
			path = _keyTransformer.getDirectoryKey(
				companyId, repositoryId, dirName);
		}

		Storage.BlobListOption prefixOption = Storage.BlobListOption.prefix(
			path);

		Stopwatch stopwatch = null;

		if (_log.isTraceEnabled()) {
			stopwatch = Stopwatch.createStarted();
		}

		Bucket bucket = _getBucket();

		Page<Blob> blobPage = bucket.list(prefixOption);

		if (_log.isTraceEnabled()) {
			stopwatch.stop();

			long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);

			_log.trace(
				StringBundler.concat(
					"Listing ", path, " took ", elapsed, " milliseconds"));
		}

		Iterable<Blob> blobs = blobPage.iterateAll();

		Stream<Blob> blobStream = StreamSupport.stream(
			blobs.spliterator(), false);

		String[] fileNames = blobStream.map(
			BlobInfo::getName
		).toArray(
			String[]::new
		);

		if (_log.isTraceEnabled()) {
			StringBuilder sb = new StringBuilder();

			sb.append("Listing content with prefix: " + path);
			sb.append("\n    ");
			sb.append(StringUtil.merge(fileNames, StringPool.NEW_LINE));

			_log.trace(sb.toString());
		}

		return fileNames;
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		String pathName = _getHeadVersionLabel(
			companyId, repositoryId, fileName, versionLabel);

		if (_log.isTraceEnabled()) {
			_log.trace("Getting file size for: " + pathName);
		}

		Blob blob = _getBlob(_getBlobId(pathName));

		if (blob == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Cannot retrieve: " + pathName);
			}

			throw new PortalException("No such file store entry: " + pathName);
		}

		Long size = blob.getSize();

		if (_log.isTraceEnabled()) {
			_log.trace(
				StringBundler.concat("Size for ", pathName, " is ", size));
		}

		return size;
	}

	@Override
	public String[] getFileVersions(
		long companyId, long repositoryId, String fileName) {

		String key = _keyTransformer.getFileKey(
			companyId, repositoryId, fileName);

		return getFileNames(companyId, repositoryId, key);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String path = _keyTransformer.getFileVersionKey(
			companyId, repositoryId, fileName, versionLabel);

		Stopwatch stopwatch = null;

		if (_log.isTraceEnabled()) {
			stopwatch = Stopwatch.createStarted();
		}

		Page<Blob> blobPage = _gcsStore.list(
			_getBucketName(), Storage.BlobListOption.pageSize(1),
			Storage.BlobListOption.prefix(path));

		if (_log.isTraceEnabled()) {
			stopwatch.stop();

			long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);

			String traceMsg = String.format(
				"Check if file %s exists took %d milliseconds", path, elapsed);

			_log.trace(traceMsg);
		}

		Iterable<Blob> filesFoundIterable = blobPage.getValues();

		Iterator<Blob> filesFoundIterator = filesFoundIterable.iterator();

		boolean hasFile = filesFoundIterator.hasNext();

		if (_log.isTraceEnabled()) {
			String key = _keyTransformer.getFileVersionKey(
				companyId, repositoryId, fileName, versionLabel);

			_log.trace(
				StringBundler.concat(
					"Checking file presence for: ", key, ", presence ",
					hasFile));
		}

		return hasFile;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_gcsStoreConfiguration = ConfigurableUtil.createConfigurable(
			GCSStoreConfiguration.class, properties);

		try {
			_gcsStore = null;

			_setupEncryptedCommunication();

			_setGcsStore();
		}
		catch (PortalException portalException) {
			throw new IllegalStateException(
				"Unable to initialize GCS store", portalException);
		}
	}

	protected void setCredentials() throws PortalException {
		if (_log.isTraceEnabled()) {
			_log.trace("Initializing credentials");
		}

		try (InputStream inputStream = _getCredentialsInputStream()) {
			googleCredentials = ServiceAccountCredentials.fromStream(
				inputStream);
		}
		catch (IOException ioException) {
			throw new PortalException(
				"Unable to authenticate with authentication file", ioException);
		}
	}

	protected GoogleCredentials googleCredentials;

	private RetrySettings _buildRetrySettings(
		int maxAttempts, int initialRetryDelay, int maxRetryDelay,
		double retryDelayMultiplier, int maxRpcTimeout, int initRpcTimout,
		double rpcTimeoutMultiplier, boolean jittered) {

		RetrySettings.Builder builder = RetrySettings.newBuilder();

		builder.setInitialRetryDelay(Duration.ofMillis(initialRetryDelay));
		builder.setInitialRpcTimeout(Duration.ofMillis(initRpcTimout));
		builder.setJittered(jittered);
		builder.setMaxAttempts(maxAttempts);
		builder.setMaxRetryDelay(Duration.ofMillis(maxRetryDelay));
		builder.setMaxRpcTimeout(Duration.ofMillis(maxRpcTimeout));
		builder.setRetryDelayMultiplier(retryDelayMultiplier);
		builder.setRpcTimeoutMultiplier(rpcTimeoutMultiplier);

		return builder.build();
	}

	private StorageOptions _buildStorage(
		RetrySettings retrySettings, GoogleCredentials googleCredentials) {

		StorageOptions.Builder builder = StorageOptions.newBuilder();

		builder.setCredentials(googleCredentials);
		builder.setRetrySettings(retrySettings);

		return builder.build();
	}

	private boolean _deleteBlob(Blob blob) {
		boolean deleted = false;

		Stopwatch stopwatch = null;
		String blobName = null;

		if (_log.isTraceEnabled()) {
			blobName = blob.getName();

			stopwatch = Stopwatch.createStarted();
		}

		if (_blobDecryptSourceOption == null) {
			deleted = blob.delete();
		}
		else {
			deleted = blob.delete(_blobDecryptSourceOption);
		}

		if (_log.isTraceEnabled()) {
			stopwatch.stop();

			long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);

			_log.trace(
				StringBundler.concat(
					"Took ", elapsed, " to delete ", blobName));
		}

		return deleted;
	}

	private void _deleteFile(String filePath) {
		boolean deleted = _gcsStore.delete(_getBlobId(filePath));

		if (!deleted && _log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Unable to delete \"", filePath, "\" from file store"));
		}
		else if (deleted && _log.isTraceEnabled()) {
			_log.trace(
				StringBundler.concat(
					"Deleted \"", filePath, "\" from file store"));
		}
	}

	private Blob _getBlob(BlobId blobId) {
		Stopwatch stopwatch = null;

		if (_log.isTraceEnabled()) {
			_log.trace("Fetching " + blobId);

			stopwatch = Stopwatch.createStarted();
		}

		Blob blob = _gcsStore.get(blobId);

		if (_log.isTraceEnabled()) {
			stopwatch.stop();

			long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);

			_log.trace(
				StringBundler.concat(
					"Spent ", elapsed, " milliseconds retrieving ", blobId));
		}

		return blob;
	}

	private Blob _getBlob(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String path = _keyTransformer.getFileVersionKey(
			companyId, repositoryId, fileName, versionLabel);

		return _getBlob(_getBlobId(path));
	}

	private BlobId _getBlobId(String pathName) {
		return BlobId.of(_getBucketName(), pathName);
	}

	private Bucket _getBucket() {
		return _gcsStore.get(_getBucketName());
	}

	private BucketInfo _getBucketInfo() {
		if (_bucketInfo == null) {
			BucketInfo.Builder builder = BucketInfo.newBuilder(
				_getBucketName());

			_bucketInfo = builder.build();
		}

		return _bucketInfo;
	}

	private String _getBucketName() {
		return _gcsStoreConfiguration.bucketName();
	}

	private InputStream _getCredentialsInputStream()
		throws FileNotFoundException {

		if (_log.isTraceEnabled()) {
			_log.trace(
				"Using authentication file; " +
					_gcsStoreConfiguration.authFileLocation());
		}

		File credentialFile = new File(
			_gcsStoreConfiguration.authFileLocation());

		return new FileInputStream(credentialFile);
	}

	private String _getHeadVersionLabel(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		if (Validator.isNotNull(versionLabel)) {
			return _keyTransformer.getFileVersionKey(
				companyId, repositoryId, fileName, versionLabel);
		}

		String key = _keyTransformer.getFileKey(
			companyId, repositoryId, fileName);

		String[] names = getFileNames(companyId, repositoryId, key);

		if ((names == null) || (names.length == 0)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to determine available versions for: " + key);
				_log.debug("Using default: " + VERSION_DEFAULT);
			}

			return _keyTransformer.getFileVersionKey(
				companyId, repositoryId, fileName, VERSION_DEFAULT);
		}

		List<String> fileNames = Arrays.asList(names);

		fileNames.sort(new VersionNumberComparator());

		return fileNames.get(fileNames.size() - 1);
	}

	private ReadChannel _getReader(Blob blob) {
		if (_blobDecryptSourceOption == null) {
			return blob.reader();
		}

		return blob.reader(_blobDecryptSourceOption);
	}

	private WriteChannel _getWriter(BlobInfo blobInfo) {
		if (_blobEncryptWriteOption == null) {
			return _gcsStore.writer(blobInfo);
		}

		return _gcsStore.writer(blobInfo, _blobEncryptWriteOption);
	}

	private void _logAndDeleteBlob(Blob blob) {
		boolean deleted = _deleteBlob(blob);

		if (!deleted && _log.isWarnEnabled()) {
			_log.warn(
				"Unable to delete \"" + blob.getBlobId() +
					"\" from file store");
		}
		else if (deleted && _log.isTraceEnabled()) {
			_log.trace("Deleted \"" + blob.getBlobId() + "\" from file store");
		}
	}

	private void _setGcsStore() throws PortalException {
		if (_gcsStore == null) {
			setCredentials();

			RetrySettings retrySettings = _buildRetrySettings(
				_gcsStoreConfiguration.maxRetryAttempts(),
				_gcsStoreConfiguration.initialRetryDelay(),
				_gcsStoreConfiguration.maxRetryDelay(),
				_gcsStoreConfiguration.retryDelayMultiplier(),
				_gcsStoreConfiguration.maxRpcTimeout(),
				_gcsStoreConfiguration.initialRpcTimeout(),
				_gcsStoreConfiguration.rpcTimeoutMultiplier(),
				_gcsStoreConfiguration.retryJitter());

			StorageOptions storageOptions = _buildStorage(
				retrySettings, googleCredentials);

			if (_log.isTraceEnabled()) {
				_log.trace("Initializing new gcsStore component");
			}

			_gcsStore = storageOptions.getService();

			return;
		}

		if (_log.isTraceEnabled()) {
			_log.trace("gcsStore component already set");
		}
	}

	private void _setupEncryptedCommunication() {
		String keyValue = PropsUtil.get(KEY_PROPERTY);

		if ((keyValue == null) || keyValue.equals(StringPool.BLANK)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Property \"dl.store.gcs.aes256.key\" should be set to " +
						"encrypt stored files. Using default storage. The " +
							"key must be AES 256bit key, encoded in Base64.");
			}

			_blobDecryptSourceOption = null;

			_blobEncryptWriteOption = null;
		}
		else {
			_blobDecryptSourceOption = Blob.BlobSourceOption.decryptionKey(
				keyValue);

			_blobEncryptWriteOption = Storage.BlobWriteOption.encryptionKey(
				keyValue);
		}
	}

	private void _writeInputStream(InputStream inputStream, WriteChannel writer)
		throws IOException, PortalException {

		byte[] buffer = new byte[_WRITE_BUFFER_SIZE];
		int limit = -1;

		Stopwatch outputWatch = null;
		Stopwatch overallClock = null;
		long totalWriteTimeNanoSec = 0;
		int writtenBytes = 0;
		int writtenTotal = 0;

		if (_log.isTraceEnabled()) {
			_log.trace("Writing out to buffer...");

			outputWatch = Stopwatch.createUnstarted();

			overallClock = Stopwatch.createStarted();
		}

		while ((limit = inputStream.read(buffer)) >= 0) {
			try {
				if (_log.isTraceEnabled()) {
					outputWatch.start();
				}

				writtenBytes = writer.write(ByteBuffer.wrap(buffer, 0, limit));

				if (_log.isTraceEnabled()) {
					outputWatch.stop();

					long elapsed = outputWatch.elapsed(TimeUnit.NANOSECONDS);

					totalWriteTimeNanoSec += elapsed;

					writtenTotal += writtenBytes;

					outputWatch.reset();
				}
			}
			catch (IOException ioException) {
				throw new PortalException(ioException);
			}
		}

		if (_log.isTraceEnabled()) {
			overallClock.stop();

			long elapsed = overallClock.elapsed(TimeUnit.MILLISECONDS);

			String traceMsg = String.format(
				"Took %d milliseconds to transferring %d bytes", elapsed,
				writtenTotal);

			_log.trace(traceMsg);

			traceMsg = String.format(
				"Took %d nanoseconds (%d milliseconds) writing to GCS",
				totalWriteTimeNanoSec, totalWriteTimeNanoSec / 1000000);

			_log.trace(traceMsg);
		}
	}

	private static final int _WRITE_BUFFER_SIZE = 1024;

	private static final Log _log = LogFactoryUtil.getLog(GCSStore.class);

	private Blob.BlobSourceOption _blobDecryptSourceOption;
	private Storage.BlobWriteOption _blobEncryptWriteOption;
	private BucketInfo _bucketInfo;
	private Storage _gcsStore;
	private GCSStoreConfiguration _gcsStoreConfiguration;

	@Reference
	private GCSKeyTransformer _keyTransformer;

}