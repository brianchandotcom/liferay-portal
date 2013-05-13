/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionResponseImpl
	extends StateAwareResponseImpl implements ActionResponse {

	@Override
	public String getLifecycle() {
		return PortletRequest.ACTION_PHASE;
	}

	@Override
	public void sendRedirect(String location) {
		if ((location == null) ||
			(!location.startsWith("/") && !location.contains("://") &&
			 !location.startsWith("wsrp_rewrite?"))) {

			throw new IllegalArgumentException(
				location + " is not a valid redirect");
		}

		// This is needed because app servers will try to prepend a host if they
		// see an invalid URL

		if (location.startsWith("wsrp_rewrite?")) {
			location = "http://wsrp-rewrite-holder?" + location;
		}

		if (isCalledSetRenderParameter()) {
			throw new IllegalStateException(
				"Set render parameter has already been called");
		}

		setRedirectLocation(location);
	}

	@Override
	public void sendRedirect(String location, String renderUrlParamName) {
	}

}