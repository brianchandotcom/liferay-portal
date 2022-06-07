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
int oAuthClientASLocalMetadataCount = OAuthClientASLocalMetadataLocalServiceUtil.getOAuthClientASLocalMetadatasCount();

OAuthClientASLocalMetadataManagementToolbarDisplayContext oAuthClientASLocalMetadataManagementToolbarDisplayContext = new OAuthClientASLocalMetadataManagementToolbarDisplayContext(currentURLObj, liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	actionDropdownItems="<%= oAuthClientASLocalMetadataManagementToolbarDisplayContext.getActionDropdownItems() %>"
	additionalProps="<%= oAuthClientASLocalMetadataManagementToolbarDisplayContext.getAdditionalProps() %>"
	creationMenu="<%= oAuthClientASLocalMetadataManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= oAuthClientASLocalMetadataCount == 0 %>"
	filterDropdownItems="<%= oAuthClientASLocalMetadataManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= oAuthClientASLocalMetadataCount %>"
	searchContainerId="oAuthClientASLocalMetadataSearchContainer"
	selectable="<%= true %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= false %>"
	sortingOrder="<%= oAuthClientASLocalMetadataManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(oAuthClientASLocalMetadataManagementToolbarDisplayContext.getSortingURL()) %>"
	viewTypeItems="<%= oAuthClientASLocalMetadataManagementToolbarDisplayContext.getViewTypes() %>"
/>

<clay:container-fluid
	cssClass="closed"
>
	<liferay-ui:search-container
		emptyResultsMessage="no-oauth-client-as-local-metadata-were-found"
		id="oAuthClientASLocalMetadataSearchContainer"
		iteratorURL="<%= currentURLObj %>"
		rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
		total="<%= oAuthClientASLocalMetadataCount %>"
	>
		<liferay-ui:search-container-results
			results="<%= OAuthClientASLocalMetadataServiceUtil.getCompanyOAuthClientASLocalMetadata(themeDisplay.getCompanyId(), searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata"
			escapedModel="<%= true %>"
			keyProperty="localWellKnownURI"
			modelVar="oAuthClientASLocalMetadata"
		>
			<portlet:renderURL var="editURL">
				<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_oauth_client_as_local_metadata" />
				<portlet:param name="localWellKnownURI" value="<%= oAuthClientASLocalMetadata.getLocalWellKnownURI() %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= editURL %>"
				name="oauth-client-as-local-well-known-uri"
				property="localWellKnownURI"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/admin/oauth_client_as_local_metadata_actions.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>