<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewBlacklistedEntriesManagementToolbarDisplayContext viewBlackListedEntriesManagementToolbarDisplayContext = (ViewBlacklistedEntriesManagementToolbarDisplayContext)request.getAttribute(KeywordIndexWebKeys.BLACKLISTED_ENTRIES_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT);

ViewKeywordEntriesDisplayContext viewKeywordEntriesDisplayContext = (ViewKeywordEntriesDisplayContext)request.getAttribute(KeywordIndexWebKeys.VIEW_KEYWORD_ENTRIES_DISPLAY_CONTEXT);
%>

<clay:management-toolbar-v2
	displayContext="<%= viewBlackListedEntriesManagementToolbarDisplayContext %>"
	searchContainerId="blackListedEntries"
	supportsBulkActions="<%= true %>"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			cssClass="blueprints-search-container"
			id="blackListedEntries"
			searchContainer="<%= viewKeywordEntriesDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry"
				keyProperty="keywordEntryId"
				modelVar="entry"
			>
				<%@ include file="/blacklisted_entry_search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= viewKeywordEntriesDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<liferay-frontend:component
	componentId="<%= viewBlackListedEntriesManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/view_blacklisted_entries/ViewBlacklistedEntriesManagementToolbarDefaultEventHandler"
/>

<aui:script sandbox="<%= true %>">
	var submitForm = function (url) {
		var searchContainer = document.getElementById(
			'<portlet:namespace />blackListedEntries'
		);

		if (searchContainer) {
			Liferay.Util.postForm(document.<portlet:namespace />fm, {
				data: {
					actionFormInstanceIds: Liferay.Util.listCheckedExcept(
						searchContainer,
						'<portlet:namespace />allRowIds'
					),
				},
				url: url,
			});
		}
	};

	var deleteEntries = function () {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-selected-entries" />'
			)
		) {
			<portlet:actionURL name="<%=KeywordIndexMVCCommandNames.DELETE_KEYWORD_ENTRY%>" var="deleteEntryURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			submitForm('<%= deleteEntryURL %>');
		}
	};

	var ACTIONS = {
		deleteEntries: deleteEntries,
	};

	Liferay.componentReady('blackListedEntriesManagementToolbar').then(
		(managementToolbar) => {
			managementToolbar.on('actionItemClicked', (event) => {
				var itemData = event.data.item.data;

				if (itemData && itemData.action && ACTIONS[itemData.action]) {
					ACTIONS[itemData.action]();
				}
			});
		}
	);
</aui:script>