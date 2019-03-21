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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.list.exception.AssetEntryAssetListEntryRelPostionException;
import com.liferay.asset.list.model.AssetEntryAssetListEntryRel;
import com.liferay.asset.list.service.base.AssetEntryAssetListEntryRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pavel Savinov
 */
public class AssetEntryAssetListEntryRelLocalServiceImpl
	extends AssetEntryAssetListEntryRelLocalServiceBaseImpl {

	@Override
	public AssetEntryAssetListEntryRel addAssetEntryAssetListEntryRel(
			long assetEntryId, long assetListEntryId, long segmentsEntryId,
			int position, ServiceContext serviceContext)
		throws PortalException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			assetEntryAssetListEntryRelPersistence.fetchByA_S_P(
				assetListEntryId, segmentsEntryId, position);

		if (assetEntryAssetListEntryRel != null) {
			throw new AssetEntryAssetListEntryRelPostionException();
		}

		User user = userLocalService.getUser(serviceContext.getUserId());

		long assetEntryAssetListEntryRelId = counterLocalService.increment();

		assetEntryAssetListEntryRel =
			assetEntryAssetListEntryRelPersistence.create(
				assetEntryAssetListEntryRelId);

		assetEntryAssetListEntryRel.setUuid(serviceContext.getUuid());
		assetEntryAssetListEntryRel.setGroupId(
			serviceContext.getScopeGroupId());
		assetEntryAssetListEntryRel.setCompanyId(serviceContext.getCompanyId());
		assetEntryAssetListEntryRel.setUserId(serviceContext.getUserId());
		assetEntryAssetListEntryRel.setUserName(user.getFullName());
		assetEntryAssetListEntryRel.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		assetEntryAssetListEntryRel.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		assetEntryAssetListEntryRel.setAssetEntryId(assetEntryId);
		assetEntryAssetListEntryRel.setAssetListEntryId(assetListEntryId);
		assetEntryAssetListEntryRel.setSegmentsEntryId(segmentsEntryId);
		assetEntryAssetListEntryRel.setPosition(position);

		return assetEntryAssetListEntryRelPersistence.update(
			assetEntryAssetListEntryRel);
	}

	@Override
	public AssetEntryAssetListEntryRel addAssetEntryAssetListEntryRel(
			long assetEntryId, long assetListEntryId, long segmentsEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		int position = getAssetEntryAssetListEntryRelsCount(
			assetListEntryId, segmentsEntryId);

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			assetEntryAssetListEntryRelPersistence.fetchByA_S_P(
				assetListEntryId, segmentsEntryId, position);

		if (assetEntryAssetListEntryRel != null) {
			throw new AssetEntryAssetListEntryRelPostionException();
		}

		return addAssetEntryAssetListEntryRel(
			assetEntryId, assetListEntryId, segmentsEntryId, position,
			serviceContext);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public AssetEntryAssetListEntryRel deleteAssetEntryAssetListEntryRel(
			long assetListEntryId, long segmentsEntryId, int position)
		throws PortalException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			assetEntryAssetListEntryRelPersistence.removeByA_S_P(
				assetListEntryId, segmentsEntryId, position);

		List<AssetEntryAssetListEntryRel> assetEntryAssetListEntryRels =
			assetEntryAssetListEntryRelPersistence.findByA_S_GtP(
				assetListEntryId, segmentsEntryId, position);

		for (AssetEntryAssetListEntryRel curAssetEntryAssetListEntryRel :
				assetEntryAssetListEntryRels) {

			curAssetEntryAssetListEntryRel.setPosition(
				curAssetEntryAssetListEntryRel.getPosition() - 1);

			assetEntryAssetListEntryRelPersistence.update(
				curAssetEntryAssetListEntryRel);
		}

		return assetEntryAssetListEntryRel;
	}

	@Override
	public void deleteAssetEntryAssetListEntryRelByAssetListEntryId(
		long assetListEntryId) {

		assetEntryAssetListEntryRelPersistence.removeByAssetListEntryId(
			assetListEntryId);
	}

	@Override
	public List<AssetEntryAssetListEntryRel> getAssetEntryAssetListEntryRels(
		long assetListEntryId, int start, int end) {

		List<AssetEntryAssetListEntryRel> assetEntryAssetListEntryRels =
			assetEntryAssetListEntryRelPersistence.findByAssetListEntryId(
				assetListEntryId, start, end);

		return _getAssetEntryAssetListEntryRels(assetEntryAssetListEntryRels);
	}

	@Override
	public List<AssetEntryAssetListEntryRel> getAssetEntryAssetListEntryRels(
		long assetListEntryId, long segmentsEntryId, int start, int end) {

		List<AssetEntryAssetListEntryRel> assetEntryAssetListEntryRels =
			assetEntryAssetListEntryRelPersistence.findByA_S(
				assetListEntryId, segmentsEntryId, start, end);

		return _getAssetEntryAssetListEntryRels(assetEntryAssetListEntryRels);
	}

	@Override
	public int getAssetEntryAssetListEntryRelsCount(long assetListEntryId) {
		return assetEntryAssetListEntryRelPersistence.countByAssetListEntryId(
			assetListEntryId);
	}

	@Override
	public int getAssetEntryAssetListEntryRelsCount(
		long assetListEntryId, long segmentsEntryId) {

		return assetEntryAssetListEntryRelPersistence.countByA_S(
			assetListEntryId, segmentsEntryId);
	}

	@Override
	public AssetEntryAssetListEntryRel moveAssetEntryAssetListEntryRel(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
		throws PortalException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			assetEntryAssetListEntryRelPersistence.findByA_S_P(
				assetListEntryId, segmentsEntryId, position);

		int count =
			assetEntryAssetListEntryRelPersistence.countByAssetListEntryId(
				assetListEntryId);

		if ((newPosition < 0) || (newPosition >= count)) {
			return assetEntryAssetListEntryRel;
		}

		AssetEntryAssetListEntryRel swapAssetEntryAssetListEntryRel =
			assetEntryAssetListEntryRelPersistence.fetchByA_S_P(
				assetListEntryId, segmentsEntryId, newPosition);

		if (swapAssetEntryAssetListEntryRel == null) {
			assetEntryAssetListEntryRel.setPosition(newPosition);

			return assetEntryAssetListEntryRelPersistence.update(
				assetEntryAssetListEntryRel);
		}

		assetEntryAssetListEntryRel.setPosition(-1);

		assetEntryAssetListEntryRelPersistence.update(
			assetEntryAssetListEntryRel);

		swapAssetEntryAssetListEntryRel.setPosition(-2);

		assetEntryAssetListEntryRelPersistence.update(
			swapAssetEntryAssetListEntryRel);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				assetEntryAssetListEntryRel.setPosition(newPosition);

				assetEntryAssetListEntryRelLocalService.
					updateAssetEntryAssetListEntryRel(
						assetEntryAssetListEntryRel);

				swapAssetEntryAssetListEntryRel.setPosition(position);

				assetEntryAssetListEntryRelLocalService.
					updateAssetEntryAssetListEntryRel(
						swapAssetEntryAssetListEntryRel);

				return null;
			});

		return assetEntryAssetListEntryRel;
	}

	@Override
	public AssetEntryAssetListEntryRel updateAssetEntryAssetListEntryRel(
			long assetEntryAssetListEntryRelId, long assetEntryId,
			long assetListEntryId, long segmentsEntryId, int position)
		throws PortalException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			assetEntryAssetListEntryRelPersistence.findByPrimaryKey(
				assetEntryAssetListEntryRelId);

		assetEntryAssetListEntryRel.setAssetEntryId(assetEntryId);
		assetEntryAssetListEntryRel.setAssetListEntryId(assetListEntryId);
		assetEntryAssetListEntryRel.setSegmentsEntryId(segmentsEntryId);
		assetEntryAssetListEntryRel.setPosition(position);

		assetEntryAssetListEntryRelPersistence.update(
			assetEntryAssetListEntryRel);

		return assetEntryAssetListEntryRel;
	}

	private List<AssetEntryAssetListEntryRel> _getAssetEntryAssetListEntryRels(
		List<AssetEntryAssetListEntryRel> assetEntryAssetListEntryRels) {

		Stream<AssetEntryAssetListEntryRel> stream =
			assetEntryAssetListEntryRels.stream();

		return stream.filter(
			assetEntryAssetListEntryRel -> {
				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					assetEntryAssetListEntryRel.getAssetEntryId());

				if (assetEntry == null) {
					return false;
				}

				if (!assetEntry.isVisible()) {
					return false;
				}

				AssetRendererFactory assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassName(
							assetEntry.getClassName());

				if (assetRendererFactory == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"No asset renderer factory associated with " +
								assetEntry.getClassName());
					}

					return false;
				}

				return true;
			}
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryAssetListEntryRelLocalServiceImpl.class);

	@ServiceReference(type = AssetEntryLocalService.class)
	private AssetEntryLocalService _assetEntryLocalService;

}