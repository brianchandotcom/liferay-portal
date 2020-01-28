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

<%@ include file="/document_library/init.jsp" %>

<%
DLViewFileEntryMetadataSetsDisplayContext
	dLViewFileEntryMetadataSetsDisplayContext = (DLViewFileEntryMetadataSetsDisplayContext)request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_VIEW_FILE_ENTRY_METADATA_SETS_DISPLAY_CONTEXT);

DDMDisplay ddmDisplay = dLViewFileEntryMetadataSetsDisplayContext.getDDMDisplay();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

com.liferay.dynamic.data.mapping.model.DDMStructure structure = (com.liferay.dynamic.data.mapping.model.DDMStructure)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_structure.jsp" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, ActionKeys.VIEW) %>">
		<portlet:renderURL var="manageViewURL">
			<portlet:param name="mvcPath" value="/view_template.jsp" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
			<portlet:param name="resourceClassNameId" value="<%= String.valueOf(structure.getClassNameId()) %>" />
			<portlet:param name="showHeader" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			localizeMessage="<%= false %>"
			message='<%= LanguageUtil.format(request, "manage-x", HtmlUtil.escape(ddmDisplay.getViewTemplatesTitle(null, locale))) %>'
			url="<%= manageViewURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMStructurePermission.getStructureModelResourceName(structure.getClassNameId()) %>"
			modelResourceDescription="<%= structure.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(structure.getStructureId()) %>"
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

	<c:if test="<%= ddmDisplay.isShowAddButton(themeDisplay.getScopeGroup()) && DDMStructurePermission.containsAddDDMStructurePermission(permissionChecker, scopeGroupId, structure.getClassNameId()) %>">
		<portlet:renderURL var="copyURL">
			<portlet:param name="mvcPath" value="/copy_structure.jsp" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="copy"
			url="<%= copyURL %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, structure, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteStructure" var="deleteURL">
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(structure.getStructureId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>