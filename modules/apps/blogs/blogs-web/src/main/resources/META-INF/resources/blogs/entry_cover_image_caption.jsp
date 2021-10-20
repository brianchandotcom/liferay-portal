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

<%@ include file="/blogs/init.jsp" %>

<%
String coverImageCaption = ParamUtil.getString(request, "coverImageCaption");
String coverImageURL = ParamUtil.getString(request, "coverImageURL");
String viewEntryURL = ParamUtil.getString(request, "viewEntryURL");
%>

<c:if test="<%= Validator.isNotNull(coverImageURL) %>">
	<c:if test="<%= Validator.isNotNull(viewEntryURL) %>">
		<a href="<%= HtmlUtil.escape(viewEntryURL) %>">
	</c:if>

	<div <c:if test="<%= Validator.isNotNull(coverImageCaption) %>">aria-label="<%= HtmlUtil.escapeAttribute(HtmlUtil.stripHtml(coverImageCaption)) %>" role="img"</c:if> class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image" style="background-image: url(<%= coverImageURL %>);"></div>

	<c:if test="<%= Validator.isNotNull(viewEntryURL) %>">
		</a>
	</c:if>
</c:if>