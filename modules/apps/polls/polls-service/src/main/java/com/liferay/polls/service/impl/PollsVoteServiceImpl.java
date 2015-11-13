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

package com.liferay.polls.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.base.PollsVoteServiceBaseImpl;
import com.liferay.polls.service.permission.PollsQuestionPermissionChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class PollsVoteServiceImpl extends PollsVoteServiceBaseImpl {

	@Override
	public PollsVote addVote(
			long questionId, long choiceId, ServiceContext serviceContext)
		throws PortalException {

		long userId = 0;

		try {
			userId = getUserId();
		}
		catch (PrincipalException pe) {
			userId = counterLocalService.increment();
		}

		PollsQuestionPermissionChecker.check(
			getPermissionChecker(), questionId, ActionKeys.ADD_VOTE);

		return pollsVoteLocalService.addVote(
			userId, questionId, choiceId, serviceContext);
	}

}