/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * The interface for the portlet preferences remote service.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesServiceUtil
 * @see com.liferay.portal.service.base.PortletPreferencesServiceBaseImpl
 * @see com.liferay.portal.service.impl.PortletPreferencesServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface PortletPreferencesService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletPreferencesServiceUtil} to access the portlet preferences remote service. Add custom service methods to {@link com.liferay.portal.service.impl.PortletPreferencesServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void deleteArchivedPreferences(long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void restoreArchivedPreferences(long groupId,
		com.liferay.portal.model.Layout layout, java.lang.String portletId,
		long portletItemId, javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void restoreArchivedPreferences(long groupId,
		com.liferay.portal.model.Layout layout, java.lang.String portletId,
		com.liferay.portal.model.PortletItem portletItem,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void restoreArchivedPreferences(long groupId, java.lang.String name,
		com.liferay.portal.model.Layout layout, java.lang.String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void updateArchivePreferences(long userId, long groupId,
		java.lang.String name, java.lang.String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}