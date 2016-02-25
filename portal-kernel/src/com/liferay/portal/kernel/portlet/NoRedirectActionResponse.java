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

package com.liferay.portal.kernel.portlet;

import javax.portlet.ActionResponse;
import javax.portlet.filter.ActionResponseWrapper;

/**
 * @author Brian Wing Shun Chan
 */
public class NoRedirectActionResponse extends ActionResponseWrapper {

	public NoRedirectActionResponse(ActionResponse actionResponse) {
		super(actionResponse);
	}

	public String getRedirectLocation() {
		return _redirectLocation;
	}

	@Override
	public void sendRedirect(String location) {

		// Disable send redirect

		_redirectLocation = location;
	}

	private String _redirectLocation;

}