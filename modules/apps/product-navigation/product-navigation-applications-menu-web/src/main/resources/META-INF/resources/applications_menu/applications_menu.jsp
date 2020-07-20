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

<%@ include file="/applications_menu/init.jsp" %>

<%
ApplicationsMenuDisplayContext applicationsMenuDisplayContext = new ApplicationsMenuDisplayContext(request);
%>

<li class="control-menu-nav-item control-menu-nav-item-separator">
	<clay:button
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