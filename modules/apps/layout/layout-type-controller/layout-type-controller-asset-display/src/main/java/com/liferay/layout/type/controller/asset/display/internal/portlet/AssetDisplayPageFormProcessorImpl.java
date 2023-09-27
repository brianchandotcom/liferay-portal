/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.asset.display.internal.portlet;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.portlet.AssetDisplayPageEntryFormProcessor;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AssetDisplayPageEntryFormProcessor.class)
public class AssetDisplayPageFormProcessorImpl
	implements AssetDisplayPageEntryFormProcessor {

	@Override
	public void process(
			String className, long classPK, PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long classNameId = _portal.getClassNameId(className);

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				themeDisplay.getScopeGroupId(), classNameId, classPK);

		int displayPageType = ParamUtil.getInteger(
			portletRequest, "displayPageType",
			AssetDisplayPageConstants.TYPE_DEFAULT);

		String layoutUuid = ParamUtil.getString(portletRequest, "layoutUuid");

		if ((displayPageType == AssetDisplayPageConstants.TYPE_DEFAULT) ||
			((displayPageType == AssetDisplayPageConstants.TYPE_SPECIFIC) &&
			 Validator.isNotNull(layoutUuid))) {

			if (assetDisplayPageEntry != null) {
				_assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntry(
					themeDisplay.getScopeGroupId(), classNameId, classPK);
			}

			return;
		}

		long assetDisplayPageId = ParamUtil.getLong(
			portletRequest, "assetDisplayPageId");

		if (displayPageType == AssetDisplayPageConstants.TYPE_NONE) {
			assetDisplayPageId = 0;
		}

		if (assetDisplayPageEntry == null) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				portletRequest);

			_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				classNameId, classPK, assetDisplayPageId, displayPageType,
				serviceContext);

			return;
		}

		_assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(
			assetDisplayPageEntry.getAssetDisplayPageEntryId(),
			assetDisplayPageId, displayPageType);
	}

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private Portal _portal;

}