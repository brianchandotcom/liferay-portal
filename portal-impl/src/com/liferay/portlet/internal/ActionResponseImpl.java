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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.portlet.LiferayActionResponse;

import java.io.IOException;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderURL;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class ActionResponseImpl
	extends StateAwareResponseImpl implements LiferayActionResponse {

	@Override
	public RenderURL createRedirectURL(MimeResponse.Copy copy) {
		return createRenderURL(copy);
	}

	@Override
	public String getLifecycle() {
		return PortletRequest.ACTION_PHASE;
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		if ((location == null) ||
			(!location.startsWith("/") && !location.contains("://"))) {

			throw new IllegalArgumentException(
				location + " is not a valid redirect");
		}

		if (isCalledSetRenderParameter()) {
			throw new IllegalStateException(
				"Set render parameter has already been called");
		}

		setRedirectLocation(location);
	}

	@Override
	public void sendRedirect(String location, String renderUrlParamName)
		throws IOException {
	}

}