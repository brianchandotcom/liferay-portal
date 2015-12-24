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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for PortletPreferences. This utility wraps
 * {@link com.liferay.portal.service.impl.PortletPreferencesServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesService
 * @see com.liferay.portal.service.base.PortletPreferencesServiceBaseImpl
 * @see com.liferay.portal.service.impl.PortletPreferencesServiceImpl
 * @generated
 */
@ProviderType
public class PortletPreferencesServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PortletPreferencesServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteArchivedPreferences(long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteArchivedPreferences(portletItemId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void restoreArchivedPreferences(long groupId,
		com.liferay.portal.model.Layout layout, java.lang.String portletId,
		com.liferay.portal.model.PortletItem portletItem,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.restoreArchivedPreferences(groupId, layout, portletId,
			portletItem, preferences);
	}

	public static void restoreArchivedPreferences(long groupId,
		com.liferay.portal.model.Layout layout, java.lang.String portletId,
		long portletItemId, javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.restoreArchivedPreferences(groupId, layout, portletId,
			portletItemId, preferences);
	}

	public static void restoreArchivedPreferences(long groupId,
		java.lang.String name, com.liferay.portal.model.Layout layout,
		java.lang.String portletId, javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.restoreArchivedPreferences(groupId, name, layout, portletId,
			preferences);
	}

	public static void updateArchivePreferences(long userId, long groupId,
		java.lang.String name, java.lang.String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateArchivePreferences(userId, groupId, name, portletId,
			preferences);
	}

	public static PortletPreferencesService getService() {
		if (_service == null) {
			_service = (PortletPreferencesService)PortalBeanLocatorUtil.locate(PortletPreferencesService.class.getName());

			ReferenceRegistry.registerReference(PortletPreferencesServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static PortletPreferencesService _service;
}