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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%= ReleaseInfo.getReleaseInfo() %><br />

<%
long uptimeDiff = System.currentTimeMillis() - PortalUtil.getUptime().getTime();
long days = uptimeDiff / Time.DAY;
long hours = (uptimeDiff / Time.HOUR) % 24;
long minutes = (uptimeDiff / Time.MINUTE) % 60;
long seconds = (uptimeDiff / Time.SECOND) % 60;

PortletURL serverURL = renderResponse.createRenderURL();

serverURL.setParameter("mvcRenderCommandName", "/admin/view");
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
		<html:link page="/admin/view?windowState=maximized&portletMode=view&actionURL=0"><liferay-ui:message key="more" /></html:link> &raquo;
	</c:when>
	<c:otherwise>
		<liferay-ui:tabs
			names="resources,log-levels,properties,captcha,data-migration,file-uploads,mail,external-services,script,shutdown"
			param="tabs2"
			portletURL="<%= serverURL %>"
		/>

		<c:choose>
			<c:when test='<%= tabs2.equals("log-levels") %>'>
				<liferay-util:include page="/html/portlet/admin/server/log_levels.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("properties") %>'>
				<liferay-util:include page="/html/portlet/admin/server/properties.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("captcha") %>'>
				<liferay-util:include page="/html/portlet/admin/server/captcha.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("data-migration") %>'>
				<liferay-util:include page="/html/portlet/admin/server/data_migration.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("file-uploads") %>'>
				<liferay-util:include page="/html/portlet/admin/server/file_uploads.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("mail") %>'>
				<liferay-util:include page="/html/portlet/admin/server/mail.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("external-services") %>'>
				<liferay-util:include page="/html/portlet/admin/server/external_services.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("script") %>'>
				<liferay-util:include page="/html/portlet/admin/server/script.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("shutdown") %>'>
				<liferay-util:include page="/html/portlet/admin/server/shutdown.jsp" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/html/portlet/admin/server/resources.jsp" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>