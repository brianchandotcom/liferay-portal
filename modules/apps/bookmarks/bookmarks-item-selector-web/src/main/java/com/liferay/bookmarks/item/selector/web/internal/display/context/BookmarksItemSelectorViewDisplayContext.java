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

package com.liferay.bookmarks.item.selector.web.internal.display.context;

import com.liferay.bookmarks.item.selector.criterion.BookmarksItemSelectorCriterion;
import com.liferay.bookmarks.item.selector.web.internal.BookmarksItemSelectorView;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yang Cao
 */
public class BookmarksItemSelectorViewDisplayContext {

	public BookmarksItemSelectorViewDisplayContext(
		BookmarksItemSelectorCriterion bookmarksItemSelectorCriterion,
		BookmarksItemSelectorView bookmarksItemSelectorView,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler,
		String itemSelectedEventName, boolean search, PortletURL portletURL,
		BookmarksEntryLocalService bookmarksEntryLocalService) {

		_bookmarksItemSelectorCriterion = bookmarksItemSelectorCriterion;
		_bookmarksItemSelectorView = bookmarksItemSelectorView;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_itemSelectedEventName = itemSelectedEventName;
		_search = search;
		_portletURL = portletURL;
		_bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	public BookmarksItemSelectorCriterion getBookmarksItemSelectorCriterion() {
		return _bookmarksItemSelectorCriterion;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver() {

		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_bookmarksItemSelectorCriterion, _bookmarksItemSelectorView,
				FileEntry.class);
	}

	public PortletURL getPortletURL(
			HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		return PortletURLBuilder.create(
			PortletURLUtil.clone(_portletURL, liferayPortletResponse)
		).setParameter(
			"selectedTab", getTitle(httpServletRequest.getLocale())
		).buildPortletURL();
	}

	public String getTitle(Locale locale) {
		return _bookmarksItemSelectorView.getTitle(locale);
	}

	public boolean isSearch() {
		return _search;
	}

	private final BookmarksEntryLocalService _bookmarksEntryLocalService;
	private final BookmarksItemSelectorCriterion
		_bookmarksItemSelectorCriterion;
	private final BookmarksItemSelectorView _bookmarksItemSelectorView;
	private final String _itemSelectedEventName;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final PortletURL _portletURL;
	private final boolean _search;

}