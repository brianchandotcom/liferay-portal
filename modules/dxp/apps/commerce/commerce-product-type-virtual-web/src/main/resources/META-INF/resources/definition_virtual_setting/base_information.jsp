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
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="base-information" />

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<aui:fieldset>
	<aui:input name="activationStatus" />

	<aui:input helpMessage="number-of-days" label="duration" name="numberOfDays" type="long" value="<%= (cpDefinitionVirtualSetting == null) ? 0 : TimeUnit.MILLISECONDS.toDays(cpDefinitionVirtualSetting.getDuration()) %>">
		<aui:validator name="number" />
	</aui:input>

	<aui:input name="maxUsages" />
</aui:fieldset>