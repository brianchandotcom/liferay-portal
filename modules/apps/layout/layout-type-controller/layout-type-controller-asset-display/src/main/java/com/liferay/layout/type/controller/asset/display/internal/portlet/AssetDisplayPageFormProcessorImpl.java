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

package com.liferay.layout.type.controller.asset.display.internal.portlet;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.portlet.AssetDisplayPageEntryFormProcessor;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
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

		long assetDisplayPageId = ParamUtil.getLong(
			portletRequest, "assetDisplayPageId");

		if ((displayPageType == AssetDisplayPageConstants.TYPE_DEFAULT) &&
			(assetDisplayPageId == 0)) {

			assetDisplayPageId = _getDefaultLayoutPageTemplateEntryId(
				className, classPK, themeDisplay);
		}

		if (displayPageType == AssetDisplayPageConstants.TYPE_NONE) {
			if (assetDisplayPageEntry != null) {
				_assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntry(
					themeDisplay.getScopeGroupId(), classNameId, classPK);
			}

			return;
		}

		if (assetDisplayPageEntry == null) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				className, portletRequest);

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

	private long _getDefaultLayoutPageTemplateEntryId(
			String className, long classPK, ThemeDisplay themeDisplay)
		throws PortalException {

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

		if (infoDisplayContributor == null) {
			return 0;
		}

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			infoDisplayContributor.getInfoDisplayObjectProvider(classPK);

		if (infoDisplayObjectProvider == null) {
			return 0;
		}

		LayoutPageTemplateEntry defaultAssetDisplayPage =
			_layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				themeDisplay.getScopeGroupId(),
				_portal.getClassNameId(className),
				infoDisplayObjectProvider.getClassTypeId());

		if (defaultAssetDisplayPage == null) {
			return 0;
		}

		return defaultAssetDisplayPage.getLayoutPageTemplateEntryId();
	}

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}