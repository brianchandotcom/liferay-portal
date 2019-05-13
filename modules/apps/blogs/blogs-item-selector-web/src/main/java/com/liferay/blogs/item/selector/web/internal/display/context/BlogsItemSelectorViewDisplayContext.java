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

package com.liferay.blogs.item.selector.web.internal.display.context;

import com.liferay.blogs.configuration.BlogsFileUploadsConfiguration;
import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.blogs.item.selector.web.internal.BlogsItemSelectorView;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class BlogsItemSelectorViewDisplayContext {

	public BlogsItemSelectorViewDisplayContext(
		BlogsItemSelectorCriterion blogsItemSelectorCriterion,
		BlogsItemSelectorView blogsItemSelectorView,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler,
		String itemSelectedEventName, boolean search, PortletURL portletURL,
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsItemSelectorCriterion = blogsItemSelectorCriterion;
		_blogsItemSelectorView = blogsItemSelectorView;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_itemSelectedEventName = itemSelectedEventName;
		_search = search;
		_portletURL = portletURL;
		_blogsEntryLocalService = blogsEntryLocalService;
	}

	public Folder fetchAttachmentsFolder(long userId, long groupId) {
		return _blogsEntryLocalService.fetchAttachmentsFolder(userId, groupId);
	}

	public BlogsItemSelectorCriterion getBlogsItemSelectorCriterion() {
		return _blogsItemSelectorCriterion;
	}

	public String[] getImageExtensions() throws ConfigurationException {
		return _getBlogsFileUploadsConfiguration().imageExtensions();
	}

	public long getImageMaxSize() throws ConfigurationException {
		return _getBlogsFileUploadsConfiguration().imageMaxSize();
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public ItemSelectorReturnTypeResolver getItemSelectorReturnTypeResolver() {
		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_blogsItemSelectorCriterion, _blogsItemSelectorView,
				FileEntry.class);
	}

	public PortletURL getPortletURL(
			HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, liferayPortletResponse);

		portletURL.setParameter(
			"selectedTab",
			String.valueOf(getTitle(httpServletRequest.getLocale())));

		return portletURL;
	}

	public String getTitle(Locale locale) {
		return _blogsItemSelectorView.getTitle(locale);
	}

	public PortletURL getUploadURL(
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL(
			PortletKeys.BLOGS);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/blogs/upload_image");

		return portletURL;
	}

	public boolean isSearch() {
		return _search;
	}

	private BlogsFileUploadsConfiguration _getBlogsFileUploadsConfiguration()
		throws ConfigurationException {

		if (_blogsFileUploadsConfiguration == null) {
			_blogsFileUploadsConfiguration =
				ConfigurationProviderUtil.getSystemConfiguration(
					BlogsFileUploadsConfiguration.class);
		}

		return _blogsFileUploadsConfiguration;
	}

	private final BlogsEntryLocalService _blogsEntryLocalService;
	private BlogsFileUploadsConfiguration _blogsFileUploadsConfiguration;
	private final BlogsItemSelectorCriterion _blogsItemSelectorCriterion;
	private final BlogsItemSelectorView _blogsItemSelectorView;
	private final String _itemSelectedEventName;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final PortletURL _portletURL;
	private final boolean _search;

}