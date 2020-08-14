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

<%@ include file="/headless_data_set_display/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_step_tracker") + StringPool.UNDERLINE;

String containerId = randomNamespace + "table-id";

JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/frontend-taglib-clay/data_set_display/styles/main.css") %>" rel="stylesheet" />

<div class="table-root" id="<%= containerId %>">
	<span aria-hidden="true" class="loading-animation my-7"></span>
</div>

<aui:script require='<%= module + " as dataSetDisplay" %>'>
	dataSetDisplay.default(
		{
			actionParameterName: '<%= actionParameterName %>',
			activeViewSettings: <%= activeViewSettingsJSON %>,
			apiURL: '<%= apiURL %>',
			appURL: '<%= appURL %>',
			bulkActions: <%= jsonSerializer.serializeDeep(bulkActionDropdownItems) %>,
			creationMenu: <%= jsonSerializer.serializeDeep(creationMenu) %>,
			currentUrl: '<%= PortalUtil.getCurrentURL(request) %>',
			filters: <%= jsonSerializer.serializeDeep(clayDataSetFiltersContext) %>,
			formId: '<%= formId %>',
			id: '<%= id %>',
			itemsActions: <%= jsonSerializer.serializeDeep(clayDataSetActionDropdownItems) %>,
			namespace: '<%= namespace %>',

			<%
			if (Validator.isNotNull(nestedItemsKey)) {
			%>

				nestedItemsKey: '<%= nestedItemsKey %>',

				<%
				}

				if (Validator.isNotNull(nestedItemsReferenceKey)) {
				%>

				nestedItemsReferenceKey: '<%= nestedItemsReferenceKey %>',

			<%
			}
			%>

			pagination: {
				deltas: <%= jsonSerializer.serializeDeep(clayPaginationEntries) %>,
				initialDelta: <%= itemsPerPage %>,
				initialPageNumber: <%= pageNumber %>,
			},
			portletId: '<%= portletDisplay.getRootPortletId() %>',
			portletURL: '<%= portletURL %>',
			selectedItems: <%= jsonSerializer.serializeDeep(selectedItems) %>,
			selectedItemsKey: '<%= selectedItemsKey %>',
			showManagementBar: <%= showManagementBar %>,
			showSearch: <%= showSearch %>,
			style: '<%= style %>',
			selectionType: '<%= selectionType %>',
			showPagination: <%= showPagination %>,
			views: <%= jsonSerializer.serializeDeep(clayDataSetDisplayViewsContext) %>,
		},
		document.getElementById('<%= containerId %>')
	);
</aui:script>