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

request.setAttribute("view_suggestions.jsp-resultRowSplitter", kbCommentResultRowSplitter);
request.setAttribute("view_suggestions.jsp-searchContainer", kbCommentsSearchContainer);

List<KBComment> kbComments = kbCommentsSearchContainer.getResults();
%>

<liferay-util:include page="/admin/common/top_tabs.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar
	disabled="<%= kbComments.isEmpty() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="kbComments"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="descriptive"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>

		<%
		PortletURL navigationURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

		navigationURL.setParameter("storeOrderByPreference", Boolean.FALSE.toString());
		%>

		<liferay-frontend:management-bar-navigation
			disabled="<%= false %>"
			navigationKeys='<%= new String[] {"all", "new", "in-progress", "resolved"} %>'
			portletURL="<%= navigationURL %>"
		/>

		<%
		Map<String, String> orderColumns = new HashMap<String, String>();

		if (navigation.equals("all")) {
			orderColumns.put("status", "status");
		}

		orderColumns.put("modified-date", "modified-date");
		orderColumns.put("user-name", "user-name");

		PortletURL sortURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

		sortURL.setParameter("storeOrderByPreference", Boolean.TRUE.toString());
		%>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns="<%= orderColumns %>"
			portletURL="<%= sortURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteKBComments();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:success key="suggestionDeleted" message="suggestion-deleted-successfully" />

	<liferay-ui:success key="suggestionsDeleted" message="suggestions-deleted-successfully" />

	<liferay-ui:success key="suggestionStatusUpdated" message="suggestion-status-updated-successfully" />

	<liferay-ui:success key="suggestionSaved" message="suggestion-saved-successfully" />

	<liferay-util:include page="/admin/common/view_suggestions_by_status.jsp" servletContext="<%= application %>" />
</div>

<aui:script>
	function <portlet:namespace />deleteKBComments() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}
</aui:script>