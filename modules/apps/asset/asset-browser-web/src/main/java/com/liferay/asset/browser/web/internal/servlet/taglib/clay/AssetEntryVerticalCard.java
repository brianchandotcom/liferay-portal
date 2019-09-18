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

package com.liferay.asset.browser.web.internal.servlet.taglib.clay;

import com.liferay.asset.browser.web.internal.display.context.AssetBrowserDisplayContext;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryVerticalCard implements VerticalCard {

	public AssetEntryVerticalCard(
		AssetEntry assetEntry, RenderRequest renderRequest,
		AssetBrowserDisplayContext assetBrowserDisplayContext) {

		_assetEntry = assetEntry;
		_renderRequest = renderRequest;
		_assetBrowserDisplayContext = assetBrowserDisplayContext;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_assetRenderer = assetEntry.getAssetRenderer();
		_assetRendererFactory =
			_assetBrowserDisplayContext.getAssetRendererFactory();
	}

	@Override
	public Map<String, String> getData() {
		if (_assetBrowserDisplayContext.isMultipleSelection()) {
			return null;
		}

		if (_assetEntry.getEntryId() ==
				_assetBrowserDisplayContext.getRefererAssetEntryId()) {

			return null;
		}

		Map<String, String> data = new HashMap<>();

		data.put("assetclassname", _assetEntry.getClassName());
		data.put(
			"assetclassnameid", String.valueOf(_assetEntry.getClassNameId()));
		data.put("assetclasspk", String.valueOf(_assetEntry.getClassPK()));
		data.put(
			"assettitle", _assetRenderer.getTitle(_themeDisplay.getLocale()));
		data.put(
			"assettype",
			_assetRendererFactory.getTypeName(
				_themeDisplay.getLocale(),
				_assetBrowserDisplayContext.getSubtypeSelectionId()));
		data.put("entityid", String.valueOf(_assetEntry.getEntryId()));

		Group group = GroupLocalServiceUtil.fetchGroup(
			_assetEntry.getGroupId());

		if (group != null) {
			try {
				data.put(
					"groupdescriptivename",
					group.getDescriptiveName(_themeDisplay.getLocale()));
			}
			catch (Exception e) {
			}
		}

		return data;
	}

	@Override
	public String getElementClasses() {
		if (_assetEntry.getEntryId() !=
				_assetBrowserDisplayContext.getRefererAssetEntryId()) {

			return "card-interactive card-interactive-secondary " +
				"selector-button";
		}

		return StringPool.BLANK;
	}

	@Override
	public String getIcon() {
		return _assetRendererFactory.getIconCssClass();
	}

	@Override
	public String getImageSrc() {
		try {
			return _assetRenderer.getThumbnailPath(_renderRequest);
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getSubtitle() {
		if (Validator.isNull(_assetBrowserDisplayContext.getTypeSelection())) {
			return HtmlUtil.escape(
				_assetRendererFactory.getTypeName(
					_themeDisplay.getLocale(),
					_assetBrowserDisplayContext.getSubtypeSelectionId()));
		}

		Group group = GroupLocalServiceUtil.fetchGroup(
			_assetEntry.getGroupId());

		try {
			return HtmlUtil.escape(
				group.getDescriptiveName(_themeDisplay.getLocale()));
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getTitle() {
		return _assetRenderer.getTitle(_themeDisplay.getLocale());
	}

	@Override
	public boolean isSelectable() {
		return _assetBrowserDisplayContext.isMultipleSelection();
	}

	private final AssetBrowserDisplayContext _assetBrowserDisplayContext;
	private final AssetEntry _assetEntry;
	private final AssetRenderer _assetRenderer;
	private final AssetRendererFactory _assetRendererFactory;
	private final RenderRequest _renderRequest;
	private final ThemeDisplay _themeDisplay;

}