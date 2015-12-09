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
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

Set<String> allPortletModes = selPortlet.getAllPortletModes();
%>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="supported-clients" />
</liferay-util:include>

<portlet:actionURL name="editSupportedClients" var="editSupportedClientsURL">
	<portlet:param name="mvcPath" value="/edit_supported_clients.jsp" />
	<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<aui:form action="<%= editSupportedClientsURL %>" cssClass="container-fluid-1280" method="post" name=">fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<%
	for (String curPortletMode : allPortletModes) {
		String mobileDevicesParam = "portletSetupSupportedClientsMobileDevices_" + curPortletMode;
		boolean mobileDevicesDefault = selPortlet.hasPortletMode(ContentTypes.XHTML_MP, PortletModeFactory.getPortletMode(curPortletMode));

		boolean mobileDevices = GetterUtil.getBoolean(portletPreferences.getValue(mobileDevicesParam, String.valueOf(mobileDevicesDefault)));
	%>

		<aui:fieldset label='<%= LanguageUtil.get(request, "portlet-mode") + ": " + LanguageUtil.get(request, curPortletMode) %>'>
			<aui:input disabled="<%= true %>" label="regular-browsers" name='<%= "regularBrowsersEnabled" + curPortletMode %>' type="toggle-switch" value="<%= true %>" />

			<aui:input label="mobile-devices" name="<%= mobileDevicesParam %>" type="toggle-switch" value="<%= mobileDevices %>" />
		</aui:fieldset>

	<%
	}
	%>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>