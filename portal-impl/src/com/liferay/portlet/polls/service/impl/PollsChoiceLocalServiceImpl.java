/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.polls.QuestionChoiceException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.service.base.PollsChoiceLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PollsChoiceLocalServiceImpl
	extends PollsChoiceLocalServiceBaseImpl {

	@Override
	public PollsChoice addChoice(
			long userId, long questionId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		validate(name, description);

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long choiceId = counterLocalService.increment();

		PollsChoice choice = pollsChoicePersistence.create(choiceId);

		choice.setUuid(serviceContext.getUuid());
		choice.setGroupId(serviceContext.getScopeGroupId());
		choice.setCompanyId(user.getCompanyId());
		choice.setUserId(user.getUserId());
		choice.setUserName(user.getFullName());
		choice.setCreateDate(serviceContext.getCreateDate(now));
		choice.setModifiedDate(serviceContext.getModifiedDate(now));
		choice.setQuestionId(questionId);
		choice.setName(name);
		choice.setDescription(description);

		pollsChoicePersistence.update(choice);

		return choice;
	}

	@Override
	public PollsChoice getChoice(long choiceId)
		throws PortalException, SystemException {

		return pollsChoicePersistence.findByPrimaryKey(choiceId);
	}

	@Override
	public List<PollsChoice> getChoices(long questionId)
		throws SystemException {

		return pollsChoicePersistence.findByQuestionId(questionId);
	}

	@Override
	public int getChoicesCount(long questionId) throws SystemException {
		return pollsChoicePersistence.countByQuestionId(questionId);
	}

	@Override
	public PollsChoice updateChoice(
			long choiceId, long questionId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		validate(name, description);

		pollsQuestionPersistence.findByPrimaryKey(questionId);

		PollsChoice choice = pollsChoicePersistence.findByPrimaryKey(choiceId);

		choice.setModifiedDate(serviceContext.getModifiedDate(null));
		choice.setQuestionId(questionId);
		choice.setName(name);
		choice.setDescription(description);

		pollsChoicePersistence.update(choice);

		return choice;
	}

	protected void validate(String name, String description)
		throws PortalException {

		if (Validator.isNull(name) || Validator.isNull(description)) {
			throw new QuestionChoiceException();
		}
	}

}