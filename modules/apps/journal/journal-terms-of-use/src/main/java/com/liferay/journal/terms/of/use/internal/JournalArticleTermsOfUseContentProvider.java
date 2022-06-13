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

package com.liferay.journal.terms.of.use.internal;

import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.terms.of.use.internal.constants.JournalArticleTermsOfUseWebConstants;
import com.liferay.journal.terms.of.use.internal.display.context.JournalArticleTermsOfUseDisplayContext;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.terms.of.use.TermsOfUseContentProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.journal.configuration.JournalServiceConfiguration",
	immediate = true, service = TermsOfUseContentProvider.class
)
public class JournalArticleTermsOfUseContentProvider
	implements TermsOfUseContentProvider {

	@Override
	public void includeConfig(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_includeJSPPath(
			httpServletRequest, httpServletResponse, _JSP_PATH_CONFIGURATION);
	}

	@Override
	public void includeView(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_includeJSPPath(
			httpServletRequest, httpServletResponse, _JSP_PATH_VIEW);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.terms.of.use)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private void _includeJSPPath(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String jspPath)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(jspPath);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JournalServiceConfiguration journalServiceConfiguration =
			_configurationProvider.getCompanyConfiguration(
				JournalServiceConfiguration.class, themeDisplay.getCompanyId());

		httpServletRequest.setAttribute(
			JournalArticleTermsOfUseWebConstants.
				JOURNAL_ARTICLE_TERMS_OF_USE_DISPLAY_CONTEXT,
			new JournalArticleTermsOfUseDisplayContext(
				journalServiceConfiguration, themeDisplay));

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	private static final String _JSP_PATH_CONFIGURATION = "/configuration.jsp";

	private static final String _JSP_PATH_VIEW = "/view.jsp";

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JournalServiceConfiguration _journalServiceConfiguration;

	private ServletContext _servletContext;

	@Reference
	private SettingsFactory _settingsFactory;

}