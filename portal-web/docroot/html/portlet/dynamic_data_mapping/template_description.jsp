<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMTemplate template = (DDMTemplate)row.getObject();

String rowHREF = (String)row.getParameter("rowHREF");
%>

<a href="<%= rowHREF %>">

<c:choose>
	<c:when test="<%= template.isSmallImage() %>">
		<img alt="<%= HtmlUtil.escape(template.getName(locale)) %>" border="0" class="lfr-ddm-small-image-view" src="<%= Validator.isNotNull(template.getSmallImageURL()) ? HtmlUtil.escape(template.getSmallImageURL()) : themeDisplay.getPathImage() + "/template?img_id=" + template.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(template.getSmallImageId()) %>" />
	</c:when>
	<c:otherwise>
		<%= HtmlUtil.escape(template.getDescription(locale)) %>
	</c:otherwise>
</c:choose>

</a>