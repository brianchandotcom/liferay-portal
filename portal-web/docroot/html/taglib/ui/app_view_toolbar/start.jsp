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

<%
boolean includeSelectAll = GetterUtil.getBoolean(request.getAttribute("liferay-ui:app_view_toolbar:includeSelectAll"));
String searchJsp = (String)request.getAttribute("liferay-ui:app_view_toolbar:searchJsp");
%>

<div class="app-view-taglib lfr-header-row">
	<div class="lfr-header-row-content">
		<c:if test="<%= Validator.isNotNull(searchJsp) %>">
			<liferay-util:include page="<%= searchJsp %>" />
		</c:if>

		<div class="toolbar">
			<c:if test="<%= includeSelectAll %>">
				<c:if test="<%= !user.isDefaultUser() %>">
					<aui:input cssClass="select-all-entries" inline="<%= true %>" label="" name="<%= RowChecker.ALL_ROW_IDS %>" type="checkbox" />
				</c:if>
			</c:if>