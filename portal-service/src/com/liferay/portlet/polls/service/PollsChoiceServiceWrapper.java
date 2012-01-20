/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link PollsChoiceService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsChoiceService
 * @generated
 */
public class PollsChoiceServiceWrapper implements PollsChoiceService,
	ServiceWrapper<PollsChoiceService> {
	public PollsChoiceServiceWrapper(PollsChoiceService pollsChoiceService) {
		_pollsChoiceService = pollsChoiceService;
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public PollsChoiceService getWrappedPollsChoiceService() {
		return _pollsChoiceService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedPollsChoiceService(
		PollsChoiceService pollsChoiceService) {
		_pollsChoiceService = pollsChoiceService;
	}

	public PollsChoiceService getWrappedService() {
		return _pollsChoiceService;
	}

	public void setWrappedService(PollsChoiceService pollsChoiceService) {
		_pollsChoiceService = pollsChoiceService;
	}

	private PollsChoiceService _pollsChoiceService;
}