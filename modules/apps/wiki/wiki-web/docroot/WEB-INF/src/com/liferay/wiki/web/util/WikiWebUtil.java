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

package com.liferay.wiki.web.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermission;
import com.liferay.wiki.web.configuration.WikiPortletInstanceSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Ivan Zaera
 */
public class WikiWebUtil {

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

		nodes = orderNodes(nodes, visibleNodeNames);

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

	public static List<String> getNodeNames(List<WikiNode> nodes) {
		List<String> nodeNames = new ArrayList<String>(nodes.size());

		for (WikiNode node : nodes) {
			nodeNames.add(node.getName());
		}

		return nodeNames;
	}

	public static List<WikiNode> orderNodes(
		List<WikiNode> nodes, String[] visibleNodeNames) {

		if (ArrayUtil.isEmpty(visibleNodeNames)) {
			return nodes;
		}

		nodes = ListUtil.copy(nodes);

		List<WikiNode> orderedNodes = new ArrayList<WikiNode>(nodes.size());

		for (String visibleNodeName : visibleNodeNames) {
			for (WikiNode node : nodes) {
				if (node.getName().equals(visibleNodeName)) {
					orderedNodes.add(node);

					nodes.remove(node);

					break;
				}
			}
		}

		orderedNodes.addAll(nodes);

		return orderedNodes;
	}

}