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

package com.liferay.portal.store.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.StorageClass;

import com.liferay.document.library.kernel.exception.DuplicateFileException;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.BaseStore;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.s3.configuration.S3StoreConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 * @author Edward C. Han
 * @author Vilmos Papp
 * @author Mate Thurzo
 * @author Manuel de la Peña
 */
@Component(
	configurationPid = "com.liferay.portal.store.s3.configuration.S3StoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "store.type=com.liferay.portal.store.s3.S3Store",
	service = Store.class
)
public class S3Store extends BaseStore {

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException {

		if (hasFile(companyId, repositoryId, fileName)) {
			throw new DuplicateFileException(companyId, repositoryId, fileName);
		}

		try {
			String key = _s3KeyTransformer.getFileVersionKey(
				companyId, repositoryId, fileName, VERSION_DEFAULT);

			PutObjectRequest putObjectRequest = new PutObjectRequest(
				_bucketName, key, is, new ObjectMetadata());

			putObjectRequest.withStorageClass(_storageClass);

			_amazonS3.putObject(putObjectRequest);
		}
		catch (AmazonClientException ace) {
			throw transform(ace);
		}
		finally {
			StreamUtil.cleanUp(is);
		}
	}

	@Override
	public void checkRoot(long companyId) {
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		String key = _s3KeyTransformer.getDirectoryKey(
			companyId, repositoryId, dirName);

		deleteObjects(key);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		String key = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, fileName);

		deleteObjects(key);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		try {
			String key = _s3KeyTransformer.getFileVersionKey(
				companyId, repositoryId, fileName, versionLabel);

			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
				_bucketName, key);

			_amazonS3.deleteObject(deleteObjectRequest);
		}
		catch (AmazonClientException ace) {
			throw transform(ace);
		}
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		try {
			S3Object s3Object = getS3Object(
				companyId, repositoryId, fileName, versionLabel);

			File file = _s3FileCache.getCacheFile(s3Object, fileName);

			_s3FileCache.cleanUpCacheFiles();

			return file;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		S3Object s3Object = getS3Object(
			companyId, repositoryId, fileName, versionLabel);

		return s3Object.getObjectContent();
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		return getFileNames(companyId, repositoryId, StringPool.BLANK);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		String key = null;

		if (Validator.isNull(dirName)) {
			key = _s3KeyTransformer.getRepositoryKey(companyId, repositoryId);
		}
		else {
			key = _s3KeyTransformer.getDirectoryKey(
				companyId, repositoryId, dirName);
		}

		List<S3ObjectSummary> s3ObjectSummaries = getS3ObjectSummaries(key);

		Iterator<S3ObjectSummary> iterator = s3ObjectSummaries.iterator();

		String[] fileNames = new String[s3ObjectSummaries.size()];

		for (int i = 0; i < fileNames.length; i++) {
			S3ObjectSummary s3ObjectSummary = iterator.next();

			fileNames[i] = _s3KeyTransformer.getFileName(
				s3ObjectSummary.getKey());
		}

		return fileNames;
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		String headVersionLabel = getHeadVersionLabel(
			companyId, repositoryId, fileName);

		String key = _s3KeyTransformer.getFileVersionKey(
			companyId, repositoryId, fileName, headVersionLabel);

		GetObjectMetadataRequest getObjectMetadataRequest =
			new GetObjectMetadataRequest(_bucketName, key);

		ObjectMetadata objectMetadata = _amazonS3.getObjectMetadata(
			getObjectMetadataRequest);

		if (objectMetadata == null) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		return objectMetadata.getContentLength();
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		return true;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		S3Object s3Object = null;

		try {
			s3Object = getS3Object(
				companyId, repositoryId, fileName, versionLabel);

			return true;
		}
		catch (NoSuchFileException nsfe) {
			return false;
		}
		finally {
			try {
				if (s3Object != null) {
					s3Object.close();
				}
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn("Uanble to to close S3 object", ioe);
				}
			}
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException {

		if (repositoryId == newRepositoryId) {
			throw new DuplicateFileException(
				companyId, newRepositoryId, fileName);
		}

		String oldKey = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, fileName);
		String newKey = _s3KeyTransformer.getFileKey(
			companyId, newRepositoryId, fileName);

		moveObjects(oldKey, newKey);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException {

		if (fileName.equals(newFileName)) {
			throw new DuplicateFileException(companyId, repositoryId, fileName);
		}

		String oldKey = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, fileName);
		String newKey = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, newFileName);

		moveObjects(oldKey, newKey);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException {

		if (hasFile(companyId, repositoryId, fileName, versionLabel)) {
			throw new DuplicateFileException(
				companyId, repositoryId, fileName, versionLabel);
		}

		try {
			String key = _s3KeyTransformer.getFileVersionKey(
				companyId, repositoryId, fileName, versionLabel);

			PutObjectRequest putObjectRequest = new PutObjectRequest(
				_bucketName, key, is, new ObjectMetadata());

			_amazonS3.putObject(putObjectRequest);
		}
		catch (AmazonClientException ace) {
			throw transform(ace);
		}
		finally {
			StreamUtil.cleanUp(is);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_s3StoreConfiguration = ConfigurableUtil.createConfigurable(
			S3StoreConfiguration.class, properties);

		_awsCredentialsProvider = getAWSCredentialsProvider();

		_amazonS3 = getAmazonS3(_awsCredentialsProvider);

		_bucketName = _s3StoreConfiguration.bucketName();

		try {
			if (Validator.isNull(_s3StoreConfiguration.s3StorageClass())) {
				_storageClass = StorageClass.Standard;
			}
			else {
				_storageClass = StorageClass.fromValue(
					_s3StoreConfiguration.s3StorageClass());
			}
		}
		catch (IllegalArgumentException iae) {
			_storageClass = StorageClass.Standard;

			if (_log.isWarnEnabled()) {
				_log.warn(iae);
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		_amazonS3 = null;
		_awsCredentialsProvider = null;
		_bucketName = null;
		_s3StoreConfiguration = null;
	}

	protected void deleteObjects(String prefix) {
		try {
			String[] keys = new String[_DELETE_MAX];

			List<S3ObjectSummary> s3ObjectSummaries = getS3ObjectSummaries(
				prefix);

			Iterator<S3ObjectSummary> iterator = s3ObjectSummaries.iterator();

			while (iterator.hasNext()) {
				DeleteObjectsRequest deleteObjectsRequest =
					new DeleteObjectsRequest(_bucketName);

				for (int i = 0; i < keys.length; i++) {
					if (iterator.hasNext()) {
						S3ObjectSummary s3ObjectSummary = iterator.next();

						keys[i] = s3ObjectSummary.getKey();
					}
					else {
						keys = Arrays.copyOfRange(keys, 0, i);

						break;
					}
				}

				deleteObjectsRequest.withKeys(keys);

				_amazonS3.deleteObjects(deleteObjectsRequest);
			}
		}
		catch (AmazonClientException ace) {
			throw transform(ace);
		}
	}

	protected AmazonS3 getAmazonS3(
		AWSCredentialsProvider awsCredentialsProvider) {

		ClientConfiguration clientConfiguration = getClientConfiguration();

		setProxyConfiguration(clientConfiguration);

		AmazonS3 amazonS3 = new AmazonS3Client(
			awsCredentialsProvider, clientConfiguration);

		Region region = Region.getRegion(
			Regions.fromName(_s3StoreConfiguration.s3Region()));

		amazonS3.setRegion(region);

		return amazonS3;
	}

	protected AWSCredentialsProvider getAWSCredentialsProvider() {
		if (Validator.isNotNull(_s3StoreConfiguration.accessKey()) &&
			Validator.isNotNull(_s3StoreConfiguration.secretKey())) {

			AWSCredentials awsCredentials = new BasicAWSCredentials(
				_s3StoreConfiguration.accessKey(),
				_s3StoreConfiguration.secretKey());

			return new StaticCredentialsProvider(awsCredentials);
		}

		return new DefaultAWSCredentialsProviderChain();
	}

	protected ClientConfiguration getClientConfiguration() {
		ClientConfiguration clientConfiguration = new ClientConfiguration();

		clientConfiguration.setMaxConnections(
			_s3StoreConfiguration.httpClientMaxConnections());

		return clientConfiguration;
	}

	protected String getHeadVersionLabel(
			long companyId, long repositoryId, String fileName)
		throws NoSuchFileException {

		String key = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, fileName);

		List<S3ObjectSummary> s3ObjectSummaries = getS3ObjectSummaries(key);

		Iterator<S3ObjectSummary> iterator = s3ObjectSummaries.iterator();

		String[] keys = new String[s3ObjectSummaries.size()];

		for (int i = 0; i < keys.length; i++) {
			S3ObjectSummary s3ObjectSummary = iterator.next();

			keys[i] = s3ObjectSummary.getKey();
		}

		if (keys.length > 0) {
			Arrays.sort(keys);

			String headVersionKey = keys[keys.length - 1];

			int x = headVersionKey.lastIndexOf(CharPool.SLASH);

			return headVersionKey.substring(x + 1);
		}

		throw new NoSuchFileException(companyId, repositoryId, fileName);
	}

	protected S3Object getS3Object(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		try {
			if (Validator.isNull(versionLabel)) {
				versionLabel = getHeadVersionLabel(
					companyId, repositoryId, fileName);
			}

			String key = _s3KeyTransformer.getFileVersionKey(
				companyId, repositoryId, fileName, versionLabel);

			GetObjectRequest getObjectRequest = new GetObjectRequest(
				_bucketName, key);

			S3Object s3Object = _amazonS3.getObject(getObjectRequest);

			if (s3Object == null) {
				throw new NoSuchFileException(
					companyId, repositoryId, fileName, versionLabel);
			}
			else {
				return s3Object;
			}
		}
		catch (AmazonClientException ace) {
			if (isFileNotFound(ace)) {
				throw new NoSuchFileException(
					companyId, repositoryId, fileName, versionLabel);
			}

			throw transform(ace);
		}
	}

	protected List<S3ObjectSummary> getS3ObjectSummaries(String prefix) {
		try {
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest();

			listObjectsRequest.withBucketName(_bucketName);
			listObjectsRequest.withPrefix(prefix);

			ObjectListing objectListing = _amazonS3.listObjects(
				listObjectsRequest);

			List<S3ObjectSummary> s3ObjectSummaries = new ArrayList<>(
				objectListing.getMaxKeys());

			while (true) {
				s3ObjectSummaries.addAll(objectListing.getObjectSummaries());

				if (objectListing.isTruncated()) {
					objectListing = _amazonS3.listNextBatchOfObjects(
						objectListing);
				}
				else {
					break;
				}
			}

			return s3ObjectSummaries;
		}
		catch (AmazonClientException ace) {
			throw transform(ace);
		}
	}

	protected boolean isFileNotFound(
		AmazonClientException amazonClientException) {

		if (amazonClientException instanceof AmazonServiceException) {
			AmazonServiceException amazonServiceException =
				(AmazonServiceException)amazonClientException;

			String errorCode = amazonServiceException.getErrorCode();

			if (errorCode.equals(_ERROR_CODE_FILE_NOT_FOUND) &&
				(amazonServiceException.getStatusCode() ==
					_STATUS_CODE_FILE_NOT_FOUND)) {

				return true;
			}
		}

		return false;
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		deactivate();

		activate(properties);
	}

	protected void moveObjects(String oldPrefix, String newPrefix)
		throws DuplicateFileException {

		ObjectListing objectListing = _amazonS3.listObjects(
			_bucketName, newPrefix);

		List<S3ObjectSummary> newS3ObjectSummaries =
			objectListing.getObjectSummaries();

		if (!newS3ObjectSummaries.isEmpty()) {
			throw new DuplicateFileException(
				"Duplicate S3 object found when moving files from " +
					oldPrefix + " to " + newPrefix);
		}

		List<S3ObjectSummary> oldS3ObjectSummaries = getS3ObjectSummaries(
			oldPrefix);

		for (S3ObjectSummary s3ObjectSummary : oldS3ObjectSummaries) {
			String oldKey = s3ObjectSummary.getKey();

			String newKey = _s3KeyTransformer.moveKey(
				oldKey, oldPrefix, newPrefix);

			CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
				_bucketName, oldKey, _bucketName, newKey);

			_amazonS3.copyObject(copyObjectRequest);
		}

		for (S3ObjectSummary objectSummary : oldS3ObjectSummaries) {
			String oldKey = objectSummary.getKey();

			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
				_bucketName, oldKey);

			_amazonS3.deleteObject(deleteObjectRequest);
		}
	}

	protected void setProxyConfiguration(
		ClientConfiguration clientConfiguration) {

		String proxyHost = _s3StoreConfiguration.proxyHost();
		int proxyPort = GetterUtil.getInteger(
			_s3StoreConfiguration.proxyPort(), -1);

		if (Validator.isNull(proxyHost) || proxyPort == -1) {
			return;
		}

		clientConfiguration.setProxyHost(proxyHost);
		clientConfiguration.setProxyPort(proxyPort);

		String proxyAuthType = _s3StoreConfiguration.proxyAuthType();

		if (!proxyAuthType.equals("username-password") &&
			!proxyAuthType.equals("ntlm")) {
			return;
		}

		String proxyPassword = _s3StoreConfiguration.proxyPassword();
		String proxyUsername = _s3StoreConfiguration.proxyUsername();

		clientConfiguration.setProxyPassword(proxyPassword);
		clientConfiguration.setProxyUsername(proxyUsername);

		if (proxyAuthType.equals("ntlm")) {
			String ntlmProxyDomain = _s3StoreConfiguration.ntlmProxyDomain();
			String ntlmProxyWorkstation =
				_s3StoreConfiguration.ntlmProxyWorkstation();

			clientConfiguration.setProxyDomain(ntlmProxyDomain);
			clientConfiguration.setProxyWorkstation(ntlmProxyWorkstation);
		}
	}

	@Reference(unbind = "-")
	protected void setS3FileCache(S3FileCache s3FileCache) {
		_s3FileCache = s3FileCache;
	}

	@Reference(unbind = "-")
	protected void setS3KeyTransformer(S3KeyTransformer s3KeyTransformer) {
		_s3KeyTransformer = s3KeyTransformer;
	}

	protected SystemException transform(
		AmazonClientException amazonClientException) {

		if (amazonClientException instanceof AmazonServiceException) {
			AmazonServiceException amazonServiceException =
				(AmazonServiceException)amazonClientException;

			StringBundler sb = new StringBundler(11);

			sb.append("{errorCode=");
			sb.append(amazonServiceException.getErrorCode());
			sb.append(", errorType=");
			sb.append(amazonServiceException.getErrorType());
			sb.append(", message=");
			sb.append(amazonServiceException.getMessage());
			sb.append(", requestId=");
			sb.append(amazonServiceException.getRequestId());
			sb.append(", statusCode=");
			sb.append(amazonServiceException.getStatusCode());
			sb.append("}");

			return new SystemException(sb.toString());
		}
		else {
			return new SystemException(
				amazonClientException.getMessage(), amazonClientException);
		}
	}

	private static final int _DELETE_MAX = 1000;

	private static final String _ERROR_CODE_FILE_NOT_FOUND = "NoSuchKey";

	private static final int _STATUS_CODE_FILE_NOT_FOUND = 404;

	private static final Log _log = LogFactoryUtil.getLog(S3Store.class);

	private static volatile S3StoreConfiguration _s3StoreConfiguration;

	private AmazonS3 _amazonS3;
	private AWSCredentialsProvider _awsCredentialsProvider;
	private String _bucketName;
	private S3FileCache _s3FileCache;
	private S3KeyTransformer _s3KeyTransformer;
	private StorageClass _storageClass;

}