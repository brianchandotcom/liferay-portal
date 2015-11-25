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

<%= ReleaseInfo.getReleaseInfo() %><br />

<%
long uptimeDiff = System.currentTimeMillis() - PortalUtil.getUptime().getTime();
long days = uptimeDiff / Time.DAY;
long hours = (uptimeDiff / Time.HOUR) % 24;
long minutes = (uptimeDiff / Time.MINUTE) % 60;
long seconds = (uptimeDiff / Time.SECOND) % 60;

PortletURL serverURL = renderResponse.createRenderURL();

serverURL.setParameter("mvcRenderCommandName", "/server_admin/view");
serverURL.setParameter("tabs1", tabs1);
serverURL.setParameter("tabs2", tabs2);
serverURL.setParameter("tabs3", tabs3);
%>

<liferay-ui:message key="uptime" />:

<c:if test="<%= days > 0 %>">
	<%= days %> <%= LanguageUtil.get(request, ((days > 1) ? "days" : "day")) %>,
</c:if>

<%= numberFormat.format(hours) %>:<%= numberFormat.format(minutes) %>:<%= numberFormat.format(seconds) %>

<br /><br />

<c:choose>
	<c:when test="<%= windowState.equals(WindowState.NORMAL) %>">
		<html:link page="/server_admin/view?windowState=maximized&portletMode=view&actionURL=0"><liferay-ui:message key="more" /></html:link> &raquo;
	</c:when>
	<c:otherwise>
		<liferay-ui:tabs
			names="resources,log-levels,properties,captcha,data-migration,file-uploads,mail,external-services,script,shutdown"
			param="tabs2"
			portletURL="<%= serverURL %>"
		/>

		<c:choose>
			<c:when test='<%= tabs2.equals("log-levels") %>'>
				<liferay-util:include page="/log_levels.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("properties") %>'>
				<liferay-util:include page="/properties.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("captcha") %>'>
				<liferay-util:include page="/captcha.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("data-migration") %>'>
				<liferay-util:include page="/data_migration.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("file-uploads") %>'>
				<liferay-util:include page="/file_uploads.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("mail") %>'>
				<liferay-util:include page="/mail.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("external-services") %>'>
				<liferay-util:include page="/external_services.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("script") %>'>
				<liferay-util:include page="/script.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("shutdown") %>'>
				<liferay-util:include page="/shutdown.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/resources.jsp" servletContext="<%= application %>" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>