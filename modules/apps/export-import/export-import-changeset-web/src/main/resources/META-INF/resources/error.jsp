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
String backURL = ParamUtil.getString(request, "backURL");

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}
%>

<liferay-ui:error exception="<%= ExportImportEntityException.class %>">

	<%
	ExportImportEntityException eiee = (ExportImportEntityException)errorException;
	%>

	<c:choose>
		<c:when test="<%= eiee.getType() == ExportImportEntityException.TYPE_GROUP_NOT_STAGED %>">
			<liferay-ui:message key="group-not-staged" />
		</c:when>
		<c:when test="<%= eiee.getType() == ExportImportEntityException.TYPE_INVALID_COMMAND %>">
			<liferay-ui:message key="invalid-command" />
		</c:when>
		<c:when test="<%= eiee.getType() == ExportImportEntityException.TYPE_NO_DATA_FOUND %>">
			<liferay-ui:message key="no-data-found" />
		</c:when>
		<c:when test="<%= eiee.getType() == ExportImportEntityException.TYPE_PORTLET_NOT_STAGED %>">
			<liferay-ui:message key="application-not-staged" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="an-unexpected-error-occurred" />
		</c:otherwise>
	</c:choose>
</liferay-ui:error>