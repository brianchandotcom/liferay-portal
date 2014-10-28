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
import com.liferay.portal.kernel.CompanyProvider;
import com.liferay.portal.kernel.GroupProvider;
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
 * [[@]] Simple Repository example. The goals:
 *
 * + created for each type of aggregate that needs global access
 * + provide the illusion of an in-memory collection of all objects of aggregate’s root type.
 * + provide methods to add and remove objects, which will encapsulate the actual insertion
 * or removal of data in the data store.
 * + provide methods that select objects based on criteria meaningful to domain. ATTN: this
 * responsibility may be given to eg Finders, too!
 * + provide repositories only for aggregate roots.
 * + keep app focused on the model, delegating all object storage and access to the repositories.
 *
 */
public class PollsQuestionRepository {

	public static final String ID_PREFIX = "PollsQuestion:";

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

	// ---------------------------------------------------------------- persist

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

							// [[@]] REMOVE company Id and groupId. Three things to note here:
							// 1) companyId is NOT needed, as groupId identifies the company
							// 2) only entity should have these fields, not value objects!
							// 3) groupId may be removed. Here is why.
							//
							// We can assume that groupId (group entity belongs to)
							// is actually not a behavior of a model, but of the
							// persistence and 'outside' boundaries. For example, when
							// dealing with the questions, developer does not need to
							// deal with the group, as the questions are going to be
							// stored to the users group. Only few outside modules
							// do actually need a groupId (like staging).

							pq.setCompanyId(_companyProvider.get().getCompanyId());
							pq.setGroupId(_groupProvider.get().getGroupId());

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

	// [[@]] this method should be PROTECTED!
	// repository deals only with the aggregation roots.
	// PollsChoice is a value object.
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
							pc.setCompanyId(pc.getPollsQuestion().getCompanyId());
							pc.setGroupId(pc.getPollsQuestion().getGroupId());
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

	@BeanReference(type = GroupProvider.class)
	protected GroupProvider _groupProvider;

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider _companyProvider;


}