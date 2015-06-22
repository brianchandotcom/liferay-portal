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

package com.liferay.wiki.web.item.selector.view;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.wiki.item.selector.criterion.WikiAttachmentItemSelectorCriterion;
import com.liferay.wiki.web.item.selector.view.display.context.WikiAttachmentItemSelectorViewDisplayContext;

import java.io.IOException;

import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component
public class WikiAttachmentItemSelectorView
	implements ItemSelectorView<WikiAttachmentItemSelectorCriterion> {

	public static final String
		WIKI_ATTACHMENT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT =
			"WIKI_ATTACHMENT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT";

	@Override
	public Class<WikiAttachmentItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return WikiAttachmentItemSelectorCriterion.class;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public Set<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content/Language", locale);

		return resourceBundle.getString("page-attachments");
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			WikiAttachmentItemSelectorCriterion
				wikiAttachmentItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName)
		throws IOException, ServletException {

		WikiAttachmentItemSelectorViewDisplayContext
			wikiAttachmentItemSelectorViewDisplayContext =
				new WikiAttachmentItemSelectorViewDisplayContext(
					wikiAttachmentItemSelectorCriterion, this,
					itemSelectedEventName, portletURL);

		request.setAttribute(
			WIKI_ATTACHMENT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			wikiAttachmentItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(
				"/html/item/selector/attachments.jsp");

		requestDispatcher.include(request, response);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.wiki.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final Set<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableSet(
			SetUtil.fromArray(
				new ItemSelectorReturnType[] {
					new URLItemSelectorReturnType()
				}));

	private ServletContext _servletContext;

}