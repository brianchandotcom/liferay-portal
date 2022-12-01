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
ViewClientExtensionEntryDisplayContext<CET> viewClientExtensionEntryDisplayContext = (ViewClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.VIEW_CLIENT_EXTENSION_ENTRY_PART_DISPLAY_CONTEXT);

Collection<Method> propertyMethods = viewClientExtensionEntryDisplayContext.getPropertyMethods();

for (Method method : propertyMethods) {
	CETProperty cetProperty = method.getAnnotation(CETProperty.class);
	String label = viewClientExtensionEntryDisplayContext.getLabel(method);
	Object value = viewClientExtensionEntryDisplayContext.getValue(method);
%>

	<c:choose>
		<c:when test="<%= cetProperty.type() == CETProperty.Type.Boolean %>">
			<aui:input disabled="<%= true %>" label="<%= label %>" name="<%= label %>" type="checkbox" value="<%= value %>" />
		</c:when>
		<c:when test="<%= (cetProperty.type() == CETProperty.Type.String) || (cetProperty.type() == CETProperty.Type.URL) %>">
			<aui:input disabled="<%= true %>" label="<%= label %>" name="<%= label %>" type="text" value="<%= value %>" />
		</c:when>
		<c:when test="<%= (cetProperty.type() == CETProperty.Type.StringList) || (cetProperty.type() == CETProperty.Type.URLList) %>">
			<aui:input disabled="<%= true %>" label="<%= label %>" name="<%= label %>" type="textarea" value="<%= value %>" />
		</c:when>
		<c:otherwise>
			<aui:input disabled="<%= true %>" label="<%= label %>" name="<%= label %>" type="text" value="<%= value %>" />
		</c:otherwise>
	</c:choose>

<%
}
%>