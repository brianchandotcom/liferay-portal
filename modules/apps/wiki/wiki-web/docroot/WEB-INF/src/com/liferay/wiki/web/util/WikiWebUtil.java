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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermission;
import com.liferay.wiki.web.configuration.WikiPortletInstanceSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

/**
 * @author Ivan Zaera
 */
public class WikiWebUtil {

	public static Map<String, String> getEmailFromDefinitionTerms(
		RenderRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$PAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PAGE_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PORTLET_NAME$]", PortalUtil.getPortletTitle(request));
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-wiki"));

		return definitionTerms;
	}

	public static Map<String, String> getEmailNotificationDefinitionTerms(
		RenderRequest request, String emailFromAddress, String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$DIFFS_URL$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-url-of-the-page-comparing-this-page-content-with-the-" +
					"previous-version"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));
		definitionTerms.put(
			"[$NODE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-node-in-which-the-page-was-added"));
		definitionTerms.put(
			"[$PAGE_CONTENT$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-page-content"));
		definitionTerms.put(
			"[$PAGE_DATE_UPDATE$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-date-of-the-modifications"));
		definitionTerms.put(
			"[$PAGE_DIFFS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-page-content-compared-with-the-previous-version-page-" +
					"content"));
		definitionTerms.put(
			"[$PAGE_ID$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-page-id"));
		definitionTerms.put(
			"[$PAGE_SUMMARY$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-summary-of-the-page-or-the-modifications"));
		definitionTerms.put(
			"[$PAGE_TITLE$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-page-title"));
		definitionTerms.put(
			"[$PAGE_URL$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-page-url"));
		definitionTerms.put(
			"[$PAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PAGE_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-page"));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		definitionTerms.put(
			"[$PORTLET_NAME$]", PortalUtil.getPortletTitle(request));
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-recipient"));

		return definitionTerms;
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