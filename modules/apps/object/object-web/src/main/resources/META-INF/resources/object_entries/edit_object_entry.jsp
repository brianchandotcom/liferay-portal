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
long objectEntryId = ParamUtil.getLong(request, "objectEntryId");

ObjectEntryDisplayContext objectEntryDisplayContext = (ObjectEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectLayoutTab objectLayoutTab = objectEntryDisplayContext.getObjectLayoutTab();
%>

<clay:navigation-bar
	inverted="<%= false %>"
	navigationItems="<%= objectEntryDisplayContext.getNavigationItems() %>"
/>

<c:choose>
	<c:when test="<%= (objectLayoutTab != null) && (objectLayoutTab.getObjectRelationshipId() > 0) %>">
		<liferay-util:include page="/object_entries/object_entry/relationship.jsp" servletContext="<%= application %>">
			<liferay-util:param name="objectEntryId" value="<%= String.valueOf(objectEntryId) %>" />
			<liferay-util:param name="objectLayoutTabId" value="<%= String.valueOf(objectLayoutTab.getObjectLayoutTabId()) %>" />
		</liferay-util:include>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/object_entries/object_entry/form.jsp" servletContext="<%= application %>">
			<liferay-util:param name="objectEntryId" value="<%= String.valueOf(objectEntryId) %>" />
		</liferay-util:include>
	</c:otherwise>
</c:choose>