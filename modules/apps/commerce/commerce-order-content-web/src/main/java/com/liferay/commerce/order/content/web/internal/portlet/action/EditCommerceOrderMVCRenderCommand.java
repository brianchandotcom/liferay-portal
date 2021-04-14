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

package com.liferay.commerce.order.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.content.web.internal.display.context.CommerceOrderContentDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT,
		"mvc.command.name=/commerce_open_order_content/edit_commerce_order"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceOrderMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			CommerceOrderContentDisplayContext
				commerceOrderContentDisplayContext =
					(CommerceOrderContentDisplayContext)
						renderRequest.getAttribute(
							WebKeys.PORTLET_DISPLAY_CONTEXT);

			CommerceOrder commerceOrder =
				commerceOrderContentDisplayContext.getCommerceOrder();

			if ((commerceOrder != null) && commerceOrder.isOpen()) {
				_commerceOrderHttpHelper.setCurrentCommerceOrder(
					_portal.getHttpServletRequest(renderRequest),
					commerceOrder);
			}

			return "/pending_orders/edit_commerce_order.jsp";
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchOrderException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(renderRequest, exception.getClass());

				return "/error.jsp";
			}

			throw new PortletException(exception);
		}
	}

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private Portal _portal;

}