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
 * Provides a wrapper for {@link PortletPreferencesService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesService
 * @generated
 */
public class PortletPreferencesServiceWrapper
	implements PortletPreferencesService,
			   ServiceWrapper<PortletPreferencesService> {

	public PortletPreferencesServiceWrapper(
		PortletPreferencesService portletPreferencesService) {

		_portletPreferencesService = portletPreferencesService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletPreferencesServiceUtil} to access the portlet preferences remote service. Add custom service methods to <code>com.liferay.portal.service.impl.PortletPreferencesServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void deleteArchivedPreferences(long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_portletPreferencesService.deleteArchivedPreferences(portletItemId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _portletPreferencesService.getOSGiServiceIdentifier();
	}

	@Override
	public void restoreArchivedPreferences(
			long groupId, com.liferay.portal.kernel.model.Layout layout,
			java.lang.String portletId, long portletItemId,
			javax.portlet.PortletPreferences jxPortletPreferences)
		throws com.liferay.portal.kernel.exception.PortalException {

		_portletPreferencesService.restoreArchivedPreferences(
			groupId, layout, portletId, portletItemId, jxPortletPreferences);
	}

	@Override
	public void restoreArchivedPreferences(
			long groupId, com.liferay.portal.kernel.model.Layout layout,
			java.lang.String portletId,
			com.liferay.portal.kernel.model.PortletItem portletItem,
			javax.portlet.PortletPreferences jxPortletPreferences)
		throws com.liferay.portal.kernel.exception.PortalException {

		_portletPreferencesService.restoreArchivedPreferences(
			groupId, layout, portletId, portletItem, jxPortletPreferences);
	}

	@Override
	public void restoreArchivedPreferences(
			long groupId, java.lang.String name,
			com.liferay.portal.kernel.model.Layout layout,
			java.lang.String portletId,
			javax.portlet.PortletPreferences jxPortletPreferences)
		throws com.liferay.portal.kernel.exception.PortalException {

		_portletPreferencesService.restoreArchivedPreferences(
			groupId, name, layout, portletId, jxPortletPreferences);
	}

	@Override
	public void updateArchivePreferences(
			long userId, long groupId, java.lang.String name,
			java.lang.String portletId,
			javax.portlet.PortletPreferences jxPortletPreferences)
		throws com.liferay.portal.kernel.exception.PortalException {

		_portletPreferencesService.updateArchivePreferences(
			userId, groupId, name, portletId, jxPortletPreferences);
	}

	@Override
	public PortletPreferencesService getWrappedService() {
		return _portletPreferencesService;
	}

	@Override
	public void setWrappedService(
		PortletPreferencesService portletPreferencesService) {

		_portletPreferencesService = portletPreferencesService;
	}

	private PortletPreferencesService _portletPreferencesService;

}