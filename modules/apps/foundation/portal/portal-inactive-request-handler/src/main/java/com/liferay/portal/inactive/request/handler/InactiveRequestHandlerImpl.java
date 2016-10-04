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

package com.liferay.portal.inactive.request.handler;

import com.liferay.petra.content.ContentUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.inactive.request.handler.configuration.InactiveRequestHandlerConfiguration;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.InactiveRequestHandler;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	configurationPid = "com.liferay.portal.inactive.request.handler.configuration.InactiveRequestHandlerConfiguration",
	immediate = true, service = InactiveRequestHandler.class
)
public class InactiveRequestHandlerImpl implements InactiveRequestHandler {

	@Override
	public void processInactiveRequest(
			HttpServletRequest request, HttpServletResponse response,
			String messageKey)
		throws IOException {

		response.setStatus(HttpServletResponse.SC_NOT_FOUND);

		PrintWriter printWriter = response.getWriter();

		if (!_inactiveRequestHandlerConfiguration.
				showInactiveRequestMessage()) {

			printWriter.print(StringPool.BLANK);

			return;
		}

		response.setContentType(ContentTypes.TEXT_HTML_UTF8);

		Locale locale = PortalUtil.getLocale(request);

		String message = null;

		if (LanguageUtil.isValidLanguageKey(locale, messageKey)) {
			message = LanguageUtil.get(locale, messageKey);
		}
		else {
			message = HtmlUtil.escape(messageKey);
		}

		String html = ContentUtil.get(
			InactiveRequestHandlerImpl.class.getClassLoader(),
			"com/liferay/portal/dependencies/inactive.html");

		html = StringUtil.replace(html, "[$MESSAGE$]", message);

		printWriter.print(html);
	};

	@Activate
	protected void activate(Map<String, Object> properties) {
		_inactiveRequestHandlerConfiguration =
			ConfigurableUtil.createConfigurable(
				InactiveRequestHandlerConfiguration.class, properties);
	}

	private InactiveRequestHandlerConfiguration
		_inactiveRequestHandlerConfiguration;

}