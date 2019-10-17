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

package com.liferay.item.selector.upload.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.item.selector.upload.web.internal.display.context.ItemSelectorUploadViewDisplayContext;
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
 * @author Ambrín Chaudhary
 */
@Component(
	property = "item.selector.view.key=upload", service = ItemSelectorView.class
)
public class ItemSelectorUploadView
	implements ItemSelectorView<UploadItemSelectorCriterion> {

	public static final String ITEM_SELECTOR_UPLOAD_VIEW_DISPLAY_CONTEXT =
		"ITEM_SELECTOR_UPLOAD_VIEW_DISPLAY_CONTEXT";

	@Override
	public Class<UploadItemSelectorCriterion> getItemSelectorCriterionClass() {
		return UploadItemSelectorCriterion.class;
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
		return LanguageUtil.get(locale, "upload-image");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			UploadItemSelectorCriterion uploadItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/upload.jsp");

		ItemSelectorUploadViewDisplayContext
			itemSelectorUploadViewDisplayContext =
				new ItemSelectorUploadViewDisplayContext(
					uploadItemSelectorCriterion, this, itemSelectedEventName,
					_itemSelectorReturnTypeResolverHandler);

		servletRequest.setAttribute(
			ITEM_SELECTOR_UPLOAD_VIEW_DISPLAY_CONTEXT,
			itemSelectorUploadViewDisplayContext);

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.item.selector.upload.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new DownloadFileEntryItemSelectorReturnType(),
				new FileEntryItemSelectorReturnType()));

	@Reference
	private ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;

	private ServletContext _servletContext;

}