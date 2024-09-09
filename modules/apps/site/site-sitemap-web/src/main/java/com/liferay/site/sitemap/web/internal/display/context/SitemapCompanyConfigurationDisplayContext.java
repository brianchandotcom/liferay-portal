/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.sitemap.web.internal.display.context;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.GroupItemSelectorReturnType;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.site.configuration.manager.SitemapConfigurationManager;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public class SitemapCompanyConfigurationDisplayContext {

	public SitemapCompanyConfigurationDisplayContext(
		GroupLocalService groupLocalService, ItemSelector itemSelector,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SitemapConfigurationManager sitemapConfigurationManager,
		ThemeDisplay themeDisplay) {

		_groupLocalService = groupLocalService;
		_itemSelector = itemSelector;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_sitemapConfigurationManager = sitemapConfigurationManager;
		_themeDisplay = themeDisplay;
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = _liferayPortletResponse.getNamespace() + "selectGroup";

		return _eventName;
	}

	public String getGroupSelectorURL() throws Exception {
		if (_groupSelectorURL != null) {
			return _groupSelectorURL;
		}

		SiteItemSelectorCriterion siteItemSelectorCriterion =
			new SiteItemSelectorCriterion();

		siteItemSelectorCriterion.setAllowNavigation(false);
		siteItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new GroupItemSelectorReturnType());

		Group guestGroup = _getGuestGroup();

		siteItemSelectorCriterion.setExcludedGroupIds(
			new long[] {guestGroup.getGroupId()});

		siteItemSelectorCriterion.setIncludeCompany(false);
		siteItemSelectorCriterion.setIncludeParentSites(true);
		siteItemSelectorCriterion.setIncludeRecentSites(false);

		_groupSelectorURL = String.valueOf(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(
					_liferayPortletRequest),
				getEventName(), siteItemSelectorCriterion));

		return _groupSelectorURL;
	}

	public SearchContainer<Group> getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<Group> searchContainer = new SearchContainer<>(
			_liferayPortletRequest, _liferayPortletResponse.createRenderURL(),
			null, "no-sites-were-found");

		List<Group> groups = ListUtil.fromArray(_getGuestGroup());

		groups.addAll(
			ListUtil.sort(
				ListUtil.filter(
					TransformUtil.transformToList(
						_sitemapConfigurationManager.getCompanySitemapGroupIds(
							_themeDisplay.getCompanyId()),
						groupId -> _groupLocalService.fetchGroup(groupId)),
					group -> (group != null) && !group.isGuest()),
				new GroupNameComparator(true, _themeDisplay.getLocale())));

		searchContainer.setResultsAndTotal(groups);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public boolean hasVirtualHost(Group group) {
		LayoutSet layoutSet = group.getPublicLayoutSet();

		if ((layoutSet != null) &&
			MapUtil.isNotEmpty(layoutSet.getVirtualHostnames())) {

			return true;
		}

		return false;
	}

	public boolean includeCategories() throws ConfigurationException {
		return _sitemapConfigurationManager.includeCategoriesCompanyEnabled(
			_themeDisplay.getCompanyId());
	}

	public boolean includePages() throws ConfigurationException {
		return _sitemapConfigurationManager.includePagesCompanyEnabled(
			_themeDisplay.getCompanyId());
	}

	public boolean includeWebContent() throws ConfigurationException {
		return _sitemapConfigurationManager.includeWebContentCompanyEnabled(
			_themeDisplay.getCompanyId());
	}

	public boolean xmlSitemapIndexEnabled() throws ConfigurationException {
		return _sitemapConfigurationManager.xmlSitemapIndexCompanyEnabled(
			_themeDisplay.getCompanyId());
	}

	private Group _getGuestGroup() throws Exception {
		if (_guestGroup != null) {
			return _guestGroup;
		}

		_guestGroup = _groupLocalService.getGroup(
			_themeDisplay.getCompanyId(), GroupConstants.GUEST);

		return _guestGroup;
	}

	private String _eventName;
	private final GroupLocalService _groupLocalService;
	private String _groupSelectorURL;
	private Group _guestGroup;
	private final ItemSelector _itemSelector;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<Group> _searchContainer;
	private final SitemapConfigurationManager _sitemapConfigurationManager;
	private final ThemeDisplay _themeDisplay;

}