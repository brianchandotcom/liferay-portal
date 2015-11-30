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

LayoutPrototype layoutPrototype = (LayoutPrototype)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= LayoutPrototypePermissionUtil.contains(permissionChecker, layoutPrototype.getLayoutPrototypeId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_layout_prototype.jsp" />
			<portlet:param name="layoutPrototypeId" value="<%= String.valueOf(layoutPrototype.getLayoutPrototypeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPrototypePermissionUtil.contains(permissionChecker, layoutPrototype.getLayoutPrototypeId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= LayoutPrototype.class.getName() %>"
			modelResourceDescription="<%= layoutPrototype.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(layoutPrototype.getLayoutPrototypeId()) %>"
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

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, layoutPrototype.getGroup(), ActionKeys.EXPORT_IMPORT_LAYOUTS) %>">

		<%
		PortletURL exportURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.EXPORT_IMPORT, PortletRequest.RENDER_PHASE);

		exportURL.setParameter("mvcRenderCommandName", "exportLayouts");
		exportURL.setParameter(Constants.CMD, Constants.EXPORT);
		exportURL.setParameter("groupId", String.valueOf(layoutPrototype.getGroupId()));
		exportURL.setParameter("privateLayout", Boolean.TRUE.toString());
		exportURL.setParameter("rootNodeName", layoutPrototype.getName(locale));
		exportURL.setParameter("showHeader", Boolean.FALSE.toString());
		exportURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<liferay-ui:icon
			cssClass="export-layout-prototype layout-prototype-action"
			message="export"
			method="get"
			url="<%= exportURL.toString() %>"
			useDialog="<%= true %>"
		/>

		<%
		PortletURL importURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.EXPORT_IMPORT, PortletRequest.RENDER_PHASE);

		importURL.setParameter("mvcRenderCommandName", "importLayouts");
		importURL.setParameter(Constants.CMD, Constants.IMPORT);
		importURL.setParameter("groupId", String.valueOf(layoutPrototype.getGroupId()));
		importURL.setParameter("privateLayout", Boolean.TRUE.toString());
		importURL.setParameter("rootNodeName", layoutPrototype.getName(locale));
		importURL.setParameter("showHeader", Boolean.FALSE.toString());
		importURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<liferay-ui:icon
			cssClass="import-layout-prototype layout-prototype-action"
			message="import"
			method="get"
			url="<%= importURL.toString() %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPrototypePermissionUtil.contains(permissionChecker, layoutPrototype.getLayoutPrototypeId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteLayoutPrototypes" var="deleteLayoutPrototypesURL">
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="layoutPrototypeId" value="<%= String.valueOf(layoutPrototype.getLayoutPrototypeId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteLayoutPrototypesURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>