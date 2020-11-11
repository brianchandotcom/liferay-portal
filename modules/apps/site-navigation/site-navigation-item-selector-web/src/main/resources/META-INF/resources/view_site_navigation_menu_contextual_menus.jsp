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

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/site-navigation-item-selector-web/css/ContextualMenus.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<clay:container-fluid
	cssClass="contextual-menu-selector p-4"
	id='<%= liferayPortletResponse.getNamespace() + "contextualMenuSelector" %>'
>
	<div class="alert alert-info">
		<liferay-ui:message key="this-will-make-the-menu-show-only-related-pages.-select-here-the-type-of-relationship-of-the-pages-to-display" />
	</div>

	<clay:row
		cssClass="mt-5 text-center"
	>

		<%
		for (JSONObject jsonObject : (Iterable<JSONObject>)siteNavigationMenuContextualMenusItemSelectorViewDisplayContext.getLevelsJSONArray()) {
		%>

			<clay:col
				cssClass="align-items-center d-flex flex-column"
				md="4"
			>
				<clay:button
					cssClass="align-items-center contextual-menu-option contextual-menu-selector d-flex justify-content-center"
					data-contextual-menu='<%= jsonObject.getString("value") %>'
					data-title='<%= jsonObject.getString("title") %>'
					displayType="unstyled"
				>
					<img alt="<%= jsonObject.getString("title") %>" class="contextual-menu-image p-5" src="<%= jsonObject.getString("imageURL") %>" />
				</clay:button>

				<p class="font-weight-bold mb-2 mt-3">
					<%= jsonObject.getString("title") %>
				</p>

				<p class="text-secondary">
					<%= jsonObject.getString("description") %>
				</p>
			</clay:col>

		<%
		}
		%>

	</clay:row>
</clay:container-fluid>

<liferay-frontend:component
	componentId="SelectEntityHandler"
	context="<%= siteNavigationMenuContextualMenusItemSelectorViewDisplayContext.getContext(liferayPortletResponse) %>"
	module="js/SelectEntityHandler"
/>