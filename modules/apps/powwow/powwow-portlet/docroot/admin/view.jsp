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

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-servers"
	iteratorURL="<%= portletURL %>"
	total="<%= PowwowServerLocalServiceUtil.getPowwowServersCount() %>"
>
	<liferay-ui:search-container-results
		results="<%= PowwowServerLocalServiceUtil.getPowwowServers(searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<aui:button-row>
		<portlet:renderURL var="addURL">
			<portlet:param name="mvcPath" value="/admin/edit_server.jsp" />
			<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
		</portlet:renderURL>

		<aui:button onClick="<%= addURL.toString() %>" value="add-server" />
	</aui:button-row>

	<liferay-ui:search-container-row
		className="com.liferay.powwow.model.PowwowServer"
		escapedModel="<%= true %>"
		keyProperty="powwowServerId"
		modelVar="powwowServer"
	>
		<portlet:renderURL var="rowURL">
			<portlet:param name="mvcPath" value="/admin/edit_server.jsp" />
			<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<portlet:param name="powwowServerId" value="<%= String.valueOf(powwowServer.getPowwowServerId()) %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="provider"
			value="<%= PowwowServiceProviderUtil.getPowwowServiceProviderName(powwowServer.getProviderType()) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="branding-name"
			value="<%= LanguageUtil.get(request, PowwowServiceProviderUtil.getBrandingLabel(powwowServer.getProviderType())) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="active"
			property="active"
		/>

		<liferay-ui:search-container-column-jsp path="/admin/server_action.jsp" />
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>