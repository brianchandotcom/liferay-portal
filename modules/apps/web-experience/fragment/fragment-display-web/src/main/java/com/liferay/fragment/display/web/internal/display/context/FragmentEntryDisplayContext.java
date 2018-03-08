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

package com.liferay.fragment.display.web.internal.display.context;

import com.liferay.fragment.display.web.internal.constants.FragmentEntryDisplayWebKeys;
import com.liferay.fragment.item.selector.criterion.FragmentItemSelectorCriterion;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class FragmentEntryDisplayContext {

	public FragmentEntryDisplayContext(
		PortletRequest portletRequest, PortletPreferences portletPreferences) {

		_portletRequest = portletRequest;
		_portletPreferences = portletPreferences;
	}

	public String getEventName() {
		return "selectFragmentEntry";
	}

	public FragmentEntry getFragmentEntry() {
		long fragmentEntryId = ParamUtil.getLong(
			_portletRequest, "fragmentEntryId");

		if (fragmentEntryId != 0) {
			return FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentEntryId);
		}

		FragmentEntryLink fragmentEntryLink = getFragmentEntryLink();

		if (Validator.isNotNull(fragmentEntryLink)) {
			return FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());
		}

		return null;
	}

	public FragmentEntryLink getFragmentEntryLink() {
		return FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
			getFragmentEntryLinkId());
	}

	public long getFragmentEntryLinkId() {
		if (_fragmentEntryLinkId == null) {
			_fragmentEntryLinkId = PrefsParamUtil.getLong(
				_portletPreferences, _portletRequest, "fragmentEntryLinkId");
		}

		return _fragmentEntryLinkId;
	}

	public String getItemSelectorURL() {
		ItemSelector itemSelector = (ItemSelector)_portletRequest.getAttribute(
			FragmentEntryDisplayWebKeys.ITEM_SELECTOR);

		FragmentItemSelectorCriterion fragmentItemSelectorCriterion =
			new FragmentItemSelectorCriterion();

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(new UUIDItemSelectorReturnType());

		fragmentItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_portletRequest),
			getEventName(), fragmentItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public boolean isShowConfigurationLink() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			portletDisplay.getId(), ActionKeys.CONFIGURATION);
	}

	private Long _fragmentEntryLinkId;
	private final PortletPreferences _portletPreferences;
	private final PortletRequest _portletRequest;

}