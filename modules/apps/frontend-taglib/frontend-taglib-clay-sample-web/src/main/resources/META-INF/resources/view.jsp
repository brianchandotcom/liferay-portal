<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<TabsItem> tabsItems = claySampleDisplayContext.getTabsItems();
%>

<clay:tabs
	tabsItems="<%= tabsItems %>"
>

	<%
	for (TabsItem tabsItem : tabsItems) {
	%>

		<clay:tabs-panel>
			<liferay-util:include page='<%= "/partials/" + tabsItem.get("panelId") + ".jsp" %>' servletContext="<%= application %>" />
		</clay:tabs-panel>

	<%
	}
	%>

</clay:tabs>