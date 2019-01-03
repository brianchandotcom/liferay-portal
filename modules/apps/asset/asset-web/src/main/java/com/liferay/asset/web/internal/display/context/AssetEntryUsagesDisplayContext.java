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

package com.liferay.asset.web.internal.display.context;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalServiceUtil;
import com.liferay.asset.util.comparator.AssetEntryUsageModifiedDateComparator;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
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
public class AssetEntryUsagesDisplayContext {

	public AssetEntryUsagesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public int getAllUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			getAssetEntryId());
	}

	public long getAssetEntryId() {
		if (_assetEntryId != null) {
			return _assetEntryId;
		}

		_assetEntryId = ParamUtil.getLong(_renderRequest, "assetEntryId");

		return _assetEntryId;
	}

	public String getAssetEntryTitle() {
		if (Validator.isNotNull(_assetEntryTitle)) {
			return _assetEntryTitle;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			getAssetEntryId());

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (assetEntry != null) {
			_assetEntryTitle = assetEntry.getTitle(themeDisplay.getLocale());
		}

		return _assetEntryTitle;
	}

	public String getAssetEntryUsageName(AssetEntryUsage assetEntryUsage) {
		long classNameId = assetEntryUsage.getClassNameId();

		if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				assetEntryUsage.getClassPK());

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
				fetchLayoutPageTemplateEntry(assetEntryUsage.getClassPK());

		if (layoutPageTemplateEntry == null) {
			return StringPool.BLANK;
		}

		return layoutPageTemplateEntry.getName();
	}

	public String getAssetEntryUsageTypeLabel(AssetEntryUsage assetEntryUsage) {
		long classNameId = assetEntryUsage.getClassNameId();

		if (classNameId ==
				PortalUtil.getClassNameId(AssetDisplayPageEntry.class)) {

			return "display-page";
		}

		if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
			return "page";
		}

		if (classNameId == PortalUtil.getClassNameId(FragmentEntryLink.class)) {
			return "fragment";
		}

		return "page-template";
	}

	public int getDisplayPagesUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			getAssetEntryId(),
			PortalUtil.getClassNameId(AssetDisplayPageEntry.class));
	}

	public int getFragmentsUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			getAssetEntryId(),
			PortalUtil.getClassNameId(FragmentEntryLink.class));
	}

	public String getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_renderRequest, "navigation", "all");

		return _navigation;
	}

	public int getPagesUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			getAssetEntryId(), PortalUtil.getClassNameId(Layout.class));
	}

	public int getPageTemplatesUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			getAssetEntryId(),
			PortalUtil.getClassNameId(LayoutPageTemplateEntry.class));
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_asset_entry_usages.jsp");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"assetEntryId", String.valueOf(getAssetEntryId()));

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer assetEntryUsagesSearchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-asset-entry-usages");

		boolean orderByAsc = false;

		String orderByType = _getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetEntryUsage> orderByComparator =
			new AssetEntryUsageModifiedDateComparator(orderByAsc);

		assetEntryUsagesSearchContainer.setOrderByCol(_getOrderByCol());
		assetEntryUsagesSearchContainer.setOrderByComparator(orderByComparator);
		assetEntryUsagesSearchContainer.setOrderByType(_getOrderByType());

		List<AssetEntryUsage> assetEntryUsages = null;

		int assetEntryUsagesCount = 0;

		if (Objects.equals(getNavigation(), "pages")) {
			assetEntryUsages =
				AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					getAssetEntryId(), PortalUtil.getClassNameId(Layout.class),
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetEntryUsagesCount = getPagesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "page-templates")) {
			assetEntryUsages =
				AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					getAssetEntryId(),
					PortalUtil.getClassNameId(LayoutPageTemplateEntry.class),
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetEntryUsagesCount = getDisplayPagesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "display-pages")) {
			assetEntryUsages =
				AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					getAssetEntryId(),
					PortalUtil.getClassNameId(AssetDisplayPageEntry.class),
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetEntryUsagesCount = getDisplayPagesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "fragments")) {
			assetEntryUsages =
				AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					getAssetEntryId(),
					PortalUtil.getClassNameId(FragmentEntryLink.class),
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetEntryUsagesCount = getDisplayPagesUsageCount();
		}
		else {
			assetEntryUsages =
				AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					getAssetEntryId(),
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetEntryUsagesCount = getAllUsageCount();
		}

		assetEntryUsagesSearchContainer.setResults(assetEntryUsages);
		assetEntryUsagesSearchContainer.setTotal(assetEntryUsagesCount);

		_searchContainer = assetEntryUsagesSearchContainer;

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

	private Long _assetEntryId;
	private String _assetEntryTitle;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;

}