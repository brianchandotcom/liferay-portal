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

package com.liferay.journal.content.web.internal.portlet.toolbar.contributor;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.BasePortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourcePermissionChecker;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
		"mvc.path=-", "mvc.path=/view.jsp"
	},
	service = {
		JournalContentPortletToolbarContributor.class,
		PortletToolbarContributor.class
	}
)
public class JournalContentPortletToolbarContributor
	extends BasePortletToolbarContributor {

	protected void addPortletTitleAddJournalArticleMenuItems(
			List<MenuItem> menuItems, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws Exception {

		long plid = themeDisplay.getPlid();
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
		long scopeGroupId = themeDisplay.getScopeGroupId();

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, JournalPortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"hideDefaultSuccessMessage", Boolean.TRUE.toString());
		portletURL.setParameter("groupId", String.valueOf(scopeGroupId));
		portletURL.setParameter("mvcPath", "/edit_article.jsp");
		portletURL.setParameter("portletResource", portletDisplay.getId());
		portletURL.setParameter(
			"redirect",
			_getAddJournalArticleRedirectURL(themeDisplay, portletRequest));
		portletURL.setParameter("referringPlid", String.valueOf(plid));
		portletURL.setParameter("showHeader", Boolean.FALSE.toString());

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		List<DDMStructure> ddmStructures =
			_journalFolderService.getDDMStructures(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalFolderConstants.RESTRICTION_TYPE_INHERIT);

		for (DDMStructure ddmStructure : ddmStructures) {
			portletURL.setParameter(
				"ddmStructureId",
				String.valueOf(ddmStructure.getStructureId()));

			URLMenuItem urlMenuItem = new URLMenuItem();

			String ddmStructureName = ddmStructure.getName(
				themeDisplay.getLocale());

			String title = LanguageUtil.format(
				themeDisplay.getLocale(), "new-x", ddmStructureName);

			Map<String, Object> data = new HashMap<>();

			data.put(
				"id",
				HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset");

			data.put("title", HtmlUtil.escape(title));

			urlMenuItem.setData(data);

			String label = ddmStructure.getUnambiguousName(
				ddmStructures, themeDisplay.getScopeGroupId(),
				themeDisplay.getLocale());

			urlMenuItem.setLabel(label);

			urlMenuItem.setURL(portletURL.toString());
			urlMenuItem.setUseDialog(true);

			menuItems.add(urlMenuItem);
		}
	}

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		if (!_resourcePermissionChecker.checkResource(
				themeDisplay.getPermissionChecker(), scopeGroupId,
				ActionKeys.ADD_ARTICLE)) {

			return Collections.emptyList();
		}

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			addPortletTitleAddJournalArticleMenuItems(
				menuItems, themeDisplay, portletRequest);
		}
		catch (Exception e) {
			_log.error("Unable to add folder menu item", e);
		}

		return menuItems;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderService(
		JournalFolderService journalFolderService) {

		_journalFolderService = journalFolderService;
	}

	@Reference(target = "(resource.name=com.liferay.journal)", unbind = "-")
	protected void setResourcePermissionChecker(
		ResourcePermissionChecker resourcePermissionChecker) {

		_resourcePermissionChecker = resourcePermissionChecker;
	}

	private String _getAddJournalArticleRedirectURL(
			ThemeDisplay themeDisplay, PortletRequest portletRequest)
		throws Exception {

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			portletRequest, JournalContentPortletKeys.JOURNAL_CONTENT,
			PortletRequest.RENDER_PHASE);

		redirectURL.setWindowState(LiferayWindowState.POP_UP);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		redirectURL.setParameter(
			"mvcPath", "/update_journal_article_redirect.jsp");
		redirectURL.setParameter(
			"referringPortletResource", portletDisplay.getId());

		return redirectURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentPortletToolbarContributor.class);

	private JournalFolderService _journalFolderService;
	private ResourcePermissionChecker _resourcePermissionChecker;

}