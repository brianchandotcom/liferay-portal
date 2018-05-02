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

package com.liferay.item.selector.test;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Roberto Díaz
 */
public class TestItemSelectorView
	implements ItemSelectorView<TestItemSelectorCriterion> {

	@Override
	public Class<TestItemSelectorCriterion> getItemSelectorCriterionClass() {
		return TestItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return "Test View";
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			TestItemSelectorCriterion itemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {
	}

	private final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new ItemSelectorReturnType[] {
					new FileEntryItemSelectorReturnType()
				}));

}