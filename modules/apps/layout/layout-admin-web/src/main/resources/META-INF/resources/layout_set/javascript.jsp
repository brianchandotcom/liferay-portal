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
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);
%>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="javascript-client-extensions"
>
	<react:component
		module="js/layout/look_and_feel/GlobalJSCETsConfiguration"
		props="<%= layoutLookAndFeelDisplayContext.getGlobalJSCETsConfigurationProps(LayoutSet.class.getName(), selLayoutSet.getLayoutSetId()) %>"
	/>
</liferay-frontend:fieldset>

<%
UnicodeProperties layoutSetTypeSettingsUnicodeProperties = selLayoutSet.getSettingsProperties();
%>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="custom-javascript"
>
	<aui:input label="javascript" name="TypeSettingsProperties--javascript--" placeholder="javascript" type="textarea" value='<%= layoutSetTypeSettingsUnicodeProperties.getProperty("javascript") %>' wrap="soft" wrapperCssClass="mb-0 mt-4" />

	<p class="text-secondary">
		<liferay-ui:message key="paste-javascript-code-that-is-executed-at-the-bottom-of-every-page" />
	</p>
</liferay-frontend:fieldset>