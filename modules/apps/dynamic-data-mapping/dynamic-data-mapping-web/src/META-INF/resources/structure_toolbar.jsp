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

<%@ include file="/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute(WebKeys.SEARCH_CONTAINER);
String eventName = ParamUtil.getString(request, "eventName", "selectStructure");
long classPK = ParamUtil.getLong(request, "classPK");
%>

<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="searchURL">
	<portlet:param name="mvcPath" value="/select_structure.jsp" />
	<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	<portlet:param name="eventName" value="<%= eventName %>" />
</liferay-portlet:renderURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav-bar-search searchContainer="<%= searchContainer %>">
		<aui:form action="<%= searchURL.toString() %>" method="post" name="fm1">
			<liferay-util:include page="/structure_search.jsp" servletContext="<%= application %>" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>