<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>

<liferay-portlet:runtime
	persistSettings='<%= ParamUtil.getBoolean(request, "persistSettings", true) %>'
	portletName='<%= ParamUtil.getString(request, "testRuntimePortletId", (String)request.getAttribute("testRuntimePortletId")) %>'
/>