/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.inactive.request.handler.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.inactive.request.handler.configuration.InactiveRequestHandlerConfiguration;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.InactiveRequestHandler;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.net.URL;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	configurationPid = "com.liferay.portal.inactive.request.handler.configuration.InactiveRequestHandlerConfiguration",
	service = InactiveRequestHandler.class
)
public class InactiveRequestHandlerImpl implements InactiveRequestHandler {

	@Override
	public boolean isShowInactiveRequestMessage() {
		return _showInactiveRequestMessage;
	}

	@Override
	public void processInactiveRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String messageKey)
		throws IOException {

		httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

		if (!_showInactiveRequestMessage) {
			return;
		}

		httpServletResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);

		PrintWriter printWriter = httpServletResponse.getWriter();

		String message = _language.get(
			_portal.getLocale(httpServletRequest), messageKey,
			StringPool.BLANK);

		if (Validator.isNull(message)) {
			message = HtmlUtil.escape(messageKey);
		}

		String html = StringUtil.replace(_content, "[$MESSAGE$]", message);

		printWriter.print(html);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		modified(properties);

		Bundle bundle = bundleContext.getBundle();

		URL url = bundle.getResource(_INACTIVE_HTML_FILE_NAME);

		if (url == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to load " + _INACTIVE_HTML_FILE_NAME);
			}

			return;
		}

		try (InputStream inputStream = url.openStream()) {
			_content = StringUtil.read(inputStream);
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to read " + _INACTIVE_HTML_FILE_NAME, ioException);
			}
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		InactiveRequestHandlerConfiguration
			inactiveRequestHandlerConfiguration =
				ConfigurableUtil.createConfigurable(
					InactiveRequestHandlerConfiguration.class, properties);

		_showInactiveRequestMessage =
			inactiveRequestHandlerConfiguration.showInactiveRequestMessage();
	}

	private static final String _INACTIVE_HTML_FILE_NAME =
		"com/liferay/portal/dependencies/inactive.html";

	private static final Log _log = LogFactoryUtil.getLog(
		InactiveRequestHandlerImpl.class);

	private String _content = StringPool.BLANK;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private volatile boolean _showInactiveRequestMessage;

}