/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.exception.AssetTagGroupRelGroupIdException;
import com.liferay.asset.kernel.model.AssetTagGroupRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.asset.service.base.AssetTagGroupRelLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Gislayne Vitorino
 */
public class AssetTagGroupRelLocalServiceImpl
	extends AssetTagGroupRelLocalServiceBaseImpl {

	@Override
	public AssetTagGroupRel addAssetTagGroupRel(long groupId, long tagId)
		throws PortalException {

		AssetTagGroupRel assetTagGroupRel =
			assetTagGroupRelPersistence.fetchByG_T(groupId, tagId);

		if (assetTagGroupRel != null) {
			return assetTagGroupRel;
		}

		assetTagGroupRel = createAssetTagGroupRel(
			counterLocalService.increment());

		assetTagGroupRel.setGroupId(groupId);
		assetTagGroupRel.setTagId(tagId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			assetTagGroupRel.setUuid(serviceContext.getUuid());
		}

		return addAssetTagGroupRel(assetTagGroupRel);
	}

	@Override
	public void deleteAssetTagGroupRelsByGroupId(long groupId) {
		assetTagGroupRelPersistence.removeByGroupId(groupId);
	}

	@Override
	public void deleteAssetTagGroupRelsByTagId(long tagId) {
		assetTagGroupRelPersistence.removeByTagId(tagId);
	}

	@Override
	public List<AssetTagGroupRel> getAssetTagGroupRelsByGroupyId(long groupId) {
		return assetTagGroupRelPersistence.findByGroupId(groupId);
	}

	@Override
	public List<AssetTagGroupRel> getAssetTagGroupRelsByTagId(long tagId) {
		return assetTagGroupRelPersistence.findByTagId(tagId);
	}

	@Override
	public void setAssetTagGroupRels(long tagId, long[] groupIds)
		throws PortalException {

		if (ArrayUtil.isEmpty(groupIds)) {
			throw new AssetTagGroupRelGroupIdException();
		}

		assetTagGroupRelPersistence.removeByTagId(tagId);

		for (long groupId : groupIds) {
			addAssetTagGroupRel(groupId, tagId);
		}
	}

}