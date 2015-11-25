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

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= LayoutSetPrototypePermissionUtil.contains(permissionChecker, layoutSetPrototypeId, ActionKeys.UPDATE) %>">

		<%
		PortletURL siteAdministrationURL = null;

		PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);

		String portletId = panelCategoryHelper.getFirstPortletId(PanelCategoryKeys.SITE_ADMINISTRATION, permissionChecker, group);

		if (Validator.isNotNull(portletId)) {
			siteAdministrationURL = PortalUtil.getControlPanelPortletURL(request, group, portletId, 0, 0, PortletRequest.RENDER_PHASE);
		}
		%>

		<c:if test="<%= Validator.isNotNull(siteAdministrationURL) %>">
			<liferay-ui:icon
				message="manage"
				method="get"
				url="<%= siteAdministrationURL.toString() %>"
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
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.EXPORT_IMPORT_LAYOUTS) %>">

		<%
		PortletURL exportPagesURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.EXPORT_IMPORT, PortletRequest.RENDER_PHASE);

		exportPagesURL.setParameter("mvcRenderCommandName", "exportLayouts");
		exportPagesURL.setParameter(Constants.CMD, Constants.EXPORT);
		exportPagesURL.setParameter("groupId", String.valueOf(group.getGroupId()));
		exportPagesURL.setParameter("privateLayout", Boolean.TRUE.toString());
		exportPagesURL.setParameter("rootNodeName", layoutSetPrototype.getName(locale));
		exportPagesURL.setParameter("showHeader", Boolean.FALSE.toString());
		exportPagesURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<liferay-ui:icon
			cssClass="export-layoutset-prototype layoutset-prototype-action"
			message="export"
			method="get"
			url="<%= exportPagesURL.toString() %>"
			useDialog="<%= true %>"
		/>

		<%
		PortletURL importPagesURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.EXPORT_IMPORT, PortletRequest.RENDER_PHASE);

		importPagesURL.setParameter("mvcRenderCommandName", "importLayouts");
		importPagesURL.setParameter(Constants.CMD, Constants.IMPORT);
		importPagesURL.setParameter("groupId", String.valueOf(group.getGroupId()));
		importPagesURL.setParameter("privateLayout", Boolean.TRUE.toString());
		importPagesURL.setParameter("rootNodeName", layoutSetPrototype.getName(locale));
		importPagesURL.setParameter("showHeader", Boolean.FALSE.toString());
		importPagesURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<liferay-ui:icon
			cssClass="import-layoutset-prototype layoutset-prototype-action"
			message="import"
			method="get"
			url="<%= importPagesURL.toString() %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= LayoutSetPrototypePermissionUtil.contains(permissionChecker, layoutSetPrototypeId, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteLayoutSetPrototypes" var="deleteLayoutSetPrototypesURL">
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(layoutSetPrototypeId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteLayoutSetPrototypesURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>