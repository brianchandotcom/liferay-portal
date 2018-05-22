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
CommerceCloudClientConfigurationDisplayContext commerceCloudClientConfigurationDisplayContext = (CommerceCloudClientConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-portlet:actionURL name="editCommerceCloudClientConfiguration" portletName="<%= ConfigurationAdminPortletKeys.SYSTEM_SETTINGS %>" var="editCommerceCloudConfigurationActionURL" />

<aui:form action="<%= editCommerceCloudConfigurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault();" + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-frontend:screen-navigation
		context="<%= commerceCloudClientConfigurationDisplayContext %>"
		key="<%= CommerceCloudClientScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_CLOUD_CLIENT_CONFIGURATION %>"
		portletURL="<%= currentURLObj %>"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>