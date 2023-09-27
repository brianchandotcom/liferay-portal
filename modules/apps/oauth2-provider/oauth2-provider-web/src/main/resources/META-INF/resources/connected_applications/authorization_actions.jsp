<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<portlet:param name="mvcRenderCommandName" value="/oauth2_provider/view_connected_applications" />
		<portlet:param name="oAuth2ApplicationId" value="<%= String.valueOf(oAuth2Authorization.getOAuth2ApplicationId()) %>" />
		<portlet:param name="oAuth2AuthorizationId" value="<%= String.valueOf(oAuth2Authorization.getOAuth2AuthorizationId()) %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view"
		url="<%= viewURL.toString() %>"
	/>

	<portlet:actionURL name="/connected_applications/revoke_oauth2_authorizations" var="revokeOAuth2AuthorizationURL">
		<portlet:param name="mvcRenderCommandName" value="/oauth2_provider/view_connected_applications" />
		<portlet:param name="oAuth2AuthorizationIds" value="<%= String.valueOf(oAuth2Authorization.getOAuth2AuthorizationId()) %>" />
		<portlet:param name="backURL" value="<%= currentURL %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		message="remove-access"
		url="<%= revokeOAuth2AuthorizationURL.toString() %>"
	/>
</liferay-ui:icon-menu>