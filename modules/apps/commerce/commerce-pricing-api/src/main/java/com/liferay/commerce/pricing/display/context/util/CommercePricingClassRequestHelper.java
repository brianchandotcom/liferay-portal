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

package com.liferay.commerce.pricing.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.util.JavaConstants;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassRequestHelper extends BaseRequestHelper {

	public CommercePricingClassRequestHelper(
		HttpServletRequest httpServletRequest) {

		super(httpServletRequest);

		Object portletRequest = httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest instanceof RenderRequest) {
			_renderRequest = (RenderRequest)portletRequest;
		}

		Object portletResponse = httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse instanceof RenderResponse) {
			_renderResponse = (RenderResponse)portletResponse;
		}
	}

	public RenderRequest getRenderRequest() {
		return _renderRequest;
	}

	public RenderResponse getRenderResponse() {
		return _renderResponse;
	}

	public void setRenderRequest(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public void setRenderResponse(RenderResponse renderResponse) {
		_renderResponse = renderResponse;
	}

	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;

}