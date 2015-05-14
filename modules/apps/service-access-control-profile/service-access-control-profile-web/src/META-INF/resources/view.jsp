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

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-service-access-control-profiles"
	headerNames="name"
	iteratorURL="<%= portletURL %>"
	total="<%= SACPEntryServiceUtil.getCompanySACPEntriesCount(company.getCompanyId()) %>"
>
	<liferay-ui:search-container-results
		results="<%= SACPEntryServiceUtil.getCompanySACPEntries(company.getCompanyId(), searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.service.access.control.profile.model.SACPEntry"
		escapedModel="<%= true %>"
		keyProperty="sacpEntryId"
		modelVar="sacpEntry"
	>
		<portlet:renderURL var="rowURL">
			<portlet:param name="mvcPath" value="/edit_entry.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sacpEntryId" value="<%= String.valueOf(sacpEntry.getSacpEntryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="title"
			value="<%= sacpEntry.getTitle(locale) %>"

		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/entry_action.jsp"
			valign="top"
		/>
	</liferay-ui:search-container-row>

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, SACPConstants.PORTLET_ID, SACPConstants.ACTION_ADD_SACP_ENTRY) %>">
		<portlet:renderURL var="addSACPEntryURL">
			<portlet:param name="mvcPath" value="/edit_entry.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<aui:button-row>
			<aui:button href="<%= addSACPEntryURL %>" value="add" />
		</aui:button-row>
	</c:if>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>