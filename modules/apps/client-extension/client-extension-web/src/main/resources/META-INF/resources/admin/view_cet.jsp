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
ViewClientExtensionEntryPartDisplayContext<CET> viewClientExtensionEntryPartDisplayContext = (ViewClientExtensionEntryPartDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.VIEW_CLIENT_EXTENSION_ENTRY_PART_DISPLAY_CONTEXT);

Collection<Method> propertyMethods = viewClientExtensionEntryPartDisplayContext.getPropertyMethods();

for (Method method : propertyMethods) {
	CETProperty cetProperty = method.getAnnotation(CETProperty.class);
	String label = viewClientExtensionEntryPartDisplayContext.getLabel(method);
	Object value = viewClientExtensionEntryPartDisplayContext.getValue(method);
%>

	<c:choose>
		<c:when test="<%= cetProperty.type() == CETPropertyType.Boolean %>">
			<aui:input disabled="<%= true %>" label="<%= label %>" name="<%= label %>" type="checkbox" value="<%= value %>" />
		</c:when>
		<c:when test="<%= (cetProperty.type() == CETPropertyType.String) || (cetProperty.type() == CETPropertyType.URL) %>">
			<aui:input disabled="<%= true %>" label="<%= label %>" name="<%= label %>" type="text" value="<%= value %>" />
		</c:when>
		<c:when test="<%= (cetProperty.type() == CETPropertyType.StringList) || (cetProperty.type() == CETPropertyType.URLList) %>">
			<aui:input disabled="<%= true %>" label="<%= label %>" name="<%= label %>" type="textarea" value="<%= value %>" />
		</c:when>
		<c:otherwise>
			<aui:input disabled="<%= true %>" label="<%= label %>" name="<%= label %>" type="text" value="<%= value %>" />
		</c:otherwise>
	</c:choose>

<%
}
%>