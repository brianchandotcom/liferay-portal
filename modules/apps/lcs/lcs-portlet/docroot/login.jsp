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
Token requestToken = OAuthUtil.getRequestToken(liferayPortletRequest);

portletSession.setAttribute(Token.class.getName(), requestToken);
%>

<h3><liferay-ui:message arguments="2" key="registration-step-x-3" /> - <liferay-ui:message key="authorize-access" /></h3>

<div class="alert alert-info lcs-alert">
	<liferay-ui:message key="please-authorize-liferay-connected-services-to-use-the-services-you-have-enabled" />
</div>

<portlet:actionURL name="setupOAuth" var="setupOAuthURL" />

<liferay-ui:error key="oAuthConsumerKeyRefused" message="provided-oauth-consumer-key-refused" />
<liferay-ui:error key="oAuthGeneralError" message="oauth-authorization-initialization-failed" />
<liferay-ui:error key="oAuthTimestampRefused" message="provided-oauth-timestamp-out-of-allowed-timeframe-synchronized" />

<c:if test="<%= requestToken != null %>">
	<aui:button-row>
		<liferay-portlet:renderURL var="configureLCSServicesURL">
			<liferay-portlet:param name="configureLCSServices" value="true" />
		</liferay-portlet:renderURL>

		<aui:button href="<%= configureLCSServicesURL.toString() %>" name="back" value="back" />

		<aui:button cssClass="btn-success" name="authorizeAccess" onClick='<%= renderResponse.getNamespace() + "loadAuthorizeAccess();" %>' value="authorize-access" />
	</aui:button-row>

	<aui:script>
		function <portlet:namespace />loadAuthorizeAccess() {
			window.location.href = '<%= OAuthUtil.getAuthorizeURL(setupOAuthURL, requestToken) %>';
		}
	</aui:script>
</c:if>