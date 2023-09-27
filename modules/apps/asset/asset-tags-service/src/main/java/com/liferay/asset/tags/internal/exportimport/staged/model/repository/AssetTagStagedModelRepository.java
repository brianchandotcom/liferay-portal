/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.exportimport.staged.model.repository;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
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
	property = "model.class.name=com.liferay.asset.kernel.model.AssetTag",
	service = StagedModelRepository.class
)
public class AssetTagStagedModelRepository
	implements StagedModelRepository<AssetTag> {

	@Override
	public AssetTag addStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(AssetTag assetTag) throws PortalException {
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
	public AssetTag fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AssetTag fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<AssetTag> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetTag getStagedModel(long tagId) throws PortalException {
		return _assetTagLocalService.getAssetTag(tagId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetTag saveStagedModel(AssetTag assetTag) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public AssetTag updateStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}