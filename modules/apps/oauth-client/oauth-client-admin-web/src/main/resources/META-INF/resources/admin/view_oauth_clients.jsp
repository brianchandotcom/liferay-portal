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
int oAuthClientEntriesCount = OAuthClientEntryLocalServiceUtil.getOAuthClientEntriesCount();

OAuthClientsManagementToolbarDisplayContext oAuthClientsManagementToolbarDisplayContext = new OAuthClientsManagementToolbarDisplayContext(currentURLObj, liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	actionDropdownItems="<%= oAuthClientsManagementToolbarDisplayContext.getActionDropdownItems() %>"
	additionalProps="<%= oAuthClientsManagementToolbarDisplayContext.getAdditionalProps() %>"
	creationMenu="<%= oAuthClientsManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= oAuthClientEntriesCount == 0 %>"
	filterDropdownItems="<%= oAuthClientsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= oAuthClientEntriesCount %>"
	searchContainerId="oAuthClientEntrySearchContainer"
	selectable="<%= true %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= false %>"
	sortingOrder="<%= oAuthClientsManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(oAuthClientsManagementToolbarDisplayContext.getSortingURL()) %>"
	viewTypeItems="<%= oAuthClientsManagementToolbarDisplayContext.getViewTypes() %>"
/>

<clay:container-fluid
	cssClass="closed"
>
	<liferay-ui:search-container
		emptyResultsMessage="no-oauth-clients-were-found"
		id="oAuthClientEntrySearchContainer"
		iteratorURL="<%= currentURLObj %>"
		rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
		total="<%= oAuthClientEntriesCount %>"
	>
		<liferay-ui:search-container-results
			results="<%= OAuthClientEntryServiceUtil.getCompanyOAuthClientEntries(themeDisplay.getCompanyId()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.oauth.client.persistence.model.OAuthClientEntry"
			escapedModel="<%= true %>"
			keyProperty="clientId"
			modelVar="oAuthClientEntry"
		>
			<portlet:renderURL var="editURL">
				<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_o_auth_client" />
				<portlet:param name="authServerWellKnownURI" value="<%= oAuthClientEntry.getAuthServerWellKnownURI() %>" />
				<portlet:param name="clientId" value="<%= oAuthClientEntry.getClientId() %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= editURL %>"
				name="oauth-client-id"
				property="clientId"
			/>

			<liferay-ui:search-container-column-text
				name="oauth-client-as-well-known-uri"
				property="authServerWellKnownURI"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/admin/oauth_client_actions.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>