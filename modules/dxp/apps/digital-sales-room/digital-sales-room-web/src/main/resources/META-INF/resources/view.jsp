<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="dsr-page">
	<liferay-frontend:screen-navigation
		containerCssClass="col-lg-8"
		containerWrapperCssClass="dsr-container-wrapper"
		key="<%= DigitalSalesRoomScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_DIGITAL_SALES_ROOM %>"
		menubarCssClass="menubar menubar-transparent menubar-vertical-expand-lg"
		navCssClass="col-lg-3"
		portletURL="<%= currentURLObj %>"
	/>
</div>