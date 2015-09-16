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

String title = LanguageUtil.format(request, "view-x", ddmStructure.getName(locale), false);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<div class="container-fluid-1280">
	<c:if test="<%= recordVersion != null %>">
		<aui:model-context bean="<%= recordVersion %>" model="<%= DDLRecordVersion.class %>" />

		<aui:workflow-status model="<%= DDLRecord.class %>" status="<%= recordVersion.getStatus() %>" version="<%= recordVersion.getVersion() %>" />
	</c:if>

	<aui:fieldset>

		<%
		DDMFormValues ddmFormValues = null;

		if (recordVersion != null) {
			ddmFormValues = StorageEngineUtil.getDDMFormValues(recordVersion.getDDMStorageId());
		}

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);
		long classPK = ddmStructure.getPrimaryKey();

		if (formDDMTemplateId > 0) {
			classNameId = PortalUtil.getClassNameId(DDMTemplate.class);
			classPK = formDDMTemplateId;
		}
		%>

		<liferay-ddm:html
			classNameId="<%= classNameId %>"
			classPK="<%= classPK %>"
			ddmFormValues="<%= ddmFormValues %>"
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

</div>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_record_set.jsp");
portletURL.setParameter("recordSetId", String.valueOf(recordSetId));

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), portletURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.format(request, "view-x", ddmStructure.getName(locale), false), currentURL);
%>