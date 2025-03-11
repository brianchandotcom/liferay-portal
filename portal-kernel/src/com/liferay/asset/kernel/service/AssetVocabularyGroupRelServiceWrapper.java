/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

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