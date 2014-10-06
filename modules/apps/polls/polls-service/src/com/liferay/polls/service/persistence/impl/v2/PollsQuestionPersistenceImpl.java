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

package com.liferay.polls.service.persistence.impl.v2;

import aQute.bnd.annotation.ProviderType;

import com.google.common.base.Optional;
import com.liferay.kernel.persistence.LazyPageableResult;
import com.liferay.polls.model.impl.v2.PollsQuestionImpl;
import com.liferay.polls.model.v2.PollsQuestion;
import com.liferay.polls.service.persistence.v2.PollsQuestionPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.List;

/**
 * @author Carlos Sierra
 */
@ProviderType
public class PollsQuestionPersistenceImpl implements PollsQuestionPersistence {

	private SessionFactory _sessionFactory;

	@Override
	public PollsQuestion create(long questionId) {
		PollsQuestionImpl pollsQuestionImpl = new PollsQuestionImpl();

		pollsQuestionImpl.setCompanyId(companyProvider.get());
		pollsQuestionImpl.setGroupId(groupProvider.get());
		pollsQuestionImpl.setNew(true);

		return pollsQuestionImpl;
	}

	@Override
	public Optional<PollsQuestion> remove(long questionId) {
		Session currentSession = _sessionFactory.getCurrentSession();

		PollsQuestion pollsQuestion = (PollsQuestion)currentSession.get(
			PollsQuestionImpl.class, questionId);

		if (pollsQuestion == null) {
			return Optional.absent();
		}

		currentSession.delete(pollsQuestion);

		return Optional.of(pollsQuestion);
	}

	@Override
	public void updateImpl(PollsQuestion pollsQuestion) {
		Session currentSession = _sessionFactory.getCurrentSession();

		PollsQuestionImpl pollsQuestionImpl = (PollsQuestionImpl) pollsQuestion;

		currentSession.save(pollsQuestionImpl);
	}

	@Override
	public Optional<PollsQuestion> findByPrimaryKey(long questionId) {
		Session currentSession = _sessionFactory.getCurrentSession();

		PollsQuestion pollsQuestion = (PollsQuestion) currentSession.get(
			PollsQuestionImpl.class, questionId);

		return Optional.fromNullable(pollsQuestion);
	}

	@Override
	public LazyPageableResult<PollsQuestion> find() {
		final Session currentSession = _sessionFactory.getCurrentSession();

		Criteria criteria = currentSession.createCriteria(
			PollsQuestionImpl.class);
		criteria.add(
			Restrictions.eq("companyId", companyProvider.get()));
		criteria.add(Restrictions.eq("groupId", groupProvider.get()));

		return new LazyPageableResult<PollsQuestion>() {

			@Override
			public List<PollsQuestion> subList(int start, int end) {


				criteria.setFirstResult(start);
				criteria.setMaxResults(end);

				return criteria.list();
			}

			@Override
			public List<PollsQuestion> all() {
				Criteria criteria = currentSession.createCriteria(
					PollsQuestionImpl.class);

				criteria.add(
					Restrictions.eq("companyId", companyProvider.get()));
				criteria.add(Restrictions.eq("groupId", groupProvider.get()));

				return criteria.list();
			}
		};
	}

	@Override
	public LazyPageableResult<PollsQuestion> find(
		OrderByComparator<PollsQuestion> orderByComparator) {

		return find();
	}

	@Override
	public long removeAll() {
		long count = 0;

		List<PollsQuestion> all = find().all();

		for (PollsQuestion pollsQuestion : all) {
			pollsQuestion.delete();

			count++;
		}

		return count;
	}

	@Override
	public long countAll() {
		Session currentSession = _sessionFactory.getCurrentSession();

		Criteria criteria = currentSession.
			createCriteria(PollsQuestionImpl.class).
			setProjection(Projections.rowCount());

		return (long)criteria.uniqueResult();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Inject @Named("Company") Provider<Long> companyProvider;
	@Inject @Named("Group") Provider<Long> groupProvider;
}