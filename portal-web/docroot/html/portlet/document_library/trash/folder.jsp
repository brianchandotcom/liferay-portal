<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);
%>

<div class="asset-resource-info">
	<liferay-ui:icon
		image='<%= "../common/folder_empty" %>'
		label="<%= true %>"
		message="<%= HtmlUtil.escape(folder.getName()) %>"
	/>
</div>

<p class="asset-description">
	<%= HtmlUtil.escape(StringUtil.shorten(folder.getDescription())) %>
</p>