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
DDMFormViewFormInstanceRecordsDisplayContext ddmFormViewFormInstanceRecordsDisplayContext = ddmFormAdminDisplayContext.getFormViewRecordsDisplayContext();

DDMFormInstance ddmFormInstance = ddmFormViewFormInstanceRecordsDisplayContext.getDDMFormInstance();

PortletURL searchURL = renderResponse.createRenderURL();

searchURL.setParameter("mvcPath", "/admin/view_form_instance_records.jsp");
searchURL.setParameter("redirect", ParamUtil.getString(request, "redirect"));
searchURL.setParameter("formInstanceId", String.valueOf(ddmFormInstance.getFormInstanceId()));

renderResponse.setTitle(LanguageUtil.get(request, "form-entries"));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= ddmFormViewFormInstanceRecordsDisplayContext.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="ddmFormInstanceRecord"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= searchURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= ddmFormViewFormInstanceRecordsDisplayContext.getOrderByCol() %>"
			orderByType="<%= ddmFormViewFormInstanceRecordsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"modified-date"} %>'
			portletURL="<%= searchURL %>"
		/>

		<li>
			<aui:form action="<%= searchURL %>" method="post" name="fm">
				<liferay-ui:input-search
					autoFocus="<%= true %>"
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteRecords();" %>'
			icon="trash"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />viewEntriesContainer">
	<aui:form action="<%= searchURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="deleteFormInstanceRecordIds" type="hidden" />

		<liferay-ui:search-container
			id="ddmFormInstanceRecord"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= ddmFormViewFormInstanceRecordsDisplayContext.getDDMFormInstanceRecordSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord"
				cssClass="entry-display-style selectable"
				keyProperty="formInstanceRecordId"
				modelVar="formInstanceRecord"
			>

				<%
				DDMFormValues ddmFormValues = ddmFormViewFormInstanceRecordsDisplayContext.getDDMFormValues(formInstanceRecord);

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();

				DDMForm ddmForm = ddmFormValues.getDDMForm();

				Map<String, DDMFormField> ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);

				for (DDMFormField ddmFormField : ddmFormViewFormInstanceRecordsDisplayContext.getDDMFormFields()) {
				%>

					<liferay-ui:search-container-column-text
						name="<%= ddmFormViewFormInstanceRecordsDisplayContext.getColumnName(ddmFormField) %>"
						value="<%= ddmFormViewFormInstanceRecordsDisplayContext.getColumnValue(ddmFormFieldsMap.get(ddmFormField.getName()), ddmFormFieldValuesMap.get(ddmFormField.getName())) %>"
					/>

				<%
				}
				%>

				<liferay-ui:search-container-column-status
					name="status"
					status="<%= ddmFormViewFormInstanceRecordsDisplayContext.getStatus(formInstanceRecord) %>"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					value="<%= formInstanceRecord.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-text
					name="author"
					value="<%= PortalUtil.getUserName(formInstanceRecord) %>"
				/>

				<liferay-ui:search-container-column-jsp
					path="/admin/form_instance_record_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= ddmFormViewFormInstanceRecordsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
				paginate="<%= false %>"
				searchContainer="<%= ddmFormViewFormInstanceRecordsDisplayContext.getDDMFormInstanceRecordSearchContainer() %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<div class="container-fluid-1280">
	<liferay-ui:search-paginator
		searchContainer="<%= ddmFormViewFormInstanceRecordsDisplayContext.getDDMFormInstanceRecordSearchContainer() %>"
	/>
</div>

<%@ include file="/admin/export_form_instance.jspf" %>

<aui:script>
	function <portlet:namespace />deleteRecords() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />searchContainerForm);

			var searchContainer = AUI.$('#<portlet:namespace />ddmFormInstanceRecord', form);

			form.attr('method', 'post');
			form.fm('deleteFormInstanceRecordIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteFormInstanceRecord"><portlet:param name="mvcPath" value="/admin/view_form_instance_records.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}
</aui:script>