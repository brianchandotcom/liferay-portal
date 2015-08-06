<%--
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
--%>

<%@ include file="/html/taglib/init.jsp" %>

<portlet:defineObjects />

<%
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:user-search:portletURL");
RowChecker rowChecker = (RowChecker)request.getAttribute("liferay-ui:user-search:rowChecker");
LinkedHashMap<String, Object> userParams = (LinkedHashMap<String, Object>)request.getAttribute("liferay-ui:user-search:userParams");

UserSearch searchContainer = new UserSearch(renderRequest, portletURL);

request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);

searchContainer.setRowChecker(rowChecker);

SearchContainer userSearchContainer = searchContainer;

UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

List<User> results = null;
int total = 0;
%>

<liferay-ui:user-search-form />

<%
if (searchTerms.isAdvancedSearch()) {
	total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.getStatus(), userParams, searchTerms.isAndOperator());

	results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.getStatus(), userParams, searchTerms.isAndOperator(), userSearchContainer.getStart(), userSearchContainer.getEnd(), userSearchContainer.getOrderByComparator());
}
else {
	total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams);

	results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams, userSearchContainer.getStart(), userSearchContainer.getEnd(), userSearchContainer.getOrderByComparator());
}

searchContainer.setResults(results);
searchContainer.setTotal(total);
%>