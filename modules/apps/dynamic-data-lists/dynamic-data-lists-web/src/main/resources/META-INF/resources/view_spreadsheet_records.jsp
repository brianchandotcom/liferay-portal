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
DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

boolean editable = ParamUtil.getBoolean(request, "editable", true);

if (editable || ddlDisplayContext.isAdminPortlet()) {
	editable = DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), DDLActionKeys.ADD_RECORD) && DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), ActionKeys.UPDATE);
}

DDMStructure ddmStructure = recordSet.getDDMStructure();
%>

<div class="container-fluid-1280 lfr-spreadsheet-container">
	<div id="<portlet:namespace />spreadsheet">
		<div class="table-striped yui3-datatable yui3-widget" id="<portlet:namespace />dataTable">
			<div class="yui3-datatable-content yui3-datatable-scrollable" id="<portlet:namespace />dataTableContent"></div>
		</div>
	</div>

	<c:if test="<%= editable %>">
		<div class="lfr-spreadsheet-add-rows-buttons">
			<aui:select inlineField="<%= true %>" label="more-rows-at-bottom" name="numberOfRecords">
				<aui:option label="1" />
				<aui:option label="5" />
				<aui:option label="10" />
				<aui:option label="20" />
				<aui:option label="50" />
			</aui:select>

			<aui:button inlineField="<%= true %>" name="addRecords" value="add" />
		</div>
	</c:if>
</div>

<%@ include file="/custom_spreadsheet_editors.jspf" %>

<aui:script use="liferay-portlet-dynamic-data-lists">
	var structure = <%= DDMUtil.getDDMFormFieldsJSONArray(ddmStructure, ddmStructure.getDefinition()) %>;

	var columns = Liferay.SpreadSheet.buildDataTableColumns(
		<%= ddlDisplayContext.getRecordSetJSONArray(recordSet, locale) %>,
		'<%= LocaleUtil.toLanguageId(locale) %>',
		structure,
		<%= editable %>
	);

	var ignoreEmptyRecordsNumericSort = function(recA, recB, desc, field) {
		var a = recA.get(field);
		var b = recB.get(field);

		return A.ArraySort.compareIgnoreWhiteSpace(a, b, desc, function(
			a,
			b,
			desc
		) {
			var num1 = parseFloat(a);
			var num2 = parseFloat(b);

			var result;

			if (isNaN(num1) || isNaN(num2)) {
				result = A.ArraySort.compare(a, b, desc);
			}
			else {
				result = desc ? num2 - num1 : num1 - num2;
			}

			return result;
		});
	};

	var ignoreEmptyRecordsStringSort = function(recA, recB, desc, field) {
		var a = recA.get(field);
		var b = recB.get(field);

		return A.ArraySort.compareIgnoreWhiteSpace(a, b, desc);
	};

	var numericData = {
		double: 1,
		integer: 1,
		number: 1
	};

	var keys = columns.map(function(item, index) {
		var key = item.key;

		if (!item.sortFn) {
			if (numericData[item.dataType]) {
				item.sortFn = A.rbind(ignoreEmptyRecordsNumericSort, item, key);
			}
			else {
				item.sortFn = A.rbind(ignoreEmptyRecordsStringSort, item, key);
			}
		}

		return key;
	});

	<%
	int status = WorkflowConstants.STATUS_APPROVED;

	if (DDLRecordSetPermission.contains(permissionChecker, recordSet, DDLActionKeys.ADD_RECORD)) {
		status = WorkflowConstants.STATUS_ANY;
	}

	List<DDLRecord> records = DDLRecordLocalServiceUtil.getRecords(recordSet.getRecordSetId(), status, 0, 1000, null);
	%>

	var records = <%= ddlDisplayContext.getRecordsJSONArray(records, !editable, locale) %>;

	records.sort(function(a, b) {
		return a.displayIndex - b.displayIndex;
	});

	var data = Liferay.SpreadSheet.buildEmptyRecords(
		<%= Math.max(recordSet.getMinDisplayRows() - records.size(), 0) %>,
		keys
	);

	records.forEach(function(item, index) {
		data.splice(item.displayIndex, 0, item);
	});

	var spreadSheet = new Liferay.SpreadSheet({
		boundingBox: '#<portlet:namespace />dataTable',
		columns: columns,
		contentBox: '#<portlet:namespace />dataTableContent',
		data: data,
		editEvent: 'dblclick',
		plugins: [
			{
				cfg: {
					highlightRange: false
				},
				fn: A.Plugin.DataTableHighlight
			}
		],
		portletNamespace: '<portlet:namespace />',
		recordsetId: <%= recordSet.getRecordSetId() %>,
		strings: {
			asc: '<liferay-ui:message key="ascending" />',
			desc: '<liferay-ui:message key="descending" />',
			reverseSortBy:
				'<liferay-ui:message arguments="{column}" key="reverse-sort-by-x" />',
			sortBy: '<liferay-ui:message arguments="{column}" key="sort-by-x" />'
		},
		structure: structure,
		width: '100%'
	});

	spreadSheet.render('#<portlet:namespace />spreadsheet');

	spreadSheet.get('boundingBox').unselectable();

	<c:if test="<%= editable %>">
		var numberOfRecordsNode = A.one('#<portlet:namespace />numberOfRecords');

		A.one('#<portlet:namespace />addRecords').on('click', function(event) {
			var numberOfRecords = parseInt(numberOfRecordsNode.val(), 10) || 0;

			spreadSheet.addEmptyRows(numberOfRecords);

			spreadSheet.updateMinDisplayRows(spreadSheet.get('data').size());
		});
	</c:if>

	window.<portlet:namespace />spreadSheet = spreadSheet;
	window.<portlet:namespace />structure = structure;
</aui:script>

<%@ include file="/export_record_set.jspf" %>