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

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;
import com.liferay.portlet.wiki.util.WikiCacheUtil;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 */
public class WikiDisplayPortletDataHandler extends WikiPortletDataHandler {

	public WikiDisplayPortletDataHandler() {
		setDataPortletPreferences("title", "nodeId");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue("title", StringPool.BLANK);
		portletPreferences.setValue("nodeId", StringPool.BLANK);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		long nodeId = GetterUtil.getLong(
			portletPreferences.getValue("nodeId", StringPool.BLANK));

		if (nodeId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No node id found in preferences of portlet " + portletId);
			}

			return StringPool.BLANK;
		}

		String title = portletPreferences.getValue("title", null);

		if (title == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No title found in preferences of portlet " + portletId);
			}

			return StringPool.BLANK;
		}

		WikiNode node = null;

		try {
			node = WikiNodeUtil.findByPrimaryKey(nodeId);
		}
		catch (NoSuchNodeException nsne) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsne, nsne);
			}

			return StringPool.BLANK;
		}

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

		WikiPortletDataHandler.exportNode(
			portletDataContext, nodesElement, pagesElement,
			dlFileEntryTypesElement, dlFoldersElement, dlFileEntriesElement,
			dlFileRanksElement, dlRepositoriesElement,
			dlRepositoryEntriesElement, node);

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

			WikiPortletDataHandler.importNode(portletDataContext, node);
		}

		Element pagesElement = rootElement.element("pages");

		for (Element pageElement : pagesElement.elements("page")) {
			String path = pageElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			WikiPage page = (WikiPage)portletDataContext.getZipEntryAsObject(
				path);

			WikiPortletDataHandler.importPage(
				portletDataContext, pageElement, page);
		}

		Map<Long, Long> nodeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

		for (long nodeId : nodeIds.values()) {
			WikiCacheUtil.clearCache(nodeId);
		}

		long nodeId = GetterUtil.getLong(
			portletPreferences.getValue("nodeId", StringPool.BLANK));

		if (nodeId > 0) {
			nodeId = MapUtil.getLong(nodeIds, nodeId, nodeId);

			portletPreferences.setValue("nodeId", String.valueOf(nodeId));
		}

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(
		WikiDisplayPortletDataHandler.class);

}