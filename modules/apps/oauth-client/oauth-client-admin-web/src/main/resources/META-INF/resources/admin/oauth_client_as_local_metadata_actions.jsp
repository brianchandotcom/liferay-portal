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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

OAuthClientASLocalMetadata oAuthClientASLocalMetadata = (OAuthClientASLocalMetadata)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_oauth_client_as_local_metadata" />
		<portlet:param name="localWellKnownURI" value="<%= oAuthClientASLocalMetadata.getLocalWellKnownURI() %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL.toString() %>"
	/>

	<portlet:actionURL name="/oauth_client_admin/delete_oauth_client_as_local_metadata" var="deleteURL">
		<portlet:param name="localWellKnownURI" value="<%= oAuthClientASLocalMetadata.getLocalWellKnownURI() %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		message="delete"
		url="<%= deleteURL.toString() %>"
	/>
</liferay-ui:icon-menu>