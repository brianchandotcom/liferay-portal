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

<liferay-util:html-top
	outputKey="blogs_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/blogs/css/common_main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<liferay-util:dynamic-include key="com.liferay.blogs.web#/blogs/asset/full_content.jsp#pre" />

<%
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

String entryTitle = BlogsEntryUtil.getDisplayTitle(resourceBundle, entry);
%>

<div class="portlet-blogs">
	<div class="widget-mode-simple" data-analytics-asset-id="<%= String.valueOf(entry.getEntryId()) %>" data-analytics-asset-title="<%= HtmlUtil.escapeAttribute(entryTitle) %>" data-analytics-asset-type="blog">
		<div class="widget-mode-simple-entry">
			<div class="widget-content" id="<portlet:namespace /><%= entry.getEntryId() %>">
				<liferay-util:include page="/blogs/entry_cover_image_caption.jsp" servletContext="<%= application %>">
					<liferay-util:param name="coverImageCaption" value="<%= entry.getCoverImageCaption() %>" />
					<liferay-util:param name="coverImageURL" value="<%= entry.getCoverImageURL(themeDisplay) %>" />
				</liferay-util:include>

				<%= entry.getContent() %>
			</div>

			<liferay-expando:custom-attributes-available
				className="<%= BlogsEntry.class.getName() %>"
			>
				<liferay-expando:custom-attribute-list
					className="<%= BlogsEntry.class.getName() %>"
					classPK="<%= (entry != null) ? entry.getEntryId() : 0 %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-expando:custom-attributes-available>
		</div>
	</div>
</div>

<liferay-util:dynamic-include key="com.liferay.blogs.web#/blogs/asset/full_content.jsp#post" />