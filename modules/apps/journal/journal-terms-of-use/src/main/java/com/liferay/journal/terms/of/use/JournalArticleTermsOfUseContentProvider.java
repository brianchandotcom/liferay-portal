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

package com.liferay.journal.terms.of.use;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.util.TermsOfUseContentProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.journal.model.JournalArticle"},
	service = TermsOfUseContentProvider.class
)
public class JournalArticleTermsOfUseContentProvider
	implements TermsOfUseContentProvider {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public void includeConfig(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(_JSP_PATH_CONFIGURATION);

		requestDispatcher.include(request, response);
	}

	@Override
	public void includeView(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(_JSP_PATH_VIEW);

		requestDispatcher.include(request, response);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.terms.of.use)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final String _JSP_PATH_CONFIGURATION = "/configuration.jsp";

	private static final String _JSP_PATH_VIEW = "/view.jsp";

	private volatile ServletContext _servletContext;

}