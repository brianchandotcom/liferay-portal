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

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.asset.list.service.AssetListEntryUsageLocalServiceUtil;
import com.liferay.asset.list.util.comparator.AssetListEntryUsageModifiedDateComparator;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
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
	}

	public int getAllUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId());
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

		long classNameId = assetListEntryUsage.getClassNameId();

		if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				assetListEntryUsage.getClassPK());

			if (layout == null) {
				return StringPool.BLANK;
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_renderRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return layout.getName(themeDisplay.getLocale());
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntry(assetListEntryUsage.getClassPK());

		if (layoutPageTemplateEntry == null) {
			return StringPool.BLANK;
		}

		return layoutPageTemplateEntry.getName();
	}

	public String getAssetListEntryUsageTypeLabel(
		AssetListEntryUsage assetListEntryUsage) {

		long classNameId = assetListEntryUsage.getClassNameId();

		if (classNameId == PortalUtil.getClassNameId(
				AssetDisplayPageEntry.class)) {

			return "display-page-template";
		}

		if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
			return "page";
		}

		return "page-template";
	}

	public int getDisplayPagesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId(),
			PortalUtil.getClassNameId(AssetDisplayPageEntry.class));
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
			getAssetListEntryId(), PortalUtil.getClassNameId(Layout.class));
	}

	public int getPageTemplatesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId(),
			PortalUtil.getClassNameId(LayoutPageTemplateEntry.class));
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_asset_list_entry_usages.jsp");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"assetListEntryId", String.valueOf(getAssetListEntryId()));

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	public SearchContainer getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer assetListEntryUsagesSearchContainer =
			new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-content-set-usages");

		boolean orderByAsc = false;

		String orderByType = _getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetListEntryUsage> orderByComparator =
			new AssetListEntryUsageModifiedDateComparator(orderByAsc);

		assetListEntryUsagesSearchContainer.setOrderByCol(_getOrderByCol());
		assetListEntryUsagesSearchContainer.setOrderByComparator(
			orderByComparator);
		assetListEntryUsagesSearchContainer.setOrderByType(_getOrderByType());

		List<AssetListEntryUsage> assetListEntryUsages = null;

		int assetListEntryUsagesCount = 0;

		if (Objects.equals(getNavigation(), "pages")) {
			assetListEntryUsages =
				AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsages(
					getAssetListEntryId(),
					PortalUtil.getClassNameId(Layout.class),
					assetListEntryUsagesSearchContainer.getStart(),
					assetListEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetListEntryUsagesCount = getPagesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "page-templates")) {
			assetListEntryUsages =
				AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsages(
					getAssetListEntryId(),
					PortalUtil.getClassNameId(LayoutPageTemplateEntry.class),
					assetListEntryUsagesSearchContainer.getStart(),
					assetListEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetListEntryUsagesCount = getDisplayPagesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "display-page-templates")) {
			assetListEntryUsages =
				AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsages(
					getAssetListEntryId(),
					PortalUtil.getClassNameId(AssetDisplayPageEntry.class),
					assetListEntryUsagesSearchContainer.getStart(),
					assetListEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetListEntryUsagesCount = getDisplayPagesUsageCount();
		}
		else {
			assetListEntryUsages =
				AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsages(
					getAssetListEntryId(),
					assetListEntryUsagesSearchContainer.getStart(),
					assetListEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetListEntryUsagesCount = getAllUsageCount();
		}

		assetListEntryUsagesSearchContainer.setResults(assetListEntryUsages);
		assetListEntryUsagesSearchContainer.setTotal(assetListEntryUsagesCount);

		_searchContainer = assetListEntryUsagesSearchContainer;

		return _searchContainer;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	private Long _assetListEntryId;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;

}