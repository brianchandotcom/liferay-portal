/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.model.listener;

import com.liferay.asset.list.model.AssetListEntry;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Objects;

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
		long classNameId, long groupId, String itemId, String key, long plid) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		try {
			_assetListEntryUsageLocalService.addAssetListEntryUsage(
				serviceContext.getUserId(), groupId, classNameId, itemId,
				_getCollectionStyledLayoutStructureItemClassNameId(), key, plid,
				serviceContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private long _getAssetListEntryClassNameId() {
		if (_assetListEntryClassNameId != null) {
			return _assetListEntryClassNameId;
		}

		_assetListEntryClassNameId = _portal.getClassNameId(
			AssetListEntry.class.getName());

		return _assetListEntryClassNameId;
	}

	private long _getCollectionStyledLayoutStructureItemClassNameId() {
		if (_collectionStyledLayoutStructureItemClassNameId != null) {
			return _collectionStyledLayoutStructureItemClassNameId;
		}

		_collectionStyledLayoutStructureItemClassNameId =
			_portal.getClassNameId(
				CollectionStyledLayoutStructureItem.class.getName());

		return _collectionStyledLayoutStructureItemClassNameId;
	}

	private long _getInfoCollectionProviderClassNameId() {
		if (_infoCollectionProviderClassNameId != null) {
			return _infoCollectionProviderClassNameId;
		}

		_infoCollectionProviderClassNameId = _portal.getClassNameId(
			InfoCollectionProvider.class.getName());

		return _infoCollectionProviderClassNameId;
	}

	private boolean _isMapped(
		JSONObject collectionJSONObject, String itemId,
		LayoutStructure layoutStructure) {

		if ((collectionJSONObject == null) ||
			(!collectionJSONObject.has("classPK") &&
			 !collectionJSONObject.has("key"))) {

			return false;
		}

		if (layoutStructure.isItemMarkedForDeletion(itemId)) {
			return false;
		}

		return true;
	}

	private void _updateAssetListEntryUsages(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) {

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				layoutPageTemplateStructureRel.getSegmentsExperienceId());

		if ((segmentsExperience == null) ||
			!Objects.equals(
				SegmentsExperienceConstants.KEY_DEFAULT,
				segmentsExperience.getSegmentsExperienceKey())) {

			return;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateStructureRel.
						getLayoutPageTemplateStructureId());

		if (layoutPageTemplateStructure == null) {
			return;
		}

		_assetListEntryUsageLocalService.deleteAssetListEntryUsages(
			_getCollectionStyledLayoutStructureItemClassNameId(),
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
					_getAssetListEntryClassNameId(),
					layoutPageTemplateStructure.getGroupId(),
					layoutStructureItem.getItemId(),
					collectionJSONObject.getString("classPK"),
					layoutPageTemplateStructure.getPlid());
			}

			if (collectionJSONObject.has("key")) {
				_addAssetListEntryUsage(
					_getInfoCollectionProviderClassNameId(),
					layoutPageTemplateStructure.getGroupId(),
					layoutStructureItem.getItemId(),
					collectionJSONObject.getString("key"),
					layoutPageTemplateStructure.getPlid());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateStructureRelModelListener.class);

	private Long _assetListEntryClassNameId;

	@Reference
	private AssetListEntryUsageLocalService _assetListEntryUsageLocalService;

	private Long _collectionStyledLayoutStructureItemClassNameId;
	private Long _infoCollectionProviderClassNameId;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}