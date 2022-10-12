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

package com.liferay.commerce.product.subscription.type.web.internal;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.subscription.type.web.internal.display.context.WeeklyCPSubscriptionTypeDisplayContext;
import com.liferay.commerce.product.util.CPSubscriptionTypeJSPContributor;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.product.subscription.type.jsp.contributor.key=" + CPConstants.WEEKLY_SUBSCRIPTION_TYPE,
	service = CPSubscriptionTypeJSPContributor.class
)
public class WeeklyCPSubscriptionTypeJSPContributor
	implements CPSubscriptionTypeJSPContributor {

	@Override
	public void render(
			Object object, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		render(object, httpServletRequest, httpServletResponse, true);
	}

	@Override
	public void render(
			Object object, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, boolean payment)
		throws Exception {

		WeeklyCPSubscriptionTypeDisplayContext
			weeklyCPSubscriptionTypeDisplayContext =
				new WeeklyCPSubscriptionTypeDisplayContext(
					object, httpServletRequest, payment);

		httpServletRequest.setAttribute(
			"view.jsp-weeklyCPSubscriptionTypeDisplayContext",
			weeklyCPSubscriptionTypeDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/weekly/view.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.subscription.type.web)"
	)
	private ServletContext _servletContext;

}