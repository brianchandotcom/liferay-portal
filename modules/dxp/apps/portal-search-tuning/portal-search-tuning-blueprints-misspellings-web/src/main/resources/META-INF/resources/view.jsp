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

<liferay-ui:error key="<%= MisspellingsWebKeys.ERROR %>">
	<liferay-ui:message arguments="<%= SessionErrors.get(liferayPortletRequest, MisspellingsWebKeys.ERROR) %>" key="error-x" />
</liferay-ui:error>

<%
ViewMisspellingSetsManagementToolbarDisplayContext viewMisspellingSetsManagementToolbarDisplayContext = (ViewMisspellingSetsManagementToolbarDisplayContext)request.getAttribute(MisspellingsWebKeys.VIEW_MISSPELLING_SETS_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT);

ViewMisspellingSetsDisplayContext viewMisspellingSetsDisplayContext = (ViewMisspellingSetsDisplayContext)request.getAttribute(MisspellingsWebKeys.VIEW_MISSPELLING_SETS_DISPLAY_CONTEXT);
%>

<clay:management-toolbar-v2
	displayContext="<%= viewMisspellingSetsManagementToolbarDisplayContext %>"
	searchContainerId="misspellingSets"
	supportsBulkActions="<%= true %>"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			cssClass="blueprints-search-container"
			id="misspellingSets"
			searchContainer="<%= viewMisspellingSetsDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet"
				keyProperty="misspellingSetId"
				modelVar="entry"
			>
				<%@ include file="/entry_search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= viewMisspellingSetsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<liferay-frontend:component
	componentId="<%= viewMisspellingSetsManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/view/ViewMisspellingSetsManagementToolbarDefaultEventHandler"
/>

<aui:script sandbox="<%= true %>">
	var submitForm = function (url) {
		var searchContainer = document.getElementById(
			'<portlet:namespace />misspellingSets'
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
			<portlet:actionURL name="<%= MisspellingsMVCCommandNames.DELETE_MISSPELLING_SET %>" var="deleteEntryURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			submitForm('<%= deleteEntryURL %>');
		}
	};

	var ACTIONS = {
		deleteEntries: deleteEntries,
	};

	Liferay.componentReady('misspellingSetsManagementToolbar').then(
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