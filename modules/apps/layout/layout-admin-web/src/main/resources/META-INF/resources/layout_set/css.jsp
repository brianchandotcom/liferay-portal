<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);

LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();
%>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="theme-css-client-extension"
>
	<react:component
		module="{ThemeCSSReplacementSelector} from layout-admin-web"
		props="<%= layoutsAdminDisplayContext.getThemeCSSReplacementSelectorProps() %>"
	/>
</liferay-frontend:fieldset>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="css-client-extensions"
>
	<react:component
		module="{GlobalCSSCETsConfiguration} from layout-admin-web"
		props="<%= layoutLookAndFeelDisplayContext.getGlobalCSSCETsConfigurationProps(LayoutSet.class.getName(), selLayoutSet.getLayoutSetId()) %>"
	/>
</liferay-frontend:fieldset>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="theme-spritemap-client-extension"
>
	<react:component
		module="{ThemeSpritemapCETsConfiguration} from layout-admin-web"
		props="<%= layoutLookAndFeelDisplayContext.getThemeSpritemapCETConfigurationProps(LayoutSet.class.getName(), selLayoutSet.getLayoutSetId()) %>"
	/>
</liferay-frontend:fieldset>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="custom-css"
>
	<aui:input label="css" name="regularCss" type="textarea" value="<%= selLayoutSet.getCss() %>" wrapperCssClass="c-mb-0 c-mt-4" />

	<p class="text-secondary">
		<liferay-ui:message key="this-css-is-loaded-after-the-theme" />
	</p>
</liferay-frontend:fieldset>