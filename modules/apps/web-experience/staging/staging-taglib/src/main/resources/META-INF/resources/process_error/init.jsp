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
boolean authException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:authException"));
boolean duplicateLockException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:duplicateLockException"));
boolean illegalArgumentException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:illegalArgumentException"));
boolean layoutPrototypeException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:layoutPrototypeException"));
boolean noSuchExceptions = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:noSuchExceptions"));
boolean remoteExportException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:remoteExportException"));
boolean remoteOptionsException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:remoteOptionsException"));
boolean systemException = GetterUtil.getBoolean(request.getAttribute("liferay-staging:process-error:systemException"));
%>