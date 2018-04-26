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

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link PortletPreferencesService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesService
 * @generated
 */
@ProviderType
public class PortletPreferencesServiceWrapper
	implements PortletPreferencesService,
		ServiceWrapper<PortletPreferencesService> {
	public PortletPreferencesServiceWrapper(
		PortletPreferencesService portletPreferencesService) {
		_portletPreferencesService = portletPreferencesService;
	}

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
	public String getOSGiServiceIdentifier() {
		return _portletPreferencesService.getOSGiServiceIdentifier();
	}

	@Override
	public void restoreArchivedPreferences(long groupId,
		com.liferay.portal.kernel.model.Layout layout, String portletId,
		long portletItemId, javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException {
		_portletPreferencesService.restoreArchivedPreferences(groupId, layout,
			portletId, portletItemId, preferences);
	}

	@Override
	public void restoreArchivedPreferences(long groupId,
		com.liferay.portal.kernel.model.Layout layout, String portletId,
		com.liferay.portal.kernel.model.PortletItem portletItem,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException {
		_portletPreferencesService.restoreArchivedPreferences(groupId, layout,
			portletId, portletItem, preferences);
	}

	@Override
	public void restoreArchivedPreferences(long groupId, String name,
		com.liferay.portal.kernel.model.Layout layout, String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException {
		_portletPreferencesService.restoreArchivedPreferences(groupId, name,
			layout, portletId, preferences);
	}

	@Override
	public void updateArchivePreferences(long userId, long groupId,
		String name, String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException {
		_portletPreferencesService.updateArchivePreferences(userId, groupId,
			name, portletId, preferences);
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