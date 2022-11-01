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

package com.liferay.asset.display.page.item.selector.web.internal;

import com.liferay.asset.display.page.item.selector.criterion.AssetDisplayPageSelectorCriterion;
import com.liferay.asset.display.page.item.selector.web.internal.constants.AssetDisplayPageItemSelectorWebKeys;
import com.liferay.asset.display.page.item.selector.web.internal.display.context.AssetDisplayPagesItemSelectorViewDisplayContext;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.language.Language;

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
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = ItemSelectorView.class)
public class AssetDisplayPagesItemSelectorView
	implements ItemSelectorView<AssetDisplayPageSelectorCriterion> {

	@Override
	public Class<AssetDisplayPageSelectorCriterion>
		getItemSelectorCriterionClass() {

		return AssetDisplayPageSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "display-page-templates");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			AssetDisplayPageSelectorCriterion assetDisplayPageSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)servletRequest;

		AssetDisplayPagesItemSelectorViewDisplayContext
			assetDisplayPagesItemSelectorViewDisplayContext =
				new AssetDisplayPagesItemSelectorViewDisplayContext(
					httpServletRequest, assetDisplayPageSelectorCriterion,
					itemSelectedEventName, portletURL);

		servletRequest.setAttribute(
			AssetDisplayPageItemSelectorWebKeys.
				ASSET_DISPLAY_PAGES_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			assetDisplayPagesItemSelectorViewDisplayContext);

		servletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_SERVICE_TRACKER,
			_infoItemServiceTracker);

		ServletContext servletContext = _servletContext;

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/display_pages.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.display.page.item.selector.web)"
	)
	private ServletContext _servletContext;

}