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
String driverClassName = ParamUtil.getString(request, "driverClassName");
String driverUrl = ParamUtil.getString(request, "driverUrl");
String driverUserName = ParamUtil.getString(request, "driverUserName");
String driverPassword = ParamUtil.getString(request, "driverPassword");

if (Validator.isNull(driverPassword)) {
	long sourceId = ParamUtil.getLong(request, "sourceId");

	Source source = SourceLocalServiceUtil.fetchSource(sourceId);

	if (source != null) {
		driverPassword = source.getDriverPassword();
	}
}

boolean validJDBCConnection = true;

try {
	ReportsEngineConsoleUtil.validateJDBCConnection(driverClassName, driverUrl, driverUserName, driverPassword);
}
catch (SourceJDBCConnectionException sjdbcce) {
	validJDBCConnection = false;
}
%>

<c:choose>
	<c:when test="<%= validJDBCConnection %>">
		<div class="portlet-msg-success">
			<liferay-ui:message key="you-have-successfully-connected-to-the-database" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-error">
			<liferay-ui:message key="could-not-connect-to-the-database.-please-verify-that-the-settings-are-correct" />
		</div>
	</c:otherwise>
</c:choose>