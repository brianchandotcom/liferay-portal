<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.EditRankingDisplayContext" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
EditRankingDisplayContext editRankingDisplayContext = (EditRankingDisplayContext)request.getAttribute(ResultRankingsPortletKeys.EDIT_RANKING_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(editRankingDisplayContext.getBackURL());

renderResponse.setTitle(LanguageUtil.get(request, "customize-results"));
%>

<portlet:actionURL name="/result_rankings/edit_ranking" var="addResultsRankingEntryURL" />

<aui:form action="<%= addResultsRankingEntryURL %>" name="<%= editRankingDisplayContext.getFormName() %>" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= editRankingDisplayContext.getRedirect() %>" />
	<aui:input name="companyId" type="hidden" value="<%= editRankingDisplayContext.getCompanyId() %>" />
	<aui:input name="keywords" type="hidden" value="<%= editRankingDisplayContext.getKeywords() %>" />
	<aui:input name="resultsRankingUid" type="hidden" value="<%= editRankingDisplayContext.getResultsRankingUid() %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<div>
		<div class="loading-animation-container">
			<span aria-hidden="true" class="loading-animation"></span>
		</div>

		<react:component
			module="js/ResultRankingsApp.es"
			props="<%= editRankingDisplayContext.getData() %>"
		/>
	</div>
</aui:form>