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
long changesetGroupId = GetterUtil.getLong(request.getAttribute("liferay-export-import-changeset:publish-changeset:groupId"));
String changesetUuid = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:publish-changeset:changesetUuid"));

boolean showMenuItem = false;

if (changesetGroupId != 0) {
	Group changesetGroup = GroupLocalServiceUtil.fetchGroup(changesetGroupId);

	showMenuItem = ChangesetTaglibDisplayContext.isShowPublishMenuItem(changesetGroup, portletDisplay.getId());
}
else {
	showMenuItem = ChangesetTaglibDisplayContext.isShowPublishMenuItem(group, portletDisplay.getId());
}
%>