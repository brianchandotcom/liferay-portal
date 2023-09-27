<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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