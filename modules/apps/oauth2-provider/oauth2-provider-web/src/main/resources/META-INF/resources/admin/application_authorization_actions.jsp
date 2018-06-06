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

<%@ include file="/admin/init.jsp" %>

<%
OAuth2Application oAuth2Application = oAuth2AdminPortletDisplayContext.getOAuth2Application();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

OAuth2Authorization oAuth2Authorization = (OAuth2Authorization)row.getObject();
%>

<c:if test="<%= oAuth2AdminPortletDisplayContext.hasRevokeTokenPermission(oAuth2Application) %>">
	<portlet:actionURL name="revokeOAuth2Authorization" var="revokeOAuth2AuthorizationURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="oAuth2AuthorizationId" value="<%= String.valueOf(oAuth2Authorization.getOAuth2AuthorizationId()) %>" />
	</portlet:actionURL>

	<aui:button onClick='<%= renderResponse.getNamespace() + "revokeOAuth2Authorization(" + oAuth2Authorization.getOAuth2AuthorizationId() + ")" %>' value="revoke" />
</c:if>