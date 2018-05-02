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

import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.polls.service.PollsVoteService;
import com.liferay.polls.web.internal.portlet.util.PollsUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	property = {
		"javax.portlet.name=" + PollsPortletKeys.POLLS,
		"javax.portlet.name=" + PollsPortletKeys.POLLS_DISPLAY,
		"mvc.command.name=/polls_display/vote_question"
	},
	service = MVCActionCommand.class
)
public class VoteQuestionMVCActionCommand extends EditQuestionMVCActionCommand {

	@Reference(unbind = "-")
	protected void setPollsVoteService(PollsVoteService pollsVoteService) {
		_pollsVoteService = pollsVoteService;
	}

	@Override
	protected void updateQuestion(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		long questionId = ParamUtil.getLong(actionRequest, "questionId");
		long choiceId = ParamUtil.getLong(actionRequest, "choiceId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_pollsVoteService.addVote(questionId, choiceId, serviceContext);

		PollsUtil.saveVote(actionRequest, actionResponse, questionId);
	}

	private PollsVoteService _pollsVoteService;

}