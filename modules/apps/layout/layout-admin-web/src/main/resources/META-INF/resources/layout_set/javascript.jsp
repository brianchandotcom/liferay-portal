<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);
%>

<clay:alert
	displayType="info"
	message="some-load-attributes-cannot-be-changed-because-they-were-set-in-a-client-extension"
/>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="javascript-client-extensions"
>
	<react:component
		module="{GlobalJSCETsConfiguration} from layout-admin-web"
		props="<%= layoutLookAndFeelDisplayContext.getGlobalJSCETsConfigurationProps(LayoutSet.class.getName(), selLayoutSet.getLayoutSetId()) %>"
	/>
</liferay-frontend:fieldset>

<%
UnicodeProperties layoutSetTypeSettingsUnicodeProperties = selLayoutSet.getSettingsProperties();
%>

<c:if test='<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_LAYOUTSET_JAVASCRIPT || Validator.isNotNull(layoutSetTypeSettingsUnicodeProperties.getProperty("javascript")) %>'>
	<liferay-frontend:fieldset
		collapsed="<%= false %>"
		collapsible="<%= true %>"
		label="custom-javascript"
	>
		<aui:input disabled="<%= !PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_LAYOUTSET_JAVASCRIPT %>" label="javascript" name="TypeSettingsProperties--javascript--" placeholder="javascript" type="textarea" value='<%= layoutSetTypeSettingsUnicodeProperties.getProperty("javascript") %>' wrap="soft" wrapperCssClass="c-mb-0 c-mt-4" />

		<p class="text-secondary">
			<liferay-ui:message key="paste-javascript-code-that-is-executed-at-the-bottom-of-every-page" />
		</p>
	</liferay-frontend:fieldset>
</c:if>