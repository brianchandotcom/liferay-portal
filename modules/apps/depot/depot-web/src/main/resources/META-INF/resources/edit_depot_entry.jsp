<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:container-fluid
	cssClass="container-view"
>
	<c:if test="<%= GetterUtil.getBoolean(request.getAttribute(DepotAdminWebKeys.SHOW_BREADCRUMB)) %>">
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, true, false, false, true, true) %>"
		/>
	</c:if>

	<liferay-frontend:screen-navigation
		containerCssClass="col-lg-8"
		context="<%= (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY) %>"
		key="<%= DepotScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_DEPOT %>"
		menubarCssClass="menubar menubar-transparent menubar-vertical-expand-lg"
		navCssClass="col-lg-3"
		portletURL="<%= currentURLObj %>"
	/>
</clay:container-fluid>