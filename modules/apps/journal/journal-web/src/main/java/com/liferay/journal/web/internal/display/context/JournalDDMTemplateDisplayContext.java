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
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.security.permission.resource.DDMTemplatePermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
public class JournalDDMTemplateDisplayContext {

	public JournalDDMTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_request = PortalUtil.getHttpServletRequest(renderRequest);

		_journalWebConfiguration =
			(JournalWebConfiguration)_request.getAttribute(
				JournalWebConfiguration.class.getName());
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteDDMTemplates");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(), "mvcPath",
							"/edit_ddm_template.jsp", "redirect",
							themeDisplay.getURLCurrent(), "groupId",
							String.valueOf(themeDisplay.getScopeGroupId()),
							"classNameId",
							String.valueOf(
								PortalUtil.getClassNameId(DDMStructure.class)),
							"classPK", String.valueOf(_getClassPK()),
							"resourceClassNameId",
							String.valueOf(
								PortalUtil.getClassNameId(
									JournalArticle.class)),
							"type", DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "add"));
					});
			}
		};
	}

	public DDMStructure getDDMStructure() {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		long structureClassNameId = PortalUtil.getClassNameId(
			DDMStructure.class);

		if ((_getClassPK() <= 0) ||
			(structureClassNameId != _getClassNameId())) {

			return _ddmStructure;
		}

		_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			_getClassPK());

		return _ddmStructure;
	}

	public SearchContainer getDDMTemplateSearch() throws Exception {
		if (_ddmTemplateSearch != null) {
			return _ddmTemplateSearch;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer ddmTemplateSearch = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-templates");

		if (Validator.isNotNull(_getKeywords())) {
			ddmTemplateSearch.setEmptyResultsMessage("no-templates-were-found");
		}

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMTemplate> orderByComparator =
			DDMUtil.getTemplateOrderByComparator(
				getOrderByCol(), getOrderByType());

		ddmTemplateSearch.setOrderByCol(orderByCol);
		ddmTemplateSearch.setOrderByComparator(orderByComparator);
		ddmTemplateSearch.setOrderByType(orderByType);
		ddmTemplateSearch.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		long[] groupIds = {themeDisplay.getScopeGroupId()};

		if (_journalWebConfiguration.showAncestorScopesByDefault()) {
			groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
				themeDisplay.getScopeGroupId());
		}

		int total = DDMTemplateServiceUtil.searchCount(
			themeDisplay.getCompanyId(), groupIds,
			new long[] {PortalUtil.getClassNameId(DDMStructure.class)},
			_getDDMTemplateClassPKs(),
			PortalUtil.getClassNameId(JournalArticle.class), _getKeywords(),
			StringPool.BLANK, StringPool.BLANK, WorkflowConstants.STATUS_ANY);

		ddmTemplateSearch.setTotal(total);

		List<DDMTemplate> results = DDMTemplateServiceUtil.search(
			themeDisplay.getCompanyId(), groupIds,
			new long[] {PortalUtil.getClassNameId(DDMStructure.class)},
			_getDDMTemplateClassPKs(),
			PortalUtil.getClassNameId(JournalArticle.class), _getKeywords(),
			StringPool.BLANK, StringPool.BLANK, WorkflowConstants.STATUS_ANY,
			ddmTemplateSearch.getStart(), ddmTemplateSearch.getEnd(),
			ddmTemplateSearch.getOrderByComparator());

		ddmTemplateSearch.setResults(results);

		_ddmTemplateSearch = ddmTemplateSearch;

		return ddmTemplateSearch;
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
		PortletURL searchActionURL = _getPortletURL();

		return searchActionURL.toString();
	}

	public String getSortingURL() {
		PortletURL sortingURL = _getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() throws Exception {
		SearchContainer<?> searchContainer = getDDMTemplateSearch();

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

	public boolean isShowAddButton() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (DDMTemplatePermission.containsAddTemplatePermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				PortalUtil.getClassNameId(JournalArticle.class))) {

			return true;
		}

		return false;
	}

	private long _getClassNameId() {
		if (_classNameId != null) {
			return _classNameId;
		}

		_classNameId = ParamUtil.getLong(_request, "classNameId");

		return _classNameId;
	}

	private long _getClassPK() {
		if (_classPK != null) {
			return _classPK;
		}

		_classPK = ParamUtil.getLong(_request, "classPK");

		return _classPK;
	}

	private long[] _getDDMTemplateClassPKs() {
		if (_getClassPK() > 0) {
			return new long[] {_getClassPK()};
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getClassStructures(
				themeDisplay.getCompanyId(),
				PortalUtil.getClassNameId(JournalArticle.class));

		List<Long> classPKs = ListUtil.toList(
			ddmStructures, DDMStructure.STRUCTURE_ID_ACCESSOR);

		classPKs.add(0, 0L);

		return ArrayUtil.toLongArray(classPKs);
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

		portletURL.setParameter("mvcPath", "/view_ddm_templates.jsp");

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

	private Long _classNameId;
	private Long _classPK;
	private DDMStructure _ddmStructure;
	private SearchContainer _ddmTemplateSearch;
	private final JournalWebConfiguration _journalWebConfiguration;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}