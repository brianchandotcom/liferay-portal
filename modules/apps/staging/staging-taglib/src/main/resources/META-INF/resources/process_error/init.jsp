<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
boolean authException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:authException"));
boolean duplicateLockException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:duplicateLockException"));
boolean illegalArgumentException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:illegalArgumentException"));
boolean layoutPrototypeException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:layoutPrototypeException"));
boolean noSuchExceptions = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:noSuchExceptions"));
boolean remoteExportException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:remoteExportException"));
boolean remoteOptionsException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:remoteOptionsException"));
boolean systemException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:systemException"));
%>