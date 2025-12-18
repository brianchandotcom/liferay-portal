<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewDigitalSalesRoomListDisplayContext viewDigitalSalesRoomListDisplayContext = (ViewDigitalSalesRoomListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<h2 class="font-weight-semi-bold page-title text-7 text-dark">
	<liferay-ui:message key="rooms" />
</h2>

<div class="portlet-digital-sales-room-management">
	<frontend-data-set:headless-display
		apiURL="<%= viewDigitalSalesRoomListDisplayContext.getAPIURL() %>"
		creationMenu="<%= viewDigitalSalesRoomListDisplayContext.getCreationMenu() %>"
		fdsActionDropdownItems="<%= viewDigitalSalesRoomListDisplayContext.getFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= DigitalSalesRoomFDSNames.ROOMS %>"
		propsTransformer="{DSRListFDSPropsTransformer} from digital-sales-room-web"
		style="fluid"
	/>
</div>