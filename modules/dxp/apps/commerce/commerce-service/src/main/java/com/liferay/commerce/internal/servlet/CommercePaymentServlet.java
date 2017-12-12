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

package com.liferay.commerce.internal.servlet;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommercePaymentHelper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/" + CommerceConstants.PAYMENT_SERVLET_PATH,
		"osgi.http.whiteboard.servlet.name=com.liferay.commerce.internal.servlet.CommercePaymentServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + CommerceConstants.PAYMENT_SERVLET_PATH + "/*"
	},
	service = Servlet.class
)
public class CommercePaymentServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			HttpSession httpSession = httpServletRequest.getSession();

			if (PortalSessionThreadLocal.getHttpSession() == null) {
				PortalSessionThreadLocal.setHttpSession(httpSession);
			}

			User user = _portal.getUser(httpServletRequest);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			boolean cancel = ParamUtil.getBoolean(httpServletRequest, "cancel");
			String redirect = ParamUtil.getString(
				httpServletRequest, "redirect");

			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
			String uuid = ParamUtil.getString(httpServletRequest, "uuid");

			CommerceOrder commerceOrder =
				_commerceOrderService.getCommerceOrderByUuidAndGroupId(
					uuid, groupId);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			if (cancel) {
				_commercePaymentHelper.cancelPayment(
					commerceOrder, serviceContext);
			}
			else {
				_commercePaymentHelper.completePayment(
					commerceOrder, serviceContext);
			}

			httpServletResponse.sendRedirect(redirect);
		}
		catch (Exception e) {
			_portal.sendError(e, httpServletRequest, httpServletResponse);
		}
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentHelper _commercePaymentHelper;

	@Reference
	private Portal _portal;

}