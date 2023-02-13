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

package com.liferay.document.library.preview.pdf.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.kernel.language.Language;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
public abstract class BasePDFPreviewConfigurationScreen
	implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "documents-and-media";
	}

	@Override
	public String getKey() {
		return "pdf-preview-configuration-" + getScope();
	}

	@Override
	public String getName(Locale locale) {
		return language.get(locale, "pdf-preview-configuration-name");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(
					"/document_library_preview_pdf_settings" +
						"/pdf_preview_limit.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render pdf_preview_limit.jsp", exception);
		}
	}

	@Reference
	protected Language language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.pdf)"
	)
	protected ServletContext servletContext;

}