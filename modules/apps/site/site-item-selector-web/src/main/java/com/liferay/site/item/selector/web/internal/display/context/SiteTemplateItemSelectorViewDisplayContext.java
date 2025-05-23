/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.item.selector.SiteItemSelectorCriterion;
import com.liferay.site.search.GroupSearch;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletURL;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Roberto Díaz
 */
public class SiteTemplateItemSelectorViewDisplayContext
	extends BaseSitesItemSelectorViewDisplayContext {

	public SiteTemplateItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest,
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL) {

		super(
			httpServletRequest, siteItemSelectorCriterion,
			itemSelectedEventName, portletURL);

		_portletRequest = getPortletRequest();
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_addBreadcrumbEntries();
	}

	@Override
	public GroupSearch getGroupSearch() throws Exception {
		if (_groupSearch != null) {
			return _groupSearch;
		}

		PortletURL portletURL = getPortletURL();

		Group group = _getGroup();

		if (group != null) {
			portletURL.setParameter(
				"groupId", String.valueOf(group.getGroupId()));
		}

		_groupSearch = new GroupSearch(_portletRequest, portletURL);

		List<LayoutSetPrototype> layoutSetPrototypes =
			LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(
				_themeDisplay.getCompanyId());

		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			_groupSearch.setResultsAndTotal(
				() -> ListUtil.concat(
					Arrays.asList(layoutSetPrototype.getGroup()),
					_groupSearch.getResults()),
				_groupSearch.getTotal() + 1);
		}

		return _groupSearch;
	}

	@Override
	public boolean isShowChildSitesLink() {
		return false;
	}

	@Override
	public boolean isShowSortFilter() {
		return true;
	}

	private void _addBreadcrumbEntries() {
		Group group = _getGroup();

		if (group == null) {
			return;
		}

		try {
			PortletURL portletURL = getPortletURL();

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, LanguageUtil.get(httpServletRequest, "all"),
				portletURL.toString());

			_addPortletBreadcrumbEntries(group, httpServletRequest, portletURL);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to add breadcrumb entries for group " +
					group.getGroupId(),
				exception);
		}
	}

	private void _addPortletBreadcrumbEntries(
			Group group, HttpServletRequest httpServletRequest,
			PortletURL portletURL)
		throws Exception {

		List<Group> ancestorGroups = group.getAncestors();

		Collections.reverse(ancestorGroups);

		for (Group ancestorGroup : ancestorGroups) {
			portletURL.setParameter(
				"groupId", String.valueOf(ancestorGroup.getGroupId()));

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, ancestorGroup.getDescriptiveName(),
				portletURL.toString());
		}

		Group unescapedGroup = group.toUnescapedModel();

		portletURL.setParameter(
			"groupId", String.valueOf(unescapedGroup.getGroupId()));

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, unescapedGroup.getDescriptiveName(),
			portletURL.toString());
	}

	private Group _getGroup() {
		long groupId = ParamUtil.getLong(
			httpServletRequest, "groupId",
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId > 0) {
			return GroupLocalServiceUtil.fetchGroup(groupId);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteTemplateItemSelectorViewDisplayContext.class);

	private GroupSearch _groupSearch;
	private final PortletRequest _portletRequest;
	private final ThemeDisplay _themeDisplay;

}