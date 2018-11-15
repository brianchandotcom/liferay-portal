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

<%@ include file="/shared_with_me/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SharingEntry sharingEntry = (SharingEntry)row.getObject();

SharedWithMeViewDisplayContext sharedWithMeViewDisplayContext = (SharedWithMeViewDisplayContext)renderRequest.getAttribute(SharedWithMeViewDisplayContext.class.getName());

boolean hasEditPermission = sharedWithMeViewDisplayContext.hasEditPermission(sharingEntry.getClassNameId(), sharingEntry.getClassPK());
%>

<c:if test="<%= hasEditPermission || sharingEntry.isShareable() %>">
	<liferay-ui:menu
		menu="<%= sharedWithMeViewDisplayContext.getSharingEntryMenu(sharingEntry) %>"
	/>
</c:if>