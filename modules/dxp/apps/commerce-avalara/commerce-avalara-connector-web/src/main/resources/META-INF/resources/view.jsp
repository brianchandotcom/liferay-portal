<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAvalaraDisplayContext commerceAvalaraDisplayContext = (CommerceAvalaraDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:navigation-bar
	inverted="<%= false %>"
	navigationItems="<%= commerceAvalaraDisplayContext.getNavigationItems() %>"
/>

<c:choose>
	<c:when test="<%= commerceAvalaraDisplayContext.getType() == 0 %>">
		<%@ include file="/tabs/credentials.jspf" %>
	</c:when>
</c:choose>