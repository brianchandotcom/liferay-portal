/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.doubleclick;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.TransientValue;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class DoubleClickFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		HttpSession session = request.getSession(false);

		if (session == null) {
			processFilter(
				DoubleClickFilter.class, request, response, filterChain);
		}
		else {
			DoubleClickController doubleClickController = null;

			synchronized (session) {
				TransientValue<DoubleClickController> transientValue =
					(TransientValue<DoubleClickController>)session.getAttribute(
						_CONTROLLER_KEY);

				if (transientValue != null) {
					doubleClickController = transientValue.getValue();
				}

				if (doubleClickController == null) {
					doubleClickController = new DoubleClickController();

					transientValue = new TransientValue<DoubleClickController>(
						doubleClickController);

					session.setAttribute(_CONTROLLER_KEY, transientValue);
				}
			}

			boolean ok = false;

			try {
				doubleClickController.control(request, response, filterChain);

				ok = true;
			}
			finally {
				if (stopWatch != null) {
					String completeURL = HttpUtil.getCompleteURL(request);

					if (ok) {
						_log.debug(
							"Double click prevention succeeded in " +
								stopWatch.getTime() + " ms for " + completeURL);
					}
					else {
						_log.debug(
							"Double click prevention failed in " +
								stopWatch.getTime() + " ms for " + completeURL);
					}
				}
			}
		}
	}

	private static final String _CONTROLLER_KEY =
		DoubleClickFilter.class.getName();

	private static Log _log = LogFactoryUtil.getLog(DoubleClickFilter.class);

}