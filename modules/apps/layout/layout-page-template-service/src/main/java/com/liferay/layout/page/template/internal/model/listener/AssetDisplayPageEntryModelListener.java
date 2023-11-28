/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.model.listener;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class AssetDisplayPageEntryModelListener
	extends BaseModelListener<AssetDisplayPageEntry> {

	@Override
	public void onAfterCreate(AssetDisplayPageEntry assetDisplayPageEntry)
		throws ModelListenerException {

		_updateModifiedDate(
			assetDisplayPageEntry.getLayoutPageTemplateEntryId());
	}

	@Override
	public void onAfterUpdate(
			AssetDisplayPageEntry originalAssetDisplayPageEntry,
			AssetDisplayPageEntry assetDisplayPageEntry)
		throws ModelListenerException {

		_updateModifiedDate(
			assetDisplayPageEntry.getLayoutPageTemplateEntryId());
	}

	private void _updateModifiedDate(long layoutPageTemplateEntryId) {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				layoutPageTemplateEntryId);

		if (layoutPageTemplateEntry != null) {
			layoutPageTemplateEntry.setModifiedDate(new Date());

			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry);
		}
	}

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}