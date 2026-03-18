/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetTagGroupRel;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for AssetTagGroupRel. This utility wraps
 * <code>com.liferay.portlet.asset.service.impl.AssetTagGroupRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagGroupRelService
 * @generated
 */
public class AssetTagGroupRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetTagGroupRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static AssetTagGroupRel addAssetTagGroupRel(long groupId, long tagId)
		throws PortalException {

		return getService().addAssetTagGroupRel(groupId, tagId);
	}

	public static List<AssetTagGroupRel> getAssetTagGroupRelsByTagId(long tagId)
		throws PortalException {

		return getService().getAssetTagGroupRelsByTagId(tagId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void setAssetTagGroupRels(long tagId, long[] groupIds)
		throws PortalException {

		getService().setAssetTagGroupRels(tagId, groupIds);
	}

	public static AssetTagGroupRelService getService() {
		return _service;
	}

	public static void setService(AssetTagGroupRelService service) {
		_service = service;
	}

	private static volatile AssetTagGroupRelService _service;

}
// LIFERAY-SERVICE-BUILDER-HASH:2097904946