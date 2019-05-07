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

package com.liferay.password.policies.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.password.policies.admin.web.internal.search.AddOrganizationPasswordPolicyChecker;
import com.liferay.password.policies.admin.web.internal.search.AddUserPasswordPolicyChecker;
import com.liferay.password.policies.admin.web.internal.search.DeleteOrganizationPasswordPolicyChecker;
import com.liferay.password.policies.admin.web.internal.search.DeleteUserPasswordPolicyChecker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.OrganizationSearch;
import com.liferay.portlet.usersadmin.search.OrganizationSearchTerms;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class EditPasswordPolicyAssignmentsManagementToolbarDisplayContext {

	public EditPasswordPolicyAssignmentsManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest, RenderRequest renderRequest,
			RenderResponse renderResponse, String displayStyle, String mvcPath)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_displayStyle = displayStyle;
		_mvcPath = mvcPath;

		long passwordPolicyId = ParamUtil.getLong(
			httpServletRequest, "passwordPolicyId");

		_passwordPolicy = PasswordPolicyLocalServiceUtil.fetchPasswordPolicy(
			passwordPolicyId);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						String methodName = StringPool.BLANK;

						if (_tabs2.equals("users")) {
							methodName = "deleteUsers();";
						}
						else if (_tabs2.equals("organizations")) {
							methodName = "deleteOrganizations();";
						}

						dropdownItem.setHref(
							StringBundler.concat(
								"javascript:", _renderResponse.getNamespace(),
								methodName));
						dropdownItem.setIcon("trash");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "order-by"));
					});
			}
		};
	}

	public String getKeywords() {
		if (Validator.isNull(_keywords)) {
			_keywords = ParamUtil.getString(_httpServletRequest, "keywords");
		}

		return _keywords;
	}

	public String getOrderByCol() {
		if (Validator.isNull(_orderByCol)) {
			_orderByCol = ParamUtil.getString(
				_httpServletRequest, "orderByCol", _getDefaultOrderByCol());
		}

		if (!ArrayUtil.contains(_getOrderColumns(), _orderByCol)) {
			_orderByCol = _getDefaultOrderByCol();
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNull(_orderByType)) {
			_orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");
		}

		return _orderByType;
	}

	public SearchContainer getOrganizationSearchContainer()
		throws PortalException {

		OrganizationSearch organizationSearch = new OrganizationSearch(
			_renderRequest, getPortletURL());

		RowChecker rowChecker = new AddOrganizationPasswordPolicyChecker(
			_renderResponse, _passwordPolicy);

		LinkedHashMap<String, Object> organizationParams =
			new LinkedHashMap<>();

		if (_mvcPath.equals("/edit_password_policy_assignments.jsp")) {
			rowChecker = new DeleteOrganizationPasswordPolicyChecker(
				_renderResponse, _passwordPolicy);

			organizationParams.put(
				"organizationsPasswordPolicies",
				Long.valueOf(_passwordPolicy.getPasswordPolicyId()));
		}

		organizationSearch.setRowChecker(rowChecker);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long parentOrganizationId =
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

		OrganizationSearchTerms searchTerms =
			(OrganizationSearchTerms)organizationSearch.getSearchTerms();

		List<Organization> results = OrganizationLocalServiceUtil.search(
			themeDisplay.getCompanyId(), parentOrganizationId, getKeywords(),
			searchTerms.getType(), searchTerms.getRegionIdObj(),
			searchTerms.getCountryIdObj(), organizationParams,
			organizationSearch.getStart(), organizationSearch.getEnd(),
			organizationSearch.getOrderByComparator());

		int total = OrganizationLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), parentOrganizationId,
			searchTerms.getKeywords(), searchTerms.getType(),
			searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(),
			organizationParams);

		organizationSearch.setResults(results);
		organizationSearch.setTotal(total);

		return organizationSearch;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", _mvcPath);
		portletURL.setParameter("tabs1", "assignees");
		portletURL.setParameter("tabs2", getTabs2());
		portletURL.setParameter(
			"passwordPolicyId",
			String.valueOf(_passwordPolicy.getPasswordPolicyId()));

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter("displayStyle", _displayStyle);

		if (Validator.isNotNull(getKeywords())) {
			portletURL.setParameter("keywords", getKeywords());
		}

		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		if (_searchContainer != null) {
			portletURL.setParameter(
				_searchContainer.getCurParam(),
				String.valueOf(_searchContainer.getCur()));
			portletURL.setParameter(
				_searchContainer.getDeltaParam(),
				String.valueOf(_searchContainer.getDelta()));
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	public SearchContainer getSearchContainer() throws Exception {
		if (Objects.equals(getTabs2(), "organizations")) {
			_searchContainer = getOrganizationSearchContainer();
		}
		else {
			_searchContainer = getUserSearchContainer();
		}

		return _searchContainer;
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public String getTabs2() {
		if (Validator.isNull(_tabs2)) {
			_tabs2 = ParamUtil.getString(_httpServletRequest, "tabs2", "users");
		}

		return _tabs2;
	}

	public SearchContainer getUserSearchContainer() {
		UserSearch userSearch = new UserSearch(_renderRequest, getPortletURL());

		RowChecker rowChecker = new AddUserPasswordPolicyChecker(
			_renderResponse, _passwordPolicy);

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		if (_mvcPath.equals("/edit_password_policy_assignments.jsp")) {
			rowChecker = new DeleteUserPasswordPolicyChecker(
				_renderResponse, _passwordPolicy);

			userParams.put(
				"usersPasswordPolicies",
				Long.valueOf(_passwordPolicy.getPasswordPolicyId()));
		}

		userSearch.setRowChecker(rowChecker);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearch.getSearchTerms();

		List<User> results = UserLocalServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			searchTerms.getStatus(), userParams, userSearch.getStart(),
			userSearch.getEnd(), userSearch.getOrderByComparator());

		int total = UserLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			searchTerms.getStatus(), userParams);

		userSearch.setResults(results);
		userSearch.setTotal(total);

		return userSearch;
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), _displayStyle) {
			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	private String _getDefaultOrderByCol() {
		if (_tabs2.equals("users")) {
			return "last-name";
		}

		return "name";
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				for (String orderColumn : _getOrderColumns()) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(
								Objects.equals(getOrderByCol(), orderColumn));
							dropdownItem.setHref(
								getPortletURL(), "orderByCol", orderColumn);
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, orderColumn));
						});
				}
			}
		};
	}

	private String[] _getOrderColumns() {
		if (_tabs2.equals("users")) {
			return new String[] {"first-name", "last-name", "screen-name"};
		}

		if (_tabs2.equals("organizations")) {
			return new String[] {"name", "type"};
		}

		return new String[0];
	}

	private final String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final String _mvcPath;
	private String _orderByCol;
	private String _orderByType;
	private final PasswordPolicy _passwordPolicy;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;
	private String _tabs2;

}