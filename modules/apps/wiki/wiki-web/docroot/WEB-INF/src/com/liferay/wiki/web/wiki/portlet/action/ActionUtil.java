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

package com.liferay.wiki.web.wiki.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.wiki.configuration.WikiServiceConfigurationValues;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiNodeServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermission;
import com.liferay.wiki.util.WikiUtil;
import com.liferay.wiki.web.settings.WikiPortletInstanceSettings;

import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class ActionUtil {

	public static WikiNode getFirstNode(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		List<WikiNode> nodes = WikiNodeLocalServiceUtil.getNodes(groupId);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		WikiPortletInstanceSettings wikiPortletInstanceSettings =
			WikiPortletInstanceSettings.getInstance(
				themeDisplay.getLayout(), portletDisplay.getId());

		String[] visibleNodeNames =
			wikiPortletInstanceSettings.getVisibleNodes();

		nodes = WikiUtil.orderNodes(nodes, visibleNodeNames);

		String[] hiddenNodes = wikiPortletInstanceSettings.getHiddenNodes();
		Arrays.sort(hiddenNodes);

		for (WikiNode node : nodes) {
			if ((Arrays.binarySearch(hiddenNodes, node.getName()) < 0) &&
				WikiNodePermission.contains(
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

			if (layout.isPublicLayout()) {
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

		WikiPage page = WikiPageLocalServiceUtil.fetchPage(
			nodeId, WikiServiceConfigurationValues.FRONT_PAGE_NAME, 0);

		if (page == null) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				WikiPage.class.getName(), portletRequest);

			Layout layout = themeDisplay.getLayout();

			serviceContext.setAddGroupPermissions(true);

			if (layout.isPublicLayout()) {
				serviceContext.setAddGuestPermissions(true);
			}
			else {
				serviceContext.setAddGuestPermissions(false);
			}

			boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

			try {
				WorkflowThreadLocal.setEnabled(false);

				page = WikiPageLocalServiceUtil.addPage(
					themeDisplay.getDefaultUserId(), nodeId,
					WikiServiceConfigurationValues.FRONT_PAGE_NAME, null,
					WikiPageConstants.NEW, true, serviceContext);
			}
			finally {
				WorkflowThreadLocal.setEnabled(workflowEnabled);
			}
		}

		return page;
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
			node = ActionUtil.getFirstVisibleNode(portletRequest);
		}

		request.setAttribute(WikiWebKeys.WIKI_NODE, node);

		return node;
	}

	public static void getPage(PortletRequest portletRequest) throws Exception {
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

		if (Validator.isNull(title)) {
			title = WikiServiceConfigurationValues.FRONT_PAGE_NAME;
		}

		WikiPage page = null;

		try {
			page = WikiPageServiceUtil.getPage(nodeId, title, version);

			if (page.isDraft()) {
				StringBundler sb = new StringBundler(7);

				sb.append("{nodeId=");
				sb.append(nodeId);
				sb.append(", title=");
				sb.append(title);
				sb.append(", version=");
				sb.append(version);
				sb.append("}");

				throw new NoSuchPageException(sb.toString());
			}
		}
		catch (NoSuchPageException nspe) {
			if (title.equals(WikiServiceConfigurationValues.FRONT_PAGE_NAME) &&
				(version == 0)) {

				page = getFirstVisiblePage(nodeId, portletRequest);
			}
			else {
				throw nspe;
			}
		}

		request.setAttribute(WikiWebKeys.WIKI_PAGE, page);
	}

}