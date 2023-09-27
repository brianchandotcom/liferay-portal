<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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