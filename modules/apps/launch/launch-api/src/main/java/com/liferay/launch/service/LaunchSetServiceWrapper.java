/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LaunchSetService}.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchSetService
 * @generated
 */
public class LaunchSetServiceWrapper
	implements LaunchSetService, ServiceWrapper<LaunchSetService> {

	public LaunchSetServiceWrapper() {
		this(null);
	}

	public LaunchSetServiceWrapper(LaunchSetService launchSetService) {
		_launchSetService = launchSetService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _launchSetService.getOSGiServiceIdentifier();
	}

	@Override
	public LaunchSetService getWrappedService() {
		return _launchSetService;
	}

	@Override
	public void setWrappedService(LaunchSetService launchSetService) {
		_launchSetService = launchSetService;
	}

	private LaunchSetService _launchSetService;

}
// LIFERAY-SERVICE-BUILDER-HASH:1769804218