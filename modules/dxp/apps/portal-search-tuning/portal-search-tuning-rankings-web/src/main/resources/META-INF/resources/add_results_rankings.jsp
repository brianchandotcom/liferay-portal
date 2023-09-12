<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionErrors" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.exception.DuplicateQueryStringException" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String redirect = ParamUtil.getString(request, "redirect");

String resultActionCmd = ParamUtil.getString(request, "resultActionCmd");
String resultActionUid = ParamUtil.getString(request, "resultActionUid");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "new-ranking"));
%>

<c:if test="<%= !SessionErrors.isEmpty(renderRequest) %>">
	<div class="result-rankings-alert-container">
		<liferay-ui:error exception="<%= DuplicateQueryStringException.class %>" message="ranking-with-that-search-query-already-exists" />
		<liferay-ui:error exception="<%= Exception.class %>" message="an-unexpected-error-occurred" />

		<liferay-ui:error-principal />
	</div>
</c:if>

<portlet:actionURL name="/result_rankings/edit_ranking" var="addResultsRankingEntryURL" />

<liferay-frontend:edit-form
	action="<%= addResultsRankingEntryURL %>"
>
	<liferay-frontend:edit-form-body>
		<div class="sheet-text">
			<liferay-ui:message key="customize-how-users-see-results-for-a-given-search-query" />
		</div>

		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="resultActionCmd" type="hidden" value="<%= resultActionCmd %>" />
		<aui:input name="resultActionUid" type="hidden" value="<%= resultActionUid %>" />

		<aui:input label="search-query" name="keywords" required="<%= true %>" showRequiredLabel="<%= true %>" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= redirect %>"
			submitLabel="customize-results"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>