<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
String title = (String)request.getAttribute("liferay-ui:app_view_navigation:title");
%>

<div class="app-view-navigation-taglib">
	<div class="lfr-header-row">
		<div class="lfr-header-row-content" id="<portlet:namespace />parentTitleContainer">
			<div class="parent-title" id="<portlet:namespace />parentTitle">
				<c:if test="<%= Validator.isNotNull(title) %>">
					<span>
						<%= title %>
					</span>
				</c:if>
			</div>
		</div>
	</div>
	<div class="body-row">
		<div id="<portlet:namespace />listViewContainer">
			<div class="lfr-list-view-content" id="<portlet:namespace />folderContainer">