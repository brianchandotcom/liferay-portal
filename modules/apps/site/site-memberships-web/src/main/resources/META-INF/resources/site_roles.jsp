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
SiteRolesDisplayContext siteRolesDisplayContext = new SiteRolesDisplayContext(request, renderRequest, renderResponse);
%>

<clay:navigation-bar
	items="<%= siteMembershipsDisplayContext.getSiteRolesNavigationItems() %>"
/>

<liferay-frontend:management-bar
	disabled="<%= siteRolesDisplayContext.isDisabledManagementBar() %>"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= siteRolesDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= siteRolesDisplayContext.getOrderByCol() %>"
			orderByType="<%= siteRolesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"title"} %>'
			portletURL="<%= siteRolesDisplayContext.getPortletURL() %>"
		/>

		<c:if test="<%= siteRolesDisplayContext.isShowSearch() %>">
			<li>
				<aui:form action="<%= siteRolesDisplayContext.getPortletURL() %>" name="searchFm">
					<liferay-ui:input-search
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= siteRolesDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= siteRolesDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280 portlet-site-memberships-assign-site-roles" name="fm">
	<liferay-ui:search-container
		id="siteRoles"
		searchContainer="<%= siteRolesDisplayContext.getRoleSearchSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Role"
			escapedModel="<%= true %>"
			keyProperty="roleId"
			modelVar="role"
		>

			<%
			String displayStyle = siteRolesDisplayContext.getDisplayStyle();
			%>

			<%@ include file="/role_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= siteRolesDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />siteRoles');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(siteRolesDisplayContext.getEventName()) %>',
				{
					data: event.elements.allSelectedElements.getDOMNodes()
				}
			);
		}
	);
</aui:script>