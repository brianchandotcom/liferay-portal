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

<%@ page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.admin.web.internal.display.context.SearchAdminDisplayContext" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%
SearchAdminDisplayContext searchAdminDisplayContext = (SearchAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test="<%= !searchAdminDisplayContext.isMissingSearchEngine() %>">
			<div class="alert alert-info">
				<strong><liferay-ui:message key="search-engine" /></strong>: <%= searchAdminDisplayContext.getStatusString() %>
			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-warning">
				<liferay-ui:message key="no-search-engine-detected-help" />
			</div>
		</c:otherwise>
	</c:choose>
</div>