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

package com.liferay.portlet.polls.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.polls.DuplicateVoteException;
import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.QuestionExpiredException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.base.PollsVoteLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class PollsVoteLocalServiceImpl extends PollsVoteLocalServiceBaseImpl {

	@Override
	public PollsVote addVote(
			long userId, long questionId, long choiceId,
			ServiceContext serviceContext)
		throws PortalException {

		// Choice

		Date now = new Date();

		PollsChoice choice = pollsChoicePersistence.findByPrimaryKey(choiceId);

		if (choice.getQuestionId() != questionId) {
			throw new NoSuchQuestionException(
				"{questionId=" + questionId + "}");
		}

		// Question

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		if (question.isExpired(serviceContext, now)) {
			throw new QuestionExpiredException();
		}

		question.setLastVoteDate(serviceContext.getCreateDate(now));

		pollsQuestionPersistence.update(question);

		// Vote

		PollsVote vote = pollsVotePersistence.fetchByQ_U(questionId, userId);

		if (vote != null) {
			StringBundler sb = new StringBundler(5);

			sb.append("{questionId=");
			sb.append(questionId);
			sb.append(", userId=");
			sb.append(userId);
			sb.append("}");

			throw new DuplicateVoteException(sb.toString());
		}

		String userName = null;

		User user = userPersistence.fetchByPrimaryKey(userId);

		if (user != null) {
			userName = user.getFullName();
		}
		else {
			userName = serviceContext.translate("anonymous");
		}

		long voteId = counterLocalService.increment();

		vote = pollsVotePersistence.create(voteId);

		vote.setUuid(serviceContext.getUuid());
		vote.setGroupId(serviceContext.getScopeGroupId());
		vote.setCompanyId(serviceContext.getCompanyId());
		vote.setUserId(userId);
		vote.setUserName(userName);
		vote.setCreateDate(serviceContext.getCreateDate(now));
		vote.setModifiedDate(serviceContext.getModifiedDate(now));
		vote.setQuestionId(questionId);
		vote.setChoiceId(choiceId);
		vote.setVoteDate(serviceContext.getCreateDate(now));

		pollsVotePersistence.update(vote);

		return vote;
	}

	@Override
	public List<PollsVote> getChoiceVotes(long choiceId, int start, int end) {
		return pollsVotePersistence.findByChoiceId(choiceId, start, end);
	}

	@Override
	public int getChoiceVotesCount(long choiceId) {
		return pollsVotePersistence.countByChoiceId(choiceId);
	}

	@Override
	public List<PollsVote> getQuestionVotes(
		long questionId, int start, int end) {

		return pollsVotePersistence.findByQuestionId(questionId, start, end);
	}

	@Override
	public int getQuestionVotesCount(long questionId) {
		return pollsVotePersistence.countByQuestionId(questionId);
	}

	@Override
	public PollsVote getVote(long questionId, long userId)
		throws PortalException {

		return pollsVotePersistence.findByQ_U(questionId, userId);
	}

}