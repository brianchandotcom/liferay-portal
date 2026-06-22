/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for AssetVocabularyGroupRel. This utility wraps
 * <code>com.liferay.portlet.asset.service.impl.AssetVocabularyGroupRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyGroupRelService
 * @generated
 */
public class AssetVocabularyGroupRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetVocabularyGroupRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static AssetVocabularyGroupRel addAssetVocabularyGroupRel(
			long groupId, long vocabularyId, int depotEntryType)
		throws PortalException {

		return getService().addAssetVocabularyGroupRel(
			groupId, vocabularyId, depotEntryType);
	}

	public static List<AssetVocabularyGroupRel>
			getAssetVocabularyGroupRelsByVocabularyId(long vocabularyId)
		throws PortalException {

		return getService().getAssetVocabularyGroupRelsByVocabularyId(
			vocabularyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void setAssetVocabularyGroupRels(
			long vocabularyId, long[] groupIds, int depotEntryType)
		throws PortalException {

		getService().setAssetVocabularyGroupRels(
			vocabularyId, groupIds, depotEntryType);
	}

	public static AssetVocabularyGroupRelService getService() {
		return _service;
	}

	public static void setService(AssetVocabularyGroupRelService service) {
		_service = service;
	}

	private static volatile AssetVocabularyGroupRelService _service;

}
// LIFERAY-SERVICE-BUILDER-HASH:278641686