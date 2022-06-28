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
CommerceChannelDisplayContext commerceChannelDisplayContext = (CommerceChannelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceChannel commerceChannel = commerceChannelDisplayContext.getCommerceChannel();

String thumbnailURL = PortalUtil.getPortalURL(request) + PortalUtil.getPathContext() + "/o/commerce-channel-web/images/channel-default-icon.svg";

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCommerceChannelExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_channels/edit_commerce_channel_external_reference_code" />
	<portlet:param name="commerceChannelId" value="<%= String.valueOf(commerceChannel.getCommerceChannelId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceChannelDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceChannel %>"
	beanIdLabel="id"
	externalReferenceCode="<%= commerceChannel.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommerceChannelExternalReferenceCodeURL %>"
	model="<%= CommerceChannel.class %>"
	thumbnailUrl="<%= thumbnailURL %>"
	title="<%= commerceChannel.getName() %>"
/>

<div id="<portlet:namespace />editChannelContainer">
	<liferay-frontend:screen-navigation
		containerWrapperCssClass="container mt-4"
		key="<%= CommerceChannelScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_CHANNEL_GENERAL %>"
		modelBean="<%= commerceChannel %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>