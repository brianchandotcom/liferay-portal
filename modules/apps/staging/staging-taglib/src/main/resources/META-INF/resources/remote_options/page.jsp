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

<%@ include file="/remote_options/init.jsp" %>

<div id="<portlet:namespace />remote">
	<div class="alert alert-info">
		<liferay-ui:message key="export-the-selected-data-to-the-site-of-a-remote-portal-or-to-another-site-in-the-same-portal" />
	</div>

	<aui:fieldset>
		<aui:input disabled="<%= disableInputs %>" label="remote-host-ip" name="remoteAddress" size="20" type="text" value='<%= MapUtil.getString(settingsMap, "remoteAddress", liveGroupTypeSettings.getProperty("remoteAddress")) %>' />

		<aui:input disabled="<%= disableInputs %>" label="remote-port" name="remotePort" size="10" type="text" value='<%= MapUtil.getString(settingsMap, "remotePort", liveGroupTypeSettings.getProperty("remotePort")) %>' />

		<aui:input disabled="<%= disableInputs %>" label="remote-path-context" name="remotePathContext" size="10" type="text" value='<%= MapUtil.getString(settingsMap, "remotePathContext", liveGroupTypeSettings.getProperty("remotePathContext")) %>' />

		<aui:input disabled="<%= disableInputs %>" label="remote-site-id" name="remoteGroupId" size="10" type="text" value='<%= MapUtil.getString(settingsMap, "targetGroupId", liveGroupTypeSettings.getProperty("remoteGroupId")) %>' />

		<aui:input disabled="<%= disableInputs %>" name="remotePrivateLayout" type="hidden" value='<%= MapUtil.getBoolean(settingsMap, "remotePrivateLayout", privateLayout) %>' />
	</aui:fieldset>

	<aui:fieldset>
		<aui:input disabled="<%= disableInputs %>" label="use-a-secure-network-connection" name="secureConnection" type="checkbox" value='<%= MapUtil.getString(settingsMap, "secureConnection", liveGroupTypeSettings.getProperty("secureConnection")) %>' />
	</aui:fieldset>
</div>