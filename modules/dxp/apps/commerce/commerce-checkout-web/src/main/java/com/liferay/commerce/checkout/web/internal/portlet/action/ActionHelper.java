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

package com.liferay.commerce.checkout.web.internal.portlet.action;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.util.CommercePaymentHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public void startPayment(
			CommerceOrder commerceOrder, ActionRequest actionRequest,
			ActionResponse actionResponse, ServiceContext serviceContext)
		throws Exception {

		String output = _commercePaymentHelper.startPayment(
			commerceOrder, serviceContext);

		if (Validator.isHTML(output)) {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);

			ServletResponseUtil.write(httpServletResponse, output);

			httpServletResponse.flushBuffer();

			return;
		}

		String redirect = null;

		if (Validator.isUrl(output)) {
			redirect = output;
		}
		else {
			redirect = ParamUtil.getString(serviceContext, "redirect");
		}

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	@Reference
	private CommercePaymentHelper _commercePaymentHelper;

	@Reference
	private Portal _portal;

}