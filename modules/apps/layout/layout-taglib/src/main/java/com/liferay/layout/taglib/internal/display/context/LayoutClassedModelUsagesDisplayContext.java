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

package com.liferay.layout.taglib.internal.display.context;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.fragment.helper.FragmentEntryLinkHelper;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.service.LayoutClassedModelUsageLocalServiceUtil;
import com.liferay.layout.util.LayoutClassedModelUsageActionMenuContributor;
import com.liferay.layout.util.LayoutClassedModelUsageActionMenuContributorRegistryUtil;
import com.liferay.layout.util.comparator.LayoutClassedModelUsageModifiedDateComparator;
import com.liferay.layout.util.constants.LayoutClassedModelUsageConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
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
public class LayoutClassedModelUsagesDisplayContext {

	public LayoutClassedModelUsagesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String className, long classPK) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_className = className;
		_classPK = classPK;

		_classNameId = PortalUtil.getClassNameId(className);
		_fragmentEntryLinkHelper =
			(FragmentEntryLinkHelper)renderRequest.getAttribute(
				FragmentEntryLinkHelper.class.getName());

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _themeDisplay.getLocale(), getClass());
	}

	public int getAllUsageCount() {
		return LayoutClassedModelUsageLocalServiceUtil.
			getLayoutClassedModelUsagesCount(_classNameId, _classPK);
	}

	public int getDisplayPagesUsageCount() {
		return LayoutClassedModelUsageLocalServiceUtil.
			getLayoutClassedModelUsagesCount(
				_classNameId, _classPK,
				LayoutClassedModelUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE);
	}

	public List<DropdownItem> getLayoutClassedModelUsageActionDropdownItems(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		if (!isShowPreview(layoutClassedModelUsage)) {
			return Collections.emptyList();
		}

		LayoutClassedModelUsageActionMenuContributor
			layoutClassedModelUsageActionMenuContributor =
				LayoutClassedModelUsageActionMenuContributorRegistryUtil.
					getLayoutClassedModelUsageActionMenuContributor(_className);

		if (layoutClassedModelUsageActionMenuContributor == null) {
			return Collections.emptyList();
		}

		return layoutClassedModelUsageActionMenuContributor.
			getLayoutClassedModelUsageActionDropdownItems(
				PortalUtil.getHttpServletRequest(_renderRequest),
				layoutClassedModelUsage);
	}

	public String getLayoutClassedModelUsageName(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		if (layoutClassedModelUsage.getType() ==
				LayoutClassedModelUsageConstants.TYPE_LAYOUT) {

			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				layoutClassedModelUsage.getPlid());

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

		long plid = layoutClassedModelUsage.getPlid();

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

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

	public String getLayoutClassedModelUsageTypeLabel(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		if (layoutClassedModelUsage.getType() ==
				LayoutClassedModelUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE) {

			return "display-page-template";
		}

		if (layoutClassedModelUsage.getType() ==
				LayoutClassedModelUsageConstants.TYPE_LAYOUT) {

			return "page";
		}

		return "page-template";
	}

	public String getLayoutClassedModelUsageWhereLabel(
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws PortalException {

		if ((layoutClassedModelUsage.getContainerType() !=
				PortalUtil.getClassNameId(FragmentEntryLink.class)) &&
			(layoutClassedModelUsage.getContainerType() !=
				PortalUtil.getClassNameId(LayoutPageTemplateStructure.class))) {

			return LanguageUtil.format(
				_resourceBundle, "x-widget",
				PortalUtil.getPortletTitle(
					PortletIdCodec.decodePortletName(
						layoutClassedModelUsage.getContainerKey()),
					_themeDisplay.getLocale()));
		}

		if (layoutClassedModelUsage.getContainerType() ==
				PortalUtil.getClassNameId(FragmentEntryLink.class)) {

			FragmentEntryLink fragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLink(
					GetterUtil.getLong(
						layoutClassedModelUsage.getContainerKey()));

			String name = _fragmentEntryLinkHelper.getFragmentEntryName(
				fragmentEntryLink, _themeDisplay.getLocale());

			if (Validator.isNull(name)) {
				return StringPool.BLANK;
			}

			if (!fragmentEntryLink.isTypeSection()) {
				return LanguageUtil.format(_resourceBundle, "x-element", name);
			}

			return LanguageUtil.format(_resourceBundle, "x-section", name);
		}

		if (layoutClassedModelUsage.getContainerType() ==
				PortalUtil.getClassNameId(LayoutPageTemplateStructure.class)) {

			return LanguageUtil.get(_resourceBundle, "section");
		}

		return StringPool.BLANK;
	}

	public String getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_renderRequest, "navigation", "all");

		return _navigation;
	}

	public int getPagesUsageCount() {
		return LayoutClassedModelUsageLocalServiceUtil.
			getLayoutClassedModelUsagesCount(
				_classNameId, _classPK,
				LayoutClassedModelUsageConstants.TYPE_LAYOUT);
	}

	public int getPageTemplatesUsageCount() {
		return LayoutClassedModelUsageLocalServiceUtil.
			getLayoutClassedModelUsagesCount(
				_classNameId, _classPK,
				LayoutClassedModelUsageConstants.TYPE_PAGE_TEMPLATE);
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse),
			_renderResponse);
	}

	public String getPreviewURL(LayoutClassedModelUsage layoutClassedModelUsage)
		throws PortalException {

		String layoutURL = null;

		if (layoutClassedModelUsage.getContainerType() ==
				PortalUtil.getClassNameId(FragmentEntryLink.class)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_renderRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			layoutURL = PortalUtil.getLayoutFriendlyURL(
				LayoutLocalServiceUtil.fetchLayout(
					layoutClassedModelUsage.getPlid()),
				themeDisplay);

			layoutURL = HttpComponentsUtil.setParameter(
				layoutURL, "previewClassNameId",
				String.valueOf(layoutClassedModelUsage.getClassNameId()));
			layoutURL = HttpComponentsUtil.setParameter(
				layoutURL, "previewClassPK",
				String.valueOf(layoutClassedModelUsage.getClassPK()));
			layoutURL = HttpComponentsUtil.setParameter(
				layoutURL, "previewType",
				String.valueOf(AssetRendererFactory.TYPE_LATEST));
		}
		else {
			layoutURL = PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					_renderRequest, layoutClassedModelUsage.getContainerKey(),
					layoutClassedModelUsage.getPlid(),
					PortletRequest.RENDER_PHASE)
			).setParameter(
				"previewClassNameId", layoutClassedModelUsage.getClassNameId()
			).setParameter(
				"previewClassPK", layoutClassedModelUsage.getClassPK()
			).setParameter(
				"previewType", AssetRendererFactory.TYPE_LATEST
			).buildString();
		}

		String portletURLString = HttpComponentsUtil.addParameter(
			layoutURL, "p_l_mode", Constants.PREVIEW);

		return portletURLString + "#portlet_" +
			layoutClassedModelUsage.getContainerKey();
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	public SearchContainer<LayoutClassedModelUsage> getSearchContainer()
		throws PortletException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<LayoutClassedModelUsage>
			layoutClassedModelUsagesSearchContainer = new SearchContainer(
				_renderRequest, getPortletURL(), null, "there-are-no-usages");

		layoutClassedModelUsagesSearchContainer.setOrderByCol(_getOrderByCol());

		boolean orderByAsc = false;

		String orderByType = _getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		layoutClassedModelUsagesSearchContainer.setOrderByComparator(
			new LayoutClassedModelUsageModifiedDateComparator(orderByAsc));
		layoutClassedModelUsagesSearchContainer.setOrderByType(
			_getOrderByType());

		if (Objects.equals(getNavigation(), "pages")) {
			layoutClassedModelUsagesSearchContainer.setResultsAndTotal(
				() ->
					LayoutClassedModelUsageLocalServiceUtil.
						getLayoutClassedModelUsages(
							_classNameId, _classPK,
							LayoutClassedModelUsageConstants.TYPE_LAYOUT,
							layoutClassedModelUsagesSearchContainer.getStart(),
							layoutClassedModelUsagesSearchContainer.getEnd(),
							layoutClassedModelUsagesSearchContainer.
								getOrderByComparator()),
				getPagesUsageCount());
		}
		else if (Objects.equals(getNavigation(), "page-templates")) {
			layoutClassedModelUsagesSearchContainer.setResultsAndTotal(
				() ->
					LayoutClassedModelUsageLocalServiceUtil.
						getLayoutClassedModelUsages(
							_classNameId, _classPK,
							LayoutClassedModelUsageConstants.TYPE_PAGE_TEMPLATE,
							layoutClassedModelUsagesSearchContainer.getStart(),
							layoutClassedModelUsagesSearchContainer.getEnd(),
							layoutClassedModelUsagesSearchContainer.
								getOrderByComparator()),
				getPageTemplatesUsageCount());
		}
		else if (Objects.equals(getNavigation(), "display-page-templates")) {
			layoutClassedModelUsagesSearchContainer.setResultsAndTotal(
				() ->
					LayoutClassedModelUsageLocalServiceUtil.
						getLayoutClassedModelUsages(
							_classNameId, _classPK,
							LayoutClassedModelUsageConstants.
								TYPE_DISPLAY_PAGE_TEMPLATE,
							layoutClassedModelUsagesSearchContainer.getStart(),
							layoutClassedModelUsagesSearchContainer.getEnd(),
							layoutClassedModelUsagesSearchContainer.
								getOrderByComparator()),
				getDisplayPagesUsageCount());
		}
		else {
			layoutClassedModelUsagesSearchContainer.setResultsAndTotal(
				() ->
					LayoutClassedModelUsageLocalServiceUtil.
						getLayoutClassedModelUsages(
							_classNameId, _classPK,
							layoutClassedModelUsagesSearchContainer.getStart(),
							layoutClassedModelUsagesSearchContainer.getEnd(),
							layoutClassedModelUsagesSearchContainer.
								getOrderByComparator()),
				getAllUsageCount());
		}

		_searchContainer = layoutClassedModelUsagesSearchContainer;

		return _searchContainer;
	}

	public boolean isShowPreview(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		if (layoutClassedModelUsage.getType() ==
				LayoutClassedModelUsageConstants.TYPE_LAYOUT) {

			return true;
		}

		if ((layoutClassedModelUsage.getType() ==
				LayoutClassedModelUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE) ||
			(layoutClassedModelUsage.getType() !=
				LayoutClassedModelUsageConstants.TYPE_PAGE_TEMPLATE)) {

			return false;
		}

		long plid = layoutClassedModelUsage.getPlid();

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

	private final String _className;
	private final long _classNameId;
	private final long _classPK;
	private final FragmentEntryLinkHelper _fragmentEntryLinkHelper;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ResourceBundle _resourceBundle;
	private SearchContainer<LayoutClassedModelUsage> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}