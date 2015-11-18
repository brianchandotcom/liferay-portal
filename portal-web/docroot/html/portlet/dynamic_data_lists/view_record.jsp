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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDLRecord record = (DDLRecord)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD);

long recordId = BeanParamUtil.getLong(record, request, "recordId");

long recordSetId = BeanParamUtil.getLong(record, request, "recordSetId");

DDLRecordSet recordSet = DDLRecordSetServiceUtil.getRecordSet(recordSetId);

long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

DDMStructure ddmStructure = recordSet.getDDMStructure(formDDMTemplateId);

String version = ParamUtil.getString(request, "version", DDLRecordConstants.VERSION_DEFAULT);

DDLRecordVersion recordVersion = record.getRecordVersion(version);

DDLRecordVersion latestRecordVersion = record.getLatestRecordVersion();
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title='<%= LanguageUtil.format(pageContext, "view-x", ddmStructure.getName(locale)) %>'
/>

<c:if test="<%= recordVersion != null %>">
	<aui:model-context bean="<%= recordVersion %>" model="<%= DDLRecordVersion.class %>" />

	<aui:workflow-status model="<%= DDLRecord.class %>" status="<%= recordVersion.getStatus() %>" version="<%= recordVersion.getVersion() %>" />
</c:if>

<aui:fieldset>

	<%
	long classNameId = PortalUtil.getClassNameId(DDMStructure.class);
	long classPK = ddmStructure.getPrimaryKey();

	if (formDDMTemplateId > 0) {
		classNameId = PortalUtil.getClassNameId(DDMTemplate.class);
		classPK = formDDMTemplateId;
	}

	Fields fields = null;

	if (recordVersion != null) {
		fields = StorageEngineUtil.getFields(recordVersion.getDDMStorageId());
	}
	%>

	<liferay-ddm:html
		classNameId="<%= classNameId %>"
		classPK="<%= classPK %>"
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
		<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, record.getRecordSet(), ActionKeys.UPDATE) && version.equals(latestRecordVersion.getVersion()) %>">
			<portlet:renderURL var="editRecordURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
				<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
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

portletURL.setParameter("struts_action", "/dynamic_data_lists/view_record_set");
portletURL.setParameter("recordSetId", String.valueOf(recordSetId));

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), portletURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.format(pageContext, "view-x", ddmStructure.getName(locale)), currentURL);
%>