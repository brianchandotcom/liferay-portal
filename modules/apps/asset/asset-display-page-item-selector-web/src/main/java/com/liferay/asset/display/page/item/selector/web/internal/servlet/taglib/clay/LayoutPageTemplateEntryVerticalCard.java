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

package com.liferay.asset.display.page.item.selector.web.internal.servlet.taglib.clay;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.display.page.item.selector.web.internal.constants.AssetDisplayPageItemSelectorWebKeys;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateEntryVerticalCard implements VerticalCard {

	public LayoutPageTemplateEntryVerticalCard(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;

		_assetDisplayContributorTracker =
			(AssetDisplayContributorTracker)renderRequest.getAttribute(
				AssetDisplayPageItemSelectorWebKeys.
					ASSET_DISPLAY_CONTRIBUTOR_TRACKER);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public Map<String, String> getData() {
		Map<String, String> data = new HashMap<>();

		data.put(
			"id",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		data.put("name", _layoutPageTemplateEntry.getName());
		data.put("type", "asset-display-page");

		return data;
	}

	@Override
	public String getElementClasses() {
		return "layout-page-template-entry";
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getSubtitle() {
		String typeLabel = _getTypeLabel();

		if (Validator.isNull(typeLabel)) {
			return StringPool.DASH;
		}

		String subtypeLabel = StringPool.BLANK;

		try {
			subtypeLabel = _getSubtypeLabel();
		}
		catch (Exception e) {
		}

		if (Validator.isNull(subtypeLabel)) {
			return typeLabel;
		}

		return typeLabel + " - " + subtypeLabel;
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateEntry.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private String _getSubtypeLabel() throws PortalException {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_layoutPageTemplateEntry.getClassName());

		if ((assetRendererFactory == null) ||
			(_layoutPageTemplateEntry.getClassTypeId() <= 0)) {

			return StringPool.BLANK;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(
			_layoutPageTemplateEntry.getClassTypeId(),
			_themeDisplay.getLocale());

		return classType.getName();
	}

	private String _getTypeLabel() {
		AssetDisplayContributor assetDisplayContributor =
			_assetDisplayContributorTracker.getAssetDisplayContributor(
				_layoutPageTemplateEntry.getClassName());

		if (assetDisplayContributor == null) {
			return StringPool.BLANK;
		}

		return assetDisplayContributor.getLabel(_themeDisplay.getLocale());
	}

	private final AssetDisplayContributorTracker
		_assetDisplayContributorTracker;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final ThemeDisplay _themeDisplay;

}