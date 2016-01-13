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
String redirect = renderRequest.getParameter("redirect");

List<String> configurationCategories = (List<String>)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_CATEGORIES);
String configurationCategory = (String)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_CATEGORY);
ConfigurationModelIterator configurationModelIterator = (ConfigurationModelIterator)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("configurationCategory", configurationCategory);

String keywords = renderRequest.getParameter("keywords");

if (Validator.isNotNull(keywords)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(LanguageUtil.get(request, "search-results"));
}
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<c:if test="<%= configurationCategories != null %>">
		<aui:nav cssClass="navbar-nav">

			<%
			for (String curConfigurationCategory : configurationCategories) {
			%>

				<portlet:renderURL var="configurationCategoryURL">
					<portlet:param name="configurationCategory" value="<%= curConfigurationCategory %>" />
				</portlet:renderURL>

				<aui:nav-item
					cssClass='<%= curConfigurationCategory.equals(configurationCategory) ? "active" : "" %>'
					href="<%= configurationCategoryURL %>"
					label="<%= curConfigurationCategory %>"
				/>

			<%
			}
			%>

		</aui:nav>
	</c:if>

	<aui:nav-bar-search>
		<portlet:renderURL var="searchURL">
			<portlet:param name="mvcRenderCommandName" value="/search" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<aui:form action="<%= searchURL %>" name="searchFm">
			<liferay-ui:input-search autoFocus="<%= true %>" markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="no-configurations-were-found"
		iteratorURL="<%= portletURL %>"
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

			<portlet:renderURL var="viewFactoryInstancesURL">
				<portlet:param name="mvcRenderCommandName" value="/view_factory_instances" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text name="name">
				<c:choose>
					<c:when test="<%= configurationModel.isFactory() %>">
						<aui:a href="<%= viewFactoryInstancesURL %>"><strong><%= configurationModel.getName() %></strong></aui:a>
					</c:when>
					<c:otherwise>
						<aui:a href="<%= editURL %>"><strong><%= configurationModel.getName() %></strong></aui:a>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text name="scope">
				<c:choose>
					<c:when test="<%= ConfigurationAdmin.Scope.COMPANY.equals(configurationModel.getScope()) %>">
						<liferay-ui:message key="default-settings-for-all-instances" />
					</c:when>
					<c:when test="<%= ConfigurationAdmin.Scope.GROUP.equals(configurationModel.getScope()) %>">
						<liferay-ui:message key="default-configuration-for-all-sites" />
					</c:when>
					<c:when test="<%= ConfigurationAdmin.Scope.PORTLET_INSTANCE.equals(configurationModel.getScope()) %>">
						<liferay-ui:message key="default-configuration-for-application" />
					</c:when>
					<c:when test="<%= ConfigurationAdmin.Scope.SYSTEM.equals(configurationModel.getScope()) %>">
						<liferay-ui:message key="system" />
					</c:when>
					<c:otherwise>
						-
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				align="right"
				cssClass="entry-action"
				name=""
			>
				<liferay-ui:icon-menu
					direction="right"
					markupView="lexicon"
					showWhenSingleIcon="<%= true %>"
				>
					<c:choose>
						<c:when test="<%= configurationModel.isFactory() %>">
							<liferay-ui:icon
								message="view"
								method="post"
								url="<%= viewFactoryInstancesURL %>"
							/>
						</c:when>
						<c:otherwise>
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
									message='<%= configurationModel.isFactory() ? "delete" : "reset-default-values" %>'
									method="post"
									url="<%= deleteConfigActionURL %>"
								/>

								<portlet:resourceURL id="export" var="exportURL">
									<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
									<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
								</portlet:resourceURL>

								<liferay-ui:icon
									message="export"
									method="post"
									url="<%= exportURL %>"
								/>
							</c:if>
						</c:otherwise>
					</c:choose>
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>