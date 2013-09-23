/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.DLProcessorThreadLocal;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class FileEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<FileEntry> {

	public static final String[] CLASS_NAMES = {
		DLFileEntry.class.getName(), FileEntry.class.getName(),
		LiferayFileEntry.class.getName()
	};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry =
			DLFileEntryLocalServiceUtil.fetchDLFileEntryByUuidAndGroupId(
				uuid, groupId);

		if (dlFileEntry != null) {
			DLFileEntryLocalServiceUtil.deleteFileEntry(dlFileEntry);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(FileEntry fileEntry) {
		return fileEntry.getTitle();
	}

	@Override
	public void importStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortletDataException {

		boolean dlProcessorEnabled = DLProcessorThreadLocal.isEnabled();

		try {
			DLProcessorThreadLocal.setEnabled(false);

			super.importStagedModel(portletDataContext, fileEntry);
		}
		finally {
			DLProcessorThreadLocal.setEnabled(dlProcessorEnabled);
		}
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws Exception {

		Element fileEntryElement = portletDataContext.getExportDataElement(
			fileEntry, FileEntry.class);

		String fileEntryPath = ExportImportPathUtil.getModelPath(fileEntry);

		if (!fileEntry.isDefaultRepository()) {
			Repository repository = RepositoryLocalServiceUtil.getRepository(
				fileEntry.getRepositoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fileEntry, FileEntry.class, repository,
				Repository.class, PortletDataContext.REFERENCE_TYPE_STRONG);

			portletDataContext.addClassedModel(
				fileEntryElement, fileEntryPath, fileEntry);

			long liferayRepositoryClassNameId = PortalUtil.getClassNameId(
				LiferayRepository.class.getName());

			if (repository.getClassNameId() != liferayRepositoryClassNameId) {
				return;
			}
		}

		FileVersion fileVersion = fileEntry.getFileVersion();

		if (!fileVersion.isApproved() && !fileEntry.isInTrash()) {
			return;
		}

		if (fileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fileEntry, FileEntry.class,
				fileEntry.getFolder(), Folder.class,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		liferayFileEntry.setCachedFileVersion(fileVersion);

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
				String binPath = ExportImportPathUtil.getModelPath(
					fileEntry, fileEntry.getVersion());

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

		if (portletDataContext.getBooleanParameter(
				DLPortletDataHandler.NAMESPACE, "previews-and-thumbnails")) {

			DLProcessorRegistryUtil.exportGeneratedFiles(
				portletDataContext, fileEntry, fileEntryElement);
		}

		exportMetaData(portletDataContext, fileEntryElement, fileEntry);

		portletDataContext.addClassedModel(
			fileEntryElement, fileEntryPath, liferayFileEntry,
			DLFileEntry.class);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws Exception {

		long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

		String path = ExportImportPathUtil.getModelPath(
			portletDataContext, FileEntry.class.getName(),
			fileEntry.getFileEntryId());

		Element fileEntryElement =
			portletDataContext.getImportDataElement(
				FileEntry.class.getSimpleName(), "path", path);

		Element referenceDataElement =
			portletDataContext.getReferenceDataElement(
				fileEntryElement, Repository.class,
				fileEntry.getRepositoryId());

		if (referenceDataElement != null) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, referenceDataElement);
		}

		if (fileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			String folderPath = ExportImportPathUtil.getModelPath(
				portletDataContext, Folder.class.getName(),
				fileEntry.getFolderId());

			Folder folder = (Folder)portletDataContext.getZipEntryAsObject(
				folderPath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, folder);
		}

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		long folderId = MapUtil.getLong(
			folderIds, fileEntry.getFolderId(), fileEntry.getFolderId());

		long[] assetCategoryIds = portletDataContext.getAssetCategoryIds(
			DLFileEntry.class, fileEntry.getFileEntryId());
		String[] assetTagNames = portletDataContext.getAssetTagNames(
			DLFileEntry.class, fileEntry.getFileEntryId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntry, DLFileEntry.class);

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

		importMetaData(
			portletDataContext, fileEntryElement, fileEntry, serviceContext);

		FileEntry importedFileEntry = null;

		String titleWithExtension = DLUtil.getTitleWithExtension(fileEntry);
		String extension = fileEntry.getExtension();

		String periodAndExtension = StringPool.PERIOD.concat(extension);

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

						if (fileEntryTitle.endsWith(periodAndExtension)) {
							fileEntryTitle = FileUtil.stripExtension(
								fileEntryTitle);

							titleHasExtension = true;
						}

						for (int i = 1;; i++) {
							fileEntryTitle += StringPool.SPACE + i;

							titleWithExtension =
								fileEntryTitle + periodAndExtension;

							existingTitleFileEntry = FileEntryUtil.fetchByR_F_T(
								portletDataContext.getScopeGroupId(), folderId,
								titleWithExtension);

							if (existingTitleFileEntry == null) {
								if (titleHasExtension) {
									fileEntryTitle += periodAndExtension;
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

				if (fileEntry.isInTrash()) {
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

				title = titleParts[0] + StringUtil.randomString();

				if (titleParts.length > 1) {
					title += StringPool.PERIOD + titleParts[1];
				}

				if (!title.endsWith(periodAndExtension)) {
					title += periodAndExtension;
				}

				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					title, fileEntry.getMimeType(), title,
					fileEntry.getDescription(), null, is, fileEntry.getSize(),
					serviceContext);
			}
		}

		if (portletDataContext.getBooleanParameter(
				DLPortletDataHandler.NAMESPACE, "previews-and-thumbnails")) {

			DLProcessorRegistryUtil.importGeneratedFiles(
				portletDataContext, fileEntry, importedFileEntry,
				fileEntryElement);
		}

		portletDataContext.importClassedModel(
			fileEntry, importedFileEntry, DLFileEntry.class);

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		fileEntryIds.put(
			fileEntry.getFileEntryId(), importedFileEntry.getFileEntryId());
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws Exception {

		long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

		FileEntry existingFileEntry = FileEntryUtil.fetchByUUID_R(
			fileEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFileEntry == null) || !existingFileEntry.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFileEntry.class.getName());

		if (trashHandler.isRestorable(existingFileEntry.getFileEntryId())) {
			trashHandler.restoreTrashEntry(
				userId, existingFileEntry.getFileEntryId());
		}
	}

	protected void exportMetaData(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry)
		throws Exception {

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		long fileEntryTypeId = dlFileEntry.getFileEntryTypeId();

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.fetchFileEntryType(fileEntryTypeId);

		if ((dlFileEntryType == null) || !dlFileEntryType.isExportable()) {
			return;
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, fileEntry, FileEntry.class, dlFileEntryType,
			DLFileEntryType.class, PortletDataContext.REFERENCE_TYPE_STRONG);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			Element structureFields = fileEntryElement.addElement(
				"structure-fields");

			String path = ExportImportPathUtil.getModelPath(
				ddmStructure, String.valueOf(ddmStructure.getStructureId()));

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

	protected void importMetaData(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry, ServiceContext serviceContext)
		throws Exception {

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		Element fileEntryTypeElement =
			portletDataContext.getReferenceDataElement(
				fileEntryElement, DLFileEntryType.class,
				dlFileEntry.getFileEntryTypeId());

		if (fileEntryTypeElement == null) {
			return;
		}

		String fileEntryTypePath = fileEntryTypeElement.attributeValue("path");

		DLFileEntryType dlFileEntryType =
			(DLFileEntryType)portletDataContext.getZipEntryAsObject(
				fileEntryTypePath);

		StagedModelDataHandlerUtil.importReferenceStagedModel(
			portletDataContext, dlFileEntryType);

		Map<Long, Long> dlFileEntryTypeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntryType.class);

		long dlFileEntryTypeId = MapUtil.getLong(
			dlFileEntryTypeIds, dlFileEntryType.getFileEntryTypeId(),
			dlFileEntryType.getFileEntryTypeId());

		DLFileEntryType existingDLFileEntryType =
			DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(
				dlFileEntryTypeId);

		if (existingDLFileEntryType == null) {
			serviceContext.setAttribute("fileEntryTypeId", -1);

			return;
		}

		serviceContext.setAttribute(
			"fileEntryTypeId", existingDLFileEntryType.getFileEntryTypeId());

		List<DDMStructure> ddmStructures =
			existingDLFileEntryType.getDDMStructures();

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

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		DLFileEntry dlFileEntry =
			DLFileEntryLocalServiceUtil.fetchDLFileEntryByUuidAndGroupId(
				uuid, groupId);

		if (dlFileEntry == null) {
			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		FileEntryStagedModelDataHandler.class);

}