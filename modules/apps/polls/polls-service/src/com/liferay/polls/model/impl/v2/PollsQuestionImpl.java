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

package com.liferay.polls.model.impl.v2;

import com.liferay.kernel.persistence.LazyResult;
import com.liferay.polls.model.v2.PollsChoice;
import com.liferay.polls.model.v2.PollsQuestion;
import com.liferay.polls.service.persistence.PollsQuestionPersistence;
import com.liferay.polls.service.persistence.v2.PollsChoicePersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author Carlos Sierra Andrés
 */
public class PollsQuestionImpl implements PollsQuestion {

	public void setNew(boolean isNew) {
		_isNew = isNew;
	}

	private boolean _isNew;

	@Override
	public void addChoice(PollsChoice pollsChoice) {
		_addedChoices.add(pollsChoice);
	}

	@Override
	public LazyResult<PollsChoice> getChoices() {
		return _pollsChoicePersistence.findByQuestionId();
	}

	@Override
	public Set<ConstraintViolation<PollsQuestion>> validate() {
		return null;
	}

	@Override
	public void delete() {

	}

	@Override
	public boolean save() {
		Set<ConstraintViolation<PollsQuestion>> violations = validate();

		if (!violations.isEmpty()) {
			return false;
		}

		for (PollsChoice addedChoice : _addedChoices) {
			violations = addedChoice.validate();

			if (!violations.isEmpty()) {
				return false;
			}
		}
		TransactionAttribute.Builder builder =
			new TransactionAttribute.Builder();

		try {
			TransactionInvokerUtil.invoke(
				builder.build(), new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					for (PollsChoice addedChoice : _addedChoices) {
						_pollsChoicePersistence.update(addedChoice);
					}

					_
					return null;
				}
			});
		}
		catch (Throwable throwable) {
			throw new IllegalStateException();
		}
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public PollsChoice createChoice() {
		return null;
	}

	@BeanReference(type=PollsChoicePersistence.class)
	private PollsChoicePersistence _pollsChoicePersistence;
	@BeanReference(type=PollsQuestionPersistence.class)
	private PollsQuestionPersistence _pollsQuestionPersistence;

	private List<PollsChoice> _addedChoices;
	private long companyId;
	private long groupId;
	private long _id;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return null;
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public void resetOriginalValues() {

	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {

	}

	@Override
	public int compareTo(PollsQuestion o) {
		return 0;
	}

	@Override
	public Object clone() {
		return null;
	}
}
