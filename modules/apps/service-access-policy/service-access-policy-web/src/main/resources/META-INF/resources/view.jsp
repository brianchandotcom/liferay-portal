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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="policies" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<%
	PortletURL iteratorURL = renderResponse.createRenderURL();

	iteratorURL.setParameter("displayStyle", displayStyle);
	%>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= iteratorURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-service-access-policies"
		iteratorURL="<%= portletURL %>"
		total="<%= SAPEntryServiceUtil.getCompanySAPEntriesCount(company.getCompanyId()) %>"
	>
		<liferay-ui:search-container-results
			results="<%= SAPEntryServiceUtil.getCompanySAPEntries(company.getCompanyId(), searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.service.access.policy.model.SAPEntry"
			escapedModel="<%= true %>"
			keyProperty="sapEntryId"
			modelVar="sapEntry"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/edit_entry.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="sapEntryId" value="<%= String.valueOf(sapEntry.getSapEntryId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
			>
				<strong><%= sapEntry.getName() %></strong>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="title"
				value="<%= sapEntry.getTitle(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				name="enabled"
				value='<%= LanguageUtil.get(request, sapEntry.isEnabled() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-text
				name="default"
				value='<%= LanguageUtil.get(request, sapEntry.isDefaultSAPEntry() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				path="/entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, SAPConstants.SERVICE_NAME, SAPActionKeys.ACTION_ADD_SAP_ENTRY) %>">
	<portlet:renderURL var="addSAPEntryURL">
		<portlet:param name="mvcPath" value="/edit_entry.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addSAPEntryURL %>" />
	</liferay-frontend:add-menu>
</c:if>