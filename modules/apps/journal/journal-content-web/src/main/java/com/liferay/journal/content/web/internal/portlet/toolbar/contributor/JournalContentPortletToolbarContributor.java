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
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.content.web.internal.configuration.JournalContentPortletInstanceConfiguration;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.toolbar.contributor.BasePortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
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

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!_hasAddArticlePermission(themeDisplay) ||
			layout.isLayoutPrototypeLinkActive()) {

			return Collections.emptyList();
		}

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			_addPortletTitleAddJournalArticleMenuItems(
				menuItems, themeDisplay, portletRequest);
		}
		catch (Exception exception) {
			_log.error("Unable to add folder menu item", exception);
		}

		return menuItems;
	}

	private void _addPortletTitleAddJournalArticleMenuItems(
			List<MenuItem> menuItems, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws Exception {

		long plid = themeDisplay.getPlid();
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
		long scopeGroupId = themeDisplay.getScopeGroupId();

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				portletRequest, JournalPortletKeys.JOURNAL,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_article.jsp"
		).setRedirect(
			_portal.getLayoutFullURL(themeDisplay)
		).setPortletResource(
			portletDisplay.getId()
		).setParameter(
			"groupId", scopeGroupId
		).setParameter(
			"refererPlid", plid
		).buildPortletURL();

		List<DDMStructure> ddmStructures =
			_journalFolderService.getDDMStructures(
				_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalFolderConstants.RESTRICTION_TYPE_INHERIT);

		JournalContentPortletInstanceConfiguration
			journalContentPortletInstanceConfiguration =
				portletDisplay.getPortletInstanceConfiguration(
					JournalContentPortletInstanceConfiguration.class);

		if (journalContentPortletInstanceConfiguration.
				sortStructuresByByName()) {

			Locale locale = themeDisplay.getLocale();

			ddmStructures.sort(
				(ddmStructure1, ddmStructure2) -> {
					String name1 = ddmStructure1.getName(locale);
					String name2 = ddmStructure2.getName(locale);

					return name1.compareTo(name2);
				});
		}

		for (DDMStructure ddmStructure : ddmStructures) {
			portletURL.setParameter(
				"ddmStructureId",
				String.valueOf(ddmStructure.getStructureId()));

			URLMenuItem urlMenuItem = new URLMenuItem();

			urlMenuItem.setData(
				HashMapBuilder.<String, Object>put(
					"id",
					HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset"
				).put(
					"title",
					HtmlUtil.escape(
						LanguageUtil.format(
							themeDisplay.getLocale(), "new-x",
							ddmStructure.getName(themeDisplay.getLocale())))
				).build());

			String label = ddmStructure.getUnambiguousName(
				ddmStructures, themeDisplay.getScopeGroupId(),
				themeDisplay.getLocale());

			urlMenuItem.setLabel(label);

			String url = _http.addParameter(
				portletURL.toString(), "refererPlid", plid);

			urlMenuItem.setURL(url);

			menuItems.add(urlMenuItem);
		}
	}

	private boolean _hasAddArticlePermission(ThemeDisplay themeDisplay) {
		boolean hasResourcePermission = _portletResourcePermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			ActionKeys.ADD_ARTICLE);

		boolean hasPortletPermission = false;

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			hasPortletPermission = PortletPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				portletDisplay.getId(), ActionKeys.CONFIGURATION);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to check Journal Content portlet permission",
					portalException);
			}
		}

		boolean hasAddArticlePermission = false;

		if (hasResourcePermission && hasPortletPermission) {
			hasAddArticlePermission = true;
		}

		return hasAddArticlePermission;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentPortletToolbarContributor.class);

	@Reference
	private Http _http;

	@Reference
	private JournalFolderService _journalFolderService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + JournalConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}