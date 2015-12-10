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
PortletURL portletURL = renderResponse.createRenderURL();
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="policies" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-service-access-policies"
	headerNames="name"
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
			property="name"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="title"
			value="<%= sapEntry.getTitle(locale) %>"
		/>

		<liferay-ui:search-container-column-text name="enabled">
			<liferay-ui:icon cssClass='<%= sapEntry.isEnabled() ? "icon-check" : "icon-check-empty" %>' />
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text name="default">
			<liferay-ui:icon cssClass='<%= sapEntry.isDefaultSAPEntry() ? "icon-check" : "icon-check-empty" %>' />
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/entry_action.jsp"
			valign="top"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, SAPConstants.SERVICE_NAME, SAPActionKeys.ACTION_ADD_SAP_ENTRY) %>">
	<portlet:renderURL var="addSAPEntryURL">
		<portlet:param name="mvcPath" value="/edit_entry.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addSAPEntryURL %>" />
	</liferay-frontend:add-menu>
</c:if>