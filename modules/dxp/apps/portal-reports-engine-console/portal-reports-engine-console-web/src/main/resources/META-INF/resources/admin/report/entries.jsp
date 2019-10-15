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