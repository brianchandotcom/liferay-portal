<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-layout:render-layout-utility-page-entry
	type="<%= LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR %>"
>
	<clay:alert
		displayType="danger"
		message="internal-server-error"
	/>

	<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

	<br /><br />

	<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>

	<%
	statusDisplayContext.logSessionErrors();
	%>

</liferay-layout:render-layout-utility-page-entry>