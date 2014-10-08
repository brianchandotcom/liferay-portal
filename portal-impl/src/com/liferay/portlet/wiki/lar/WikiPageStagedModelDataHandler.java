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

package com.liferay.portlet.wiki.lar;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class WikiPageStagedModelDataHandler
	extends BaseStagedModelDataHandler<WikiPage> {

	public static final String[] CLASS_NAMES = {WikiPage.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		WikiPage wikiPage = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (wikiPage != null) {
			WikiPageLocalServiceUtil.deletePage(wikiPage);
		}
	}

	@Override
	public WikiPage fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<WikiPage> pages =
			WikiPageLocalServiceUtil.getWikiPagesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<WikiPage>());

		if (ListUtil.isEmpty(pages)) {
			return null;
		}

		return pages.get(0);
	}

	@Override
	public WikiPage fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return WikiPageLocalServiceUtil.fetchWikiPageByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, WikiPage page)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, page, page.getNode(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		String content = ExportImportHelperUtil.replaceExportContentReferences(
			portletDataContext, page, page.getContent(),
			portletDataContext.getBooleanParameter(
				WikiPortletDataHandler.NAMESPACE, "referenced-content"));

		page.setContent(content);

		if (page.isHead()) {
			for (FileEntry fileEntry : page.getAttachmentsFileEntries()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, page, fileEntry,
					PortletDataContext.REFERENCE_TYPE_WEAK);
			}
		}

		Element pageElement = portletDataContext.getExportDataElement(page);

		portletDataContext.addClassedModel(
			pageElement, ExportImportPathUtil.getModelPath(page), page);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long pageId)
		throws Exception {

		WikiPage existingPage = fetchMissingReference(uuid, groupId);

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

		String content = ExportImportHelperUtil.replaceImportContentReferences(
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

		WikiPage existingPage = WikiPageLocalServiceUtil.fetchPage(
			nodeId, page.getTitle());

		if (existingPage == null) {
			importedPage = WikiPageLocalServiceUtil.addPage(
				userId, nodeId, page.getTitle(), page.getVersion(),
				page.getContent(), page.getSummary(), page.isMinorEdit(),
				page.getFormat(), page.getHead(), page.getParentTitle(),
				page.getRedirectTitle(), serviceContext);
		}
		else {
			existingPage = fetchStagedModelByUuidAndGroupId(
				page.getUuid(), portletDataContext.getScopeGroupId());

			if (existingPage == null) {
				existingPage = WikiPageLocalServiceUtil.fetchPage(
					nodeId, page.getTitle(), page.getVersion());
			}

			if (existingPage == null) {
				importedPage = WikiPageLocalServiceUtil.updatePage(
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
				String mimeType = null;

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

					mimeType = MimeTypesUtil.getContentType(
						inputStream, fileEntry.getTitle());

					WikiPageLocalServiceUtil.addPageAttachment(
						userId, importedPage.getNodeId(),
						importedPage.getTitle(), fileEntry.getTitle(),
						inputStream, mimeType);
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

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageStagedModelDataHandler.class);

}