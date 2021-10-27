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
			actionParameterName: '<%= GetterUtil.getString(actionParameterName) %>',
			activeViewSettings: <%= activeViewSettingsJSON %>,
			apiURL: '<%= apiURL %>',
			appURL: '<%= appURL %>',
			bulkActions: <%= jsonSerializer.serializeDeep(bulkActionDropdownItems) %>,
			creationMenu: <%= jsonSerializer.serializeDeep(creationMenu) %>,
			currentURL: '<%= PortalUtil.getCurrentURL(request) %>',
			filters: <%= jsonSerializer.serializeDeep(clayDataSetFiltersContext) %>,

			<c:if test="<%= Validator.isNotNull(formId) %>">
				formId: '<%= formId %>',
			</c:if>

			<c:if test="<%= Validator.isNotNull(formName) %>">
				formName: '<%= formName %>',
			</c:if>

			id: '<%= id %>',
			itemsActions: <%= jsonSerializer.serializeDeep(clayDataSetActionDropdownItems) %>,
			namespace: '<%= namespace %>',

			<c:if test="<%= Validator.isNotNull(nestedItemsKey) %>">
				nestedItemsKey: '<%= nestedItemsKey %>',
			</c:if>

			<c:if test="<%= Validator.isNotNull(nestedItemsReferenceKey) %>">
				nestedItemsReferenceKey: '<%= nestedItemsReferenceKey %>',
			</c:if>

			pagination: {
				deltas: <%= jsonSerializer.serializeDeep(clayPaginationEntries) %>,
				initialDelta: <%= itemsPerPage %>,
				initialPageNumber: <%= pageNumber %>,
			},
			portletId: '<%= portletDisplay.getRootPortletId() %>',
			portletURL: '<%= portletURL %>',
			selectedItems: <%= jsonSerializer.serializeDeep(selectedItems) %>,

			<c:if test="<%= Validator.isNotNull(selectedItemsKey) %>">
				selectedItemsKey: '<%= selectedItemsKey %>',
			</c:if>

			<c:if test="<%= Validator.isNotNull(selectionType) %>">
				selectionType: '<%= selectionType %>',
			</c:if>

			showManagementBar: <%= showManagementBar %>,
			showPagination: <%= showPagination %>,
			showSearch: <%= showSearch %>,
			sorting: <%= jsonSerializer.serializeDeep(sortItemList) %>,

			<c:if test="<%= Validator.isNotNull(style) %>">
				style: '<%= style %>',
			</c:if>

			views: <%= jsonSerializer.serializeDeep(clayDataSetDisplayViewsContext) %>,
		},
		document.getElementById('<%= containerId %>')
	);
</aui:script>