/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PatcherFixService}.
 *
 * @author Brian Wing Shun Chan
 * @see PatcherFixService
 * @generated
 */
public class PatcherFixServiceWrapper
	implements PatcherFixService, ServiceWrapper<PatcherFixService> {

	public PatcherFixServiceWrapper() {
		this(null);
	}

	public PatcherFixServiceWrapper(PatcherFixService patcherFixService) {
		_patcherFixService = patcherFixService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _patcherFixService.getOSGiServiceIdentifier();
	}

	@Override
	public PatcherFixService getWrappedService() {
		return _patcherFixService;
	}

	@Override
	public void setWrappedService(PatcherFixService patcherFixService) {
		_patcherFixService = patcherFixService;
	}

	private PatcherFixService _patcherFixService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-185665322