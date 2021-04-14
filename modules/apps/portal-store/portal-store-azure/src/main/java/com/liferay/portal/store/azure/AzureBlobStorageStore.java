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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.azure.configuration.AzureBlobStorageStoreConfiguration;
import com.liferay.portal.store.azure.internal.FullPathsMapper;
import com.liferay.portal.store.azure.internal.LiferayToAzurePathsMapper;

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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Josef Sustacek
 */
@Component(
	configurationPid = AzureBlobStorageStoreConfiguration.ID,
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "store.type=com.liferay.portal.store.azure.AzureBlobStorageStore",
	service = Store.class
)
public class AzureBlobStorageStore implements Store {

	/**
	 * Whether to use batching (where possible) or not.
	 */
	public static final boolean _USE_BATCHES = true;

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream inputStream)
		throws PortalException {

		Objects.requireNonNull(fileName, "'fileName' cannot be null");
		Objects.requireNonNull(versionLabel, "'versionLabel' cannot be null");
		Objects.requireNonNull(inputStream, "'inputStream' cannot be null");

		String blobName = _liferayToAzurePathsMapper.toAzureBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Adding file '%s' (v: %s), uploading as blob '%s'.",
					fileName, versionLabel, blobName));
		}

		// we don't have the size of the stream (as required by Azure), so we need
		// to first dump the IS to a temp file :-(

		File dump = null;

		try {
			dump = FileUtil.createTempFile(inputStream);

			// no timeout, no context

			blobClient.uploadFromFile(dump.getAbsolutePath(), true);

			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Stream of '%s' (v: %s) uploaded to Azure as blob '%s', via temp file '%s'.",
						fileName, versionLabel, blobName,
						dump.getAbsolutePath()));
			}
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
			if (dump != null) {
				FileUtil.delete(dump);
			}
		}
	}

	// TODO catch AzureException / BlobStorageException and wrap & throw?

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		Objects.requireNonNull(dirName, "'dirName' cannot be null");

		// delete all the blobs under the subtree...

		Objects.requireNonNull(dirName);

		String blobsPrefix = _liferayToAzurePathsMapper.toAzureBlobsPrefix(
			companyId, repositoryId, dirName);

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Deleting directory '%s' (all blobs with prefix '%s').",
					dirName, blobsPrefix));
		}

		// 256 per request is the max for batch client used below, see:
		// https://docs.microsoft.com/en-us/rest/api/storageservices/blob-batch#request-body

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

		// Use batch client to delete all matching blobs in one go.
		// https://docs.microsoft.com/en-us/java/api/overview/azure/storage-blob-batch-readme

		for (PagedResponse<BlobItem> blobItemsToDeletePage :
				blobItemsToDeletePages.iterableByPage()) {

			List<BlobItem> blobItemsToDelete = blobItemsToDeletePage.getValue();

			if (_USE_BATCHES) {
				BlobBatch batch = batchClient.getBlobBatch();

				List<Response<Void>> batchItemsResponses = new ArrayList<>(
					blobItemsToDelete.size());

				blobItemsToDelete.forEach(
					bi -> batchItemsResponses.add(
						batch.deleteBlob(containerName, bi.getName())));

				// if some file could not be deleted, ignore the failure

				batchClient.submitBatchWithResponse(
					batch, false, null, Context.NONE);

				if (_log.isInfoEnabled()) {
					_log.info(
						String.format(
							"Submitted batch to delete %s blob(s).",
							blobItemsToDelete.size()));
				}

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
						else {
							if (_log.isTraceEnabled()) {
								_log.trace(
									String.format(
										"Deletion of %s using batch resulted in %s",
										r.getRequest(
										).getUrl(
										).getPath(),
										r.getStatusCode()));
							}
						}
					});
			}
			else {
				for (BlobItem bi : blobItemsToDelete) {
					BlobClient bc = _blobContainerClient.getBlobClient(
						Utility.urlEncode(bi.getName()));

					bc.delete();
				}
			}
		}
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Objects.requireNonNull(fileName, "'fileName' cannot be null");
		Objects.requireNonNull(versionLabel, "'versionLabel' cannot be null");

		String blobName = _liferayToAzurePathsMapper.toAzureBlobName(
			companyId, repositoryId, fileName, versionLabel);

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Deleting file '%s' (v: %s), as blob '%s'.", fileName,
					versionLabel, blobName));
		}

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		if (blobClient.exists()) {
			blobClient.delete();

			if (_log.isDebugEnabled()) {
				_log.debug(String.format("Blob '%s' deleted.", blobName));
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Blob '%s' does not exist, no need to delete.",
						blobName));
			}
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		Objects.requireNonNull(fileName, "'fileName' cannot be null");

		// The 'versionLabel' may be null, meaning the first one should be returned
		// (just a guess based on S3Store, not a word in the API)...

		if (Validator.isNull(versionLabel)) {
			versionLabel = _fetchFirstVersion(
				companyId, repositoryId, fileName);
		}

		String blobName = _liferayToAzurePathsMapper.toAzureBlobName(
			companyId, repositoryId, fileName, versionLabel);

		BlobClient blobClient = _blobContainerClient.getBlobClient(
			Utility.urlEncode(blobName));

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Opening stream to file '%s' (v: %s), as blob '%s'",
					fileName, versionLabel, blobName));
		}

		if (!blobClient.exists()) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel);
		}

		return blobClient.openInputStream();
	}

	@Override

	// Returns all (even sub-directory's ) children in given directory. Returns absolute paths of files!

	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		Objects.requireNonNull(dirName, "'dirName' cannot be null");

		// "dirName" may be empty ~ "root"

		String blobNamesPrefix = _liferayToAzurePathsMapper.toAzureBlobsPrefix(
			companyId, repositoryId, dirName);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Listing fileNames under '%s', as blobs with prefix '%s'",
					dirName, blobNamesPrefix));
		}

		ListBlobsOptions opts = new ListBlobsOptions().setPrefix(
			blobNamesPrefix);

		PagedIterable<BlobItem> blobItems = _blobContainerClient.listBlobs(
			opts, null);

		// use Set to automatically collapse 2 blobs being 2 versions of the same file into 1 entry
		// use LinkedHashSet to preserve the ordering

		Set<String> fileNames = blobItems.stream(
		).map(
			blobItem -> _liferayToAzurePathsMapper.toLiferayFileName(
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

		Objects.requireNonNull(fileName, "'fileName' cannot be null");

		// The 'versionLabel' may be null, meaning the first one should be returned
		// (just a guess based on S3Store, not a word in the API)...

		if (Validator.isNull(versionLabel)) {
			versionLabel = _fetchFirstVersion(
				companyId, repositoryId, fileName);
		}

		String blobName = _liferayToAzurePathsMapper.toAzureBlobName(
			companyId, repositoryId, fileName, versionLabel);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Getting size of '%s' (v: %s), as blob '%s'", fileName,
					versionLabel, blobName));
		}

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

		Objects.requireNonNull(fileName, "'fileName' cannot be null");

		// the blobs holding the versions of the file have paths like
		// '${companyId}/${repositoryId}/<path ~ fileName>/<version>',

		String blobsPrefix = _liferayToAzurePathsMapper.toAzureBlobsPrefix(
			companyId, repositoryId, fileName);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Listing versions of '%s', as non-directory blob items with prefix '%s'",
					fileName, blobsPrefix));
		}

		ListBlobsOptions opts = new ListBlobsOptions().setPrefix(blobsPrefix);

		PagedIterable<BlobItem> blobItems =
			_blobContainerClient.listBlobsByHierarchy(
				LiferayToAzurePathsMapper.PATH_DELIMITER, opts, null);

		// fetch all blobs under given directory; only take non-directories; drop the shared prefix

		// IsPrefix seems to be always <null> for blobs (non-directories returned), see:
		// https://stackoverflow.com/questions/64791712/azure-java-sdk-blobitem-isprefix-is-null-when-calling-listblobsbyhierarchy/66750644#66750644

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

		Objects.requireNonNull(fileName, "'fileName' cannot be null");

		// version may be null, meaning the first one should be returned...

		if (Validator.isNull(versionLabel)) {
			String blobsPrefix = _liferayToAzurePathsMapper.toAzureBlobsPrefix(
				companyId, repositoryId, fileName);

			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Checking existence of '%s' (v: %s) -- no version provided, " +
							"so listing as blobs with prefix '%s' to find any version",
						fileName, versionLabel, blobsPrefix));
			}

			String[] versions = getFileVersions(
				companyId, repositoryId, fileName);

			if ((versions != null) && (versions.length > 0)) {
				return true;
			}

			return false;
		}

		String blobName = _liferayToAzurePathsMapper.toAzureBlobName(
			companyId, repositoryId, fileName, versionLabel);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Checking existence of '%s' (v: %s), as blob '%s'",
					fileName, versionLabel, blobName));
		}

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

			// set what gets logged

			bccBuilder.httpLogOptions(
				BlobServiceClientBuilder.getDefaultHttpLogOptions(
				).setLogLevel(
					HttpLogDetailLevel.BODY_AND_HEADERS
				));

			//  configure fallback logger, in case no slf4j binding is found

			bccBuilder.configuration(
				Configuration.getGlobalConfiguration(
				).put(
					Configuration.PROPERTY_AZURE_LOG_LEVEL,
					String.valueOf(LogLevel.VERBOSE.getLogLevel())
				));
		}
		else {

			// set that nothing gets logged + disable fallback logger

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

		// enable encryption for individual blobs (uploads)

		if (Validator.isNotNull(_configuration.encryptionScope())) {
			bccBuilder.encryptionScope(_configuration.encryptionScope());
		}

		_blobContainerClient = bccBuilder.buildClient();

		// just verify container exists, do not try to create it;

		if (!_blobContainerClient.exists()) {
			throw new SystemException(
				String.format(
					"Azure store was configured to store files in container '%s' (as blobs), " +
						"but it does not exist. Please make sure the container exists and the used " +
							"credentials are sufficient to access it.",
					_blobContainerClient.getBlobContainerName()));
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Azure Blob Storage store activated, files will be stored in '%s' / '%s'.",
					_blobContainerClient.getAccountName(),
					_blobContainerClient.getBlobContainerName()));
		}
	}

	@Deactivate
	protected void deactivate() {
		_blobContainerClient = null;

		if (_log.isInfoEnabled()) {
			_log.info("Azure Blob Storage store deactivated");
		}
	}

	/**
	 * Returns the first version of given file as present in Azure, or throws
	 * <code>{@link NoSuchFileException}</code>.
	 * @param companyId
	 * @param repositoryId
	 * @param fileName
	 * @return
	 * @throws NoSuchFileException
	 */
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

	private static volatile AzureBlobStorageStoreConfiguration _configuration;

	private BlobContainerClient _blobContainerClient;

	// Use the full mapper by default, similar to how S3Store does it

	@Reference(
		target = "(" + LiferayToAzurePathsMapper.IMPL_TYPE_OSGI_PROPERTY + "=" + FullPathsMapper.IMPL_TYPE + ")"
	)
	private LiferayToAzurePathsMapper _liferayToAzurePathsMapper;

}