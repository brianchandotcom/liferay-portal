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

package com.liferay.document.library.internal.exportimport.data.handler;

import com.liferay.document.library.exportimport.data.handler.DLPluggableContentDataHandler;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLProcessorThreadLocal;
import com.liferay.dynamic.data.mapping.exportimport.content.processor.DDMFormValuesExportImportContentProcessor;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.verify.extender.marker.VerifyProcessCompletionMarker;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.trash.TrashHelper;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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
			return _trashHelper.getOriginalTitle(fileEntry.getTitle());
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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, DLPluggableContentDataHandler.class,
			"(model.class.name=" + FileEntry.class.getName() + ")");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
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

			long portletRepositoryClassNameId = _portal.getClassNameId(
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

		for (DLPluggableContentDataHandler dlPluggableContentDataHandler :
				_serviceTrackerList) {

			dlPluggableContentDataHandler.exportContent(
				portletDataContext, fileEntryElement, fileEntry);
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

		try {
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
				portletDataContext, fileEntryElement, fileEntry,
				serviceContext);

			FileEntry importedFileEntry = null;

			if (portletDataContext.isDataStrategyMirror()) {
				FileEntry existingFileEntry = fetchStagedModelByUuidAndGroupId(
					fileEntry.getUuid(), portletDataContext.getScopeGroupId());

				FileVersion fileVersion = fileEntry.getFileVersion();

				if (existingFileEntry == null) {
					if (portletDataContext.
							isDataStrategyMirrorWithOverwriting()) {

						FileEntry existingTitleFileEntry =
							FileEntryUtil.fetchByR_F_T(
								portletDataContext.getScopeGroupId(), folderId,
								fileEntry.getTitle());

						if (existingTitleFileEntry == null) {
							existingTitleFileEntry =
								FileEntryUtil.fetchByR_F_FN(
									portletDataContext.getScopeGroupId(),
									folderId, fileEntry.getFileName());
						}

						if (existingTitleFileEntry != null) {
							_dlAppLocalService.deleteFileEntry(
								existingTitleFileEntry.getFileEntryId());
						}
					}

					serviceContext.setAttribute(
						"fileVersionUuid", fileVersion.getUuid());
					serviceContext.setUuid(fileEntry.getUuid());

					String fileEntryTitle =
						_dlFileEntryLocalService.getUniqueTitle(
							portletDataContext.getScopeGroupId(), folderId, 0,
							fileEntry.getTitle(), fileEntry.getExtension());

					importedFileEntry = _dlAppLocalService.addFileEntry(
						userId, portletDataContext.getScopeGroupId(), folderId,
						fileEntry.getFileName(), fileEntry.getMimeType(),
						fileEntryTitle, fileEntry.getDescription(), null, is,
						fileEntry.getSize(), serviceContext);

					if (fileEntry.isInTrash()) {
						importedFileEntry =
							_dlTrashService.moveFileEntryToTrash(
								importedFileEntry.getFileEntryId());
					}
				}
				else {
					FileVersion latestExistingFileVersion =
						existingFileEntry.getLatestFileVersion(true);

					boolean indexEnabled = serviceContext.isIndexingEnabled();

					boolean deleteFileEntry = false;
					boolean updateFileEntry = false;

					if (!Objects.equals(
							fileVersion.getUuid(),
							latestExistingFileVersion.getUuid())) {

						deleteFileEntry = true;
						updateFileEntry = true;
					}
					else {
						InputStream existingFileVersionInputStream = null;

						try {
							existingFileVersionInputStream =
								latestExistingFileVersion.getContentStream(
									false);
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
									alreadyExistingFileVersion.
										getFileVersionId());
							}

							serviceContext.setUuid(fileVersion.getUuid());

							String fileEntryTitle =
								_dlFileEntryLocalService.getUniqueTitle(
									portletDataContext.getScopeGroupId(),
									existingFileEntry.getFolderId(),
									existingFileEntry.getFileEntryId(),
									fileEntry.getTitle(),
									fileEntry.getExtension());

							importedFileEntry =
								_dlAppLocalService.updateFileEntry(
									userId, existingFileEntry.getFileEntryId(),
									fileEntry.getFileName(),
									fileEntry.getMimeType(), fileEntryTitle,
									fileEntry.getDescription(), null, false, is,
									fileEntry.getSize(), serviceContext);
						}
						else {
							_dlAppLocalService.updateAsset(
								userId, existingFileEntry,
								latestExistingFileVersion, assetCategoryIds,
								assetTagNames, null);

							importedFileEntry = existingFileEntry;
						}

						if (importedFileEntry.getFolderId() != folderId) {
							importedFileEntry =
								_dlAppLocalService.moveFileEntry(
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

							String latestExistingVersion =
								latestExistingFileVersion.getVersion();

							if (!latestExistingVersion.equals(
									importedFileEntry.getVersion())) {

								_dlAppService.deleteFileVersion(
									latestExistingFileVersion.getFileEntryId(),
									latestExistingFileVersion.getVersion());
							}
						}
					}
					finally {
						serviceContext.setIndexingEnabled(indexEnabled);
					}
				}

				if (ExportImportThreadLocal.isStagingInProcess()) {
					_overrideFileVersion(
						importedFileEntry, fileVersion.getVersion());
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

			for (DLPluggableContentDataHandler dlPluggableContentDataHandler :
					_serviceTrackerList) {

				dlPluggableContentDataHandler.importContent(
					portletDataContext, fileEntryElement, fileEntry,
					importedFileEntry);
			}

			portletDataContext.importClassedModel(
				fileEntry, importedFileEntry, DLFileEntry.class);

			Map<Long, Long> fileEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					FileEntry.class);

			fileEntryIds.put(
				fileEntry.getFileEntryId(), importedFileEntry.getFileEntryId());
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

	protected void exportDDMFormValues(
			PortletDataContext portletDataContext, DDMStructure ddmStructure,
			FileEntry fileEntry, Element fileEntryElement)
		throws Exception {

		FileVersion fileVersion = fileEntry.getFileVersion();

		DLFileEntryMetadata dlFileEntryMetadata =
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				ddmStructure.getStructureId(), fileVersion.getFileVersionId());

		if (dlFileEntryMetadata == null) {
			return;
		}

		Element structureFields = fileEntryElement.addElement(
			"structure-fields");

		String ddmFormValuesPath = ExportImportPathUtil.getModelPath(
			ddmStructure,
			String.valueOf(dlFileEntryMetadata.getDDMStorageId()));

		structureFields.addAttribute("ddm-form-values-path", ddmFormValuesPath);

		structureFields.addAttribute("structureUuid", ddmStructure.getUuid());

		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues =
			_storageEngine.getDDMFormValues(
				dlFileEntryMetadata.getDDMStorageId());

		ddmFormValues =
			_ddmFormValuesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, fileEntry, ddmFormValues, false, false);

		portletDataContext.addZipEntry(
			ddmFormValuesPath,
			_ddmFormValuesJSONSerializer.serialize(ddmFormValues));
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
			exportDDMFormValues(
				portletDataContext, ddmStructure, fileEntry, fileEntryElement);
		}
	}

	protected DDMFormValues getImportDDMFormValues(
			PortletDataContext portletDataContext,
			Element structureFieldsElement, DDMStructure ddmStructure)
		throws Exception {

		String ddmFormValuesPath = structureFieldsElement.attributeValue(
			"ddm-form-values-path");

		String serializedDDMFormValues = portletDataContext.getZipEntryAsString(
			ddmFormValuesPath);

		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues =
			_ddmFormValuesJSONDeserializer.deserialize(
				DDMBeanTranslatorUtil.translate(ddmStructure.getDDMForm()),
				serializedDDMFormValues);

		ddmFormValues =
			_ddmFormValuesExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, ddmStructure, ddmFormValues);

		return DDMBeanTranslatorUtil.translate(ddmFormValues);
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

			DDMFormValues ddmFormValues = getImportDDMFormValues(
				portletDataContext, structureFieldsElement, ddmStructure);

			serviceContext.setAttribute(
				DDMFormValues.class.getName() + StringPool.POUND +
					ddmStructure.getStructureId(),
				ddmFormValues);
		}
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesExportImportContentProcessor(
		DDMFormValuesExportImportContentProcessor
			ddmFormValuesExportImportContentProcessor) {

		_ddmFormValuesExportImportContentProcessor =
			ddmFormValuesExportImportContentProcessor;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONDeserializer(
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer) {

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONSerializer(
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer) {

		_ddmFormValuesJSONSerializer = ddmFormValuesJSONSerializer;
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

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	@Reference(
		target = "(&(verify.process.name=com.liferay.document.library.service))",
		unbind = "-"
	)
	protected void setVerifyProcessCompletionMarker(
		VerifyProcessCompletionMarker verifyProcessCompletionMarker) {
	}

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortletDataException {

		if ((fileEntry.getGroupId() != portletDataContext.getGroupId()) &&
			(fileEntry.getGroupId() != portletDataContext.getScopeGroupId())) {

			PortletDataException pde = new PortletDataException(
				PortletDataException.INVALID_GROUP);

			pde.setStagedModelDisplayName(getDisplayName(fileEntry));
			pde.setStagedModelClassName(fileEntry.getModelClassName());
			pde.setStagedModelClassPK(
				GetterUtil.getString(fileEntry.getFileEntryId()));

			throw pde;
		}

		try {
			FileVersion fileVersion = fileEntry.getFileVersion();

			if (!portletDataContext.isInitialPublication() &&
				!ArrayUtil.contains(
					getExportableStatuses(), fileVersion.getStatus())) {

				PortletDataException pde = new PortletDataException(
					PortletDataException.STATUS_UNAVAILABLE);

				pde.setStagedModelDisplayName(getDisplayName(fileEntry));
				pde.setStagedModelClassName(fileVersion.getModelClassName());
				pde.setStagedModelClassPK(
					GetterUtil.getString(fileVersion.getFileVersionId()));

				throw pde;
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

	private void _overrideFileVersion(
			final FileEntry importedFileEntry, final String version)
		throws PortalException {

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						DLFileEntry dlFileEntry =
							(DLFileEntry)importedFileEntry.getModel();

						if (version.equals(dlFileEntry.getVersion())) {
							return null;
						}

						DLFileVersion dlFileVersion =
							dlFileEntry.getFileVersion();

						String oldVersion = dlFileVersion.getVersion();

						dlFileVersion.setVersion(version);

						_dlFileVersionLocalService.updateDLFileVersion(
							dlFileVersion);

						dlFileEntry.setVersion(version);

						_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

						if (DLStoreUtil.hasFile(
								dlFileEntry.getCompanyId(),
								dlFileEntry.getDataRepositoryId(),
								dlFileEntry.getName(), oldVersion)) {

							DLStoreUtil.updateFileVersion(
								dlFileEntry.getCompanyId(),
								dlFileEntry.getDataRepositoryId(),
								dlFileEntry.getName(), oldVersion, version);
						}

						return null;
					}

				});
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Throwable t) {
			throw new PortalException(t);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryStagedModelDataHandler.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	private DDMFormValuesExportImportContentProcessor
		_ddmFormValuesExportImportContentProcessor;
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;
	private DLAppLocalService _dlAppLocalService;
	private DLAppService _dlAppService;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private DLFileVersionLocalService _dlFileVersionLocalService;
	private DLTrashService _dlTrashService;

	@Reference
	private Portal _portal;

	private RepositoryLocalService _repositoryLocalService;
	private ServiceTrackerList
		<DLPluggableContentDataHandler, DLPluggableContentDataHandler>
			_serviceTrackerList;
	private StorageEngine _storageEngine;

	@Reference
	private TrashHelper _trashHelper;

}