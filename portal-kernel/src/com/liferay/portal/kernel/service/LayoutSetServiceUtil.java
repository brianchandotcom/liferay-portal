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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the remote service utility for LayoutSet. This utility wraps
 * <code>com.liferay.portal.service.impl.LayoutSetServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetService
 * @generated
 */
public class LayoutSetServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.LayoutSetServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	 */
	public static void updateLayoutSetPrototypeLinkEnabled(
			long groupId, boolean privateLayout,
			boolean layoutSetPrototypeLinkEnabled,
			String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateLayoutSetPrototypeLinkEnabled(
			groupId, privateLayout, layoutSetPrototypeLinkEnabled,
			layoutSetPrototypeUuid);
	}

	public static void updateLogo(
			long groupId, boolean privateLayout, boolean hasLogo, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateLogo(groupId, privateLayout, hasLogo, bytes);
	}

	public static void updateLogo(
			long groupId, boolean privateLayout, boolean hasLogo,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateLogo(groupId, privateLayout, hasLogo, file);
	}

	public static void updateLogo(
			long groupId, boolean privateLayout, boolean hasLogo,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateLogo(groupId, privateLayout, hasLogo, inputStream);
	}

	public static void updateLogo(
			long groupId, boolean privateLayout, boolean hasLogo,
			java.io.InputStream inputStream, boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateLogo(
			groupId, privateLayout, hasLogo, inputStream, cleanUpStream);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateLookAndFeel(
			long groupId, boolean privateLayout, String themeId,
			String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLookAndFeel(
			groupId, privateLayout, themeId, colorSchemeId, css);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateSettings(
			long groupId, boolean privateLayout, String settings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSettings(groupId, privateLayout, settings);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #updateVirtualHosts(long, boolean, TreeMap)}
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.LayoutSet updateVirtualHost(
			long groupId, boolean privateLayout, String virtualHost)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateVirtualHost(
			groupId, privateLayout, virtualHost);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateVirtualHosts(
			long groupId, boolean privateLayout,
			java.util.TreeMap<String, String> virtualHostnames)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateVirtualHosts(
			groupId, privateLayout, virtualHostnames);
	}

	public static LayoutSetService getService() {
		if (_service == null) {
			_service = (LayoutSetService)PortalBeanLocatorUtil.locate(
				LayoutSetService.class.getName());
		}

		return _service;
	}

	private static LayoutSetService _service;

}