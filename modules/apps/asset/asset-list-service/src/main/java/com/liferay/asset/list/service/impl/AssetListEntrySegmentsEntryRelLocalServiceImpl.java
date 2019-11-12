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

package com.liferay.asset.list.service.impl;

import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.base.AssetListEntrySegmentsEntryRelLocalServiceBaseImpl;
import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel",
	service = AopService.class
)
public class AssetListEntrySegmentsEntryRelLocalServiceImpl
	extends AssetListEntrySegmentsEntryRelLocalServiceBaseImpl {

	@Override
	public AssetListEntrySegmentsEntryRel addAssetListEntrySegmentsEntryRel(
			long userId, long groupId, long assetListEntryId,
			long segmentsEntryId, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long assetListEntrySegmentsEntryRelId = counterLocalService.increment();

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			assetListEntrySegmentsEntryRelPersistence.create(
				assetListEntrySegmentsEntryRelId);

		assetListEntrySegmentsEntryRel.setUuid(serviceContext.getUuid());
		assetListEntrySegmentsEntryRel.setGroupId(groupId);
		assetListEntrySegmentsEntryRel.setCompanyId(user.getCompanyId());
		assetListEntrySegmentsEntryRel.setUserId(userId);
		assetListEntrySegmentsEntryRel.setUserName(user.getFullName());
		assetListEntrySegmentsEntryRel.setCreateDate(new Date());
		assetListEntrySegmentsEntryRel.setModifiedDate(new Date());
		assetListEntrySegmentsEntryRel.setAssetListEntryId(assetListEntryId);
		assetListEntrySegmentsEntryRel.setSegmentsEntryId(segmentsEntryId);
		assetListEntrySegmentsEntryRel.setTypeSettings(typeSettings);

		return assetListEntrySegmentsEntryRelPersistence.update(
			assetListEntrySegmentsEntryRel);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public AssetListEntrySegmentsEntryRel deleteAssetListEntrySegmentsEntryRel(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel) {

		assetListEntrySegmentsEntryRelPersistence.remove(
			assetListEntrySegmentsEntryRel);

		// Asset list entry rels

		_assetListEntryAssetEntryRelPersistence.removeByA_S(
			assetListEntrySegmentsEntryRel.getAssetListEntryId(),
			assetListEntrySegmentsEntryRel.getSegmentsEntryId());

		return assetListEntrySegmentsEntryRel;
	}

	@Override
	public void deleteAssetListEntrySegmentsEntryRel(
			long assetListEntryId, long segmentsEntryId)
		throws PortalException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			assetListEntrySegmentsEntryRelPersistence.findByA_S(
				assetListEntryId, segmentsEntryId);

		assetListEntrySegmentsEntryRelLocalService.
			deleteAssetListEntrySegmentsEntryRel(
				assetListEntrySegmentsEntryRel);
	}

	@Override
	public void deleteAssetListEntrySegmentsEntryRelByAssetListEntryId(
		long assetListEntryId) {

		List<AssetListEntrySegmentsEntryRel> assetListEntrySegmentsEntryRels =
			assetListEntrySegmentsEntryRelPersistence.findByAssetListEntryId(
				assetListEntryId);

		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				assetListEntrySegmentsEntryRels) {

			assetListEntrySegmentsEntryRelLocalService.
				deleteAssetListEntrySegmentsEntryRel(
					assetListEntrySegmentsEntryRel);
		}
	}

	@Override
	public void deleteAssetListEntrySegmentsEntryRelBySegmentsEntryId(
		long segmentsEntryId) {

		List<AssetListEntrySegmentsEntryRel> assetListEntrySegmentsEntryRels =
			assetListEntrySegmentsEntryRelPersistence.findBySegmentsEntryId(
				segmentsEntryId);

		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				assetListEntrySegmentsEntryRels) {

			assetListEntrySegmentsEntryRelLocalService.
				deleteAssetListEntrySegmentsEntryRel(
					assetListEntrySegmentsEntryRel);
		}
	}

	@Override
	public AssetListEntrySegmentsEntryRel fetchAssetListEntrySegmentsEntryRel(
		long assetListEntryId, long segmentsEntryId) {

		return assetListEntrySegmentsEntryRelPersistence.fetchByA_S(
			assetListEntryId, segmentsEntryId);
	}

	@Override
	public AssetListEntrySegmentsEntryRel getAssetListEntrySegmentsEntryRel(
			long assetListEntryId, long segmentsEntryId)
		throws PortalException {

		return assetListEntrySegmentsEntryRelPersistence.findByA_S(
			assetListEntryId, segmentsEntryId);
	}

	@Override
	public List<AssetListEntrySegmentsEntryRel>
		getAssetListEntrySegmentsEntryRels(
			long assetListEntryId, int start, int end) {

		return assetListEntrySegmentsEntryRelPersistence.findByAssetListEntryId(
			assetListEntryId, start, end);
	}

	@Override
	public int getAssetListEntrySegmentsEntryRelsCount(long assetListEntryId) {
		return assetListEntrySegmentsEntryRelPersistence.
			countByAssetListEntryId(assetListEntryId);
	}

	@Override
	public AssetListEntrySegmentsEntryRel
		updateAssetListEntrySegmentsEntryRelTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings) {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			assetListEntrySegmentsEntryRelPersistence.fetchByA_S(
				assetListEntryId, segmentsEntryId);

		assetListEntrySegmentsEntryRel.setModifiedDate(new Date());
		assetListEntrySegmentsEntryRel.setTypeSettings(typeSettings);

		return assetListEntrySegmentsEntryRelPersistence.update(
			assetListEntrySegmentsEntryRel);
	}

	@Reference
	private AssetListEntryAssetEntryRelPersistence
		_assetListEntryAssetEntryRelPersistence;

}