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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
int abstractLength = (Integer)request.getAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH);

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);
%>

<c:if test="<%= entry.isSmallImage() %>">
	<div class="asset-small-image">
		<img alt="" class="asset-small-image img-polaroid" src="<%= HtmlUtil.escape(entry.getEntryImageURL(themeDisplay)) %>" width="150" />
	</div>
</c:if>

<%
String summary = HtmlUtil.escape(entry.getDescription());

summary = HtmlUtil.replaceNewLine(summary);

if (Validator.isNull(summary)) {
	summary = HtmlUtil.escape(entry.getDeckTitle());
	summary = HtmlUtil.replaceNewLine(summary);
}

if (Validator.isNull(summary)) {
	summary = HtmlUtil.stripHtml(entry.getContent());
}
%>

<%= StringUtil.shorten(summary, abstractLength) %>