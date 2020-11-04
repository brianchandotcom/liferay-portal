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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsConstants" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingEntryDisplayContext" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingPortletDisplayContext" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.exception.DuplicateQueryStringException" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<liferay-ui:error embed="<%= false %>" exception="<%= DuplicateQueryStringException.class %>" message="active-search-queries-and-aliases-must-be-unique-across-all-rankings" />

<%
RankingPortletDisplayContext rankingPortletDisplayContext = (RankingPortletDisplayContext)request.getAttribute(ResultRankingsPortletKeys.RESULT_RANKINGS_DISPLAY_CONTEXT);
%>

<clay:management-toolbar-v2
	actionDropdownItems="<%= rankingPortletDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= rankingPortletDisplayContext.getClearResultsURL() %>"
	componentId="resultsRankingEntriesManagementToolbar"
	creationMenu="<%= rankingPortletDisplayContext.getCreationMenu() %>"
	disabled="<%= rankingPortletDisplayContext.isDisabledManagementBar() %>"
	itemsTotal="<%= rankingPortletDisplayContext.getTotalItems() %>"
	searchContainerId="resultsRankingEntries"
	selectable="<%= true %>"
	showCreationMenu="<%= rankingPortletDisplayContext.isShowCreationMenu() %>"
	showSearch="<%= false %>"
/>

<aui:form cssClass="container-fluid-1280" method="post" name="resultsRankingEntriesFm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:search-container
		id="resultsRankingEntries"
		searchContainer="<%= rankingPortletDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingEntryDisplayContext"
			keyProperty="uid"
			modelVar="rankingEntryDisplayContextModelVar"
		>

			<%
			RankingEntryDisplayContext rankingEntryDisplayContext = rankingEntryDisplayContextModelVar;
			%>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="editResultsRankingEntry" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="resultsRankingUid" value="<%= rankingEntryDisplayContext.getUid() %>" />
				<portlet:param name="aliases" value="<%= rankingEntryDisplayContext.getAliases() %>" />
				<portlet:param name="companyId" value="<%= String.valueOf(themeDisplay.getCompanyId()) %>" />
				<portlet:param name="inactive" value="<%= String.valueOf(rankingEntryDisplayContext.getInactive()) %>" />
				<portlet:param name="keywords" value="<%= rankingEntryDisplayContext.getKeywords() %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="search-query"
			>
				<div class="list-group-title">
					<a href="<%= rowURL %>">
						<%= HtmlUtil.escape(rankingEntryDisplayContext.getKeywords()) %>
					</a>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="aliases"
			>
				<div class="list-group-subtext">
					<%= HtmlUtil.escape(rankingEntryDisplayContext.getAliases()) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-150"
				name="pinned-results"
				value="<%= rankingEntryDisplayContext.getPinnedResultsCount() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-150"
				name="hidden-results"
				value="<%= rankingEntryDisplayContext.getHiddenResultsCount() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-150"
				name="status"
			>
				<div class="label <%= rankingEntryDisplayContext.getInactive() ? "label-secondary" : "label-success" %>">
					<span class="label-item label-item-expand">
						<liferay-ui:message key='<%= rankingEntryDisplayContext.getInactive() ? "inactive" : "active" %>' />
					</span>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/view_results_rankings_entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	var submitForm = function (url) {
		var searchContainer = document.getElementById(
			'<portlet:namespace />resultsRankingEntries'
		);

		if (searchContainer) {
			Liferay.Util.postForm(
				document.<portlet:namespace />resultsRankingEntriesFm,
				{
					data: {
						actionFormInstanceIds: Liferay.Util.listCheckedExcept(
							searchContainer,
							'<portlet:namespace />allRowIds'
						),
					},
					url: url,
				}
			);
		}
	};

	var activateResultsRankingsEntries = function () {
		<portlet:actionURL name="/results_ranking/edit" var="activateResultsRankingEntryURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= ResultRankingsConstants.ACTIVATE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		submitForm('<%= activateResultsRankingEntryURL %>');
	};

	var deactivateResultsRankingsEntries = function () {
		<portlet:actionURL name="/results_ranking/edit" var="deactivateResultsRankingEntryURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= ResultRankingsConstants.DEACTIVATE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		submitForm('<%= deactivateResultsRankingEntryURL %>');
	};

	var deleteResultsRankingsEntries = function () {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />'
			)
		) {
			<portlet:actionURL name="/results_ranking/edit" var="deleteResultsRankingEntryURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			submitForm('<%= deleteResultsRankingEntryURL %>');
		}
	};

	var ACTIONS = {
		activateResultsRankingsEntries: activateResultsRankingsEntries,
		deactivateResultsRankingsEntries: deactivateResultsRankingsEntries,
		deleteResultsRankingsEntries: deleteResultsRankingsEntries,
	};

	Liferay.componentReady('resultsRankingEntriesManagementToolbar').then(function (
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function (event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>