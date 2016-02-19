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

<%@ include file="/document_library/init.jsp" %>

<%
FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

boolean showExtraInfo = ParamUtil.getBoolean(request, "showExtraInfo");
%>

<c:choose>
	<c:when test="<%= PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED && !showExtraInfo && (fileEntry.getSize() > 0) %>">
		<liferay-util:include page="/document_library/view_file_entry_simple_view.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/document_library/view_file_entry.jsp" servletContext="<%= application %>">
			<liferay-util:param name="addPortletBreadcrumbEntries" value="<%= Boolean.FALSE.toString() %>" />
		</liferay-util:include>
	</c:otherwise>
</c:choose>