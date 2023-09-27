<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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