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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.membership.requests.kernel.util.comparator.MembershipRequestCreateDateComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.memberships.web.internal.servlet.taglib.util.ViewMembershipRequetsPendingActionDropdownItemsProvider;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ViewMembershipRequestsDisplayContext {

	public ViewMembershipRequestsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public List<DropdownItem> getActionDropdownItems(
			MembershipRequest membershipRequest)
		throws Exception {

		ViewMembershipRequetsPendingActionDropdownItemsProvider
			viewMembershipRequetsPendingActionDropdownItemsProvider =
				new ViewMembershipRequetsPendingActionDropdownItemsProvider(
					membershipRequest, _renderRequest, _renderResponse);

		return viewMembershipRequetsPendingActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "icon");

		return _displayStyle;
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
							LanguageUtil.get(_httpServletRequest, "pending"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "approved"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "approved");
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "approved"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "denied"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "denied");
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "denied"));
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
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_membership_requests.jsp");
		portletURL.setParameter("tabs1", getTabs1());
		portletURL.setParameter(
			"groupId",
			String.valueOf(themeDisplay.getSiteGroupIdOrLiveGroupId()));

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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
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
				themeDisplay.getSiteGroupIdOrLiveGroupId(), getStatusId());

		siteMembershipSearch.setTotal(membershipRequestCount);

		List results = MembershipRequestLocalServiceUtil.search(
			themeDisplay.getSiteGroupIdOrLiveGroupId(), getStatusId(),
			siteMembershipSearch.getStart(), siteMembershipSearch.getEnd(),
			siteMembershipSearch.getOrderByComparator());

		siteMembershipSearch.setResults(results);

		_siteMembershipSearch = siteMembershipSearch;

		return _siteMembershipSearch;
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

		_tabs1 = ParamUtil.getString(_httpServletRequest, "tabs1", "pending");

		return _tabs1;
	}

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _siteMembershipSearch;
	private String _tabs1;

}