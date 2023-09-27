<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionVirtualSettingDisplayContext.getCPDefinition();
CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();

boolean durationDisabled = cpDefinition.isSubscriptionEnabled();

long durationDays = 0;

if ((cpDefinitionVirtualSetting != null) && (cpDefinitionVirtualSetting.getDuration() > 0)) {
	durationDays = cpDefinitionVirtualSetting.getDuration() / Time.DAY;
}
%>

<%@ include file="/base_information.jspf" %>