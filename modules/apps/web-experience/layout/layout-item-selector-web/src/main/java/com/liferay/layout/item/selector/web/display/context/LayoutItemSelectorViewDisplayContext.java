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

package com.liferay.layout.item.selector.web.display.context;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class LayoutItemSelectorViewDisplayContext {

	public LayoutItemSelectorViewDisplayContext(
		HttpServletRequest request,
		LayoutItemSelectorCriterion layoutItemSelectorCriterion,
		String itemSelectedEventName, boolean privateLayout) {

		_request = request;
		_layoutItemSelectorCriterion = layoutItemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_privateLayout = privateLayout;
	}

	public String getCkEditorFuncNum() {
		String ckEditorFuncNum = ParamUtil.getString(
			_request, "CKEditorFuncNum");

		return ckEditorFuncNum;
	}

	public PortletURL getEditLayoutURL() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL editLayoutURL = PortletProviderUtil.getPortletURL(
			_request, Layout.class.getName(), PortletProvider.Action.EDIT);

		editLayoutURL.setParameter("redirect", themeDisplay.getURLCurrent());
		editLayoutURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
		editLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());

		return editLayoutURL;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getItemSelectorReturnTypeName() {
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			_layoutItemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		ItemSelectorReturnType desiredItemSelectorReturnType =
			desiredItemSelectorReturnTypes.get(0);

		String itemSelectorReturnTypeName = ClassUtil.getClassName(
			desiredItemSelectorReturnType);

		if (Validator.isNull(itemSelectorReturnTypeName)) {
			throw new IllegalArgumentException(
				"Invalid item selector return type " +
					itemSelectorReturnTypeName);
		}

		return itemSelectorReturnTypeName;
	}

	public LayoutItemSelectorCriterion getLayoutItemSelectorCriterion() {
		return _layoutItemSelectorCriterion;
	}

	public String getRootNodeName() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		return group.getLayoutRootNodeName(
			isPrivateLayout(), themeDisplay.getLocale());
	}

	public String getSelectedLayoutIds() {
		String selectedLayoutIds = ParamUtil.getString(
			_request, "selectedLayoutIds");

		return selectedLayoutIds;
	}

	public long getSelPlid() {
		long selPlid = ParamUtil.getLong(
			_request, "selPlid", LayoutConstants.DEFAULT_PLID);

		return selPlid;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	private final String _itemSelectedEventName;
	private final LayoutItemSelectorCriterion _layoutItemSelectorCriterion;
	private final boolean _privateLayout;
	private final HttpServletRequest _request;

}