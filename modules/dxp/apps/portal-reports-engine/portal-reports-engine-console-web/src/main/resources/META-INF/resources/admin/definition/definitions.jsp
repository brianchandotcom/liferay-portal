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
PortletURL portletURL = reportsEngineDisplayContext.getPortletURL();

portletURL.setParameter("mvcPath", "/admin/view.jsp");

DefinitionSearch definitionSearch = reportsEngineDisplayContext.getDefinitionSearch();
%>

<aui:form action="<%= portletURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<liferay-ui:search-container
		searchContainer="<%= definitionSearch %>"
	>
		<liferay-ui:search-container-results>
			<%@ include file="/admin/definition/definition_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.reports.engine.console.model.Definition"
			keyProperty="definitionId"
			modelVar="definition"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="mvcPath" value="/admin/definition/edit_definition.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="definitionId" value="<%= String.valueOf(definition.getDefinitionId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="definition-name"
				value="<%= HtmlUtil.escape(definition.getName(locale)) %>"
			/>

			<%
			Source source = SourceLocalServiceUtil.fetchSource(definition.getSourceId());
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="source-name"
				value="<%= (source == null) ? ReportDataSourceType.PORTAL.getValue() : HtmlUtil.escape(source.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-date
				href="<%= rowURL %>"
				name="create-date"
				value="<%= definition.getCreateDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/admin/definition/definition_actions.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= reportsEngineDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:renderURL var="addDefinitionURL">
	<portlet:param name="mvcPath" value="/admin/definition/edit_definition.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item
		title='<%= LanguageUtil.get(request, "add") %>'
		url="<%= addDefinitionURL.toString() %>"
	/>
</liferay-frontend:add-menu>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "definitions"), currentURL);
%>