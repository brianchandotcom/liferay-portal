/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.inactive.request.handler.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.inactive.request.handler.configuration.InactiveRequestHandlerConfiguration;
import com.liferay.portal.kernel.servlet.InactiveRequestHandler;

import java.io.IOException;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		render(httpServletRequest, httpServletResponse, messageKey);
	}

	public void render(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String messageKey) {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/inactive.jsp");

			httpServletRequest.setAttribute("MESSAGE", messageKey);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		modified(properties);
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

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.inactive.request.handler)"
	)
	private ServletContext _servletContext;

	private volatile boolean _showInactiveRequestMessage;

}