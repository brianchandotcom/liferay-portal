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

package com.liferay.layout.type.controller.display.page.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageLayoutTypeControllerDisplayContext {

	public DisplayPageLayoutTypeControllerDisplayContext(
			HttpServletRequest httpServletRequest,
			InfoItemServiceTracker infoItemServiceTracker)
		throws Exception {

		_httpServletRequest = httpServletRequest;
		_infoItemServiceTracker = infoItemServiceTracker;

		long assetEntryId = ParamUtil.getLong(
			_httpServletRequest, "assetEntryId");

		Object infoItem = httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM);
		InfoItemDetails infoItemDetails =
			(InfoItemDetails)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM_DETAILS);

		if ((assetEntryId > 0) && (infoItem == null) &&
			(infoItemDetails == null)) {

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				assetEntryId);

			String className = assetEntry.getClassName();

			if (Objects.equals(className, DLFileEntry.class.getName())) {
				className = FileEntry.class.getName();
			}

			InfoItemObjectProvider<Object> infoItemObjectProvider =
				(InfoItemObjectProvider<Object>)
					infoItemServiceTracker.getFirstInfoItemService(
						InfoItemObjectProvider.class, className);

			InfoItemIdentifier infoItemIdentifier =
				new ClassPKInfoItemIdentifier(assetEntry.getClassPK());

			infoItemIdentifier.setVersion(InfoItemIdentifier.VERSION_LATEST);

			infoItem = infoItemObjectProvider.getInfoItem(infoItemIdentifier);

			AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

			if (assetRenderer != null) {
				InfoItemDetailsProvider infoItemDetailsProvider =
					infoItemServiceTracker.getFirstInfoItemService(
						InfoItemDetailsProvider.class, className);

				infoItemDetails = infoItemDetailsProvider.getInfoItemDetails(
					assetRenderer.getAssetObject());
			}

			_httpServletRequest.setAttribute(
				InfoDisplayWebKeys.INFO_ITEM_FIELD_VALUES_PROVIDER,
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class, className));

			_httpServletRequest.setAttribute(
				WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
		}

		_infoItem = infoItem;
		_infoItemDetails = infoItemDetails;
	}

	public AssetRendererFactory<?> getAssetRendererFactory() {
		if (_infoItemDetails == null) {
			return null;
		}

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassNameId(
				PortalUtil.getClassNameId(_infoItemDetails.getClassName()));
	}

	public Map<String, Object> getInfoDisplayFieldsValues() {
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			(InfoItemFieldValuesProvider)_httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM_FIELD_VALUES_PROVIDER);

		if (infoItemFieldValuesProvider == null) {
			return null;
		}

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(_infoItem);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return infoItemFieldValues.getMap(themeDisplay.getLocale());
	}

	public boolean hasPermission(
			PermissionChecker permissionChecker, String actionId)
		throws Exception {

		if (_infoItemDetails == null) {
			return true;
		}

		InfoItemPermissionProvider infoItemPermissionProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemPermissionProvider.class,
				_infoItemDetails.getClassName());

		if (infoItemPermissionProvider != null) {
			return infoItemPermissionProvider.hasPermission(
				permissionChecker, _infoItem, actionId);
		}

		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactory();

		if (assetRendererFactory != null) {
			InfoItemReference infoItemReference =
				_infoItemDetails.getInfoItemReference();

			return assetRendererFactory.hasPermission(
				permissionChecker, infoItemReference.getClassPK(), actionId);
		}

		return true;
	}

	private final HttpServletRequest _httpServletRequest;
	private final Object _infoItem;
	private final InfoItemDetails _infoItemDetails;
	private final InfoItemServiceTracker _infoItemServiceTracker;

}