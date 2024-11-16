/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.model.listener;

import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.layout.page.template.exception.RequiredLayoutPageTemplateEntryException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class LayoutPageTemplateCollectionModelListener
	extends BaseModelListener<LayoutPageTemplateCollection> {

	@Override
	public void onBeforeRemove(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws ModelListenerException {

		if (_hasAssetDisplayPageEntry(
				layoutPageTemplateCollection.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId())) {

			throw new ModelListenerException(
				new RequiredLayoutPageTemplateEntryException());
		}
	}

	private boolean _hasAssetDisplayPageEntry(
		long groupId, long layoutPageTemplateCollectionId) {

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			_layoutPageTemplateCollectionLocalService.
				getLayoutPageTemplateCollections(
					groupId, layoutPageTemplateCollectionId);

		for (LayoutPageTemplateCollection layoutPageTemplateCollection :
				layoutPageTemplateCollections) {

			if (_hasAssetDisplayPageEntry(
					groupId,
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId())) {

				return true;
			}
		}

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntries(
				groupId, layoutPageTemplateCollectionId);

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			int assetDisplayPageEntriesCount =
				_assetDisplayPageEntryLocalService.
					getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
						layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

			if (assetDisplayPageEntriesCount > 0) {
				return true;
			}
		}

		return false;
	}

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}