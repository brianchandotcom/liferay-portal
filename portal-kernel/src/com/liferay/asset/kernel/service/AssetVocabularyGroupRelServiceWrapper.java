/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetVocabularyGroupRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyGroupRelService
 * @generated
 */
public class AssetVocabularyGroupRelServiceWrapper
	implements AssetVocabularyGroupRelService,
			   ServiceWrapper<AssetVocabularyGroupRelService> {

	public AssetVocabularyGroupRelServiceWrapper() {
		this(null);
	}

	public AssetVocabularyGroupRelServiceWrapper(
		AssetVocabularyGroupRelService assetVocabularyGroupRelService) {

		_assetVocabularyGroupRelService = assetVocabularyGroupRelService;
	}

	@Override
	public AssetVocabularyGroupRel addAssetVocabularyGroupRel(
			long groupId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyGroupRelService.addAssetVocabularyGroupRel(
			groupId, vocabularyId);
	}

	@Override
	public java.util.List<AssetVocabularyGroupRel>
			getAssetVocabularyGroupRelsByVocabularyId(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyGroupRelService.
			getAssetVocabularyGroupRelsByVocabularyId(vocabularyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetVocabularyGroupRelService.getOSGiServiceIdentifier();
	}

	@Override
	public void setAssetVocabularyGroupRels(long vocabularyId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetVocabularyGroupRelService.setAssetVocabularyGroupRels(
			vocabularyId, groupIds);
	}

	@Override
	public AssetVocabularyGroupRelService getWrappedService() {
		return _assetVocabularyGroupRelService;
	}

	@Override
	public void setWrappedService(
		AssetVocabularyGroupRelService assetVocabularyGroupRelService) {

		_assetVocabularyGroupRelService = assetVocabularyGroupRelService;
	}

	private AssetVocabularyGroupRelService _assetVocabularyGroupRelService;

}
// SB-Hash:-628723472