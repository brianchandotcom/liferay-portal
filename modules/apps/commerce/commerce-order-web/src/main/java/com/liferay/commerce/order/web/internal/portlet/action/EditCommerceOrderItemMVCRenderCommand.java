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

package com.liferay.commerce.order.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.order.web.internal.display.context.CommerceOrderEditDisplayContext;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER,
		"mvc.command.name=/commerce_order/edit_commerce_order_item"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceOrderItemMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			CommerceOrderEditDisplayContext commerceOrderEditDisplayContext =
				new CommerceOrderEditDisplayContext(
					_commerceChannelLocalService,
					_commerceNotificationQueueEntryLocalService,
					_commerceOrderEngine, _commerceOrderItemService,
					_commerceOrderNoteService, _commerceOrderService,
					_commerceOrderStatusRegistry,
					_commercePaymentMethodGroupRelLocalService,
					_commerceShipmentService, renderRequest);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceOrderEditDisplayContext);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchOrderException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(renderRequest, exception.getClass());

				return "/error.jsp";
			}

			throw new PortletException(exception);
		}

		return "/edit_commerce_order_item.jsp";
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private CommerceShipmentService _commerceShipmentService;

}