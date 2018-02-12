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

<%@ include file="/process_title/init.jsp" %>

<c:choose>
	<c:when test="<%= listView %>">
		<span id="<%= domId %>">
			<liferay-ui:message key="<%= HtmlUtil.escape(backgroundTaskName) %>" />
		</span>
	</c:when>
	<c:otherwise>
		<h6 class="text-default">
			<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(userName), modifiedDateDescription} %>" key="x-modified-x-ago" />
		</h6>

		<h5 id="<%= domId %>">
			<liferay-ui:message key="<%= HtmlUtil.escape(backgroundTaskName) %>" />
		</h5>
	</c:otherwise>
</c:choose>