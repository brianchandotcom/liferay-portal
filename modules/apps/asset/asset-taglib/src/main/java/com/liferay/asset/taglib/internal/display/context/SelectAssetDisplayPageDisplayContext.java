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
import com.liferay.asset.display.page.util.AssetDisplayPageHelper;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.taglib.internal.info.display.contributor.InfoDisplayContributorTrackerUtil;
import com.liferay.asset.taglib.internal.item.selector.ItemSelectorUtil;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
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
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;

		_liferayPortletResponse = liferayPortletResponse;

		_classNameId = GetterUtil.getLong(
			_httpServletRequest.getAttribute(
				"liferay-asset:select-asset-display-page:classNameId"));
		_classPK = GetterUtil.getLong(
			_httpServletRequest.getAttribute(
				"liferay-asset:select-asset-display-page:classPK"));
		_classTypeId = GetterUtil.getLong(
			_httpServletRequest.getAttribute(
				"liferay-asset:select-asset-display-page:classTypeId"));
		_eventName = GetterUtil.getString(
			_httpServletRequest.getAttribute(
				"liferay-asset:select-asset-display-page:eventName"),
			_liferayPortletResponse.getNamespace() + "selectDisplayPage");
		_groupId = GetterUtil.getLong(
			_httpServletRequest.getAttribute(
				"liferay-asset:select-asset-display-page:groupId"));
		_showPortletLayouts = GetterUtil.getBoolean(
			_httpServletRequest.getAttribute(
				"liferay-asset:select-asset-display-page:showPortletLayouts"));
		_showViewInContextLink = GetterUtil.getBoolean(
			_httpServletRequest.getAttribute(
				"liferay-asset:select-asset-display-page:" +
					"showViewInContextLink"));
	}

	public long getAssetDisplayPageId() {
		if (_assetDisplayPageId != null) {
			return _assetDisplayPageId;
		}

		_assetDisplayPageId = 0L;

		AssetDisplayPageEntry assetDisplayPageEntry =
			_getAssetDisplayPageEntry();

		if (assetDisplayPageEntry != null) {
			_assetDisplayPageId =
				assetDisplayPageEntry.getLayoutPageTemplateEntryId();
		}

		return _assetDisplayPageId;
	}

	public String getAssetDisplayPageItemSelectorURL() throws PortalException {
		ItemSelector itemSelector = ItemSelectorUtil.getItemSelector();

		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>();

		AssetDisplayPageSelectorCriterion assetDisplayPageSelectorCriterion =
			new AssetDisplayPageSelectorCriterion();

		assetDisplayPageSelectorCriterion.setClassNameId(_classNameId);
		assetDisplayPageSelectorCriterion.setClassTypeId(_classTypeId);
		assetDisplayPageSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new UUIDItemSelectorReturnType());

		itemSelectorCriteria.add(assetDisplayPageSelectorCriterion);

		if (_showPortletLayouts) {
			LayoutItemSelectorCriterion layoutItemSelectorCriterion =
				new LayoutItemSelectorCriterion();

			layoutItemSelectorCriterion.setCheckDisplayPage(true);
			layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
				new UUIDItemSelectorReturnType());
			layoutItemSelectorCriterion.setShowHiddenPages(true);

			itemSelectorCriteria.add(layoutItemSelectorCriterion);
		}

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest),
			_eventName,
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));

		itemSelectorURL.setParameter("layoutUuid", getLayoutUuid());

		return itemSelectorURL.toString();
	}

	public int getAssetDisplayPageType() {
		if (_displayPageType != null) {
			return _displayPageType;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getDefaultLayoutPageTemplateEntry();

		if ((_classPK == 0) && (layoutPageTemplateEntry != null)) {
			_displayPageType = AssetDisplayPageConstants.TYPE_DEFAULT;

			return _displayPageType;
		}

		AssetDisplayPageEntry assetDisplayPageEntry =
			_getAssetDisplayPageEntry();

		if (assetDisplayPageEntry == null) {
			if (Validator.isNull(getLayoutUuid())) {
				_displayPageType = AssetDisplayPageConstants.TYPE_DEFAULT;
			}
			else {
				_displayPageType = AssetDisplayPageConstants.TYPE_SPECIFIC;
			}
		}
		else {
			_displayPageType = assetDisplayPageEntry.getType();
		}

		return _displayPageType;
	}

	public String getDefaultAssetDisplayPageName() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getDefaultLayoutPageTemplateEntry();

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry.getName();
		}

		return null;
	}

	public String getEventName() {
		return _eventName;
	}

	public String getLayoutUuid() {
		if (_classPK == 0) {
			return null;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			_classNameId, _classPK);

		if (assetEntry == null) {
			return null;
		}

		return assetEntry.getLayoutUuid();
	}

	public String getSpecificAssetDisplayPageName() throws Exception {
		String assetDisplayPageName = _getAssetDisplayPageName();

		if (Validator.isNotNull(assetDisplayPageName)) {
			return assetDisplayPageName;
		}

		String layoutUuid = getLayoutUuid();

		if (Validator.isNull(layoutUuid)) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
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

		return null;
	}

	public String getURLViewInContext() {
		if (_classPK <= 0) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(_classNameId);

		try {
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				_classPK);

			String viewInContextURL = assetRenderer.getURLViewInContext(
				_liferayPortletRequest, _liferayPortletResponse,
				themeDisplay.getURLCurrent());

			return HttpUtil.addParameter(
				viewInContextURL, "p_l_mode", Constants.PREVIEW);
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	public boolean isAssetDisplayPageTypeDefault() {
		if (getAssetDisplayPageType() ==
				AssetDisplayPageConstants.TYPE_DEFAULT) {

			return true;
		}

		return false;
	}

	public boolean isAssetDisplayPageTypeSpecific() {
		if (getAssetDisplayPageType() ==
				AssetDisplayPageConstants.TYPE_SPECIFIC) {

			return true;
		}

		return false;
	}

	public boolean isShowViewInContextLink() {
		if (!_showViewInContextLink) {
			return false;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			InfoDisplayContributorTracker infoDisplayContributorTracker =
				InfoDisplayContributorTrackerUtil.
					getInfoDisplayContributorTracker();

			InfoDisplayContributor infoDisplayContributor =
				infoDisplayContributorTracker.getInfoDisplayContributor(
					PortalUtil.getClassName(_classNameId));

			if (infoDisplayContributor == null) {
				return false;
			}

			InfoDisplayObjectProvider infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(_classPK);

			if (infoDisplayObjectProvider == null) {
				return false;
			}

			if (!AssetDisplayPageHelper.hasAssetDisplayPage(
					themeDisplay.getScopeGroupId(),
					infoDisplayObjectProvider.getClassNameId(),
					infoDisplayObjectProvider.getClassPK(),
					infoDisplayObjectProvider.getClassTypeId())) {

				return false;
			}
		}
		catch (Exception e) {
		}

		return true;
	}

	public boolean isURLViewInContext() throws Exception {
		if (_classPK == 0) {
			return false;
		}

		if (Validator.isNull(getLayoutUuid()) &&
			Validator.isNull(getURLViewInContext())) {

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

	private LayoutPageTemplateEntry _getDefaultLayoutPageTemplateEntry() {
		if (_defaultLayoutPageTemplateEntry != null) {
			return _defaultLayoutPageTemplateEntry;
		}

		_defaultLayoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					_groupId, _classNameId, _classTypeId);

		return _defaultLayoutPageTemplateEntry;
	}

	private String _getLayoutBreadcrumb(Layout layout) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(_httpServletRequest, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(_httpServletRequest, "public-pages"));
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
	private final Long _classNameId;
	private Long _classPK;
	private final Long _classTypeId;
	private LayoutPageTemplateEntry _defaultLayoutPageTemplateEntry;
	private Integer _displayPageType;
	private final String _eventName;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final boolean _showPortletLayouts;
	private final boolean _showViewInContextLink;

}