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

package com.liferay.polls.service.persistence.v2;

import aQute.bnd.annotation.ProviderType;
import com.google.common.base.Optional;
import com.liferay.kernel.persistence.LazyPageableResult;
import com.liferay.polls.model.v2.PollsQuestion;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@ProviderType
public interface PollsQuestionPersistence {

	/**
	* Creates a new polls question with the primary key. Does not add the polls question to the database.
	*
	* @param questionId the primary key for the new polls question
	* @return the new polls question
	*/
	public PollsQuestion create(long questionId);

	/**
	* Removes the polls question with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param questionId the primary key of the polls question
	* @return the polls question that was removed
	* @throws com.liferay.polls.NoSuchQuestionException if a polls question with the primary key could not be found
	*/
	public Optional<PollsQuestion> remove(long questionId);

	public void updateImpl(PollsQuestion pollsQuestion);

	/**
	* Returns the polls question with the primary key or throws a {@link com.liferay.polls.NoSuchQuestionException} if it could not be found.
	*
	* @param questionId the primary key of the polls question
	* @return the polls question
	* @throws com.liferay.polls.NoSuchQuestionException if a polls question with the primary key could not be found
	*/
	public Optional<PollsQuestion> findByPrimaryKey(long questionId);

	/**
	* Returns all the polls questions.
	*
	* @return the polls questions
	*/
	public LazyPageableResult<PollsQuestion> find();

	/**
	* Returns an ordered range of all the polls questions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.polls.model.impl.PollsQuestionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of polls questions
	* @param end the upper bound of the range of polls questions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of polls questions
	*/
	public LazyPageableResult<PollsQuestion> find(
		OrderByComparator<PollsQuestion> orderByComparator);

	/**
	* Removes all the polls questions from the database.
	*/
	public long removeAll();

	/**
	* Returns the number of polls questions.
	*
	* @return the number of polls questions
	*/
	public long countAll();
}