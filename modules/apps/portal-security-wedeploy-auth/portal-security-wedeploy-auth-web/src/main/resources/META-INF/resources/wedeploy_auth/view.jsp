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
String redirectURI = ParamUtil.getString(request, "redirectURI");

String clientId = ParamUtil.getString(request, "clientId");

WeDeployAuthApp weDeployAuthApp = WeDeployAuthAppLocalServiceUtil.fetchWeDeployAuthApp(redirectURI, clientId);
%>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test="<%= weDeployAuthApp == null %>">
			<div class="alert alert-info">
				<liferay-ui:message key="no-wedeploy-apps-were-found" />
			</div>
		</c:when>
		<c:otherwise>
			<p>
				<liferay-ui:message arguments="<%= weDeployAuthApp.getName() %>" key="x-would-like-to-view-the-following-information" />
			</p>

			<ul>
				<li>
					<liferay-ui:message key="full-name" />
				</li>
				<li>
					<liferay-ui:message key="email-address" />
				</li>
			</ul>

			<div class="button-holder">
				<portlet:actionURL name="/wedeploy_auth/authorize_user" var="allowAuthorizeUserURL">
					<portlet:param name="<%= Constants.CMD %>" value="allow" />
					<portlet:param name="redirectURI" value="<%= redirectURI %>" />
					<portlet:param name="clientId" value="<%= clientId %>" />
				</portlet:actionURL>

				<aui:button cssClass="btn-primary" href="<%= allowAuthorizeUserURL %>" value='<%= LanguageUtil.get(request, "allow") %>' />

				<portlet:actionURL name="/wedeploy_auth/authorize_user" var="denyAuthorizeUserURL">
					<portlet:param name="<%= Constants.CMD %>" value="deny" />
					<portlet:param name="redirectURI" value="<%= redirectURI %>" />
					<portlet:param name="clientId" value="<%= clientId %>" />
				</portlet:actionURL>

				<aui:button cssClass="btn btn-danger" href="<%= denyAuthorizeUserURL %>" value='<%= LanguageUtil.get(request, "deny") %>' />
			</div>
		</c:otherwise>
	</c:choose>
</div>