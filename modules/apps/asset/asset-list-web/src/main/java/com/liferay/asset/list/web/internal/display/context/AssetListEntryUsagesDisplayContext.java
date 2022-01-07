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

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.list.constants.AssetListEntryUsageConstants;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.asset.list.service.AssetListEntryUsageLocalServiceUtil;
import com.liferay.asset.list.util.comparator.AssetListEntryUsageModifiedDateComparator;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Pavel Savinov
 */
public class AssetListEntryUsagesDisplayContext {

	public AssetListEntryUsagesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public int getAllUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			_themeDisplay.getScopeGroupId(),
			PortalUtil.getClassNameId(AssetListEntry.class),
			String.valueOf(getAssetListEntryId()));
	}

	public long getAssetListEntryId() {
		if (_assetListEntryId != null) {
			return _assetListEntryId;
		}

		_assetListEntryId = ParamUtil.getLong(
			_renderRequest, "assetListEntryId");

		return _assetListEntryId;
	}

	public String getAssetListEntryUsageName(
		AssetListEntryUsage assetListEntryUsage) {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			assetListEntryUsage.getPlid());

		if (assetListEntryUsage.getType() ==
				AssetListEntryUsageConstants.TYPE_LAYOUT) {

			if (layout == null) {
				return StringPool.BLANK;
			}

			if (!layout.isDraftLayout()) {
				return layout.getName(_themeDisplay.getLocale());
			}

			return _getName(layout.getName(_themeDisplay.getLocale()));
		}

		long plid = assetListEntryUsage.getPlid();

		if (layout.isDraftLayout()) {
			plid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if (layoutPageTemplateEntry == null) {
			return StringPool.BLANK;
		}

		if (!layout.isDraftLayout()) {
			return layoutPageTemplateEntry.getName();
		}

		return _getName(layoutPageTemplateEntry.getName());
	}

	public String getAssetListEntryUsageTypeLabel(
		AssetListEntryUsage assetListEntryUsage) {

		long type = assetListEntryUsage.getType();

		if (Objects.equals(
				type,
				AssetListEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE)) {

			return "display-page-template";
		}

		if (Objects.equals(
				type, AssetListEntryUsageConstants.TYPE_PAGE_TEMPLATE)) {

			return "page-template";
		}

		return "page";
	}

	public int getDisplayPagesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			_themeDisplay.getScopeGroupId(),
			PortalUtil.getClassNameId(AssetListEntry.class),
			String.valueOf(getAssetListEntryId()),
			AssetListEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE);
	}

	public String getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_renderRequest, "navigation", "all");

		return _navigation;
	}

	public int getPagesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			_themeDisplay.getScopeGroupId(),
			PortalUtil.getClassNameId(AssetListEntry.class),
			String.valueOf(getAssetListEntryId()),
			AssetListEntryUsageConstants.TYPE_LAYOUT);
	}

	public int getPageTemplatesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			_themeDisplay.getScopeGroupId(),
			PortalUtil.getClassNameId(AssetListEntry.class),
			String.valueOf(getAssetListEntryId()),
			AssetListEntryUsageConstants.TYPE_PAGE_TEMPLATE);
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view_asset_list_entry_usages.jsp"
		).setRedirect(
			getRedirect()
		).setParameter(
			"assetListEntryId", getAssetListEntryId()
		).buildPortletURL();
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	public SearchContainer<AssetListEntryUsage> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<AssetListEntryUsage>
			assetListEntryUsagesSearchContainer = new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-collection-usages");

		assetListEntryUsagesSearchContainer.setOrderByCol(_getOrderByCol());

		boolean orderByAsc = false;

		String orderByType = _getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		assetListEntryUsagesSearchContainer.setOrderByComparator(
			new AssetListEntryUsageModifiedDateComparator(orderByAsc));

		assetListEntryUsagesSearchContainer.setOrderByType(orderByType);

		List<AssetListEntryUsage> assetListEntryUsages = null;

		int assetListEntryUsagesCount = 0;

		if (Objects.equals(getNavigation(), "pages")) {
			assetListEntryUsages =
				AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsages(
					_themeDisplay.getScopeGroupId(),
					PortalUtil.getClassNameId(AssetListEntry.class),
					String.valueOf(getAssetListEntryId()),
					AssetListEntryUsageConstants.TYPE_LAYOUT,
					assetListEntryUsagesSearchContainer.getStart(),
					assetListEntryUsagesSearchContainer.getEnd(),
					assetListEntryUsagesSearchContainer.getOrderByComparator());

			assetListEntryUsagesCount = getPagesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "page-templates")) {
			assetListEntryUsages =
				AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsages(
					_themeDisplay.getScopeGroupId(),
					PortalUtil.getClassNameId(AssetListEntry.class),
					String.valueOf(getAssetListEntryId()),
					AssetListEntryUsageConstants.TYPE_PAGE_TEMPLATE,
					assetListEntryUsagesSearchContainer.getStart(),
					assetListEntryUsagesSearchContainer.getEnd(),
					assetListEntryUsagesSearchContainer.getOrderByComparator());

			assetListEntryUsagesCount = getDisplayPagesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "display-page-templates")) {
			assetListEntryUsages =
				AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsages(
					_themeDisplay.getScopeGroupId(),
					PortalUtil.getClassNameId(AssetListEntry.class),
					String.valueOf(getAssetListEntryId()),
					AssetListEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE,
					assetListEntryUsagesSearchContainer.getStart(),
					assetListEntryUsagesSearchContainer.getEnd(),
					assetListEntryUsagesSearchContainer.getOrderByComparator());

			assetListEntryUsagesCount = getDisplayPagesUsageCount();
		}
		else {
			assetListEntryUsages =
				AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsages(
					_themeDisplay.getScopeGroupId(),
					PortalUtil.getClassNameId(AssetListEntry.class),
					String.valueOf(getAssetListEntryId()),
					assetListEntryUsagesSearchContainer.getStart(),
					assetListEntryUsagesSearchContainer.getEnd(),
					assetListEntryUsagesSearchContainer.getOrderByComparator());

			assetListEntryUsagesCount = getAllUsageCount();
		}

		assetListEntryUsagesSearchContainer.setResults(assetListEntryUsages);
		assetListEntryUsagesSearchContainer.setTotal(assetListEntryUsagesCount);

		_searchContainer = assetListEntryUsagesSearchContainer;

		return _searchContainer;
	}

	private String _getName(String name) {
		return StringBundler.concat(
			name, " (", LanguageUtil.get(_themeDisplay.getLocale(), "draft"),
			")");
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_renderRequest, AssetListPortletKeys.ASSET_LIST,
			"entry-usages-order-by-col", "modified-date");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_renderRequest, AssetListPortletKeys.ASSET_LIST,
			"entry-usages-order-by-type", "asc");

		return _orderByType;
	}

	private Long _assetListEntryId;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<AssetListEntryUsage> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}