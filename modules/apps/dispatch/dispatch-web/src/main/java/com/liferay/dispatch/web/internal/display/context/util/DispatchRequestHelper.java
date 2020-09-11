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

package com.liferay.dispatch.web.internal.display.context.util;

import com.liferay.dispatch.constants.DispatchWebKeys;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guywandji
 */
public class DispatchRequestHelper extends BaseRequestHelper {

	public DispatchRequestHelper(RenderRequest renderRequest) {
		super(PortalUtil.getHttpServletRequest(renderRequest));
	}

	public DispatchTrigger getDispatchTrigger() {
		HttpServletRequest httpServletRequest = getRequest();

		return (DispatchTrigger)httpServletRequest.getAttribute(
			DispatchWebKeys.DISPATCH_TRIGGER);
	}

}