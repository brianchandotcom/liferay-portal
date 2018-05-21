<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
PortletURL portletURL = reportsEngineDisplayContext.getPortletURL();

portletURL.setParameter("mvcPath", "/admin/view.jsp");

SourceSearch sourceSearch = reportsEngineDisplayContext.getSourceSearch();
%>

<aui:form action="<%= portletURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />

	<liferay-ui:search-container
		searchContainer="<%= sourceSearch %>"
	>
		<liferay-ui:search-container-results>
			<%@ include file="/admin/data_source/source_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.reports.engine.console.model.Source"
			keyProperty="sourceId"
			modelVar="source"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="mvcPath" value="/admin/data_source/edit_data_source.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="sourceId" value="<%= String.valueOf(source.getSourceId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="source-name"
				value="<%= HtmlUtil.escape(source.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="jdbc-url"
				property="driverUrl"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="jdbc-user-name"
				property="driverUserName"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/admin/data_source/data_source_actions.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= reportsEngineDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:renderURL var="addSourceURL">
	<portlet:param name="mvcPath" value="/admin/data_source/edit_data_source.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item
		title='<%= LanguageUtil.get(request, "add") %>'
		url="<%= addSourceURL.toString() %>"
	/>
</liferay-frontend:add-menu>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "sources"), currentURL);
%>