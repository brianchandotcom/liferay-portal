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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
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
	public String getCssClass() {
		if (_assetEntry.getEntryId() !=
				_assetBrowserDisplayContext.getRefererAssetEntryId()) {

			return "card-interactive card-interactive-secondary " +
				"selector-button";
		}

		return StringPool.BLANK;
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		if (_assetBrowserDisplayContext.isMultipleSelection() ||
			(_assetEntry.getEntryId() ==
				_assetBrowserDisplayContext.getRefererAssetEntryId())) {

			return null;
		}

		Map<String, String> data = HashMapBuilder.put(
			"data-assetclassname", _assetEntry.getClassName()
		).put(
			"data-assetclassnameid",
			String.valueOf(_assetEntry.getClassNameId())
		).put(
			"data-assetclasspk", String.valueOf(_assetEntry.getClassPK())
		).put(
			"data-assettitle",
			_assetRenderer.getTitle(_themeDisplay.getLocale())
		).put(
			"data-assettype",
			_assetRendererFactory.getTypeName(
				_themeDisplay.getLocale(),
				_assetBrowserDisplayContext.getSubtypeSelectionId())
		).put(
			"data-entityid", String.valueOf(_assetEntry.getEntryId())
		).build();

		Group group = GroupLocalServiceUtil.fetchGroup(
			_assetEntry.getGroupId());

		if (group != null) {
			try {
				data.put(
					"data-groupdescriptivename",
					group.getDescriptiveName(_themeDisplay.getLocale()));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}

		return data;
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	@Override
	public List<LabelItem> getLabels() {
		if (!_assetBrowserDisplayContext.isShowAssetEntryStatus()) {
			return Collections.emptyList();
		}

		return LabelItemListBuilder.add(
			labelItem -> labelItem.setStatus(_assetRenderer.getStatus())
		).build();
	}

	@Override
	public String getSubtitle() {
		if (Validator.isNull(_assetBrowserDisplayContext.getTypeSelection())) {
			return HtmlUtil.escape(
				_assetRendererFactory.getTypeName(
					_themeDisplay.getLocale(),
					_assetBrowserDisplayContext.getSubtypeSelectionId()));
		}

		if (_assetBrowserDisplayContext.isSearchEverywhere()) {
			Group group = GroupLocalServiceUtil.fetchGroup(
				_assetEntry.getGroupId());

			try {
				return HtmlUtil.escape(
					group.getDescriptiveName(_themeDisplay.getLocale()));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
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

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryVerticalCard.class);

	private final AssetBrowserDisplayContext _assetBrowserDisplayContext;
	private final AssetEntry _assetEntry;
	private final AssetRenderer<?> _assetRenderer;
	private final AssetRendererFactory<?> _assetRendererFactory;
	private final RenderRequest _renderRequest;
	private final ThemeDisplay _themeDisplay;

}