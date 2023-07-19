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
ConfigurationEntry configurationEntry = (ConfigurationEntry)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_ENTRY);

ConfigurationCategoryMenuDisplay configurationCategoryMenuDisplay = (ConfigurationCategoryMenuDisplay)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_CATEGORY_MENU_DISPLAY);

for (ConfigurationScopeDisplay configurationScopeDisplay : configurationCategoryMenuDisplay.getConfigurationScopeDisplays()) {
	VerticalNavItemList verticalNavItemList = new VerticalNavItemList();

	if (configurationScopeDisplay.isEmpty()) {
		continue;
	}
%>

	<div class="c-ml-3 c-my-2 h6 text-uppercase">
		<liferay-ui:message key='<%= "scope." + configurationScopeDisplay.getScope() %>' />
	</div>

	<%
	List<ConfigurationEntry> configurationEntries = configurationScopeDisplay.getConfigurationEntries();

	final RenderRequest renderRequest1 = renderRequest;

	final RenderResponse renderResponse1 = renderResponse;

	for (ConfigurationEntry curConfigurationEntry : configurationEntries) {
		verticalNavItemList.add(
			verticalNavItem -> {
				String name = curConfigurationEntry.getName();

				verticalNavItem.setHref(curConfigurationEntry.getEditURL(renderRequest1, renderResponse1));
				verticalNavItem.setLabel(name);
				verticalNavItem.setId(name);
				verticalNavItem.setActive(configurationEntry.equals(curConfigurationEntry));
			});
	}
	%>

	<div class="c-ml-3">
		<clay:vertical-nav
			verticalNavItems="<%= verticalNavItemList %>"
		/>
	</div>

<%
}
%>