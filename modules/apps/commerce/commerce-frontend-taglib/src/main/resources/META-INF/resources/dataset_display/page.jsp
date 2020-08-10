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

<%@ include file="/dataset_display/init.jsp" %>

<div class="table-root" id="<%= containerId %>">
	<span aria-hidden="true" class="loading-animation my-7"></span>
</div>

<aui:script require="commerce-frontend-js/components/dataset_display/entry as datasetDisplay">
	datasetDisplay.default('<%= containerId %>', '<%= containerId %>', {
		views: <%= jsonSerializer.serializeDeep(clayDataSetDisplayViewsContext) %>,
		apiUrl: '<%= dataSetAPI %>',
		bulkActions: <%= jsonSerializer.serializeDeep(bulkActions) %>,
		creationMenuItems: <%= jsonSerializer.serializeDeep(clayCreationMenu.getClayCreationMenuActionItems()) %>,
		currentUrl: '<%= currentURL %>',
		formId: '<%= formId %>',
		dataProviderKey: '<%= dataProviderKey %>',
		id: '<%= id %>',

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

		showPagination: <%= showPagination %>,
		showManagementBar: <%= showManagementBar %>,
		showSearch: <%= showSearch %>,
		pagination: {
			deltas: <%= jsonSerializer.serializeDeep(paginationEntries) %>,
			initialDelta: <%= itemsPerPage %>,
			initialPageNumber: <%= pageNumber %>
		},
		portletId: '<%= portletDisplay.getRootPortletId() %>',
		namespace: '<%= namespace %>',
		portletURL: '<%= portletURL %>',
		selectedItems: <%= jsonSerializer.serializeDeep(selectedItems) %>,
		selectedItemsKey: '<%= selectedItemsKey %>',
		selectionType: '<%= selectionType %>',
		spritemap: '<%= spritemap %>',
		style: '<%= style %>'
	});

	document.querySelectorAll('form').forEach(function(form) {
		form.setAttribute('data-senna-off', true);
	});
</aui:script>