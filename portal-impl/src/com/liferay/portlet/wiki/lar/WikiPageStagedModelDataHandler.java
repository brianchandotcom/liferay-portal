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

package com.liferay.portlet.wiki.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageUtil;

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
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, WikiPage page)
		throws Exception {

		Element pageElement = portletDataContext.getExportDataElement(page);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, page.getNode());

		String content = ExportImportHelperUtil.replaceExportContentReferences(
			portletDataContext, page, pageElement, page.getContent(),
			portletDataContext.getBooleanParameter(
				WikiPortletDataHandler.NAMESPACE, "referenced-content"));

		page.setContent(content);

		if (page.isHead()) {
			for (FileEntry fileEntry : page.getAttachmentsFileEntries()) {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, fileEntry);

				portletDataContext.addReferenceElement(
					page, pageElement, fileEntry, FileEntry.class,
					PortletDataContext.REFERENCE_TYPE_WEAK, false);
			}

			long folderId = page.getAttachmentsFolderId();

			if (folderId != 0) {
				page.setAttachmentsFolderId(folderId);
			}
		}

		portletDataContext.addClassedModel(
			pageElement, ExportImportPathUtil.getModelPath(page), page,
			WikiPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, WikiPage page)
		throws Exception {

		long userId = portletDataContext.getUserId(page.getUserUuid());

		String nodePath = ExportImportPathUtil.getModelPath(
			portletDataContext, WikiNode.class.getName(), page.getNodeId());

		WikiNode node = (WikiNode)portletDataContext.getZipEntryAsObject(
			nodePath);

		StagedModelDataHandlerUtil.importStagedModel(portletDataContext, node);

		Element pageElement =
			portletDataContext.getImportDataStagedModelElement(page);

		String content = ExportImportHelperUtil.replaceImportContentReferences(
			portletDataContext, pageElement, page.getContent(),
			portletDataContext.getBooleanParameter(
				WikiPortletDataHandler.NAMESPACE, "referenced-content"));

		page.setContent(content);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			page, WikiPortletDataHandler.NAMESPACE);

		Map<Long, Long> nodeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

		long nodeId = MapUtil.getLong(
			nodeIds, page.getNodeId(), page.getNodeId());

		WikiPage importedPage = null;

		WikiPage existingPage = WikiPageUtil.fetchByUUID_G(
			page.getUuid(), portletDataContext.getScopeGroupId());

		if (existingPage == null) {
			try {
				existingPage = WikiPageLocalServiceUtil.getPage(
					nodeId, page.getTitle());
			}
			catch (NoSuchPageException nspe) {
			}
		}

		if (existingPage == null) {
			serviceContext.setUuid(page.getUuid());

			importedPage = WikiPageLocalServiceUtil.addPage(
				userId, nodeId, page.getTitle(), page.getVersion(),
				page.getContent(), page.getSummary(), page.isMinorEdit(),
				page.getFormat(), page.getHead(), page.getParentTitle(),
				page.getRedirectTitle(), serviceContext);
		}
		else {
			importedPage = WikiPageLocalServiceUtil.updatePage(
				userId, nodeId, existingPage.getTitle(), 0, page.getContent(),
				page.getSummary(), page.isMinorEdit(), page.getFormat(),
				page.getParentTitle(), page.getRedirectTitle(), serviceContext);
		}

		if (page.isHead()) {
			List<Element> attachmentElements =
				portletDataContext.getReferenceDataElements(
					pageElement, FileEntry.class,
					PortletDataContext.REFERENCE_TYPE_WEAK);

			for (Element attachmentElement : attachmentElements) {
				String path = attachmentElement.attributeValue("path");
				String binPath = attachmentElement.attributeValue("bin-path");

				FileEntry fileEntry =
					(FileEntry)portletDataContext.getZipEntryAsObject(path);

				InputStream inputStream = null;
				String mimeType = null;

				try {
					inputStream = portletDataContext.getZipEntryAsInputStream(
						binPath);

					mimeType = MimeTypesUtil.getContentType(
						inputStream, fileEntry.getTitle());
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}

				try {
					inputStream = portletDataContext.getZipEntryAsInputStream(
						binPath);

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

		portletDataContext.importClassedModel(
			page, importedPage, WikiPortletDataHandler.NAMESPACE);
	}

}