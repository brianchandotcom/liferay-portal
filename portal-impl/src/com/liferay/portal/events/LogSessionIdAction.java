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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class LogSessionIdAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Session id " + session.getId() + " is " +
					(!session.isNew() ? "not " : "") + "new");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LogSessionIdAction.class);

}