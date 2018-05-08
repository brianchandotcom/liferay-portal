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

package com.liferay.site.memberships.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.membership.requests.kernel.util.comparator.MembershipRequestCreateDateComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.memberships.web.internal.constants.SiteMembershipsPortletKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ViewMembershipRequestsDisplayContext {

	public ViewMembershipRequestsDisplayContext(
		HttpServletRequest request, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_request = request;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			SiteMembershipsPortletKeys.SITE_MEMBERSHIPS_ADMIN, "display-style",
			"icon");

		return _displayStyle;
	}

	public DropdownItemList getFilterDropdownItems() {
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

	public List<NavigationItem> getNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "pending"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "pending");
						navigationItem.setLabel(
							LanguageUtil.get(_request, "pending"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "approved"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "approved");
						navigationItem.setLabel(
							LanguageUtil.get(_request, "approved"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "denied"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "denied");
						navigationItem.setLabel(
							LanguageUtil.get(_request, "denied"));
					});
			}
		};
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_renderRequest, "orderByCol", "date");

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

	public PortletURL getPortletURL() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_membership_requests.jsp");
		portletURL.setParameter("tabs1", getTabs1());
		portletURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String tabs1 = getTabs1();

		if (Validator.isNotNull(tabs1)) {
			portletURL.setParameter("tabs1", tabs1);
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

	public SearchContainer getSiteMembershipSearchContainer() {
		if (_siteMembershipSearch != null) {
			return _siteMembershipSearch;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer siteMembershipSearch = new SearchContainer(
			_renderRequest, getPortletURL(), null, "no-requests-were-found");

		siteMembershipSearch.setOrderByCol(getOrderByCol());

		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		siteMembershipSearch.setOrderByComparator(
			new MembershipRequestCreateDateComparator(orderByAsc));

		siteMembershipSearch.setOrderByType(orderByType);

		int membershipRequestCount =
			MembershipRequestLocalServiceUtil.searchCount(
				themeDisplay.getScopeGroupId(), getStatusId());

		siteMembershipSearch.setTotal(membershipRequestCount);

		List results = MembershipRequestLocalServiceUtil.search(
			themeDisplay.getScopeGroupId(), getStatusId(),
			siteMembershipSearch.getStart(), siteMembershipSearch.getEnd(),
			siteMembershipSearch.getOrderByComparator());

		siteMembershipSearch.setResults(results);

		_siteMembershipSearch = siteMembershipSearch;

		return _siteMembershipSearch;
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getStatusId() {
		if (Objects.equals(getTabs1(), "approved")) {
			return MembershipRequestConstants.STATUS_APPROVED;
		}
		else if (Objects.equals(getTabs1(), "denied")) {
			return MembershipRequestConstants.STATUS_DENIED;
		}

		return MembershipRequestConstants.STATUS_PENDING;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_request, "tabs1", "pending");

		return _tabs1;
	}

	public int getTotalItems() {
		SearchContainer siteMembershipSearchContainer =
			getSiteMembershipSearchContainer();

		return siteMembershipSearchContainer.getTotal();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = _renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "changeDisplayStyle");
		portletURL.setParameter("redirect", PortalUtil.getCurrentURL(_request));

		return new ViewTypeItemList(portletURL, getDisplayStyle()) {
			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean isDisabledManagementBar() {
		if (getTotalItems() <= 0) {
			return true;
		}

		return false;
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

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "date");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "date"));
					});
			}
		};
	}

	private String _displayStyle;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _siteMembershipSearch;
	private String _tabs1;

}