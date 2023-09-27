<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String className = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:export-entity:className"));
long classNameId = GetterUtil.getLong(request.getAttribute("liferay-export-import-changeset:export-entity:classNameId"));

ClassName classNameModel = null;

if (classNameId != 0) {
	classNameModel = ClassNameLocalServiceUtil.getClassName(classNameId);
}
else if (Validator.isNotNull(className)) {
	classNameModel = ClassNameLocalServiceUtil.getClassName(className);
}

if (classNameModel != null) {
	className = classNameModel.getClassName();
	classNameId = classNameModel.getClassNameId();
}

long exportEntityGroupId = GetterUtil.getLong(request.getAttribute("liferay-export-import-changeset:export-entity:groupId"));
String uuid = GetterUtil.getString(request.getAttribute("liferay-export-import-changeset:export-entity:uuid"));

boolean showMenuItem = ChangesetTaglibDisplayContext.isShowExportMenuItem(group, portletDisplay.getId());
%>