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
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

UnicodeProperties layoutTypeSettingsUnicodeProperties = null;

if (selLayout != null) {
	layoutTypeSettingsUnicodeProperties = selLayout.getTypeSettingsProperties();
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="javascript"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<%
LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);
%>

<div class="mb-5">
	<react:component
		module="js/layout/look_and_feel/GlobalJSCETsConfiguration"
		props="<%= layoutLookAndFeelDisplayContext.getGlobalJSCETsConfigurationProps(Layout.class.getName(), selLayout.getPlid()) %>"
	/>
</div>

<div>
	<h3 class="sheet-subtitle"><liferay-ui:message key="custom-javascript" /></h3>

	<aui:input cssClass="propagatable-field" disabled="<%= selLayout.isLayoutPrototypeLinkActive() %>" label="javascript" name="TypeSettingsProperties--javascript--" placeholder="javascript" type="textarea" value='<%= layoutTypeSettingsUnicodeProperties.getProperty("javascript") %>' wrap="soft" wrapperCssClass="mb-0" />

	<p class="text-secondary">
		<liferay-ui:message key="this-javascript-code-is-executed-at-the-bottom-of-the-page" />
	</p>
</div>