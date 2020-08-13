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
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageLayoutTypeControllerDisplayContext {

	public DisplayPageLayoutTypeControllerDisplayContext(
		HttpServletRequest httpServletRequest,
		InfoItemServiceTracker infoItemServiceTracker) {

		_httpServletRequest = httpServletRequest;
		_infoItemServiceTracker = infoItemServiceTracker;

		_infoItem = httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM);
		_infoItemDetails = (InfoItemDetails)httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM_DETAILS);
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

		Object infoItem = _httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM);

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(infoItem);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return infoItemFieldValues.getMap(themeDisplay.getLocale());
	}

	public boolean hasPermission(
			PermissionChecker permissionChecker, String actionId)
		throws Exception {

		if (_infoItemDetails == null) {
			return false;
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