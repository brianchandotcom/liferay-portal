<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "all");

KBSuggestionListDisplayContext kbSuggestionListDisplayContext = new KBSuggestionListDisplayContext(request, scopeGroupId);

request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_SUGGESTION_LIST_DISPLAY_CONTEXT, kbSuggestionListDisplayContext);

SearchContainer<KBComment> kbCommentsSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, kbSuggestionListDisplayContext.getEmptyResultsMessage());

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

KBSuggestionListManagementToolbarDisplayContext kbSuggestionListManagementToolbarDisplayContext = new KBSuggestionListManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, kbCommentsSearchContainer);

request.setAttribute("view_kb_suggestions.jsp-kbSuggestionListManagementToolbarDisplayContext", kbSuggestionListManagementToolbarDisplayContext);

request.setAttribute("view_kb_suggestions.jsp-resultRowSplitter", kbCommentResultRowSplitter);
request.setAttribute("view_kb_suggestions.jsp-searchContainer", kbCommentsSearchContainer);
%>

<liferay-util:include page="/admin/common/vertical_menu.jsp" servletContext="<%= application %>" />

<div class="knowledge-base-admin-content">
	<clay:management-toolbar
		actionDropdownItems="<%= kbSuggestionListManagementToolbarDisplayContext.getActionDropdownItems() %>"
		clearResultsURL="<%= kbSuggestionListManagementToolbarDisplayContext.getClearResultsURL() %>"
		disabled="<%= kbSuggestionListManagementToolbarDisplayContext.isDisabled() %>"
		filterDropdownItems="<%= kbSuggestionListManagementToolbarDisplayContext.getFilterDropdownItems() %>"
		filterLabelItems="<%= kbSuggestionListManagementToolbarDisplayContext.getFilterLabelItems() %>"
		itemsTotal="<%= kbSuggestionListManagementToolbarDisplayContext.getTotal() %>"
		orderDropdownItems="<%= kbSuggestionListManagementToolbarDisplayContext.getOrderByDropdownItems() %>"
		propsTransformer="admin/js/SuggestionsManagementToolbarPropsTransformer"
		searchContainerId="kbComments"
		selectable="<%= true %>"
		showSearch="<%= false %>"
		sortingOrder="<%= kbSuggestionListManagementToolbarDisplayContext.getOrderByType() %>"
		sortingURL="<%= String.valueOf(kbSuggestionListManagementToolbarDisplayContext.getSortingURL()) %>"
	/>

	<clay:container-fluid>
		<liferay-ui:success key="suggestionDeleted" message="suggestion-was-deleted-successfully" />

		<liferay-ui:success key="suggestionsDeleted" message="suggestions-were-deleted-successfully" />

		<liferay-ui:success key="suggestionStatusUpdated" message="suggestion-status-was-updated-successfully" />

		<liferay-ui:success key="suggestionSaved" message="suggestion-was-saved-successfully" />

		<liferay-util:include page="/admin/common/view_kb_suggestions_by_status.jsp" servletContext="<%= application %>" />
	</clay:container-fluid>
</div>