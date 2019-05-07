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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalSelectDDMStructureDisplayContext {

	public JournalSelectDDMStructureDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public long getClassPK() {
		if (_classPK != null) {
			return _classPK;
		}

		_classPK = ParamUtil.getLong(_renderRequest, "classPK");

		return _classPK;
	}

	public SearchContainer getDDMStructureSearch() throws Exception {
		if (_ddmStructureSearch != null) {
			return _ddmStructureSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer ddmStructureSearch = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-structures");

		if (Validator.isNotNull(_getKeywords())) {
			ddmStructureSearch.setEmptyResultsMessage(
				"no-structures-were-found");
		}

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMStructure> orderByComparator =
			DDMUtil.getStructureOrderByComparator(
				getOrderByCol(), getOrderByType());

		ddmStructureSearch.setOrderByCol(orderByCol);
		ddmStructureSearch.setOrderByComparator(orderByComparator);
		ddmStructureSearch.setOrderByType(orderByType);

		long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
			themeDisplay.getScopeGroupId());

		int total = 0;

		if (_isSearchRestriction()) {
			total = DDMStructureLinkLocalServiceUtil.getStructureLinksCount(
				_getSearchRestrictionClassNameId(),
				_getSearchRestrictionClassPK());
		}
		else if (Validator.isNotNull(_getKeywords())) {
			total = DDMStructureServiceUtil.searchCount(
				themeDisplay.getCompanyId(), groupIds,
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				_getKeywords(), WorkflowConstants.STATUS_ANY);
		}
		else {
			total = DDMStructureServiceUtil.getStructuresCount(
				themeDisplay.getCompanyId(), groupIds,
				PortalUtil.getClassNameId(JournalArticle.class.getName()));
		}

		ddmStructureSearch.setTotal(total);

		List<DDMStructure> results = null;

		if (_isSearchRestriction()) {
			results =
				DDMStructureLinkLocalServiceUtil.getStructureLinkStructures(
					_getSearchRestrictionClassNameId(),
					_getSearchRestrictionClassPK(),
					ddmStructureSearch.getStart(), ddmStructureSearch.getEnd());
		}
		else if (Validator.isNotNull(_getKeywords())) {
			results = DDMStructureServiceUtil.search(
				themeDisplay.getCompanyId(), groupIds,
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				_getKeywords(), WorkflowConstants.STATUS_ANY,
				ddmStructureSearch.getStart(), ddmStructureSearch.getEnd(),
				ddmStructureSearch.getOrderByComparator());
		}
		else {
			results = DDMStructureServiceUtil.getStructures(
				themeDisplay.getCompanyId(), groupIds,
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				ddmStructureSearch.getStart(), ddmStructureSearch.getEnd(),
				ddmStructureSearch.getOrderByComparator());
		}

		ddmStructureSearch.setResults(results);

		_ddmStructureSearch = ddmStructureSearch;

		return ddmStructureSearch;
	}

	public String getEventName() {
		return _renderResponse.getNamespace() + "selectDDMStructure";
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public String getSortingURL() {
		PortletURL sortingURL = _getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public boolean isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_renderRequest, "keywords");

		return _keywords;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/select_ddm_structure.jsp");

		long classPK = getClassPK();

		if (classPK != 0) {
			portletURL.setParameter("classPK", String.valueOf(classPK));
		}

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	private long _getSearchRestrictionClassNameId() {
		if (_searchRestrictionClassNameId != null) {
			return _searchRestrictionClassNameId;
		}

		_searchRestrictionClassNameId = ParamUtil.getLong(
			_httpServletRequest, "searchRestrictionClassNameId");

		return _searchRestrictionClassNameId;
	}

	private long _getSearchRestrictionClassPK() {
		if (_searchRestrictionClassPK != null) {
			return _searchRestrictionClassPK;
		}

		_searchRestrictionClassPK = ParamUtil.getLong(
			_httpServletRequest, "searchRestrictionClassPK");

		return _searchRestrictionClassPK;
	}

	private boolean _isSearchRestriction() {
		if (_searchRestriction != null) {
			return _searchRestriction;
		}

		_searchRestriction = ParamUtil.getBoolean(
			_httpServletRequest, "searchRestriction");

		return _searchRestriction;
	}

	private Long _classPK;
	private SearchContainer _ddmStructureSearch;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _searchRestriction;
	private Long _searchRestrictionClassNameId;
	private Long _searchRestrictionClassPK;

}