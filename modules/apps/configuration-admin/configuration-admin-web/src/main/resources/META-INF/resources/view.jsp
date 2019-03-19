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
List<ConfigurationCategorySectionDisplay> configurationCategorySectionDisplays = (List<ConfigurationCategorySectionDisplay>)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_CATEGORY_SECTION_DISPLAYS);
ConfigurationEntryRetriever configurationEntryRetriever = (ConfigurationEntryRetriever)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_ENTRY_RETRIEVER);
%>

<portlet:renderURL var="redirectURL" />

<portlet:renderURL var="searchURL">
	<portlet:param name="mvcRenderCommandName" value="/search" />
	<portlet:param name="redirect" value="<%= redirectURL %>" />
</portlet:renderURL>

<clay:management-toolbar
	searchActionURL="<%= searchURL %>"
	selectable="<%= false %>"
	showSearch="<%= true %>"
/>

<div class="container-fluid container-fluid-max-xl container-view">
	<ul class="list-group">

		<%
		for (ConfigurationCategorySectionDisplay configurationCategorySectionDisplay : configurationCategorySectionDisplays) {
		%>

			<li class="list-group-header">
				<h3 class="list-group-header-title text-uppercase">
					<%= HtmlUtil.escape(configurationCategorySectionDisplay.getConfigurationCategorySectionLabel(locale)) %>
				</h3>
			</li>
			<li class="list-group-card">
				<ul class="list-group">

					<%
					ConfigurationScopeDisplayContext configurationScopeDisplayContext = new ConfigurationScopeDisplayContext(renderRequest);

					for (ConfigurationCategoryDisplay configurationCategoryDisplay : configurationCategorySectionDisplay.getConfigurationCategoryDisplays()) {
						ConfigurationCategoryMenuDisplay configurationCategoryMenuDisplay = configurationEntryRetriever.getConfigurationCategoryMenuDisplay(configurationCategoryDisplay.getCategoryKey(), themeDisplay.getLanguageId(), configurationScopeDisplayContext.getScope(), configurationScopeDisplayContext.getScopePK());

						if (configurationCategoryMenuDisplay.isEmpty()) {
							continue;
						}

						String viewCategoryHREF = ConfigurationCategoryUtil.getHREF(configurationCategoryMenuDisplay, liferayPortletResponse, renderRequest, renderResponse);
					%>

						<li class="list-group-card-item">
							<a href="<%= viewCategoryHREF %>">
								<clay:icon
									elementClasses="user-icon-sm"
									symbol="<%= configurationCategoryDisplay.getCategoryIcon() %>"
								/>

								<span class="list-group-card-item-text">
									<%= HtmlUtil.escape(configurationCategoryDisplay.getCategoryLabel(locale)) %>
								</span>
							</a>
						</li>

					<%
					}
					%>

				</ul>
			</li>

		<%
		}
		%>

	</ul>
</div>