<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String changesetUuid = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:publish-model-menu-item:changesetUuid"));

String className = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:publish-model-menu-item:className"));
String uuid = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:publish-model-menu-item:uuid"));

boolean showMenuItem = ChangesetTaglibDisplayContext.isShowPublishMenuItem(group, portletDisplay.getId(), className, uuid);
%>