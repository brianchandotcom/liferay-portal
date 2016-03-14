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

package com.liferay.polls.service.persistence.impl;

import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.persistence.PollsQuestionPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PollsQuestionFinderBaseImpl extends BasePersistenceImpl<PollsQuestion> {
	@Override
	public Set<String> getBadColumnNames() {
		return getPollsQuestionPersistence().getBadColumnNames();
	}

	/**
	 * Returns the polls question persistence.
	 *
	 * @return the polls question persistence
	 */
	public PollsQuestionPersistence getPollsQuestionPersistence() {
		return pollsQuestionPersistence;
	}

	/**
	 * Sets the polls question persistence.
	 *
	 * @param pollsQuestionPersistence the polls question persistence
	 */
	public void setPollsQuestionPersistence(
		PollsQuestionPersistence pollsQuestionPersistence) {
		this.pollsQuestionPersistence = pollsQuestionPersistence;
	}

	@BeanReference(type = PollsQuestionPersistence.class)
	protected PollsQuestionPersistence pollsQuestionPersistence;
}