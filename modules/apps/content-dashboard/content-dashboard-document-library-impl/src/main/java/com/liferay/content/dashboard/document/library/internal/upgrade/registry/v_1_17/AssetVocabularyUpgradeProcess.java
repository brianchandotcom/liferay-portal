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

package com.liferay.content.dashboard.document.library.internal.upgrade.registry.v_1_17;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.Arrays;

/**
 * @author Cristina González
 */
public class AssetVocabularyUpgradeProcess extends UpgradeProcess {

	public AssetVocabularyUpgradeProcess(
		AssetVocabularyLocalService assetVocabularyLocalService,
		ClassNameLocalService classNameLocalService,
		CompanyLocalService companyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
		_classNameLocalService = classNameLocalService;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				AssetVocabulary audienceAssetVocabulary =
					_assetVocabularyLocalService.fetchGroupVocabulary(
						company.getGroupId(), "audience");

				if (audienceAssetVocabulary != null) {
					_updateAssetVocabulary(audienceAssetVocabulary);
				}

				AssetVocabulary stageAssetVocabulary =
					_assetVocabularyLocalService.fetchGroupVocabulary(
						company.getGroupId(), "stage");

				if (stageAssetVocabulary != null) {
					_updateAssetVocabulary(stageAssetVocabulary);
				}
			});
	}

	private AssetVocabulary _updateAssetVocabulary(
			AssetVocabulary assetVocabulary)
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			FileEntry.class.getName());

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper(assetVocabulary.getSettings());

		long[] classNameIds = assetVocabularySettingsHelper.getClassNameIds();

		if (ArrayUtil.contains(classNameIds, classNameId)) {
			long[] selectedClassNameIds = ArrayUtil.remove(
				classNameIds, classNameId);

			long[] classNameTypePKs = new long[selectedClassNameIds.length];

			Arrays.fill(classNameTypePKs, -1);

			boolean[] requireds = new boolean[selectedClassNameIds.length];

			Arrays.fill(requireds, false);

			assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
				selectedClassNameIds, classNameTypePKs, requireds);
		}

		return _assetVocabularyLocalService.updateVocabulary(
			assetVocabulary.getVocabularyId(), assetVocabulary.getTitleMap(),
			assetVocabulary.getDescriptionMap(),
			assetVocabularySettingsHelper.toString());
	}

	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final ClassNameLocalService _classNameLocalService;
	private final CompanyLocalService _companyLocalService;

}