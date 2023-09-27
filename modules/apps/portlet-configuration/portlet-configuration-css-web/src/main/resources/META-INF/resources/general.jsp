<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:input
	checked="<%= portletConfigurationCSSPortletDisplayContext.isUseCustomTitle() %>"
	data='<%=
		HashMapBuilder.<String, Object>put(
			"disableOnChecked", false
		).put(
			"inputSelector", ".custom-title input"
		).build()
	%>'
	inlineLabel="right"
	label="use-custom-title"
	labelCssClass="simple-toggle-switch"
	name="useCustomTitle"
	type="toggle-switch"
/>

<aui:field-wrapper cssClass="custom-title form-group lfr-input-text-container">
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