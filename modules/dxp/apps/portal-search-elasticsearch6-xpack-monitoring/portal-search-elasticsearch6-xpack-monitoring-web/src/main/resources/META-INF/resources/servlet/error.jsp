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

<%@ page import="com.liferay.portal.kernel.security.auth.PrincipalException" %>

<%@ page import="java.net.ConnectException" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%
com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.servlet.display.context.ErrorDisplayContext errorDisplayContext = (com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.servlet.display.context.ErrorDisplayContext)request.getAttribute(com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.constants.XPackMonitoringProxyServletWebKeys.ERROR_DISPLAY_CONTEXT);

Exception e = errorDisplayContext.getException();
%>

<div>
	<liferay-ui:message arguments="xpack-monitoring-configuration-name" key="is-temporarily-unavailable" translateArguments="<%= true %>" />
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