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

import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageLayoutTypeControllerDisplayContext {

	public DisplayPageLayoutTypeControllerDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_infoDisplayObjectProvider =
			(InfoDisplayObjectProvider<Object>)httpServletRequest.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);
	}

	public AssetRendererFactory<?> getAssetRendererFactory() {
		if (_infoDisplayObjectProvider == null) {
			return null;
		}

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassNameId(
				_infoDisplayObjectProvider.getClassNameId());
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

	public InfoDisplayObjectProvider<?> getInfoDisplayObjectProvider() {
		return _infoDisplayObjectProvider;
	}

	private final HttpServletRequest _httpServletRequest;
	private final InfoDisplayObjectProvider<Object> _infoDisplayObjectProvider;

}