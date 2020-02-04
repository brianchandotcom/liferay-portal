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

package com.liferay.journal.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.journal.item.selector.web.internal.constants.JournalItemSelectorWebKeys;
import com.liferay.journal.item.selector.web.internal.display.context.JournalItemSelectorViewDisplayContext;
import com.liferay.portal.kernel.language.Language;
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
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "item.selector.view.order:Integer=100",
	service = ItemSelectorView.class
)
public class JournalItemSelectorView
	implements ItemSelectorView<JournalItemSelectorCriterion> {

	@Override
	public Class<JournalItemSelectorCriterion> getItemSelectorCriterionClass() {
		return JournalItemSelectorCriterion.class;
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
		return _language.get(locale, "web-content-images");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			JournalItemSelectorCriterion journalItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		JournalItemSelectorViewDisplayContext
			journalItemSelectorViewDisplayContext =
				new JournalItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest, itemSelectedEventName,
					_itemSelectorReturnTypeResolverHandler,
					journalItemSelectorCriterion, this, portletURL, search);

		servletRequest.setAttribute(
			JournalItemSelectorWebKeys.
				JOURNAL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			journalItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/journal_images.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Reference(unbind = "-")
	public void setItemSelectorReturnTypeResolverHandler(
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler) {

		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.item.selector.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new FileEntryItemSelectorReturnType(),
				new URLItemSelectorReturnType()));

	private ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;

	@Reference
	private Language _language;

	private ServletContext _servletContext;

}