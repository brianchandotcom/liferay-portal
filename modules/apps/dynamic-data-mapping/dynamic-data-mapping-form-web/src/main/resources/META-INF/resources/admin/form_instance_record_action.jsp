<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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