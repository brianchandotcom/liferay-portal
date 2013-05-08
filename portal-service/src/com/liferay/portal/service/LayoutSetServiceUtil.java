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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for LayoutSet. This utility wraps
 * {@link com.liferay.portal.service.impl.LayoutSetServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetService
 * @see com.liferay.portal.service.base.LayoutSetServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutSetServiceImpl
 * @generated
 */
public class LayoutSetServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutSetServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the state of the layout set prototype link.
	*
	* <p>
	* <strong>Important:</strong> Setting
	* <code>layoutSetPrototypeLinkEnabled</code> to <code>true</code> and
	* <code>layoutSetPrototypeUuid</code> to <code>null</code> when the layout
	* set prototype's current uuid is <code>null</code> will result in an
	* <code>IllegalStateException</code>.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout set is private to the group
	* @param layoutSetPrototypeLinkEnabled whether the layout set prototype is
	link enabled
	* @param layoutSetPrototypeUuid the uuid of the layout set prototype to
	link with
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean privateLayout, boolean layoutSetPrototypeLinkEnabled,
		java.lang.String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateLayoutSetPrototypeLinkEnabled(groupId, privateLayout,
			layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);
	}

	public static void updateLogo(long groupId, boolean privateLayout,
		boolean logo, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(groupId, privateLayout, logo, bytes);
	}

	public static void updateLogo(long groupId, boolean privateLayout,
		boolean logo, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(groupId, privateLayout, logo, file);
	}

	public static void updateLogo(long groupId, boolean privateLayout,
		boolean logo, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLogo(groupId, privateLayout, logo, inputStream);
	}

	public static void updateLogo(long groupId, boolean privateLayout,
		boolean logo, java.io.InputStream inputStream, boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateLogo(groupId, privateLayout, logo, inputStream, cleanUpStream);
	}

	public static com.liferay.portal.model.LayoutSet updateLookAndFeel(
		long groupId, boolean privateLayout, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLookAndFeel(groupId, privateLayout, themeId,
			colorSchemeId, css, wapTheme);
	}

	public static com.liferay.portal.model.LayoutSet updateSettings(
		long groupId, boolean privateLayout, java.lang.String settings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSettings(groupId, privateLayout, settings);
	}

	public static com.liferay.portal.model.LayoutSet updateVirtualHost(
		long groupId, boolean privateLayout, java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateVirtualHost(groupId, privateLayout, virtualHost);
	}

	public static LayoutSetService getService() {
		if (_service == null) {
			_service = (LayoutSetService)PortalBeanLocatorUtil.locate(LayoutSetService.class.getName());

			ReferenceRegistry.registerReference(LayoutSetServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(LayoutSetService service) {
	}

	private static LayoutSetService _service;
}