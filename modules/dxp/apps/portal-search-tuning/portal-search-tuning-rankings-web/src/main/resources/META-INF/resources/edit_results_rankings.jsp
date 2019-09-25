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

<portlet:actionURL name="/results_ranking/edit" var="addResultsRankingEntryURL" />

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
			data="<%= editRankingDisplayContext.getData() %>"
			module="js/ResultRankingsApp.es"
		/>
	</div>
</aui:form>