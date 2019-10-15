<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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