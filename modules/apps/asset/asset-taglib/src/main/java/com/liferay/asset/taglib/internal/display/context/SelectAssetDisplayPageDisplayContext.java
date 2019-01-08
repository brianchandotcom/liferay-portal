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

package com.liferay.asset.taglib.internal.display.context;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.item.selector.criterion.AssetDisplayPageSelectorCriterion;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.taglib.internal.item.selector.ItemSelectorUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class SelectAssetDisplayPageDisplayContext {

	public SelectAssetDisplayPageDisplayContext(
		HttpServletRequest request, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_request = request;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_classNameId = GetterUtil.getLong(
			_request.getAttribute(
				"liferay-asset:select-asset-display-page:classNameId"));
		_classPK = GetterUtil.getLong(
			_request.getAttribute(
				"liferay-asset:select-asset-display-page:classPK"));
		_classTypeId = GetterUtil.getLong(
			_request.getAttribute(
				"liferay-asset:select-asset-display-page:classTypeId"));
		_eventName = GetterUtil.getString(
			_request.getAttribute(
				"liferay-asset:select-asset-display-page:eventName"),
			_liferayPortletResponse.getNamespace() + "selectDisplayPage");
		_groupId = GetterUtil.getLong(
			_request.getAttribute(
				"liferay-asset:select-asset-display-page:groupId"));
		_showPortletLayouts = GetterUtil.getBoolean(
			_request.getAttribute(
				"liferay-asset:select-asset-display-page:showPortletLayouts"));
	}

	public long getAssetDisplayPageId() {
		if (_assetDisplayPageId != null) {
			return _assetDisplayPageId;
		}

		AssetDisplayPageEntry assetDisplayPageEntry =
			_getAssetDisplayPageEntry();

		if (assetDisplayPageEntry != null) {
			_assetDisplayPageId =
				assetDisplayPageEntry.getLayoutPageTemplateEntryId();

			return _assetDisplayPageId;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					_groupId, _classNameId, _classTypeId);

		if (layoutPageTemplateEntry != null) {
			_assetDisplayPageId =
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId();
		}
		else {
			_assetDisplayPageId = 0L;
		}

		return _assetDisplayPageId;
	}

	public String getAssetDisplayPageItemSelectorURL() throws PortalException {
		ItemSelector itemSelector = ItemSelectorUtil.getItemSelector();

		List<ItemSelectorCriterion> criteria = new ArrayList<>();

		AssetDisplayPageSelectorCriterion assetDisplayPageSelectorCriterion =
			new AssetDisplayPageSelectorCriterion();

		assetDisplayPageSelectorCriterion.setClassNameId(_classNameId);
		assetDisplayPageSelectorCriterion.setClassTypeId(_classTypeId);

		List<ItemSelectorReturnType>
			desiredAssetDisplayPageItemSelectorReturnTypes = new ArrayList<>();

		desiredAssetDisplayPageItemSelectorReturnTypes.add(
			new UUIDItemSelectorReturnType());

		assetDisplayPageSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredAssetDisplayPageItemSelectorReturnTypes);

		criteria.add(assetDisplayPageSelectorCriterion);

		if (_showPortletLayouts) {
			LayoutItemSelectorCriterion layoutItemSelectorCriterion =
				new LayoutItemSelectorCriterion();

			layoutItemSelectorCriterion.setCheckDisplayPage(true);
			layoutItemSelectorCriterion.setShowHiddenPages(true);

			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
				new ArrayList<>();

			desiredItemSelectorReturnTypes.add(
				new UUIDItemSelectorReturnType());

			layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
				desiredItemSelectorReturnTypes);

			criteria.add(layoutItemSelectorCriterion);
		}

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest),
			_eventName, criteria.toArray(new ItemSelectorCriterion[0]));

		itemSelectorURL.setParameter("layoutUuid", getLayoutUuid());

		return itemSelectorURL.toString();
	}

	public String getAssetDisplayPageName() throws Exception {
		String assetDisplayPageName = _getAssetDisplayPageName();

		if (Validator.isNotNull(assetDisplayPageName)) {
			return assetDisplayPageName;
		}

		String layoutUuid = getLayoutUuid();

		if (Validator.isNull(layoutUuid)) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layoutUuid, themeDisplay.getSiteGroupId(), false);

		if (selLayout == null) {
			selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layoutUuid, themeDisplay.getSiteGroupId(), true);
		}

		if (selLayout != null) {
			return _getLayoutBreadcrumb(selLayout);
		}

		return StringPool.BLANK;
	}

	public String getAssetTypeName() throws PortalException {
		if (Validator.isNotNull(_assetTypeName)) {
			return _assetTypeName;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(_classNameId);

		_assetTypeName = assetRendererFactory.getTypeName(
			themeDisplay.getLocale());

		if (_classTypeId > 0) {
			ClassTypeReader classTypeReader =
				assetRendererFactory.getClassTypeReader();

			ClassType classType = classTypeReader.getClassType(
				_classTypeId, themeDisplay.getLocale());

			_assetTypeName = classType.getName();
		}

		return _assetTypeName;
	}

	public String getDefaultAssetDisplayPageName() {
		if (_defaultAssetDisplayPageName != null) {
			return _defaultAssetDisplayPageName;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = null;

		layoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					_groupId, _classNameId, _classTypeId);

		if (layoutPageTemplateEntry != null) {
			_defaultAssetDisplayPageName = layoutPageTemplateEntry.getName();
		}

		return _defaultAssetDisplayPageName;
	}

	public String getEventName() {
		return _eventName;
	}

	public String getLayoutUuid() throws PortalException {
		if (_classPK == 0) {
			return null;
		}

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(_classNameId);

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			PortalUtil.getClassName(_classNameId), _classPK);

		return assetEntry.getLayoutUuid();
	}

	public String getURLViewInContext() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(_classNameId);

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			_classPK);

		return assetRenderer.getURLViewInContext(
			_liferayPortletRequest, _liferayPortletResponse,
			themeDisplay.getURLCurrent());
	}

	public boolean isAssetDisplayPageTypeDefault() {
		if (_getAssetDisplayPageType() ==
				AssetDisplayPageConstants.TYPE_DEFAULT) {

			return true;
		}

		return false;
	}

	public boolean isAssetDisplayPageTypeNone() {
		if (_getAssetDisplayPageType() == AssetDisplayPageConstants.TYPE_NONE) {
			return true;
		}

		return false;
	}

	public boolean isAssetDisplayPageTypeSpecific() {
		if (_getAssetDisplayPageType() ==
				AssetDisplayPageConstants.TYPE_SPECIFIC) {

			return true;
		}

		return false;
	}

	public boolean isURLViewInContext() throws Exception {
		if (_classPK == 0) {
			return false;
		}

		if (Validator.isNull(getLayoutUuid())) {
			return false;
		}

		if (Validator.isNull(getURLViewInContext())) {
			return false;
		}

		return true;
	}

	private AssetDisplayPageEntry _getAssetDisplayPageEntry() {
		if (_assetDisplayPageEntry != null) {
			return _assetDisplayPageEntry;
		}

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryLocalServiceUtil.fetchAssetDisplayPageEntry(
				_groupId, _classNameId, _classPK);

		if (assetDisplayPageEntry != null) {
			_assetDisplayPageEntry = assetDisplayPageEntry;
		}

		return _assetDisplayPageEntry;
	}

	private String _getAssetDisplayPageName() {
		long assetDisplayPageId = getAssetDisplayPageId();

		if (assetDisplayPageId == 0) {
			return StringPool.BLANK;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntry(assetDisplayPageId);

		if (layoutPageTemplateEntry == null) {
			return StringPool.BLANK;
		}

		return layoutPageTemplateEntry.getName();
	}

	private int _getAssetDisplayPageType() {
		if (_displayPageType != null) {
			return _displayPageType;
		}

		if (_classPK == 0) {
			_displayPageType = AssetDisplayPageConstants.TYPE_DEFAULT;

			return _displayPageType;
		}

		AssetDisplayPageEntry assetDisplayPageEntry =
			_getAssetDisplayPageEntry();

		if (assetDisplayPageEntry == null) {
			_displayPageType = AssetDisplayPageConstants.TYPE_NONE;
		}
		else {
			_displayPageType = assetDisplayPageEntry.getType();
		}

		return _displayPageType;
	}

	private String _getLayoutBreadcrumb(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(_request, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(_request, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}

	private AssetDisplayPageEntry _assetDisplayPageEntry;
	private Long _assetDisplayPageId;
	private String _assetTypeName;
	private final Long _classNameId;
	private Long _classPK;
	private final Long _classTypeId;
	private String _defaultAssetDisplayPageName;
	private Integer _displayPageType;
	private final String _eventName;
	private final long _groupId;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final boolean _showPortletLayouts;

}