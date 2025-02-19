/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.exception.InvalidAssetVocabularyGroupRelException;
import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.asset.service.base.AssetVocabularyGroupRelLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class AssetVocabularyGroupRelLocalServiceImpl
	extends AssetVocabularyGroupRelLocalServiceBaseImpl {

	@Override
	public AssetVocabularyGroupRel addAssetVocabularyGroupRel(
			long vocabularyId, long groupId)
		throws PortalException {

		AssetVocabularyGroupRel assetVocabularyGroupRel =
			assetVocabularyGroupRelPersistence.fetchByV_G(
				vocabularyId, groupId);

		if (assetVocabularyGroupRel != null) {
			return assetVocabularyGroupRel;
		}

		assetVocabularyGroupRel = createAssetVocabularyGroupRel(
			counterLocalService.increment());

		assetVocabularyGroupRel.setVocabularyId(vocabularyId);
		assetVocabularyGroupRel.setGroupId(groupId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			assetVocabularyGroupRel.setUuid(serviceContext.getUuid());
		}

		return addAssetVocabularyGroupRel(assetVocabularyGroupRel);
	}

	public void deleteAssetVocabularyGroupRelsByGroupId(long groupId) {
		assetVocabularyGroupRelPersistence.removeByGroupId(groupId);
	}

	public void deleteAssetVocabularyGroupRelsByVocabularyId(
		long vocabularyId) {

		assetVocabularyGroupRelPersistence.removeByVocabularyId(vocabularyId);
	}

	public List<AssetVocabularyGroupRel> getAssetVocabularyGroupRelsByGroupId(
		long groupId) {

		return assetVocabularyGroupRelPersistence.findByGroupId(groupId);
	}

	public List<AssetVocabularyGroupRel>
		getAssetVocabularyGroupRelsByVocabularyId(long vocabularyId) {

		return assetVocabularyGroupRelPersistence.findByVocabularyId(
			vocabularyId);
	}

	public void setAssetVocabularyGroupRels(long vocabularyId, long[] groupIds)
		throws PortalException {

		if (ArrayUtil.isEmpty(groupIds)) {
			throw new InvalidAssetVocabularyGroupRelException(
				"Group IDs cannot be empty");
		}

		assetVocabularyGroupRelPersistence.removeByVocabularyId(vocabularyId);

		for (long groupId : groupIds) {
			addAssetVocabularyGroupRel(vocabularyId, groupId);
		}
	}

}