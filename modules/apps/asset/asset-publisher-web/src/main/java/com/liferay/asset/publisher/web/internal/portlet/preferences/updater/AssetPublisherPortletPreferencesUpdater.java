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

package com.liferay.asset.publisher.web.internal.portlet.preferences.updater;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.web.internal.constants.AssetPublisherSelectionStyleConstants;
import com.liferay.asset.publisher.web.internal.helper.AssetPublisherWebHelper;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.portlet.preferences.updater.PortletPreferencesUpdater;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = PortletPreferencesUpdater.class
)
public class AssetPublisherPortletPreferencesUpdater
	implements PortletPreferencesUpdater {

	@Override
	public void updatePortletPreferences(
			String className, long classPK, String portletId,
			PortletPreferences portletPreferences, ThemeDisplay themeDisplay)
		throws Exception {

		portletPreferences.setValue("displayStyle", "full-content");
		portletPreferences.setValue(
			"emailAssetEntryAddedEnabled", Boolean.FALSE.toString());
		portletPreferences.setValue(
			"selectionStyle",
			AssetPublisherSelectionStyleConstants.TYPE_MANUAL);
		portletPreferences.setValue(
			"showAddContentButton", Boolean.FALSE.toString());
		portletPreferences.setValue("showAssetTitle", Boolean.FALSE.toString());
		portletPreferences.setValue("showExtraInfo", Boolean.FALSE.toString());

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			className, classPK);

		_assetPublisherWebHelper.addSelection(
			portletPreferences, assetEntry.getEntryId(), -1,
			assetEntry.getClassName());

		_addLayoutClassedModelUsage(
			assetEntry.getClassNameId(), assetEntry.getClassPK(),
			themeDisplay.getLayout(), portletId);
	}

	private void _addLayoutClassedModelUsage(
		long classNameId, long classPK, Layout layout, String portletId) {

		LayoutClassedModelUsage layoutClassedModelUsage =
			_layoutClassedModelUsageLocalService.fetchLayoutClassedModelUsage(
				classNameId, classPK, portletId,
				_portal.getClassNameId(Portlet.class), layout.getPlid());

		if (layoutClassedModelUsage != null) {
			return;
		}

		_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
			layout.getGroupId(), classNameId, classPK, portletId,
			_portal.getClassNameId(Portlet.class), layout.getPlid(),
			ServiceContextThreadLocal.getServiceContext());
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetPublisherWebHelper _assetPublisherWebHelper;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private Portal _portal;

}