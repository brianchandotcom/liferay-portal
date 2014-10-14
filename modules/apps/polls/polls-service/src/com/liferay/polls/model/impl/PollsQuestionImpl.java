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

	@Override
	public boolean addChoice(PollsChoice pollsChoice) {
		_addedChoices.add(pollsChoice);

		return true;
	}

	@Override
	public PollsChoice createChoice() {
		PollsChoiceImpl pollsChoiceImpl =
			(PollsChoiceImpl) PollsChoiceUtil.create();

		pollsChoiceImpl.setQuestionId(getQuestionId());

		return pollsChoiceImpl;
	}

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
		else {
			return false;
		}
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
		else {
			return false;
		}
	}

	@Override
	public void persist() {
		try {
			validate();
		} catch (PortalException pe) {
			throw new IllegalStateException(pe);
		}

		try {
			TransactionAttribute.Builder builder =
				new TransactionAttribute.Builder();

			TransactionAttribute transactionAttribute = builder.setPropagation(
				Propagation.SUPPORTS).build();

			final Date now = new Date();

			TransactionInvokerUtil.invoke(
				transactionAttribute, new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					if (isNew()) {
						setCreateDate(now);
						setPrimaryKey(CounterLocalServiceUtil.increment());

						PollsQuestionUtil.update(PollsQuestionImpl.this);

						// Resources

						boolean defaultGroupPermissions =
							_groupPermissions == null;

						boolean defaultGuestPermissions =
							_guestPermissions == null;

						if (defaultGroupPermissions ||
							defaultGuestPermissions) {

							PollsQuestionLocalServiceUtil.addQuestionResources(
								PollsQuestionImpl.this, defaultGroupPermissions,
								defaultGuestPermissions);
						}
						else {
							PollsQuestionLocalServiceUtil.addQuestionResources(
								PollsQuestionImpl.this, _groupPermissions,
								_guestPermissions);
						}
					}

					setModifiedDate(now);

					if (!_addedChoices.isEmpty()) {
						for (PollsChoice choice : _addedChoices) {
							choice.setQuestionId(getQuestionId());

							choice.persist();
						}
					}

					PollsQuestionUtil.update(PollsQuestionImpl.this);

					return null;
				};
			});
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}

		_addedChoices.clear();
		setNew(false);
	}

	@Override
	public void setGroupPermissions(String... permissions) {
		_groupPermissions = permissions;
	}

	@Override
	public void setGuestPermissions(String... permissions) {
		_guestPermissions = permissions;
	}

	@Override
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