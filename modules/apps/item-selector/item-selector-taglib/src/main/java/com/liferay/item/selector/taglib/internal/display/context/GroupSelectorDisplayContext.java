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
import com.liferay.item.selector.taglib.internal.util.GroupItemSelectorTrackerUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

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

	public String getGroupItemSelectorIcon(String groupType) {
		Optional<GroupItemSelectorProvider> groupItemSelectorProviderOptional =
			GroupItemSelectorTrackerUtil.getGroupItemSelectorProviderOptional(
				groupType);

		return groupItemSelectorProviderOptional.map(
			GroupItemSelectorProvider::getIcon
		).orElse(
			"folder"
		);
	}

	public String getGroupItemSelectorLabel(String groupType) {
		Optional<GroupItemSelectorProvider> groupItemSelectorProviderOptional =
			GroupItemSelectorTrackerUtil.getGroupItemSelectorProviderOptional(
				groupType);

		return groupItemSelectorProviderOptional.map(
			groupItemSelectorProvider -> groupItemSelectorProvider.getLabel(
				_liferayPortletRequest.getLocale())
		).orElse(
			StringPool.BLANK
		);
	}

	public PortletURL getGroupItemSelectorURL(String groupType) {
		PortletURL portletURL = _getItemSelectorURL();

		portletURL.setParameter("groupType", groupType);
		portletURL.setParameter(
			"selectedTab",
			ParamUtil.getString(_liferayPortletRequest, "selectedTab"));
		portletURL.setParameter("showGroupSelector", Boolean.TRUE.toString());

		return portletURL;
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

	public Set<String> getGroupTypes() {
		return GroupItemSelectorTrackerUtil.getGroupItemSelectorProviderTypes();
	}

	public PortletURL getIteratorURL() {
		PortletURL portletURL = _getItemSelectorURL();

		portletURL.setParameter(
			"selectedTab",
			ParamUtil.getString(_liferayPortletRequest, "selectedTab"));
		portletURL.setParameter("showGroupSelector", Boolean.TRUE.toString());

		return portletURL;
	}

	public PortletURL getViewGroupURL(Group group) {
		ItemSelector itemSelector = _getItemSelector();

		String itemSelectedEventName = ParamUtil.getString(
			_liferayPortletRequest, "itemSelectedEventName");

		List<ItemSelectorCriterion> itemSelectorCriteria =
			itemSelector.getItemSelectorCriteria(
				_liferayPortletRequest.getParameterMap());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long refererGroupId = themeDisplay.getRefererGroupId();

		if (refererGroupId == 0) {
			refererGroupId = themeDisplay.getScopeGroupId();
		}

		PortletURL portletURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest),
			group, refererGroupId, itemSelectedEventName,
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));

		portletURL.setParameter(
			"selectedTab",
			ParamUtil.getString(_liferayPortletRequest, "selectedTab"));

		return portletURL;
	}

	private ItemSelector _getItemSelector() {
		return ItemSelectorUtil.getItemSelector();
	}

	private final LiferayPortletRequest _liferayPortletRequest;

}