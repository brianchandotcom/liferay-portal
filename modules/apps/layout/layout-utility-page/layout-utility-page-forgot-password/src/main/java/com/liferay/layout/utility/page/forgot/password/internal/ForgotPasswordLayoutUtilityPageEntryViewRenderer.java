/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.forgot.password.internal;

import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRenderer;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.portal.kernel.language.Language;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alvaro Saugar
 */
public class ForgotPasswordLayoutUtilityPageEntryViewRenderer
	implements LayoutUtilityPageEntryViewRenderer {

	public ForgotPasswordLayoutUtilityPageEntryViewRenderer(
		Language language, ServletContext servletContext) {

		_language = language;
		_servletContext = servletContext;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "forgot-password");
	}

	@Override
	public String getType() {
		return LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD;
	}

	@Override
	public void renderHTML(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {
	}

	private final Language _language;
	private final ServletContext _servletContext;

}