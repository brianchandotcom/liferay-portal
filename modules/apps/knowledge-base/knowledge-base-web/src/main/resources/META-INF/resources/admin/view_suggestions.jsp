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
String navigation = ParamUtil.getString(request, "navigation", "all");

KBSuggestionListDisplayContext kbSuggestionListDisplayContext = new KBSuggestionListDisplayContext(request, templatePath, scopeGroupId);

request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_SUGGESTION_LIST_DISPLAY_CONTEXT, kbSuggestionListDisplayContext);

SearchContainer kbCommentsSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, kbSuggestionListDisplayContext.getEmptyResultsMessage());

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

boolean storeOrderByPreference = ParamUtil.getBoolean(request, "storeOrderByPreference", true);

if (storeOrderByPreference && Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "suggestions-order-by-col", orderByCol);
	portalPreferences.setValue(KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "suggestions-order-by-type", orderByType);
}

if (Validator.isNull(orderByCol) || Validator.isNull(orderByType)) {
	orderByCol = portalPreferences.getValue(KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "suggestions-order-by-col", "status");
	orderByType = portalPreferences.getValue(KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "suggestions-order-by-type", "desc");
}

if (!navigation.equals("all") && orderByCol.equals("status")) {
	orderByCol = "modified-date";
}

kbCommentsSearchContainer.setOrderByCol(orderByCol);
kbCommentsSearchContainer.setOrderByType(orderByType);

KBCommentResultRowSplitter kbCommentResultRowSplitter = orderByCol.equals("status") ? new KBCommentResultRowSplitter(kbSuggestionListDisplayContext, resourceBundle, orderByType) : null;

kbSuggestionListDisplayContext.populateResultsAndTotal(kbCommentsSearchContainer);

kbCommentsSearchContainer.setRowChecker(new KBCommentsChecker(liferayPortletRequest, liferayPortletResponse));

KBSuggestionListManagementToolbarDisplayContext kbSuggestionListManagementToolbarDisplayContext = new KBSuggestionListManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, kbCommentsSearchContainer);

request.setAttribute("view_suggestions.jsp-kbSuggestionListManagementToolbarDisplayContext", kbSuggestionListManagementToolbarDisplayContext);
request.setAttribute("view_suggestions.jsp-resultRowSplitter", kbCommentResultRowSplitter);
request.setAttribute("view_suggestions.jsp-searchContainer", kbCommentsSearchContainer);

List<KBComment> kbComments = kbCommentsSearchContainer.getResults();
%>

<liferay-util:include page="/admin/common/top_tabs.jsp" servletContext="<%= application %>" />

<clay:management-toolbar
	actionDropdownItems="<%= kbSuggestionListManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= kbSuggestionListManagementToolbarDisplayContext.getClearResultsURL() %>"
	componentId="kbSuggestionListManagementToolbar"
	disabled="<%= kbSuggestionListManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= kbSuggestionListManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= kbSuggestionListManagementToolbarDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= kbSuggestionListManagementToolbarDisplayContext.getTotal() %>"
	searchContainerId="kbComments"
	selectable="<%= true %>"
	showSearch="false"
	sortingOrder="<%= kbSuggestionListManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(kbSuggestionListManagementToolbarDisplayContext.getSortingURL()) %>"
/>

<div class="container-fluid-1280">
	<liferay-ui:success key="suggestionDeleted" message="suggestion-deleted-successfully" />

	<liferay-ui:success key="suggestionsDeleted" message="suggestions-deleted-successfully" />

	<liferay-ui:success key="suggestionStatusUpdated" message="suggestion-status-updated-successfully" />

	<liferay-ui:success key="suggestionSaved" message="suggestion-saved-successfully" />

	<liferay-util:include page="/admin/common/view_suggestions_by_status.jsp" servletContext="<%= application %>" />
</div>

<aui:script>
	var deleteKBComments = function() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />'
			)
		) {
			var form = document.getElementById('<portlet:namespace />fm');

			if (form) {
				submitForm(form);
			}
		}
	};

	var ACTIONS = {
		deleteKBComments: deleteKBComments
	};

	Liferay.componentReady('kbSuggestionListManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>