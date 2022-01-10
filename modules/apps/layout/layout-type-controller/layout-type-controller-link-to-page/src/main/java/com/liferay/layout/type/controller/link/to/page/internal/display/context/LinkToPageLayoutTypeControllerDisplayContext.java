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

package com.liferay.layout.type.controller.link.to.page.internal.display.context;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.layout.type.controller.link.to.page.internal.constants.LinkToPageLayoutTypeControllerWebKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class LinkToPageLayoutTypeControllerDisplayContext {

	public LinkToPageLayoutTypeControllerDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_setSelectedLayout();
	}

	public String getEventName() {
		return _liferayPortletResponse.getNamespace() + "selectLinkToPage";
	}

	public String getItemSelectorURL() throws Exception {
		ItemSelector itemSelector =
			(ItemSelector)_liferayPortletRequest.getAttribute(
				LinkToPageLayoutTypeControllerWebKeys.ITEM_SELECTOR);

		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setCheckDisplayPage(false);
		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new UUIDItemSelectorReturnType());
		layoutItemSelectorCriterion.setEnableCurrentPage(false);
		layoutItemSelectorCriterion.setShowBreadcrumb(false);
		layoutItemSelectorCriterion.setShowHiddenPages(true);

		boolean privateLayout = ParamUtil.getBoolean(
			_liferayPortletRequest, "privateLayout");

		layoutItemSelectorCriterion.setShowPrivatePages(privateLayout);
		layoutItemSelectorCriterion.setShowPublicPages(!privateLayout);

		PortletURL itemSelectorURL = PortletURLBuilder.create(
			itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(
					_liferayPortletRequest),
				getEventName(), layoutItemSelectorCriterion)
		).setParameter(
			"layoutUuid", getLinkToLayoutUuid()
		).buildPortletURL();

		long selPlid = ParamUtil.getLong(_liferayPortletRequest, "selPlid");

		itemSelectorURL.setParameter("selPlid", String.valueOf(selPlid));

		return itemSelectorURL.toString();
	}

	public String getLinkToLayoutName() throws Exception {
		if (_selectedLayout != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return _selectedLayout.getBreadcrumb(themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	public String getLinkToLayoutUuid() {
		if (_selectedLayout != null) {
			return _selectedLayout.getUuid();
		}

		return ParamUtil.getString(_liferayPortletRequest, "layoutUuid");
	}

	private void _setSelectedLayout() {
		Layout layout = (Layout)_liferayPortletRequest.getAttribute(
			WebKeys.SEL_LAYOUT);

		if (layout != null) {
			long linkToLayoutId = GetterUtil.getLong(
				layout.getTypeSettingsProperty("linkToLayoutId"));

			_selectedLayout = LayoutLocalServiceUtil.fetchLayout(
				layout.getGroupId(), layout.isPrivateLayout(), linkToLayoutId);
		}
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Layout _selectedLayout;

}