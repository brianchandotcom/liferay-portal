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

package com.liferay.portal.store.azure;

import com.azure.core.http.HttpRequest;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Configuration;
import com.azure.core.util.Context;
import com.azure.core.util.logging.LogLevel;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.batch.BlobBatch;
import com.azure.storage.blob.batch.BlobBatchClient;
import com.azure.storage.blob.batch.BlobBatchClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobProperties;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.azure.storage.common.Utility;

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.azure.configuration.AzureStoreConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Josef Sustacek
 */
@Component(
	configurationPid = "com.liferay.portal.store.azure.configuration.AzureStoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "store.type=com.liferay.portal.store.azure.AzureStore",
	service = Store.class
)
public class AzureStore implements Store {

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream inputStream)
		throws PortalException {

		String blobName = _getBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		File tempFile = null;

		try {
			tempFile = FileUtil.createTempFile(inputStream);

			blobClient.uploadFromFile(tempFile.getAbsolutePath(), true);
		}
		catch (IOException ioException) {
			throw new PortalException(
				"Cannot create temp file for upload to Azure", ioException);
		}
		catch (UncheckedIOException uncheckedIOException) {
			throw new PortalException(
				"Failed to upload to Azure from file", uncheckedIOException);
		}
		finally {
			FileUtil.delete(tempFile);
		}
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		String blobPrefix = _getBlobPrefix(
			companyId, repositoryId, dirName);

		ListBlobsOptions listBlobsOptions = new ListBlobsOptions();

		listBlobsOptions.setMaxResultsPerPage(256);
		listBlobsOptions.setPrefix(blobPrefix);

		PagedIterable<BlobItem> pagedIterable = _blobContainerClient.listBlobs(
			listBlobsOptions, null);

		BlobBatchClient blobBatchClient = new BlobBatchClientBuilder(
			_blobContainerClient.getServiceClient()
		).buildClient();

		String blobContainerName = _blobContainerClient.getBlobContainerName();

		for (PagedResponse<BlobItem> pagedResponse :
				pagedIterable.iterableByPage()) {

			List<BlobItem> blobItems = pagedResponse.getValue();

			BlobBatch blobBatch = blobBatchClient.getBlobBatch();

			List<Response<Void>> responses = new ArrayList<>(blobItems.size());

			blobItems.forEach(
				blobItem -> responses.add(
					blobBatch.deleteBlob(
						blobContainerName, blobItem.getName())));

			blobBatchClient.submitBatchWithResponse(
				blobBatch, false, null, Context.NONE);

			for (Response<Void> response : responses) {
				if (response.getStatusCode() >= 400) {
					HttpRequest httpRequest = response.getRequest();

					URL url = httpRequest.getUrl();

					_log.error(
						StringBundler.concat(
							"The blob '", url.getPath(),
							"' could not be deleted using a batch, got status ",
							"code ", response.getStatusCode(), "."));
				}
			}
		}
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String blobName = _getBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		if (blobClient.exists()) {
			blobClient.delete();
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = _fetchFirstVersion(
				companyId, repositoryId, fileName);
		}

		String blobName = _getBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		if (!blobClient.exists()) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel);
		}

		return blobClient.openInputStream();
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		String blobPrefix = _getBlobPrefix(
			companyId, repositoryId, dirName);

		ListBlobsOptions listBlobsOptions = new ListBlobsOptions();

		listBlobsOptions.setPrefix(blobPrefix);

		PagedIterable<BlobItem> pagedIterable = _blobContainerClient.listBlobs(
			listBlobsOptions, null);

		Stream<BlobItem> stream = pagedIterable.stream();

		return stream.map(
			blobItem -> _getFileName(
				companyId, repositoryId, blobItem.getName())
		).toArray(
			String[]::new
		);
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = _fetchFirstVersion(
				companyId, repositoryId, fileName);
		}

		String blobName = _getBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		if (!blobClient.exists()) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel);
		}

		BlobProperties blobProperties = blobClient.getProperties();

		return blobProperties.getBlobSize();
	}

	@Override
	public String[] getFileVersions(
		long companyId, long repositoryId, String fileName) {

		String blobPrefix = _getBlobPrefix(
			companyId, repositoryId, fileName);

		ListBlobsOptions listBlobsOptions = new ListBlobsOptions();

		listBlobsOptions.setPrefix(blobPrefix);

		PagedIterable<BlobItem> pagedIterable =
			_blobContainerClient.listBlobsByHierarchy(
				StringPool.SLASH, listBlobsOptions, null);

		Stream<BlobItem> stream = pagedIterable.stream();

		return stream.filter(
			blobItem -> !GetterUtil.getBoolean(blobItem.isPrefix())
		).map(
			blobItem -> {
				String blobItemName = blobItem.getName();

				return blobItemName.substring(blobPrefix.length());
			}
		).sorted(
			DLUtil::compareVersions
		).toArray(
			String[]::new
		);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		if (Validator.isNull(versionLabel)) {
			String[] versions = getFileVersions(
				companyId, repositoryId, fileName);

			if (ArrayUtil.isNotEmpty(versions)) {
				return true;
			}

			return false;
		}

		String blobName = _getBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		return blobClient.exists();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_azureBlobStorageStoreConfiguration =
			ConfigurableUtil.createConfigurable(
				AzureStoreConfiguration.class, properties);

		BlobContainerClientBuilder blobContainerClientBuilder =
			new BlobContainerClientBuilder();

		blobContainerClientBuilder.connectionString(
			_azureBlobStorageStoreConfiguration.connectionString());

		blobContainerClientBuilder.containerName(
			_azureBlobStorageStoreConfiguration.containerName());

		if (_azureBlobStorageStoreConfiguration.httpLoggingEnabled()) {
			HttpLogOptions defaultHttpLogOptions =
				BlobServiceClientBuilder.getDefaultHttpLogOptions();

			Configuration globalConfiguration =
				Configuration.getGlobalConfiguration();

			blobContainerClientBuilder.httpLogOptions(
				defaultHttpLogOptions.setLogLevel(
					HttpLogDetailLevel.BODY_AND_HEADERS)
			).configuration(
				globalConfiguration.put(
					Configuration.PROPERTY_AZURE_LOG_LEVEL,
					String.valueOf(LogLevel.VERBOSE.getLogLevel()))
			);
		}
		else {
			HttpLogOptions defaultHttpLogOptions =
				BlobServiceClientBuilder.getDefaultHttpLogOptions();

			Configuration globalConfiguration =
				Configuration.getGlobalConfiguration();

			blobContainerClientBuilder.httpLogOptions(
				defaultHttpLogOptions.setLogLevel(HttpLogDetailLevel.NONE)
			).configuration(
				globalConfiguration.put(
					Configuration.PROPERTY_AZURE_LOG_LEVEL,
					String.valueOf(LogLevel.NOT_SET.getLogLevel()))
			);
		}

		if (Validator.isNotNull(
				_azureBlobStorageStoreConfiguration.encryptionScope())) {

			blobContainerClientBuilder.encryptionScope(
				_azureBlobStorageStoreConfiguration.encryptionScope());
		}

		_blobContainerClient = blobContainerClientBuilder.buildClient();

		if (!_blobContainerClient.exists()) {
			throw new SystemException(
				StringBundler.concat(
					"Azure store was configured to store files in container '",
					_blobContainerClient.getBlobContainerName(),
					"' (as blobs), but it does not exist. Please make sure ",
					"the container exists and the used credentials are ",
					"sufficient to access it."));
		}
	}

	@Deactivate
	protected void deactivate() {
		_blobContainerClient = null;
	}

	private String _fetchFirstVersion(
			long companyId, long repositoryId, String fileName)
		throws NoSuchFileException {

		String[] fileVersions = getFileVersions(
			companyId, repositoryId, fileName);

		if (ArrayUtil.isEmpty(fileVersions)) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		return fileVersions[0];
	}

	private String _getBlobName(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return _toFullAzurePath(companyId, repositoryId, fileName, versionLabel);
	}

	private String _getBlobPrefix(
		long companyId, long repositoryId, String dirName) {

		String dirPath = _toFullAzurePath(
			companyId, repositoryId, dirName, null);

		return dirPath + StringPool.SLASH;
	}

	private String _toFullAzurePath(
		long companyId, long repositoryId, String liferayPath,
		String versionLabel) {

		if (StringUtil.startsWith(liferayPath, StringPool.SLASH)) {
			liferayPath = liferayPath.substring(1);
		}

		if (StringUtil.endsWith(liferayPath, StringPool.SLASH)) {
			liferayPath = liferayPath.substring(0, liferayPath.length() - 1);
		}

		StringBundler sb = new StringBundler(7);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);

		if (!liferayPath.isEmpty()) {
			sb.append(StringPool.SLASH);
			sb.append(liferayPath);
		}

		if (Validator.isNotNull(versionLabel)) {
			sb.append(StringPool.SLASH);
			sb.append(versionLabel);
		}

		return sb.toString();
	}

	private String _getFileName(
		long companyId, long repositoryId, String azureBlobName) {

		Objects.requireNonNull(azureBlobName);

		String rootPrefix =
			_toFullAzurePath(companyId, repositoryId, StringPool.BLANK, null) +
			StringPool.SLASH;

		if (!azureBlobName.startsWith(rootPrefix)) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"It looks like blob '", azureBlobName,
					"' does not belong to company: ", companyId,
					"and repository: ", repositoryId));
		}

		String fileNamePathWithVersion = azureBlobName.substring(
			rootPrefix.length());

		if (fileNamePathWithVersion.isEmpty() ||
			!fileNamePathWithVersion.contains(StringPool.SLASH)) {

			throw new IllegalArgumentException(
				StringBundler.concat(
					"The blob '", azureBlobName, "' does not conform to the ",
					"pattern ${companyId}/${repositoryId}/${fileName}",
					"/${versionLabel} -- missing the '/${versionLabel}' part. ",
					"Delete the blob in Azure to fix this (there should be no ",
					"blobs directly under ${companyId}/${repositoryId}, only ",
					"subfolders."));
		}

		return fileNamePathWithVersion.substring(
			0, fileNamePathWithVersion.lastIndexOf(StringPool.SLASH));
	}

	private static final Log _log = LogFactoryUtil.getLog(AzureStore.class);

	private volatile AzureStoreConfiguration
		_azureBlobStorageStoreConfiguration;
	private BlobContainerClient _blobContainerClient;

}