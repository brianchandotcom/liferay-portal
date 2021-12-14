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

<%@ include file="/classic_display/init.jsp" %>

<div class="table-root" id="<%= containerId %>">
	<span aria-hidden="true" class="loading-animation my-7"></span>

	<%
	Map<String, Object> props = HashMapBuilder.<String, Object>put(
		"actionParameterName", GetterUtil.getString(actionParameterName)
	).put(
		"activeViewSettings", activeViewSettingsJSON
	).put(
		"apiURL", apiURL
	).put(
		"appURL", appURL
	).put(
		"bulkActions", bulkActionDropdownItems
	).put(
		"creationMenu", creationMenu
	).put(
		"currentURL", PortalUtil.getCurrentURL(request)
	).put(
		"dataProviderKey", dataProviderKey
	).build();

	if (Validator.isNotNull(formId)) {
		props.put("formId", formId);
	}

	if (Validator.isNotNull(formName)) {
		props.put("formName", formName);
	}

	props.put("id", id);
	props.put("namespace", namespace);

	if (Validator.isNotNull(nestedItemsKey)) {
		props.put("nestedItemsKey", nestedItemsKey);
	}

	if (Validator.isNotNull(nestedItemsReferenceKey)) {
		props.put("nestedItemsReferenceKey", nestedItemsReferenceKey);
	}

	props.put(
		"pagination",
		HashMapBuilder.<String, Object>put(
			"deltas", fdsPaginationEntries
		).put(
			"initialDelta", itemsPerPage
		).put(
			"initialPageNumber", pageNumber
		).build());
	props.put("portletId", portletDisplay.getRootPortletId());
	props.put("portletURL", portletURL.toString());
	props.put("selectedItems", selectedItems);

	if (Validator.isNotNull(selectedItemsKey)) {
		props.put("selectedItemsKey", selectedItemsKey);
	}

	if (Validator.isNotNull(selectionType)) {
		props.put("selectionType", selectionType);
	}

	props.put("showManagementBar", showManagementBar);
	props.put("showPagination", showPagination);
	props.put("showSearch", showSearch);
	props.put("sorting", fdsSortItemList);

	if (Validator.isNotNull(style)) {
		props.put("style", style);
	}

	props.put("views", dataSetDisplayViewsContext);
	%>

	<react:component
		module="js/FDSTag"
		props="<%= props %>"
	/>
</div>