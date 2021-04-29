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