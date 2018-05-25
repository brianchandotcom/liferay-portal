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
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

OAuth2Application oAuth2Application = oAuth2AdminPortletDisplayContext.getOAuth2Application();

long oAuth2ApplicationId = 0;

String headerTitle = LanguageUtil.get(request, "add-o-auth2-application");

if (oAuth2Application != null) {
	oAuth2ApplicationId = oAuth2Application.getOAuth2ApplicationId();
	headerTitle = oAuth2Application.getName();
}

renderResponse.setTitle(headerTitle);
String currentAppTab = ParamUtil.getString(request, "appTab", "credentials");
%>

<portlet:renderURL var="editURL">
	<portlet:param name="appTab" value="credentials" />
	<portlet:param name="mvcRenderCommandName" value="/admin/update_oauth2_application" />
	<portlet:param name="oAuth2ApplicationId" value="<%= String.valueOf(oAuth2ApplicationId) %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:renderURL>

<portlet:renderURL var="assignScopesURL">
	<portlet:param name="appTab" value="scopes" />
	<portlet:param name="mvcRenderCommandName" value="/admin/assign_scopes" />
	<portlet:param name="oAuth2ApplicationId" value="<%= String.valueOf(oAuth2ApplicationId) %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:renderURL>

<portlet:renderURL var="applicationAuthorizationsURL">
	<portlet:param name="appTab" value="authorizations" />
	<portlet:param name="mvcRenderCommandName" value="/admin/view_oauth2_authorizations" />
	<portlet:param name="oAuth2ApplicationId" value="<%= String.valueOf(oAuth2ApplicationId) %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:renderURL>

<nav class="navbar navbar-collapse-absolute navbar-expand-md navbar-underline navigation-bar navigation-bar-secondary">
	<div class="container">
		<a aria-controls="navigationBarCollapse00" aria-expanded="false" aria-label="Toggle Navigation" class="collapsed navbar-toggler navbar-toggler-link" data-toggle="collapse" href="#navigationBarCollapse00" role="button">
			<span class="navbar-text-truncate">Credentials</span>
			<svg aria-hidden="true" class="lexicon-icon lexicon-icon-caret-bottom">
				<use xlink:href="/vendor/lexicon/icons.svg#caret-bottom" />
			</svg>
		</a>

		<div class="collapse navbar-collapse" id="navigationBarCollapse00">
			<div class="container">
				<ul class="navbar-nav">
					<c:choose>
						<c:when test='<%= currentAppTab.equals("credentials") %>'>
							<li aria-label="Current Page" class="nav-item">
								<a class="active nav-link" href="<%= editURL %>">
									<span class="navbar-text-truncate"><liferay-ui:message key="credentials" /></span>
								</a>
							</li>
						</c:when>
						<c:otherwise>
							<li aria-label="Current Page" class="nav-item">
								<a class="nav-link" href="<%= editURL %>">
									<span class="navbar-text-truncate"><liferay-ui:message key="credentials" /></span>
								</a>
							</li>
						</c:otherwise>
					</c:choose>

					<c:if test="<%= oAuth2Application != null %>">
						<c:choose>
							<c:when test='<%= currentAppTab.equals("scopes") %>'>
								<li aria-label="Current Page" class="nav-item">
									<a class="active nav-link" href="<%= assignScopesURL %>">
										<span class="navbar-text-truncate"><liferay-ui:message key="scopes" /></span>
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li class="nav-item">
									<a class="nav-link" href="<%= assignScopesURL %>">
										<span class="navbar-text-truncate"><liferay-ui:message key="scopes" /></span>
									</a>
								</li>
							</c:otherwise>
						</c:choose>

						<c:if test="<%= oAuth2AdminPortletDisplayContext.hasViewGrantedAuthorizationsPermission() %>">
							<c:choose>
								<c:when test='<%= currentAppTab.equals("authorizations") %>'>
									<li aria-label="Current Page" class="nav-item">
										<a class="active nav-link" href="<%= applicationAuthorizationsURL %>">
											<span class="navbar-text-truncate"><liferay-ui:message key="authorizations" /></span>
										</a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="nav-item">
										<a class="nav-link" href="<%= applicationAuthorizationsURL %>">
											<span class="navbar-text-truncate"><liferay-ui:message key="authorizations" /></span>
										</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</nav>

<c:choose>
	<c:when test='<%= currentAppTab.equals("credentials") %>'>
		<%@ include file="/admin/edit_application_credentials.jspf" %>
	</c:when>
	<c:when test='<%= (oAuth2Application != null) && currentAppTab.equals("scopes") %>'>
		<%@ include file="/admin/assign_scopes.jspf" %>
	</c:when>
	<c:when test='<%= (oAuth2Application != null) && currentAppTab.equals("authorizations") && oAuth2AdminPortletDisplayContext.hasViewGrantedAuthorizationsPermission() %>'>
		<%@ include file="/admin/application_authorizations.jspf" %>
	</c:when>
</c:choose>