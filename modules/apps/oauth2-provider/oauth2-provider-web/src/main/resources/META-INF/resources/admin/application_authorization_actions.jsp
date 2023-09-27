<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "revokeOAuth2Authorization(" + oAuth2Authorization.getOAuth2AuthorizationId() + ")" %>' value="revoke" />
</c:if>