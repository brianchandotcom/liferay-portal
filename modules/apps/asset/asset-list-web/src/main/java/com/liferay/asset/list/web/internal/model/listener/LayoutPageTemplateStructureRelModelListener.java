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

package com.liferay.asset.list.web.internal.model.listener;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.asset.list.service.AssetListEntryUsageLocalService;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = ModelListener.class)
public class LayoutPageTemplateStructureRelModelListener
	extends BaseModelListener<LayoutPageTemplateStructureRel> {

	@Override
	public void onAfterCreate(
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws ModelListenerException {

		_updateAssetListEntryUsages(layoutPageTemplateStructureRel);
	}

	@Override
	public void onAfterUpdate(
			LayoutPageTemplateStructureRel
				originalLayoutPageTemplateStructureRel,
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws ModelListenerException {

		_updateAssetListEntryUsages(layoutPageTemplateStructureRel);
	}

	private void _addAssetListEntryUsage(
		long classNameId, long groupId, String key,
		long layoutPageTemplateStructureId, long plid) {

		AssetListEntryUsage assetListEntryUsage =
			_assetListEntryUsageLocalService.fetchAssetListEntryUsage(
				groupId, classNameId,
				String.valueOf(layoutPageTemplateStructureId),
				_portal.getClassNameId(LayoutPageTemplateStructure.class), key,
				plid);

		if (assetListEntryUsage != null) {
			return;
		}

		ServiceContext serviceContext = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext()
		).orElse(
			new ServiceContext()
		);

		try {
			_assetListEntryUsageLocalService.addAssetListEntryUsage(
				serviceContext.getUserId(), groupId, classNameId,
				String.valueOf(layoutPageTemplateStructureId),
				_portal.getClassNameId(LayoutPageTemplateStructure.class), key,
				plid, serviceContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private boolean _isMapped(
		JSONObject collectionJSONObject, String itemId,
		LayoutStructure layoutStructure) {

		if ((collectionJSONObject == null) ||
			(!collectionJSONObject.has("classPK") &&
			 !collectionJSONObject.has("key"))) {

			return false;
		}

		if (ListUtil.exists(
				layoutStructure.getDeletedLayoutStructureItems(),
				deletedLayoutStructureItem ->
					deletedLayoutStructureItem.containsItemId(itemId))) {

			return false;
		}

		return true;
	}

	private void _updateAssetListEntryUsages(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateStructureRel.
						getLayoutPageTemplateStructureId());

		if (layoutPageTemplateStructure == null) {
			return;
		}

		_assetListEntryUsageLocalService.deleteAssetListEntryUsages(
			String.valueOf(
				layoutPageTemplateStructure.getLayoutPageTemplateStructureId()),
			_portal.getClassNameId(LayoutPageTemplateStructure.class),
			layoutPageTemplateStructure.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructureRel.getData());

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			if (!(layoutStructureItem instanceof
					CollectionStyledLayoutStructureItem)) {

				continue;
			}

			CollectionStyledLayoutStructureItem
				collectionStyledLayoutStructureItem =
					(CollectionStyledLayoutStructureItem)layoutStructureItem;

			JSONObject collectionJSONObject =
				collectionStyledLayoutStructureItem.getCollectionJSONObject();

			if (!_isMapped(
					collectionJSONObject, layoutStructureItem.getItemId(),
					layoutStructure)) {

				continue;
			}

			if (collectionJSONObject.has("classPK")) {
				_addAssetListEntryUsage(
					_portal.getClassNameId(AssetListEntry.class),
					layoutPageTemplateStructure.getGroupId(),
					collectionJSONObject.getString("classPK"),
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					layoutPageTemplateStructure.getPlid());
			}

			if (collectionJSONObject.has("key")) {
				_addAssetListEntryUsage(
					_portal.getClassNameId(InfoCollectionProvider.class),
					layoutPageTemplateStructure.getGroupId(),
					collectionJSONObject.getString("key"),
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					layoutPageTemplateStructure.getPlid());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateStructureRelModelListener.class);

	@Reference
	private AssetListEntryUsageLocalService _assetListEntryUsageLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}