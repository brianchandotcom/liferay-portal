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
SiteNavigationMenuContextualMenusItemSelectorViewDisplayContext siteNavigationMenuContextualMenusItemSelectorViewDisplayContext = (SiteNavigationMenuContextualMenusItemSelectorViewDisplayContext)request.getAttribute(SiteNavigationItemSelectorWebKeys.SITE_NAVIGATION_MENU_CONTEXTUAL_MENUS_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<div id="<portlet:namespace />contextualMenuSelector">
	<p class="m-4 text-secondary">
		<liferay-ui:message key="this-will-make-the-menu-show-only-related-pages.-select-here-the-type-of-relationship-of-the-pages-to-display" />
	</p>

	<div class="d-flex justify-content-around mt-5 text-center">

		<%
		JSONArray levelsJSONArray = siteNavigationMenuContextualMenusItemSelectorViewDisplayContext.getLevelsJSONArray();

		for (Object o : levelsJSONArray) {
			JSONObject jsonObject = (JSONObject)o;
		%>

			<div>
				<clay:button
					cssClass="btn-unstyled selector-button"
					data-contextual-menu='<%= jsonObject.getString("value") %>'
				>
					<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAJCAYAAAA7KqwyAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAkSURBVHgB7cxBEQAACAIwtH8Pzw52kxD8OBZgNXsPQUOUwCIgAz0DHTyygaAAAAAASUVORK5CYII=" style="height: 150px; width: 250px;" />
				</clay:button>

				<p class="font-weight-bold mt-3">
					<%= jsonObject.getString("title") %>
				</p>

				<p class="text-secondary">
					<%= jsonObject.getString("description") %>
				</p>
			</div>

		<%
		}
		%>

	</div>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />contextualMenuSelector',
		'<%= HtmlUtil.escapeJS(siteNavigationMenuContextualMenusItemSelectorViewDisplayContext.getItemSelectedEventName()) %>'
	);
</aui:script>