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
boolean includeSelectAll = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app_view_toolbar:includeSelectAll"));
String searchJsp = (String)request.getAttribute("liferay-ui:app_view_toolbar:searchJsp");

String cssClass = "select-all-entries";

if (!includeSelectAll) {
	cssClass += " hide";
}
%>

<div class="app-view-taglib">
	<div class="lfr-header-row-content">
		<c:if test="<%= Validator.isNotNull(searchJsp) %>">
			<liferay-util:include page="<%= searchJsp %>" />
		</c:if>

		<div>
			<c:if test="<%= !user.isDefaultUser() %>">
				<aui:input cssClass="<%= cssClass %>" inline="<%= true %>" label="" name="<%= RowChecker.ALL_ROW_IDS %>" title="select-all" type="checkbox" />
			</c:if>