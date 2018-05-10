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

package com.liferay.fragment.item.selector.web.internal.display.context;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentCollectionNameComparator;
import com.liferay.fragment.util.comparator.FragmentEntryCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentEntryNameComparator;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
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
		HttpServletRequest request, String itemSelectedEventName,
		boolean search, PortletURL portletURL) {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_request = request;
		_itemSelectedEventName = itemSelectedEventName;
		_search = search;
		_portletURL = portletURL;
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(_request, "displayStyle", "icon");

		return _displayStyle;
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public String getFragmentCollectionsRedirect() {
		PortletURL backURL = getPortletURL();

		backURL.setParameter("fragmentCollectionId", "0");

		return backURL.toString();
	}

	public SearchContainer getFragmentCollectionsSearchContainer() {
		if (_fragmentCollectionsSearchContainer != null) {
			return _fragmentCollectionsSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer fragmentCollectionsSearchContainer =
			new SearchContainer(
				_portletRequest, getPortletURL(), null,
				"there-are-no-collections");

		if (_isSearch()) {
			fragmentCollectionsSearchContainer.setSearch(true);
		}

		OrderByComparator<FragmentCollection> orderByComparator =
			_getFragmentCollectionOrderByComparator(
				_getOrderByCol(), getOrderByType());

		fragmentCollectionsSearchContainer.setOrderByCol(_getOrderByCol());
		fragmentCollectionsSearchContainer.setOrderByComparator(
			orderByComparator);
		fragmentCollectionsSearchContainer.setOrderByType(getOrderByType());
		fragmentCollectionsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_portletResponse));

		List<FragmentCollection> fragmentCollections = null;
		int fragmentCollectionsCount = 0;

		if (_isSearch()) {
			fragmentCollections =
				FragmentCollectionServiceUtil.getFragmentCollections(
					themeDisplay.getScopeGroupId(), _getKeywords(),
					fragmentCollectionsSearchContainer.getStart(),
					fragmentCollectionsSearchContainer.getEnd(),
					orderByComparator);

			fragmentCollectionsCount =
				FragmentCollectionServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId(), _getKeywords());
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

	public int getFragmentCollectionsTotalItems() {
		SearchContainer fragmentCollectionsSearchContainer =
			getFragmentCollectionsSearchContainer();

		return fragmentCollectionsSearchContainer.getTotal();
	}

	public String getFragmentCollectionTitle() throws PortalException {
		FragmentCollection fragmentCollection = _getFragmentCollection();

		return fragmentCollection.getName();
	}

	public SearchContainer getFragmentEntriesSearchContainer() {
		if (_fragmentEntriesSearchContainer != null) {
			return _fragmentEntriesSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer fragmentEntriesSearchContainer = new SearchContainer(
			_portletRequest, getPortletURL(), null, "there-are-no-fragments");

		if (_isSearch()) {
			fragmentEntriesSearchContainer.setSearch(true);
		}

		OrderByComparator<FragmentEntry> orderByComparator =
			_getFragmentEntryOrderByComparator(
				_getOrderByCol(), getOrderByType());

		fragmentEntriesSearchContainer.setOrderByCol(_getOrderByCol());
		fragmentEntriesSearchContainer.setOrderByComparator(orderByComparator);
		fragmentEntriesSearchContainer.setOrderByType(getOrderByType());

		List<FragmentEntry> fragmentEntries = null;
		int fragmentEntriesCount = 0;

		if (_isSearch()) {
			fragmentEntries = FragmentEntryServiceUtil.getFragmentEntries(
				themeDisplay.getScopeGroupId(), _getFragmentCollectionId(),
				_getKeywords(), fragmentEntriesSearchContainer.getStart(),
				WorkflowConstants.STATUS_APPROVED,
				fragmentEntriesSearchContainer.getEnd(), orderByComparator);

			fragmentEntriesCount =
				FragmentEntryServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId(), _getFragmentCollectionId(),
					_getKeywords(), WorkflowConstants.STATUS_APPROVED);
		}
		else {
			fragmentEntries = FragmentEntryServiceUtil.getFragmentEntries(
				themeDisplay.getScopeGroupId(), _getFragmentCollectionId(),
				WorkflowConstants.STATUS_APPROVED,
				fragmentEntriesSearchContainer.getStart(),
				fragmentEntriesSearchContainer.getEnd(), orderByComparator);

			fragmentEntriesCount =
				FragmentEntryServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId(), _getFragmentCollectionId(),
					WorkflowConstants.STATUS_APPROVED);
		}

		fragmentEntriesSearchContainer.setResults(fragmentEntries);
		fragmentEntriesSearchContainer.setTotal(fragmentEntriesCount);

		_fragmentEntriesSearchContainer = fragmentEntriesSearchContainer;

		return _fragmentEntriesSearchContainer;
	}

	public int getFragmentEntriesTotalItems() {
		SearchContainer fragmentEntriesSearchContainer =
			getFragmentEntriesSearchContainer();

		return fragmentEntriesSearchContainer.getTotal();
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_portletResponse);

		PortletURL portletURL = null;

		try {
			portletURL = PortletURLUtil.clone(
				_portletURL, liferayPortletResponse);
		}
		catch (PortletException pe) {
			portletURL = liferayPortletResponse.createRenderURL();
		}

		long fragmentCollectionId = _getFragmentCollectionId();

		if (fragmentCollectionId > 0) {
			portletURL.setParameter(
				"fragmentCollectionId", String.valueOf(fragmentCollectionId));
		}

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = _getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), getDisplayStyle()) {

			{
				addCardViewTypeItem();
			}

		};
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(getPortletURL());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});
			}
		};
	}

	private FragmentCollection _getFragmentCollection() throws PortalException {
		if (_fragmentCollection != null) {
			return _fragmentCollection;
		}

		_fragmentCollection =
			FragmentCollectionServiceUtil.fetchFragmentCollection(
				_getFragmentCollectionId());

		return _fragmentCollection;
	}

	private long _getFragmentCollectionId() {
		if (Validator.isNotNull(_fragmentCollectionId)) {
			return _fragmentCollectionId;
		}

		_fragmentCollectionId = ParamUtil.getLong(
			_request, "fragmentCollectionId");

		return _fragmentCollectionId;
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

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getOrderByCol(), "name"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "name");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "name"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getOrderByCol(), "create-date"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "create-date");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "create-date"));
					});
			}
		};
	}

	private boolean _isSearch() {
		return _search;
	}

	private String _displayStyle;
	private FragmentCollection _fragmentCollection;
	private Long _fragmentCollectionId;
	private SearchContainer _fragmentCollectionsSearchContainer;
	private SearchContainer _fragmentEntriesSearchContainer;
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