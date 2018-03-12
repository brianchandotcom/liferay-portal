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

package com.liferay.knowledge.base.internal.exportimport.data.handler;

import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.internal.exportimport.content.processor.KBArticleExportImportContentProcessor;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.knowledge.base.util.AdminHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class KBArticleStagedModelDataHandler
	extends BaseStagedModelDataHandler<KBArticle> {

	public static final String[] CLASS_NAMES = {KBArticle.class.getName()};

	@Override
	public void deleteStagedModel(KBArticle kbArticle) throws PortalException {
		_kbArticleLocalService.deleteKBArticle(kbArticle);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		KBArticle kbArticle = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (kbArticle != null) {
			deleteStagedModel(kbArticle);
		}
	}

	@Override
	public KBArticle fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _kbArticleLocalService.fetchKBArticleByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<KBArticle> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _kbArticleLocalService.getKBArticlesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<KBArticle>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(KBArticle kbArticle) {
		return kbArticle.getTitle();
	}

	@Override
	protected boolean countStagedModel(
		PortletDataContext portletDataContext, KBArticle kbArticle) {

		return !portletDataContext.isModelCounted(
			KBArticle.class.getName(), kbArticle.getResourcePrimKey());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, KBArticle kbArticle)
		throws Exception {

		if (kbArticle.getParentResourcePrimKey() !=
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			long kbArticleClassNameId = _portal.getClassNameId(
				KBArticleConstants.getClassName());

			if (kbArticle.getParentResourceClassNameId() ==
					kbArticleClassNameId) {

				KBArticle parentKBArticle =
					_kbArticleLocalService.getLatestKBArticle(
						kbArticle.getParentResourcePrimKey(),
						WorkflowConstants.STATUS_APPROVED);

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, kbArticle, parentKBArticle,
					PortletDataContext.REFERENCE_TYPE_PARENT);
			}
			else {
				KBFolder parentKBFolder = _kbFolderLocalService.getKBFolder(
					kbArticle.getParentResourcePrimKey());

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, kbArticle, parentKBFolder,
					PortletDataContext.REFERENCE_TYPE_PARENT);
			}
		}

		Element kbArticleElement = portletDataContext.getExportDataElement(
			kbArticle);

		exportKBArticleAttachments(
			portletDataContext, kbArticleElement, kbArticle);

		String content =
			_kbArticleExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, kbArticle, kbArticle.getContent(), true,
					true);

		kbArticle.setContent(content);

		portletDataContext.addClassedModel(
			kbArticleElement, ExportImportPathUtil.getModelPath(kbArticle),
			kbArticle);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, KBArticle kbArticle)
		throws Exception {

		long userId = portletDataContext.getUserId(kbArticle.getUserUuid());

		Map<Long, Long> kbArticleResourcePrimKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				KBArticle.class);

		long resourcePrimaryKey = MapUtil.getLong(
			kbArticleResourcePrimKeys, kbArticle.getResourcePrimKey(),
			kbArticle.getResourcePrimKey());
		long parentResourcePrimKey = MapUtil.getLong(
			kbArticleResourcePrimKeys, kbArticle.getParentResourcePrimKey(),
			kbArticle.getParentResourcePrimKey());

		long kbFolderClassNameId = _portal.getClassNameId(
			KBFolderConstants.getClassName());

		if ((kbArticle.getParentResourceClassNameId() !=
				kbArticle.getClassNameId()) &&
			(kbArticle.getParentResourceClassNameId() != kbFolderClassNameId)) {

			KBArticle parentKBArticle =
				_kbArticleLocalService.fetchLatestKBArticle(
					parentResourcePrimKey, WorkflowConstants.STATUS_APPROVED);

			if (parentKBArticle != null) {
				kbArticle.setParentResourceClassNameId(
					kbArticle.getClassNameId());
			}
			else {
				kbArticle.setParentResourceClassNameId(kbFolderClassNameId);
			}
		}

		if (kbArticle.getParentResourcePrimKey() !=
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			if (kbArticle.getClassNameId() ==
					kbArticle.getParentResourceClassNameId()) {

				StagedModelDataHandlerUtil.importReferenceStagedModels(
					portletDataContext, kbArticle, KBArticle.class);
			}
			else {
				StagedModelDataHandlerUtil.importReferenceStagedModels(
					portletDataContext, kbArticle, KBFolder.class);

				Map<Long, Long> kbFolderResourcePrimKeys =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						KBFolder.class);

				parentResourcePrimKey = MapUtil.getLong(
					kbFolderResourcePrimKeys, parentResourcePrimKey,
					parentResourcePrimKey);
			}
		}

		if (parentResourcePrimKey ==
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			Map<Long, Long> kbFolderResourcePrimKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					KBFolder.class);

			parentResourcePrimKey = MapUtil.getLong(
				kbFolderResourcePrimKeys, kbArticle.getParentResourcePrimKey(),
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		String urlTitle = kbArticle.getUrlTitle();

		if (Validator.isNotNull(urlTitle) &&
			!urlTitle.startsWith(StringPool.SLASH)) {

			kbArticle.setUrlTitle(StringPool.SLASH + urlTitle);
		}

		String[] sections = _adminHelper.unescapeSections(
			kbArticle.getSections());

		String content =
			_kbArticleExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, kbArticle, kbArticle.getContent());

		kbArticle.setContent(content);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			kbArticle);

		KBArticle importedKBArticle = null;

		if (portletDataContext.isDataStrategyMirror()) {
			KBArticle existingKBArticle = _kbArticleLocalService.fetchKBArticle(
				resourcePrimaryKey, portletDataContext.getScopeGroupId(),
				kbArticle.getVersion());

			if (existingKBArticle == null) {
				existingKBArticle = fetchStagedModelByUuidAndGroupId(
					kbArticle.getUuid(), portletDataContext.getScopeGroupId());
			}

			if (existingKBArticle == null) {
				serviceContext.setUuid(kbArticle.getUuid());

				existingKBArticle = _kbArticleLocalService.fetchLatestKBArticle(
					resourcePrimaryKey, portletDataContext.getScopeGroupId());

				if (existingKBArticle == null) {
					Map<Long, Long> kbFolderIds =
						(Map<Long, Long>)portletDataContext.
							getNewPrimaryKeysMap(KBFolder.class);

					long kbFolderId = MapUtil.getLong(
						kbFolderIds, kbArticle.getKbFolderId(),
						kbArticle.getKbFolderId());

					existingKBArticle =
						_kbArticleLocalService.fetchLatestKBArticleByUrlTitle(
							portletDataContext.getScopeGroupId(), kbFolderId,
							kbArticle.getUrlTitle(),
							WorkflowConstants.STATUS_ANY);
				}

				if (existingKBArticle == null) {
					importedKBArticle = _kbArticleLocalService.addKBArticle(
						userId, kbArticle.getParentResourceClassNameId(),
						parentResourcePrimKey, kbArticle.getTitle(),
						kbArticle.getUrlTitle(), kbArticle.getContent(),
						kbArticle.getDescription(), kbArticle.getSourceURL(),
						sections, null, serviceContext);

					_kbArticleLocalService.updatePriority(
						importedKBArticle.getResourcePrimKey(),
						kbArticle.getPriority());
				}
				else {
					_kbArticleLocalService.updateKBArticle(
						userId, existingKBArticle.getResourcePrimKey(),
						kbArticle.getTitle(), kbArticle.getContent(),
						kbArticle.getDescription(), kbArticle.getSourceURL(),
						sections, null, null, serviceContext);

					_kbArticleLocalService.moveKBArticle(
						userId, existingKBArticle.getResourcePrimKey(),
						existingKBArticle.getParentResourceClassNameId(),
						parentResourcePrimKey, kbArticle.getPriority());

					importedKBArticle =
						_kbArticleLocalService.getLatestKBArticle(
							existingKBArticle.getResourcePrimKey(),
							WorkflowConstants.STATUS_APPROVED);
				}
			}
			else {
				importedKBArticle = existingKBArticle;
			}
		}
		else {
			if (resourcePrimaryKey != kbArticle.getResourcePrimKey()) {
				_kbArticleLocalService.updateKBArticle(
					userId, resourcePrimaryKey, kbArticle.getTitle(),
					kbArticle.getContent(), kbArticle.getDescription(),
					kbArticle.getSourceURL(), sections, null, null,
					serviceContext);

				_kbArticleLocalService.moveKBArticle(
					userId, resourcePrimaryKey,
					kbArticle.getParentResourceClassNameId(),
					parentResourcePrimKey, kbArticle.getPriority());

				importedKBArticle = _kbArticleLocalService.getLatestKBArticle(
					resourcePrimaryKey, WorkflowConstants.STATUS_APPROVED);
			}
			else {
				importedKBArticle = _kbArticleLocalService.addKBArticle(
					userId, kbArticle.getParentResourceClassNameId(),
					parentResourcePrimKey, kbArticle.getTitle(),
					kbArticle.getUrlTitle(), kbArticle.getContent(),
					kbArticle.getDescription(), kbArticle.getSourceURL(),
					sections, null, serviceContext);

				_kbArticleLocalService.updatePriority(
					importedKBArticle.getResourcePrimKey(),
					kbArticle.getPriority());
			}
		}

		importKBArticleAttachments(
			portletDataContext, kbArticle, importedKBArticle);

		portletDataContext.importClassedModel(kbArticle, importedKBArticle);

		if (!kbArticle.isMain()) {
			kbArticleResourcePrimKeys.put(
				kbArticle.getResourcePrimKey(),
				importedKBArticle.getResourcePrimKey());
		}
	}

	protected void exportKBArticleAttachments(
			PortletDataContext portletDataContext, Element kbArticleElement,
			KBArticle kbArticle)
		throws Exception {

		List<FileEntry> attachmentsFileEntries =
			kbArticle.getAttachmentsFileEntries();

		for (FileEntry fileEntry : attachmentsFileEntries) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, kbArticle, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}
	}

	protected void importKBArticleAttachments(
			PortletDataContext portletDataContext, KBArticle kbArticle,
			KBArticle importedKBArticle)
		throws Exception {

		List<Element> dlFileEntryElements =
			portletDataContext.getReferenceDataElements(
				kbArticle, DLFileEntry.class);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(portletDataContext.getCompanyId());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		for (Element dlFileEntryElement : dlFileEntryElements) {
			String path = dlFileEntryElement.attributeValue("path");

			FileEntry fileEntry =
				(FileEntry)portletDataContext.getZipEntryAsObject(path);

			String binPath = dlFileEntryElement.attributeValue("bin-path");

			try (InputStream inputStream = _getKBArticalAttachmentInputStream(
					binPath, portletDataContext, fileEntry)) {

				if (inputStream == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to import attachment for file entry " +
								fileEntry.getFileEntryId());
					}

					continue;
				}

				_portletFileRepository.addPortletFileEntry(
					portletDataContext.getScopeGroupId(),
					portletDataContext.getUserId(
						importedKBArticle.getUserUuid()),
					KBArticle.class.getName(), importedKBArticle.getClassPK(),
					KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
					importedKBArticle.getAttachmentsFolderId(), inputStream,
					fileEntry.getFileName(), fileEntry.getMimeType(), true);
			}
			catch (DuplicateFileEntryException dfee) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(dfee, dfee);
				}
			}
		}
	}

	@Reference(unbind = "-")
	protected void setAdminUtilHelper(AdminHelper adminHelper) {
		_adminHelper = adminHelper;
	}

	@Reference(unbind = "-")
	protected void setKBArticleLocalService(
		KBArticleLocalService kbArticleLocalService) {

		_kbArticleLocalService = kbArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setKBFolderLocalService(
		KBFolderLocalService kbFolderLocalService) {

		_kbFolderLocalService = kbFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(unbind = "-")
	protected void setPortletFileRepository(
		PortletFileRepository portletFileRepository) {

		_portletFileRepository = portletFileRepository;
	}

	private InputStream _getKBArticalAttachmentInputStream(
			String binPath, PortletDataContext portletDataContext,
			FileEntry fileEntry)
		throws Exception {

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			try {
				return FileEntryUtil.getContentStream(fileEntry);
			}
			catch (NoSuchFileException nsfe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsfe, nsfe);
				}

				return null;
			}
		}

		return portletDataContext.getZipEntryAsInputStream(binPath);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleStagedModelDataHandler.class);

	private AdminHelper _adminHelper;

	@Reference
	private KBArticleExportImportContentProcessor
		_kbArticleExportImportContentProcessor;

	private KBArticleLocalService _kbArticleLocalService;
	private KBFolderLocalService _kbFolderLocalService;
	private Portal _portal;
	private PortletFileRepository _portletFileRepository;

}