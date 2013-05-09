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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageUtil;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;
import com.liferay.portlet.wiki.util.comparator.PageVersionComparator;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class WikiPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "wiki";

	public static void exportNode(
			PortletDataContext portletDataContext, Element nodesElement,
			Element pagesElement, Element dlFileEntryTypesElement,
			Element dlFoldersElement, Element dlFileEntriesElement,
			Element dlFileRanksElement, Element dlRepositoriesElement,
			Element dlRepositoryEntriesElement, WikiNode node)
		throws Exception {

		if (portletDataContext.isWithinDateRange(node.getModifiedDate())) {
			String path = getNodePath(portletDataContext, node);

			if (portletDataContext.isPathNotProcessed(path)) {
				Element nodeElement = nodesElement.addElement("node");

				portletDataContext.addClassedModel(
					nodeElement, path, node, NAMESPACE);
			}
		}

		List<WikiPage> pages = WikiPageUtil.findByN_S(
			node.getNodeId(), WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new PageVersionComparator(true));

		for (WikiPage page : pages) {
			exportPage(
				portletDataContext, nodesElement, pagesElement,
				dlFileEntryTypesElement, dlFoldersElement, dlFileEntriesElement,
				dlFileRanksElement, dlRepositoriesElement,
				dlRepositoryEntriesElement, page, true);
		}
	}

	public static void importNode(
			PortletDataContext portletDataContext, WikiNode node)
		throws Exception {

		long userId = portletDataContext.getUserId(node.getUserUuid());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(node.getCreateDate());
		serviceContext.setModifiedDate(node.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		WikiNode importedNode = null;

		if (portletDataContext.isDataStrategyMirror()) {
			WikiNode existingNode = WikiNodeUtil.fetchByUUID_G(
				node.getUuid(), portletDataContext.getScopeGroupId());

			String initialNodeName = PropsValues.WIKI_INITIAL_NODE_NAME;

			if ((existingNode == null) &&
				initialNodeName.equals(node.getName())) {

				try {
					WikiNodeUtil.removeByG_N(
						portletDataContext.getScopeGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			if (existingNode == null) {
				serviceContext.setUuid(node.getUuid());

				importedNode = WikiNodeLocalServiceUtil.addNode(
					userId, node.getName(), node.getDescription(),
					serviceContext);
			}
			else {
				importedNode = WikiNodeLocalServiceUtil.updateNode(
					existingNode.getNodeId(), node.getName(),
					node.getDescription(), serviceContext);
			}
		}
		else {
			String initialNodeName = PropsValues.WIKI_INITIAL_NODE_NAME;

			if (initialNodeName.equals(node.getName())) {
				try {
					WikiNodeUtil.removeByG_N(
						portletDataContext.getScopeGroupId(), node.getName());
				}
				catch (NoSuchNodeException nsne) {
				}
			}

			String nodeName = getNodeName(
				portletDataContext, node, node.getName(), 2);

			importedNode = WikiNodeLocalServiceUtil.addNode(
				userId, nodeName, node.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(node, importedNode, NAMESPACE);
	}

	public static void importPage(
			PortletDataContext portletDataContext, Element pageElement,
			WikiPage page)
		throws Exception {

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

	public WikiPortletDataHandler() {
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "wikis-and-pages", true, true));
		setExportMetadataControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "wiki-pages", true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(NAMESPACE, "attachments"),
					new PortletDataHandlerBoolean(NAMESPACE, "categories"),
					new PortletDataHandlerBoolean(NAMESPACE, "comments"),
					new PortletDataHandlerBoolean(NAMESPACE, "ratings"),
					new PortletDataHandlerBoolean(NAMESPACE, "tags")
				}));
	}

	@Override
	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		WikiCacheThreadLocal.setClearCache(false);

		try {
			return super.importData(
				portletDataContext, portletId, portletPreferences, data);
		}
		finally {
			WikiCacheThreadLocal.setClearCache(true);
		}
	}

	protected static void exportNode(
			PortletDataContext portletDataContext, Element nodesElement,
			long nodeId)
		throws Exception {

		if (!portletDataContext.hasDateRange()) {
			return;
		}

		WikiNode node = WikiNodeUtil.findByPrimaryKey(nodeId);

		String path = getNodePath(portletDataContext, node);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element nodeElement = nodesElement.addElement("node");

		portletDataContext.addClassedModel(nodeElement, path, node, NAMESPACE);
	}

	protected static void exportPage(
			PortletDataContext portletDataContext, Element nodesElement,
			Element pagesElement, Element dlFileEntryTypesElement,
			Element dlFoldersElement, Element dlFileEntriesElement,
			Element dlFileRanksElement, Element dlRepositoriesElement,
			Element dlRepositoryEntriesElement, WikiPage page,
			boolean checkDateRange)
		throws Exception {

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
				portletDataContext, page, pageElement, page.getContent());

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

	protected static String getNodeName(
			PortletDataContext portletDataContext, WikiNode node, String name,
			int count)
		throws Exception {

		WikiNode existingNode = WikiNodeUtil.fetchByG_N(
			portletDataContext.getScopeGroupId(), name);

		if (existingNode == null) {
			return name;
		}

		String nodeName = node.getName();

		return getNodeName(
			portletDataContext, node,
			nodeName.concat(StringPool.SPACE).concat(String.valueOf(count)),
			++count);
	}

	protected static String getNodePath(
		PortletDataContext portletDataContext, WikiNode node) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(
				portletDataContext, PortletKeys.WIKI));
		sb.append("/nodes/");
		sb.append(node.getNodeId());
		sb.append(".xml");

		return sb.toString();
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

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				WikiPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		WikiNodeLocalServiceUtil.deleteNodes(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.wiki", portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element nodesElement = rootElement.addElement("nodes");
		Element pagesElement = rootElement.addElement("pages");

		Element dlFileEntryTypesElement = pagesElement.addElement(
			"dl-file-entry-types");
		Element dlFoldersElement = pagesElement.addElement("dl-folders");
		Element dlFileEntriesElement = pagesElement.addElement(
			"dl-file-entries");
		Element dlFileRanksElement = pagesElement.addElement("dl-file-ranks");
		Element dlRepositoriesElement = pagesElement.addElement(
			"dl-repositories");
		Element dlRepositoryEntriesElement = pagesElement.addElement(
			"dl-repository-entries");

		List<WikiNode> nodes = WikiNodeUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (WikiNode node : nodes) {
			exportNode(
				portletDataContext, nodesElement, pagesElement,
				dlFileEntryTypesElement, dlFoldersElement, dlFileEntriesElement,
				dlFileRanksElement, dlRepositoriesElement,
				dlRepositoryEntriesElement, node);
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.wiki", portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element nodesElement = rootElement.element("nodes");

		for (Element nodeElement : nodesElement.elements("node")) {
			String path = nodeElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			WikiNode node = (WikiNode)portletDataContext.getZipEntryAsObject(
				path);

			importNode(portletDataContext, node);
		}

		Element pagesElement = rootElement.element("pages");

		for (Element pageElement : pagesElement.elements("page")) {
			String path = pageElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			WikiPage page = (WikiPage)portletDataContext.getZipEntryAsObject(
				path);

			importPage(portletDataContext, pageElement, page);
		}

		Map<Long, Long> nodeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

		for (long nodeId : nodeIds.values()) {
			WikiCacheUtil.clearCache(nodeId);
		}

		return null;
	}

}