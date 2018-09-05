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

package com.liferay.polls.model.impl;

import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PollsQuestionImpl extends PollsQuestionBaseImpl {

	@Override
	public List<PollsChoice> getChoices() {
		return PollsChoiceLocalServiceUtil.getChoices(getQuestionId());
	}

	@Override
	public List<PollsVote> getVotes() {
		return PollsVoteLocalServiceUtil.getQuestionVotes(
			getQuestionId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<PollsVote> getVotes(int start, int end) {
		return PollsVoteLocalServiceUtil.getQuestionVotes(
			getQuestionId(), start, end);
	}

	@Override
	public int getVotesCount() {
		return PollsVoteLocalServiceUtil.getQuestionVotesCount(getQuestionId());
	}

	@Override
	public boolean isExpired() {
		Date expirationDate = getExpirationDate();

		if ((expirationDate != null) && expirationDate.before(new Date())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isExpired(
		ServiceContext serviceContext, Date defaultCreateDate) {

		Date expirationDate = getExpirationDate();

		if (expirationDate == null) {
			return false;
		}

		Date createDate = serviceContext.getCreateDate(defaultCreateDate);

		if (createDate.after(expirationDate)) {
			return true;
		}

		return false;
	}

}