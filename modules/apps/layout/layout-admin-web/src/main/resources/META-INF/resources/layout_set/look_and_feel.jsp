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

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="look-and-feel"
/>

<aui:model-context bean="<%= selLayoutSet %>" model="<%= Layout.class %>" />

<aui:input name="devices" type="hidden" value="regular" />

<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>" />

<div class="mt-5">
	<liferay-util:include page="/look_and_feel_theme_css.jsp" servletContext="<%= application %>" />
</div>

<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-166479")) %>'>
	<clay:sheet-section>
		<h3 class="sheet-subtitle"><liferay-ui:message key="theme-spritemap-client-extension" /></h3>

		<clay:alert
			displayType="info"
			message='<%= LanguageUtil.get(request, "to-add-or-edit-the-existing-spritemap-simply-copy-paste-and-make-changes-as-needed-to-your-registered-extension") %>'
		/>

		<p>
			<liferay-ui:message key="use-this-client-extension-to-fully-replace-the-default-spritemap-contained-in-the-theme" />
		</p>

		<div>
			<react:component
				module="js/layout/look_and_feel/ThemeSpritemapCETsConfiguration"
				props="<%= layoutLookAndFeelDisplayContext.getThemeSpritemapCETConfigurationProps(LayoutSet.class.getName(), selLayoutSet.getLayoutSetId()) %>"
			/>
		</div>
	</clay:sheet-section>
</c:if>

<clay:sheet-section
	cssClass="mt-5"
>
	<react:component
		module="js/layout/look_and_feel/GlobalCSSCETsConfiguration"
		props="<%= layoutLookAndFeelDisplayContext.getGlobalCSSCETsConfigurationProps(LayoutSet.class.getName(), selLayoutSet.getLayoutSetId()) %>"
	/>
</clay:sheet-section>