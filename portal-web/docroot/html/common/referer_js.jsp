<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	referer = HtmlUtil.escapeJSLink(referer);

	referer = HtmlUtil.escapeJS(referer);
	%>

	location.href = '<%= referer %>';
</script>