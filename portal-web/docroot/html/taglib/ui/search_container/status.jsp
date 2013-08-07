<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/search_container/init.jsp" %>

<%
int status = GetterUtil.getInteger(request.getAttribute("liferay-ui:search-container-column-status:status"));
long statusByUserId = GetterUtil.getLong(request.getAttribute("liferay-ui:search-container-column-status:statusByUserId"));
Date statusDate = GetterUtil.getDate(request.getAttribute("liferay-ui:search-container-column-status:statusDate"), DateFormatFactoryUtil.getDate(locale), null);
%>

<c:if test="<%= statusByUserId > 0 %>">

	<%
	User userDisplay = UserLocalServiceUtil.getUser(statusByUserId);
	%>

	<liferay-util:buffer var="buffer">
		<div class="user-status-tooltip">
			<span class="user-status-avatar">
				<img alt="<%= HtmlUtil.escapeAttribute(userDisplay.getFullName()) %>" class="user-status-avatar-image" src="<%= HtmlUtil.escape(userDisplay.getPortraitURL(themeDisplay)) %>" />
			</span>
			<span class="user-status-info">
				<div class="user-status-name">
					<aui:a href="<%= userDisplay.getDisplayURL(themeDisplay) %>"><%= StringUtil.shorten(userDisplay.getFullName(), 20) %></aui:a>
				</div>
				<div class="user-status-date">
					<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(pageContext, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" />
				</div>
			</span>
		</div>
	</liferay-util:buffer>

	<span onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(buffer) %>')">
</c:if>

<aui:workflow-status showIcon="<%= false %>" showLabel="<%= false %>" status="<%= status %>" />

<c:if test="<%= statusByUserId > 0 %>">
	</span>
</c:if>