<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<NavigationItem> navigationItems = ddmDisplayContext.getNavigationItem();
%>

<c:if test="<%= navigationItems.size() > 1 %>">
	<clay:navigation-bar
		inverted="<%= true %>"
		navigationItems="<%= navigationItems %>"
	/>
</c:if>