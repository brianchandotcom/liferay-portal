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