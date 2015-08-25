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

import aQute.bnd.annotation.metatype.Configurable;

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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.s3.configuration.S3StoreConfiguration;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.store.BaseStore;
import com.liferay.portlet.documentlibrary.store.Store;

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
			ObjectMetadata objectMetadata = constructMetadata(fileName);

			String objectKey = _s3KeyTransformer.getFileVersionKey(
				companyId, repositoryId, fileName, VERSION_DEFAULT);

			PutObjectRequest request = new PutObjectRequest(
				_bucketName, objectKey, is, objectMetadata);

			request.withStorageClass(_storageClass);

			_amazonS3.putObject(request);
		}
		catch (AmazonClientException ae) {
			throw convertAWSException(ae);
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

		String prefix = _s3KeyTransformer.getDirectoryKey(
			companyId, repositoryId, dirName);

		deleteObjectsWithPrefix(prefix);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		String prefix = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, fileName);

		deleteObjectsWithPrefix(prefix);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String key = _s3KeyTransformer.getFileVersionKey(
			companyId, repositoryId, fileName, versionLabel);

		try {
			DeleteObjectRequest request = new DeleteObjectRequest(
				_bucketName, key);

			_amazonS3.deleteObject(request);
		}
		catch (AmazonClientException ae) {
			throw convertAWSException(ae);
		}
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		S3Object s3Object = getS3Object(
			companyId, repositoryId, fileName, versionLabel);

		try {
			File tempFile = _s3FileCache.getTempFile(s3Object, fileName);

			_s3FileCache.cleanUpTempFiles();

			return tempFile;
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

		String prefix = null;

		if (Validator.isNull(dirName)) {
			prefix = _s3KeyTransformer.getRepositoryKey(
				companyId, repositoryId);
		}
		else {
			prefix = _s3KeyTransformer.getDirectoryKey(
				companyId, repositoryId, dirName);
		}

		List<S3ObjectSummary> objectSummaries = listObjectsWithPrefix(prefix);

		String[] fileNames = new String[objectSummaries.size()];

		Iterator<S3ObjectSummary> objectSummaryIterator =
			objectSummaries.iterator();

		for (int i = 0; i < fileNames.length; i++) {
			S3ObjectSummary s3ObjectSummary = objectSummaryIterator.next();

			String objectKey = s3ObjectSummary.getKey();

			fileNames[i] = _s3KeyTransformer.getFileName(objectKey);
		}

		return fileNames;
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		String version = getHeadVersionLabel(companyId, repositoryId, fileName);

		String key = _s3KeyTransformer.getFileVersionKey(
			companyId, repositoryId, fileName, version);

		GetObjectMetadataRequest request = new GetObjectMetadataRequest(
			_bucketName, key);

		ObjectMetadata objectMetadata = _amazonS3.getObjectMetadata(request);

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
					_log.warn(
						"Failed to close S3Object, connection to AWS" +
							" S3 bucket may be open",
						ioe);
				}
			}
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException {

		String oldPrefix = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, fileName);
		String newPrefix = _s3KeyTransformer.getFileKey(
			companyId, newRepositoryId, fileName);

		moveObjects(oldPrefix, newPrefix);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException {

		String oldPrefix = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, fileName);
		String newPrefix = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, newFileName);

		moveObjects(oldPrefix, newPrefix);
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
			ObjectMetadata objectMetadata = constructMetadata(fileName);

			String objectKey = _s3KeyTransformer.getFileVersionKey(
				companyId, repositoryId, fileName, versionLabel);

			PutObjectRequest request = new PutObjectRequest(
				_bucketName, objectKey, is, objectMetadata);

			_amazonS3.putObject(request);
		}
		catch (AmazonClientException ae) {
			throw convertAWSException(ae);
		}
		finally {
			StreamUtil.cleanUp(is);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_s3StoreConfiguration = Configurable.createConfigurable(
			S3StoreConfiguration.class, properties);

		_bucketName = _s3StoreConfiguration.bucketName();

		_awsCredentialsProvider = getAWSCredentialsProvider();

		_amazonS3 = getAmazonS3(_awsCredentialsProvider);

		int tempDirCleanUpExpunge =
			_s3StoreConfiguration.tempDirCleanUpExpunge();

		int tempDirCleanUpFrequency =
			_s3StoreConfiguration.tempDirCleanUpFrequency();

		_s3FileCache = new S3FileCacheImpl(
			_s3KeyTransformer, tempDirCleanUpExpunge, tempDirCleanUpFrequency);

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

	protected ObjectMetadata constructMetadata(String filename) {
		return new ObjectMetadata();
	}

	protected SystemException convertAWSException(
		AmazonClientException amazonClientException) {

		if (amazonClientException instanceof AmazonServiceException) {
			AmazonServiceException se =
				(AmazonServiceException)amazonClientException;

			return new SystemException(
				"An error occurred in AWS: " + se.getMessage() +
					" - HTTP Code: " + se.getStatusCode() +
					" - AWS Error Code: " + se.getErrorCode() +
					" - Error Type: " + se.getErrorType() +
					" - Request ID: " + se.getRequestId(),
				se);
		}
		else {
			return new SystemException(
				"An error occurred in the AWS client: " +
					amazonClientException.getMessage(),
				amazonClientException);
		}
	}

	@Deactivate
	protected void deactivate() {
		_amazonS3 = null;
		_awsCredentialsProvider = null;
		_bucketName = null;
		_s3StoreConfiguration = null;
	}

	protected void deleteObjectsWithPrefix(String prefix) {
		try {
			List<S3ObjectSummary> objects = listObjectsWithPrefix(prefix);
			String[] objectKeysArray = new String[_MAX_AWS_MULTI_DELETE_SIZE];
			Iterator<S3ObjectSummary> iterator = objects.iterator();

			while (iterator.hasNext()) {
				DeleteObjectsRequest deleteObjectsRequest =
					new DeleteObjectsRequest(_bucketName);

				for (int i = 0; i < objectKeysArray.length; i++) {
					if (iterator.hasNext()) {
						objectKeysArray[i] = iterator.next().getKey();
					}
					else {
						objectKeysArray = Arrays.copyOfRange(
							objectKeysArray, 0, i);

						break;
					}
				}

				deleteObjectsRequest.withKeys(objectKeysArray);

				_amazonS3.deleteObjects(deleteObjectsRequest);
			}
		}
		catch (AmazonClientException ae) {
			throw convertAWSException(ae);
		}
	}

	protected AmazonS3 getAmazonS3(
		AWSCredentialsProvider awsCredentialsProvider) {

		ClientConfiguration clientConfiguration = getClientConfiguration();

		AmazonS3 amazonS3 = new AmazonS3Client(
			awsCredentialsProvider, clientConfiguration);

		Region region = Region.getRegion(
			Regions.fromName(_s3StoreConfiguration.s3Region()));

		amazonS3.setRegion(region);

		return amazonS3;
	}

	protected AWSCredentialsProvider getAWSCredentialsProvider() {
		String accessKey = _s3StoreConfiguration.accessKey();
		String secretKey = _s3StoreConfiguration.secretKey();

		if (Validator.isNotNull(accessKey) && Validator.isNotNull(secretKey)) {
			AWSCredentials awsCredentials = new BasicAWSCredentials(
				accessKey, secretKey);

			return new StaticCredentialsProvider(awsCredentials);
		}
		else {
			return new DefaultAWSCredentialsProviderChain();
		}
	}

	protected ClientConfiguration getClientConfiguration() {
		ClientConfiguration clientConfiguration = new ClientConfiguration();

		int maxConnections = _s3StoreConfiguration.httpClientMaxConnections();

		clientConfiguration.setMaxConnections(maxConnections);

		return clientConfiguration;
	}

	protected String getHeadVersionLabel(
			long companyId, long repositoryId, String fileName)
		throws NoSuchFileException {

		String prefix = _s3KeyTransformer.getFileKey(
			companyId, repositoryId, fileName);

		List<S3ObjectSummary> versions = listObjectsWithPrefix(prefix);

		String[] keys = new String[versions.size()];

		Iterator<S3ObjectSummary> versionsIterator = versions.iterator();

		for (int i = 0; i < keys.length; i++) {
			S3ObjectSummary s3ObjectSummary = versionsIterator.next();

			keys[i] = s3ObjectSummary.getKey();
		}

		if (keys.length > 0) {
			Arrays.sort(keys);

			String headKey = keys[keys.length - 1];

			int x = headKey.lastIndexOf(CharPool.SLASH);

			return headKey.substring(x + 1);
		}
		else {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}
	}

	protected S3Object getS3Object(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = getHeadVersionLabel(
				companyId, repositoryId, fileName);
		}

		String key = _s3KeyTransformer.getFileVersionKey(
			companyId, repositoryId, fileName, versionLabel);

		try {
			GetObjectRequest request = new GetObjectRequest(_bucketName, key);

			S3Object s3Object = _amazonS3.getObject(request);

			if (s3Object == null) {
				throw new NoSuchFileException(
					companyId, repositoryId, fileName, versionLabel);
			}
			else {
				return s3Object;
			}
		}
		catch (AmazonClientException ae) {
			if (isFileNotFound(ae)) {
				throw new NoSuchFileException(
					companyId, repositoryId, fileName, versionLabel);
			}

			throw convertAWSException(ae);
		}
	}

	/**
	 *	Note: AWS 1.10.11 API docs state that attempting to fetch a file for a
	 *	key that does not exist should result in a null being returned. However,
	 *	actual behavior results in an AmazonClientException of status code 404
	 *	and error code NoSuchKey.
	 */
	protected boolean isFileNotFound(AmazonClientException clientException) {
		if (clientException instanceof AmazonServiceException) {
			AmazonServiceException serviceException =
				(AmazonServiceException)clientException;

			if ((serviceException.getStatusCode() ==
					_AWS_FILE_NOT_FOUND_STATUS_CODE) &&
				serviceException.getErrorCode().equals(
					_AWS_FILE_NOT_FOUND_ERROR_CODE)) {

				return true;
			}
		}

		return false;
	}

	protected List<S3ObjectSummary> listObjectsWithPrefix(String prefix) {
		ListObjectsRequest request = new ListObjectsRequest();
		request.withBucketName(_bucketName);
		request.withPrefix(prefix);

		try {
			ObjectListing objectListing = _amazonS3.listObjects(request);
			List<S3ObjectSummary> summaries = new ArrayList<>(
				objectListing.getMaxKeys());

			do {
				List<S3ObjectSummary> batchSummary =
					objectListing.getObjectSummaries();

				summaries.addAll(batchSummary);
			}
			while (objectListing.isTruncated());

			return summaries;
		}
		catch (AmazonClientException ae) {
			throw convertAWSException(ae);
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties)
		throws PortalException {

		deactivate();

		activate(properties);
	}

	protected void moveObjects(String oldPrefix, String newPrefix)
		throws DuplicateFileException {

		ObjectListing targetPrefix = _amazonS3.listObjects(
			_bucketName, newPrefix);

		if (targetPrefix.getObjectSummaries().size() > 0) {
			throw new DuplicateFileException(
				"Duplicate S3 object found when moving files from " +
					oldPrefix + " to " + newPrefix);
		}

		List<S3ObjectSummary> oldObjectSummaries = listObjectsWithPrefix(
			oldPrefix);

		for (S3ObjectSummary objectSummary : oldObjectSummaries) {
			String oldKey = objectSummary.getKey();
			String newKey = _s3KeyTransformer.moveKey(
				oldKey, oldPrefix, newPrefix);

			CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
				_bucketName, oldKey, _bucketName, newKey);

			_amazonS3.copyObject(copyObjectRequest);
		}

		for (S3ObjectSummary objectSummary : oldObjectSummaries) {
			String oldKey = objectSummary.getKey();

			DeleteObjectRequest deleteOldObjectRequest =
				new DeleteObjectRequest(_bucketName, oldKey);

			_amazonS3.deleteObject(deleteOldObjectRequest);
		}
	}

	@Reference(unbind = "-")
	protected void setS3KeyTransformer(S3KeyTransformer s3KeyTransformer) {
		_s3KeyTransformer = s3KeyTransformer;
	}

	private static final String _AWS_FILE_NOT_FOUND_ERROR_CODE = "NoSuchKey";

	private static final int _AWS_FILE_NOT_FOUND_STATUS_CODE = 404;

	private static final int _MAX_AWS_MULTI_DELETE_SIZE = 1000;

	private static final Log _log = LogFactoryUtil.getLog(S3Store.class);

	private static volatile S3StoreConfiguration _s3StoreConfiguration;

	private AmazonS3 _amazonS3;
	private AWSCredentialsProvider _awsCredentialsProvider;
	private String _bucketName;
	private S3FileCache _s3FileCache;
	private S3KeyTransformer _s3KeyTransformer;
	private StorageClass _storageClass;

}