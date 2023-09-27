<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="basic-settings"
/>

<aui:model-context bean="<%= layoutsAdminDisplayContext.getSelLayout() %>" model="<%= Layout.class %>" />

<liferay-ui:error exception="<%= ImageTypeException.class %>" message="please-enter-a-file-with-a-valid-file-type" />

<%
LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);
%>

<div>
	<react:component
		module="js/layout/look_and_feel/Favicon"
		props="<%= layoutsAdminDisplayContext.getFaviconButtonProps() %>"
	/>
</div>

<div class="d-flex mb-4">
	<c:if test="<%= layoutLookAndFeelDisplayContext.hasEditableMasterLayout() %>">
		<div class="c-mr-4 flex-grow-1">
			<react:component
				module="js/layout/look_and_feel/MasterLayoutConfiguration"
				props="<%= layoutLookAndFeelDisplayContext.getMasterLayoutConfigurationProps() %>"
			/>
		</div>
	</c:if>

	<div class="flex-grow-1">
		<react:component
			module="js/layout/look_and_feel/StyleBookConfiguration"
			props="<%= layoutLookAndFeelDisplayContext.getStyleBookConfigurationProps() %>"
		/>
	</div>
</div>

<c:if test="<%= layoutLookAndFeelDisplayContext.isIconSelectorVisible() %>">

	<%
	Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
	%>

	<liferay-frontend:logo-selector
		currentLogoURL='<%= (selLayout.getIconImageId() == 0) ? themeDisplay.getPathThemeImages() + "/spacer.png" : themeDisplay.getPathImage() + "/logo?img_id=" + selLayout.getIconImageId() + "&t=" + WebServerServletTokenUtil.getToken(selLayout.getIconImageId()) %>'
		defaultLogoURL='<%= themeDisplay.getPathThemeImages() + "/spacer.png" %>'
		description='<%= LanguageUtil.get(request, "this-icon-will-be-shown-in-the-navigation-menu") %>'
		disabled="<%= layoutsAdminDisplayContext.isReadOnly() %>"
		label='<%= LanguageUtil.get(request, "icon") %>'
	/>
</c:if>