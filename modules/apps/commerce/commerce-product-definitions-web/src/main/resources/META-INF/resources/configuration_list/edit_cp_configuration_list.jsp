<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPConfigurationListDisplayContext cpConfigurationListDisplayContext = (CPConfigurationListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPConfigurationList cpConfigurationList = cpConfigurationListDisplayContext.getCPConfigurationList();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
%>

<liferay-portlet:renderURL var="editCPConfigurationListExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/cp_configuration_lists/edit_cp_configuration_list_external_reference_code" />
	<portlet:param name="cpConfigurationListId" value="<%= String.valueOf(cpConfigurationListDisplayContext.getCPConfigurationListId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	bean="<%= cpConfigurationList %>"
	beanIdLabel="id"
	displayBeanId="<%= cpConfigurationListDisplayContext.getCPConfigurationListId() %>"
	externalReferenceCode="<%= (cpConfigurationList == null) ? StringPool.BLANK : cpConfigurationList.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= (cpConfigurationList == null) ? StringPool.BLANK : editCPConfigurationListExternalReferenceCodeURL %>"
	model="<%= CPConfigurationList.class %>"
	title="<%= (cpConfigurationList == null) ? StringPool.BLANK : cpConfigurationList.getName() %>"
	wrapperCssClasses="side-panel-top-anchor"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= CPConfigurationListScreenNavigationConstants.SCREEN_NAVIGATION_KEY_CP_CONFIGURATION_LIST_GENERAL %>"
	modelBean="<%= cpConfigurationList %>"
	portletURL="<%= currentURLObj %>"
/>