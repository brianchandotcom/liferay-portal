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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONSerializer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

String resultsRankingsRootElementId = renderResponse.getNamespace() + "-results-rankings-root";

String resultsRankingUid = ParamUtil.getString(request, "resultsRankingUid");
String keywords = ParamUtil.getString(request, "keywords");
String companyId = ParamUtil.getString(request, "companyId");
String[] aliases = StringUtil.split(ParamUtil.getString(request, "aliases"), StringPool.COMMA_AND_SPACE);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "customize-results"));
%>

<portlet:actionURL name="/results_ranking/edit" var="addResultsRankingEntryURL" />

<aui:form action="<%= addResultsRankingEntryURL %>" name="editResultsRankingsFm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="companyId" type="hidden" value="<%= companyId %>" />
	<aui:input name="keywords" type="hidden" value="<%= keywords %>" />
	<aui:input name="resultsRankingUid" type="hidden" value="<%= resultsRankingUid %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<div id="<%= resultsRankingsRootElementId %>">
		<div class="loading-animation-container">
			<span aria-hidden="true" class="loading-animation"></span>
		</div>

		<liferay-portlet:resourceURL id="/results_ranking/get_results" portletName="<%= ResultRankingsPortletKeys.RESULT_RANKINGS %>" var="resultsRankingResourceURL">
			<portlet:param name="resultsRankingUid" value="<%= resultsRankingUid %>" />
			<portlet:param name="companyId" value="<%= companyId %>" />
			<portlet:param name="<%= Constants.CMD %>" value="getVisibleResults" />
		</liferay-portlet:resourceURL>

		<liferay-portlet:resourceURL id="/results_ranking/get_results" portletName="<%= ResultRankingsPortletKeys.RESULT_RANKINGS %>" var="hiddenResultsRankingResourceURL">
			<portlet:param name="resultsRankingUid" value="<%= resultsRankingUid %>" />
			<portlet:param name="<%= Constants.CMD %>" value="getHiddenResults" />
		</liferay-portlet:resourceURL>

		<liferay-portlet:resourceURL id="/results_ranking/get_results" portletName="<%= ResultRankingsPortletKeys.RESULT_RANKINGS %>" var="searchResultsRankingResourceURL">
			<portlet:param name="companyId" value="<%= companyId %>" />
			<portlet:param name="<%= Constants.CMD %>" value="getSearchResults" />
		</liferay-portlet:resourceURL>

		<%
		Map<String, Object> context = new HashMap<>();
		context.put("companyId", themeDisplay.getCompanyId());
		context.put("namespace", renderResponse.getNamespace());
		context.put("spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");

		Map<String, Object> constants = new HashMap<>();

		constants.put("WORKFLOW_ACTION_PUBLISH", WorkflowConstants.ACTION_PUBLISH);
		constants.put("WORKFLOW_ACTION_SAVE_DRAFT", WorkflowConstants.ACTION_SAVE_DRAFT);

		context.put("constants", constants);

		Map<String, Object> props = new HashMap<>();

		props.put("cancelUrl", HtmlUtil.escape(redirect));
		props.put("fetchDocumentsHiddenUrl", hiddenResultsRankingResourceURL.toString());
		props.put("fetchDocumentsSearchUrl", searchResultsRankingResourceURL.toString());
		props.put("fetchDocumentsVisibleUrl", resultsRankingResourceURL.toString());
		props.put("formName", renderResponse.getNamespace() + "editResultsRankingsFm");
		props.put("initialAliases", (aliases.length > 0) ? "['" + StringUtil.merge(aliases, "','") + "']" : "[]");
		props.put("searchQuery", HtmlUtil.escape(keywords));

		Map<String, Object> data = new HashMap<>();
		data.put("context", context);
		data.put("props", props);
		%>

		<react:component
			data="<%= data %>"
			module="js/ResultsRankingApp.es"
		/>
	</div>
</aui:form>