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

package com.liferay.wiki.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;
import com.liferay.wiki.exportimport.content.processor.WikiPageExportImportContentProcessor;
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
@Component(immediate = true, service = StagedModelDataHandler.class)
public class WikiPageStagedModelDataHandler
	extends BaseStagedModelDataHandler<WikiPage> {

	public static final String[] CLASS_NAMES = {WikiPage.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		WikiPageResource pageResource =
			_wikiPageResourceLocalService.fetchWikiPageResourceByUuidAndGroupId(
				uuid, groupId);

		if (pageResource == null) {
			return;
		}

		WikiPage latestPage = _wikiPageLocalService.getLatestPage(
			pageResource.getResourcePrimKey(), WorkflowConstants.STATUS_ANY,
			true);

		deleteStagedModel(latestPage);
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
			new StagedModelModifiedDateComparator<WikiPage>());
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
						WikiPortletDataHandler.NAMESPACE, "referenced-content"),
					true);

		page.setContent(content);

		if (page.isHead()) {
			for (FileEntry fileEntry : page.getAttachmentsFileEntries()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, page, fileEntry,
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
			long pageId)
		throws Exception {

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
			importedPage = _wikiPageLocalService.addPage(
				userId, nodeId, page.getTitle(), page.getVersion(),
				page.getContent(), page.getSummary(), page.isMinorEdit(),
				page.getFormat(), page.getHead(), page.getParentTitle(),
				page.getRedirectTitle(), serviceContext);

			WikiPageResource pageResource =
				_wikiPageResourceLocalService.getPageResource(
					importedPage.getResourcePrimKey());

			String pageResourceUuid = GetterUtil.getString(
				pageElement.attributeValue("page-resource-uuid"));

			if (Validator.isNotNull(pageResourceUuid)) {
				pageResource.setUuid(
					pageElement.attributeValue("page-resource-uuid"));

				_wikiPageResourceLocalService.updateWikiPageResource(
					pageResource);
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
				importedPage = existingPage;
			}
		}

		if (page.isHead()) {
			List<Element> attachmentElements =
				portletDataContext.getReferenceDataElements(
					pageElement, DLFileEntry.class,
					PortletDataContext.REFERENCE_TYPE_WEAK);

			for (Element attachmentElement : attachmentElements) {
				String path = attachmentElement.attributeValue("path");

				FileEntry fileEntry =
					(FileEntry)portletDataContext.getZipEntryAsObject(path);

				InputStream inputStream = null;

				try {
					String binPath = attachmentElement.attributeValue(
						"bin-path");

					if (Validator.isNull(binPath) &&
						portletDataContext.isPerformDirectBinaryImport()) {

						try {
							inputStream = FileEntryUtil.getContentStream(
								fileEntry);
						}
						catch (NoSuchFileException nsfe) {
						}
					}
					else {
						inputStream =
							portletDataContext.getZipEntryAsInputStream(
								binPath);
					}

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
				finally {
					StreamUtil.cleanUp(inputStream);
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

		long userId = portletDataContext.getUserId(page.getUserUuid());

		WikiPage existingPage = fetchStagedModelByUuidAndGroupId(
			page.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingPage == null) || !existingPage.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingPage.getTrashHandler();

		if (trashHandler.isRestorable(existingPage.getResourcePrimKey())) {
			trashHandler.restoreTrashEntry(
				userId, existingPage.getResourcePrimKey());
		}
	}

	@Reference(unbind = "-")
	protected void setWikiPageExportImportContentProcessor(
		WikiPageExportImportContentProcessor
			wikiPageExportImportContentProcessor) {

		_wikiPageExportImportContentProcessor =
			wikiPageExportImportContentProcessor;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageResourceLocalService(
		WikiPageResourceLocalService wikiPageResourceLocalService) {

		_wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageStagedModelDataHandler.class);

	private volatile WikiPageExportImportContentProcessor
		_wikiPageExportImportContentProcessor;
	private volatile WikiPageLocalService _wikiPageLocalService;
	private volatile WikiPageResourceLocalService _wikiPageResourceLocalService;

}