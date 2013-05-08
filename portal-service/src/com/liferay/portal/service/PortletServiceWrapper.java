/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * Provides a wrapper for {@link PortletService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletService
 * @generated
 */
public class PortletServiceWrapper implements PortletService,
	ServiceWrapper<PortletService> {
	public PortletServiceWrapper(PortletService portletService) {
		_portletService = portletService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _portletService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_portletService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.kernel.json.JSONArray getWARPortlets() {
		return _portletService.getWARPortlets();
	}

	public com.liferay.portal.model.Portlet updatePortlet(long companyId,
		java.lang.String portletId, java.lang.String roles, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletService.updatePortlet(companyId, portletId, roles, active);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public PortletService getWrappedPortletService() {
		return _portletService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedPortletService(PortletService portletService) {
		_portletService = portletService;
	}

	public PortletService getWrappedService() {
		return _portletService;
	}

	public void setWrappedService(PortletService portletService) {
		_portletService = portletService;
	}

	private PortletService _portletService;
}