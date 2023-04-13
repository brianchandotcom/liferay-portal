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

LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);
%>

<clay:alert
	displayType="info"
	message='<%= LanguageUtil.get(request, "theme-css-client-extension-and-custom-css-are-disabled-when-using-the-inherited-theme") %>'
/>

<liferay-util:include page="/look_and_feel_theme_css.jsp" servletContext="<%= application %>" />

<div class="mb-5">
	<react:component
		module="js/layout/look_and_feel/GlobalCSSCETsConfiguration"
		props="<%= layoutLookAndFeelDisplayContext.getGlobalCSSCETsConfigurationProps(Layout.class.getName(), selLayout.getPlid()) %>"
	/>
</div>

<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-166479") %>'>
	<div class="mb-5">
		<h3 class="sheet-subtitle"><liferay-ui:message key="theme-spritemap-client-extension" /></h3>

		<clay:alert
			displayType="info"
			message='<%= LanguageUtil.get(request, "to-add-or-edit-the-existing-spritemap-simply-copy-paste-and-make-changes-as-needed-to-your-registered-extension") %>'
		/>

		<p class="text-secondary">
			<liferay-ui:message key="use-this-client-extension-to-fully-replace-the-default-spritemap-contained-in-the-theme" />
		</p>

		<div>
			<react:component
				module="js/layout/look_and_feel/ThemeSpritemapCETsConfiguration"
				props="<%= layoutLookAndFeelDisplayContext.getThemeSpritemapCETConfigurationProps(Layout.class.getName(), selLayout.getPlid()) %>"
			/>
		</div>
	</div>
</c:if>

<div class="mb-5">
	<h3 class="sheet-subtitle"><liferay-ui:message key="custom-css" /></h3>

	<aui:input disabled="<%= selLayout.isInheritLookAndFeel() %>" label="css" name="regularCss" type="textarea" value="<%= selLayout.getCssText() %>" wrapperCssClass="mb-0" />

	<p class="text-secondary">
		<liferay-ui:message key="this-css-is-loaded-after-the-theme" />
	</p>
</div>