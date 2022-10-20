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
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

Set<String> allPortletModes = selPortlet.getAllPortletModes();
%>

<portlet:actionURL name="editSupportedClients" var="editSupportedClientsURL">
	<portlet:param name="mvcPath" value="/edit_supported_clients.jsp" />
	<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="supported-clients" />
</liferay-util:include>

<div class="portlet-configuration-edit-supported-clients">
	<liferay-frontend:edit-form
		action="<%= editSupportedClientsURL %>"
		cssClass="form"
		method="post"
		name="fm"
	>
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
		<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

		<liferay-frontend:edit-form-body>
			<liferay-frontend:fieldset-group>

				<%
				boolean first = true;

				for (String curPortletMode : allPortletModes) {
					String mobileDevicesParam = "portletSetupSupportedClientsMobileDevices_" + curPortletMode;
					boolean mobileDevicesDefault = selPortlet.hasPortletMode(ContentTypes.XHTML_MP, PortletModeFactory.getPortletMode(curPortletMode));
				%>

					<liferay-frontend:fieldset
						collapsed="<%= !first %>"
						collapsible="<%= true %>"
						label='<%= LanguageUtil.get(request, "portlet-mode") + ": " + LanguageUtil.get(request, curPortletMode) %>'
					>
						<aui:input disabled="<%= true %>" inlineLabel="right" label="regular-browsers" labelCssClass="simple-toggle-switch" name='<%= "regularBrowsersEnabled" + curPortletMode %>' type="toggle-switch" value="<%= true %>" />

						<aui:input inlineLabel="right" label="mobile-devices" labelCssClass="simple-toggle-switch" name="<%= mobileDevicesParam %>" type="toggle-switch" value="<%= GetterUtil.getBoolean(portletPreferences.getValue(mobileDevicesParam, String.valueOf(mobileDevicesDefault))) %>" />
					</liferay-frontend:fieldset>

				<%
					first = false;
				}
				%>

			</liferay-frontend:fieldset-group>
		</liferay-frontend:edit-form-body>

		<liferay-frontend:edit-form-footer>
			<liferay-frontend:edit-form-buttons />
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</div>