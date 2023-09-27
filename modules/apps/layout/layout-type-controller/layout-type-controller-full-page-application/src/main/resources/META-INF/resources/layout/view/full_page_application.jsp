<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout/view/init.jsp" %>

<%
String ppid = ParamUtil.getString(request, "p_p_id");

UnicodeProperties typeSettingsUnicodeProperties = layout.getTypeSettingsProperties();

if (Validator.isNull(ppid)) {
	ppid = typeSettingsUnicodeProperties.getProperty("fullPageApplicationPortlet");
}

String velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
String velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());

if (Validator.isNotNull(velocityTemplateContent)) {
	RuntimePageUtil.processTemplate(request, response, ppid, velocityTemplateId, velocityTemplateContent);
}
%>

<liferay-layout:layout-common />