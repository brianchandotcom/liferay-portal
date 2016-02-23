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

<%@ include file="/card/user_vertical_card/init.jsp" %>

<%@ include file="/card/vertical_card/start.jspf" %>

<c:choose>
	<c:when test="<%= Validator.isNotNull(portraitURL) %>">
		<div class="aspect-ratio aspect-ratio-bg-center aspect-ratio-bg-cover" style="background-image: url('<%= portraitURL %>')">
			<aui:a href="<%= url %>">
				<img alt="" class="sr-only" src="<%= portraitURL %>" />
			</aui:a>
		</div>
	</c:when>
	<c:otherwise>
		<div class="aspect-ratio aspect-ratio-bg-center aspect-ratio-bg-cover <%= colorCssClass %>">
			<span class="icon-xl user-vertical-card-initials"><%= userInitials %></span>
		</div>
	</c:otherwise>
</c:choose>

<%@ include file="/card/vertical_card/end.jspf" %>