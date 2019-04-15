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

import com.liferay.asset.constants.AssetEntryUsageConstants;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalServiceUtil;
import com.liferay.asset.util.AssetEntryUsageActionMenuContributor;
import com.liferay.asset.util.AssetEntryUsageActionMenuContributorRegistryUtil;
import com.liferay.asset.util.comparator.AssetEntryUsageModifiedDateComparator;
import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class AssetEntryUsagesDisplayContext {

	public AssetEntryUsagesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)renderRequest.getAttribute(
				ContentPageEditorWebKeys.
					FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
		_fragmentRendererTracker =
			(FragmentRendererTracker)renderRequest.getAttribute(
				FragmentActionKeys.FRAGMENT_RENDERER_TRACKER);
		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
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

		if (assetEntry != null) {
			_assetEntryTitle = assetEntry.getTitle(_themeDisplay.getLocale());
		}

		return _assetEntryTitle;
	}

	public List<DropdownItem> getAssetEntryUsageActionDropdownItems(
		AssetEntryUsage assetEntryUsage) {

		if (assetEntryUsage.getType() ==
				AssetEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE) {

			return Collections.emptyList();
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			getAssetEntryId());

		if (assetEntry == null) {
			return Collections.emptyList();
		}

		AssetEntryUsageActionMenuContributor
			assetEntryUsageActionMenuContributor =
				AssetEntryUsageActionMenuContributorRegistryUtil.
					getAssetEntryUsageActionMenuContributor(
						assetEntry.getClassName());

		if (assetEntryUsageActionMenuContributor == null) {
			return Collections.emptyList();
		}

		return assetEntryUsageActionMenuContributor.
			getAssetEntryUsageActionMenu(
				assetEntryUsage,
				PortalUtil.getHttpServletRequest(_renderRequest));
	}

	public String getAssetEntryUsageName(AssetEntryUsage assetEntryUsage) {
		if (assetEntryUsage.getType() == AssetEntryUsageConstants.TYPE_LAYOUT) {
			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				assetEntryUsage.getPlid());

			if (layout == null) {
				return StringPool.BLANK;
			}

			if (!_isDraft(layout)) {
				return layout.getName(_themeDisplay.getLocale());
			}

			StringBundler sb = new StringBundler(4);

			sb.append(layout.getName(_themeDisplay.getLocale()));
			sb.append(" (");
			sb.append(LanguageUtil.get(_themeDisplay.getLocale(), "draft"));
			sb.append(")");

			return sb.toString();
		}

		long plid = assetEntryUsage.getPlid();

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			assetEntryUsage.getPlid());

		if ((layout.getClassNameId() > 0) && (layout.getClassPK() > 0)) {
			plid = layout.getClassPK();
		}

		layout = LayoutLocalServiceUtil.fetchLayout(plid);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if (layoutPageTemplateEntry == null) {
			return StringPool.BLANK;
		}

		if (!_isDraft(layout)) {
			return layoutPageTemplateEntry.getName();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(layoutPageTemplateEntry.getName());
		sb.append(" (");
		sb.append(LanguageUtil.get(_themeDisplay.getLocale(), "draft"));
		sb.append(")");

		return sb.toString();
	}

	public String getAssetEntryUsageTypeLabel(AssetEntryUsage assetEntryUsage) {
		if (assetEntryUsage.getType() ==
				AssetEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE) {

			return "display-page-template";
		}

		if (assetEntryUsage.getType() == AssetEntryUsageConstants.TYPE_LAYOUT) {
			return "page";
		}

		return "page-template";
	}

	public String getAssetEntryUsageWhereLabel(AssetEntryUsage assetEntryUsage)
		throws PortalException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			_renderRequest);

		if (assetEntryUsage.getContainerType() != PortalUtil.getClassNameId(
				FragmentEntryLink.class)) {

			String portletTitle = PortalUtil.getPortletTitle(
				PortletIdCodec.decodePortletName(
					assetEntryUsage.getContainerKey()),
				_themeDisplay.getLocale());

			return LanguageUtil.format(request, "x-widget", portletTitle);
		}

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLink(
				GetterUtil.getLong(assetEntryUsage.getContainerKey()));

		String name = _getFragmentEntryName(fragmentEntryLink);

		if (Validator.isNull(name)) {
			return StringPool.BLANK;
		}

		if (_getType(fragmentEntryLink) == FragmentConstants.TYPE_COMPONENT) {
			return LanguageUtil.format(request, "x-element", name);
		}

		return LanguageUtil.format(request, "x-section", name);
	}

	public int getDisplayPagesUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			getAssetEntryId(),
			AssetEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE);
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
			getAssetEntryId(), AssetEntryUsageConstants.TYPE_LAYOUT);
	}

	public int getPageTemplatesUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			getAssetEntryId(), AssetEntryUsageConstants.TYPE_PAGE_TEMPLATE);
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_asset_entry_usages.jsp");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"assetEntryId", String.valueOf(getAssetEntryId()));
		portletURL.setParameter("navigation", getNavigation());

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

		SearchContainer assetEntryUsagesSearchContainer = new SearchContainer(
			_renderRequest, getPortletURL(), null,
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
					getAssetEntryId(), AssetEntryUsageConstants.TYPE_LAYOUT,
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetEntryUsagesCount = getPagesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "page-templates")) {
			assetEntryUsages =
				AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					getAssetEntryId(),
					AssetEntryUsageConstants.TYPE_PAGE_TEMPLATE,
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					orderByComparator);

			assetEntryUsagesCount = getPageTemplatesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "display-page-templates")) {
			assetEntryUsages =
				AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					getAssetEntryId(),
					AssetEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE,
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

	private String _getFragmentEntryName(FragmentEntryLink fragmentEntryLink) {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if (fragmentEntry != null) {
			return fragmentEntry.getName();
		}

		String rendererKey = fragmentEntryLink.getRendererKey();

		if (Validator.isNull(rendererKey)) {
			return StringPool.BLANK;
		}

		Map<String, FragmentEntry> fragmentCollectionContributorEntries =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributorEntries();

		FragmentEntry contributedFragmentEntry =
			fragmentCollectionContributorEntries.get(rendererKey);

		if (contributedFragmentEntry != null) {
			return contributedFragmentEntry.getName();
		}

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(
				fragmentEntryLink.getRendererKey());

		if (fragmentRenderer != null) {
			DefaultFragmentRendererContext fragmentRendererContext =
				new DefaultFragmentRendererContext(fragmentEntryLink);

			fragmentRendererContext.setLocale(_themeDisplay.getLocale());

			return fragmentRenderer.getLabel(fragmentRendererContext);
		}

		return StringPool.BLANK;
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

	private int _getType(FragmentEntryLink fragmentEntryLink) {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if (fragmentEntry != null) {
			return fragmentEntry.getType();
		}

		String rendererKey = fragmentEntryLink.getRendererKey();

		if (Validator.isNull(rendererKey)) {
			return 0;
		}

		Map<String, FragmentEntry> fragmentCollectionContributorEntries =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributorEntries();

		FragmentEntry contributedFragmentEntry =
			fragmentCollectionContributorEntries.get(rendererKey);

		if (contributedFragmentEntry != null) {
			return contributedFragmentEntry.getType();
		}

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(
				fragmentEntryLink.getRendererKey());

		if (fragmentRenderer != null) {
			return fragmentRenderer.getType();
		}

		return 0;
	}

	private boolean _isDraft(Layout layout) {
		if (layout.getClassNameId() != PortalUtil.getClassNameId(
				Layout.class.getName())) {

			return false;
		}

		return true;
	}

	private Long _assetEntryId;
	private String _assetEntryTitle;
	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final FragmentRendererTracker _fragmentRendererTracker;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;
	private final ThemeDisplay _themeDisplay;

}