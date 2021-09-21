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

package com.liferay.item.selector.taglib.internal.display.context;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.item.selector.taglib.internal.servlet.item.selector.ItemSelectorUtil;
import com.liferay.item.selector.taglib.internal.util.EntryURLUtil;
import com.liferay.item.selector.taglib.internal.util.GroupItemSelectorTrackerUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.GroupSearch;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.portlet.PortletURL;

/**
 * @author Cristina González
 */
public class GroupSelectorDisplayContext {

	public GroupSelectorDisplayContext(
		LiferayPortletRequest liferayPortletRequest) {

		_liferayPortletRequest = liferayPortletRequest;
	}

	public String getGroupItemSelectorIcon() {
		Optional<GroupItemSelectorProvider> optional =
			GroupItemSelectorTrackerUtil.getGroupItemSelectorProviderOptional(
				_getGroupType());

		return optional.map(
			GroupItemSelectorProvider::getIcon
		).orElse(
			"folder"
		);
	}

	public String getGroupItemSelectorLabel(String groupType) {
		Optional<GroupItemSelectorProvider> optional =
			GroupItemSelectorTrackerUtil.getGroupItemSelectorProviderOptional(
				groupType);

		return optional.map(
			groupItemSelectorProvider -> groupItemSelectorProvider.getLabel(
				_liferayPortletRequest.getLocale())
		).orElse(
			StringPool.BLANK
		);
	}

	public PortletURL getGroupItemSelectorURL(String groupType) {
		return PortletURLBuilder.create(
			_getItemSelectorURL()
		).setParameter(
			"groupType", groupType
		).setParameter(
			"scopeGroupType",
			ParamUtil.getString(_liferayPortletRequest, "scopeGroupType")
		).setParameter(
			"selectedTab",
			ParamUtil.getString(_liferayPortletRequest, "selectedTab")
		).setParameter(
			"showGroupSelector", true
		).buildPortletURL();
	}

	public Set<String> getGroupTypes() {
		return GroupItemSelectorTrackerUtil.getGroupItemSelectorProviderTypes();
	}

	public SearchContainer<Group> getSearchContainer() {
		SearchContainer<Group> searchContainer = new GroupSearch(
			_liferayPortletRequest, _getIteratorURL());

		searchContainer.setEmptyResultsMessage(_getEmptyResultsMessage());

		List<Group> groups = (List<Group>)_liferayPortletRequest.getAttribute(
			"liferay-item-selector:group-selector:groups");

		searchContainer.setResults(groups);

		int groupsCount = GetterUtil.getInteger(
			_liferayPortletRequest.getAttribute(
				"liferay-item-selector:group-selector:groupsCount"));

		searchContainer.setTotal(groupsCount);

		return searchContainer;
	}

	public String getViewGroupURL(Group group) {
		PortletURL portletURL = EntryURLUtil.getGroupPortletURL(
			group, _liferayPortletRequest);

		return portletURL.toString();
	}

	public boolean isGroupTypeActive(String groupType) {
		if (groupType.equals(_getGroupType())) {
			return true;
		}

		return false;
	}

	protected GroupSelectorDisplayContext(
		String groupType, LiferayPortletRequest liferayPortletRequest) {

		_groupType = groupType;
		_liferayPortletRequest = liferayPortletRequest;
	}

	private String _getEmptyResultsMessage() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Optional<GroupItemSelectorProvider> optional =
			GroupItemSelectorTrackerUtil.getGroupItemSelectorProviderOptional(
				_getGroupType());

		return optional.map(
			groupItemSelectorProvider ->
				groupItemSelectorProvider.getEmptyResultsMessage(
					themeDisplay.getLocale())
		).orElse(
			GroupSearch.EMPTY_RESULTS_MESSAGE
		);
	}

	private String _getGroupType() {
		if (_groupType != null) {
			return _groupType;
		}

		_groupType = ParamUtil.getString(_liferayPortletRequest, "groupType");

		return _groupType;
	}

	private ItemSelector _getItemSelector() {
		return ItemSelectorUtil.getItemSelector();
	}

	private PortletURL _getItemSelectorURL() {
		ItemSelector itemSelector = _getItemSelector();

		String itemSelectedEventName = ParamUtil.getString(
			_liferayPortletRequest, "itemSelectedEventName");

		List<ItemSelectorCriterion> itemSelectorCriteria =
			itemSelector.getItemSelectorCriteria(
				_liferayPortletRequest.getParameterMap());

		return itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest),
			itemSelectedEventName,
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));
	}

	private PortletURL _getIteratorURL() {
		return PortletURLBuilder.create(
			_getItemSelectorURL()
		).setParameter(
			"groupType", _getGroupType()
		).setParameter(
			"selectedTab",
			ParamUtil.getString(_liferayPortletRequest, "selectedTab")
		).setParameter(
			"showGroupSelector", true
		).buildPortletURL();
	}

	private String _groupType;
	private final LiferayPortletRequest _liferayPortletRequest;

}