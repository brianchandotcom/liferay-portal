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
DDLFormViewRecordsDisplayContext ddlFormViewRecordsDisplayContext = ddlFormAdminDisplayContext.getDDLFormViewRecordsDisplayContext();

DDLRecordSet ddlRecordSet = ddlFormViewRecordsDisplayContext.getDDLRecordSet();

PortletURL searchURL = renderResponse.createRenderURL();

searchURL.setParameter("mvcPath", "/admin/view_records.jsp");
searchURL.setParameter("redirect", ParamUtil.getString(request, "redirect"));
searchURL.setParameter("recordSetId", String.valueOf(ddlRecordSet.getRecordSetId()));

renderResponse.setTitle(LanguageUtil.get(request, "form-entries"));
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="<%= HtmlUtil.escape(ddlRecordSet.getName(locale)) %>" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= searchURL %>" method="post" name="fm">
			<liferay-ui:input-search autoFocus="<%= true %>" markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="ddlRecord"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= searchURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= ddlFormViewRecordsDisplayContext.getOrderByCol() %>"
			orderByType="<%= ddlFormViewRecordsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"modified-date"} %>'
			portletURL="<%= searchURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteRecords();" %>' icon="trash" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />viewEntriesContainer">
	<aui:form action="<%= searchURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="deleteRecordIds" type="hidden" />

		<liferay-ui:search-container
			id="ddlRecord"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= ddlFormViewRecordsDisplayContext.getRecordSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecord"
				cssClass="entry-display-style selectable"
				keyProperty="recordId"
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
	</aui:form>
</div>

<div class="container-fluid-1280">
	<liferay-ui:search-paginator searchContainer="<%= ddlFormViewRecordsDisplayContext.getRecordSearchContainer() %>" />
</div>

<%@ include file="/admin/export_record_set.jspf" %>

<aui:script>
	function <portlet:namespace />deleteRecords() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />searchContainerForm);

			var searchContainer = AUI.$('#<portlet:namespace />ddlRecord', form);

			form.attr('method', 'post');
			form.fm('deleteRecordIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteRecord"><portlet:param name="mvcPath" value="/admin/view_records.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}
</aui:script>