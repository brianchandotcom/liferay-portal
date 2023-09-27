/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.reference.service;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * Provides the local service utility for ERAssetVocabulary. This utility wraps
 * <code>com.liferay.external.reference.service.impl.ERAssetVocabularyLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ERAssetVocabularyLocalService
 * @generated
 */
public class ERAssetVocabularyLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.external.reference.service.impl.ERAssetVocabularyLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.asset.kernel.model.AssetVocabulary
			addOrUpdateVocabulary(
				String externalReferenceCode, long userId, long groupId,
				String title, Map<java.util.Locale, String> titleMap,
				Map<java.util.Locale, String> descriptionMap, String settings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateVocabulary(
			externalReferenceCode, userId, groupId, title, titleMap,
			descriptionMap, settings, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ERAssetVocabularyLocalService getService() {
		return _service;
	}

	public static void setService(ERAssetVocabularyLocalService service) {
		_service = service;
	}

	private static volatile ERAssetVocabularyLocalService _service;

}