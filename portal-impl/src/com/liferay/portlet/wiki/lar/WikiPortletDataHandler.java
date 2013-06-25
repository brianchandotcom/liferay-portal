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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiPermission;
import com.liferay.portlet.wiki.service.persistence.WikiNodeExportActionableDynamicQuery;
import com.liferay.portlet.wiki.service.persistence.WikiPageExportActionableDynamicQuery;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Zsolt Berentey
 * @author Mate Thurzo
 */
public class WikiPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "wiki";

	public WikiPortletDataHandler() {
		setDataPortletPreferences("hiddenNodes, visibleNodes");
		setDeletionSystemEventClassNames(
			WikiNode.class.getName(), WikiPage.class.getName());
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "wiki-pages", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "referenced-content")
				},
				WikiPage.class.getName()));
		setImportControls(getExportControls());
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

		portletDataContext.addPermissions(
			WikiPermission.RESOURCE_NAME, portletDataContext.getScopeGroupId());

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery nodeActionableDynamicQuery =
			new WikiNodeExportActionableDynamicQuery(portletDataContext);

		nodeActionableDynamicQuery.performActions();

		ActionableDynamicQuery pageActionableDynamicQuery =
			getPageActionableDynamicQuery(portletDataContext);

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

		portletDataContext.importPermissions(
			WikiPermission.RESOURCE_NAME, portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

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
			PortletDataContext portletDataContext)
		throws Exception {

		ActionableDynamicQuery nodeActionableDynamicQuery =
			new WikiNodeExportActionableDynamicQuery(portletDataContext);

		nodeActionableDynamicQuery.performCount();

		ActionableDynamicQuery pageExportActionableDynamicQuery =
			getPageActionableDynamicQuery(portletDataContext);

		pageExportActionableDynamicQuery.performCount();
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, Element rootElement)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		String hiddenNodeNames = portletPreferences.getValue(
			"hiddenNodes", null);

		rootElement.addAttribute("hidden-node-names", hiddenNodeNames);

		for (String hiddenNodeName : StringUtil.split(hiddenNodeNames)) {
			WikiNode wikiNode =
				WikiNodeLocalServiceUtil.getNode(
					portletDataContext.getScopeGroupId(), hiddenNodeName);

			portletDataContext.addReferenceElement(
				portlet, rootElement, wikiNode, WikiNode.class,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
				!portletDataContext.getBooleanParameter(
					NAMESPACE, "wiki-pages"));
		}

		String visibleNodeNames = portletPreferences.getValue(
			"visibleNodes", null);

		rootElement.addAttribute("visible-node-names", visibleNodeNames);

		for (String visibleNodeName : StringUtil.split(visibleNodeNames)) {
			WikiNode wikiNode =
				WikiNodeLocalServiceUtil.getNode(
					portletDataContext.getScopeGroupId(), visibleNodeName);

			portletDataContext.addReferenceElement(
				portlet, rootElement, wikiNode, WikiNode.class,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
				!portletDataContext.getBooleanParameter(
					NAMESPACE, "wiki-pages"));
		}

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = portletDataContext.getImportDataRootElement();

		String hiddenNodeNames = rootElement.attributeValue(
			"hidden-node-names");

		if (Validator.isNotNull(hiddenNodeNames)) {
			portletPreferences.setValue("hiddenNodes", hiddenNodeNames);
		}

		String visibleNodeNames = rootElement.attributeValue(
			"visible-node-names");

		if (Validator.isNotNull(visibleNodeNames)) {
			portletPreferences.setValue("visibleNodes", visibleNodeNames);
		}

		return portletPreferences;
	}

	protected ActionableDynamicQuery getPageActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws SystemException {

		return new WikiPageExportActionableDynamicQuery(portletDataContext) {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				super.addCriteria(dynamicQuery);

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.eq(WorkflowConstants.STATUS_APPROVED));
			}

		};
	}

}