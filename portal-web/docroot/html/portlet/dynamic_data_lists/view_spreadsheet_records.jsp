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
DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

boolean editable = ParamUtil.getBoolean(request, "editable", true);

if (portletName.equals(PortletKeys.DYNAMIC_DATA_LISTS)) {
	editable = true;
}

if (!DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), ActionKeys.ADD_RECORD)) {
	editable = false;
}

DDMStructure ddmStructure = recordSet.getDDMStructure();
%>

<div class="lfr-spreadsheet-container">
	<div id="<portlet:namespace />spreadsheet">
		<div class="yui3-widget yui3-datatable" id="<portlet:namespace />dataTable">
			<div class="yui3-datatable-scrollable yui3-datatable-content" id="<portlet:namespace />dataTableContent"></div>
		</div>
	</div>

	<c:if test="<%= editable %>">
		<div class="lfr-spreadsheet-add-rows-buttons">
			<aui:button inlineField="<%= true %>" name="addRecords" value="add" />

			<aui:select inlineField="<%= true %>" label="more-rows-at-bottom" name="numberOfRecords">
				<aui:option label="1" />
				<aui:option label="5" />
				<aui:option label="10" />
				<aui:option label="20" />
				<aui:option label="50" />
			</aui:select>
		</div>
	</c:if>
</div>

<%@ include file="/html/portlet/dynamic_data_lists/custom_spreadsheet_editors.jspf" %>

<aui:script use="liferay-portlet-dynamic-data-lists">
	var structure = <%= DDMXSDUtil.getJSONArray(ddmStructure.getXsd()) %>;
	var columnset = Liferay.SpreadSheet.buildDataTableColumnset(<%= DDLUtil.getRecordSetJSONArray(recordSet) %>, structure, <%= editable %>);

	var ignoreEmptyRecordsNumericSort = function(recA, recB, field, desc) {
		var a = recA.getValue(field);
		var b = recB.getValue(field);

		return A.ArraySort.compareIgnoreWhiteSpace(
			a,
			b,
			desc,
			function(a, b, desc) {
				var num1 = parseFloat(a);
				var num2 = parseFloat(b);

				var result;

				if (isNaN(num1) || isNaN(num2)) {
					result = A.ArraySort.compare(a, b, desc);
				}
				else {
					result = desc ? (num2 - num1) : (num1 - num2);
				}

				return result;
			}
		);
	};

	var ignoreEmptyRecordsStringSort = function(recA, recB, field, desc) {
		var a = recA.getValue(field);
		var b = recB.getValue(field);

		return A.ArraySort.compareIgnoreWhiteSpace(a, b, desc);
	};

	var numericData = {
		'double': 1,
		integer: 1,
		number: 1
	};

	var keys = A.Array.map(
		columnset,
		function(item, index, collection) {
			if (!item.sortFn) {
				if (numericData[item.dataType]) {
					item.sortFn = ignoreEmptyRecordsNumericSort;
				}
				else {
					item.sortFn = ignoreEmptyRecordsStringSort;
				}
			}

			return item.key;
		}
	);

	<%
	int status = WorkflowConstants.STATUS_APPROVED;

	if (DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.ADD_RECORD)) {
		status = WorkflowConstants.STATUS_ANY;
	}

	List<DDLRecord> records = DDLRecordLocalServiceUtil.getRecords(recordSet.getRecordSetId(), status, 0, 1000, null);
	%>

	var records = <%= DDLUtil.getRecordsJSONArray(records, !editable) %>;

	records.sort(
		function(a, b) {
			return (a.displayIndex - b.displayIndex);
		}
	);

	var recordset = Liferay.SpreadSheet.buildEmptyRecords(<%= Math.max(recordSet.getMinDisplayRows(), records.size()) %>, keys);

	A.Array.each(
		records,
		function(item, index, collection) {
			recordset.splice(item.displayIndex, 0, item);
		}
	);

	var spreadSheet = new Liferay.SpreadSheet(
		{
			boundingBox: '#<portlet:namespace />dataTable',
			columnset: columnset,
			contentBox: '#<portlet:namespace />dataTableContent',
			editEvent: 'dblclick',
			recordset: recordset,
			recordsetId: <%= recordSet.getRecordSetId() %>,
			structure: structure
		}
	).plug(
		A.Plugin.DataTableScroll,
		{
			height: 700,
			width: 900
		}
	).plug(
		A.Plugin.DataTableSelection,
		{
			selectEvent: 'mousedown'
		}
	).plug(A.Plugin.DataTableSort);

	spreadSheet.render('#<portlet:namespace />spreadsheet');

	spreadSheet.get('boundingBox').unselectable();

	<c:if test="<%= editable %>">
		var numberOfRecordsNode = A.one('#<portlet:namespace />numberOfRecords');

		A.one('#<portlet:namespace />addRecords').on(
			'click',
			function(event) {
				var numberOfRecords = parseInt(numberOfRecordsNode.val(), 10) || 0;

				var recordset = spreadSheet.get('recordset');

				spreadSheet.addEmptyRows(numberOfRecords);

				spreadSheet.updateMinDisplayRows(recordset.getLength());
			}
		);
	</c:if>

	window.<portlet:namespace />spreadSheet = spreadSheet;
	window.<portlet:namespace />structure = structure;
</aui:script>