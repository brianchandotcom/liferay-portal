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

package com.liferay.blogs.item.selector.web.display.context;

import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.blogs.item.selector.web.BlogsItemSelectorView;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class BlogsItemSelectorViewDisplayContext {

	public BlogsItemSelectorViewDisplayContext(
		BlogsItemSelectorCriterion blogsItemSelectorCriterion,
		BlogsItemSelectorView blogsItemSelectorView,
		String itemSelectedEventName, PortletURL portletURL) {

		_blogsItemSelectorCriterion = blogsItemSelectorCriterion;
		_blogsItemSelectorView = blogsItemSelectorView;
		_itemSelectedEventName = itemSelectedEventName;
		_portletURL = portletURL;
	}

	public Folder fetchAttachmentsFolder(long userId, long groupId) {
		return BlogsEntryLocalServiceUtil.fetchAttachmentsFolder(
			userId, groupId);
	}

	public BlogsItemSelectorCriterion getBlogsItemSelectorCriterion() {
		return _blogsItemSelectorCriterion;
	}

	public String getDisplayStyle(HttpServletRequest request) {
		return ParamUtil.getString(request, "displayStyle");
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public String getTitle(Locale locale) {
		return _blogsItemSelectorView.getTitle(locale);
	}

	public PortletURL getUploadURL(
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL(
			PortletKeys.BLOGS);

		portletURL.setParameter("struts_action", "/blogs/cover_image_selector");

		return portletURL;
	}

	private final BlogsItemSelectorCriterion _blogsItemSelectorCriterion;
	private final BlogsItemSelectorView _blogsItemSelectorView;
	private final String _itemSelectedEventName;
	private final PortletURL _portletURL;

}