/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
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
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import com.liferay.document.library.kernel.exception.AccessDeniedException;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.io.unsync.UnsyncFilterInputStream;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.s3.configuration.S3StoreConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.ProxyConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3AsyncClientBuilder;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

/**
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 * @author Edward C. Han
 * @author Vilmos Papp
 * @author Máté Thurzó
 * @author Manuel de la Peña
 * @author Daniel Sanz
 */
@Component(
	configurationPid = "com.liferay.portal.store.s3.configuration.S3StoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = "store.type=com.liferay.portal.store.s3.S3Store",
	service = Store.class
)
public class S3Store implements Store {

	public void abortMultipartUploads(Date date) {
		_transferManager.abortMultipartUploads(
			_s3StoreConfiguration.bucketName(), date);
	}

	@Override
	public void addFile(
		long companyId, long repositoryId, String fileName, String versionLabel,
		InputStream inputStream) {

		if (hasFile(companyId, repositoryId, fileName, versionLabel)) {
			deleteFile(companyId, repositoryId, fileName, versionLabel);
		}

		File file = null;

		try {
			file = FileUtil.createTempFile(inputStream);

			Upload upload = null;

			try {
				String key = S3KeyTransformerUtil.getFileVersionKey(
					companyId, repositoryId, fileName, versionLabel);

				PutObjectRequest putObjectRequest = new PutObjectRequest(
					_s3StoreConfiguration.bucketName(), key, file);

				putObjectRequest.withStorageClass(_storageClass);

				upload = _transferManager.upload(putObjectRequest);

				upload.waitForCompletion();
			}
			catch (AmazonClientException amazonClientException) {
				throw _transform(amazonClientException);
			}
			catch (InterruptedException interruptedException) {
				if (_log.isDebugEnabled()) {
					_log.debug(interruptedException);
				}

				upload.abort();

				Thread thread = Thread.currentThread();

				thread.interrupt();
			}
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		try {
			String[] keys = new String[_DELETE_MAX];

			List<S3ObjectSummary> s3ObjectSummaries = _getS3ObjectSummaries(
				S3KeyTransformerUtil.getDirectoryKey(
					companyId, repositoryId, dirName));

			Iterator<S3ObjectSummary> iterator = s3ObjectSummaries.iterator();

			while (iterator.hasNext()) {
				DeleteObjectsRequest deleteObjectsRequest =
					new DeleteObjectsRequest(
						_s3StoreConfiguration.bucketName());

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
		catch (AmazonClientException amazonClientException) {
			throw _transform(amazonClientException);
		}
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		try {
			_amazonS3.deleteObject(
				new DeleteObjectRequest(
					_s3StoreConfiguration.bucketName(),
					S3KeyTransformerUtil.getFileVersionKey(
						companyId, repositoryId, fileName, versionLabel)));
		}
		catch (AmazonClientException amazonClientException) {
			throw _transform(amazonClientException);
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		try {
			if (Validator.isNull(versionLabel)) {
				versionLabel = _getHeadVersionLabel(
					companyId, repositoryId, fileName);
			}

			S3Object s3Object = _amazonS3.getObject(
				new GetObjectRequest(
					_s3StoreConfiguration.bucketName(),
					S3KeyTransformerUtil.getFileVersionKey(
						companyId, repositoryId, fileName, versionLabel)));

			if (s3Object == null) {
				throw new NoSuchFileException(
					companyId, repositoryId, fileName, versionLabel);
			}

			InputStream s3InputStream = s3Object.getObjectContent();

			if (s3InputStream == null) {
				throw new IOException("S3 object input stream is null");
			}

			return new UnsyncFilterInputStream(s3InputStream) {

				@Override
				public void close() throws IOException {
					super.close();

					s3Object.close();
				}

			};
		}
		catch (AmazonClientException amazonClientException) {
			if (_isFileNotFound(amazonClientException)) {
				throw new NoSuchFileException(
					companyId, repositoryId, fileName, versionLabel);
			}

			throw _transform(amazonClientException);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		String key = null;

		if (Validator.isNull(dirName)) {
			key = S3KeyTransformerUtil.getRepositoryKey(
				companyId, repositoryId);
		}
		else {
			key = S3KeyTransformerUtil.getDirectoryKey(
				companyId, repositoryId, dirName);
		}

		List<S3ObjectSummary> s3ObjectSummaries = _getS3ObjectSummaries(key);

		Iterator<S3ObjectSummary> iterator = s3ObjectSummaries.iterator();

		String[] fileNames = new String[s3ObjectSummaries.size()];

		for (int i = 0; i < fileNames.length; i++) {
			S3ObjectSummary s3ObjectSummary = iterator.next();

			fileNames[i] = S3KeyTransformerUtil.getFileName(
				s3ObjectSummary.getKey());
		}

		return fileNames;
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = _getHeadVersionLabel(
				companyId, repositoryId, fileName);
		}

		ObjectMetadata objectMetadata = _amazonS3.getObjectMetadata(
			new GetObjectMetadataRequest(
				_s3StoreConfiguration.bucketName(),
				S3KeyTransformerUtil.getFileVersionKey(
					companyId, repositoryId, fileName, versionLabel)));

		if (objectMetadata == null) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		return objectMetadata.getContentLength();
	}

	@Override
	public String[] getFileVersions(
		long companyId, long repositoryId, String fileName) {

		List<S3ObjectSummary> s3ObjectSummaries = _getS3ObjectSummaries(
			S3KeyTransformerUtil.getFileKey(companyId, repositoryId, fileName));

		if (s3ObjectSummaries.isEmpty()) {
			return StringPool.EMPTY_ARRAY;
		}

		String[] versions = new String[s3ObjectSummaries.size()];

		for (int i = 0; i < s3ObjectSummaries.size(); i++) {
			S3ObjectSummary s3ObjectSummary = s3ObjectSummaries.get(i);

			String versionKey = s3ObjectSummary.getKey();

			versions[i] = versionKey.substring(
				versionKey.lastIndexOf(CharPool.SLASH) + 1);
		}

		Arrays.sort(versions, DLUtil::compareVersions);

		return versions;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		try {
			if (Validator.isNull(versionLabel)) {
				versionLabel = _getHeadVersionLabel(
					companyId, repositoryId, fileName);
			}

			return _amazonS3.doesObjectExist(
				_s3StoreConfiguration.bucketName(),
				S3KeyTransformerUtil.getFileVersionKey(
					companyId, repositoryId, fileName, versionLabel));
		}
		catch (AmazonClientException amazonClientException) {
			if (_isFileNotFound(amazonClientException)) {
				return false;
			}

			throw _transform(amazonClientException);
		}
		catch (NoSuchFileException noSuchFileException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFileException);
			}

			return false;
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_s3StoreConfiguration = ConfigurableUtil.createConfigurable(
			S3StoreConfiguration.class, properties);

		AwsCredentialsProvider awsCredentialsProvider = null;

		if (Validator.isNotNull(_s3StoreConfiguration.accessKey()) &&
			Validator.isNotNull(_s3StoreConfiguration.secretKey())) {

			awsCredentialsProvider = StaticCredentialsProvider.create(
				AwsBasicCredentials.create(
					_s3StoreConfiguration.accessKey(),
					_s3StoreConfiguration.secretKey()));
		}
		else {
			awsCredentialsProvider = DefaultCredentialsProvider.create();
		}

		NettyNioAsyncHttpClient.Builder asyncHttpClientBuilder =
			NettyNioAsyncHttpClient.builder();

		asyncHttpClientBuilder.connectionTimeout(
			Duration.ofMillis(_s3StoreConfiguration.connectionTimeout()));
		asyncHttpClientBuilder.maxConcurrency(
			_s3StoreConfiguration.httpClientMaxConnections());

		String proxyHost = _s3StoreConfiguration.proxyHost();

		if (Validator.isNotNull(proxyHost)) {
			ProxyConfiguration.Builder proxyConfigurationBuilder =
				ProxyConfiguration.builder();

			proxyConfigurationBuilder.host(proxyHost);
			proxyConfigurationBuilder.port(_s3StoreConfiguration.proxyPort());

			String proxyAuthType = _s3StoreConfiguration.proxyAuthType();

			if (Objects.equals(proxyAuthType, "username-password")) {
				proxyConfigurationBuilder.password(
					_s3StoreConfiguration.proxyPassword());
				proxyConfigurationBuilder.username(
					_s3StoreConfiguration.proxyUsername());
			}

			asyncHttpClientBuilder.proxyConfiguration(
				proxyConfigurationBuilder.build());
		}

		S3AsyncClientBuilder s3AsyncClientBuilder = S3AsyncClient.builder(
		).credentialsProvider(
			awsCredentialsProvider
		).forcePathStyle(
			_s3StoreConfiguration.s3PathStyle()
		).httpClientBuilder(
			asyncHttpClientBuilder
		).multipartEnabled(
			true
		).multipartConfiguration(
			builder -> {
				builder.minimumPartSizeInBytes(
					(long)_s3StoreConfiguration.minimumUploadPartSize());
				builder.thresholdInBytes(
					(long)_s3StoreConfiguration.multipartUploadThreshold());
			}
		).overrideConfiguration(
			overrideBuilder -> overrideBuilder.retryPolicy(
				retryBuilder -> retryBuilder.numRetries(
					_s3StoreConfiguration.httpClientMaxErrorRetry()))
		);

		if (Validator.isNotNull(_s3StoreConfiguration.s3Endpoint())) {
			s3AsyncClientBuilder.endpointOverride(
				URI.create(_s3StoreConfiguration.s3Endpoint()));
		}

		if (Validator.isNotNull(_s3StoreConfiguration.s3Region())) {
			s3AsyncClientBuilder.region(
				Region.of(_s3StoreConfiguration.s3Region()));
		}

		_s3AsyncClient = s3AsyncClientBuilder.build();

		_threadPoolExecutor = new ThreadPoolExecutor(
			_s3StoreConfiguration.corePoolSize(),
			_s3StoreConfiguration.maxPoolSize());

		_s3TransferManager = S3TransferManager.builder(
		).executor(
			_threadPoolExecutor
		).s3Client(
			_s3AsyncClient
		).build();

		try {
			_storageClass = StorageClass.fromValue(
				_s3StoreConfiguration.s3StorageClass());
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_storageClass = StorageClass.Standard;

			if (_log.isWarnEnabled()) {
				_log.warn(
					_s3StoreConfiguration.s3StorageClass() +
						" is not a valid value for the storage class",
					illegalArgumentException);
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		_s3TransferManager.close();
		_s3AsyncClient.close();

		_threadPoolExecutor.shutdown();
	}

	private String _getHeadVersionLabel(
			long companyId, long repositoryId, String fileName)
		throws NoSuchFileException {

		List<S3ObjectSummary> s3ObjectSummaries = _getS3ObjectSummaries(
			S3KeyTransformerUtil.getFileKey(companyId, repositoryId, fileName));

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

	private List<S3ObjectSummary> _getS3ObjectSummaries(String prefix) {
		try {
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest();

			listObjectsRequest.withBucketName(
				_s3StoreConfiguration.bucketName());
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
		catch (AmazonClientException amazonClientException) {
			throw _transform(amazonClientException);
		}
	}

	private boolean _isFileNotFound(
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

	private SystemException _transform(
		AmazonClientException amazonClientException) {

		if (amazonClientException instanceof AmazonServiceException) {
			AmazonServiceException amazonServiceException =
				(AmazonServiceException)amazonClientException;

			StringBundler sb = new StringBundler(11);

			sb.append("{errorCode=");

			String errorCode = amazonServiceException.getErrorCode();

			sb.append(errorCode);

			sb.append(", errorType=");
			sb.append(amazonServiceException.getErrorType());
			sb.append(", message=");
			sb.append(amazonServiceException.getMessage());
			sb.append(", requestId=");
			sb.append(amazonServiceException.getRequestId());
			sb.append(", statusCode=");
			sb.append(amazonServiceException.getStatusCode());
			sb.append("}");

			if (errorCode.equals("AccessDenied")) {
				return new AccessDeniedException(sb.toString());
			}

			return new SystemException(sb.toString());
		}

		return new SystemException(
			amazonClientException.getMessage(), amazonClientException);
	}

	private static final int _DELETE_MAX = 1000;

	private static final String _ERROR_CODE_FILE_NOT_FOUND = "NoSuchKey";

	private static final int _STATUS_CODE_FILE_NOT_FOUND = 404;

	private static final Log _log = LogFactoryUtil.getLog(S3Store.class);

	private AmazonS3 _amazonS3;
	private S3AsyncClient _s3AsyncClient;
	private S3StoreConfiguration _s3StoreConfiguration;
	private S3TransferManager _s3TransferManager;
	private StorageClass _storageClass;
	private ThreadPoolExecutor _threadPoolExecutor;
	private TransferManager _transferManager;

}