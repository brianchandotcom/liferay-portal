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

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");
Hits hits = (Hits)request.getAttribute("liferay-ui:search:hits");

searchContainer.setTotal(hits.getLength());

NumberFormat doubleFormat = NumberFormat.getInstance(locale);
doubleFormat.setMaximumFractionDigits(2);

NumberFormat integerFormat = NumberFormat.getInstance(locale);
integerFormat.setMaximumFractionDigits(0);
%>

<liferay-ui:message arguments='<%= new Object[] {"<strong>" + ((searchContainer.getResultEnd() > 0) ? searchContainer.getStart() + 1 : 0)+ "</strong> - <strong>" + searchContainer.getResultEnd() + "</strong>", "<strong>" + integerFormat.format(searchContainer.getTotal()) + "</strong>"} %>' key="results-of" translateArguments="<%= false %>" />

<liferay-ui:message arguments='<%= new Object[] {"<strong>" + doubleFormat.format(hits.getSearchTime()) + "</strong>"} %>' key="search-took-x-seconds" translateArguments="<%= false %>" />