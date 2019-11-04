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
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
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
public class LayoutClassedModelUsagesDisplayContext {

	public LayoutClassedModelUsagesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String className, long classPK) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_className = className;
		_classPK = classPK;

		_classNameId = PortalUtil.getClassNameId(className);
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

		long plid = layoutClassedModelUsage.getPlid();

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			layoutClassedModelUsage.getPlid());

		if ((layout.getClassNameId() > 0) && (layout.getClassPK() > 0)) {
			plid = layout.getClassPK();
		}

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

			String portletTitle = PortalUtil.getPortletTitle(
				PortletIdCodec.decodePortletName(
					layoutClassedModelUsage.getContainerKey()),
				_themeDisplay.getLocale());

			return LanguageUtil.format(
				_resourceBundle, "x-widget", portletTitle);
		}

		if (layoutClassedModelUsage.getContainerType() ==
				PortalUtil.getClassNameId(FragmentEntryLink.class)) {

			FragmentEntryLink fragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLink(
					GetterUtil.getLong(
						layoutClassedModelUsage.getContainerKey()));

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
		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		return PortletURLUtil.clone(currentURLObj, _renderResponse);
	}

	public String getPreviewURL(LayoutClassedModelUsage layoutClassedModelUsage)
		throws PortalException {

		String layoutURL = null;

		if (layoutClassedModelUsage.getContainerType() ==
				PortalUtil.getClassNameId(FragmentEntryLink.class)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_renderRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				layoutClassedModelUsage.getPlid());

			layoutURL = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);

			layoutURL = HttpUtil.setParameter(
				layoutURL, "previewClassNameId",
				String.valueOf(layoutClassedModelUsage.getClassNameId()));
			layoutURL = HttpUtil.setParameter(
				layoutURL, "previewClassPK",
				String.valueOf(layoutClassedModelUsage.getClassPK()));
			layoutURL = HttpUtil.setParameter(
				layoutURL, "previewType",
				String.valueOf(AssetRendererFactory.TYPE_LATEST));
		}
		else {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				_renderRequest, layoutClassedModelUsage.getContainerKey(),
				layoutClassedModelUsage.getPlid(), PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"previewClassNameId",
				String.valueOf(layoutClassedModelUsage.getClassNameId()));
			portletURL.setParameter(
				"previewClassPK",
				String.valueOf(layoutClassedModelUsage.getClassPK()));
			portletURL.setParameter(
				"previewType",
				String.valueOf(AssetRendererFactory.TYPE_LATEST));

			layoutURL = portletURL.toString();
		}

		String portletURLString = HttpUtil.addParameter(
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

	public SearchContainer getSearchContainer() throws PortletException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer layoutClassedModelUsagesSearchContainer =
			new SearchContainer(
				_renderRequest, getPortletURL(), null, "there-are-no-usages");

		boolean orderByAsc = false;

		String orderByType = _getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<LayoutClassedModelUsage> orderByComparator =
			new LayoutClassedModelUsageModifiedDateComparator(orderByAsc);

		layoutClassedModelUsagesSearchContainer.setOrderByCol(_getOrderByCol());
		layoutClassedModelUsagesSearchContainer.setOrderByComparator(
			orderByComparator);
		layoutClassedModelUsagesSearchContainer.setOrderByType(
			_getOrderByType());

		List<LayoutClassedModelUsage> layoutClassedModelUsages = null;

		int layoutClassedModelUsagesCount = 0;

		if (Objects.equals(getNavigation(), "pages")) {
			layoutClassedModelUsages =
				LayoutClassedModelUsageLocalServiceUtil.
					getLayoutClassedModelUsages(
						_classNameId, _classPK,
						LayoutClassedModelUsageConstants.TYPE_LAYOUT,
						layoutClassedModelUsagesSearchContainer.getStart(),
						layoutClassedModelUsagesSearchContainer.getEnd(),
						orderByComparator);

			layoutClassedModelUsagesCount = getPagesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "page-templates")) {
			layoutClassedModelUsages =
				LayoutClassedModelUsageLocalServiceUtil.
					getLayoutClassedModelUsages(
						_classNameId, _classPK,
						LayoutClassedModelUsageConstants.TYPE_PAGE_TEMPLATE,
						layoutClassedModelUsagesSearchContainer.getStart(),
						layoutClassedModelUsagesSearchContainer.getEnd(),
						orderByComparator);

			layoutClassedModelUsagesCount = getPageTemplatesUsageCount();
		}
		else if (Objects.equals(getNavigation(), "display-page-templates")) {
			layoutClassedModelUsages =
				LayoutClassedModelUsageLocalServiceUtil.
					getLayoutClassedModelUsages(
						_classNameId, _classPK,
						LayoutClassedModelUsageConstants.
							TYPE_DISPLAY_PAGE_TEMPLATE,
						layoutClassedModelUsagesSearchContainer.getStart(),
						layoutClassedModelUsagesSearchContainer.getEnd(),
						orderByComparator);

			layoutClassedModelUsagesCount = getDisplayPagesUsageCount();
		}
		else {
			layoutClassedModelUsages =
				LayoutClassedModelUsageLocalServiceUtil.
					getLayoutClassedModelUsages(
						_classNameId, _classPK,
						layoutClassedModelUsagesSearchContainer.getStart(),
						layoutClassedModelUsagesSearchContainer.getEnd(),
						orderByComparator);

			layoutClassedModelUsagesCount = getAllUsageCount();
		}

		layoutClassedModelUsagesSearchContainer.setResults(
			layoutClassedModelUsages);
		layoutClassedModelUsagesSearchContainer.setTotal(
			layoutClassedModelUsagesCount);

		_searchContainer = layoutClassedModelUsagesSearchContainer;

		return _searchContainer;
	}

	public boolean isShowPreview(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		if (layoutClassedModelUsage.getType() ==
				LayoutClassedModelUsageConstants.TYPE_LAYOUT) {

			return true;
		}

		if (layoutClassedModelUsage.getType() ==
				LayoutClassedModelUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE) {

			return false;
		}

		if (layoutClassedModelUsage.getType() !=
				LayoutClassedModelUsageConstants.TYPE_PAGE_TEMPLATE) {

			return false;
		}

		long plid = layoutClassedModelUsage.getPlid();

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if ((layout.getClassNameId() > 0) && (layout.getClassPK() > 0)) {
			plid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if (layoutPageTemplateEntry == null) {
			return false;
		}

		if (layoutPageTemplateEntry.getType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE) {

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

	private boolean _isDraft(Layout layout) {
		if (layout.getClassNameId() != PortalUtil.getClassNameId(
				Layout.class.getName())) {

			return false;
		}

		return true;
	}

	private final String _className;
	private final long _classNameId;
	private final long _classPK;
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
	private SearchContainer _searchContainer;
	private final ThemeDisplay _themeDisplay;

}