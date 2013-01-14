/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
import com.liferay.portal.service.persistence.RepositoryUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypeUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.DLProcessorThreadLocal;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.lar.DDMPortletDataHandlerImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.util.PwdGenerator;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Sampsa Sohlman
 */
public class DLPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportFileEntry(
			PortletDataContext portletDataContext,
			Element fileEntryTypesElement, Element foldersElement,
			Element fileEntriesElement, Element fileRanksElement,
			Element repositoriesElement, Element repositoryEntriesElement,
			FileEntry fileEntry, boolean checkDateRange)
		throws Exception {

		if (checkDateRange &&
			!portletDataContext.isWithinDateRange(
				fileEntry.getModifiedDate())) {

			return;
		}

		if (!fileEntry.isDefaultRepository()) {
			Repository repository = RepositoryUtil.findByPrimaryKey(
				fileEntry.getRepositoryId());

			exportRepository(
				portletDataContext, repositoriesElement,
				repositoryEntriesElement, repository);

			return;
		}

		FileVersion fileVersion = fileEntry.getFileVersion();

		if (!fileVersion.isApproved() && !fileVersion.isInTrash()) {
			return;
		}

		String path = getFileEntryPath(portletDataContext, fileEntry);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element fileEntryElement = fileEntriesElement.addElement("file-entry");

		if (foldersElement != null) {
			exportParentFolder(
				portletDataContext, fileEntryTypesElement, foldersElement,
				repositoriesElement, repositoryEntriesElement,
				fileEntry.getFolderId());
		}

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			InputStream is = null;

			try {
				is = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (NoSuchFileException nsfe) {
			}

			if (is == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No file found for file entry " +
							fileEntry.getFileEntryId());
				}

				fileEntryElement.detach();

				return;
			}

			try {
				String binPath = getFileEntryBinPath(
					portletDataContext, fileEntry);

				portletDataContext.addZipEntry(binPath, is);

				fileEntryElement.addAttribute("bin-path", binPath);
			}
			finally {
				try {
					is.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ranks")) {
			List<DLFileRank> fileRanks = DLFileRankUtil.findByFileEntryId(
				fileEntry.getFileEntryId());

			for (DLFileRank fileRank : fileRanks) {
				exportFileRank(portletDataContext, fileRanksElement, fileRank);
			}
		}

		if (portletDataContext.getBooleanParameter(
				_NAMESPACE, "previews-and-thumbnails")) {

			DLProcessorRegistryUtil.exportGeneratedFiles(
				portletDataContext, fileEntry, fileEntryElement);
		}

		exportMetaData(
			portletDataContext, fileEntryTypesElement, fileEntryElement,
			fileEntry);

		portletDataContext.addClassedModel(
			fileEntryElement, path, fileEntry, _NAMESPACE);
	}

	public static String getFileEntryPath(
		PortletDataContext portletDataContext, FileEntry fileEntry) {

		StringBundler sb = new StringBundler(6);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/file-entries/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());
		sb.append(".xml");

		return sb.toString();
	}

	public static PortletDataHandlerControl[] getMetadataControls() {
		return _metadataControls;
	}

	public static String getRepositoryEntryPath(
		PortletDataContext portletDataContext, long repositoryEntryId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/repository-entries/");
		sb.append(repositoryEntryId);
		sb.append(".xml");

		return sb.toString();
	}

	public static void importFileEntry(
			PortletDataContext portletDataContext, Element fileEntryElement)
		throws Exception {

		String path = fileEntryElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		boolean dlProcessorEnabled = DLProcessorThreadLocal.isEnabled();

		try {
			DLProcessorThreadLocal.setEnabled(false);

			importFileEntry(portletDataContext, fileEntryElement, path);
		}
		finally {
			DLProcessorThreadLocal.setEnabled(dlProcessorEnabled);
		}
	}

	public static void importFileEntry(
			PortletDataContext portletDataContext, Element fileEntryElement,
			String path)
		throws Exception {

		FileEntry fileEntry = (FileEntry)portletDataContext.getZipEntryAsObject(
			path);

		long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long folderId = MapUtil.getLong(
			folderIds, fileEntry.getFolderId(), fileEntry.getFolderId());

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = portletDataContext.getAssetCategoryIds(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = portletDataContext.getAssetTagNames(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntryElement, fileEntry, _NAMESPACE);

		serviceContext.setAttribute(
			"sourceFileName", "A." + fileEntry.getExtension());
		serviceContext.setUserId(userId);

		String binPath = fileEntryElement.attributeValue("bin-path");

		InputStream is = null;

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			try {
				is = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (NoSuchFileException nsfe) {
			}
		}
		else {
			is = portletDataContext.getZipEntryAsInputStream(binPath);
		}

		if (is == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No file found for file entry " +
						fileEntry.getFileEntryId());
			}

			return;
		}

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == fileEntry.getFolderId())) {

			String folderPath = getImportFolderPath(
				portletDataContext, folderId);

			Folder folder = (Folder)portletDataContext.getZipEntryAsObject(
				folderPath);

			Document document = fileEntryElement.getDocument();

			Element rootElement = document.getRootElement();

			Element folderElement = (Element)rootElement.selectSingleNode(
				"//folder[@path='".concat(folderPath).concat("']"));

			importFolder(portletDataContext, folderPath, folderElement, folder);

			folderId = MapUtil.getLong(
				folderIds, fileEntry.getFolderId(), fileEntry.getFolderId());
		}

		importMetaData(portletDataContext, fileEntryElement, serviceContext);

		FileEntry importedFileEntry = null;

		String titleWithExtension = DLUtil.getTitleWithExtension(fileEntry);
		String extension = fileEntry.getExtension();

		String dotExtension = StringPool.PERIOD + extension;

		if (portletDataContext.isDataStrategyMirror()) {
			FileEntry existingFileEntry = FileEntryUtil.fetchByUUID_R(
				fileEntry.getUuid(), portletDataContext.getScopeGroupId());

			FileVersion fileVersion = fileEntry.getFileVersion();

			if (existingFileEntry == null) {
				String fileEntryTitle = fileEntry.getTitle();

				FileEntry existingTitleFileEntry = FileEntryUtil.fetchByR_F_T(
					portletDataContext.getScopeGroupId(), folderId,
					fileEntryTitle);

				if (existingTitleFileEntry != null) {
					if ((fileEntry.getGroupId() ==
							portletDataContext.getSourceGroupId()) &&
						portletDataContext.
							isDataStrategyMirrorWithOverwriting()) {

						DLAppLocalServiceUtil.deleteFileEntry(
							existingTitleFileEntry.getFileEntryId());
					}
					else {
						boolean titleHasExtension = false;

						if (fileEntryTitle.endsWith(dotExtension)) {
							fileEntryTitle = FileUtil.stripExtension(
								fileEntryTitle);

							titleHasExtension = true;
						}

						for (int i = 1;; i++) {
							fileEntryTitle += StringPool.SPACE + i;

							titleWithExtension = fileEntryTitle + dotExtension;

							existingTitleFileEntry = FileEntryUtil.fetchByR_F_T(
								portletDataContext.getScopeGroupId(), folderId,
								titleWithExtension);

							if (existingTitleFileEntry == null) {
								if (titleHasExtension) {
									fileEntryTitle += dotExtension;
								}

								break;
							}
						}
					}
				}

				serviceContext.setAttribute(
					"fileVersionUuid", fileVersion.getUuid());
				serviceContext.setUuid(fileEntry.getUuid());

				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					titleWithExtension, fileEntry.getMimeType(), fileEntryTitle,
					fileEntry.getDescription(), null, is, fileEntry.getSize(),
					serviceContext);

				if (fileVersion.isInTrash()) {
					importedFileEntry = DLAppServiceUtil.moveFileEntryToTrash(
						importedFileEntry.getFileEntryId());
				}
			}
			else {
				FileVersion latestExistingFileVersion =
					existingFileEntry.getLatestFileVersion();

				boolean indexEnabled = serviceContext.isIndexingEnabled();

				try {
					serviceContext.setIndexingEnabled(false);

					if (!fileVersion.getUuid().equals(
							latestExistingFileVersion.getUuid())) {

						DLFileVersion alreadyExistingFileVersion =
							DLFileVersionLocalServiceUtil.
								getFileVersionByUuidAndGroupId(
									fileVersion.getUuid(),
									existingFileEntry.getGroupId());

						if (alreadyExistingFileVersion != null) {
							serviceContext.setAttribute(
								"existingDLFileVersionId",
								alreadyExistingFileVersion.getFileVersionId());
						}

						serviceContext.setUuid(fileVersion.getUuid());

						importedFileEntry =
							DLAppLocalServiceUtil.updateFileEntry(
								userId, existingFileEntry.getFileEntryId(),
								fileEntry.getTitle(), fileEntry.getMimeType(),
								fileEntry.getTitle(),
								fileEntry.getDescription(), null, false, is,
								fileEntry.getSize(), serviceContext);
					}
					else {
						DLAppLocalServiceUtil.updateAsset(
							userId, existingFileEntry,
							latestExistingFileVersion, assetCategoryIds,
							assetTagNames, null);

						importedFileEntry = existingFileEntry;
					}

					if (importedFileEntry.getFolderId() != folderId) {
						importedFileEntry = DLAppLocalServiceUtil.moveFileEntry(
							userId, importedFileEntry.getFileEntryId(),
							folderId, serviceContext);
					}

					if (importedFileEntry instanceof LiferayFileEntry) {
						LiferayFileEntry liferayFileEntry =
							(LiferayFileEntry)importedFileEntry;

						Indexer indexer = IndexerRegistryUtil.getIndexer(
							DLFileEntry.class);

						indexer.reindex(liferayFileEntry.getModel());
					}
				}
				finally {
					serviceContext.setIndexingEnabled(indexEnabled);
				}
			}
		}
		else {
			try {
				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					titleWithExtension, fileEntry.getMimeType(),
					fileEntry.getTitle(), fileEntry.getDescription(), null, is,
					fileEntry.getSize(), serviceContext);
			}
			catch (DuplicateFileException dfe) {
				String title = fileEntry.getTitle();

				String[] titleParts = title.split("\\.", 2);

				title = titleParts[0] + PwdGenerator.getPassword();

				if (titleParts.length > 1) {
					title += StringPool.PERIOD + titleParts[1];
				}

				if (!title.endsWith(dotExtension)) {
					title += dotExtension;
				}

				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					title, fileEntry.getMimeType(), title,
					fileEntry.getDescription(), null, is, fileEntry.getSize(),
					serviceContext);
			}
		}

		if (portletDataContext.getBooleanParameter(
				_NAMESPACE, "previews-and-thumbnails")) {

			DLProcessorRegistryUtil.importGeneratedFiles(
				portletDataContext, fileEntry, importedFileEntry,
				fileEntryElement);
		}

		Map<String, String> fileEntryTitles =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class.getName() + ".title");

		fileEntryTitles.put(fileEntry.getTitle(), importedFileEntry.getTitle());

		portletDataContext.importClassedModel(
			fileEntry, importedFileEntry, _NAMESPACE);
	}

	public static void importFileRank(
			PortletDataContext portletDataContext, Element fileRankElement)
		throws Exception {

		String path = fileRankElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DLFileRank fileRank =
			(DLFileRank)portletDataContext.getZipEntryAsObject(path);

		String fileEntryUuid = fileRankElement.attributeValue(
			"file-entry-uuid");

		importFileRank(portletDataContext, fileRank, fileEntryUuid);
	}

	public static void importFolder(
			PortletDataContext portletDataContext, Element folderElement)
		throws Exception {

		String path = folderElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Folder folder = (Folder)portletDataContext.getZipEntryAsObject(path);

		importFolder(portletDataContext, path, folderElement, folder);
	}

	public static void importRepository(
			PortletDataContext portletDataContext, Element repositoryElement)
		throws Exception {

		String path = repositoryElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Repository repository =
			(Repository)portletDataContext.getZipEntryAsObject(
				repositoryElement, path);

		long userId = portletDataContext.getUserId(repository.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			repositoryElement, repository, _NAMESPACE);

		long importedRepositoryId = 0;

		try {
			if (portletDataContext.isDataStrategyMirror()) {
				Repository existingRepository = RepositoryUtil.fetchByUUID_G(
					repository.getUuid(), portletDataContext.getScopeGroupId());

				if (existingRepository == null) {
					existingRepository =
						RepositoryLocalServiceUtil.fetchRepository(
							portletDataContext.getScopeGroupId(),
							repository.getName());
				}

				long classNameId = 0;

				if (existingRepository != null) {
					classNameId = existingRepository.getClassNameId();
				}

				if ((existingRepository == null) ||
					(classNameId !=
						PortalUtil.getClassNameId(LiferayRepository.class))) {

					serviceContext.setUuid(repository.getUuid());

					importedRepositoryId =
						RepositoryLocalServiceUtil.addRepository(
							userId, portletDataContext.getScopeGroupId(),
							repository.getClassNameId(),
							DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
							repository.getName(), repository.getDescription(),
							repository.getPortletId(),
							repository.getTypeSettingsProperties(), false,
							serviceContext);
				}
				else {
					RepositoryLocalServiceUtil.updateRepository(
						existingRepository.getRepositoryId(),
						repository.getName(), repository.getDescription());

					importedRepositoryId = existingRepository.getRepositoryId();
				}
			}
			else {
				importedRepositoryId = RepositoryLocalServiceUtil.addRepository(
					userId, portletDataContext.getScopeGroupId(),
					repository.getClassNameId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					repository.getName(), repository.getDescription(),
					repository.getPortletId(),
					repository.getTypeSettingsProperties(), false,
					serviceContext);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to connect to repository {name=" +
						repository.getName() + ", typeSettings=" +
							repository.getTypeSettingsProperties() + "}",
					e);
			}
		}

		Repository importedRepository =
			RepositoryLocalServiceUtil.getRepository(importedRepositoryId);

		portletDataContext.importClassedModel(
			repository, importedRepository, _NAMESPACE);
	}

	public static void importRepositoryEntry(
			PortletDataContext portletDataContext,
			Element repositoryEntryElement)
		throws Exception {

		String path = repositoryEntryElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		RepositoryEntry repositoryEntry =
			(RepositoryEntry)portletDataContext.getZipEntryAsObject(path);

		Map<Long, Long> repositoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Repository.class);

		long repositoryId = MapUtil.getLong(
			repositoryIds, repositoryEntry.getRepositoryId(),
			repositoryEntry.getRepositoryId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			repositoryEntryElement, repositoryEntry, _NAMESPACE);

		RepositoryEntry importedRepositoryEntry = null;

		if (portletDataContext.isDataStrategyMirror()) {
			RepositoryEntry existingRepositoryEntry =
				RepositoryEntryUtil.fetchByUUID_G(
					repositoryEntry.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingRepositoryEntry == null) {
				serviceContext.setUuid(repositoryEntry.getUuid());

				importedRepositoryEntry =
					RepositoryEntryLocalServiceUtil.addRepositoryEntry(
						portletDataContext.getScopeGroupId(), repositoryId,
						repositoryEntry.getMappedId(), serviceContext);
			}
			else {
				importedRepositoryEntry =
					RepositoryEntryLocalServiceUtil.updateRepositoryEntry(
						existingRepositoryEntry.getRepositoryEntryId(),
						repositoryEntry.getMappedId());
			}
		}
		else {
			importedRepositoryEntry =
				RepositoryEntryLocalServiceUtil.addRepositoryEntry(
					portletDataContext.getScopeGroupId(), repositoryId,
					repositoryEntry.getMappedId(), serviceContext);
		}

		Map<Long, Long> repositoryEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				RepositoryEntry.class);

		repositoryEntryIds.put(
			repositoryEntry.getRepositoryEntryId(),
			importedRepositoryEntry.getRepositoryEntryId());

		portletDataContext.importClassedModel(
			repositoryEntry, importedRepositoryEntry, _NAMESPACE);
	}

	@Override
	public String[] getDataPortletPreferences() {
		return new String[] {"rootFolderId"};
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _previewsAndThumbnails, _ranks
		};
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "folders-and-documents", true, _metadataControls)
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _previewsAndThumbnails, _ranks
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "folders-and-documents", true, _metadataControls)
		};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isDataLocalized() {
		return _DATA_LOCALIZED;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return PropsValues.DL_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected static void exportFileEntryType(
			PortletDataContext portletDataContext,
			Element fileEntryTypesElement, DLFileEntryType dlFileEntryType)
		throws Exception {

		String path = getFileEntryTypePath(portletDataContext, dlFileEntryType);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element fileEntryTypeElement = fileEntryTypesElement.addElement(
			"file-entry-type");

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		String[] ddmStructureUuids = new String[ddmStructures.size()];

		for (int i = 0; i < ddmStructures.size(); i++) {
			DDMStructure ddmStructure = ddmStructures.get(i);

			ddmStructureUuids[i] = ddmStructure.getUuid();

			DDMPortletDataHandlerImpl.exportStructure(
				portletDataContext, fileEntryTypeElement, ddmStructure);
		}

		fileEntryTypeElement.addAttribute(
			"structureUuids", StringUtil.merge(ddmStructureUuids));

		portletDataContext.addClassedModel(
			fileEntryTypeElement, path, dlFileEntryType, _NAMESPACE);
	}

	protected static void exportFileRank(
			PortletDataContext portletDataContext, Element fileRanksElement,
			DLFileRank fileRank)
		throws Exception {

		String path = getFileRankPath(portletDataContext, fileRank);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element fileRankElement = fileRanksElement.addElement("file-rank");

		FileEntry fileEntry = FileEntryUtil.fetchByPrimaryKey(
			fileRank.getFileEntryId());

		String fileEntryUuid = fileEntry.getUuid();

		fileRankElement.addAttribute("file-entry-uuid", fileEntryUuid);

		portletDataContext.addClassedModel(
			fileRankElement, path, fileRank, _NAMESPACE);
	}

	protected static void exportFileShortcut(
			PortletDataContext portletDataContext,
			Element fileEntryTypesElement, Element foldersElement,
			Element fileShortcutsElement, Element repositoriesElement,
			Element repositoryEntriesElement, DLFileShortcut fileShortcut)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(
				fileShortcut.getModifiedDate())) {

			return;
		}

		exportParentFolder(
			portletDataContext, fileEntryTypesElement, foldersElement,
			repositoriesElement, repositoryEntriesElement,
			fileShortcut.getFolderId());

		String path = getFileShortcutPath(portletDataContext, fileShortcut);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element fileShortcutElement = fileShortcutsElement.addElement(
				"file-shortcut");

			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileShortcut.getToFileEntryId());

			String fileEntryUuid = fileEntry.getUuid();

			fileShortcutElement.addAttribute("file-entry-uuid", fileEntryUuid);

			portletDataContext.addClassedModel(
				fileShortcutElement, path, fileShortcut, _NAMESPACE);
		}
	}

	protected static void exportFolder(
			PortletDataContext portletDataContext,
			Element fileEntryTypesElement, Element foldersElement,
			Element fileEntriesElement, Element fileShortcutsElement,
			Element fileRanksElement, Element repositoriesElement,
			Element repositoryEntriesElement, Folder folder, boolean recurse)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(folder.getLastPostDate())) {
			return;
		}

		if (folder.isMountPoint()) {
			Repository repository = RepositoryUtil.findByPrimaryKey(
				folder.getRepositoryId());

			exportRepository(
				portletDataContext, repositoriesElement,
				repositoryEntriesElement, repository);

			return;
		}
		else if (!folder.isDefaultRepository()) {

			// No need to export non-Liferay repository items since they would
			// be exported as part of repository export

			return;
		}

		exportParentFolder(
			portletDataContext, fileEntryTypesElement, foldersElement,
			repositoriesElement, repositoryEntriesElement,
			folder.getParentFolderId());

		String path = getFolderPath(portletDataContext, folder);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element folderElement = foldersElement.addElement("folder");

		exportFolderFileEntryTypes(
			portletDataContext, folder, fileEntryTypesElement, folderElement);

		portletDataContext.addClassedModel(
			folderElement, path, folder, _NAMESPACE);

		if (recurse) {
			List<Folder> folders = FolderUtil.findByR_P(
				folder.getRepositoryId(), folder.getFolderId());

			for (Folder curFolder : folders) {
				exportFolder(
					portletDataContext, fileEntryTypesElement, foldersElement,
					fileEntriesElement, fileShortcutsElement, fileRanksElement,
					repositoriesElement, repositoryEntriesElement, curFolder,
					recurse);
			}
		}

		List<FileEntry> fileEntries = FileEntryUtil.findByR_F(
			folder.getRepositoryId(), folder.getFolderId());

		for (FileEntry fileEntry : fileEntries) {
			exportFileEntry(
				portletDataContext, fileEntryTypesElement, foldersElement,
				fileEntriesElement, fileRanksElement, repositoriesElement,
				repositoryEntriesElement, fileEntry, true);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<DLFileShortcut> fileShortcuts = DLFileShortcutUtil.findByG_F_A(
				folder.getRepositoryId(), folder.getFolderId(), true);

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				exportFileShortcut(
					portletDataContext, fileEntryTypesElement, foldersElement,
					fileShortcutsElement, repositoriesElement,
					repositoryEntriesElement, fileShortcut);
			}
		}
	}

	protected static void exportFolderFileEntryTypes(
			PortletDataContext portletDataContext, Folder folder,
			Element fileEntryTypesElement, Element folderElement)
		throws Exception {

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(
				new long[] {portletDataContext.getScopeGroupId()},
				folder.getFolderId(), false);

		String[] fileEntryTypeUuids = new String[dlFileEntryTypes.size()];

		long defaultFileEntryTypeId =
			DLFileEntryTypeLocalServiceUtil.getDefaultFileEntryTypeId(
				folder.getFolderId());

		String defaultFileEntryTypeUuid = StringPool.BLANK;

		for (int i = 0; i < dlFileEntryTypes.size(); i++) {
			DLFileEntryType dlFileEntryType = dlFileEntryTypes.get(i);

			if (dlFileEntryType.getFileEntryTypeId() == 0) {
				fileEntryTypeUuids[i] = "@basic_document@";
			}
			else {
				fileEntryTypeUuids[i] = dlFileEntryType.getUuid();
			}

			if (defaultFileEntryTypeId ==
					dlFileEntryType.getFileEntryTypeId()) {

				defaultFileEntryTypeUuid = dlFileEntryType.getUuid();
			}

			if (isFileEntryTypeExportable(dlFileEntryType)) {
				exportFileEntryType(
					portletDataContext, fileEntryTypesElement, dlFileEntryType);
			}
		}

		folderElement.addAttribute(
			"fileEntryTypeUuids", StringUtil.merge(fileEntryTypeUuids));
		folderElement.addAttribute(
			"defaultFileEntryTypeUuid", defaultFileEntryTypeUuid);
	}

	protected static void exportMetaData(
			PortletDataContext portletDataContext,
			Element fileEntryTypesElement, Element fileEntryElement,
			FileEntry fileEntry)
		throws Exception {

		if (!(fileEntry instanceof LiferayFileEntry)) {
			return;
		}

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		long fileEntryTypeId = dlFileEntry.getFileEntryTypeId();

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.fetchFileEntryType(fileEntryTypeId);

		if (dlFileEntryType == null) {
			return;
		}

		fileEntryElement.addAttribute(
			"fileEntryTypeUuid", dlFileEntryType.getUuid());

		if (!isFileEntryTypeExportable(dlFileEntryType)) {
			return;
		}

		exportFileEntryType(
			portletDataContext, fileEntryTypesElement, dlFileEntryType);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			Element structureFields = fileEntryElement.addElement(
				"structure-fields");

			String path = getFileEntryFileEntryTypeStructureFieldsPath(
				portletDataContext, fileEntry, dlFileEntryType.getUuid(),
				ddmStructure.getStructureId());

			structureFields.addAttribute("path", path);

			structureFields.addAttribute(
				"structureUuid", ddmStructure.getUuid());

			FileVersion fileVersion = fileEntry.getFileVersion();

			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
					ddmStructure.getStructureId(),
					fileVersion.getFileVersionId());

			Fields fields = StorageEngineUtil.getFields(
				dlFileEntryMetadata.getDDMStorageId());

			portletDataContext.addZipEntry(path, fields);
		}
	}

	protected static void exportParentFolder(
			PortletDataContext portletDataContext,
			Element fileEntryTypesElement, Element foldersElement,
			Element repositoriesElement, Element repositoryEntriesElement,
			long folderId)
		throws Exception {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

		if (folder.isMountPoint()) {
			Repository repository = RepositoryUtil.findByPrimaryKey(
				folder.getRepositoryId());

			exportRepository(
				portletDataContext, repositoriesElement,
				repositoryEntriesElement, repository);

			return;
		}
		else if (!folder.isDefaultRepository()) {

			// No need to export non-Liferay repository items since they would
			// be exported as part of repository export

			return;
		}

		exportParentFolder(
			portletDataContext, fileEntryTypesElement, foldersElement,
			repositoriesElement, repositoryEntriesElement,
			folder.getParentFolderId());

		String path = getFolderPath(portletDataContext, folder);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			exportFolderFileEntryTypes(
				portletDataContext, folder, fileEntryTypesElement,
				folderElement);

			portletDataContext.addClassedModel(
				folderElement, path, folder, _NAMESPACE);
		}
	}

	protected static void exportRepository(
			PortletDataContext portletDataContext, Element repositoriesElement,
			Element repositoryEntriesElement, Repository repository)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(
				repository.getModifiedDate())) {

			return;
		}

		String path = getRepositoryPath(portletDataContext, repository);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element repositoryElement = repositoriesElement.addElement(
			"repository");

		portletDataContext.addClassedModel(
			repositoryElement, path, repository, _NAMESPACE);

		List<RepositoryEntry> repositoryEntries =
			RepositoryEntryUtil.findByRepositoryId(
				repository.getRepositoryId());

		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			exportRepositoryEntry(
				portletDataContext, repositoryEntriesElement, repositoryEntry);
		}
	}

	protected static void exportRepositoryEntry(
			PortletDataContext portletDataContext,
			Element repositoryEntriesElement, RepositoryEntry repositoryEntry)
		throws Exception {

		String path = getRepositoryEntryPath(
			portletDataContext, repositoryEntry);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element repositoryEntryElement = repositoryEntriesElement.addElement(
			"repository-entry");

		portletDataContext.addClassedModel(
			repositoryEntryElement, path, repositoryEntry, _NAMESPACE);
	}

	protected static String getFileEntryBinPath(
		PortletDataContext portletDataContext, FileEntry fileEntry) {

		StringBundler sb = new StringBundler(5);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/bin/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());

		return sb.toString();
	}

	protected static String getFileEntryFileEntryTypeStructureFieldsPath(
		PortletDataContext portletDataContext, FileEntry fileEntry,
		String fileEntryTypeUuid, long structureId) {

		StringBundler sb = new StringBundler(4);

		String fileEntryPath = getFileEntryPath(portletDataContext, fileEntry);

		sb.append(StringUtil.replace(fileEntryPath, ".xml", StringPool.BLANK));
		sb.append("/file-entry-type/");
		sb.append(fileEntryTypeUuid);
		sb.append("/structure-fields/");
		sb.append(structureId);
		sb.append(".xml");

		return sb.toString();
	}

	/**
	 * @see com.liferay.portal.lar.PortletImporter#getAssetCategoryName(String,
	 *      long, long, String, int)
	 * @see com.liferay.portal.lar.PortletImporter#getAssetVocabularyName(
	 *      String, long, String, int)
	 */
	protected static String getFileEntryTypeName(
			String uuid, long groupId, String name, int count)
		throws Exception {

		DLFileEntryType dlFileEntryType = DLFileEntryTypeUtil.fetchByG_N(
			groupId, name);

		if (dlFileEntryType == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) &&
			uuid.equals(dlFileEntryType.getUuid())) {

			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getFileEntryTypeName(uuid, groupId, name, ++count);
	}

	protected static String getFileEntryTypePath(
		PortletDataContext portletDataContext,
		DLFileEntryType dlFileEntryType) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/entry-types/");
		sb.append(dlFileEntryType.getFileEntryTypeId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFileRankPath(
		PortletDataContext portletDataContext, DLFileRank fileRank) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/ranks/");
		sb.append(fileRank.getFileRankId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFileShortcutPath(
		PortletDataContext portletDataContext, DLFileShortcut fileShortcut) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/shortcuts/");
		sb.append(fileShortcut.getFileShortcutId());
		sb.append(".xml");

		return sb.toString();
	}

	/**
	 * @see com.liferay.portal.lar.PortletImporter#getAssetCategoryName(String,
	 *      long, long, String, int)
	 * @see com.liferay.portal.lar.PortletImporter#getAssetVocabularyName(
	 *      String, long, String, int)
	 */
	protected static String getFolderName(
			String uuid, long groupId, long parentFolderId, String name,
			int count)
		throws Exception {

		Folder folder = FolderUtil.fetchByR_P_N(groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getFolderName(uuid, groupId, parentFolderId, name, ++count);
	}

	protected static String getFolderPath(
		PortletDataContext portletDataContext, Folder folder) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getImportFolderPath(
		PortletDataContext portletDataContext, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getSourcePortletPath(
				PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getRepositoryEntryPath(
		PortletDataContext portletDataContext,
		RepositoryEntry repositoryEntry) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/repository-entries/");
		sb.append(repositoryEntry.getRepositoryEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getRepositoryPath(
		PortletDataContext portletDataContext, Repository repository) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/repositories/");
		sb.append(repository.getRepositoryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static void importFileEntryType(
			PortletDataContext portletDataContext, Element fileEntryTypeElement)
		throws Exception {

		String path = fileEntryTypeElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DLFileEntryType dlFileEntryType =
			(DLFileEntryType)portletDataContext.getZipEntryAsObject(path);

		long userId = portletDataContext.getUserId(
			dlFileEntryType.getUserUuid());

		String name = getFileEntryTypeName(
			dlFileEntryType.getUuid(), portletDataContext.getScopeGroupId(),
			dlFileEntryType.getName(), 2);

		List<Element> structureElements = fileEntryTypeElement.elements(
			"structure");

		for (Element structureElement : structureElements) {
			DDMPortletDataHandlerImpl.importStructure(
				portletDataContext, structureElement);
		}

		Map<Long, Long> ddmStructureIdsMap =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		Collection<Long> ddmStructureIdsCollection =
			ddmStructureIdsMap.values();

		long[] ddmStructureIds = ArrayUtil.toArray(
			ddmStructureIdsCollection.toArray(
				new Long[ddmStructureIdsMap.size()]));

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			path, dlFileEntryType, _NAMESPACE);

		DLFileEntryType importedDLFileEntryType = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileEntryType existingDLFileEntryType =
				DLFileEntryTypeUtil.fetchByUUID_G(
					dlFileEntryType.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingDLFileEntryType == null) {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					portletDataContext.getCompanyId());

				existingDLFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
					dlFileEntryType.getUuid(), companyGroup.getGroupId());
			}

			if (existingDLFileEntryType == null) {
				serviceContext.setUuid(dlFileEntryType.getUuid());

				importedDLFileEntryType =
					DLFileEntryTypeLocalServiceUtil.addFileEntryType(
						userId, portletDataContext.getScopeGroupId(), name,
						dlFileEntryType.getDescription(), ddmStructureIds,
						serviceContext);
			}
			else {
				if (!isFileEntryTypeGlobal(
						portletDataContext.getCompanyId(),
						existingDLFileEntryType)) {

					DLFileEntryTypeLocalServiceUtil.updateFileEntryType(
						userId, existingDLFileEntryType.getFileEntryTypeId(),
						name, dlFileEntryType.getDescription(), ddmStructureIds,
						serviceContext);
				}

				importedDLFileEntryType = existingDLFileEntryType;
			}
		}
		else {
			importedDLFileEntryType =
				DLFileEntryTypeLocalServiceUtil.addFileEntryType(
					userId, portletDataContext.getScopeGroupId(), name,
					dlFileEntryType.getDescription(), ddmStructureIds,
					serviceContext);
		}

		if (!isFileEntryTypeGlobal(
				portletDataContext.getCompanyId(), importedDLFileEntryType)) {

			portletDataContext.importClassedModel(
				dlFileEntryType, importedDLFileEntryType, _NAMESPACE);

			String importedDLFileEntryDDMStructureKey =
				DLUtil.getDDMStructureKey(importedDLFileEntryType);

			List<DDMStructure> ddmStructures =
				importedDLFileEntryType.getDDMStructures();

			for (DDMStructure ddmStructure : ddmStructures) {
				String ddmStructureKey = ddmStructure.getStructureKey();

				if (!DLUtil.isAutoGeneratedDLFileEntryTypeDDMStructureKey(
						ddmStructureKey)) {

					continue;
				}

				if (ddmStructureKey.equals(
						importedDLFileEntryDDMStructureKey)) {
					continue;
				}

				ddmStructure.setStructureKey(
					importedDLFileEntryDDMStructureKey);

				DDMStructureLocalServiceUtil.updateDDMStructure(ddmStructure);
			}
		}
	}

	protected static void importFileRank(
			PortletDataContext portletDataContext, DLFileRank fileRank,
			String fileEntryUuid)
		throws Exception {

		long userId = portletDataContext.getUserId(fileRank.getUserUuid());

		long groupId = portletDataContext.getScopeGroupId();

		FileEntry fileEntry = FileEntryUtil.fetchByUUID_R(
			fileEntryUuid, groupId);

		if (fileEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to retrieve file " + fileEntryUuid +
						" to import file rank");
			}

			return;
		}

		long fileEntryId = fileEntry.getFileEntryId();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(fileRank.getCreateDate());

		DLAppLocalServiceUtil.updateFileRank(
			portletDataContext.getScopeGroupId(),
			portletDataContext.getCompanyId(), userId, fileEntryId,
			serviceContext);
	}

	protected static void importFileShortcut(
			PortletDataContext portletDataContext, Element fileShortcutElement)
		throws Exception {

		String path = fileShortcutElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DLFileShortcut fileShortcut =
			(DLFileShortcut)portletDataContext.getZipEntryAsObject(path);

		importFileShortcut(
			portletDataContext, fileShortcutElement, fileShortcut);
	}

	protected static void importFileShortcut(
			PortletDataContext portletDataContext, Element fileShortcutElement,
			DLFileShortcut fileShortcut)
		throws Exception {

		long userId = portletDataContext.getUserId(fileShortcut.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long folderId = MapUtil.getLong(
			folderIds, fileShortcut.getFolderId(), fileShortcut.getFolderId());

		long groupId = portletDataContext.getScopeGroupId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = FolderUtil.findByPrimaryKey(folderId);

			groupId = folder.getRepositoryId();
		}

		String fileEntryUuid = fileShortcutElement.attributeValue(
			"file-entry-uuid");

		FileEntry fileEntry = FileEntryUtil.fetchByUUID_R(
			fileEntryUuid, groupId);

		if (fileEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch file entry {uuid=" + fileEntryUuid +
						", groupId=" + groupId + "}");
			}

			return;
		}

		long fileEntryId = fileEntry.getFileEntryId();

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileShortcutElement, fileShortcut, _NAMESPACE);

		DLFileShortcut importedFileShortcut = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileShortcut existingFileShortcut =
				DLFileShortcutUtil.fetchByUUID_G(
					fileShortcut.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingFileShortcut == null) {
				serviceContext.setUuid(fileShortcut.getUuid());

				importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
					userId, groupId, folderId, fileEntryId, serviceContext);
			}
			else {
				importedFileShortcut = DLAppLocalServiceUtil.updateFileShortcut(
					userId, existingFileShortcut.getFileShortcutId(), folderId,
					fileEntryId, serviceContext);
			}
		}
		else {
			importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
				userId, groupId, folderId, fileEntryId, serviceContext);
		}

		portletDataContext.importClassedModel(
			fileShortcut, importedFileShortcut, _NAMESPACE);
	}

	protected static void importFolder(
			PortletDataContext portletDataContext, String folderPath,
			Element folderElement, Folder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folderPath, folder, _NAMESPACE);

		if ((parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(
				portletDataContext, parentFolderId);

			Folder parentFolder =
				(Folder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, path, folderElement, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderIds, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		Folder importedFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			Folder existingFolder = FolderUtil.fetchByUUID_R(
				folder.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFolder == null) {
				String name = getFolderName(
					null, portletDataContext.getScopeGroupId(), parentFolderId,
					folder.getName(), 2);

				serviceContext.setUuid(folder.getUuid());

				importedFolder = DLAppLocalServiceUtil.addFolder(
					userId, portletDataContext.getScopeGroupId(),
					parentFolderId, name, folder.getDescription(),
					serviceContext);
			}
			else {
				String name = getFolderName(
					folder.getUuid(), portletDataContext.getScopeGroupId(),
					parentFolderId, folder.getName(), 2);

				importedFolder = DLAppLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId, name,
					folder.getDescription(), serviceContext);
			}
		}
		else {
			String name = getFolderName(
				null, portletDataContext.getScopeGroupId(), parentFolderId,
				folder.getName(), 2);

			importedFolder = DLAppLocalServiceUtil.addFolder(
				userId, portletDataContext.getScopeGroupId(), parentFolderId,
				name, folder.getDescription(), serviceContext);
		}

		folderIds.put(folder.getFolderId(), importedFolder.getFolderId());

		importFolderFileEntryTypes(
			portletDataContext, folderElement, importedFolder, serviceContext);

		portletDataContext.importClassedModel(
			folder, importedFolder, _NAMESPACE);
	}

	protected static void importFolderFileEntryTypes(
			PortletDataContext portletDataContext, Element folderElement,
			Folder folder, ServiceContext serviceContext)
		throws Exception {

		String[] fileEntryTypeUuids = StringUtil.split(
			folderElement.attributeValue("fileEntryTypeUuids"));

		List<Long> fileEntryTypeIds = new ArrayList<Long>();

		String defaultFileEntryTypeUuid = GetterUtil.getString(
			folderElement.attributeValue("defaultFileEntryTypeUuid"));

		long defaultFileEntryTypeId = 0;

		for (String fileEntryTypeUuid : fileEntryTypeUuids) {
			DLFileEntryType dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
				fileEntryTypeUuid, portletDataContext.getScopeGroupId());

			if (dlFileEntryType == null) {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					portletDataContext.getCompanyId());

				dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
					fileEntryTypeUuid, companyGroup.getGroupId());
			}

			if ((dlFileEntryType == null) &&
				fileEntryTypeUuid.equals("@basic_document@")) {

				dlFileEntryType =
					DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(0);
			}

			if (dlFileEntryType == null) {
				continue;
			}

			fileEntryTypeIds.add(dlFileEntryType.getFileEntryTypeId());

			if (defaultFileEntryTypeUuid.equals(dlFileEntryType.getUuid())) {
				defaultFileEntryTypeId = dlFileEntryType.getFileEntryTypeId();
			}
		}

		if (!fileEntryTypeIds.isEmpty()) {
			DLFolder dlFolder = (DLFolder)folder.getModel();

			dlFolder.setDefaultFileEntryTypeId(defaultFileEntryTypeId);
			dlFolder.setOverrideFileEntryTypes(true);

			DLFolderLocalServiceUtil.updateDLFolder(dlFolder);

			DLFileEntryTypeLocalServiceUtil.updateFolderFileEntryTypes(
				dlFolder, fileEntryTypeIds, defaultFileEntryTypeId,
				serviceContext);
		}
	}

	protected static void importMetaData(
			PortletDataContext portletDataContext, Element fileEntryElement,
			ServiceContext serviceContext)
		throws Exception {

		String fileEntryTypeUuid = fileEntryElement.attributeValue(
			"fileEntryTypeUuid");

		if (Validator.isNull(fileEntryTypeUuid)) {
			return;
		}

		DLFileEntryType dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
			fileEntryTypeUuid, portletDataContext.getScopeGroupId());

		if (dlFileEntryType == null) {
			Group group = GroupLocalServiceUtil.getCompanyGroup(
				portletDataContext.getCompanyId());

			dlFileEntryType = DLFileEntryTypeUtil.fetchByUUID_G(
				fileEntryTypeUuid, group.getGroupId());

			if (dlFileEntryType == null) {
				serviceContext.setAttribute("fileEntryTypeId", -1);

				return;
			}
		}

		serviceContext.setAttribute(
			"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			Element structureFieldsElement =
				(Element)fileEntryElement.selectSingleNode(
					"structure-fields[@structureUuid='".concat(
						ddmStructure.getUuid()).concat("']"));

			if (structureFieldsElement == null) {
				continue;
			}

			String path = structureFieldsElement.attributeValue("path");

			Fields fields = (Fields)portletDataContext.getZipEntryAsObject(
				path);

			serviceContext.setAttribute(
				Fields.class.getName() + ddmStructure.getStructureId(), fields);
		}
	}

	protected static boolean isFileEntryTypeExportable(
		DLFileEntryType dlFileEntryType) {

		if (dlFileEntryType.getFileEntryTypeId() == 0) {
			return false;
		}

		return true;
	}

	protected static boolean isFileEntryTypeGlobal(
			long companyId, DLFileEntryType dlFileEntryType)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

		if (dlFileEntryType.getGroupId() == group.getGroupId()) {
			return true;
		}

		return false;
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				DLPortletDataHandlerImpl.class, "deleteData")) {

			DLAppLocalServiceUtil.deleteAll(
				portletDataContext.getScopeGroupId());
		}

		return null;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.documentlibrary",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("documentlibrary-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			rootElement.addAttribute(
				"root-folder-id", String.valueOf(rootFolderId));
		}

		Element fileEntryTypesElement = rootElement.addElement(
			"file-entry-types");
		Element foldersElement = rootElement.addElement("folders");
		Element fileEntriesElement = rootElement.addElement("file-entries");
		Element fileShortcutsElement = rootElement.addElement("file-shortcuts");
		Element fileRanksElement = rootElement.addElement("file-ranks");
		Element repositoriesElement = rootElement.addElement("repositories");
		Element repositoryEntriesElement = rootElement.addElement(
			"repository-entries");

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			portletDataContext.getCompanyId());

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeServiceUtil.getFileEntryTypes(
				new long[] {
					portletDataContext.getScopeGroupId(),
					companyGroup.getGroupId()
				});

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			if (!isFileEntryTypeExportable(dlFileEntryType)) {
				continue;
			}

			exportFileEntryType(
				portletDataContext, fileEntryTypesElement, dlFileEntryType);
		}

		List<Folder> folders = FolderUtil.findByRepositoryId(
			portletDataContext.getScopeGroupId());

		for (Folder folder : folders) {
			exportFolder(
				portletDataContext, fileEntryTypesElement, foldersElement,
				fileEntriesElement, fileShortcutsElement, fileRanksElement,
				repositoriesElement, repositoryEntriesElement, folder, false);
		}

		List<FileEntry> fileEntries = FileEntryUtil.findByR_F(
			portletDataContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (FileEntry fileEntry : fileEntries) {
			exportFileEntry(
				portletDataContext, fileEntryTypesElement, foldersElement,
				fileEntriesElement, fileRanksElement, repositoriesElement,
				repositoryEntriesElement, fileEntry, true);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<DLFileShortcut> fileShortcuts = DLFileShortcutUtil.findByG_F_A(
				portletDataContext.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, true);

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				exportFileShortcut(
					portletDataContext, fileEntryTypesElement, foldersElement,
					fileShortcutsElement, repositoriesElement,
					repositoryEntriesElement, fileShortcut);
			}
		}

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.documentlibrary",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element repositoriesElement = rootElement.element("repositories");

		if (repositoriesElement != null) {
			List<Element> repositoryElements = repositoriesElement.elements(
				"repository");

			for (Element repositoryElement : repositoryElements) {
				importRepository(portletDataContext, repositoryElement);
			}
		}

		Element repositoryEntriesElement = rootElement.element(
			"repository-entries");

		List<Element> repositoryEntryElements =
			repositoryEntriesElement.elements("repository-entry");

		for (Element repositoryEntryElement : repositoryEntryElements) {
			importRepositoryEntry(portletDataContext, repositoryEntryElement);
		}

		Element fileEntryTypesElement = rootElement.element("file-entry-types");

		List<Element> fileEntryTypeElements = fileEntryTypesElement.elements(
			"file-entry-type");

		for (Element fileEntryTypeElement : fileEntryTypeElements) {
			importFileEntryType(portletDataContext, fileEntryTypeElement);
		}

		Element foldersElement = rootElement.element("folders");

		List<Element> folderElements = foldersElement.elements("folder");

		for (Element folderElement : folderElements) {
			importFolder(portletDataContext, folderElement);
		}

		Element fileEntriesElement = rootElement.element("file-entries");

		List<Element> fileEntryElements = fileEntriesElement.elements(
			"file-entry");

		for (Element fileEntryElement : fileEntryElements) {
			importFileEntry(portletDataContext, fileEntryElement);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<Element> fileShortcutElements = rootElement.element(
				"file-shortcuts").elements("file-shortcut");

			for (Element fileShortcutElement : fileShortcutElements) {
				importFileShortcut(portletDataContext, fileShortcutElement);
			}
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ranks")) {
			Element fileRanksElement = rootElement.element("file-ranks");

			List<Element> fileRankElements = fileRanksElement.elements(
				"file-rank");

			for (Element fileRankElement : fileRankElements) {
				importFileRank(portletDataContext, fileRankElement);
			}
		}

		long rootFolderId = GetterUtil.getLong(
			rootElement.attributeValue("root-folder-id"));

		if (rootFolderId > 0) {
			Map<Long, Long> folderIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFolder.class);

			rootFolderId = MapUtil.getLong(
				folderIds, rootFolderId, rootFolderId);

			portletPreferences.setValue(
				"rootFolderId", String.valueOf(rootFolderId));
		}

		return portletPreferences;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final boolean _DATA_LOCALIZED = true;

	private static final String _NAMESPACE = "document_library";

	private static Log _log = LogFactoryUtil.getLog(
		DLPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _foldersAndDocuments =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-documents", true, true);

	private static PortletDataHandlerControl[] _metadataControls =
		new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(_NAMESPACE, "categories"),
			new PortletDataHandlerBoolean(_NAMESPACE, "comments"),
			new PortletDataHandlerBoolean(_NAMESPACE, "ratings"),
			new PortletDataHandlerBoolean(_NAMESPACE, "tags")
		};

	private static PortletDataHandlerBoolean _previewsAndThumbnails =
		new PortletDataHandlerBoolean(_NAMESPACE, "previews-and-thumbnails");

	private static PortletDataHandlerBoolean _ranks =
		new PortletDataHandlerBoolean(_NAMESPACE, "ranks");

	private static PortletDataHandlerBoolean _shortcuts=
		new PortletDataHandlerBoolean(_NAMESPACE, "shortcuts");

}