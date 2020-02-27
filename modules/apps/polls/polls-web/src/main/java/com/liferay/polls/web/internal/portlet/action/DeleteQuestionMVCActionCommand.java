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
import com.liferay.polls.service.PollsQuestionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PollsPortletKeys.POLLS,
		"javax.portlet.name=" + PollsPortletKeys.POLLS_DISPLAY,
		"mvc.command.name=/polls/deleteQuestion"
	},
	service = MVCActionCommand.class
)
public class DeleteQuestionMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	protected void doDeleteQuestion(long questionId) throws PortalException {
		_pollsQuestionService.deleteQuestion(questionId);
	}

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteQuestionIds = null;

		long questionId = ParamUtil.getLong(actionRequest, "questionId");

		if (questionId > 0) {
			deleteQuestionIds = new long[] {questionId};
		}
		else {
			deleteQuestionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteQuestionIds"), 0L);
		}

		for (long deleteQuestionId : deleteQuestionIds) {
			doDeleteQuestion(deleteQuestionId);
		}
	}

	@Reference(unbind = "-")
	protected void setPollsQuestionService(
		PollsQuestionService pollsQuestionService) {

		_pollsQuestionService = pollsQuestionService;
	}

	private PollsQuestionService _pollsQuestionService;

}