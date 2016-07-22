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
PortletURL serverURL = renderResponse.createRenderURL();

serverURL.setParameter("mvcRenderCommandName", "/server_admin/view");
serverURL.setParameter("tabs1", tabs1);
serverURL.setParameter("tabs2", tabs2);
%>

<c:choose>
	<c:when test="<%= windowState.equals(WindowState.NORMAL) %>">
		<html:link page="/server_admin/view?windowState=maximized&portletMode=view&actionURL=0"><liferay-ui:message key="more" /></html:link> &raquo;
	</c:when>
	<c:otherwise>
		<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
			<aui:nav cssClass="navbar-nav">

				<%
				String[] tabs1Names = new String[] {"resources", "log-levels", "properties", "captcha", "data-migration", "file-uploads", "mail", "external-services", "script", "shutdown"};

				for (String tabs1Name : tabs1Names) {
					serverURL.setParameter("tabs1", tabs1Name);
				%>

					<aui:nav-item href="<%= serverURL.toString() %>" label="<%= tabs1Name %>" selected="<%= tabs1.equals(tabs1Name) %>" />

				<%
				}
				%>

			</aui:nav>
		</aui:nav-bar>

		<div class="container-fluid-1280">
			<c:choose>
				<c:when test='<%= tabs1.equals("log-levels") %>'>
					<liferay-util:include page="/log_levels.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("properties") %>'>
					<liferay-util:include page="/properties.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("captcha") %>'>
					<liferay-util:include page="/captcha.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("data-migration") %>'>
					<liferay-util:include page="/data_migration.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("file-uploads") %>'>
					<liferay-util:include page="/file_uploads.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("mail") %>'>
					<liferay-util:include page="/mail.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("external-services") %>'>
					<liferay-util:include page="/external_services.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("script") %>'>
					<liferay-util:include page="/script.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("shutdown") %>'>
					<liferay-util:include page="/shutdown.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/resources.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>