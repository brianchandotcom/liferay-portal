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

package com.liferay.dynamic.data.mapping.item.selector.web.internal;

import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.dynamic.data.mapping.item.selector.DDMStructureItemSelectorReturnType;
import com.liferay.dynamic.data.mapping.item.selector.criterion.DDMStructureItemSelectorCriterion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.constants.ItemSelectorPortletKeys;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DDMStructureItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<DDMStructure> {

	public DDMStructureItemSelectorViewDescriptor(
		DDMStructureItemSelectorCriterion ddmStructureItemSelectorCriterion,
		HttpServletRequest httpServletRequest, PortletURL portletURL) {

		_ddmStructureItemSelectorCriterion = ddmStructureItemSelectorCriterion;
		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	@Override
	public String[] getDisplayViews() {
		return new String[] {"list"};
	}

	@Override
	public ItemDescriptor getItemDescriptor(DDMStructure ddmStructure) {
		return new DDMStructureItemDescriptor(
			ddmStructure, _httpServletRequest);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new DDMStructureItemSelectorReturnType();
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, ItemSelectorPortletKeys.ITEM_SELECTOR,
			"select-ddm-structure-order-by-col", "modified-date");

		return _orderByCol;
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"modified-date"};
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, ItemSelectorPortletKeys.ITEM_SELECTOR,
			"select-ddm-structure-order-by-type", "desc");

		return _orderByType;
	}

	@Override
	public SearchContainer<DDMStructure> getSearchContainer()
		throws PortalException {

		String emptyResultsMessage = "there-are-no-structures";

		if (Validator.isNotNull(_getKeywords())) {
			emptyResultsMessage = "no-structures-were-found";
		}

		SearchContainer<DDMStructure> ddmStructureSearch =
			new SearchContainer<>(
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST),
				_portletURL, null, emptyResultsMessage);

		ddmStructureSearch.setOrderByCol(getOrderByCol());
		ddmStructureSearch.setOrderByComparator(
			DDMUtil.getStructureOrderByComparator(
				getOrderByCol(), getOrderByType()));
		ddmStructureSearch.setOrderByType(getOrderByType());

		long[] groupIds =
			SiteConnectedGroupGroupProviderUtil.
				getCurrentAndAncestorSiteAndDepotGroupIds(
					_themeDisplay.getScopeGroupId(), true);

		if (Validator.isNotNull(_getKeywords())) {
			ddmStructureSearch.setResultsAndTotal(
				() -> DDMStructureServiceUtil.search(
					_themeDisplay.getCompanyId(), groupIds,
					_ddmStructureItemSelectorCriterion.getClassNameId(),
					_getKeywords(), WorkflowConstants.STATUS_ANY,
					ddmStructureSearch.getStart(), ddmStructureSearch.getEnd(),
					ddmStructureSearch.getOrderByComparator()),
				DDMStructureServiceUtil.searchCount(
					_themeDisplay.getCompanyId(), groupIds,
					_ddmStructureItemSelectorCriterion.getClassNameId(),
					_getKeywords(), WorkflowConstants.STATUS_ANY));
		}
		else {
			ddmStructureSearch.setResultsAndTotal(
				() -> DDMStructureServiceUtil.getStructures(
					_themeDisplay.getCompanyId(), groupIds,
					_ddmStructureItemSelectorCriterion.getClassNameId(),
					ddmStructureSearch.getStart(), ddmStructureSearch.getEnd(),
					ddmStructureSearch.getOrderByComparator()),
				DDMStructureServiceUtil.getStructuresCount(
					_themeDisplay.getCompanyId(), groupIds,
					_ddmStructureItemSelectorCriterion.getClassNameId()));
		}

		return ddmStructureSearch;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowManagementToolbar() {
		return true;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private final DDMStructureItemSelectorCriterion
		_ddmStructureItemSelectorCriterion;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortletURL _portletURL;
	private final ThemeDisplay _themeDisplay;

}