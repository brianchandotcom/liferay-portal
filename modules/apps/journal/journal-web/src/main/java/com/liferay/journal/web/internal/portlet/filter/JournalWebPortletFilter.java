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

package com.liferay.journal.web.internal.portlet.filter;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Couso
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = PortletFilter.class
)
public class JournalWebPortletFilter implements RenderFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		String path = renderRequest.getParameter("mvcPath");

		if (Objects.equals(path, "/edit_article.jsp") ||
			Objects.equals(path, "/view_article_history.jsp")) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(
				renderRequest, "groupId", themeDisplay.getScopeGroupId());

			String articleId = ParamUtil.getString(renderRequest, "articleId");
			int status = WorkflowConstants.STATUS_ANY;

			try {
				_journalArticleService.getLatestArticle(
					groupId, articleId, status);
			}
			catch (PrincipalException.MustHavePermission e) {
				_log.error(e, e);

				SessionErrors.add(renderRequest, e.getClass());
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		filterChain.doFilter(renderRequest, renderResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalWebPortletFilter.class);

	@Reference
	private JournalArticleService _journalArticleService;

}