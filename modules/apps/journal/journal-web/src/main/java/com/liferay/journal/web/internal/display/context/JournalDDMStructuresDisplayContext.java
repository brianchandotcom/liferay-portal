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
import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDDMStructuresDisplayContext {

	public JournalDDMStructuresDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_journalWebConfiguration =
			(JournalWebConfiguration)_httpServletRequest.getAttribute(
				JournalWebConfiguration.class.getName());
	}

	public SearchContainer<DDMStructure> getDDMStructureSearch()
		throws Exception {

		if (_ddmStructureSearch != null) {
			return _ddmStructureSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<DDMStructure> ddmStructureSearch = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-structures");

		if (Validator.isNotNull(_getKeywords())) {
			ddmStructureSearch.setEmptyResultsMessage(
				"no-structures-were-found");
		}

		ddmStructureSearch.setOrderByCol(getOrderByCol());
		ddmStructureSearch.setOrderByComparator(
			DDMUtil.getStructureOrderByComparator(
				getOrderByCol(), getOrderByType()));
		ddmStructureSearch.setOrderByType(getOrderByType());

		long[] groupIds = {themeDisplay.getScopeGroupId()};

		if (_journalWebConfiguration.showAncestorScopesByDefault()) {
			groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
				themeDisplay.getScopeGroupId());
		}

		List<DDMStructure> results = null;
		int total = 0;

		if (Validator.isNotNull(_getKeywords())) {
			results = DDMStructureServiceUtil.search(
				themeDisplay.getCompanyId(), groupIds,
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				_getKeywords(), WorkflowConstants.STATUS_ANY,
				ddmStructureSearch.getStart(), ddmStructureSearch.getEnd(),
				ddmStructureSearch.getOrderByComparator());
			total = DDMStructureServiceUtil.searchCount(
				themeDisplay.getCompanyId(), groupIds,
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				_getKeywords(), WorkflowConstants.STATUS_ANY);
		}
		else {
			results = DDMStructureServiceUtil.getStructures(
				themeDisplay.getCompanyId(), groupIds,
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				ddmStructureSearch.getStart(), ddmStructureSearch.getEnd(),
				ddmStructureSearch.getOrderByComparator());
			total = DDMStructureServiceUtil.getStructuresCount(
				themeDisplay.getCompanyId(), groupIds,
				PortalUtil.getClassNameId(JournalArticle.class.getName()));
		}

		ddmStructureSearch.setResults(results);
		ddmStructureSearch.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));
		ddmStructureSearch.setTotal(total);

		_ddmStructureSearch = ddmStructureSearch;

		return ddmStructureSearch;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, JournalPortletKeys.JOURNAL,
			"ddm-structure-order-by-col", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, JournalPortletKeys.JOURNAL,
			"ddm-structure-order-by-type", "desc");

		return _orderByType;
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
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view_ddm_structures.jsp"
		).setKeywords(
			() -> {
				String keywords = _getKeywords();

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setParameter(
			"orderByCol",
			() -> {
				String orderByCol = getOrderByCol();

				if (Validator.isNotNull(orderByCol)) {
					return orderByCol;
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = getOrderByType();

				if (Validator.isNotNull(orderByType)) {
					return orderByType;
				}

				return null;
			}
		).buildPortletURL();
	}

	private SearchContainer<DDMStructure> _ddmStructureSearch;
	private final HttpServletRequest _httpServletRequest;
	private final JournalWebConfiguration _journalWebConfiguration;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}