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

<%@ include file="/init.jsp" %>

<%
String iconURL = ParamUtil.getString(request, "iconURL");
%>

<div class="search-container-icon">
	<c:choose>
		<c:when test='<%= iconURL.contains(".svg#") %>'>
			<svg class="lexicon-icon">
				<use xlink:href="<%= iconURL %>" />
			</svg>
		</c:when>
		<c:when test="<%= Validator.isUrl(iconURL) %>">
			<img alt="thumbnail" src="<%= iconURL %>" />
		</c:when>
	</c:choose>
</div>