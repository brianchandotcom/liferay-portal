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

import com.liferay.asset.constants.AssetEntryUsageConstants;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
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
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Pavel Savinov
 */
public class AssetEntryUsagesDisplayContext {

	public AssetEntryUsagesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String className, long classPK) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_assetEntry = AssetEntryLocalServiceUtil.fetchEntry(className, classPK);

		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)renderRequest.getAttribute(
				ContentPageEditorWebKeys.
					FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
		_fragmentRendererTracker =
			(FragmentRendererTracker)renderRequest.getAttribute(
				FragmentActionKeys.FRAGMENT_RENDERER_TRACKER);

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _themeDisplay.getLocale(), getClass());
	}

	public int getAllUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			_assetEntry.getEntryId());
	}

	public List<DropdownItem> getAssetEntryUsageActionDropdownItems(
		AssetEntryUsage assetEntryUsage) {

		if (!isShowPreview(assetEntryUsage)) {
			return Collections.emptyList();
		}

		AssetEntryUsageActionMenuContributor
			assetEntryUsageActionMenuContributor =
				AssetEntryUsageActionMenuContributorRegistryUtil.
					getAssetEntryUsageActionMenuContributor(
						_assetEntry.getClassName());

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

			if (!layout.isDraftLayout()) {
				return layout.getName(_themeDisplay.getLocale());
			}

			return StringBundler.concat(
				layout.getName(_themeDisplay.getLocale()), " (",
				LanguageUtil.get(_themeDisplay.getLocale(), "draft"), ")");
		}

		long plid = assetEntryUsage.getPlid();

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			assetEntryUsage.getPlid());

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

		return StringBundler.concat(
			layoutPageTemplateEntry.getName(), " (",
			LanguageUtil.get(_themeDisplay.getLocale(), "draft"), ")");
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

		if ((assetEntryUsage.getContainerType() != PortalUtil.getClassNameId(
				FragmentEntryLink.class)) &&
			(assetEntryUsage.getContainerType() != PortalUtil.getClassNameId(
				LayoutPageTemplateStructure.class))) {

			String portletTitle = PortalUtil.getPortletTitle(
				PortletIdCodec.decodePortletName(
					assetEntryUsage.getContainerKey()),
				_themeDisplay.getLocale());

			return LanguageUtil.format(
				_resourceBundle, "x-widget", portletTitle);
		}

		if (assetEntryUsage.getContainerType() == PortalUtil.getClassNameId(
				FragmentEntryLink.class)) {

			FragmentEntryLink fragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLink(
					GetterUtil.getLong(assetEntryUsage.getContainerKey()));

			String name = _getFragmentEntryName(fragmentEntryLink);

			if (Validator.isNull(name)) {
				return StringPool.BLANK;
			}

			if (_getType(fragmentEntryLink) ==
					FragmentConstants.TYPE_COMPONENT) {

				return LanguageUtil.format(_resourceBundle, "x-element", name);
			}

			return LanguageUtil.format(_resourceBundle, "x-section", name);
		}

		if (assetEntryUsage.getContainerType() == PortalUtil.getClassNameId(
				LayoutPageTemplateStructure.class)) {

			return LanguageUtil.get(_resourceBundle, "section");
		}

		return StringPool.BLANK;
	}

	public int getDisplayPagesUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			_assetEntry.getEntryId(),
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
			_assetEntry.getEntryId(), AssetEntryUsageConstants.TYPE_LAYOUT);
	}

	public int getPageTemplatesUsageCount() {
		return AssetEntryUsageLocalServiceUtil.getAssetEntryUsagesCount(
			_assetEntry.getEntryId(),
			AssetEntryUsageConstants.TYPE_PAGE_TEMPLATE);
	}

	public PortletURL getPortletURL() throws PortletException {
		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		return PortletURLUtil.clone(currentURLObj, _renderResponse);
	}

	public String getPreviewURL(AssetEntryUsage assetEntryUsage)
		throws PortalException {

		String layoutURL = null;

		if (assetEntryUsage.getContainerType() == PortalUtil.getClassNameId(
				FragmentEntryLink.class)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_renderRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				assetEntryUsage.getPlid());

			layoutURL = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);

			layoutURL = HttpUtil.setParameter(
				layoutURL, "previewAssetEntryId",
				String.valueOf(assetEntryUsage.getAssetEntryId()));
			layoutURL = HttpUtil.setParameter(
				layoutURL, "previewAssetEntryType",
				String.valueOf(AssetRendererFactory.TYPE_LATEST));
		}
		else {
			layoutURL = PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					_renderRequest, assetEntryUsage.getContainerKey(),
					assetEntryUsage.getPlid(), PortletRequest.RENDER_PHASE)
			).setParameter(
				"previewAssetEntryId", assetEntryUsage.getAssetEntryId()
			).setParameter(
				"previewAssetEntryType", AssetRendererFactory.TYPE_LATEST
			).buildString();
		}

		String portletURLString = HttpUtil.addParameter(
			layoutURL, "p_l_mode", Constants.PREVIEW);

		return portletURLString + "#portlet_" +
			assetEntryUsage.getContainerKey();
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	public SearchContainer<AssetEntryUsage> getSearchContainer()
		throws PortletException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<AssetEntryUsage> assetEntryUsagesSearchContainer =
			new SearchContainer(
				_renderRequest, getPortletURL(), null,
				"there-are-no-asset-entry-usages");

		assetEntryUsagesSearchContainer.setOrderByCol(_getOrderByCol());

		boolean orderByAsc = false;

		String orderByType = _getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		assetEntryUsagesSearchContainer.setOrderByComparator(
			new AssetEntryUsageModifiedDateComparator(orderByAsc));
		assetEntryUsagesSearchContainer.setOrderByType(_getOrderByType());

		if (Objects.equals(getNavigation(), "pages")) {
			assetEntryUsagesSearchContainer.setResultsAndTotal(
				() -> AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					_assetEntry.getEntryId(),
					AssetEntryUsageConstants.TYPE_LAYOUT,
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					assetEntryUsagesSearchContainer.getOrderByComparator()),
				getPagesUsageCount());
		}
		else if (Objects.equals(getNavigation(), "page-templates")) {
			assetEntryUsagesSearchContainer.setResultsAndTotal(
				() -> AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					_assetEntry.getEntryId(),
					AssetEntryUsageConstants.TYPE_PAGE_TEMPLATE,
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					assetEntryUsagesSearchContainer.getOrderByComparator()),
				getPageTemplatesUsageCount());
		}
		else if (Objects.equals(getNavigation(), "display-page-templates")) {
			assetEntryUsagesSearchContainer.setResultsAndTotal(
				() -> AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					_assetEntry.getEntryId(),
					AssetEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE,
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					assetEntryUsagesSearchContainer.getOrderByComparator()),
				getDisplayPagesUsageCount());
		}
		else {
			assetEntryUsagesSearchContainer.setResultsAndTotal(
				() -> AssetEntryUsageLocalServiceUtil.getAssetEntryUsages(
					_assetEntry.getEntryId(),
					assetEntryUsagesSearchContainer.getStart(),
					assetEntryUsagesSearchContainer.getEnd(),
					assetEntryUsagesSearchContainer.getOrderByComparator()),
				getAllUsageCount());
		}

		_searchContainer = assetEntryUsagesSearchContainer;

		return _searchContainer;
	}

	public boolean isShowPreview(AssetEntryUsage assetEntryUsage) {
		if (assetEntryUsage.getType() == AssetEntryUsageConstants.TYPE_LAYOUT) {
			return true;
		}

		if ((assetEntryUsage.getType() ==
				AssetEntryUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE) ||
			(assetEntryUsage.getType() !=
				AssetEntryUsageConstants.TYPE_PAGE_TEMPLATE)) {

			return false;
		}

		long plid = assetEntryUsage.getPlid();

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout.isDraftLayout()) {
			plid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if ((layoutPageTemplateEntry == null) ||
			(layoutPageTemplateEntry.getType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

			return false;
		}

		return true;
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

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries(
				_themeDisplay.getLocale());

		FragmentEntry contributedFragmentEntry = fragmentEntries.get(
			rendererKey);

		if (contributedFragmentEntry != null) {
			return contributedFragmentEntry.getName();
		}

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(
				fragmentEntryLink.getRendererKey());

		if (fragmentRenderer != null) {
			return fragmentRenderer.getLabel(_themeDisplay.getLocale());
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

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries();

		FragmentEntry contributedFragmentEntry = fragmentEntries.get(
			rendererKey);

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

	private final AssetEntry _assetEntry;
	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final FragmentRendererTracker _fragmentRendererTracker;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ResourceBundle _resourceBundle;
	private SearchContainer<AssetEntryUsage> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}