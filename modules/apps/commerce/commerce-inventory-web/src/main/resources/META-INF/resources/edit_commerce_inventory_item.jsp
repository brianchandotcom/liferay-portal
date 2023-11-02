<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryDisplayContext commerceInventoryDisplayContext = (CommerceInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:header
	actions="<%= commerceInventoryDisplayContext.getHeaderActionModels() %>"
	beanIdLabel=""
	externalReferenceCode=""
	externalReferenceCodeEditUrl=""
	model="<%= CommerceInventoryWarehouseItem.class %>"
	thumbnailUrl='<%= PortalUtil.getPortalURL(request) + PortalUtil.getPathContext() + "/o/commerce-inventory-web/images/inventory-default-icon.svg" %>'
	title="<%= commerceInventoryDisplayContext.getTitle() %>"
	transitionPortletURL="<%= commerceInventoryDisplayContext.getTransitionInventoryPortletURL() %>"
	wrapperCssClasses="side-panel-top-anchor"
/>

<div id="<portlet:namespace />editInventoryItemContainer">
	<liferay-frontend:screen-navigation
		containerWrapperCssClass="container mt-4"
		key="<%= CommerceInventoryScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_INVENTORY %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>