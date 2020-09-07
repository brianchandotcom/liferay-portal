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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CommerceInventoryDisplayContext commerceInventoryDisplayContext = (CommerceInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<commerce-ui:header
	actions="<%= commerceInventoryDisplayContext.getHeaderActionModels() %>"
	beanIdLabel=""
	externalReferenceCode=""
	externalReferenceCodeEditUrl=""
	model="<%= CommerceInventoryWarehouseItem.class %>"
	thumbnailUrl='<%= PortalUtil.getPortalURL(request) + "/o/commerce-inventory-web/images/inventory-default-icon.svg" %>'
	title="<%= commerceInventoryDisplayContext.getSku() %>"
	transitionPortletURL="<%= commerceInventoryDisplayContext.getTransitionInventoryPortletURL() %>"
	wrapperCssClasses="side-panel-top-anchor"
/>

<div id="<portlet:namespace />editInventoryItemContainer">
	<liferay-frontend:screen-navigation
		fullContainerCssClass="col-12 pt-4"
		key="<%= CommerceInventoryScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_INVENTORY %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>