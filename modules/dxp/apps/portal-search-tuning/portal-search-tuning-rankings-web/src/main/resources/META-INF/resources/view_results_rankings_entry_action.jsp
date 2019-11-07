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

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
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
		<portlet:param name="mvcRenderCommandName" value="editResultsRankingEntry" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="resultsRankingUid" value="<%= rankingEntryDisplayContext.getUid() %>" />
		<portlet:param name="aliases" value="<%= rankingEntryDisplayContext.getAliases() %>" />
		<portlet:param name="companyId" value="<%= String.valueOf(themeDisplay.getCompanyId()) %>" />
		<portlet:param name="keywords" value="<%= rankingEntryDisplayContext.getKeywords() %>" />
		<portlet:param name="inactive" value="<%= String.valueOf(rankingEntryDisplayContext.getInactive()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/results_ranking/edit" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="resultsRankingUid" value="<%= rankingEntryDisplayContext.getUid() %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>