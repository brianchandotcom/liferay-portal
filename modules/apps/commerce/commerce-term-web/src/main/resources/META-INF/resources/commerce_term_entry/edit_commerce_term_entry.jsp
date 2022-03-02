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
CommerceTermEntryDisplayContext commerceTermEntryDisplayContext = (CommerceTermEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTermEntry commerceTermEntry = commerceTermEntryDisplayContext.getCommerceTermEntry();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCommerceTermEntryExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_term_entry/edit_commerce_term_entry_external_reference_code" />
	<portlet:param name="commerceTermEntryId" value="<%= String.valueOf(commerceTermEntry.getCommerceTermEntryId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceTermEntryDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceTermEntry %>"
	beanIdLabel="id"
	externalReferenceCode="<%= commerceTermEntry.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommerceTermEntryExternalReferenceCodeURL %>"
	model="<%= CommerceTermEntry.class %>"
	title="<%= commerceTermEntry.getLabel(themeDisplay.getLanguageId()) %>"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= CommerceTermEntryScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_COMMERCE_TERM_ENTRY_GENERAL %>"
	modelBean="<%= commerceTermEntry %>"
	portletURL="<%= currentURLObj %>"
/>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"workflowAction", WorkflowConstants.ACTION_PUBLISH
		).build()
	%>'
	module="js/editCommerceTermEntry"
/>