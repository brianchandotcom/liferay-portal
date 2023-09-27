<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/applications_menu/init.jsp" %>

<%
ApplicationsMenuDisplayContext applicationsMenuDisplayContext = new ApplicationsMenuDisplayContext(request);
%>

<li class="control-menu-nav-item control-menu-nav-item-separator">
	<clay:button
		aria-haspopup="dialog"
		cssClass="lfr-portal-tooltip"
		displayType="unstyled"
		icon="grid"
		small="<%= true %>"
		title="applications-menu"
	/>

	<react:component
		module="js/ApplicationsMenu"
		props="<%= applicationsMenuDisplayContext.getApplicationsMenuComponentData() %>"
	/>
</li>