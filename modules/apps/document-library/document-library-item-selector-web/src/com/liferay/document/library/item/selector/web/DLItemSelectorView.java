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

package com.liferay.document.library.item.selector.web;

import com.liferay.document.library.item.selector.DLItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Roberto Díaz
 */
public class DLItemSelectorView
	implements ItemSelectorView<DLItemSelectorCriterion> {

	public static final String DL_ITEM_SELECTOR_CRITERION =
		DLItemSelectorView.class.getName() + "#DL_ITEM_SELECTOR_CRITERION";

	public static final String ITEM_SELECTED_EVENT_NAME =
		DLItemSelectorView.class.getName() + "#ITEM_SELECTED_EVENT_NAME";

	public static final String PORTLET_URL =
		DLItemSelectorView.class.getName() + "#PORTLET_URL";

	@Override
	public Class<DLItemSelectorCriterion> getItemSelectorCriterionClass() {
		return DLItemSelectorCriterion.class;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content/Language", locale);

		return resourceBundle.getString("documents");
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			DLItemSelectorCriterion dlItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName)
		throws IOException, ServletException {

		request.setAttribute(
			DL_ITEM_SELECTOR_CRITERION, dlItemSelectorCriterion);
		request.setAttribute(ITEM_SELECTED_EVENT_NAME, itemSelectedEventName);
		request.setAttribute(PORTLET_URL, portletURL);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			"/o/document-library-item-selector-web/documents.jsp");

		requestDispatcher.include(request, response);
	}

}