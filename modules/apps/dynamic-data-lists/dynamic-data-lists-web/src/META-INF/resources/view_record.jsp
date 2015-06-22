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
String redirect = ParamUtil.getString(request, "redirect");

DDLRecord record = (DDLRecord)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

long recordId = BeanParamUtil.getLong(record, request, "recordId");

long recordSetId = BeanParamUtil.getLong(record, request, "recordSetId");

DDLRecordSet recordSet = DDLRecordSetServiceUtil.getRecordSet(recordSetId);

boolean editable = ParamUtil.getBoolean(request, "editable", true);
long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

DDMStructure ddmStructure = recordSet.getDDMStructure(formDDMTemplateId);

String version = ParamUtil.getString(request, "version", DDLRecordConstants.VERSION_DEFAULT);

DDLRecordVersion recordVersion = record.getRecordVersion(version);

DDLRecordVersion latestRecordVersion = record.getLatestRecordVersion();
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title='<%= LanguageUtil.format(request, "view-x", ddmStructure.getName(locale), false) %>'
/>

<c:if test="<%= recordVersion != null %>">
	<aui:model-context bean="<%= recordVersion %>" model="<%= DDLRecordVersion.class %>" />

	<aui:workflow-status model="<%= DDLRecord.class %>" status="<%= recordVersion.getStatus() %>" version="<%= recordVersion.getVersion() %>" />
</c:if>

<aui:fieldset>

	<%
	Fields fields = null;

	if (recordVersion != null) {
		DDMFormValues ddmFormValues = StorageEngineUtil.getDDMFormValues(recordVersion.getDDMStorageId());

		fields = DDMFormValuesToFieldsConverterUtil.convert(ddmStructure, ddmFormValues);
	}
	%>

	<liferay-ddm:html
		classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
		classPK="<%= ddmStructure.getPrimaryKey() %>"
		fields="<%= fields %>"
		readOnly="<%= true %>"
		requestedLocale="<%= locale %>"
	/>

	<%
	boolean pending = false;

	if (recordVersion != null) {
		pending = recordVersion.isPending();
	}
	%>

	<aui:button-row>
		<c:if test="<%= editable && DDLRecordSetPermission.contains(permissionChecker, record.getRecordSet(), ActionKeys.UPDATE) && version.equals(latestRecordVersion.getVersion()) %>">
			<portlet:renderURL var="editRecordURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
				<portlet:param name="mvcPath" value="/edit_record.jsp" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
				<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
			</portlet:renderURL>

			<aui:button href="<%= editRecordURL %>" name="edit" value="edit" />
		</c:if>

		<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
	</aui:button-row>
</aui:fieldset>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_record_set.jsp");
portletURL.setParameter("recordSetId", String.valueOf(recordSetId));

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), portletURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.format(request, "view-x", ddmStructure.getName(locale), false), currentURL);
%>