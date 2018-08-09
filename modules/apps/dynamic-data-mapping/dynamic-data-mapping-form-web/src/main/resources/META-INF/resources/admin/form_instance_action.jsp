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
	<c:if test="<%= formInstancePermissionCheckerHelper.isShowCopyURLIcon(formInstance) && ddmFormAdminDisplayContext.isFormPublished(formInstance) %>">
		<liferay-ui:icon
			message="copy-url"
			onClick='<%= "Liferay.fire('" + renderResponse.getNamespace() + "copyFormURL', { url:'" + ddmFormAdminDisplayContext.getPublishedFormURL(formInstance) + "' , node: this});" %>'
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
			message="view-entries"
			url="<%= viewEntriesURL %>"
		/>
	</c:if>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowEditIcon(formInstance) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/admin/edit_form_instance" />
			<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowCopyButton() %>">
		<liferay-portlet:actionURL name="copyFormInstance" var="copyFormInstanceURL">
			<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			message="duplicate"
			url="<%= copyFormInstanceURL %>"
		/>
	</c:if>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowExportIcon(formInstance) %>">
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="exportFormInstance" var="exportFormInstanceURL">
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
		</liferay-portlet:resourceURL>

		<%
		StringBundler sb = new StringBundler(6);

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append("exportFormInstance");
		sb.append("('");
		sb.append(exportFormInstanceURL);
		sb.append("');");
		%>

		<liferay-ui:icon
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

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowDeleteIcon(formInstance) %>">
		<portlet:actionURL name="deleteFormInstance" var="deleteURL">
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>