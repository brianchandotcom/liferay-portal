<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();

long durationDays = 0;

if ((cpDefinitionVirtualSetting != null) && (cpDefinitionVirtualSetting.getDuration() > 0)) {
	durationDays = cpDefinitionVirtualSetting.getDuration() / Time.DAY;
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="base-information"
/>

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<aui:fieldset>
	<aui:input name="activationStatus" />

	<aui:input helpMessage="duration-help" label="duration" name="durationDays" suffix="days" type="long" value="<%= durationDays %>">
		<aui:validator name="number" />
	</aui:input>

	<aui:input label="max-number-of-downloads" name="maxUsages" />
</aui:fieldset>