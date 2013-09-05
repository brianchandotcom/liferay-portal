<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructure structure = (DDMStructure)row.getObject();
%>

<liferay-ui:icon-menu showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">
	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<%
	String editStructureDefaultValuesURL = ddmDisplay.getEditStructureDefaultValuesURL(liferayPortletRequest, liferayPortletResponse, structure, currentURL, currentURL);
	%>

	<c:if test="<%= Validator.isNotNull(editStructureDefaultValuesURL) && DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.UPDATE) %>">
		<liferay-ui:icon
			image="edit"
			message="edit-default-values"
			url="<%= editStructureDefaultValuesURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.VIEW) && showManageTemplates %>">
		<portlet:renderURL var="manageViewURL">
			<portlet:param name="struts_action" value="/dynamic_data_mapping/view_template" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="view"
			message="manage-templates"
			url="<%= manageViewURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMStructure.class.getName() %>"
			modelResourceDescription="<%= structure.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(structure.getStructureId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMPermission.contains(permissionChecker, scopeGroupId, ddmDisplay.getResourceName(), ddmDisplay.getAddStructureActionId()) %>">
		<portlet:renderURL var="copyURL">
			<portlet:param name="closeRedirect" value="<%= HttpUtil.encodeURL(currentURL) %>" />
			<portlet:param name="struts_action" value="/dynamic_data_mapping/copy_structure" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
		</portlet:renderURL>

		<%
		StringBundler sb = new StringBundler(6);

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append("copyStructure");
		sb.append("('");
		sb.append(copyURL);
		sb.append("');");
		%>

		<liferay-ui:icon
			image="copy"
			url="<%= sb.toString() %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>