/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.checkout.web.internal.portlet.action;

import com.liferay.commerce.service.CommerceOrderService;
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
			long commerceOrderId, ActionRequest actionRequest,
			ActionResponse actionResponse, ServiceContext serviceContext)
		throws Exception {

		String output = _commerceOrderService.startCommerceOrderPayment(
			commerceOrderId, serviceContext);

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
	private CommerceOrderService _commerceOrderService;

	@Reference
	private Portal _portal;

}