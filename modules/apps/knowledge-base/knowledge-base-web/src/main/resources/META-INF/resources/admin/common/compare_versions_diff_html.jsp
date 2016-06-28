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

<%@ include file="/admin/common/init.jsp" %>

<%
String diffHtmlResults = (String)request.getAttribute(WebKeys.DIFF_HTML_RESULTS);
double diffVersion = GetterUtil.getDouble(request.getAttribute(WebKeys.DIFF_VERSION));

String infoMessage = StringPool.BLANK;

if (diffVersion > 0) {
	infoMessage = LanguageUtil.format(request, "unable-to-render-version-x", diffVersion);
}
%>

<liferay-ui:diff-html
	diffHtmlResults="<%= diffHtmlResults %>"
	infoMessage="<%= infoMessage %>"
/>