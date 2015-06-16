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

package com.liferay.journal.web.portlet.action;

import com.liferay.journal.web.constants.JournalPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.journal.ArticleIdException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"mvc.command.name=copyArticle",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL
	},
	service = ActionCommand.class
)
public class CopyArticleActionCommand extends BaseActionCommand {

	protected void copyArticle(PortletRequest portletRequest) throws Exception {
		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String oldArticleId = ParamUtil.getString(
			portletRequest, "oldArticleId");
		String newArticleId = ParamUtil.getString(
			portletRequest, "newArticleId");
		boolean autoArticleId = ParamUtil.getBoolean(
			portletRequest, "autoArticleId");
		double version = ParamUtil.getDouble(portletRequest, "version");

		JournalArticleServiceUtil.copyArticle(
			groupId, oldArticleId, newArticleId, autoArticleId, version);
	}

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		try {
			copyArticle(portletRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(portletRequest, e.getClass());

				PortletSession portletSession =
					portletRequest.getPortletSession();

				PortletContext portletContext =
					portletSession.getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/error.jsp");

				portletRequestDispatcher.include(
					portletRequest, portletResponse);
			}
			else if (e instanceof DuplicateArticleIdException ||
					 e instanceof ArticleIdException) {

				SessionErrors.add(portletRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

}