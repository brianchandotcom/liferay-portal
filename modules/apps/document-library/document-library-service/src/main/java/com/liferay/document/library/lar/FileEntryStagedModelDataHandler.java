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

package com.liferay.document.library.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLTrashService;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.DLProcessorThreadLocal;
import com.liferay.portlet.dynamicdatamapping.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.StorageEngineManagerUtil;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.ExportImportThreadLocal;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class FileEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<FileEntry> {

	public static final String[] CLASS_NAMES = {
		DLFileEntry.class.getName(), FileEntry.class.getName(),
		LiferayFileEntry.class.getName()
	};

	@Override
	public void deleteStagedModel(FileEntry fileEntry) throws PortalException {
		_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		FileEntry fileEntry = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (fileEntry != null) {
			deleteStagedModel(fileEntry);
		}
	}

	@Override
	public FileEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		try {
			return _dlAppLocalService.getFileEntryByUuidAndGroupId(
				uuid, groupId);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	@Override
	public List<FileEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<DLFileEntry> dlFileEntries =
			_dlFileEntryLocalService.getDLFileEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DLFileEntry>());

		List<FileEntry> fileEntries = new ArrayList<>();

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			fileEntries.add(new LiferayFileEntry(dlFileEntry));
		}

		return fileEntries;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(FileEntry fileEntry) {
		if (fileEntry.isInTrash()) {
			return TrashUtil.getOriginalTitle(fileEntry.getTitle());
		}

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
	public void restoreStagedModel(
			PortletDataContext portletDataContext, FileEntry stagedModel)
		throws PortletDataException {

		try {
			doRestoreStagedModel(portletDataContext, stagedModel);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws Exception {

		Element fileEntryElement = portletDataContext.getExportDataElement(
			fileEntry);

		String fileEntryPath = ExportImportPathUtil.getModelPath(fileEntry);

		if (!fileEntry.isDefaultRepository()) {
			Repository repository = _repositoryLocalService.getRepository(
				fileEntry.getRepositoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fileEntry, repository,
				PortletDataContext.REFERENCE_TYPE_STRONG);

			portletDataContext.addClassedModel(
				fileEntryElement, fileEntryPath, fileEntry);

			long portletRepositoryClassNameId = PortalUtil.getClassNameId(
				PortletRepository.class.getName());

			if (repository.getClassNameId() != portletRepositoryClassNameId) {
				return;
			}
		}

		if (fileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fileEntry, fileEntry.getFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		liferayFileEntry.setCachedFileVersion(fileEntry.getFileVersion());

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			InputStream is = null;

			try {
				is = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to retrieve content for file entry " +
							fileEntry.getFileEntryId(),
						e);
				}
			}

			if (is == null) {
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
				_DL_PORTLET_DATA_HANDLER_NAMESPACE,
				"previews-and-thumbnails")) {

			DLProcessorRegistryUtil.exportGeneratedFiles(
				portletDataContext, fileEntry, fileEntryElement);
		}

		exportMetaData(portletDataContext, fileEntryElement, fileEntry);

		portletDataContext.addClassedModel(
			fileEntryElement, fileEntryPath, liferayFileEntry,
			DLFileEntry.class);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long fileEntryId)
		throws Exception {

		FileEntry existingFileEntry = fetchMissingReference(uuid, groupId);

		Map<Long, Long> dlFileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class);

		dlFileEntryIds.put(fileEntryId, existingFileEntry.getFileEntryId());

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		fileEntryIds.put(fileEntryId, existingFileEntry.getFileEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws Exception {

		long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

		if (!fileEntry.isDefaultRepository()) {

			// References has been automatically imported, nothing to do here

			return;
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

		Element fileEntryElement = portletDataContext.getImportDataElement(
			fileEntry);

		String binPath = fileEntryElement.attributeValue("bin-path");

		InputStream is = null;

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			try {
				is = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to retrieve content for file entry " +
							fileEntry.getFileEntryId(),
						e);
				}

				return;
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

		if (portletDataContext.isDataStrategyMirror()) {
			FileEntry existingFileEntry = fetchStagedModelByUuidAndGroupId(
				fileEntry.getUuid(), portletDataContext.getScopeGroupId());

			FileVersion fileVersion = fileEntry.getFileVersion();

			if (existingFileEntry == null) {
				if (portletDataContext.isDataStrategyMirrorWithOverwriting()) {
					FileEntry existingTitleFileEntry =
						FileEntryUtil.fetchByR_F_T(
							portletDataContext.getScopeGroupId(), folderId,
							fileEntry.getTitle());

					if (existingTitleFileEntry == null) {
						existingTitleFileEntry = FileEntryUtil.fetchByR_F_FN(
							portletDataContext.getScopeGroupId(), folderId,
							fileEntry.getFileName());
					}

					if (existingTitleFileEntry != null) {
						_dlAppLocalService.deleteFileEntry(
							existingTitleFileEntry.getFileEntryId());
					}
				}

				serviceContext.setAttribute(
					"fileVersionUuid", fileVersion.getUuid());
				serviceContext.setUuid(fileEntry.getUuid());

				String fileEntryTitle = _dlFileEntryLocalService.getUniqueTitle(
					portletDataContext.getScopeGroupId(), folderId, 0,
					fileEntry.getTitle(), fileEntry.getExtension());

				importedFileEntry = _dlAppLocalService.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					fileEntry.getFileName(), fileEntry.getMimeType(),
					fileEntryTitle, fileEntry.getDescription(), null, is,
					fileEntry.getSize(), serviceContext);

				if (fileEntry.isInTrash()) {
					importedFileEntry = _dlTrashService.moveFileEntryToTrash(
						importedFileEntry.getFileEntryId());
				}
			}
			else {
				FileVersion latestExistingFileVersion =
					existingFileEntry.getLatestFileVersion(true);

				boolean indexEnabled = serviceContext.isIndexingEnabled();

				boolean deleteFileEntry = false;
				boolean updateFileEntry = false;

				if (!Validator.equals(
						fileVersion.getUuid(),
						latestExistingFileVersion.getUuid())) {

					deleteFileEntry = true;
					updateFileEntry = true;
				}
				else {
					InputStream existingFileVersionInputStream = null;

					try {
						existingFileVersionInputStream =
							latestExistingFileVersion.getContentStream(false);
					}
					catch (Exception e) {
						if (_log.isDebugEnabled()) {
							_log.debug(e, e);
						}
					}
					finally {
						if (existingFileVersionInputStream != null) {
							existingFileVersionInputStream.close();
						}
					}

					if (existingFileVersionInputStream == null) {
						updateFileEntry = true;
					}
				}

				try {
					serviceContext.setIndexingEnabled(false);

					if (updateFileEntry) {
						DLFileVersion alreadyExistingFileVersion =
							_dlFileVersionLocalService.
								getFileVersionByUuidAndGroupId(
									fileVersion.getUuid(),
									existingFileEntry.getGroupId());

						if (alreadyExistingFileVersion != null) {
							serviceContext.setAttribute(
								"existingDLFileVersionId",
								alreadyExistingFileVersion.getFileVersionId());
						}

						serviceContext.setUuid(fileVersion.getUuid());

						String fileEntryTitle =
							_dlFileEntryLocalService.getUniqueTitle(
								portletDataContext.getScopeGroupId(),
								existingFileEntry.getFolderId(),
								existingFileEntry.getFileEntryId(),
								fileEntry.getTitle(), fileEntry.getExtension());

						importedFileEntry = _dlAppLocalService.updateFileEntry(
							userId, existingFileEntry.getFileEntryId(),
							fileEntry.getFileName(), fileEntry.getMimeType(),
							fileEntryTitle, fileEntry.getDescription(), null,
							false, is, fileEntry.getSize(), serviceContext);
					}
					else {
						_dlAppLocalService.updateAsset(
							userId, existingFileEntry,
							latestExistingFileVersion, assetCategoryIds,
							assetTagNames, null);

						importedFileEntry = existingFileEntry;
					}

					if (importedFileEntry.getFolderId() != folderId) {
						importedFileEntry = _dlAppLocalService.moveFileEntry(
							userId, importedFileEntry.getFileEntryId(),
							folderId, serviceContext);
					}

					if (importedFileEntry instanceof LiferayFileEntry) {
						LiferayFileEntry liferayFileEntry =
							(LiferayFileEntry)importedFileEntry;

						Indexer<DLFileEntry> indexer =
							IndexerRegistryUtil.nullSafeGetIndexer(
								DLFileEntry.class);

						indexer.reindex(
							(DLFileEntry)liferayFileEntry.getModel());
					}

					if (deleteFileEntry &&
						ExportImportThreadLocal.isStagingInProcess()) {

						_dlAppService.deleteFileVersion(
							latestExistingFileVersion.getFileEntryId(),
							latestExistingFileVersion.getVersion());
					}
				}
				finally {
					serviceContext.setIndexingEnabled(indexEnabled);
				}
			}
		}
		else {
			String fileEntryTitle = _dlFileEntryLocalService.getUniqueTitle(
				portletDataContext.getScopeGroupId(), folderId, 0,
				fileEntry.getTitle(), fileEntry.getExtension());

			importedFileEntry = _dlAppLocalService.addFileEntry(
				userId, portletDataContext.getScopeGroupId(), folderId,
				fileEntry.getFileName(), fileEntry.getMimeType(),
				fileEntryTitle, fileEntry.getDescription(), null, is,
				fileEntry.getSize(), serviceContext);
		}

		if (portletDataContext.getBooleanParameter(
				"document_library", "previews-and-thumbnails")) {

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

		FileEntry existingFileEntry = fetchStagedModelByUuidAndGroupId(
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
			_dlFileEntryTypeLocalService.fetchFileEntryType(fileEntryTypeId);

		if ((dlFileEntryType == null) || !dlFileEntryType.isExportable()) {
			return;
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, fileEntry, dlFileEntryType,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			FileVersion fileVersion = fileEntry.getFileVersion();

			DLFileEntryMetadata dlFileEntryMetadata =
				_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
					ddmStructure.getStructureId(),
					fileVersion.getFileVersionId());

			if (dlFileEntryMetadata == null) {
				continue;
			}

			Element structureFields = fileEntryElement.addElement(
				"structure-fields");

			String path = ExportImportPathUtil.getModelPath(
				ddmStructure,
				String.valueOf(dlFileEntryMetadata.getDDMStorageId()));

			structureFields.addAttribute("path", path);

			structureFields.addAttribute(
				"structureUuid", ddmStructure.getUuid());

			DDMFormValues ddmFormValues =
				StorageEngineManagerUtil.getDDMFormValues(
					dlFileEntryMetadata.getDDMStorageId());

			portletDataContext.addZipEntry(path, ddmFormValues);
		}
	}

	protected void importMetaData(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry, ServiceContext serviceContext)
		throws Exception {

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		Map<Long, Long> dlFileEntryTypeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntryType.class);

		long dlFileEntryTypeId = MapUtil.getLong(
			dlFileEntryTypeIds, dlFileEntry.getFileEntryTypeId(),
			dlFileEntry.getFileEntryTypeId());

		DLFileEntryType existingDLFileEntryType =
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(
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

			DDMFormValues ddmFormValues =
				(DDMFormValues)portletDataContext.getZipEntryAsObject(path);

			serviceContext.setAttribute(
				DDMFormValues.class.getName() + ddmStructure.getStructureId(),
				ddmFormValues);
		}
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryMetadataLocalService(
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService) {

		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {

		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLTrashService(DLTrashService dlTrashService) {
		_dlTrashService = dlTrashService;
	}

	@Reference(unbind = "-")
	protected void setRepositoryLocalService(
		RepositoryLocalService repositoryLocalService) {

		_repositoryLocalService = repositoryLocalService;
	}

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortletDataException {

		if ((fileEntry.getGroupId() != portletDataContext.getGroupId()) &&
			(fileEntry.getGroupId() != portletDataContext.getScopeGroupId())) {

			PortletDataException pde = new PortletDataException(
				PortletDataException.INVALID_GROUP);

			pde.setStagedModel(fileEntry);

			throw pde;
		}

		try {
			FileVersion fileVersion = fileEntry.getFileVersion();

			if (!portletDataContext.isInitialPublication() &&
				!ArrayUtil.contains(
					getExportableStatuses(), fileVersion.getStatus())) {

				throw new PortletDataException(
					PortletDataException.STATUS_UNAVAILABLE);
			}
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check workflow status for file entry " +
						fileEntry.getFileEntryId());
			}
		}

		if (fileEntry.isInTrash() || fileEntry.isInTrashContainer()) {
			PortletDataException pde = new PortletDataException(
				PortletDataException.STATUS_IN_TRASH);

			pde.setStagedModel(fileEntry);

			throw pde;
		}
	}

	/**
	 * @see com.liferay.document.library.web.lar.DLPortletDataHandler#NAMESPACE
	 */
	private static final String _DL_PORTLET_DATA_HANDLER_NAMESPACE =
		"document_library";

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryStagedModelDataHandler.class);

	private volatile DLAppLocalService _dlAppLocalService;
	private volatile DLAppService _dlAppService;
	private volatile DLFileEntryLocalService _dlFileEntryLocalService;
	private volatile DLFileEntryMetadataLocalService
		_dlFileEntryMetadataLocalService;
	private volatile DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private volatile DLFileVersionLocalService _dlFileVersionLocalService;
	private volatile DLTrashService _dlTrashService;
	private volatile RepositoryLocalService _repositoryLocalService;

}