<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/remote_options/init.jsp" %>

<div id="<portlet:namespace />remote">
	<div class="alert alert-info">
		<liferay-ui:message arguments="<%= StringUtil.toLowerCase(liveGroup.getScopeSimpleName(themeDisplay)) %>" key="export-the-selected-data-to-the-x-of-a-remote-portal-or-to-another-x-in-the-same-portal" translateArguments="<%= false %>" />
	</div>

	<aui:fieldset>
		<aui:input disabled="<%= disableInputs %>" label="remote-host-ip" name="remoteAddress" size="20" type="text" value='<%= MapUtil.getString(settingsMap, "remoteAddress", liveGroupTypeSettingsUnicodeProperties.getProperty("remoteAddress")) %>' />

		<aui:input disabled="<%= disableInputs %>" label="remote-port" name="remotePort" size="10" type="text" value='<%= MapUtil.getString(settingsMap, "remotePort", liveGroupTypeSettingsUnicodeProperties.getProperty("remotePort")) %>' />

		<aui:input disabled="<%= disableInputs %>" label="remote-path-context" name="remotePathContext" size="10" type="text" value='<%= MapUtil.getString(settingsMap, "remotePathContext", liveGroupTypeSettingsUnicodeProperties.getProperty("remotePathContext")) %>' />

		<aui:input disabled="<%= disableInputs %>" label='<%= LanguageUtil.format(request, "remote-x-id", liveGroup.getScopeSimpleName(themeDisplay), false) %>' name="remoteGroupId" size="10" type="text" value='<%= MapUtil.getString(settingsMap, "targetGroupId", liveGroupTypeSettingsUnicodeProperties.getProperty("remoteGroupId")) %>' />

		<aui:input disabled="<%= disableInputs %>" name="remotePrivateLayout" type="hidden" value='<%= MapUtil.getBoolean(settingsMap, "remotePrivateLayout", privateLayout) %>' />
	</aui:fieldset>

	<aui:fieldset>
		<aui:input disabled="<%= disableInputs %>" label="use-a-secure-network-connection" name="secureConnection" type="checkbox" value='<%= MapUtil.getString(settingsMap, "secureConnection", liveGroupTypeSettingsUnicodeProperties.getProperty("secureConnection")) %>' />
	</aui:fieldset>
</div>