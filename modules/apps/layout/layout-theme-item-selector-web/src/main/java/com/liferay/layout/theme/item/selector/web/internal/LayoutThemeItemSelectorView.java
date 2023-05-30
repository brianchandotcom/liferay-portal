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

package com.liferay.layout.theme.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.theme.item.selector.criterion.LayoutThemeItemSelectorCriterion;
import com.liferay.layout.theme.item.selector.web.internal.display.context.LayoutThemeItemSelectorDisplayContext;
import com.liferay.layout.theme.item.selector.web.internal.item.selector.LayoutThemeItemSelectorViewDescriptor;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.JavaConstants;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefan Tanasie
 */
@Component(service = ItemSelectorView.class)
public class LayoutThemeItemSelectorView
	implements ItemSelectorView<LayoutThemeItemSelectorCriterion> {

	@Override
	public Class<LayoutThemeItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return LayoutThemeItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "available-themes");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			LayoutThemeItemSelectorCriterion layoutThemeItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)servletRequest;

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		LayoutThemeItemSelectorDisplayContext
			layoutThemeItemSelectorDisplayContext =
				new LayoutThemeItemSelectorDisplayContext(
					httpServletRequest, renderRequest, portletURL);

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, layoutThemeItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new LayoutThemeItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest,
				layoutThemeItemSelectorDisplayContext));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private ItemSelectorViewDescriptorRenderer<LayoutThemeItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

}