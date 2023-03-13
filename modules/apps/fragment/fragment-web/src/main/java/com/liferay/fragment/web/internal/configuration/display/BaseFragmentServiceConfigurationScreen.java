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

package com.liferay.fragment.web.internal.configuration.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.fragment.web.internal.configuration.admin.service.FragmentServiceManagedServiceFactory;
import com.liferay.fragment.web.internal.display.context.FragmentServiceConfigurationDisplayContext;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.Locale;

import javax.portlet.PortletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseFragmentServiceConfigurationScreen
	implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "page-fragments";
	}

	@Override
	public String getKey() {
		return "fragments-service-" + getScope();
	}

	@Override
	public String getName(Locale locale) {
		return language.get(locale, "fragment-configuration-name");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			httpServletRequest.setAttribute(
				FragmentServiceConfigurationDisplayContext.class.getName(),
				new FragmentServiceConfigurationDisplayContext(
					httpServletRequest,
					portal.getLiferayPortletResponse(
						(PortletResponse)httpServletRequest.getAttribute(
							JavaConstants.JAVAX_PORTLET_RESPONSE)),
					fragmentServiceManagedServiceFactory, getScope()));

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(
					"/fragment_service_configuration.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render fragment_service_configuration.jsp",
				exception);
		}
	}

	@Reference
	protected FragmentServiceManagedServiceFactory
		fragmentServiceManagedServiceFactory;

	@Reference
	protected Language language;

	@Reference
	protected Portal portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.web)",
		unbind = "-"
	)
	protected ServletContext servletContext;

}