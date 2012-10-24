<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

DDLRecord record = (DDLRecord)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD);

long recordId = BeanParamUtil.getLong(record, request, "recordId");

long recordSetId = BeanParamUtil.getLong(record, request, "recordSetId");

long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

String version = ParamUtil.getString(request, "version", DDLRecordConstants.VERSION_DEFAULT);

DDLRecordVersion recordVersion = record.getRecordVersion(version);

boolean editable = ParamUtil.getBoolean(request, "editable");

if (editable) {
	recordVersion = record.getLatestRecordVersion();
}
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	title='view-record'
/>

<c:if test="<%= recordVersion != null %>">
	<aui:model-context bean="<%= recordVersion %>" model="<%= DDLRecordVersion.class %>" />

	<aui:workflow-status model="<%= DDLRecord.class %>" status="<%= recordVersion.getStatus() %>" version="<%= recordVersion.getVersion() %>" />
</c:if>

<aui:fieldset>

	<%
	DDLRecordSet recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(recordSetId);

	DDMStructure ddmStructure = recordSet.getDDMStructure(formDDMTemplateId);

	Fields fields = null;

	if (recordVersion != null) {
		fields = StorageEngineUtil.getFields(recordVersion.getDDMStorageId());
	}
	%>

	<%= DDMXSDUtil.getHTML(pageContext, ddmStructure.getCompleteXsd(), fields, "", true, locale) %>

	<%
	boolean pending = false;

	if (recordVersion != null) {
		pending = recordVersion.isPending();
	}
	%>

	<aui:button-row>
		<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
	</aui:button-row>
</aui:fieldset>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_lists/view_record_set");
portletURL.setParameter("recordSetId", String.valueOf(recordSetId));

DDLRecordSet recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(recordSetId);

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), portletURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "view-record"), currentURL);
%>