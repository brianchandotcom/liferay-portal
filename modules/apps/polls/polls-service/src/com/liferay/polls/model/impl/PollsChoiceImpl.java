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
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author Brian Wing Shun Chan
 */
public class PollsChoiceImpl extends PollsChoiceBaseImpl {

	public PollsChoiceImpl() {
	}

	@Override
	public int getVotesCount() {
		return PollsVoteLocalServiceUtil.getChoiceVotesCount(getChoiceId());
	}

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

			TransactionInvokerUtil.invoke(
				transactionAttribute, new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						Date now = new Date();

						if (isNew()) {
							setCreateDate(now);
							setPrimaryKey(CounterLocalServiceUtil.increment());
						}

						setModifiedDate(now);

						PollsChoiceUtil.update(PollsChoiceImpl.this);

						return null;
					}
				}
			);
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}

	}

	public void validate() throws PortalException {
		if (Validator.isNull(getName()) || Validator.isNull(getDescription())) {
			throw new QuestionChoiceException();
		}
	}

}