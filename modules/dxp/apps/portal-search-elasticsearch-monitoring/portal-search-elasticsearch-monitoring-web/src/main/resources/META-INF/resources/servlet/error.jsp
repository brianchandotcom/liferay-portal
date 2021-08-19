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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.security.auth.PrincipalException" %><%@
page import="com.liferay.portal.search.elasticsearch.monitoring.web.internal.constants.MonitoringProxyServletWebKeys" %><%@
page import="com.liferay.portal.search.elasticsearch.monitoring.web.internal.servlet.display.context.ErrorDisplayContext" %>

<%@ page import="java.net.ConnectException" %>

<%
ErrorDisplayContext errorDisplayContext = (ErrorDisplayContext)request.getAttribute(MonitoringProxyServletWebKeys.ERROR_DISPLAY_CONTEXT);

Exception e = errorDisplayContext.getException();
%>

<div>
	<liferay-ui:message arguments="monitoring-configuration-name[elasticsearch]" key="is-temporarily-unavailable" translateArguments="<%= true %>" />
</div>

<div>
	<c:if test="<%= e instanceof ConnectException %>">
		<liferay-ui:message arguments="<%= errorDisplayContext.getConnectExceptionAddress() %>" key="could-not-connect-to-address-x.-please-verify-that-the-specified-port-is-correct-and-that-the-remote-server-is-configured-to-accept-requests-from-this-server" />
	</c:if>

	<c:if test="<%= e instanceof PrincipalException.MustBeAuthenticated %>">
		<liferay-ui:message key="please-sign-in-to-access-this-application" />
	</c:if>

	<c:if test="<%= e instanceof PrincipalException.MustHavePermission %>">
		<liferay-ui:message key="you-do-not-have-the-required-permissions" />
	</c:if>
</div>