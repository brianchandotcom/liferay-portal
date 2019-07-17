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

package com.liferay.polls.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.polls.constants.PollsWebKeys;
import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.service.PollsQuestionService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	property = {
		"javax.portlet.name=" + PollsPortletKeys.POLLS_DISPLAY,
		"mvc.command.name=/polls_display/view"
	},
	service = MVCRenderCommand.class
)
public class ViewActionMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			PortletPreferences portletPreferences =
				renderRequest.getPreferences();

			long questionId = GetterUtil.getLong(
				portletPreferences.getValue("questionId", StringPool.BLANK));

			if (questionId > 0) {
				renderRequest.setAttribute(
					PollsWebKeys.POLLS_QUESTION,
					_pollsQuestionService.getQuestion(questionId));
			}
		}
		catch (Exception e) {
			if (!(e instanceof NoSuchQuestionException)) {
				SessionErrors.add(renderRequest, e.getClass());

				return "/polls_display/error.jsp";
			}
		}

		return "/polls_display/view.jsp";
	}

	@Reference(unbind = "-")
	protected void setPollsQuestionService(
		PollsQuestionService pollsQuestionService) {

		_pollsQuestionService = pollsQuestionService;
	}

	private PollsQuestionService _pollsQuestionService;

}