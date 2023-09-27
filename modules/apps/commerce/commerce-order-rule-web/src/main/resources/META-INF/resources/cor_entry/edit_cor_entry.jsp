<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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