/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.exportimport.data.handler;

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.WikiPageResourceLocalService;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zsolt Berentey
 * @author Akos Thurzo
 */
@Component(service = StagedModelDataHandler.class)
public class WikiPageStagedModelDataHandler
	extends BaseStagedModelDataHandler<WikiPage> {

	public static final String[] CLASS_NAMES = {WikiPage.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		WikiPage wikiPage = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (wikiPage != null) {
			deleteStagedModel(wikiPage);

			return;
		}

		WikiPageResource pageResource =
			_wikiPageResourceLocalService.fetchWikiPageResourceByUuidAndGroupId(
				uuid, groupId);

		if (pageResource == null) {
			return;
		}

		deleteStagedModel(
			_wikiPageLocalService.getLatestPage(
				pageResource.getResourcePrimKey(), WorkflowConstants.STATUS_ANY,
				true));
	}

	@Override
	public void deleteStagedModel(WikiPage page) throws PortalException {
		_wikiPageLocalService.deletePage(page);
	}

	@Override
	public WikiPage fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _wikiPageLocalService.fetchWikiPageByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<WikiPage> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _wikiPageLocalService.getWikiPagesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, WikiPage page)
		throws Exception {

		Element pageElement = portletDataContext.getExportDataElement(page);

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, page, page.getNode(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		String content =
			_wikiPageExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, page, page.getContent(),
					portletDataContext.getBooleanParameter(
						"wiki", "referenced-content"),
					true);

		page.setContent(content);

		if (page.isHead()) {
			for (FileEntry attachmentFileEntry :
					page.getAttachmentsFileEntries()) {

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, page, attachmentFileEntry,
					PortletDataContext.REFERENCE_TYPE_WEAK);
			}
		}

		WikiPageResource pageResource =
			_wikiPageResourceLocalService.getPageResource(
				page.getResourcePrimKey());

		pageElement.addAttribute("page-resource-uuid", pageResource.getUuid());

		portletDataContext.addClassedModel(
			pageElement, ExportImportPathUtil.getModelPath(page), page);
	}

	@Override
	protected void doImportMissingReference(
		PortletDataContext portletDataContext, String uuid, long groupId,
		long pageId) {

		WikiPage existingPage = fetchMissingReference(uuid, groupId);

		if (existingPage == null) {
			return;
		}

		Map<Long, Long> pageIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiPage.class);

		pageIds.put(pageId, existingPage.getPageId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, WikiPage page)
		throws Exception {

		long userId = portletDataContext.getUserId(page.getUserUuid());

		Element pageElement =
			portletDataContext.getImportDataStagedModelElement(page);

		String content =
			_wikiPageExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, page, page.getContent());

		page.setContent(content);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			page);

		serviceContext.setUuid(page.getUuid());

		Map<Long, Long> nodeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

		long nodeId = MapUtil.getLong(
			nodeIds, page.getNodeId(), page.getNodeId());

		WikiPage importedPage = null;

		WikiPage existingPage = _wikiPageLocalService.fetchPage(
			nodeId, page.getTitle());

		if (existingPage == null) {
			existingPage = fetchStagedModelByUuidAndGroupId(
				page.getUuid(), portletDataContext.getScopeGroupId());

			WikiPageResource importedPageResource = null;

			if (existingPage == null) {
				importedPage = _wikiPageLocalService.addPage(
					page.getExternalReferenceCode(), userId, nodeId,
					page.getTitle(), page.getVersion(), page.getContent(),
					page.getSummary(), page.isMinorEdit(), page.getFormat(),
					page.isHead(), page.getParentTitle(),
					page.getRedirectTitle(), serviceContext);

				String pageResourceUuid = GetterUtil.getString(
					pageElement.attributeValue("page-resource-uuid"));

				if (Validator.isNotNull(pageResourceUuid)) {
					WikiPageResource wikiPageResource =
						_wikiPageResourceLocalService.
							fetchWikiPageResourceByUuidAndGroupId(
								pageResourceUuid,
								portletDataContext.getScopeGroupId());

					importedPageResource =
						_wikiPageResourceLocalService.getPageResource(
							importedPage.getResourcePrimKey());

					if (wikiPageResource != null) {
						importedPage.setResourcePrimKey(
							wikiPageResource.getPrimaryKey());

						importedPage = _wikiPageLocalService.updateWikiPage(
							importedPage, serviceContext);

						_wikiPageResourceLocalService.deleteWikiPageResource(
							importedPageResource);
					}
					else {
						importedPageResource.setUuid(
							pageElement.attributeValue("page-resource-uuid"));

						importedPageResource =
							_wikiPageResourceLocalService.
								updateWikiPageResource(importedPageResource);
					}
				}
			}
			else {
				existingPage.setModifiedDate(page.getModifiedDate());
				existingPage.setTitle(page.getTitle());

				importedPage = _wikiPageLocalService.updateWikiPage(
					existingPage);

				importedPageResource =
					_wikiPageResourceLocalService.getPageResource(
						importedPage.getResourcePrimKey());

				importedPageResource.setTitle(page.getTitle());

				_wikiPageResourceLocalService.updateWikiPageResource(
					importedPageResource);
			}
		}
		else {
			existingPage = fetchStagedModelByUuidAndGroupId(
				page.getUuid(), portletDataContext.getScopeGroupId());

			if (existingPage == null) {
				existingPage = _wikiPageLocalService.fetchPage(
					nodeId, page.getTitle(), page.getVersion());
			}

			if (existingPage == null) {
				importedPage = _wikiPageLocalService.updatePage(
					userId, nodeId, page.getTitle(), 0.0, page.getContent(),
					page.getSummary(), page.isMinorEdit(), page.getFormat(),
					page.getParentTitle(), page.getRedirectTitle(),
					serviceContext);
			}
			else {
				_wikiPageLocalService.updateAsset(
					userId, existingPage, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(),
					serviceContext.getAssetLinkEntryIds(),
					serviceContext.getAssetPriority());

				importedPage = existingPage;
			}
		}

		if (page.isHead()) {
			existingPage = fetchStagedModelByUuidAndGroupId(
				page.getUuid(), portletDataContext.getScopeGroupId());

			if (existingPage != null) {
				for (FileEntry attachmentFileEntry :
						existingPage.getAttachmentsFileEntries()) {

					PortletFileRepositoryUtil.deletePortletFileEntry(
						attachmentFileEntry.getFileEntryId());
				}
			}

			List<Element> attachmentElements =
				portletDataContext.getReferenceDataElements(
					pageElement, DLFileEntry.class,
					PortletDataContext.REFERENCE_TYPE_WEAK);

			for (Element attachmentElement : attachmentElements) {
				String path = attachmentElement.attributeValue("path");

				FileEntry fileEntry =
					(FileEntry)portletDataContext.getZipEntryAsObject(path);

				String binPath = attachmentElement.attributeValue("bin-path");

				try (InputStream inputStream = _getPageAttachmentInputStream(
						binPath, portletDataContext, fileEntry)) {

					if (inputStream == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to import attachment for file entry " +
									fileEntry.getFileEntryId());
						}

						continue;
					}

					_wikiPageLocalService.addPageAttachment(
						userId, importedPage.getNodeId(),
						importedPage.getTitle(), fileEntry.getTitle(),
						inputStream, null);
				}
			}
		}

		portletDataContext.importClassedModel(page, importedPage);

		Map<Long, Long> pageIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiPage.class + ".pageId");

		pageIds.put(page.getPageId(), importedPage.getPageId());
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, WikiPage page)
		throws Exception {

		WikiPage existingPage1 = fetchStagedModelByUuidAndGroupId(
			page.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingPage1 == null) || !existingPage1.isInTrash()) {
			return;
		}

		WikiPage existingPage2 = _wikiPageLocalService.fetchPage(
			existingPage1.getNodeId(), page.getTitle());

		if (existingPage2 != null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to restore wiki page from the Recycle Bin. A ",
						"wiki page with the same title \"", page.getTitle(),
						"\" already exists."));
			}

			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			WikiPage.class.getName());

		if (trashHandler.isRestorable(existingPage1.getResourcePrimKey())) {
			trashHandler.restoreTrashEntry(
				portletDataContext.getUserId(page.getUserUuid()),
				existingPage1.getResourcePrimKey());
		}
	}

	private InputStream _getPageAttachmentInputStream(
			String binPath, PortletDataContext portletDataContext,
			FileEntry fileEntry)
		throws Exception {

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			try {
				return FileEntryUtil.getContentStream(fileEntry);
			}
			catch (NoSuchFileException noSuchFileException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(noSuchFileException);
				}

				return null;
			}
		}

		return portletDataContext.getZipEntryAsInputStream(binPath);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageStagedModelDataHandler.class);

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiPage)")
	private ExportImportContentProcessor<String>
		_wikiPageExportImportContentProcessor;

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

	@Reference
	private WikiPageResourceLocalService _wikiPageResourceLocalService;

}