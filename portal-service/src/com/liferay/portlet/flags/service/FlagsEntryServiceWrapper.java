/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.flags.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link FlagsEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FlagsEntryService
 * @generated
 */
public class FlagsEntryServiceWrapper implements FlagsEntryService,
	ServiceWrapper<FlagsEntryService> {
	public FlagsEntryServiceWrapper(FlagsEntryService flagsEntryService) {
		_flagsEntryService = flagsEntryService;
	}

	public void addEntry(java.lang.String className, long classPK,
		java.lang.String reporterEmailAddress, long reportedUserId,
		java.lang.String contentTitle, java.lang.String contentURL,
		java.lang.String reason,
		com.liferay.portal.service.ServiceContext serviceContext) {
		_flagsEntryService.addEntry(className, classPK, reporterEmailAddress,
			reportedUserId, contentTitle, contentURL, reason, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public FlagsEntryService getWrappedFlagsEntryService() {
		return _flagsEntryService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedFlagsEntryService(FlagsEntryService flagsEntryService) {
		_flagsEntryService = flagsEntryService;
	}

	public FlagsEntryService getWrappedService() {
		return _flagsEntryService;
	}

	public void setWrappedService(FlagsEntryService flagsEntryService) {
		_flagsEntryService = flagsEntryService;
	}

	private FlagsEntryService _flagsEntryService;
}