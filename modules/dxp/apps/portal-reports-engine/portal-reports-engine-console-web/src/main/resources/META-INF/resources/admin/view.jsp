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

<liferay-util:include page="/admin/toolbar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280 main-content-body">
	<c:choose>
		<c:when test="<%= hasAddSourcePermission && reportsEngineDisplayContext.isSourcesTabSelected() %>">
			<liferay-util:include page="/admin/data_source/sources.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= hasAddDefinitionPermission && reportsEngineDisplayContext.isDefinitionsTabSelected() %>">
			<liferay-util:include page="/admin/definition/definitions.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/admin/report/entries.jsp" servletContext="<%= application %>" />
		</c:otherwise>
	</c:choose>
</div>