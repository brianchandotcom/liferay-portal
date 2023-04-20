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
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="customization"
/>

<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>" />

<%
List<TabsItem> tabsItems = layoutLookAndFeelDisplayContext.getTabsItems();
%>

<div class="mt-5">
	<clay:tabs
		tabsItems="<%= tabsItems %>"
	>

		<%
		for (TabsItem tabsItem : tabsItems) {
		%>

			<div>
				<liferay-util:include page='<%= "/layout_set/" + tabsItem.get("panelId") + ".jsp" %>' servletContext="<%= application %>" />
			</div>

		<%
		}
		%>

	</clay:tabs>
</div>