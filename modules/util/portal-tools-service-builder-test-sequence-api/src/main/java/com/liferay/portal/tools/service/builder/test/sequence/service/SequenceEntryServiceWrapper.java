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

package com.liferay.portal.tools.service.builder.test.sequence.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SequenceEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see SequenceEntryService
 * @generated
 */
public class SequenceEntryServiceWrapper
	implements SequenceEntryService, ServiceWrapper<SequenceEntryService> {

	public SequenceEntryServiceWrapper() {
		this(null);
	}

	public SequenceEntryServiceWrapper(
		SequenceEntryService sequenceEntryService) {

		_sequenceEntryService = sequenceEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _sequenceEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public SequenceEntryService getWrappedService() {
		return _sequenceEntryService;
	}

	@Override
	public void setWrappedService(SequenceEntryService sequenceEntryService) {
		_sequenceEntryService = sequenceEntryService;
	}

	private SequenceEntryService _sequenceEntryService;

}