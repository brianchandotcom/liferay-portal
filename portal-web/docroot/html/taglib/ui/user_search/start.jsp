<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
%>

<liferay-ui:search-form
	page="/html/portlet/users_admin/user_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<%
SearchContainer userSearchContainer = searchContainer;

UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

List<User> results = null;
int total = 0;
%>

<%@ include file="/html/portlet/users_admin/user_search_results.jspf" %>

<%
searchContainer.setResults(results);
searchContainer.setTotal(total);
%>