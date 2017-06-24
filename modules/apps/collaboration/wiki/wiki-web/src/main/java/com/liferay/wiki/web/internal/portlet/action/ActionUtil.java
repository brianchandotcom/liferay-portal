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

package com.liferay.wiki.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.engine.impl.WikiEngineRenderer;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiNodeServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.util.WikiUtil;
import com.liferay.wiki.web.configuration.WikiPortletInstanceConfiguration;
import com.liferay.wiki.web.util.WikiWebComponentProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class ActionUtil {

	public static void compareVersions(
			RenderRequest renderRequest, RenderResponse renderResponse,
			WikiEngineRenderer wikiEngineRenderer)
		throws Exception {

		long nodeId = ParamUtil.getLong(renderRequest, "nodeId");
		String title = ParamUtil.getString(renderRequest, "title");
		double sourceVersion = ParamUtil.getDouble(
			renderRequest, "sourceVersion");
		double targetVersion = ParamUtil.getDouble(
			renderRequest, "targetVersion");

		String htmlDiffResult = getHtmlDiffResult(
			sourceVersion, targetVersion, renderRequest, renderResponse,
			wikiEngineRenderer);

		renderRequest.setAttribute(WebKeys.DIFF_HTML_RESULTS, htmlDiffResult);

		renderRequest.setAttribute(WebKeys.SOURCE_VERSION, sourceVersion);
		renderRequest.setAttribute(WebKeys.TARGET_VERSION, targetVersion);
		renderRequest.setAttribute(WebKeys.TITLE, title);
		renderRequest.setAttribute(WikiWebKeys.WIKI_NODE_ID, nodeId);
	}

	public static WikiNode getFirstNode(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();
		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		List<WikiNode> nodes = WikiNodeLocalServiceUtil.getNodes(groupId);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		WikiPortletInstanceConfiguration wikiPortletInstanceConfiguration =
			ConfigurationProviderUtil.getConfiguration(
				WikiPortletInstanceConfiguration.class,
				new PortletInstanceSettingsLocator(
					themeDisplay.getLayout(), portletDisplay.getId()));

		String[] visibleNodeNames =
			wikiPortletInstanceConfiguration.visibleNodes();

		nodes = WikiUtil.orderNodes(nodes, visibleNodeNames);

		String[] hiddenNodes = wikiPortletInstanceConfiguration.hiddenNodes();

		Arrays.sort(hiddenNodes);

		for (WikiNode node : nodes) {
			if ((Arrays.binarySearch(hiddenNodes, node.getName()) < 0) &&
				WikiNodePermissionChecker.contains(
					permissionChecker, node, ActionKeys.VIEW)) {

				return node;
			}
		}

		return null;
	}

	public static WikiNode getFirstVisibleNode(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		WikiNode node = null;

		int nodesCount = WikiNodeLocalServiceUtil.getNodesCount(
			themeDisplay.getScopeGroupId());

		if (nodesCount == 0) {
			Layout layout = themeDisplay.getLayout();

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				WikiNode.class.getName(), portletRequest);

			serviceContext.setAddGroupPermissions(true);

			if (layout.isPublicLayout() || layout.isTypeControlPanel()) {
				serviceContext.setAddGuestPermissions(true);
			}
			else {
				serviceContext.setAddGuestPermissions(false);
			}

			node = WikiNodeLocalServiceUtil.addDefaultNode(
				themeDisplay.getDefaultUserId(), serviceContext);
		}
		else {
			node = getFirstNode(portletRequest);

			if (node == null) {
				throw new PrincipalException();
			}

			return node;
		}

		return node;
	}

	public static WikiPage getFirstVisiblePage(
			long nodeId, PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		WikiWebComponentProvider wikiWebComponentProvider =
			WikiWebComponentProvider.getWikiWebComponentProvider();

		WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
			wikiWebComponentProvider.getWikiGroupServiceConfiguration();

		WikiPage page = WikiPageLocalServiceUtil.fetchPage(
			nodeId, wikiGroupServiceConfiguration.frontPageName(), 0);

		if (page == null) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				WikiPage.class.getName(), portletRequest);

			serviceContext.setAddGuestPermissions(true);
			serviceContext.setAddGroupPermissions(true);

			boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

			try {
				WorkflowThreadLocal.setEnabled(false);

				page = WikiPageLocalServiceUtil.addPage(
					themeDisplay.getDefaultUserId(), nodeId,
					wikiGroupServiceConfiguration.frontPageName(), null,
					WikiPageConstants.NEW, true, serviceContext);
			}
			finally {
				WorkflowThreadLocal.setEnabled(workflowEnabled);
			}
		}

		return page;
	}

	public static String getHtmlDiffResult(
			double sourceVersion, double targetVersion,
			PortletRequest portletRequest, PortletResponse portletResponse,
			WikiEngineRenderer wikiEngineRenderer)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(portletRequest, "nodeId");
		String title = ParamUtil.getString(portletRequest, "title");

		WikiPage sourcePage = WikiPageServiceUtil.getPage(
			nodeId, title, sourceVersion);
		WikiPage targetPage = WikiPageServiceUtil.getPage(
			nodeId, title, targetVersion);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		PortletURL viewPageURL = liferayPortletResponse.createRenderURL();

		viewPageURL.setParameter("mvcRenderCommandName", "wiki/view");

		WikiNode sourceNode = sourcePage.getNode();

		viewPageURL.setParameter("nodeName", sourceNode.getName());

		PortletURL editPageURL = liferayPortletResponse.createRenderURL();

		editPageURL.setParameter("mvcRenderCommandName", "wiki/edit_page");
		editPageURL.setParameter("nodeId", String.valueOf(nodeId));
		editPageURL.setParameter("title", title);

		String attachmentURLPrefix = WikiUtil.getAttachmentURLPrefix(
			themeDisplay.getPathMain(), themeDisplay.getPlid(), nodeId, title);

		return wikiEngineRenderer.diffHtml(
			sourcePage, targetPage, viewPageURL, editPageURL,
			attachmentURLPrefix);
	}

	public static WikiNode getNode(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(portletRequest, "nodeId");
		String nodeName = ParamUtil.getString(portletRequest, "nodeName");

		WikiNode node = null;

		try {
			if (nodeId > 0) {
				node = WikiNodeServiceUtil.getNode(nodeId);
			}
			else if (Validator.isNotNull(nodeName)) {
				node = WikiNodeServiceUtil.getNode(
					themeDisplay.getScopeGroupId(), nodeName);
			}
			else {
				throw new NoSuchNodeException();
			}
		}
		catch (NoSuchNodeException nsne) {
			node = getFirstVisibleNode(portletRequest);
		}

		return node;
	}

	public static List<WikiNode> getNodes(PortletRequest portletRequest)
		throws PortalException {

		long[] nodeIds = ParamUtil.getLongValues(
			portletRequest, "rowIdsWikiNode");

		if (nodeIds.length == 0) {
			return Collections.emptyList();
		}

		List<WikiNode> nodes = new ArrayList<>();

		for (long nodeId : nodeIds) {
			if (nodeId != 0) {
				nodes.add(WikiNodeServiceUtil.getNode(nodeId));
			}
		}

		return nodes;
	}

	public static WikiPage getPage(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		long nodeId = ParamUtil.getLong(request, "nodeId");
		String title = ParamUtil.getString(request, "title");
		double version = ParamUtil.getDouble(request, "version");

		WikiNode node = null;

		try {
			if (nodeId > 0) {
				node = WikiNodeServiceUtil.getNode(nodeId);
			}
		}
		catch (NoSuchNodeException nsne) {
		}

		if (node == null) {
			node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

			if (node != null) {
				nodeId = node.getNodeId();
			}
		}

		WikiWebComponentProvider wikiWebComponentProvider =
			WikiWebComponentProvider.getWikiWebComponentProvider();

		WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
			wikiWebComponentProvider.getWikiGroupServiceConfiguration();

		if (Validator.isNull(title)) {
			title = wikiGroupServiceConfiguration.frontPageName();
		}

		try {
			return WikiPageServiceUtil.getPage(nodeId, title, version);
		}
		catch (NoSuchPageException nspe) {
			if (title.equals(wikiGroupServiceConfiguration.frontPageName()) &&
				(version == 0)) {

				return getFirstVisiblePage(nodeId, portletRequest);
			}
			else {
				throw nspe;
			}
		}
	}

	public static List<WikiPage> getPages(PortletRequest portletRequest)
		throws PortalException {

		long nodeId = ParamUtil.getLong(portletRequest, "nodeId");

		String[] titles = ParamUtil.getStringValues(
			portletRequest, "rowIdsWikiPage");

		if (titles.length == 0) {
			return Collections.emptyList();
		}

		List<WikiPage> pages = new ArrayList<>();

		for (String title : titles) {
			if (Validator.isNotNull(title)) {
				pages.add(WikiPageServiceUtil.getPage(nodeId, title));
			}
		}

		return pages;
	}

	public static String viewNode(
			RenderRequest renderRequest, String defaultForward)
		throws PortletException {

		try {
			WikiNode node = getNode(renderRequest);

			renderRequest.setAttribute(WikiWebKeys.WIKI_NODE, node);

			getFirstVisiblePage(node.getNodeId(), renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/wiki/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		long categoryId = ParamUtil.getLong(renderRequest, "categoryId");
		String tag = ParamUtil.getString(renderRequest, "tag");

		if ((categoryId > 0) || Validator.isNotNull(tag)) {
			return "/wiki/view_categorized_pages.jsp";
		}
		else {
			return defaultForward;
		}
	}

}