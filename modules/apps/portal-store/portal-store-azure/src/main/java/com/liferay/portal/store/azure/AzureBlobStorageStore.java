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

import com.azure.core.http.policy.HttpLogDetailLevel;
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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.azure.internal.FullPathsMapper;
import com.liferay.portal.store.azure.internal.configuration.AzureBlobStorageStoreConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Josef Sustacek
 */
@Component(
	configurationPid = "com.liferay.portal.store.azure.internal.configuration.AzureBlobStorageStoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "store.type=com.liferay.portal.store.azure.AzureBlobStorageStore",
	service = Store.class
)
public class AzureBlobStorageStore implements Store {

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream inputStream)
		throws PortalException {

		String blobName = _fullPathsMapper.toAzureBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Adding file '%s' (v: %s), uploading as blob '%s'.",
					fileName, versionLabel, blobName));
		}

		File dump = null;

		try {
			dump = FileUtil.createTempFile(inputStream);

			blobClient.uploadFromFile(dump.getAbsolutePath(), true);
		}
		catch (IOException ioe) {
			throw new PortalException(
				"Cannot create temp file for upload to Azure", ioe);
		}
		catch (UncheckedIOException uioe) {
			throw new PortalException(
				"Failed to upload to Azure from file", uioe);
		}
		finally {
			FileUtil.delete(dump);
		}
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		String blobsPrefix = _fullPathsMapper.toAzureBlobsPrefix(
			companyId, repositoryId, dirName);

		ListBlobsOptions opts = new ListBlobsOptions().setPrefix(
			blobsPrefix
		).setMaxResultsPerPage(
			256
		);

		PagedIterable<BlobItem> blobItemsToDeletePages =
			_blobContainerClient.listBlobs(opts, null);

		BlobBatchClient batchClient = new BlobBatchClientBuilder(
			_blobContainerClient.getServiceClient()
		).buildClient();

		String containerName = _blobContainerClient.getBlobContainerName();

		for (PagedResponse<BlobItem> blobItemsToDeletePage :
				blobItemsToDeletePages.iterableByPage()) {

			List<BlobItem> blobItemsToDelete = blobItemsToDeletePage.getValue();

			BlobBatch batch = batchClient.getBlobBatch();

			List<Response<Void>> batchItemsResponses = new ArrayList<>(
				blobItemsToDelete.size());

			blobItemsToDelete.forEach(
				bi -> batchItemsResponses.add(
					batch.deleteBlob(containerName, bi.getName())));

			batchClient.submitBatchWithResponse(
				batch, false, null, Context.NONE);

			batchItemsResponses.forEach(
				r -> {
					if (r.getStatusCode() >= 400) {
						_log.error(
							String.format(
								"The blob '%s' could not be deleted using a batch, got status code %s.",
								r.getRequest(
								).getUrl(
								).getPath(),
								r.getStatusCode()));
					}
				});
		}
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String blobName = _fullPathsMapper.toAzureBlobName(
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

		String blobName = _fullPathsMapper.toAzureBlobName(
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

		String blobNamesPrefix = _fullPathsMapper.toAzureBlobsPrefix(
			companyId, repositoryId, dirName);

		ListBlobsOptions opts = new ListBlobsOptions().setPrefix(
			blobNamesPrefix);

		PagedIterable<BlobItem> blobItems = _blobContainerClient.listBlobs(
			opts, null);

		Set<String> fileNames = blobItems.stream(
		).map(
			blobItem -> _fullPathsMapper.toLiferayFileName(
				companyId, repositoryId, blobItem.getName())
		).collect(
			Collectors.toCollection(() -> new LinkedHashSet<>())
		);

		return fileNames.toArray(new String[0]);
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

		String blobName = _fullPathsMapper.toAzureBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		if (!blobClient.exists()) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel);
		}

		BlobProperties properties = blobClient.getProperties();

		return properties.getBlobSize();
	}

	@Override
	public String[] getFileVersions(
		long companyId, long repositoryId, String fileName) {

		String blobsPrefix = _fullPathsMapper.toAzureBlobsPrefix(
			companyId, repositoryId, fileName);

		ListBlobsOptions opts = new ListBlobsOptions().setPrefix(blobsPrefix);

		PagedIterable<BlobItem> blobItems =
			_blobContainerClient.listBlobsByHierarchy(
				StringPool.SLASH, opts, null);

		String[] versions = blobItems.stream(
		).filter(
			bi -> (bi.isPrefix() == null) || !bi.isPrefix()
		).map(
			bi -> bi.getName(
			).substring(
				blobsPrefix.length()
			)
		).sorted(
			DLUtil::compareVersions
		).toArray(
			String[]::new
		);

		return versions;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		if (Validator.isNull(versionLabel)) {
			String blobsPrefix = _fullPathsMapper.toAzureBlobsPrefix(
				companyId, repositoryId, fileName);

			String[] versions = getFileVersions(
				companyId, repositoryId, fileName);

			if ((versions != null) && (versions.length > 0)) {
				return true;
			}

			return false;
		}

		String blobName = _fullPathsMapper.toAzureBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		return blobClient.exists();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_configuration = ConfigurableUtil.createConfigurable(
			AzureBlobStorageStoreConfiguration.class, properties);

		BlobContainerClientBuilder bccBuilder =
			new BlobContainerClientBuilder();

		bccBuilder.connectionString(_configuration.connectionString());

		bccBuilder.containerName(_configuration.containerName());

		if (_configuration.httpLoggingEnabled()) {
			bccBuilder.httpLogOptions(
				BlobServiceClientBuilder.getDefaultHttpLogOptions(
				).setLogLevel(
					HttpLogDetailLevel.BODY_AND_HEADERS
				));

			bccBuilder.configuration(
				Configuration.getGlobalConfiguration(
				).put(
					Configuration.PROPERTY_AZURE_LOG_LEVEL,
					String.valueOf(LogLevel.VERBOSE.getLogLevel())
				));
		}
		else {
			bccBuilder.httpLogOptions(
				BlobServiceClientBuilder.getDefaultHttpLogOptions(
				).setLogLevel(
					HttpLogDetailLevel.NONE
				)
			).configuration(
				Configuration.getGlobalConfiguration(
				).put(
					Configuration.PROPERTY_AZURE_LOG_LEVEL,
					String.valueOf(LogLevel.NOT_SET.getLogLevel())
				)
			);
		}

		if (Validator.isNotNull(_configuration.encryptionScope())) {
			bccBuilder.encryptionScope(_configuration.encryptionScope());
		}

		_blobContainerClient = bccBuilder.buildClient();

		if (!_blobContainerClient.exists()) {
			throw new SystemException(
				String.format(
					"Azure store was configured to store files in container '%s' (as blobs), " +
						"but it does not exist. Please make sure the container exists and the used " +
							"credentials are sufficient to access it.",
					_blobContainerClient.getBlobContainerName()));
		}
	}

	@Deactivate
	protected void deactivate() {
		_blobContainerClient = null;
	}

	private String _fetchFirstVersion(
			long companyId, long repositoryId, String fileName)
		throws NoSuchFileException {

		Objects.requireNonNull(fileName, "'fileName' cannot be null");

		String[] fileVersions = getFileVersions(
			companyId, repositoryId, fileName);

		if ((fileVersions == null) || (fileVersions.length == 0)) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		return fileVersions[0];
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AzureBlobStorageStore.class);

	private BlobContainerClient _blobContainerClient;
	private volatile AzureBlobStorageStoreConfiguration _configuration;
	private final FullPathsMapper _fullPathsMapper = new FullPathsMapper();

}