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

import java.io.InputStream;
import java.util.Map;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageUtil;

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
		PortletDataContext portletDataContext, WikiPage page) throws Exception {

		if (!portletDataContext.isWithinDateRange(page.getModifiedDate())) {
			return;
		}

		String path = getPagePath(portletDataContext, page);

		// Clone this page to make sure changes to its content are never
		// persisted

		page = (WikiPage)page.clone();

		Element pageElement = (Element)pagesElement.selectSingleNode(
			"//page[@path='".concat(path).concat("']"));

		if (portletDataContext.isPathNotProcessed(path)) {
			if (pageElement == null) {
				pageElement = pagesElement.addElement("page");
			}

			String content = ExportImportUtil.exportContentReferences(
				portletDataContext, pageElement, page.getContent());

			page.setContent(content);

			String imagePath = getPageImagePath(portletDataContext, page);

			pageElement.addAttribute("image-path", imagePath);

			if (portletDataContext.getBooleanParameter(
					NAMESPACE, "attachments") &&
				page.isHead()) {

				int i = 0;

				for (FileEntry fileEntry : page.getAttachmentsFileEntries()) {
					Element attachmentElement = pageElement.addElement(
						"attachment");

					attachmentElement.addAttribute(
						"name", fileEntry.getTitle());

					String binPath = getPageAttachementBinPath(
						portletDataContext, page, i++);

					attachmentElement.addAttribute("bin-path", binPath);

					portletDataContext.addZipEntry(
						binPath, fileEntry.getContentStream());
				}

				long folderId = page.getAttachmentsFolderId();

				if (folderId != 0) {
					page.setAttachmentsFolderId(folderId);
				}
			}

			portletDataContext.addClassedModel(
				pageElement, path, page, NAMESPACE);
		}

		exportNode(portletDataContext, nodesElement, page.getNodeId());
	}

	@Override
	protected void doImportStagedModel(
		PortletDataContext portletDataContext, WikiPage page) throws Exception {

		long userId = portletDataContext.getUserId(page.getUserUuid());

		Map<Long, Long> nodeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

		long nodeId = MapUtil.getLong(
			nodeIds, page.getNodeId(), page.getNodeId());

		String content = ExportImportUtil.importContentReferences(
			portletDataContext, pageElement, page.getContent());

		page.setContent(content);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			pageElement, page, NAMESPACE);

		if (page.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

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

		if (portletDataContext.getBooleanParameter(NAMESPACE, "attachments") &&
			page.isHead()) {

			for (Element attachmentElement :
					pageElement.elements("attachment")) {

				String name = attachmentElement.attributeValue("name");
				String binPath = attachmentElement.attributeValue("bin-path");

				InputStream inputStream = null;
				String mimeType = null;

				try {
					inputStream = portletDataContext.getZipEntryAsInputStream(
						binPath);

					mimeType = MimeTypesUtil.getContentType(inputStream, name);
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}

				try {
					inputStream = portletDataContext.getZipEntryAsInputStream(
						binPath);

					WikiPageLocalServiceUtil.addPageAttachment(
						userId, importedPage.getNodeId(),
						importedPage.getTitle(), name, inputStream, mimeType);
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
		}

		portletDataContext.importClassedModel(page, importedPage, NAMESPACE);
	}

	protected static String getPageAttachementBinPath(
		PortletDataContext portletDataContext, WikiPage page, int count) {

		StringBundler sb = new StringBundler(6);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.WIKI));
		sb.append("/bin/");
		sb.append(page.getPageId());
		sb.append(StringPool.SLASH);
		sb.append("attachement");
		sb.append(count);

		return sb.toString();
	}

	protected static String getPageImagePath(
			PortletDataContext portletDataContext, WikiPage page)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.WIKI));
		sb.append("/page/");
		sb.append(page.getUuid());
		sb.append(StringPool.SLASH);
		sb.append(page.getVersion());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected static String getPagePath(
		PortletDataContext portletDataContext, WikiPage page) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.WIKI));
		sb.append("/pages/");
		sb.append(page.getPageId());
		sb.append(".xml");

		return sb.toString();
	}

}
