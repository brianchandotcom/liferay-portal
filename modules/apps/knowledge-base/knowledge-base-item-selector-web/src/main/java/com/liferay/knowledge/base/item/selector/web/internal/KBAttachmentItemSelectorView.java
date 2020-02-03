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

package com.liferay.knowledge.base.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.knowledge.base.item.selector.criterion.KBAttachmentItemSelectorCriterion;
import com.liferay.knowledge.base.item.selector.web.internal.constants.KBItemSelectorWebKeys;
import com.liferay.knowledge.base.item.selector.web.internal.display.context.KBAttachmentItemSelectorViewDisplayContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
 * @author Roberto Díaz
 */
@Component(service = ItemSelectorView.class)
public class KBAttachmentItemSelectorView
	implements ItemSelectorView<KBAttachmentItemSelectorCriterion> {

	@Override
	public Class<KBAttachmentItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return KBAttachmentItemSelectorCriterion.class;
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
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, KBAttachmentItemSelectorView.class);

		return ResourceBundleUtil.getString(
			resourceBundle, "article-attachments");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			KBAttachmentItemSelectorCriterion kbAttachmentItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		KBAttachmentItemSelectorViewDisplayContext
			kbAttachmentItemSelectorViewDisplayContext =
				new KBAttachmentItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest,
					kbAttachmentItemSelectorCriterion, this,
					_itemSelectorReturnTypeResolverHandler,
					itemSelectedEventName, search, portletURL);

		servletRequest.setAttribute(
			KBItemSelectorWebKeys.
				KB_ATTACHMENT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			kbAttachmentItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/kb_article_attachments.jsp");

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
		target = "(osgi.web.symbolicname=com.liferay.knowledge.base.item.selector.web)",
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
	private ServletContext _servletContext;

}