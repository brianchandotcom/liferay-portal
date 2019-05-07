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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class SampleServicePreAction extends Action {

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		setSharedSessionAttributes(httpServletRequest);
	}

	public void setSharedSessionAttributes(
		HttpServletRequest httpServletRequest) {

		// Modify portal.properties property "session.shared.attributes". Make
		// sure that "TEST_SHARED_" is also one of the prefixed attributes that
		// will be shared across all portlets.

		HttpSession session = httpServletRequest.getSession();

		session.setAttribute("TEST_SHARED_HELLO", "world");
	}

}