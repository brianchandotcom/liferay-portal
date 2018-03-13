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
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.util.FragmentEntryRenderUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.utils.SoyContext;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
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
		long fragmentEntryId = getFragmentEntryId();

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

	public long getFragmentEntryId() {
		if (_fragmentEntryId != null) {
			return _fragmentEntryId;
		}

		_fragmentEntryId = ParamUtil.getLong(
			_portletRequest, "fragmentEntryId");

		return _fragmentEntryId;
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

	public SoyContext getSoyContext() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		SoyContext soyContext = new SoyContext();

		PortletURL editFragmentEntryLinkURL = PortletURLFactoryUtil.create(
			_portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.ACTION_PHASE);

		editFragmentEntryLinkURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment_display/edit_fragment_entry_link");

		soyContext.put(
			"editFragmentEntryLinkURL", editFragmentEntryLinkURL.toString());

		SoyContext fragmentEntryLinkContext = new SoyContext();

		FragmentEntryLink fragmentEntryLink = getFragmentEntryLink();

		fragmentEntryLinkContext.putHTML(
			"content",
			FragmentEntryRenderUtil.renderFragmentEntryLink(fragmentEntryLink));
		fragmentEntryLinkContext.put(
			"editableValues",
			JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues()));
		fragmentEntryLinkContext.put(
			"fragmentEntryId", fragmentEntryLink.getFragmentEntryId());
		fragmentEntryLinkContext.put(
			"fragmentEntryLinkId", fragmentEntryLink.getFragmentEntryLinkId());

		FragmentEntry fragmentEntry =
			FragmentEntryServiceUtil.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		fragmentEntryLinkContext.put("name", fragmentEntry.getName());

		fragmentEntryLinkContext.put(
			"position", fragmentEntryLink.getPosition());

		soyContext.put("fragmentEntryLink", fragmentEntryLinkContext);

		soyContext.put("portletNamespace", portletDisplay.getNamespace());
		soyContext.put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");

		return soyContext;
	}

	public boolean hasEditPermission() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
				ActionKeys.UPDATE)) {

			return false;
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (!PortletPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				portletDisplay.getId(), ActionKeys.CONFIGURATION)) {

			return false;
		}

		return true;
	}

	public boolean isShowConfigurationLink() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			portletDisplay.getId(), ActionKeys.CONFIGURATION);
	}

	private Long _fragmentEntryId;
	private Long _fragmentEntryLinkId;
	private final PortletPreferences _portletPreferences;
	private final PortletRequest _portletRequest;

}