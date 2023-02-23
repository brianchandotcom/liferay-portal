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
String externalReferenceCode = ParamUtil.getString(request, "externalReferenceCode");

ObjectEntryDisplayContext objectEntryDisplayContext = (ObjectEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectLayoutTab objectLayoutTab = objectEntryDisplayContext.getObjectLayoutTab();
%>

<c:if test="<%= (objectEntryDisplayContext.getObjectEntry() != null) && (objectLayoutTab != null) %>">

	<%
	ObjectDefinition objectDefinition = objectEntryDisplayContext.getObjectDefinition1();
	%>

	<liferay-frontend:screen-navigation
		key="<%= objectDefinition.getClassName() %>"
		portletURL="<%= currentURLObj %>"
	/>
</c:if>

<c:choose>
	<c:when test="<%= (objectLayoutTab != null) && (objectLayoutTab.getObjectRelationshipId() > 0) %>">
		<liferay-util:include page="/object_entries/object_entry/relationship.jsp" servletContext="<%= application %>">
			<liferay-util:param name="externalReferenceCode" value="<%= externalReferenceCode %>" />
			<liferay-util:param name="objectLayoutTabId" value="<%= String.valueOf(objectLayoutTab.getObjectLayoutTabId()) %>" />
		</liferay-util:include>
	</c:when>
	<c:when test="<%= objectEntryDisplayContext.isShowObjectEntryForm() %>">
		<liferay-util:include page="/object_entries/object_entry/form.jsp" servletContext="<%= application %>">
			<liferay-util:param name="externalReferenceCode" value="<%= externalReferenceCode %>" />
		</liferay-util:include>
	</c:when>
</c:choose>