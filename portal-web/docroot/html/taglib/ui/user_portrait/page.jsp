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

<%@ include file="/html/taglib/ui/user_portrait/init.jsp" %>

<c:choose>
	<c:when test="<%= Validator.isNotNull(portraitURL) %>">
		<div class="<%= cssClass %> user-icon">
			<img alt="thumbnail" class="img-responsive <%= imageCssClass %>" src="<%= HtmlUtil.escape(portraitURL) %>">
		</div>
	</c:when>
	<c:otherwise>
		<div class="<%= colorCssClass %> <%= cssClass %> user-icon user-icon-default">
			<span><%= userInitials %></span>
		</div>
	</c:otherwise>
</c:choose>