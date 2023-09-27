/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link PortletService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortletService
 * @generated
 */
public class PortletServiceWrapper
	implements PortletService, ServiceWrapper<PortletService> {

	public PortletServiceWrapper() {
		this(null);
	}

	public PortletServiceWrapper(PortletService portletService) {
		_portletService = portletService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _portletService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getWARPortlets() {
		return _portletService.getWARPortlets();
	}

	@Override
	public boolean hasPortlet(long companyId, java.lang.String portletId) {
		return _portletService.hasPortlet(companyId, portletId);
	}

	@Override
	public com.liferay.portal.kernel.model.Portlet updatePortlet(
			long companyId, java.lang.String portletId, java.lang.String roles,
			boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portletService.updatePortlet(
			companyId, portletId, roles, active);
	}

	@Override
	public PortletService getWrappedService() {
		return _portletService;
	}

	@Override
	public void setWrappedService(PortletService portletService) {
		_portletService = portletService;
	}

	private PortletService _portletService;

}