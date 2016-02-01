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
DDLFormViewRecordsDisplayContext ddlFormViewRecordsDisplayContext = new DDLFormViewRecordsDisplayContext(liferayPortletRequest, liferayPortletResponse, ddlFormAdminDisplayContext.getRecordSet());

DDLRecordSet ddlRecordSet = ddlFormViewRecordsDisplayContext.getDDLRecordSet();

renderResponse.setTitle(LanguageUtil.get(request, "form-entries"));
%>

<portlet:renderURL var="searchURL">
	<portlet:param name="mvcPath" value="/admin/view_records.jsp" />
	<portlet:param name="redirect" value='<%= ParamUtil.getString(request, "redirect") %>' />
	<portlet:param name="recordSetId" value="<%= String.valueOf(ddlRecordSet.getRecordSetId()) %>" />
</portlet:renderURL>

<aui:form action="<%= searchURL %>" method="post" name="fm">
	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<aui:nav-bar-search>
			<liferay-ui:input-search autoFocus="<%= true %>" markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:nav-bar>
</aui:form>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-util:include page="/admin/view_records_sort_button.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />viewEntriesContainer">
	<liferay-ui:search-container searchContainer="<%= ddlFormViewRecordsDisplayContext.getRecordSearchContainer() %>">

		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.lists.model.DDLRecord"
			cssClass="entry-display-style selectable"
			modelVar="record"
		>

			<%
			DDMFormValues ddmFormValues = ddlFormViewRecordsDisplayContext.getDDMFormValues(record);

			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();

			for (DDMFormField ddmFormField : ddlFormViewRecordsDisplayContext.getDDMFormFields()) {
			%>

				<liferay-ui:search-container-column-text
					name="<%= ddlFormViewRecordsDisplayContext.getColumnName(ddmFormField) %>"
					value="<%= ddlFormViewRecordsDisplayContext.getColumnValue(ddmFormField, ddmFormFieldValuesMap.get(ddmFormField.getName())) %>"
				/>

			<%
			}
			%>

			<liferay-ui:search-container-column-status
				name="status"
				status="<%= ddlFormViewRecordsDisplayContext.getStatus(record) %>"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= record.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-text
				name="author"
				value="<%= PortalUtil.getUserName(record) %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/admin/record_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= ddlFormViewRecordsDisplayContext.getDisplayStyle() %>" markupView="lexicon" paginate="<%= false %>" searchContainer="<%= ddlFormViewRecordsDisplayContext.getRecordSearchContainer() %>" />

	</liferay-ui:search-container>
</div>

<div class="container-fluid-1280">
	<liferay-ui:search-paginator searchContainer="<%= ddlFormViewRecordsDisplayContext.getRecordSearchContainer() %>" />
</div>

<%@ include file="/admin/export_record_set.jspf" %>