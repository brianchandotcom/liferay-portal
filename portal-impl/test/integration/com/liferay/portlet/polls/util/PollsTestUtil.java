/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.util;

import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;

/**
 * @author Shinn Lok
 */
public class PollsTestUtil {

	public static PollsChoice addChoice(long groupId, long questionId)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(TestPropsValues.getUserId());

		PollsChoice choice = PollsChoiceLocalServiceUtil.createPollsChoice(
			ServiceTestUtil.randomLong());

		choice.setUuid(ServiceTestUtil.randomString());
		choice.setGroupId(groupId);
		choice.setCompanyId(user.getCompanyId());
		choice.setUserId(user.getUserId());
		choice.setUserName(user.getFullName());
		choice.setCreateDate(ServiceTestUtil.newDate());
		choice.setModifiedDate(ServiceTestUtil.newDate());
		choice.setQuestionId(questionId);
		choice.setName(ServiceTestUtil.randomString());
		choice.setDescription(ServiceTestUtil.randomString());

		PollsChoiceLocalServiceUtil.updatePollsChoice(choice);

		return choice;
	}

	public static PollsQuestion addQuestion(long groupId) throws Exception {
		return PollsQuestionLocalServiceUtil.addQuestion(
			TestPropsValues.getUserId(),
			ServiceTestUtil.randomLocaleStringMap(),
			ServiceTestUtil.randomLocaleStringMap(), 0, 0, 0, 0, 0, true, null,
			ServiceTestUtil.getServiceContext(groupId));
	}

	public static PollsVote addVote(
			long groupId, long questionId, long choiceId)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(TestPropsValues.getUserId());

		PollsVote vote = PollsVoteLocalServiceUtil.createPollsVote(
			ServiceTestUtil.randomLong());

		vote.setUuid(ServiceTestUtil.randomString());
		vote.setGroupId(groupId);
		vote.setCompanyId(user.getCompanyId());
		vote.setUserId(user.getUserId());
		vote.setUserName(user.getFullName());
		vote.setCreateDate(ServiceTestUtil.newDate());
		vote.setModifiedDate(ServiceTestUtil.newDate());
		vote.setQuestionId(questionId);
		vote.setChoiceId(choiceId);
		vote.setVoteDate(ServiceTestUtil.newDate());

		PollsVoteLocalServiceUtil.updatePollsVote(vote);

		return vote;
	}

}