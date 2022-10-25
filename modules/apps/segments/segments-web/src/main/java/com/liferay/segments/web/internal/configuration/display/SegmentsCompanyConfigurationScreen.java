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

package com.liferay.segments.web.internal.configuration.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.web.internal.display.context.SegmentsCompanyConfigurationDisplayContext;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 * @author Stefan Tanasie
 */
@Component(immediate = true, service = ConfigurationScreen.class)
public class SegmentsCompanyConfigurationScreen implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "segments";
	}

	@Override
	public String getKey() {
		return "segments-service";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "segments-service-configuration-name");
	}

	@Override
	public String getScope() {
		return "company";
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/segments_configuration.jsp");

			httpServletRequest.setAttribute(
				SegmentsCompanyConfigurationDisplayContext.class.getName(),
				new SegmentsCompanyConfigurationDisplayContext(
					httpServletRequest, _portal,
					_segmentsConfigurationProvider));

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render /segments_configuration.jsp", exception);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsConfigurationProvider _segmentsConfigurationProvider;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.segments.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}