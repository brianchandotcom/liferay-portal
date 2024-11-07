<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);
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
		props="<%= layoutLookAndFeelDisplayContext.getGlobalCSSCETsConfigurationProps(Layout.class.getName(), selLayout.getPlid()) %>"
	/>
</liferay-frontend:fieldset>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="theme-spritemap-client-extension"
>
	<react:component
		module="{ThemeSpritemapCETsConfiguration} from layout-admin-web"
		props="<%= layoutLookAndFeelDisplayContext.getThemeSpritemapCETConfigurationProps(Layout.class.getName(), selLayout.getPlid()) %>"
	/>
</liferay-frontend:fieldset>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	cssClass='<%= (selLayout.getMasterLayoutPlid() > 0) ? "hide" : StringPool.BLANK %>'
	id='<%= liferayPortletResponse.getNamespace() + "customCSS" %>'
	label="custom-css"
>
	<clay:alert
		cssClass='<%= selLayout.isInheritLookAndFeel() ? StringPool.BLANK : "d-none" %>'
		displayType="info"
		id='<%= liferayPortletResponse.getNamespace() + "regularCssAlert" %>'
		message="custom-css-is-disabled-when-using-the-inherited-theme"
	/>

	<aui:input disabled="<%= selLayout.isInheritLookAndFeel() || layoutsAdminDisplayContext.isReadOnly() %>" label="css" name="regularCss" type="textarea" value="<%= selLayout.getCssText() %>" wrapperCssClass="c-mb-0" />

	<p class="text-secondary">
		<liferay-ui:message key="this-css-is-loaded-after-the-theme" />
	</p>
</liferay-frontend:fieldset>