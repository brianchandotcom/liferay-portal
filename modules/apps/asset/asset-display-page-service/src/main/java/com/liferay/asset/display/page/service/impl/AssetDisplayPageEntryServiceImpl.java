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

package com.liferay.asset.display.page.service.impl;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.base.AssetDisplayPageEntryServiceBaseImpl;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"json.web.service.context.name=asset",
		"json.web.service.context.path=AssetDisplayPageEntry"
	},
	service = AopService.class
)
public class AssetDisplayPageEntryServiceImpl
	extends AssetDisplayPageEntryServiceBaseImpl {

	@Override
	public AssetDisplayPageEntry addAssetDisplayPageEntry(
			long userId, long groupId, long classNameId, long classPK,
			long layoutPageTemplateEntryId, int type,
			ServiceContext serviceContext)
		throws Exception {

		_checkPermissions(classNameId, classPK, ActionKeys.UPDATE);

		return assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			userId, groupId, classNameId, classPK, layoutPageTemplateEntryId,
			type, serviceContext);
	}

	@Override
	public AssetDisplayPageEntry addAssetDisplayPageEntry(
			long userId, long groupId, long classNameId, long classPK,
			long layoutPageTemplateEntryId, ServiceContext serviceContext)
		throws Exception {

		_checkPermissions(classNameId, classPK, ActionKeys.UPDATE);

		return assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			userId, groupId, classNameId, classPK, layoutPageTemplateEntryId,
			serviceContext);
	}

	@Override
	public void deleteAssetDisplayPageEntry(
			long groupId, long classNameId, long classPK)
		throws Exception {

		_checkPermissions(classNameId, classPK, ActionKeys.DELETE);

		assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntry(
			groupId, classNameId, classPK);
	}

	@Override
	public AssetDisplayPageEntry fetchAssetDisplayPageEntry(
			long groupId, long classNameId, long classPK)
		throws Exception {

		_checkPermissions(classNameId, classPK, ActionKeys.VIEW);

		return assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
			groupId, classNameId, classPK);
	}

	@Override
	public List<AssetDisplayPageEntry>
		getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId) {

		return assetDisplayPageEntryLocalService.
			getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
				layoutPageTemplateEntryId);
	}

	@Override
	public int getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		return assetDisplayPageEntryLocalService.
			getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
				layoutPageTemplateEntryId);
	}

	@Override
	public AssetDisplayPageEntry updateAssetDisplayPageEntry(
			long assetDisplayPageEntryId, long layoutPageTemplateEntryId,
			int type)
		throws Exception {

		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryPersistence.fetchByPrimaryKey(
				assetDisplayPageEntryId);

		_checkPermissions(
			assetDisplayPageEntry.getClassNameId(),
			assetDisplayPageEntry.getClassPK(), ActionKeys.UPDATE);

		return assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(
			assetDisplayPageEntryId, layoutPageTemplateEntryId, type);
	}

	private void _checkPermissions(
			long classNameId, long classPK, String actionId)
		throws Exception {

		InfoItemPermissionProvider infoItemPermissionProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemPermissionProvider.class,
				_portal.getClassName(classNameId));

		if (infoItemPermissionProvider != null) {
			if (!infoItemPermissionProvider.hasPermission(
					getPermissionChecker(), classPK, actionId)) {

				throw new PrincipalException();
			}
		}
		else {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(classNameId);

			if (!assetRendererFactory.hasPermission(
					getPermissionChecker(), classPK, actionId)) {

				throw new PrincipalException();
			}
		}
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Portal _portal;

}