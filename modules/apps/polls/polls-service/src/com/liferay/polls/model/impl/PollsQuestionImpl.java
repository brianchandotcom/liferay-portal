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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.polls.exception.QuestionChoiceException;
import com.liferay.polls.exception.QuestionDescriptionException;
import com.liferay.polls.exception.QuestionTitleException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.polls.service.persistence.PollsChoiceUtil;
import com.liferay.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * @author Brian Wing Shun Chan
 */
public class PollsQuestionImpl extends PollsQuestionBaseImpl {

	private String[] _groupPermissions;
	private String[] _guestPermissions;

	public PollsQuestionImpl() {
	}

	public boolean addChoice(PollsChoice pollsChoice) {
		_addedChoices.add(pollsChoice);

		return true;
	}

	public List<PollsChoice> getChoices() {
		return PollsChoiceLocalServiceUtil.getChoices(getQuestionId());
	}

	public List<PollsVote> getVotes() {
		return PollsVoteLocalServiceUtil.getQuestionVotes(
			getQuestionId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<PollsVote> getVotes(int start, int end) {
		return PollsVoteLocalServiceUtil.getQuestionVotes(
			getQuestionId(), start, end);
	}

	public int getVotesCount() {
		return PollsVoteLocalServiceUtil.getQuestionVotesCount(getQuestionId());
	}

	public boolean isExpired() {
		Date expirationDate = getExpirationDate();

		if ((expirationDate != null) && expirationDate.before(new Date())) {
			return true;
		}
		else {
			return false;
		}
	}

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
		else {
			return false;
		}
	}

	public void persist() {
	}


	public void setGroupPermissions(String... permissions) {
		_groupPermissions = permissions;
	}

	public void setGuestPermissions(String... permissions) {
		_guestPermissions = permissions;
	}

	public void validate() throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String title = getTitleMap().get(locale);

		if (Validator.isNull(title)) {
			throw new QuestionTitleException();
		}

		String description = getDescriptionMap().get(locale);

		if (Validator.isNull(description)) {
			throw new QuestionDescriptionException();
		}

		List<PollsChoice> choices = new ArrayList<>(getChoices());
		choices.addAll(_addedChoices);

		if ((choices.size() < 2)) {
			throw new QuestionChoiceException();
		}

		for (PollsChoice choice : choices) {
			String choiceDescription = choice.getDescription(locale);

			if (Validator.isNull(choiceDescription)) {
				throw new QuestionChoiceException();
			}
		}
	}

	private List<PollsChoice> _addedChoices = new ArrayList<>();

}