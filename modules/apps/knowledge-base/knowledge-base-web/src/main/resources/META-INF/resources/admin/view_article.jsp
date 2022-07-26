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

<%@ include file="/admin/init.jsp" %>

<c:choose>
	<c:when test='<%= GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-156421")) %>'>
		<c:if test="<%= redirect.equals(currentURL) %>">
			<liferay-util:include page="/admin/common/vertical_menu.jsp" servletContext="<%= application %>" />

			<div class="knowledge-base-admin-content">
		</c:if>

		<liferay-util:include page="/admin/common/view_kb_article.jsp" servletContext="<%= application %>" />

		<c:if test="<%= redirect.equals(currentURL) %>">
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="<%= redirect.equals(currentURL) %>">
			<liferay-util:include page="/admin/common/top_tabs.jsp" servletContext="<%= application %>" />
		</c:if>

		<liferay-util:include page="/admin/common/view_kb_article.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>