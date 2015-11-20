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
ConfigurationModelIterator configurationModelIterator = (ConfigurationModelIterator)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR);
ConfigurationModel factoryConfigurationModel = (ConfigurationModel)request.getAttribute(ConfigurationAdminWebKeys.FACTORY_CONFIGURATION_MODEL);

String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(factoryConfigurationModel.getName());
%>

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
				<portlet:param name="mvcRenderCommandName" value="/edit-configuration" />
				<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
				<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text name="entry">
				<aui:a href="<%= editURL %>"><%= configurationModel.getName() %></aui:a>

				<br />

				<%= configurationModel.getID() %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				align="center"
				name="status"
			>
				<c:choose>
					<c:when test="<%= configurationModel.getConfiguration() != null %>">
						<liferay-ui:icon
							cssClass="icon-check"
							message="active"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:icon
							cssClass="icon-check-empty"
							message="not-active"
						/>
					</c:otherwise>
				</c:choose>
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
							<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
							<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
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