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

package com.liferay.blogs.item.selector.web.internal;

import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.blogs.item.selector.web.internal.display.context.BlogsItemSelectorViewDisplayContext;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component
public class BlogsItemSelectorView
	implements ItemSelectorView<BlogsItemSelectorCriterion> {

	public static final String BLOGS_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT =
		"BLOGS_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT";

	@Override
	public Class<BlogsItemSelectorCriterion> getItemSelectorCriterionClass() {
		return BlogsItemSelectorCriterion.class;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "blog-images");
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			BlogsItemSelectorCriterion blogsItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		BlogsItemSelectorViewDisplayContext
			blogsItemSelectorViewDisplayContext =
				new BlogsItemSelectorViewDisplayContext(
					blogsItemSelectorCriterion, this, itemSelectedEventName,
					search, portletURL, _blogsEntryLocalService);

		request.setAttribute(
			BLOGS_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			blogsItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/blogs_attachments.jsp");

		requestDispatcher.include(request, response);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.blogs.item.selector.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new ItemSelectorReturnType[] {
					new FileEntryItemSelectorReturnType(),
					new URLItemSelectorReturnType()
				}));

	private BlogsEntryLocalService _blogsEntryLocalService;
	private ServletContext _servletContext;

}