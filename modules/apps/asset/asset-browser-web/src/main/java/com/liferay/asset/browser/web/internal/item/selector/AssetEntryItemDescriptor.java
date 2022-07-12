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

package com.liferay.asset.browser.web.internal.item.selector;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Barbara Cabrera
 */
public class AssetEntryItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public AssetEntryItemDescriptor(
		AssetEntry assetEntry, HttpServletRequest httpServletRequest) {

		_assetEntry = assetEntry;
		_httpServletRequest = httpServletRequest;
	}

	@Override
	public String getIcon() {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					_assetEntry.getClassNameId());

		return assetRendererFactory.getIconCssClass();
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return _assetEntry.getModifiedDate();
	}

	@Override
	public String getPayload() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return JSONUtil.put(
			"assetEntryId", String.valueOf(_assetEntry.getEntryId())
		).put(
			"assetType",
			() -> {
				AssetRendererFactory<?> assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassNameId(
							_assetEntry.getClassNameId());

				return assetRendererFactory.getTypeName(
					themeDisplay.getLocale(), _assetEntry.getClassTypeId());
			}
		).put(
			"className", _assetEntry.getClassName()
		).put(
			"classNameId", _assetEntry.getClassNameId()
		).put(
			"classPK", String.valueOf(_assetEntry.getClassPK())
		).put(
			"groupDescriptiveName",
			() -> {
				Group group = GroupLocalServiceUtil.fetchGroup(
					_assetEntry.getGroupId());

				return group.getDescriptiveName(themeDisplay.getLocale());
			}
		).put(
			"title", _assetEntry.getTitle(themeDisplay.getLocale())
		).toString();
	}

	@Override
	public Integer getStatus() {
		AssetRenderer<?> assetRenderer = _assetEntry.getAssetRenderer();

		return assetRenderer.getStatus();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _assetEntry.getTitle(locale);
	}

	@Override
	public String getUserName() {
		return _assetEntry.getUserName();
	}

	private final AssetEntry _assetEntry;
	private final HttpServletRequest _httpServletRequest;

}