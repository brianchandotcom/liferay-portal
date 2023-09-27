<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsConstants" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingEntryDisplayContext" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

RankingEntryDisplayContext rankingEntryDisplayContext = (RankingEntryDisplayContext)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="/result_rankings/edit_results_rankings" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="resultsRankingUid" value="<%= rankingEntryDisplayContext.getUid() %>" />
		<portlet:param name="aliases" value="<%= rankingEntryDisplayContext.getAliases() %>" />
		<portlet:param name="companyId" value="<%= String.valueOf(themeDisplay.getCompanyId()) %>" />
		<portlet:param name="inactive" value="<%= String.valueOf(rankingEntryDisplayContext.getInactive()) %>" />
		<portlet:param name="keywords" value="<%= rankingEntryDisplayContext.getKeywords() %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/result_rankings/edit_ranking" var="deactivateURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= rankingEntryDisplayContext.getInactive() ? ResultRankingsConstants.ACTIVATE : ResultRankingsConstants.DEACTIVATE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="resultsRankingUid" value="<%= rankingEntryDisplayContext.getUid() %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="<%= rankingEntryDisplayContext.getInactive() ? ResultRankingsConstants.ACTIVATE : ResultRankingsConstants.DEACTIVATE %>"
		url="<%= deactivateURL %>"
	/>

	<portlet:actionURL name="/result_rankings/edit_ranking" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="resultsRankingUid" value="<%= rankingEntryDisplayContext.getUid() %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>