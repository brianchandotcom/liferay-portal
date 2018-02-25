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

package com.liferay.fragment.item.selector.web.internal.context;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.item.selector.web.internal.FragmentItemSelectorView;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentCollectionNameComparator;
import com.liferay.fragment.util.comparator.FragmentEntryCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentEntryNameComparator;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class FragmentItemSelectorViewDisplayContext {

	public FragmentItemSelectorViewDisplayContext(
		PortletRequest portletRequest, PortletResponse portletResponse,
		HttpServletRequest request,
		FragmentItemSelectorView fragmentItemSelectorView,
		String itemSelectedEventName, boolean search, PortletURL portletURL) {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_request = request;
		_fragmentItemSelectorView = fragmentItemSelectorView;
		_itemSelectedEventName = itemSelectedEventName;
		_search = search;
		_portletURL = portletURL;
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

	public String getFragmentCollectionsRedirect() throws PortalException {
		PortletURL backURL = getPortletURL();

		backURL.setParameter("fragmentCollectionId", "0");

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
				_portletRequest, getPortletURL(), null,
				"there-are-no-collections");

		if (!isSearch()) {
			fragmentCollectionsSearchContainer.setEmptyResultsMessage(
				"there-are-no-collections.-you-can-add-a-collection-by-" +
					"clicking-the-plus-button-on-the-bottom-right-corner");
		}
		else {
			fragmentCollectionsSearchContainer.setSearch(true);
		}

		OrderByComparator<FragmentCollection> orderByComparator =
			_getFragmentCollectionOrderByComparator(
				getOrderByCol(), getOrderByType());

		fragmentCollectionsSearchContainer.setOrderByCol(getOrderByCol());
		fragmentCollectionsSearchContainer.setOrderByComparator(
			orderByComparator);
		fragmentCollectionsSearchContainer.setOrderByType(getOrderByType());
		fragmentCollectionsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_portletResponse));

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
			_portletRequest, getPortletURL(), null, "there-are-no-fragments");

		if (!isSearch()) {
			fragmentEntriesSearchContainer.setEmptyResultsMessage(
				"there-are-no-fragments.-you-can-add-a-fragment-by-clicking-" +
					"the-plus-button-on-the-bottom-right-corner");
		}
		else {
			fragmentEntriesSearchContainer.setSearch(true);
		}

		OrderByComparator<FragmentEntry> orderByComparator =
			_getFragmentEntryOrderByComparator(
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

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
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

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public String getTitle(Locale locale) {
		return _fragmentItemSelectorView.getTitle(locale);
	}

	public boolean isSearch() {
		return _search;
	}

	private OrderByComparator<FragmentCollection>
		_getFragmentCollectionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<FragmentCollection> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new FragmentCollectionCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new FragmentCollectionNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	private OrderByComparator<FragmentEntry> _getFragmentEntryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<FragmentEntry> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new FragmentEntryCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new FragmentEntryNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private String _displayStyle;
	private FragmentCollection _fragmentCollection;
	private Long _fragmentCollectionId;
	private SearchContainer _fragmentCollectionsSearchContainer;
	private SearchContainer _fragmentEntriesSearchContainer;
	private final FragmentItemSelectorView _fragmentItemSelectorView;
	private final String _itemSelectedEventName;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final PortletURL _portletURL;
	private final HttpServletRequest _request;
	private final boolean _search;

}