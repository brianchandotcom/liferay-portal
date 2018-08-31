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

<%@ include file="/com.liferay.sharing.web/init.jsp" %>

<%
FileEntry fileEntry = (FileEntry)request.getAttribute("view_file_entry_sharing_entry.jsp-fileEntry");
%>

<liferay-asset:asset-display
	className="<%= DLFileEntry.class.getName() %>"
	classPK="<%= fileEntry.getFileEntryId() %>"
	showExtraInfo="<%= true %>"
	template="<%= AssetRenderer.TEMPLATE_FULL_CONTENT %>"
/>

<liferay-comment:discussion
	className="<%= DLFileEntry.class.getName() %>"
	classPK="<%= fileEntry.getFileEntryId() %>"
	formName='<%= "fm" + fileEntry.getPrimaryKey() %>'
	ratingsEnabled="<%= false %>"
	redirect="<%= currentURL %>"
	userId="<%= fileEntry.getUserId() %>"
/>