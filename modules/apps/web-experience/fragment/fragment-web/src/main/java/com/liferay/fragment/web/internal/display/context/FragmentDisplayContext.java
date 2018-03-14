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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.fragment.web.util.FragmentPortletUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentDisplayContext {

	public FragmentDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;
	}

	public String getCssContent() throws PortalException {
		if (Validator.isNotNull(_cssContent)) {
			return _cssContent;
		}

		_cssContent = ParamUtil.getString(_request, "cssContent");

		FragmentEntry fragmentEntry = getFragmentEntry();

		if ((fragmentEntry != null) && Validator.isNull(_cssContent)) {
			_cssContent = fragmentEntry.getCss();

			if (Validator.isNull(_cssContent)) {
				StringBundler sb = new StringBundler(3);

				sb.append(".fragment_");
				sb.append(fragmentEntry.getFragmentEntryId());
				sb.append(" {\n}");

				_cssContent = sb.toString();
			}
		}

		return _cssContent;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			FragmentPortletKeys.FRAGMENT, "display-style", "icon");

		return _displayStyle;
	}

	public String getEditFragmentCollectionRedirect() throws PortalException {
		String redirect = ParamUtil.getString(_request, "redirect");

		if (Validator.isNull(redirect)) {
			PortletURL portletURL = _renderResponse.createRenderURL();

			redirect = portletURL.toString();
		}

		return redirect;
	}

	public String getEditFragmentEntryRedirect() throws PortalException {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/fragment/view_fragment_entries");

		if (getFragmentCollectionId() > 0) {
			portletURL.setParameter(
				"fragmentCollectionId",
				String.valueOf(getFragmentCollectionId()));
		}

		return portletURL.toString();
	}

	public FragmentCollection getFragmentCollection() throws PortalException {
		if (_fragmentCollection != null) {
			return _fragmentCollection;
		}

		_fragmentCollection =
			FragmentCollectionServiceUtil.fetchFragmentCollection(
				getFragmentCollectionId());

		return _fragmentCollection;
	}

	public long getFragmentCollectionId() {
		if (Validator.isNotNull(_fragmentCollectionId)) {
			return _fragmentCollectionId;
		}

		_fragmentCollectionId = ParamUtil.getLong(
			_request, "fragmentCollectionId");

		return _fragmentCollectionId;
	}

	public List<NavigationItem> getFragmentCollectionNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem entriesNavigationItem = new NavigationItem();

		entriesNavigationItem.setActive(true);

		PortletURL mainURL = _renderResponse.createRenderURL();

		entriesNavigationItem.setHref(mainURL.toString());

		entriesNavigationItem.setLabel(
			LanguageUtil.get(_request, "collections"));

		navigationItems.add(entriesNavigationItem);

		return navigationItems;
	}

	public String getFragmentCollectionsRedirect() throws PortalException {
		PortletURL backURL = _renderResponse.createRenderURL();

		backURL.setParameter("mvcRenderCommandName", "/fragment/view");

		return backURL.toString();
	}

	public SearchContainer getFragmentCollectionsSearchContainer()
		throws PortalException {

		if (_fragmentCollectionsSearchContainer != null) {
			return _fragmentCollectionsSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer fragmentCollectionsSearchContainer =
			new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-collections");

		if (isSearch()) {
			fragmentCollectionsSearchContainer.setSearch(true);
		}

		fragmentCollectionsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		OrderByComparator<FragmentCollection> orderByComparator =
			FragmentPortletUtil.getFragmentCollectionOrderByComparator(
				getOrderByCol(), getOrderByType());

		fragmentCollectionsSearchContainer.setOrderByCol(getOrderByCol());
		fragmentCollectionsSearchContainer.setOrderByComparator(
			orderByComparator);
		fragmentCollectionsSearchContainer.setOrderByType(getOrderByType());
		fragmentCollectionsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		List<FragmentCollection> fragmentCollections = null;
		int fragmentCollectionsCount = 0;

		if (isSearch()) {
			fragmentCollections =
				FragmentCollectionServiceUtil.getFragmentCollections(
					themeDisplay.getScopeGroupId(), getKeywords(),
					fragmentCollectionsSearchContainer.getStart(),
					fragmentCollectionsSearchContainer.getEnd(),
					orderByComparator);

			fragmentCollectionsCount =
				FragmentCollectionServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId(), getKeywords());
		}
		else {
			fragmentCollections =
				FragmentCollectionServiceUtil.getFragmentCollections(
					themeDisplay.getScopeGroupId(),
					fragmentCollectionsSearchContainer.getStart(),
					fragmentCollectionsSearchContainer.getEnd(),
					orderByComparator);

			fragmentCollectionsCount =
				FragmentCollectionServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId());
		}

		fragmentCollectionsSearchContainer.setTotal(fragmentCollectionsCount);
		fragmentCollectionsSearchContainer.setResults(fragmentCollections);

		_fragmentCollectionsSearchContainer =
			fragmentCollectionsSearchContainer;

		return _fragmentCollectionsSearchContainer;
	}

	public String getFragmentCollectionTitle() throws PortalException {
		FragmentCollection fragmentCollection = getFragmentCollection();

		if (fragmentCollection == null) {
			return LanguageUtil.get(_request, "add-collection");
		}

		return fragmentCollection.getName();
	}

	public SearchContainer getFragmentEntriesSearchContainer()
		throws PortalException {

		if (_fragmentEntriesSearchContainer != null) {
			return _fragmentEntriesSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer fragmentEntriesSearchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-fragments");

		if (isSearch()) {
			fragmentEntriesSearchContainer.setSearch(true);
		}

		fragmentEntriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		OrderByComparator<FragmentEntry> orderByComparator =
			FragmentPortletUtil.getFragmentEntryOrderByComparator(
				getOrderByCol(), getOrderByType());

		fragmentEntriesSearchContainer.setOrderByCol(getOrderByCol());
		fragmentEntriesSearchContainer.setOrderByComparator(orderByComparator);
		fragmentEntriesSearchContainer.setOrderByType(getOrderByType());

		List<FragmentEntry> fragmentEntries = null;
		int fragmentEntriesCount = 0;

		if (isSearch()) {
			fragmentEntries = FragmentEntryServiceUtil.getFragmentEntries(
				themeDisplay.getScopeGroupId(), getFragmentCollectionId(),
				getKeywords(), fragmentEntriesSearchContainer.getStart(),
				fragmentEntriesSearchContainer.getEnd(), orderByComparator);

			fragmentEntriesCount =
				FragmentEntryServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId(), getFragmentCollectionId(),
					getKeywords());
		}
		else {
			fragmentEntries = FragmentEntryServiceUtil.getFragmentEntries(
				themeDisplay.getScopeGroupId(), getFragmentCollectionId(),
				fragmentEntriesSearchContainer.getStart(),
				fragmentEntriesSearchContainer.getEnd(), orderByComparator);

			fragmentEntriesCount =
				FragmentEntryServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId(), getFragmentCollectionId());
		}

		fragmentEntriesSearchContainer.setResults(fragmentEntries);
		fragmentEntriesSearchContainer.setTotal(fragmentEntriesCount);

		_fragmentEntriesSearchContainer = fragmentEntriesSearchContainer;

		return _fragmentEntriesSearchContainer;
	}

	public FragmentEntry getFragmentEntry() throws PortalException {
		if (_fragmentEntry != null) {
			return _fragmentEntry;
		}

		_fragmentEntry = FragmentEntryServiceUtil.fetchFragmentEntry(
			getFragmentEntryId());

		return _fragmentEntry;
	}

	public long getFragmentEntryId() {
		if (Validator.isNotNull(_fragmentEntryId)) {
			return _fragmentEntryId;
		}

		_fragmentEntryId = ParamUtil.getLong(_request, "fragmentEntryId");

		return _fragmentEntryId;
	}

	public String getFragmentEntryTitle() throws PortalException {
		FragmentEntry fragmentEntry = getFragmentEntry();

		if (fragmentEntry == null) {
			return LanguageUtil.get(_request, "add-fragment");
		}

		return fragmentEntry.getName();
	}

	public String getHtmlContent() throws PortalException {
		if (Validator.isNotNull(_htmlContent)) {
			return _htmlContent;
		}

		_htmlContent = ParamUtil.getString(_request, "htmlContent");

		FragmentEntry fragmentEntry = getFragmentEntry();

		if ((fragmentEntry != null) && Validator.isNull(_htmlContent)) {
			_htmlContent = fragmentEntry.getHtml();

			if (Validator.isNull(_htmlContent)) {
				StringBundler sb = new StringBundler(3);

				sb.append("<div class=\"fragment_");
				sb.append(fragmentEntry.getFragmentEntryId());
				sb.append("\">\n</div>");

				_htmlContent = sb.toString();
			}
		}

		return _htmlContent;
	}

	public String getJsContent() throws PortalException {
		if (Validator.isNotNull(_jsContent)) {
			return _jsContent;
		}

		_jsContent = ParamUtil.getString(_request, "jsContent");

		FragmentEntry fragmentEntry = getFragmentEntry();

		if ((fragmentEntry != null) && Validator.isNull(_jsContent)) {
			_jsContent = fragmentEntry.getJs();
		}

		return _jsContent;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public String[] getOrderColumns() {
		return new String[] {"create-date", "name"};
	}

	public boolean isDisabledFragmentCollectionsManagementBar()
		throws PortalException {

		if (_hasFragmentCollectionsResults()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isDisabledFragmentEntriesManagementBar()
		throws PortalException {

		if (_hasFragmentEntriesResults()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowAddButton(String actionId) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (FragmentPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getSiteGroupId(), actionId)) {

			return true;
		}

		return false;
	}

	public boolean isShowFragmentCollectionsSearch() throws PortalException {
		if (_hasFragmentCollectionsResults()) {
			return true;
		}

		if (isSearch()) {
			return true;
		}

		return false;
	}

	public boolean isShowFragmentEntriesSearch() throws PortalException {
		if (_hasFragmentEntriesResults()) {
			return true;
		}

		if (isSearch()) {
			return true;
		}

		return false;
	}

	private boolean _hasFragmentCollectionsResults() throws PortalException {
		SearchContainer searchContainer =
			getFragmentCollectionsSearchContainer();

		if (searchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	private boolean _hasFragmentEntriesResults() throws PortalException {
		SearchContainer searchContainer = getFragmentEntriesSearchContainer();

		if (searchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	private String _cssContent;
	private String _displayStyle;
	private FragmentCollection _fragmentCollection;
	private Long _fragmentCollectionId;
	private SearchContainer _fragmentCollectionsSearchContainer;
	private SearchContainer _fragmentEntriesSearchContainer;
	private FragmentEntry _fragmentEntry;
	private Long _fragmentEntryId;
	private String _htmlContent;
	private String _jsContent;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}