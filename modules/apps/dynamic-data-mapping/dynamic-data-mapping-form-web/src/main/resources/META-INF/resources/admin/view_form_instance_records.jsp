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

PortletURL portletURL = ddmFormViewFormInstanceRecordsDisplayContext.getPortletURL();

renderResponse.setTitle(LanguageUtil.get(request, "form-entries"));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= ddmFormViewFormInstanceRecordsDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionDropdownItems="<%= ddmFormViewFormInstanceRecordsDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= ddmFormViewFormInstanceRecordsDisplayContext.getClearResultsURL() %>"
	componentId="ddmFormInstanceRecordsManagementToolbar"
	disabled="<%= ddmFormViewFormInstanceRecordsDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddmFormViewFormInstanceRecordsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmFormViewFormInstanceRecordsDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= ddmFormViewFormInstanceRecordsDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= ddmFormViewFormInstanceRecordsDisplayContext.getSearchContainerId() %>"
	searchFormName="fm"
	sortingOrder="<%= HtmlUtil.escape(ddmFormViewFormInstanceRecordsDisplayContext.getOrderByType()) %>"
	sortingURL="<%= ddmFormViewFormInstanceRecordsDisplayContext.getSortingURL() %>"
/>

<div class="container-fluid-1280" id="<portlet:namespace />viewEntriesContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="deleteFormInstanceRecordIds" type="hidden" />

		<liferay-ui:search-container
			id="<%= ddmFormViewFormInstanceRecordsDisplayContext.getSearchContainerId() %>"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= ddmFormViewFormInstanceRecordsDisplayContext.getSearch() %>"
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
						name="<%= HtmlUtil.escape(ddmFormViewFormInstanceRecordsDisplayContext.getColumnName(ddmFormField)) %>"
						truncate="<%= true %>"
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
					value="<%= HtmlUtil.escape(PortalUtil.getUserName(formInstanceRecord)) %>"
				/>

				<liferay-ui:search-container-column-jsp
					path="/admin/form_instance_record_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= ddmFormViewFormInstanceRecordsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
				paginate="<%= false %>"
				searchContainer="<%= ddmFormViewFormInstanceRecordsDisplayContext.getSearch() %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<div class="container-fluid-1280">
	<liferay-ui:search-paginator
		searchContainer="<%= ddmFormViewFormInstanceRecordsDisplayContext.getSearch() %>"
	/>
</div>

<%@ include file="/admin/export_form_instance.jspf" %>

<aui:script sandbox="<%= true %>">
	var deleteRecords = function() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			var searchContainer = document.getElementById(
				'<portlet:namespace />ddmFormInstanceRecord'
			);

			if (searchContainer) {
				Liferay.Util.postForm(
					document.<portlet:namespace />searchContainerForm,
					{
						data: {
							deleteFormInstanceRecordIds: Liferay.Util.listCheckedExcept(
								searchContainer,
								'<portlet:namespace />allRowIds'
							)
						},

						<portlet:actionURL name="deleteFormInstanceRecord" var="deleteFormInstanceRecordURL">
							<portlet:param name="mvcPath" value="/admin/view_form_instance_records.jsp" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:actionURL>

						url: '<%= deleteFormInstanceRecordURL %>'
					}
				);
			}
		}
	};

	var ACTIONS = {
		deleteRecords: deleteRecords
	};

	Liferay.componentReady('ddmFormInstanceRecordsManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on(['actionItemClicked'], function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>