<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceReturnEditDisplayContext commerceReturnEditDisplayContext = (CommerceReturnEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceReturn commerceReturn = commerceReturnEditDisplayContext.getCommerceReturn();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
%>

<liferay-portlet:renderURL var="editCommerceReturnExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_return/edit_commerce_return_external_reference_code" />
	<portlet:param name="commerceReturnId" value="<%= String.valueOf(commerceReturn.getId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceReturnEditDisplayContext.getHeaderActionModels() %>"
	additionalStatusLabel="<%= commerceReturn.getReturnStatus() %>"
	additionalStatusLabelStyle="<%= CommerceReturnConstants.getReturnStatusLabelStyle(commerceReturn.getReturnStatus()) %>"
	bean="<%= commerceReturn.getObjectEntry() %>"
	externalReferenceCode="<%= commerceReturn.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommerceReturnExternalReferenceCodeURL %>"
	model="<%= CommerceReturn.class %>"
	thumbnailUrl="<%= commerceReturnEditDisplayContext.getCommerceReturnAccountEntryThumbnailURL() %>"
	title="<%= String.valueOf(commerceReturnEditDisplayContext.getCommerceReturnId()) %>"
/>

<div id="<portlet:namespace />editReturnContainer">
	<liferay-frontend:screen-navigation
		containerWrapperCssClass="container mt-4"
		key="<%= CommerceReturnScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_RETURN_GENERAL %>"
		modelBean="<%= commerceReturn %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>

<liferay-frontend:component
	module="{editCommerceReturn} from commerce-order-web"
/>