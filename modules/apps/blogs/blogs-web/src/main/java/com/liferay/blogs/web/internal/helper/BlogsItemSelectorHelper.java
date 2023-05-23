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

package com.liferay.blogs.web.internal.helper;

import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(service = BlogsItemSelectorHelper.class)
public class BlogsItemSelectorHelper {

	public String getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		ThemeDisplay themeDisplay, String itemSelectedEventName) {

		ItemSelector itemSelector = _itemSelectorSnapshot.get();

		if (itemSelector == null) {
			return null;
		}

		ItemSelectorCriterion blogsItemSelectorCriterion =
			new BlogsItemSelectorCriterion();

		blogsItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		ImageItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DownloadFileEntryItemSelectorReturnType());

		return String.valueOf(
			itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, itemSelectedEventName,
				blogsItemSelectorCriterion, imageItemSelectorCriterion));
	}

	private static final Snapshot<ItemSelector> _itemSelectorSnapshot =
		new Snapshot<>(
			BlogsItemSelectorHelper.class, ItemSelector.class, null, true);

}