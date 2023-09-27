<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-util:dynamic-include key="com.liferay.users.admin.web#/user/memberships.jsp#pre" />

<clay:sheet-section>
	<liferay-util:include page="/user/sites.jsp" servletContext="<%= application %>" />
</clay:sheet-section>

<clay:sheet-section>
	<liferay-util:include page="/user/user_groups.jsp" servletContext="<%= application %>" />
</clay:sheet-section>

<liferay-util:dynamic-include key="com.liferay.users.admin.web#/user/memberships.jsp#post" />