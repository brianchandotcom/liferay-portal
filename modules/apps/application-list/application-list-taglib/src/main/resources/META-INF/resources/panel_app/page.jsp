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

<%@ include file="/panel_app/init.jsp" %>

<%
boolean active = GetterUtil.getBoolean(request.getAttribute("liferay-application-list:panel-app:active"));
String label = (String)request.getAttribute("liferay-application-list:panel-app:label");
int notificationsCount = GetterUtil.getInteger(request.getAttribute("liferay-application-list:panel-app:notificationsCount"));
String url = (String)request.getAttribute("liferay-application-list:panel-app:url");
%>

<c:if test="<%= Validator.isNotNull(url) %>">
	<li class="<%= active ? "active" : StringPool.BLANK %> nav-item" role="presentation">
		<aui:a ariaRole="menuitem" cssClass="nav-link" data='<%= (Map<String, Object>)request.getAttribute("liferay-application-list:panel-app:data") %>' href="<%= url %>" id='<%= (String)request.getAttribute("liferay-application-list:panel-app:id") %>'>
			<%= label %>

			<c:if test="<%= notificationsCount > 0 %>">
				<clay:badge
					cssClass="float-right"
					displayType="danger"
					label="<%= String.valueOf(notificationsCount) %>"
				/>
			</c:if>
		</aui:a>
	</li>
</c:if>