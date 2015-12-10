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

ConfigurationModelIterator configurationModelIterator = (ConfigurationModelIterator)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR);
ConfigurationModel factoryConfigurationModel = (ConfigurationModel)request.getAttribute(ConfigurationAdminWebKeys.FACTORY_CONFIGURATION_MODEL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(factoryConfigurationModel.getName());
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			label="entries"
			selected="<%= true %>"
		/>
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:add-menu>
	<portlet:renderURL var="createFactoryConfigURL">
		<portlet:param name="mvcRenderCommandName" value="/edit_configuration" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="factoryPid" value="<%= factoryConfigurationModel.getID() %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu-item
		title='<%= LanguageUtil.format(request, "add-x", factoryConfigurationModel.getName()) %>'
		url="<%= createFactoryConfigURL %>"
	/>
</liferay-frontend:add-menu>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="no-configurations-were-found"
		total="<%= configurationModelIterator.getTotal() %>"
	>
		<liferay-ui:search-container-results
			results="<%= configurationModelIterator.getResults(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.configuration.admin.web.model.ConfigurationModel"
			keyProperty="ID"
			modelVar="configurationModel"
		>
			<portlet:renderURL var="editURL">
				<portlet:param name="mvcRenderCommandName" value="/edit_configuration" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
				<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text name="entry">
				<aui:a href="<%= editURL %>"><b><%= configurationModel.getLabel() %></b></aui:a>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				align="right"
				cssClass="entry-action"
				name=""
			>
				<liferay-ui:icon-menu
					direction="down"
					markupView="lexicon"
					showWhenSingleIcon="<%= true %>"
				>
					<liferay-ui:icon
						message="edit"
						method="post"
						url="<%= editURL %>"
					/>

					<c:if test="<%= configurationModel.getConfiguration() != null %>">
						<portlet:actionURL name="deleteConfiguration" var="deleteConfigActionURL">
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
							<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
						</portlet:actionURL>

						<liferay-ui:icon
							message="delete"
							method="post"
							url="<%= deleteConfigActionURL %>"
						/>
					</c:if>
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>