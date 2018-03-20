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
EntrySearch entrySearch = reportsEngineDisplayContext.getEntrySearch();
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="mvcPath" value="/admin/view.jsp" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />

	<liferay-ui:search-container
		searchContainer="<%= entrySearch %>"
	>
		<liferay-ui:search-container-results>
			<%@ include file="/admin/report/entry_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.reports.engine.console.model.Entry"
			keyProperty="entryId"
			modelVar="entry"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="mvcPath" value="/admin/report/requested_report_detail.jsp" />
				<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
			</liferay-portlet:renderURL>

			<%
			Definition definition = DefinitionLocalServiceUtil.fetchDefinition(entry.getDefinitionId());
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="definition-name"
				value="<%= (definition == null) ? StringPool.BLANK : HtmlUtil.escape(definition.getName(locale)) %>"
			/>

			<%
			User user2 = UserLocalServiceUtil.fetchUserById(entry.getUserId());
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="source-name"
				value="<%= (user2 == null) ? StringPool.BLANK : user2.getScreenName() %>"
			/>

			<liferay-ui:search-container-column-date
				href="<%= rowURL %>"
				name="report-generation-date"
				value="<%= entry.getCreateDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/admin/report/requested_report_actions.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= reportsEngineDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "reports"), currentURL);
%>