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

package com.liferay.layout.type.controller.asset.display.internal.display.context;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayLayoutTypeControllerWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetDisplayLayoutTypeControllerDisplayContext {

	public AssetDisplayLayoutTypeControllerDisplayContext(
		HttpServletRequest request) {

		_request = request;
	}

	public Map<String, Object> getParameterMap() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetDisplayContributor assetDisplayContributor =
			(AssetDisplayContributor)_request.getAttribute(
				AssetDisplayLayoutTypeControllerWebKeys.
					ASSET_DISPLAY_CONTRIBUTOR);

		AssetEntry assetEntry = (AssetEntry)_request.getAttribute(
			AssetDisplayLayoutTypeControllerWebKeys.ASSET_ENTRY);

		return assetDisplayContributor.getParameterMap(
			assetEntry, themeDisplay.getLocale());
	}

	private final HttpServletRequest _request;

}