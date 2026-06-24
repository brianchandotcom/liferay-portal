<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/init.jsp" %>

<%
LayoutContentVersionDisplayContext layoutContentVersionDisplayContext = (LayoutContentVersionDisplayContext)request.getAttribute(LayoutContentVersionWebKeys.LAYOUT_CONTENT_VERSION_DISPLAY_CONTEXT);
%>

<react:component
	module="{VersionHistory} from layout-content-web"
	props="<%= layoutContentVersionDisplayContext.getContext() %>"
/>