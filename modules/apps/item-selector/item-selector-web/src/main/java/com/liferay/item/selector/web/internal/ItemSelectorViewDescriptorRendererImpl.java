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

package com.liferay.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.web.internal.display.context.ItemSelectorViewDescriptorRendererDisplayContext;

import java.io.IOException;

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
 * @author Alejandro Tardín
 */
@Component(service = ItemSelectorViewDescriptorRenderer.class)
public class ItemSelectorViewDescriptorRendererImpl<T>
	implements ItemSelectorViewDescriptorRenderer<T> {

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			T itemSelectorCriterion, PortletURL portletURL,
			String itemSelectedEventName, boolean search,
			ItemSelectorViewDescriptor itemSelectorViewDescriptor)
		throws IOException, ServletException {

		servletRequest.setAttribute(
			ItemSelectorViewDescriptorRendererDisplayContext.class.getName(),
			new ItemSelectorViewDescriptorRendererDisplayContext(
				(HttpServletRequest)servletRequest, itemSelectedEventName,
				itemSelectorViewDescriptor));

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/view_item_selector_view_descriptor.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.item.selector.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private ServletContext _servletContext;

}