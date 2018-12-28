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
Map<String, Object> contextUseForAllTitle = new HashMap<>();

contextUseForAllTitle.put("checked", portletConfigurationCSSPortletDisplayContext.isUseCustomTitle());
contextUseForAllTitle.put("disableOnChecked", false);
contextUseForAllTitle.put("label", LanguageUtil.get(request, "use-custom-title"));
contextUseForAllTitle.put("name", renderResponse.getNamespace() + "useCustomTitle");
contextUseForAllTitle.put("inputSelector", ".custom-title input");
%>

<soy:component-renderer
	context="<%= contextUseForAllTitle %>"
	module='<%= portletConfigurationCSSPortletDisplayContext.getModuleName() + "/js/ToggleDisableInputs.es" %>'
	templateNamespace="com.liferay.portlet.configuration.css.web.ToggleDisableInputs.render"
/>

<aui:field-wrapper cssClass="custom-title lfr-input-text-container">
	<liferay-ui:input-localized
		defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>"
		disabled="<%= !portletConfigurationCSSPortletDisplayContext.isUseCustomTitle() %>"
		name="customTitle"
		xml="<%= portletConfigurationCSSPortletDisplayContext.getCustomTitleXML() %>"
	/>
</aui:field-wrapper>

<aui:select label="portlet-decorators" name="portletDecoratorId">

	<%
	for (PortletDecorator portletDecorator : theme.getPortletDecorators()) {
	%>

		<aui:option label="<%= portletDecorator.getName() %>" selected="<%= Objects.equals(portletDecorator.getPortletDecoratorId(), portletConfigurationCSSPortletDisplayContext.getPortletDecoratorId()) %>" value="<%= portletDecorator.getPortletDecoratorId() %>" />

	<%
	}
	%>

</aui:select>

<span class="alert alert-info form-hint hide" id="border-note">
	<liferay-ui:message key="this-change-will-only-be-shown-after-you-refresh-the-page" />
</span>