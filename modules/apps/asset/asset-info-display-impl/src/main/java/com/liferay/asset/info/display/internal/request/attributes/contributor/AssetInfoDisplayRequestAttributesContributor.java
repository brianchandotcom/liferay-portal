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

package com.liferay.asset.info.display.internal.request.attributes.contributor;

import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.request.attributes.contributor.InfoDisplayRequestAttributesContributor;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, service = InfoDisplayRequestAttributesContributor.class
)
public class AssetInfoDisplayRequestAttributesContributor
	implements InfoDisplayRequestAttributesContributor {

	@Override
	public void addAttributes(HttpServletRequest httpServletRequest) {
		Object layoutAssetEntry = httpServletRequest.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);

		if (!(layoutAssetEntry instanceof AssetEntry)) {
			return;
		}

		AssetEntry assetEntry = (AssetEntry)layoutAssetEntry;

		if (assetEntry != null) {
			return;
		}

		long assetEntryId = ParamUtil.getLong(
			httpServletRequest, "assetEntryId");

		assetEntry = _assetEntryLocalService.fetchEntry(assetEntryId);

		if (assetEntry == null) {
			return;
		}

		InfoDisplayContributor<?> infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				assetEntry.getClassName());

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR,
			infoDisplayContributor);

		try {
			InfoDisplayObjectProvider<?> infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(
					assetEntry.getClassPK());

			if (infoDisplayObjectProvider != null) {
				httpServletRequest.setAttribute(
					AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER,
					infoDisplayObjectProvider);
			}

			InfoItemObjectProvider<?> infoItemObjectProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class, assetEntry.getClassName());

			if (infoItemObjectProvider != null) {
				Object infoItem = infoItemObjectProvider.getInfoItem(
					assetEntry.getClassPK());

				httpServletRequest.setAttribute(
					InfoDisplayWebKeys.INFO_ITEM, infoItem);

				InfoItemDetailsProvider infoItemDetailsProvider =
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemDetailsProvider.class,
						assetEntry.getClassName());

				httpServletRequest.setAttribute(
					InfoDisplayWebKeys.INFO_ITEM_DETAILS,
					infoItemDetailsProvider.getInfoItemDetails(infoItem));
			}
		}
		catch (Exception exception) {
			_log.error("Unable to get info display object provider", exception);
		}

		httpServletRequest.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetInfoDisplayRequestAttributesContributor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}