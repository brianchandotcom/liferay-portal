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
PortletURL portletURL = PortalUtil.getControlPanelPortletURL(request, liveGroup, StagingProcessesPortletKeys.STAGING_PROCESSES, 0, 0, PortletRequest.RENDER_PHASE);
%>

<liferay-ui:icon
	message="staging-configuration"
	onClick='<%= renderResponse.getNamespace() + "openStagingConfigurationPortlet();" %>'
	url="javascript:;"
/>

<liferay-portlet:renderURL portletMode="<%= PortletMode.VIEW.toString() %>" portletName="<%= StagingConfigurationPortletKeys.STAGING_CONFIGURATION %>" var="stagingConfigurationPortletURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="staging" />
</liferay-portlet:renderURL>

<aui:script>
	function <portlet:namespace />openStagingConfigurationPortlet() {
		var configurationDialog = Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				on: {
					visibleChange: function(event) {
						if (!event.newVal) {
							document.location.href =
								'<%= HtmlUtil.escapeJS(portletURL.toString()) %>';
						}
					}
				}
			},
			id: 'stagingConfiguration',
			title: '<liferay-ui:message key="staging-configuration" />',
			uri:
				'<%= HtmlUtil.escapeJS(stagingConfigurationPortletURL.toString()) %>'
		});
	}
</aui:script>