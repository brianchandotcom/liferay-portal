<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsConstants" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingEntryDisplayContext" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingPortletDisplayContext" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.exception.DuplicateQueryStringException" %><%@
page import="com.liferay.search.experiences.model.SXPBlueprint" %><%@
page import="com.liferay.search.experiences.service.SXPBlueprintLocalServiceUtil" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<liferay-ui:error embed="<%= false %>" exception="<%= DuplicateQueryStringException.class %>" message="active-search-queries-and-aliases-must-be-unique-across-all-rankings" />

<%
RankingPortletDisplayContext rankingPortletDisplayContext = (RankingPortletDisplayContext)request.getAttribute(ResultRankingsPortletKeys.RESULT_RANKINGS_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/result_rankings/edit_ranking" var="activateResultsRankingEntryURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= ResultRankingsConstants.ACTIVATE %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:actionURL name="/result_rankings/edit_ranking" var="deactivateResultsRankingEntryURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= ResultRankingsConstants.DEACTIVATE %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:actionURL name="/result_rankings/edit_ranking" var="deleteResultsRankingEntryURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= rankingPortletDisplayContext.getActionDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"activateResultsRankingEntryURL", activateResultsRankingEntryURL
		).put(
			"deactivateResultsRankingEntryURL", deactivateResultsRankingEntryURL
		).put(
			"deleteResultsRankingEntryURL", deleteResultsRankingEntryURL
		).build()
	%>'
	clearResultsURL="<%= rankingPortletDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= rankingPortletDisplayContext.getCreationMenu() %>"
	disabled="<%= rankingPortletDisplayContext.isDisabledManagementBar() %>"
	itemsTotal="<%= rankingPortletDisplayContext.getTotalItems() %>"
	propsTransformer="js/RankingPortletManagementToolbarPropsTransformer"
	searchContainerId="resultsRankingEntries"
	selectable="<%= true %>"
	showCreationMenu="<%= rankingPortletDisplayContext.isShowCreationMenu() %>"
	showSearch="<%= false %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
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
				<portlet:param name="mvcRenderCommandName" value="/result_rankings/edit_results_rankings" />
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

			<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-157988") || FeatureFlagManagerUtil.isEnabled("LPS-159650") %>'>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-cell-minw-150"
					name="scope"
				>
					<c:choose>
						<c:when test="<%= Validator.isNotNull(rankingEntryDisplayContext.getGroupExternalReferenceCode()) %>">

							<%
							Group group = GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(rankingEntryDisplayContext.getGroupExternalReferenceCode(), themeDisplay.getCompanyId());
							%>

							<span class="lfr-portal-tooltip" data-title='<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>'>
								<liferay-ui:message key="site" />
							</span>
						</c:when>
						<c:when test="<%= Validator.isNotNull(rankingEntryDisplayContext.getSXPBlueprintExternalReferenceCode()) %>">

							<%
							SXPBlueprint sxpBlueprint = SXPBlueprintLocalServiceUtil.getSXPBlueprintByExternalReferenceCode(rankingEntryDisplayContext.getSXPBlueprintExternalReferenceCode(), themeDisplay.getCompanyId());
							%>

							<span class="lfr-portal-tooltip" data-title='<%= HtmlUtil.escape(sxpBlueprint.getTitle(locale)) %>'>
								<liferay-ui:message key="blueprint" />
							</span>
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="everything" />
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>
			</c:if>

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