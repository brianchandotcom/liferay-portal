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

package com.liferay.commerce.notification.web.internal.portlet.action;

import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateService;
import com.liferay.commerce.notification.type.CommerceNotificationTypeRegistry;
import com.liferay.commerce.notification.web.internal.display.context.CommerceNotificationTemplatesDisplayContext;
import com.liferay.commerce.order.CommerceDefinitionTermContributorRegistry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_CHANNELS,
		"mvc.command.name=/commerce_channels/edit_commerce_notification_template"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceNotificationTemplateMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/edit_commerce_notification_template.jsp");

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			CommerceNotificationTemplatesDisplayContext
				commerceNotificationTemplatesDisplayContext =
					new CommerceNotificationTemplatesDisplayContext(
						_commerceChannelLocalService,
						_commerceDefinitionTermContributorRegistry,
						_commerceNotificationTemplateService,
						_commerceNotificationTypeRegistry, httpServletRequest);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceNotificationTemplatesDisplayContext);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchNotificationTemplateException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(renderRequest, exception.getClass());

				return "/error.jsp";
			}

			throw new PortletException(
				"Unable to include edit_commerce_notification_template.jsp",
				exception);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceDefinitionTermContributorRegistry
		_commerceDefinitionTermContributorRegistry;

	@Reference
	private CommerceNotificationTemplateService
		_commerceNotificationTemplateService;

	@Reference
	private CommerceNotificationTypeRegistry _commerceNotificationTypeRegistry;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.notification.web)"
	)
	private ServletContext _servletContext;

}