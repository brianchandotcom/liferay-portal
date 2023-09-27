/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Date;
import java.util.List;

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

		User user = _userLocalService.getUser(serviceContext.getUserId());

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
	public List<AssetListEntryAssetEntryRel> getAssetListEntryAssetEntryRels(
		long assetListEntryId, long[] segmentsEntryIds, int start, int end) {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			assetListEntryAssetEntryRelPersistence.findByA_S(
				assetListEntryId, segmentsEntryIds, start, end);

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
	public int getAssetListEntryAssetEntryRelsCount(
		long assetListEntryId, long[] segmentsEntryIds) {

		return assetListEntryAssetEntryRelPersistence.countByA_S(
			assetListEntryId, segmentsEntryIds);
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

		assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.update(
				assetListEntryAssetEntryRel);

		long assetListEntryAssetEntryRelId =
			assetListEntryAssetEntryRel.getAssetListEntryAssetEntryRelId();

		swapAssetListEntryAssetEntryRel.setPosition(-2);

		swapAssetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.update(
				swapAssetListEntryAssetEntryRel);

		long swapAssetListEntryAssetEntryRelId =
			swapAssetListEntryAssetEntryRel.getAssetListEntryAssetEntryRelId();

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				AssetListEntryAssetEntryRel
					callbackAssetListEntryAssetEntryRel =
						assetListEntryAssetEntryRelLocalService.
							fetchAssetListEntryAssetEntryRel(
								assetListEntryAssetEntryRelId);

				callbackAssetListEntryAssetEntryRel.setPosition(newPosition);

				assetListEntryAssetEntryRelLocalService.
					updateAssetListEntryAssetEntryRel(
						callbackAssetListEntryAssetEntryRel);

				callbackAssetListEntryAssetEntryRel =
					assetListEntryAssetEntryRelLocalService.
						fetchAssetListEntryAssetEntryRel(
							swapAssetListEntryAssetEntryRelId);

				callbackAssetListEntryAssetEntryRel.setPosition(position);

				assetListEntryAssetEntryRelLocalService.
					updateAssetListEntryAssetEntryRel(
						callbackAssetListEntryAssetEntryRel);

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

		return assetListEntryAssetEntryRelPersistence.update(
			assetListEntryAssetEntryRel);
	}

	private List<AssetListEntryAssetEntryRel> _getAssetListEntryAssetEntryRels(
		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels) {

		return ListUtil.filter(
			assetListEntryAssetEntryRels,
			assetListEntryAssetEntryRel -> {
				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					assetListEntryAssetEntryRel.getAssetEntryId());

				if ((assetEntry == null) || !assetEntry.isVisible()) {
					return false;
				}

				AssetRendererFactory<?> assetRendererFactory =
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
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListEntryAssetEntryRelLocalServiceImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}