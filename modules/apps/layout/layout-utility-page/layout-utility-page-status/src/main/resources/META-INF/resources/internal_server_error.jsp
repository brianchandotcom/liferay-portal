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

<liferay-layout:render-layout-utility-page-entry
	type="<%= LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR %>"
>
	<h3 class="alert alert-danger">
		<liferay-ui:message key="internal-server-error" />
	</h3>

	<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

	<br /><br />

	<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>

	<%
	statusDisplayContext.logSessionErrors();
	%>

</liferay-layout:render-layout-utility-page-entry>