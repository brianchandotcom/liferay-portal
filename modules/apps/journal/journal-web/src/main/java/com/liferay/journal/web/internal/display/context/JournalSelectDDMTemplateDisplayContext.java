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
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
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
public class JournalSelectDDMTemplateDisplayContext {

	public JournalSelectDDMTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_request = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public long getDDMTemplateId() {
		if (_ddmTemplateId != null) {
			return _ddmTemplateId;
		}

		_ddmTemplateId = ParamUtil.getLong(_request, "ddmTemplateId");

		return _ddmTemplateId;
	}

	public String getEventName() {
		return _renderResponse.getNamespace() + "selectTemplate";
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

	public String getSearchActionURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/select_ddm_template.jsp");
		portletURL.setParameter(
			"ddmTemplateId", String.valueOf(getDDMTemplateId()));
		portletURL.setParameter(
			"ddmStructureId", String.valueOf(_getDDMStructureId()));
		portletURL.setParameter("eventName", getEventName());

		return portletURL.toString();
	}

	public String getSortingURL() {
		PortletURL sortingURL = _getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public SearchContainer getTemplateSearch() throws Exception {
		if (_templateSearch != null) {
			return _templateSearch;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer templateSearch = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-templates");

		if (templateSearch.isSearch()) {
			templateSearch.setEmptyResultsMessage("no-templates-were-found");
		}

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMTemplate> orderByComparator =
			DDMUtil.getTemplateOrderByComparator(
				getOrderByCol(), getOrderByType());

		templateSearch.setOrderByCol(orderByCol);
		templateSearch.setOrderByComparator(orderByComparator);
		templateSearch.setOrderByType(orderByType);

		long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
			themeDisplay.getScopeGroupId());

		int total = DDMTemplateServiceUtil.searchCount(
			themeDisplay.getCompanyId(), groupIds,
			new long[] {PortalUtil.getClassNameId(DDMStructure.class)},
			new long[] {_getDDMStructureId()},
			PortalUtil.getClassNameId(JournalArticle.class.getName()),
			_getKeywords(), StringPool.BLANK, StringPool.BLANK,
			WorkflowConstants.STATUS_ANY);

		templateSearch.setTotal(total);

		List<DDMTemplate> results = DDMTemplateServiceUtil.search(
			themeDisplay.getCompanyId(), groupIds,
			new long[] {PortalUtil.getClassNameId(DDMStructure.class)},
			new long[] {_getDDMStructureId()},
			PortalUtil.getClassNameId(JournalArticle.class.getName()),
			_getKeywords(), StringPool.BLANK, StringPool.BLANK,
			WorkflowConstants.STATUS_ANY, templateSearch.getStart(),
			templateSearch.getEnd(), templateSearch.getOrderByComparator());

		templateSearch.setResults(results);

		_templateSearch = templateSearch;

		return _templateSearch;
	}

	public int getTotalItems() throws Exception {
		SearchContainer<?> searchContainer = getTemplateSearch();

		return searchContainer.getTotal();
	}

	public boolean isDisabledManagementBar() throws Exception {
		if (isSearch()) {
			return false;
		}

		if (getTotalItems() > 0) {
			return false;
		}

		return true;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private long _getDDMStructureId() {
		if (_ddmStructureId != null) {
			return _ddmStructureId;
		}

		_ddmStructureId = ParamUtil.getLong(_renderRequest, "ddmStructureId");

		return _ddmStructureId;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(
							_getPortletURL(), "navigation", "all");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});
			}
		};
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_renderRequest, "keywords");

		return _keywords;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "modified-date"));
						dropdownItem.setHref(
							_getPortletURL(), "orderByCol", "modified-date");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "modified-date"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "id"));
						dropdownItem.setHref(
							_getPortletURL(), "orderByCol", "id");
						dropdownItem.setLabel(LanguageUtil.get(_request, "id"));
					});
			}
		};
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/select_ddm_template.jsp");

		long ddmTemplateId = getDDMTemplateId();

		if (ddmTemplateId != 0) {
			portletURL.setParameter(
				"ddmTemplateId", String.valueOf(ddmTemplateId));
		}

		long ddmStructureId = _getDDMStructureId();

		if (ddmStructureId != 0) {
			portletURL.setParameter(
				"ddmStructureId", String.valueOf(ddmStructureId));
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

	private Long _ddmStructureId;
	private Long _ddmTemplateId;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _templateSearch;

}