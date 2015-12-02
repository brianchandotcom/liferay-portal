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

<%@ include file="/management_bar_button/init.jsp" %>

<c:choose>
	<c:when test="<%= Validator.isNotNull(icon) %>">
		<a class="<%= cssClass %>" href="<%= href %>" id="<%= id %>">
			<aui:icon image="<%= icon %>" />
		</a>
	</c:when>
	<c:when test="<%= Validator.isNotNull(iconCssClass) %>">
		<aui:a cssClass="<%= cssClass %>" href="<%= href %>" iconCssClass="<%= iconCssClass %>" id="<%= id %>" />
	</c:when>
</c:choose>