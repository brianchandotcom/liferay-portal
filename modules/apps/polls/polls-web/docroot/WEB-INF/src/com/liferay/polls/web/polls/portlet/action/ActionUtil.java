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

package com.liferay.polls.web.polls.portlet.action;

import com.liferay.polls.constants.PollsWebKeys;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getQuestion(HttpServletRequest request)
		throws Exception {

		long questionId = ParamUtil.getLong(request, "questionId");

		PollsQuestion question = null;

		if (questionId > 0) {
			question = PollsQuestionServiceUtil.getQuestion(questionId);
		}

		request.setAttribute(PollsWebKeys.POLLS_QUESTION, question);
	}

	public static void getQuestion(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getQuestion(request);
	}

}