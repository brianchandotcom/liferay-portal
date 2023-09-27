<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ConfigurationEntry configurationEntry = (ConfigurationEntry)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_ENTRY);

ConfigurationCategoryMenuDisplay configurationCategoryMenuDisplay = (ConfigurationCategoryMenuDisplay)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_CATEGORY_MENU_DISPLAY);

for (ConfigurationScopeDisplay configurationScopeDisplay : configurationCategoryMenuDisplay.getConfigurationScopeDisplays()) {
	if (configurationScopeDisplay.isEmpty()) {
		continue;
	}
%>

	<div class="c-ml-3 c-my-2 h6 text-uppercase">
		<liferay-ui:message key='<%= "scope." + configurationScopeDisplay.getScope() %>' />
	</div>

	<div class="c-ml-3">
		<clay:vertical-nav
			verticalNavItems="<%= configurationCategoryMenuDisplay.getVerticalNavItemList(configurationEntry, configurationScopeDisplay, renderRequest, renderResponse) %>"
		/>
	</div>

<%
}
%>