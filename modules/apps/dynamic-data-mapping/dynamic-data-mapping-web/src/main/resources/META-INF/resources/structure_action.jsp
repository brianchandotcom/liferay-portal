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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructure structure = (DDMStructure)row.getObject();
%>

<liferay-ui:icon-menu direction="down" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">
	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_structure.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<%
	String editStructureDefaultValuesURL = ddmDisplay.getEditStructureDefaultValuesURL(liferayPortletRequest, liferayPortletResponse, structure, currentURL);
	%>

	<c:if test="<%= Validator.isNotNull(editStructureDefaultValuesURL) && DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.UPDATE) %>">
		<liferay-ui:icon
			message="edit-default-values"
			url="<%= editStructureDefaultValuesURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.VIEW) && showManageTemplates %>">
		<portlet:renderURL var="manageViewURL">
			<portlet:param name="mvcPath" value="/view_template.jsp" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
			<portlet:param name="resourceClassNameId" value="<%= String.valueOf(structure.getClassNameId()) %>" />
			<portlet:param name="showHeader" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="manage-templates"
			url="<%= manageViewURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMStructurePermission.getStructureModelResourceName(structure.getClassNameId()) %>"
			modelResourceDescription="<%= structure.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(structure.getStructureId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			message="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.containsAddStruturePermission(permissionChecker, scopeGroupId, structure.getClassNameId()) %>">
		<portlet:renderURL var="copyURL">
			<portlet:param name="mvcPath" value="/copy_structure.jsp" />
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
			message="copy"
			url="<%= sb.toString() %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteStructure" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>