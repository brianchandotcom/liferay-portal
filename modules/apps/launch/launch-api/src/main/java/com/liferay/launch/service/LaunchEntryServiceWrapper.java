/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LaunchEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchEntryService
 * @generated
 */
public class LaunchEntryServiceWrapper
	implements LaunchEntryService, ServiceWrapper<LaunchEntryService> {

	public LaunchEntryServiceWrapper() {
		this(null);
	}

	public LaunchEntryServiceWrapper(LaunchEntryService launchEntryService) {
		_launchEntryService = launchEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _launchEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public LaunchEntryService getWrappedService() {
		return _launchEntryService;
	}

	@Override
	public void setWrappedService(LaunchEntryService launchEntryService) {
		_launchEntryService = launchEntryService;
	}

	private LaunchEntryService _launchEntryService;

}
// SB-Hash:-862104102