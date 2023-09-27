<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<liferay-ui:header
	backURL="javascript:history.go(-1);"
	title="error"
/>

<liferay-ui:error exception="<%= NoSuchKaleoProcessException.class %>" message="the-process-could-not-be-found" />
<liferay-ui:error exception="<%= WorkflowException.class %>" message="an-unexpected-error-occurred" />

+<liferay-ui:error-principal />