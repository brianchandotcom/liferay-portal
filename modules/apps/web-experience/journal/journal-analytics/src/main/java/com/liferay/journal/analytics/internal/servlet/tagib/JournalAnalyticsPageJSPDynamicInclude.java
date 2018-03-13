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

package com.liferay.journal.analytics.internal.servlet.tagib;

import com.liferay.journal.analytics.internal.contants.JournalWebKeys;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = DynamicInclude.class)
public class JournalAnalyticsPageDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		JournalArticleDisplay articleDisplay =
			(JournalArticleDisplay)request.getAttribute(
				"liferay-journal:journal-article:articleDisplay");

		if (articleDisplay == null) {
			return;
		}

		request.setAttribute(
			JournalWebKeys.JOURNAL_ARTICLE_ID, articleDisplay.getArticleId());

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(_JSP_PATH);

		try {
			requestDispatcher.include(request, response);
		}
		catch (ServletException se) {
			_log.error("Unable to include JSP " + _JSP_PATH, se);

			throw new IOException("Unable to include JSP " + _JSP_PATH, se);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.journal.taglib#/journal_article/page.jsp#post");
	}

	private static final String _JSP_PATH =
		"/com.liferay.journal.analytics/view.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalAnalyticsPageDynamicInclude.class);

	@Reference(target = "(osgi.web.symbolicname=com.liferay.journal.analytics)")
	private ServletContext _servletContext;

}