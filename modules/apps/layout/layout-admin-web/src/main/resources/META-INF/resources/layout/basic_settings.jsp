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

<%
LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);
%>

<div>
	<react:component
		module="js/layout/look_and_feel/Favicon"
		props="<%= layoutsAdminDisplayContext.getFaviconButtonProps() %>"
	/>
</div>

<div class="d-flex">
	<c:if test="<%= layoutLookAndFeelDisplayContext.hasEditableMasterLayout() %>">
		<div class="flex-grow-1 mr-4">
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