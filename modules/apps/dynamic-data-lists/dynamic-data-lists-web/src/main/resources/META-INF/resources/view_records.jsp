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
long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

DDLViewRecordsDisplayContext ddlViewRecordsDisplayContext = new DDLViewRecordsDisplayContext(liferayPortletRequest, liferayPortletResponse, formDDMTemplateId);

PortletURL portletURL = ddlViewRecordsDisplayContext.getPortletURL();
%>

<c:if test="<%= ddlViewRecordsDisplayContext.isAdminPortlet() %>">
	<clay:navigation-bar
		inverted="<%= true %>"
		navigationItems="<%= ddlViewRecordsDisplayContext.getNavigationItems() %>"
	/>
</c:if>

<clay:management-toolbar
	actionDropdownItems="<%= ddlViewRecordsDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= ddlViewRecordsDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= ddlViewRecordsDisplayContext.getCreationMenu() %>"
	disabled="<%= ddlViewRecordsDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddlViewRecordsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddlViewRecordsDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= ddlViewRecordsDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= ddlViewRecordsDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= ddlViewRecordsDisplayContext.isSelectable() %>"
	sortingOrder="<%= ddlViewRecordsDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddlViewRecordsDisplayContext.getSortingURL() %>"
/>

<div class="container-fluid-1280 view-records-container" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="recordIds" type="hidden" />

		<liferay-ui:search-container
			id="<%= ddlViewRecordsDisplayContext.getSearchContainerId() %>"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= ddlViewRecordsDisplayContext.getSearch() %>"
		>

			<%
			List results = searchContainer.getResults();
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				DDLRecord record = (DDLRecord)results.get(i);
				List<DDMFormField> ddmFormfields = ddlViewRecordsDisplayContext.getDDMFormFields();

				DDLRecordVersion recordVersion = record.getRecordVersion();

				if (ddlViewRecordsDisplayContext.isEditable()) {
					recordVersion = record.getLatestRecordVersion();
				}

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap = ddlViewRecordsDisplayContext.getDDMFormFieldValuesMap(recordVersion);

				ResultRow row = new ResultRow(record, record.getRecordId(), i);

				row.setCssClass("entry-display-style");

				row.setParameter("editable", String.valueOf(ddlViewRecordsDisplayContext.isEditable()));
				row.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));
				row.setParameter("hasDeletePermission", String.valueOf(ddlViewRecordsDisplayContext.hasDeletePermission()));
				row.setParameter("hasUpdatePermission", String.valueOf(ddlViewRecordsDisplayContext.hasUpdatePermission()));

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/view_record.jsp");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("recordId", String.valueOf(record.getRecordId()));
				rowURL.setParameter("version", recordVersion.getVersion());
				rowURL.setParameter("editable", String.valueOf(ddlViewRecordsDisplayContext.isEditable()));
				rowURL.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

				// Columns

				for (DDMFormField ddmFormField : ddmFormfields) {
			%>

					<%@ include file="/record_row_value.jspf" %>

			<%
				}

				if (ddlViewRecordsDisplayContext.hasUpdatePermission()) {
					row.addStatus(recordVersion.getStatus(), recordVersion.getStatusByUserId(), recordVersion.getStatusDate(), rowURL);
					row.addDate(record.getModifiedDate(), rowURL);
					row.addText(HtmlUtil.escape(PortalUtil.getUserName(recordVersion)), rowURL);
				}

				// Action

				row.addJSP("/record_action.jsp", "entry-action", application, request, response);

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator
				displayStyle="<%= ddlViewRecordsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<%@ include file="/export_record_set.jspf" %>

<aui:script>
	AUI().use('liferay-portlet-dynamic-data-lists');

	function <portlet:namespace />deleteRecords() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			var searchContainer = AUI.$('#<portlet:namespace />ddlRecord', form);

			form.attr('method', 'post');
			form.fm('recordIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteRecord"><portlet:param name="mvcPath" value="/view_records.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}
</aui:script>