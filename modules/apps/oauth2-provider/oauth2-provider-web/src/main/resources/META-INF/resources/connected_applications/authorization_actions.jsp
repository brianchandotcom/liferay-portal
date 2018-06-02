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

<%@ include file="/connected_applications/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

OAuth2Authorization oAuth2Authorization = (OAuth2Authorization)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="viewURL">
		<portlet:param name="mvcRenderCommandName" value="/connected_applications/view" />
		<portlet:param name="oAuth2ApplicationId" value="<%= String.valueOf(oAuth2Authorization.getOAuth2ApplicationId()) %>" />
		<portlet:param name="oAuth2AuthorizationId" value="<%= String.valueOf(oAuth2Authorization.getOAuth2AuthorizationId()) %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view"
		url="<%= viewURL.toString() %>"
	/>

	<portlet:actionURL name="/connected_applications/revoke_oauth2_authorizations" var="revokeOAuth2AuthorizationURL">
		<portlet:param name="mvcRenderCommandName" value="/connected_applications/view" />
		<portlet:param name="oAuth2AuthorizationIds" value="<%= String.valueOf(oAuth2Authorization.getOAuth2AuthorizationId()) %>" />
		<portlet:param name="backURL" value="<%= currentURL %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		message="remove-access"
		url="<%= revokeOAuth2AuthorizationURL.toString() %>"
	/>
</liferay-ui:icon-menu>