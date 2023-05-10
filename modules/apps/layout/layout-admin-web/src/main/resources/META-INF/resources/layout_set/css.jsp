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
LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);

LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();
%>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="theme-css-client-extension"
>
	<react:component
		module="js/ThemeCSSReplacementSelector"
		props="<%= layoutsAdminDisplayContext.getThemeCSSReplacementSelectorProps() %>"
	/>
</liferay-frontend:fieldset>

<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-166479") %>'>
	<liferay-frontend:fieldset
		collapsed="<%= false %>"
		collapsible="<%= true %>"
		label="theme-spritemap-client-extension"
	>
		<react:component
			module="js/layout/look_and_feel/ThemeSpritemapCETsConfiguration"
			props="<%= layoutLookAndFeelDisplayContext.getThemeSpritemapCETConfigurationProps(LayoutSet.class.getName(), selLayoutSet.getLayoutSetId()) %>"
		/>
	</liferay-frontend:fieldset>
</c:if>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="css-client-extensions"
>
	<react:component
		module="js/layout/look_and_feel/GlobalCSSCETsConfiguration"
		props="<%= layoutLookAndFeelDisplayContext.getGlobalCSSCETsConfigurationProps(LayoutSet.class.getName(), selLayoutSet.getLayoutSetId()) %>"
	/>
</liferay-frontend:fieldset>

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="custom-css"
>
	<aui:input label="css" name="regularCss" type="textarea" value="<%= selLayoutSet.getCss() %>" wrapperCssClass="mb-0 mt-4" />

	<p class="text-secondary">
		<liferay-ui:message key="this-css-is-loaded-after-the-theme" />
	</p>
</liferay-frontend:fieldset>