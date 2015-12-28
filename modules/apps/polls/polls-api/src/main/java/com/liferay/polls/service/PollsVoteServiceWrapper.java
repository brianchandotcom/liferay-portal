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

package com.liferay.polls.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PollsVoteService}.
 *
 * @author Brian Wing Shun Chan
 * @see PollsVoteService
 * @generated
 */
@ProviderType
public class PollsVoteServiceWrapper implements PollsVoteService,
	ServiceWrapper<PollsVoteService> {
	public PollsVoteServiceWrapper(PollsVoteService pollsVoteService) {
		_pollsVoteService = pollsVoteService;
	}

	@Override
	public com.liferay.polls.model.PollsVote addVote(long questionId,
		long choiceId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _pollsVoteService.addVote(questionId, choiceId, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _pollsVoteService.getOSGiServiceIdentifier();
	}

	@Override
	public PollsVoteService getWrappedService() {
		return _pollsVoteService;
	}

	@Override
	public void setWrappedService(PollsVoteService pollsVoteService) {
		_pollsVoteService = pollsVoteService;
	}

	private PollsVoteService _pollsVoteService;
}