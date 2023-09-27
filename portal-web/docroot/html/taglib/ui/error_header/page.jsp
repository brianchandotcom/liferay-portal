<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/error_header/init.jsp" %>

<%
String referer = request.getHeader(HttpHeaders.REFERER);
%>

<liferay-ui:header
	backURL='<%= Validator.isNotNull(referer) ? referer : "javascript:history.go(-1);" %>'
	title="error"
/>