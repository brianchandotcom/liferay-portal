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

package com.liferay.layout.item.selector.web;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.layout.item.selector.view.LayoutItemSelectorView;
import com.liferay.layout.item.selector.web.internal.constants.LayoutsItemSelectorWebKeys;
import com.liferay.layout.item.selector.web.internal.display.context.LayoutItemSelectorViewDisplayContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
public abstract class BaseLayoutsItemSelectorView
	implements ItemSelectorView<LayoutItemSelectorCriterion>,
			   LayoutItemSelectorView {

	public static final String LAYOUT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT =
		"LAYOUT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT";

	@Override
	public Class<LayoutItemSelectorCriterion> getItemSelectorCriterionClass() {
		return LayoutItemSelectorCriterion.class;
	}

	public abstract ServletContext getServletContext();

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public abstract boolean isPrivateLayout();

	@Override
	public boolean isShowSearch() {
		return false;
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			LayoutItemSelectorCriterion layoutItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		LayoutItemSelectorViewDisplayContext
			layoutItemSelectorViewDisplayContext =
				new LayoutItemSelectorViewDisplayContext(
					(HttpServletRequest)request, layoutItemSelectorCriterion,
					itemSelectedEventName, resourceBundleLoader,
					isPrivateLayout());

		request.setAttribute(
			LayoutsItemSelectorWebKeys.
				LAYOUT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			layoutItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/layouts.jsp");

		requestDispatcher.include(request, response);
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.layout.admin.web)",
		unbind = "-"
	)
	protected ResourceBundleLoader resourceBundleLoader;

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new ItemSelectorReturnType[] {
					new URLItemSelectorReturnType(),
					new UUIDItemSelectorReturnType()
				}));

}