<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/blogs/asset/init.jsp" %>

<liferay-util:buffer
	var="html"
>
	<liferay-util:include page="/blogs/asset/full_content.portal.jsp" servletContext="<%= application %>" />
</liferay-util:buffer>

<%
ContentTransformerHandler contentTransformerHandler = ContentTransformerUtil.getContentTransformerHandler();

if (contentTransformerHandler != null) {
	html = contentTransformerHandler.transform(ContentTransformerContentTypes.HTML, html);
}
%>

<%= html %>