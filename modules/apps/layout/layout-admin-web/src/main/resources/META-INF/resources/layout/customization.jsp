<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="customization"
/>

<aui:model-context bean="<%= layoutsAdminDisplayContext.getSelLayout() %>" model="<%= Layout.class %>" />

<%
LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);

List<TabsItem> tabsItems = layoutLookAndFeelDisplayContext.getTabsItems();
%>

<div class="sheet-row">
	<clay:tabs
		tabsItems="<%= tabsItems %>"
	>

		<%
		for (TabsItem tabsItem : tabsItems) {
		%>

			<clay:tabs-panel>
				<liferay-util:include page='<%= "/layout/" + tabsItem.get("panelId") + ".jsp" %>' servletContext="<%= application %>" />
			</clay:tabs-panel>

		<%
		}
		%>

	</clay:tabs>
</div>