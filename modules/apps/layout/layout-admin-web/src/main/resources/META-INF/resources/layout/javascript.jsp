<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="javascript-client-extensions"
>
	<react:component
		module="{GlobalJSCETsConfiguration} from layout-admin-web"
		props="<%= layoutLookAndFeelDisplayContext.getGlobalJSCETsConfigurationProps(Layout.class.getName(), selLayout.getPlid()) %>"
	/>
</liferay-frontend:fieldset>

<c:if test='<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_LAYOUT_JAVASCRIPT || Validator.isNotNull(layoutTypeSettingsUnicodeProperties.getProperty("javascript")) %>'>
	<liferay-frontend:fieldset
		collapsed="<%= false %>"
		collapsible="<%= true %>"
		label="custom-javascript"
	>
		<aui:input cssClass="propagatable-field" disabled="<%= layoutsAdminDisplayContext.isReadOnly() || selLayout.isLayoutPrototypeLinkActive() || !PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_LAYOUT_JAVASCRIPT %>" label="javascript" name="TypeSettingsProperties--javascript--" placeholder="javascript" type="textarea" value='<%= layoutTypeSettingsUnicodeProperties.getProperty("javascript") %>' wrap="soft" wrapperCssClass="c-mb-0" />

		<p class="text-secondary">
			<liferay-ui:message key="this-javascript-code-is-executed-at-the-bottom-of-the-page" />
		</p>
	</liferay-frontend:fieldset>
</c:if>