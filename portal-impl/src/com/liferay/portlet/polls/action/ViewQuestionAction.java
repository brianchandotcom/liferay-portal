/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.polls.service.PollsVoteServiceUtil;
import com.liferay.portlet.polls.util.PollsUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewQuestionAction extends EditQuestionAction {

	@Override
	protected void updateQuestion(
			PortletConfig portletConfig, ActionRequest actionRequest)
		throws Exception {

		long questionId = ParamUtil.getLong(actionRequest, "questionId");
		long choiceId = ParamUtil.getLong(actionRequest, "choiceId");

		PollsVoteServiceUtil.addVote(
			questionId, choiceId, new ServiceContext());

		PollsUtil.saveVote(actionRequest, questionId);
	}

}