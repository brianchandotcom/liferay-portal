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

package com.liferay.dynamic.data.mapping.form.builder.internal.servlet.base;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pedro Queiroz
 * @author Rafael Praxedes
 */
public class BaseDDMFormBuilderServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		createContext(request, response);

		super.service(request, response);
	}

	protected void createContext(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, request, response);
		}
		catch (ActionException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae, ae);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDDMFormBuilderServlet.class);

}