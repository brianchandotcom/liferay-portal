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

package com.liferay.portlet.polls.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.polls.model.PollsVote;

import java.util.Date;

/**
 * The cache model class for representing PollsVote in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PollsVote
 * @generated
 */
public class PollsVoteCacheModel implements CacheModel<PollsVote> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{voteId=");
		sb.append(voteId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", questionId=");
		sb.append(questionId);
		sb.append(", choiceId=");
		sb.append(choiceId);
		sb.append(", voteDate=");
		sb.append(voteDate);
		sb.append("}");

		return sb.toString();
	}

	public PollsVote toEntityModel() {
		PollsVoteImpl pollsVoteImpl = new PollsVoteImpl();

		pollsVoteImpl.setVoteId(voteId);
		pollsVoteImpl.setUserId(userId);
		pollsVoteImpl.setQuestionId(questionId);
		pollsVoteImpl.setChoiceId(choiceId);

		if (voteDate == Long.MIN_VALUE) {
			pollsVoteImpl.setVoteDate(null);
		}
		else {
			pollsVoteImpl.setVoteDate(new Date(voteDate));
		}

		pollsVoteImpl.resetOriginalValues();

		return pollsVoteImpl;
	}

	public long voteId;
	public long userId;
	public long questionId;
	public long choiceId;
	public long voteDate;
}