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

package com.liferay.dynamic.data.lists.web.internal.portlet.preferences.updater;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.layout.portlet.preferences.updater.PortletPreferencesUpdater;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "model.class.name=model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord",
	service = PortletPreferencesUpdater.class
)
public class DDLDisplayPortletPreferencesUpdater
	implements PortletPreferencesUpdater {

	@Override
	public void updatePortletPreferences(
			String className, long classPK, String portletId,
			PortletPreferences portletPreferences, ThemeDisplay themeDisplay)
		throws Exception {

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			className, classPK);

		AssetRendererFactory<DDLRecord> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				DDLRecord.class);

		AssetRenderer<DDLRecord> assetRenderer =
			assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

		DDLRecord record = assetRenderer.getAssetObject();

		portletPreferences.setValue(
			"recordSetId", String.valueOf(record.getRecordSetId()));
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

}