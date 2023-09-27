/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.exportimport.staged.model.repository;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetVocabulary",
	service = StagedModelRepository.class
)
public class AssetVocabularyStagedModelRepository
	implements StagedModelRepository<AssetVocabulary> {

	@Override
	public AssetVocabulary addStagedModel(
			PortletDataContext portletDataContext,
			AssetVocabulary assetVocabulary)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(AssetVocabulary assetVocabulary)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetVocabulary fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AssetVocabulary fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<AssetVocabulary> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetVocabulary getStagedModel(long vocabularyId)
		throws PortalException {

		return _assetVocabularyLocalService.getAssetVocabulary(vocabularyId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			AssetVocabulary assetVocabulary)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetVocabulary saveStagedModel(AssetVocabulary assetVocabulary)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetVocabulary updateStagedModel(
			PortletDataContext portletDataContext,
			AssetVocabulary assetVocabulary)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}