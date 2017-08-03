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
String lcsPage = ParamUtil.getString(request, "lcsPage", "connection");

boolean lcsClusterNodeRegistered = false;
boolean lcsPortletAuthorized = false;

if (LCSUtil.isLCSPortletAuthorized(liferayPortletRequest)) {
	lcsClusterNodeRegistered = LCSUtil.isLCSClusterNodeRegistered(liferayPortletRequest);

	if (!SessionErrors.contains(liferayPortletRequest, "oAuthTokenRejected")) {
		lcsPortletAuthorized = true;
	}
}
%>

<liferay-ui:error key="generalPluginAccess" message="an-error-occurred-while-accessing-liferay-connected-services" />
<liferay-ui:error key="keyStoreAccess" message="unable-to-access-keystore" />
<liferay-ui:error key="lcsInsufficientPrivileges" message="please-provide-user-credentials-with-the-appropriate-lcs-role" />
<liferay-ui:error key="oAuthAuthorizationFailed" message="oauth-authorization-failed" />
<liferay-ui:error key="oAuthTokenExpired" message="provided-oauth-token-expired" />
<liferay-ui:error key="oAuthTokenRejected" message="provided-oauth-token-rejected" />
<liferay-ui:error key="serverIdFileSystemAccess" message="unable-generate-server-id" />

<section class="content">
	<c:choose>
		<c:when test="<%= !lcsPortletAuthorized || !lcsClusterNodeRegistered %>">
			<div class="container-fluid-1280">
				<%@ include file="/info.jspf" %>
			</div>
		</c:when>
		<c:otherwise>
			<aui:nav-bar markupView="lexicon">
				<aui:nav cssClass="navbar-nav">
					<liferay-portlet:renderURL var="connectionURL">
						<liferay-portlet:param name="lcsPage" value="connection" />
					</liferay-portlet:renderURL>

					<aui:nav-item href="<%= connectionURL %>" label="connection" selected='<%= lcsPage.equals("connection") %>' />

					<liferay-portlet:renderURL var="infoURL">
						<liferay-portlet:param name="lcsPage" value="info" />
					</liferay-portlet:renderURL>

					<aui:nav-item href="<%= infoURL %>" label="info" selected='<%= lcsPage.equals("info") %>' />
				</aui:nav>
			</aui:nav-bar>

			<div class="container-fluid-1280">
				<c:choose>
					<c:when test='<%= lcsPage.equals("connection") %>'>
						<%@ include file="/connection.jspf" %>
					</c:when>
					<c:when test='<%= lcsPage.equals("info") %>'>
						<%@ include file="/info.jspf" %>
					</c:when>
				</c:choose>
			</div>
		</c:otherwise>
	</c:choose>

	<%
	Set<LCSAlert> lcsClusterEntryTokenAlerts = LCSUtil.getLCSClusterEntryTokenAlerts();
	%>

	<c:if test="<%= !lcsClusterEntryTokenAlerts.isEmpty() %>">

		<%
		for (LCSAlert lcsAlert : lcsClusterEntryTokenAlerts) {
		%>

			<div class="<%= lcsAlert.getCSSClass() %>">
				<liferay-ui:message key="<%= lcsAlert.getLabel() %>" />
			</div>

		<%
		}
		%>

	</c:if>
</section>

<footer class="footer">
	<div class="container-fluid-1280">
		<div class="footer-note">
			<liferay-ui:message arguments="<%= PortletPropsValues.LCS_CLIENT_VERSION %>" key="liferay-connected-services-client-x" />
		</div>

		<div class="footer-note">
			<liferay-ui:message arguments="<%= PortletPropsValues.LRDCOM_SUPPORT_URL %>" key="if-you-have-a-liferay-enterprise-subscription-and-you-have-questions-or-issues-please-open-a-ticket-in-lesa-under-the-liferay-connected-services-component" />
		</div>

		<div class="footer-note">
			<liferay-ui:message arguments="<%= PortletPropsValues.LRDCOM_SALES_EMAIL_ADDRESS %>" key="if-you-do-not-have-an-active-enterprise-subscription-please-contact-your-account-executive-or-x" />
		</div>
	</div>
</footer>