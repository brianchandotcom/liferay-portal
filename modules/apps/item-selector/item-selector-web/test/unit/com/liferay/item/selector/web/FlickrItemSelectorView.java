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

package com.liferay.item.selector.web;

import com.liferay.item.selector.ItemSelectorView;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Iván Zaera
 */
public class FlickrItemSelectorView
	implements ItemSelectorView
		<FlickrItemSelectorCriterion, TestItemSelectorReturnTypes> {

	@Override
	public Class<FlickrItemSelectorCriterion> getItemSelectorCriterionClass() {
		return FlickrItemSelectorCriterion.class;
	}

	@Override
	public Set<TestItemSelectorReturnTypes>
		getItemSelectorSupportedReturnTypes() {

		return null;
	}

	@Override
	public String getTitle(Locale locale) {
		return FlickrItemSelectorView.class.getName();
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			FlickrItemSelectorCriterion flickrItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName)
		throws IOException {

		PrintWriter printWriter = response.getWriter();

		printWriter.print(
			"<html>" + FlickrItemSelectorView.class.getName() + "</html>");
	}

	private static final Set<TestItemSelectorReturnTypes>
		_TEST_ITEM_SELECTOR_SUPPORTED_RETURN_TYPES = new HashSet<>();

	static {
		_TEST_ITEM_SELECTOR_SUPPORTED_RETURN_TYPES.add(
			TestItemSelectorReturnTypes.URL);
	}

}