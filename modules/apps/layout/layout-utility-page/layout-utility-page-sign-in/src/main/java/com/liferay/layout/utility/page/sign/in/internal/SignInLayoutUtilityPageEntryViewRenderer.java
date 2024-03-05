/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.sign.in.internal;

import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRenderer;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.portal.kernel.language.Language;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Olivér Kecskeméty
 */
public class SignInLayoutUtilityPageEntryViewRenderer
	implements LayoutUtilityPageEntryViewRenderer {

	public SignInLayoutUtilityPageEntryViewRenderer(
		Language language, ServletContext servletContext) {

		_language = language;
		_servletContext = servletContext;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "sign-in");
	}

	@Override
	public String getType() {
		return LayoutUtilityPageEntryConstants.TYPE_LOGIN;
	}

	@Override
	public void renderHTML(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/sign_in.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	private final Language _language;
	private final ServletContext _servletContext;

}