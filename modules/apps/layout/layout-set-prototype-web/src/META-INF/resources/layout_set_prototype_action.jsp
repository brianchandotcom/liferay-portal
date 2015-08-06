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
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)row.getObject();

long layoutSetPrototypeId = layoutSetPrototype.getLayoutSetPrototypeId();

Group group = layoutSetPrototype.getGroup();
%>

<liferay-ui:icon-menu direction="down" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= LayoutSetPrototypePermissionUtil.contains(permissionChecker, layoutSetPrototypeId, ActionKeys.UPDATE) %>">

		<%
		ThemeDisplay siteThemeDisplay = (ThemeDisplay)themeDisplay.clone();

		siteThemeDisplay.setScopeGroupId(group.getGroupId());

		PortletURL siteAdministrationURL = PortalUtil.getSiteAdministrationURL(request, siteThemeDisplay);
		%>

		<c:if test="<%= siteAdministrationURL != null %>">
			<liferay-ui:icon
				iconCssClass="icon-edit"
				message="manage"
				method="get"
				url="<%= siteAdministrationURL.toString() %>"
			/>
		</c:if>

		<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
			<liferay-ui:icon
				iconCssClass="icon-search"
				message="view-pages"
				target="_blank"
				url="<%= group.getDisplayURL(themeDisplay, true) %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= LayoutSetPrototypePermissionUtil.contains(permissionChecker, layoutSetPrototypeId, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= LayoutSetPrototype.class.getName() %>"
			modelResourceDescription="<%= layoutSetPrototype.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(layoutSetPrototypeId) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.EXPORT_IMPORT_LAYOUTS) %>">
		<liferay-portlet:renderURL plid="<%= PortalUtil.getControlPanelPlid(company.getCompanyId()) %>" portletName="<%= PortletKeys.EXPORT_IMPORT %>" var="exportPagesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="exportLayouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
			<portlet:param name="rootNodeName" value="<%= layoutSetPrototype.getName(locale) %>" />
			<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			cssClass="export-layoutset-prototype layoutset-prototype-action"
			iconCssClass="icon-arrow-down"
			message="export"
			method="get"
			url="<%= exportPagesURL %>"
			useDialog="<%= true %>"
		/>

		<liferay-portlet:renderURL plid="<%= PortalUtil.getControlPanelPlid(company.getCompanyId()) %>" portletName="<%= PortletKeys.EXPORT_IMPORT %>" var="importPagesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="importLayouts" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
			<portlet:param name="rootNodeName" value="<%= layoutSetPrototype.getName(locale) %>" />
			<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			cssClass="import-layoutset-prototype layoutset-prototype-action"
			iconCssClass="icon-arrow-up"
			message="import"
			method="get"
			url="<%= importPagesURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= LayoutSetPrototypePermissionUtil.contains(permissionChecker, layoutSetPrototypeId, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteLayoutSetPrototypes" var="deleteLayoutSetPrototypesURL">
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="layoutSetPrototypeIds" value="<%= String.valueOf(layoutSetPrototypeId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteLayoutSetPrototypesURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>