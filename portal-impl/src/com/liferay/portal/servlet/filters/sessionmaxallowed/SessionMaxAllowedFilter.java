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

package com.liferay.portal.servlet.filters.sessionmaxallowed;

import com.liferay.portal.kernel.servlet.TryFinallyFilter;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionMaxAllowedFilter
	extends BasePortalFilter implements TryFinallyFilter {

	@Override
	public void doFilterFinally(
		HttpServletRequest request, HttpServletResponse response,
		Object ojbect) {

		if (PropsValues.SESSION_MAX_ALLOWED > 0) {
			HttpSession session = request.getSession();

			Boolean sessionMaxAllowed = (Boolean)session.getAttribute(
				WebKeys.SESSION_MAX_ALLOWED);

			if ((sessionMaxAllowed != null) && sessionMaxAllowed) {
				session.invalidate();
			}
		}
	}

	@Override
	public Object doFilterTry(
		HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

}