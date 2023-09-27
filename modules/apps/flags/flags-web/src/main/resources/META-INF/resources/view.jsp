<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long reportedUserId = themeDisplay.getGuestUserId();

Group group = layout.getGroup();

if (group.isUser()) {
	reportedUserId = group.getClassPK();
}
%>

<liferay-flags:flags
	className="<%= Layout.class.getName() %>"
	classPK="<%= layout.getPlid() %>"
	contentTitle="<%= layout.getHTMLTitle(LocaleUtil.getDefault()) %>"
	message="flag-this-page"
	reportedUserId="<%= reportedUserId %>"
/>