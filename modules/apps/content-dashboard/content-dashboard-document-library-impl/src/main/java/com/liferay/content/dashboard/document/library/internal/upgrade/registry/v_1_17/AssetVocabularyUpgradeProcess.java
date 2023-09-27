/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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