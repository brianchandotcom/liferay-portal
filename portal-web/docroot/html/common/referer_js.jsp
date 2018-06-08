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

<%@ page session="false" %>

<%
pageContext.setAttribute(WebKeys.THEME_DEFINE_OBJECTS, Boolean.FALSE);
%>

<%@ include file="/html/common/referer_common.jsp" %>

<script type="text/javascript">

	<%
	Boolean logout = (Boolean)request.getAttribute(WebKeys.LOGOUT);

	if (logout == null) {
		logout = Boolean.FALSE;
	}
	%>

	<c:if test="<%= logout && BrowserSnifferUtil.isIe(request) && PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), PropsKeys.NTLM_AUTH_ENABLED, PropsValues.NTLM_AUTH_ENABLED) %>">
		document.execCommand('ClearAuthenticationCache');
	</c:if>

	<%
	referer = HtmlUtil.escapeJSLink(referer);

	referer = HtmlUtil.escapeJS(referer);
	%>

	location.href = '<%= referer %>';
</script>