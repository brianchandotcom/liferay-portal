<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMTemplate template = (DDMTemplate)row.getObject();

String rowHREF = (String)row.getParameter("rowHREF");
%>

<c:if test="<%= Validator.isNotNull(rowHREF) %>">
	<a href="<%= rowHREF %>">
</c:if>

<c:choose>
	<c:when test="<%= template.isSmallImage() %>">
		<img alt="<%= HtmlUtil.escape(template.getName(locale)) %>" class="lfr-ddm-small-image-view" src="<%= HtmlUtil.escapeAttribute(template.getTemplateImageURL(themeDisplay)) %>" />
	</c:when>
	<c:otherwise>
		<%= HtmlUtil.escape(template.getDescription(locale)) %>
	</c:otherwise>
</c:choose>

<c:if test="<%= Validator.isNotNull(rowHREF) %>">
	</a>
</c:if>