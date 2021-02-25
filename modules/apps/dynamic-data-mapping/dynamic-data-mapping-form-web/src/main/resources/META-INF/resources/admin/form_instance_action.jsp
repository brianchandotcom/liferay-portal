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

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMFormInstance formInstance = (DDMFormInstance)row.getObject();

FormInstancePermissionCheckerHelper formInstancePermissionCheckerHelper = ddmFormAdminDisplayContext.getPermissionCheckerHelper();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= formInstancePermissionCheckerHelper.isShowDeleteIcon(formInstance) %>">
		<portlet:actionURL name="/dynamic_data_mapping_form/delete_form_instance" var="deleteURL">
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>

	<%
	boolean valid = ddmFormAdminDisplayContext.hasValidDDMFormFields(formInstance) && ddmFormAdminDisplayContext.hasValidStorageType(formInstance);
	%>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowDuplicateIcon() %>">
		<liferay-portlet:actionURL name="/dynamic_data_mapping_form/copy_form_instance" var="copyFormInstanceURL">
			<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			cssClass='<%= !valid ? "disabled" : "" %>'
			message="duplicate"
			url="<%= copyFormInstanceURL %>"
		/>
	</c:if>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowEditIcon(formInstance) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/admin/edit_form_instance" />
			<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			cssClass='<%= !valid ? "disabled" : "" %>'
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowExportIcon(formInstance) %>">
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/dynamic_data_mapping_form/export_form_instance" var="exportFormInstanceURL">
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
		</liferay-portlet:resourceURL>

		<%
		StringBundler sb = new StringBundler(5);

		sb.append("javascript:");
		sb.append(liferayPortletResponse.getNamespace());
		sb.append("exportFormInstance('");
		sb.append(exportFormInstanceURL);
		sb.append("');");
		%>

		<liferay-ui:icon
			cssClass='<%= !valid ? "disabled" : "" %>'
			message="export"
			url="<%= sb.toString() %>"
		/>
	</c:if>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowPermissionsIcon(formInstance) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMFormInstance.class.getName() %>"
			modelResourceDescription="<%= formInstance.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(formInstance.getFormInstanceId()) %>"
			var="permissionsFormInstanceURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsFormInstanceURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowShareIcon(formInstance) && ddmFormAdminDisplayContext.isFormPublished(formInstance) %>">
		<liferay-ui:icon
			cssClass='<%= !valid ? "disabled" : "" %>'
			message="share"
			onClick='<%= "Liferay.fire('" + liferayPortletResponse.getNamespace() + "openShareFormModal', { localizedName:" + ddmFormAdminDisplayContext.getFormLocalizedNameJSONObject(formInstance) + " , shareFormInstanceURL:'" + ddmFormAdminDisplayContext.getShareFormInstanceURL(formInstance) + "' , url:'" + ddmFormAdminDisplayContext.getPublishedFormURL(formInstance) + "' , node: this});" %>'
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowViewEntriesIcon(formInstance) %>">
		<portlet:renderURL var="viewEntriesURL">
			<portlet:param name="mvcPath" value="/admin/view_form_instance_records.jsp" />
			<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			cssClass='<%= !valid ? "disabled" : "" %>'
			message="view-entries"
			url="<%= viewEntriesURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>