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
COREntryDisplayContext corEntryDisplayContext = (COREntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

COREntry corEntry = corEntryDisplayContext.getCOREntry();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCOREntryExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/cor_entry/edit_cor_entry_external_reference_code" />
	<portlet:param name="corEntryId" value="<%= String.valueOf(corEntry.getCOREntryId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= corEntryDisplayContext.getHeaderActionModels() %>"
	bean="<%= corEntry %>"
	beanIdLabel="id"
	externalReferenceCode="<%= corEntry.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCOREntryExternalReferenceCodeURL %>"
	model="<%= COREntry.class %>"
	title="<%= corEntry.getName() %>"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= COREntryScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_COR_ENTRY_GENERAL %>"
	modelBean="<%= corEntry %>"
	portletURL="<%= currentURLObj %>"
/>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"workflowAction", WorkflowConstants.ACTION_PUBLISH
		).build()
	%>'
	module="js/editCOREntry"
/>