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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerControl;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.portlet.exportimport.xstream.XStreamAliasRegistryUtil;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.impl.WikiNodeImpl;
import com.liferay.wiki.model.impl.WikiPageImpl;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiResourcePermissionChecker;
import com.liferay.wiki.util.WikiCacheThreadLocal;
import com.liferay.wiki.util.WikiCacheUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Zsolt Berentey
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN
	},
	service = PortletDataHandler.class
)
public class WikiPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "wiki";

	@Override
	public String getServiceName() {
		return WikiConstants.SERVICE_NAME;
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

	@Activate
	protected void activate() {
		setDataPortletPreferences("hiddenNodes, visibleNodes");
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(WikiNode.class),
			new StagedModelType(WikiPage.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "wiki-pages", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "referenced-content")
				},
				WikiPage.class.getName()));
		setImportControls(getExportControls());

		XStreamAliasRegistryUtil.register(WikiNodeImpl.class, "WikiNode");
		XStreamAliasRegistryUtil.register(WikiPageImpl.class, "WikiPage");
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
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "wiki-pages")) {
			return getExportDataRootElementString(rootElement);
		}

		portletDataContext.addPortletPermissions(
			WikiResourcePermissionChecker.RESOURCE_NAME);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery nodeActionableDynamicQuery =
			WikiNodeLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		nodeActionableDynamicQuery.performActions();

		ActionableDynamicQuery pageActionableDynamicQuery =
			WikiPageLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		pageActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "wiki-pages")) {
			return null;
		}

		portletDataContext.importPortletPermissions(
			WikiResourcePermissionChecker.RESOURCE_NAME);

		Element nodesElement = portletDataContext.getImportDataGroupElement(
			WikiNode.class);

		List<Element> nodeElements = nodesElement.elements();

		for (Element nodeElement : nodeElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, nodeElement);
		}

		Element pagesElement = portletDataContext.getImportDataGroupElement(
			WikiPage.class);

		List<Element> pageElements = pagesElement.elements();

		for (Element pageElement : pageElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, pageElement);
		}

		Map<Long, Long> nodeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				WikiNode.class);

		for (long nodeId : nodeIds.values()) {
			WikiCacheUtil.clearCache(nodeId);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery nodeActionableDynamicQuery =
			WikiNodeLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		nodeActionableDynamicQuery.performCount();

		ActionableDynamicQuery pageExportActionableDynamicQuery =
			WikiPageLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		pageExportActionableDynamicQuery.performCount();
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		String hiddenNodeNames = portletPreferences.getValue(
			"hiddenNodes", null);

		for (String hiddenNodeName : StringUtil.split(hiddenNodeNames)) {
			WikiNode wikiNode = WikiNodeLocalServiceUtil.getNode(
				portletDataContext.getScopeGroupId(), hiddenNodeName);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, wikiNode);
		}

		String visibleNodeNames = portletPreferences.getValue(
			"visibleNodes", null);

		for (String visibleNodeName : StringUtil.split(visibleNodeNames)) {
			WikiNode wikiNode = WikiNodeLocalServiceUtil.getNode(
				portletDataContext.getScopeGroupId(), visibleNodeName);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, wikiNode);
		}

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, WikiNode.class);

		return portletPreferences;
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

}