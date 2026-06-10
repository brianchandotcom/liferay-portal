/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.service;

import com.liferay.layout.content.model.LayoutContentVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for LayoutContentVersion. This utility wraps
 * <code>com.liferay.layout.content.service.impl.LayoutContentVersionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Lourdes Fernández Besada
 * @see LayoutContentVersionService
 * @generated
 */
public class LayoutContentVersionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.content.service.impl.LayoutContentVersionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static LayoutContentVersion addLayoutContentVersion(
			String externalReferenceCode, long plid,
			Map<java.util.Locale, String> nameMap, String data, int status,
			boolean skipIfUnchanged)
		throws PortalException {

		return getService().addLayoutContentVersion(
			externalReferenceCode, plid, nameMap, data, status,
			skipIfUnchanged);
	}

	public static LayoutContentVersion deleteLayoutContentVersion(
			long layoutContentVersionId)
		throws PortalException {

		return getService().deleteLayoutContentVersion(layoutContentVersionId);
	}

	public static LayoutContentVersion getLayoutContentVersion(
			long layoutContentVersionId)
		throws PortalException {

		return getService().getLayoutContentVersion(layoutContentVersionId);
	}

	public static LayoutContentVersion
			getLayoutContentVersionByExternalReferenceCode(
				String externalReferenceCode, long groupId)
		throws PortalException {

		return getService().getLayoutContentVersionByExternalReferenceCode(
			externalReferenceCode, groupId);
	}

	public static List<LayoutContentVersion> getLayoutContentVersions(long plid)
		throws PortalException {

		return getService().getLayoutContentVersions(plid);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static LayoutContentVersion updateLayoutContentVersion(
			long layoutContentVersionId, Map<java.util.Locale, String> nameMap)
		throws PortalException {

		return getService().updateLayoutContentVersion(
			layoutContentVersionId, nameMap);
	}

	public static LayoutContentVersionService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<LayoutContentVersionService>
		_serviceSnapshot = new Snapshot<>(
			LayoutContentVersionServiceUtil.class,
			LayoutContentVersionService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-1064776350