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

package com.liferay.polls.repository;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.polls.service.persistence.PollsChoicePersistence;
import com.liferay.polls.service.persistence.PollsChoiceUtil;
import com.liferay.polls.service.persistence.PollsQuestionPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import java8.util.Optional;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * [[@]] Trying a Repo.
 */
public class PollsQuestionRepository {

	public static final String ID_PREFIX = "PollsQuestion:";

	public PollsQuestion create(String title) {
		PollsQuestion pollsQuestion = pollsQuestionPersistence.create();

		pollsQuestion.setTitle(title);

		return pollsQuestion;
	}

	public Optional<PollsQuestion> findById(String id) {
		if (!id.startsWith(ID_PREFIX)) {
			throw new IllegalArgumentException(
				"Invalid id for this repo:\nRepo: "+ this +"\nId: " + id);
		}

		String longString = id.substring(14);

		try {
			long primaryKey = GetterUtil.getLong(longString);

			return Optional.of(pollsQuestionPersistence.findByPrimaryKey(primaryKey));
		}
		catch (NoSuchQuestionException e) {
			return Optional.empty();
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException(
				"Invalid id for this repo:\nRepo: "+ this +"\nId: " + id);
		}
	}

	public Optional<String> toId(PollsQuestion pq) {
		if (pq.getPrimaryKey() == 0) {
			return Optional.empty();
		}

		return Optional.of(doToId(pq));
	}

	private String doToId(PollsQuestion pq) {
		return ID_PREFIX + Long.toString(pq.getPrimaryKey());
	}

	public String persist(final PollsQuestion pq) {
		try {
			/* [[@]] should repo validate? should not - we should validate
			 entity only when it is actually modified. but we can't move from
			 here.
			 I guess the repo should validate and the model could cache the
			 validation. Validation should not throw exceptions but to
			 return a List<ConstraintValidation> as in JSR 303. If the model
			 has not been modified it could return always the same set of
			 validations. Although properly invalidate caches is always tricky*/
			pq.validate();
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
						if (pq.isNew()) {
							pq.setCreateDate(now);
							pq.setPrimaryKey(CounterLocalServiceUtil.increment());

							pollsQuestionPersistence.update(pq);

							// Resources

							boolean defaultGroupPermissions =
								pq.getGroupPermissions() == null;

							boolean defaultGuestPermissions =
								pq.getGuestPermissions() == null;

							if (defaultGroupPermissions ||
								defaultGuestPermissions) {

								PollsQuestionLocalServiceUtil.addQuestionResources(
									pq, defaultGroupPermissions,
									defaultGuestPermissions);
							}
							else {
								PollsQuestionLocalServiceUtil.addQuestionResources(
									pq, pq.getGroupPermissions(),
									pq.getGuestPermissions());
							}
						}

						pq.setModifiedDate(now);

						//if (!pq._addedChoices.isEmpty()) {
							for (PollsChoice choice : pq.getChoices()) {
								long id = choice.getChoiceId();

								if (id == 0) {
									choice.setQuestionId(pq.getQuestionId());
									persist(choice);
								}
							}
						//}

						pollsQuestionPersistence.update(pq);

						return null;
					}
				});
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}

		pq.setNew(false);

		return doToId(pq);
	}


	public void persist(final PollsChoice pc) {
		try {
			pc.validate();
		} catch (PortalException pe) {
			throw new IllegalStateException(pe);
		}
		try {
			TransactionAttribute.Builder builder =
				new TransactionAttribute.Builder();

			TransactionAttribute transactionAttribute = builder.setPropagation(
				Propagation.SUPPORTS).build();

			TransactionInvokerUtil.invoke(
				transactionAttribute, new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						Date now = new Date();

						if (pc.isNew()) {
							pc.setCreateDate(now);
							pc.setPrimaryKey(CounterLocalServiceUtil.increment());
						}

						pc.setModifiedDate(now);

						pollsChoicePersistence.update(pc);

						return null;
					}
				}
			);
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}

	}


	@BeanReference(type = PollsQuestionPersistence.class)
	protected PollsQuestionPersistence pollsQuestionPersistence;

	@BeanReference(type = PollsChoicePersistence.class)
	protected PollsChoicePersistence pollsChoicePersistence;
}