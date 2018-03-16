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
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<li>
			<portlet:renderURL var="redirectURL" />

			<portlet:renderURL var="searchURL">
				<portlet:param name="mvcRenderCommandName" value="/search" />
				<portlet:param name="redirect" value="<%= redirectURL %>" />
			</portlet:renderURL>

			<aui:form action="<%= searchURL %>" name="searchFm">
				<liferay-ui:input-search autoFocus="<%= true %>" markupView="lexicon" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid container-fluid-max-xl container-view">
	<ul class="list-group">

		<%
		for (ConfigurationCategorySectionDisplay configurationCategorySectionDisplay : configurationCategorySectionDisplays) {
		%>

			<li class="list-group-header">
				<h3 class="list-group-header-title text-uppercase">
					<liferay-ui:message key='<%= "category-section." + configurationCategorySectionDisplay.getConfigurationCategorySection() %>' />
				</h3>
			</li>
			<li class="list-group-card">
				<ul class="list-group">

					<%
					for (ConfigurationCategoryDisplay configurationCategoryDisplay : configurationCategorySectionDisplay.getConfigurationCategoryDisplays()) {
					%>

						<portlet:renderURL var="categoryURL">
							<portlet:param name="mvcRenderCommandName" value="/view_category" />
							<portlet:param name="configurationCategory" value="<%= configurationCategoryDisplay.getCategoryKey() %>" />
						</portlet:renderURL>

						<li class="list-group-card-item">
							<a href="<%= categoryURL %>">
								<clay:icon elementClasses="user-icon-sm" symbol="<%= configurationCategoryDisplay.getCategoryIcon() %>" />

								<span class="list-group-card-item-text">
									<liferay-ui:message key='<%= "category." + configurationCategoryDisplay.getCategoryKey() %>' />
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