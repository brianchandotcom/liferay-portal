/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.reference.service;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * Provides the local service utility for ERAssetCategory. This utility wraps
 * <code>com.liferay.external.reference.service.impl.ERAssetCategoryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ERAssetCategoryLocalService
 * @generated
 */
public class ERAssetCategoryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.external.reference.service.impl.ERAssetCategoryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.asset.kernel.model.AssetCategory
			addOrUpdateCategory(
				String externalReferenceCode, long userId, long groupId,
				long parentCategoryId, Map<java.util.Locale, String> titleMap,
				Map<java.util.Locale, String> descriptionMap, long vocabularyId,
				String[] categoryProperties,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCategory(
			externalReferenceCode, userId, groupId, parentCategoryId, titleMap,
			descriptionMap, vocabularyId, categoryProperties, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ERAssetCategoryLocalService getService() {
		return _service;
	}

	public static void setService(ERAssetCategoryLocalService service) {
		_service = service;
	}

	private static volatile ERAssetCategoryLocalService _service;

}