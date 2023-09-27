/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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