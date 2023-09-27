/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.web.internal.display.context.ItemSelectorViewDescriptorRendererDisplayContext;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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
			ItemSelectorViewDescriptor<?> itemSelectorViewDescriptor)
		throws IOException, ServletException {

		PortletRequest portletRequest =
			(PortletRequest)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		servletRequest.setAttribute(
			ItemSelectorViewDescriptorRendererDisplayContext.class.getName(),
			new ItemSelectorViewDescriptorRendererDisplayContext(
				(HttpServletRequest)servletRequest, itemSelectedEventName,
				(ItemSelectorViewDescriptor<Object>)itemSelectorViewDescriptor,
				_portal.getLiferayPortletRequest(portletRequest),
				_portal.getLiferayPortletResponse(portletResponse)));

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/view_item_selector_view_descriptor.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.item.selector.web)")
	private ServletContext _servletContext;

}