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

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			_getBlobItemName(companyId, repositoryId, fileName, versionLabel));

		File tempFile = null;

		try {
			tempFile = FileUtil.createTempFile(inputStream);

			blobClient.uploadFromFile(tempFile.getAbsolutePath(), true);
		}
		catch (IOException ioException) {
			throw new PortalException(
				"Unable to create temp file", ioException);
		}
		catch (UncheckedIOException uncheckedIOException) {
			throw new PortalException(
				"Unable to add file", uncheckedIOException);
		}
		finally {
			FileUtil.delete(tempFile);
		}
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		BlobBatchClient blobBatchClient = new BlobBatchClientBuilder(
			_blobContainerClient.getServiceClient()
		).buildClient();

		ListBlobsOptions listBlobsOptions = new ListBlobsOptions();

		listBlobsOptions.setMaxResultsPerPage(256);
		listBlobsOptions.setPrefix(
			_getPrefix(companyId, repositoryId, dirName));

		PagedIterable<BlobItem> pagedIterable = _blobContainerClient.listBlobs(
			listBlobsOptions, null);

		for (PagedResponse<BlobItem> pagedResponse :
				pagedIterable.iterableByPage()) {

			BlobBatch blobBatch = blobBatchClient.getBlobBatch();

			List<BlobItem> blobItems = pagedResponse.getValue();

			List<Response<Void>> responses = new ArrayList<>(blobItems.size());

			blobItems.forEach(
				blobItem -> responses.add(
					blobBatch.deleteBlob(
						_blobContainerClient.getBlobContainerName(),
						blobItem.getName())));

			if (!blobItems.isEmpty()) {
				blobBatchClient.submitBatchWithResponse(
					blobBatch, false, null, Context.NONE);
			}

			for (Response<Void> response : responses) {
				if (response.getStatusCode() < 400) {
					continue;
				}

				HttpRequest httpRequest = response.getRequest();

				_log.error(
					StringBundler.concat(
						"Unable to delete ", httpRequest.getUrl(),
						" due to status code ", response.getStatusCode()));
			}
		}
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			_getBlobItemName(companyId, repositoryId, fileName, versionLabel));

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
			versionLabel = _getFirstFileVersion(
				companyId, repositoryId, fileName);
		}

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			_getBlobItemName(companyId, repositoryId, fileName, versionLabel));

		if (!blobClient.exists()) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel);
		}

		return blobClient.openInputStream();
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		ListBlobsOptions listBlobsOptions = new ListBlobsOptions();

		listBlobsOptions.setPrefix(
			_getPrefix(companyId, repositoryId, dirName));

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
			versionLabel = _getFirstFileVersion(
				companyId, repositoryId, fileName);
		}

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			_getBlobItemName(companyId, repositoryId, fileName, versionLabel));

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

		ListBlobsOptions listBlobsOptions = new ListBlobsOptions();

		String prefix = _getPrefix(companyId, repositoryId, fileName);

		listBlobsOptions.setPrefix(prefix);

		PagedIterable<BlobItem> pagedIterable =
			_blobContainerClient.listBlobsByHierarchy(
				StringPool.SLASH, listBlobsOptions, null);

		Stream<BlobItem> stream = pagedIterable.stream();

		return stream.filter(
			blobItem -> !GetterUtil.getBoolean(blobItem.isPrefix())
		).map(
			blobItem -> {
				String blobItemName = blobItem.getName();

				return blobItemName.substring(prefix.length());
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

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			_getBlobItemName(companyId, repositoryId, fileName, versionLabel));

		return blobClient.exists();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		BlobContainerClientBuilder blobContainerClientBuilder =
			new BlobContainerClientBuilder();

		AzureStoreConfiguration azureStoreConfiguration =
			ConfigurableUtil.createConfigurable(
				AzureStoreConfiguration.class, properties);

		Configuration globalConfiguration =
			Configuration.getGlobalConfiguration();
		HttpLogOptions httpLogOptions =
			BlobServiceClientBuilder.getDefaultHttpLogOptions();

		if (azureStoreConfiguration.httpLoggingEnabled()) {
			blobContainerClientBuilder.configuration(
				globalConfiguration.put(
					Configuration.PROPERTY_AZURE_LOG_LEVEL,
					String.valueOf(LogLevel.VERBOSE.getLogLevel()))
			).httpLogOptions(
				httpLogOptions.setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)
			);
		}
		else {
			blobContainerClientBuilder.configuration(
				globalConfiguration.put(
					Configuration.PROPERTY_AZURE_LOG_LEVEL,
					String.valueOf(LogLevel.NOT_SET.getLogLevel()))
			).httpLogOptions(
				httpLogOptions.setLogLevel(HttpLogDetailLevel.NONE)
			);
		}

		blobContainerClientBuilder.connectionString(
			azureStoreConfiguration.connectionString());
		blobContainerClientBuilder.containerName(
			azureStoreConfiguration.containerName());

		if (Validator.isNotNull(azureStoreConfiguration.encryptionScope())) {
			blobContainerClientBuilder.encryptionScope(
				azureStoreConfiguration.encryptionScope());
		}

		_blobContainerClient = blobContainerClientBuilder.buildClient();

		if (!_blobContainerClient.exists()) {
			throw new SystemException(
				"Azure store " + _blobContainerClient.getBlobContainerName() +
					" does not exist");
		}
	}

	@Deactivate
	protected void deactivate() {
		_blobContainerClient = null;
	}

	private String _getAzurePath(
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

	private String _getBlobItemName(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return Utility.urlEncode(
			_getAzurePath(companyId, repositoryId, fileName, versionLabel));
	}

	private String _getFileName(
		long companyId, long repositoryId, String blobItemName) {

		Objects.requireNonNull(blobItemName);

		String prefix =
			_getAzurePath(companyId, repositoryId, StringPool.BLANK, null) +
				StringPool.SLASH;

		if (!blobItemName.startsWith(prefix)) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Blob ", blobItemName, " does not belong to company ",
					companyId, " and repository ", repositoryId));
		}

		String fileNameAndVersionLabel = blobItemName.substring(
			prefix.length());

		if (fileNameAndVersionLabel.isEmpty() ||
			!fileNameAndVersionLabel.contains(StringPool.SLASH)) {

			throw new IllegalArgumentException(
				StringBundler.concat(
					"Blob ", blobItemName, " does not conform to the pattern ",
					"${companyId}/${repositoryId}/${fileName}",
					"/${versionLabel}"));
		}

		return fileNameAndVersionLabel.substring(
			0, fileNameAndVersionLabel.lastIndexOf(StringPool.SLASH));
	}

	private String _getFirstFileVersion(
			long companyId, long repositoryId, String fileName)
		throws NoSuchFileException {

		String[] fileVersions = getFileVersions(
			companyId, repositoryId, fileName);

		if (ArrayUtil.isEmpty(fileVersions)) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		return fileVersions[0];
	}

	private String _getPrefix(
		long companyId, long repositoryId, String dirName) {

		return _getAzurePath(companyId, repositoryId, dirName, null) +
			StringPool.SLASH;
	}

	private static final Log _log = LogFactoryUtil.getLog(AzureStore.class);

	private volatile BlobContainerClient _blobContainerClient;

}