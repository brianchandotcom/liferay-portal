/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.exception.AssetVocabularyGroupRelGroupIdException;
import com.liferay.asset.kernel.exception.SystemVocabularyException;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
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
			long groupId, long vocabularyId)
		throws PortalException {

		AssetVocabularyGroupRel assetVocabularyGroupRel =
			assetVocabularyGroupRelPersistence.fetchByG_V(
				groupId, vocabularyId);

		if (assetVocabularyGroupRel != null) {
			return assetVocabularyGroupRel;
		}

		assetVocabularyGroupRel = assetVocabularyGroupRelPersistence.create(
			counterLocalService.increment());

		assetVocabularyGroupRel.setGroupId(groupId);
		assetVocabularyGroupRel.setVocabularyId(vocabularyId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			assetVocabularyGroupRel.setUuid(serviceContext.getUuid());
		}

		assetVocabularyGroupRel = assetVocabularyGroupRelPersistence.update(
			assetVocabularyGroupRel);

		_reindexAssetVocabulary(vocabularyId);

		return assetVocabularyGroupRel;
	}

	@Override
	public void deleteAssetVocabularyGroupRelsByGroupId(long groupId) {
		assetVocabularyGroupRelPersistence.removeByGroupId(groupId);
	}

	@Override
	public void deleteAssetVocabularyGroupRelsByVocabularyId(
		long vocabularyId) {

		assetVocabularyGroupRelPersistence.removeByVocabularyId(vocabularyId);
	}

	@Override
	public List<AssetVocabularyGroupRel> getAssetVocabularyGroupRelsByGroupId(
		long groupId) {

		return assetVocabularyGroupRelPersistence.findByGroupId(groupId);
	}

	@Override
	public List<AssetVocabularyGroupRel>
		getAssetVocabularyGroupRelsByVocabularyId(long vocabularyId) {

		return assetVocabularyGroupRelPersistence.findByVocabularyId(
			vocabularyId);
	}

	@Override
	public int getAssetVocabularyGroupRelsCount(long vocabularyId) {
		return assetVocabularyGroupRelPersistence.countByVocabularyId(
			vocabularyId);
	}

	@Override
	public void setAssetVocabularyGroupRels(long vocabularyId, long[] groupIds)
		throws PortalException {

		if (ArrayUtil.isEmpty(groupIds)) {
			throw new AssetVocabularyGroupRelGroupIdException();
		}

		_validateSystemVocabulary(groupIds, vocabularyId);

		assetVocabularyGroupRelPersistence.removeByVocabularyId(vocabularyId);

		for (long groupId : groupIds) {
			addAssetVocabularyGroupRel(groupId, vocabularyId);
		}
	}

	private void _reindexAssetVocabulary(long vocabularyId)
		throws PortalException {

		Indexer<AssetVocabulary> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(AssetVocabulary.class);

		indexer.reindex(AssetVocabulary.class.getName(), vocabularyId);
	}

	private void _validateSystemVocabulary(long[] groupIds, long vocabularyId)
		throws PortalException {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchAssetVocabulary(vocabularyId);

		if ((assetVocabulary == null) || !assetVocabulary.isSystem() ||
			!FeatureFlagManagerUtil.isEnabled(
				assetVocabulary.getCompanyId(), "LPD-86291")) {

			return;
		}

		if ((groupIds.length != 1) ||
			(groupIds[0] != GroupConstants.GROUP_ID_ALL)) {

			throw new SystemVocabularyException.
				MustNotChangeSystemVocabularyGroupRels(vocabularyId);
		}
	}

	@BeanReference(type = AssetVocabularyLocalService.class)
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}