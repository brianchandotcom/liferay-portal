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
String id = PortalUtil.generateRandomKey(request, "configuration_category_menu.jsp");

ConfigurationEntry configurationEntry = (ConfigurationEntry)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_ENTRY);

ConfigurationCategoryMenuDisplay configurationCategoryMenuDisplay = (ConfigurationCategoryMenuDisplay)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_CATEGORY_MENU_DISPLAY);
%>

<nav class="menubar menubar-transparent menubar-vertical-expand-md">
	<a aria-controls="<%= id %>" aria-expanded="false" class="menubar-toggler" data-toggle="collapse" href="#<%= id %>" role="button">
		<liferay-ui:message key="<%= configurationEntry.getName() %>" />

		<aui:icon image="caret-bottom" markupView="lexicon" />
	</a>

	<div class="collapse menubar-collapse" id="<%= id %>">
		<ul class="nav nav-nested">

			<%
			for (ConfigurationScopeDisplay configurationScopeDisplay : configurationCategoryMenuDisplay.getConfigurationScopeDisplays()) {
				if (configurationScopeDisplay.isEmpty()) {
					continue;
				}

				List<ConfigurationEntry> configurationEntries = configurationScopeDisplay.getConfigurationEntries();
			%>

				<li class="nav-item">
					<a class="nav-link text-uppercase">
						<liferay-ui:message key='<%= "scope." + configurationScopeDisplay.getScope() %>' />
					</a>

					<div>
						<ul class="nav nav-stacked">

							<%
							for (ConfigurationEntry curConfigurationEntry : configurationEntries) {
								String cssClass = configurationEntry.equals(curConfigurationEntry) ? "active nav-link": "nav-link";
							%>

								<li>
									<aui:a cssClass="<%= cssClass %>" href="<%= curConfigurationEntry.getEditURL(renderRequest, renderResponse) %>"><%= curConfigurationEntry.getName() %></aui:a>
								</li>

							<%
							}
							%>

						</ul>
					</div>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</nav>