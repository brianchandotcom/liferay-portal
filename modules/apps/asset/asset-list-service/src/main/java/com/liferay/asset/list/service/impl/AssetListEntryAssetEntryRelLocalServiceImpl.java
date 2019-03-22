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
import com.liferay.asset.list.exception.AssetListEntryAssetEntryRelPostionException;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.base.AssetListEntryAssetEntryRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel savinov
 */
@Component(
	property = "model.class.name=com.liferay.asset.list.model.AssetListEntryAssetEntryRel",
	service = AopService.class
)
public class AssetListEntryAssetEntryRelLocalServiceImpl
	extends AssetListEntryAssetEntryRelLocalServiceBaseImpl {

	@Override
	public AssetListEntryAssetEntryRel addAssetListEntryAssetEntryRel(
			long assetListEntryId, long assetEntryId, long segmentsEntryId,
			int position, ServiceContext serviceContext)
		throws PortalException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.fetchByA_S_P(
				assetListEntryId, segmentsEntryId, position);

		if (assetListEntryAssetEntryRel != null) {
			throw new AssetListEntryAssetEntryRelPostionException();
		}

		User user = userLocalService.getUser(serviceContext.getUserId());

		long assetListEntryAssetEntryRelId = counterLocalService.increment();

		assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.create(
				assetListEntryAssetEntryRelId);

		assetListEntryAssetEntryRel.setUuid(serviceContext.getUuid());
		assetListEntryAssetEntryRel.setGroupId(
			serviceContext.getScopeGroupId());
		assetListEntryAssetEntryRel.setCompanyId(serviceContext.getCompanyId());
		assetListEntryAssetEntryRel.setUserId(serviceContext.getUserId());
		assetListEntryAssetEntryRel.setUserName(user.getFullName());
		assetListEntryAssetEntryRel.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		assetListEntryAssetEntryRel.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		assetListEntryAssetEntryRel.setAssetListEntryId(assetListEntryId);
		assetListEntryAssetEntryRel.setAssetEntryId(assetEntryId);
		assetListEntryAssetEntryRel.setSegmentsEntryId(segmentsEntryId);
		assetListEntryAssetEntryRel.setPosition(position);

		return assetListEntryAssetEntryRelPersistence.update(
			assetListEntryAssetEntryRel);
	}

	@Override
	public AssetListEntryAssetEntryRel addAssetListEntryAssetEntryRel(
			long assetListEntryId, long assetEntryId, long segmentsEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		int position = getAssetListEntryAssetEntryRelsCount(
			assetListEntryId, segmentsEntryId);

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.fetchByA_S_P(
				assetListEntryId, segmentsEntryId, position);

		if (assetListEntryAssetEntryRel != null) {
			throw new AssetListEntryAssetEntryRelPostionException();
		}

		return addAssetListEntryAssetEntryRel(
			assetListEntryId, assetEntryId, segmentsEntryId, position,
			serviceContext);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public AssetListEntryAssetEntryRel deleteAssetListEntryAssetEntryRel(
			long assetListEntryId, long segmentsEntryId, int position)
		throws PortalException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.removeByA_S_P(
				assetListEntryId, segmentsEntryId, position);

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			assetListEntryAssetEntryRelPersistence.findByA_S_GtP(
				assetListEntryId, segmentsEntryId, position);

		for (AssetListEntryAssetEntryRel curAssetListEntryAssetEntryRel :
				assetListEntryAssetEntryRels) {

			curAssetListEntryAssetEntryRel.setPosition(
				curAssetListEntryAssetEntryRel.getPosition() - 1);

			assetListEntryAssetEntryRelPersistence.update(
				curAssetListEntryAssetEntryRel);
		}

		return assetListEntryAssetEntryRel;
	}

	@Override
	public void deleteAssetListEntryAssetEntryRelByAssetListEntryId(
		long assetListEntryId) {

		assetListEntryAssetEntryRelPersistence.removeByAssetListEntryId(
			assetListEntryId);
	}

	@Override
	public List<AssetListEntryAssetEntryRel> getAssetListEntryAssetEntryRels(
		long assetListEntryId, int start, int end) {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			assetListEntryAssetEntryRelPersistence.findByAssetListEntryId(
				assetListEntryId, start, end);

		return _getAssetListEntryAssetEntryRels(assetListEntryAssetEntryRels);
	}

	@Override
	public List<AssetListEntryAssetEntryRel> getAssetListEntryAssetEntryRels(
		long assetListEntryId, long segmentsEntryId, int start, int end) {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			assetListEntryAssetEntryRelPersistence.findByA_S(
				assetListEntryId, segmentsEntryId, start, end);

		return _getAssetListEntryAssetEntryRels(assetListEntryAssetEntryRels);
	}

	@Override
	public int getAssetListEntryAssetEntryRelsCount(long assetListEntryId) {
		return assetListEntryAssetEntryRelPersistence.countByAssetListEntryId(
			assetListEntryId);
	}

	@Override
	public int getAssetListEntryAssetEntryRelsCount(
		long assetListEntryId, long segmentsEntryId) {

		return assetListEntryAssetEntryRelPersistence.countByA_S(
			assetListEntryId, segmentsEntryId);
	}

	@Override
	public AssetListEntryAssetEntryRel moveAssetListEntryAssetEntryRel(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
		throws PortalException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.findByA_S_P(
				assetListEntryId, segmentsEntryId, position);

		int count =
			assetListEntryAssetEntryRelPersistence.countByAssetListEntryId(
				assetListEntryId);

		if ((newPosition < 0) || (newPosition >= count)) {
			return assetListEntryAssetEntryRel;
		}

		AssetListEntryAssetEntryRel swapAssetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.fetchByA_S_P(
				assetListEntryId, segmentsEntryId, newPosition);

		if (swapAssetListEntryAssetEntryRel == null) {
			assetListEntryAssetEntryRel.setPosition(newPosition);

			return assetListEntryAssetEntryRelPersistence.update(
				assetListEntryAssetEntryRel);
		}

		assetListEntryAssetEntryRel.setPosition(-1);

		assetListEntryAssetEntryRelPersistence.update(
			assetListEntryAssetEntryRel);

		swapAssetListEntryAssetEntryRel.setPosition(-2);

		assetListEntryAssetEntryRelPersistence.update(
			swapAssetListEntryAssetEntryRel);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				assetListEntryAssetEntryRel.setPosition(newPosition);

				assetListEntryAssetEntryRelLocalService.
					updateAssetListEntryAssetEntryRel(
						assetListEntryAssetEntryRel);

				swapAssetListEntryAssetEntryRel.setPosition(position);

				assetListEntryAssetEntryRelLocalService.
					updateAssetListEntryAssetEntryRel(
						swapAssetListEntryAssetEntryRel);

				return null;
			});

		return assetListEntryAssetEntryRel;
	}

	@Override
	public AssetListEntryAssetEntryRel updateAssetListEntryAssetEntryRel(
			long assetListEntryAssetEntryRelId, long assetListEntryId,
			long assetEntryId, long segmentsEntryId, int position)
		throws PortalException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.findByPrimaryKey(
				assetListEntryAssetEntryRelId);

		assetListEntryAssetEntryRel.setAssetListEntryId(assetListEntryId);
		assetListEntryAssetEntryRel.setAssetEntryId(assetEntryId);
		assetListEntryAssetEntryRel.setSegmentsEntryId(segmentsEntryId);
		assetListEntryAssetEntryRel.setPosition(position);

		assetListEntryAssetEntryRelPersistence.update(
			assetListEntryAssetEntryRel);

		return assetListEntryAssetEntryRel;
	}

	private List<AssetListEntryAssetEntryRel> _getAssetListEntryAssetEntryRels(
		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels) {

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryAssetEntryRels.stream();

		return stream.filter(
			assetListEntryAssetEntryRel -> {
				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					assetListEntryAssetEntryRel.getAssetEntryId());

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
							"No asset renderer factory found for class " +
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
		AssetListEntryAssetEntryRelLocalServiceImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

}