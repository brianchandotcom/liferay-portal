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
String tabs1 = ParamUtil.getString(request, "tabs1");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

PortalUtil.addPortletBreadcrumbEntry(request, PortalUtil.getPortletTitle(renderResponse), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "configuration"), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, tabs1), currentURL);
%>

<aui:nav-bar cssClass="navbar-collapse-absolute" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= selPortlet.getConfigurationActionInstance() != null %>">
			<portlet:renderURL var="configurationURL">
				<portlet:param name="mvcPath" value="/edit_configuration.jsp" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="returnToFullPageURL" value="<%= returnToFullPageURL %>" />
				<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="portletResource" value="<%= portletResource %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= configurationURL %>" label="setup" selected='<%= tabs1.equals("setup") %>' />
		</c:if>

		<c:if test="<%= selPortlet.hasMultipleMimeTypes() %>">
			<portlet:renderURL var="supportedClientsURL">
				<portlet:param name="mvcPath" value="/edit_supported_clients.jsp" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="returnToFullPageURL" value="<%= returnToFullPageURL %>" />
				<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="portletResource" value="<%= portletResource %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= supportedClientsURL %>" label="supported-clients" selected='<%= tabs1.equals("supported-clients") %>' />
		</c:if>

		<c:if test="<%= !selPortlet.getPublicRenderParameters().isEmpty() %>">
			<portlet:renderURL var="publicRenderParametersURL">
				<portlet:param name="mvcPath" value="/edit_public_render_parameters.jsp" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="returnToFullPageURL" value="<%= returnToFullPageURL %>" />
				<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="portletResource" value="<%= portletResource %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= publicRenderParametersURL.toString() %>" label="communication" selected='<%= tabs1.equals("communication") %>' />
		</c:if>

		<portlet:renderURL var="sharingURL">
			<portlet:param name="mvcPath" value="/edit_sharing.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="returnToFullPageURL" value="<%= returnToFullPageURL %>" />
			<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
			<portlet:param name="portletResource" value="<%= portletResource %>" />
		</portlet:renderURL>

		<aui:nav-item href="<%= sharingURL %>" label="sharing" selected='<%= tabs1.equals("sharing") %>' />

		<c:if test="<%= selPortlet.isScopeable() %>">
			<portlet:renderURL var="scopeURL">
				<portlet:param name="mvcPath" value="/edit_scope.jsp" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="returnToFullPageURL" value="<%= returnToFullPageURL %>" />
				<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="portletResource" value="<%= portletResource %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= scopeURL %>" label="scope" selected='<%= tabs1.equals("scope") %>' />
		</c:if>
	</aui:nav>
</aui:nav-bar>