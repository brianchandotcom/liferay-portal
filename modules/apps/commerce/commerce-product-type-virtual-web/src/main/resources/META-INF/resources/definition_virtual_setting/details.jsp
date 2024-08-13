<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();

String className = CPDefinition.class.getName();
long classPK = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionId();

if (cpDefinitionVirtualSetting != null) {
	className = cpDefinitionVirtualSetting.getClassName();
	classPK = cpDefinitionVirtualSetting.getClassPK();
}
%>

<frontend-data-set:classic-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"className", className
		).put(
			"classPK", String.valueOf(classPK)
		).build()
	%>'
	creationMenu="<%= cpDefinitionVirtualSettingDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= CPDefinitionVirtualSettingFDSNames.VIRTUAL_SETTING_FILES %>"
	formName="fm"
	id="<%= CPDefinitionVirtualSettingFDSNames.VIRTUAL_SETTING_FILES %>"
	itemsPerPage="<%= 10 %>"
	selectedItemsKey="cpDefinitionVirtualSettingFileId"
/>