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
DLViewFileEntryMetadataSetsDisplayContext dLViewFileEntryMetadataSetsDisplayContext = (DLViewFileEntryMetadataSetsDisplayContext)request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_VIEW_FILE_ENTRY_METADATA_SETS_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = (com.liferay.dynamic.data.mapping.model.DDMStructure)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, ddmStructure, ActionKeys.UPDATE) %>">
		<liferay-ui:icon
			message="edit"
			url="<%= String.valueOf(dLViewFileEntryMetadataSetsDisplayContext.getEditDDMStructurePortletURL(ddmStructure)) %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, ddmStructure, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMStructurePermission.getStructureModelResourceName(ddmStructure.getClassNameId()) %>"
			modelResourceDescription="<%= ddmStructure.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(ddmStructure.getStructureId()) %>"
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

	<c:if test="<%= dLViewFileEntryMetadataSetsDisplayContext.isShowAddStructureButton() %>">
		<liferay-ui:icon
			message="copy"
			url="<%= String.valueOf(dLViewFileEntryMetadataSetsDisplayContext.getCopyDDMStructurePortletURL(ddmStructure)) %>"
		/>
	</c:if>

	<c:if test="<%= DDMStructurePermission.contains(permissionChecker, ddmStructure, ActionKeys.DELETE) %>">
		<liferay-ui:icon-delete
			url="<%= String.valueOf(dLViewFileEntryMetadataSetsDisplayContext.getDeleteDDMStructurePortletURL(ddmStructure)) %>"
		/>
	</c:if>
</liferay-ui:icon-menu>