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

DDMFormInstanceRecord formInstanceRecord = (DDMFormInstanceRecord)row.getObject();

FormInstancePermissionCheckerHelper formInstancePermissionCheckerHelper = ddmFormAdminDisplayContext.getPermissionCheckerHelper();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= formInstancePermissionCheckerHelper.isShowViewEntriesIcon(ddmFormAdminDisplayContext.getDDMFormInstance()) %>">
		<portlet:renderURL var="viewURL">
			<portlet:param name="mvcPath" value="/admin/view_form_instance_record.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="formInstanceRecordId" value="<%= String.valueOf(formInstanceRecord.getFormInstanceRecordId()) %>" />
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstanceRecord.getFormInstanceId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="view"
			url="<%= viewURL %>"
		/>
	</c:if>

	<c:if test="<%= !DDMFormInstanceExpirationStatusUtil.isFormExpired(formInstanceRecord.getFormInstance(), timeZone) && formInstancePermissionCheckerHelper.isShowEditIcon(ddmFormAdminDisplayContext.getDDMFormInstance()) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/admin/edit_form_instance_record.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="formInstanceRecordId" value="<%= String.valueOf(formInstanceRecord.getFormInstanceRecordId()) %>" />
			<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstanceRecord.getFormInstanceId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= formInstancePermissionCheckerHelper.isShowDeleteIcon(ddmFormAdminDisplayContext.getDDMFormInstance()) %>">
		<portlet:actionURL name="/dynamic_data_mapping_form/delete_form_instance_record" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="formInstanceRecordId" value="<%= String.valueOf(formInstanceRecord.getFormInstanceRecordId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>