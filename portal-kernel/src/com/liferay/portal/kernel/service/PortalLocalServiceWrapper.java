/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link PortalLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortalLocalService
 * @generated
 */
public class PortalLocalServiceWrapper
	implements PortalLocalService, ServiceWrapper<PortalLocalService> {

	public PortalLocalServiceWrapper() {
		this(null);
	}

	public PortalLocalServiceWrapper(PortalLocalService portalLocalService) {
		_portalLocalService = portalLocalService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _portalLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PortalLocalService getWrappedService() {
		return _portalLocalService;
	}

	@Override
	public void setWrappedService(PortalLocalService portalLocalService) {
		_portalLocalService = portalLocalService;
	}

	private PortalLocalService _portalLocalService;

}