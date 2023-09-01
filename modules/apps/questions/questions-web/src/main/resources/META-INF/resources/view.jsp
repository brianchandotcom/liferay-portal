<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:renderURL var="basePortletURL" />

<div id="<%= liferayPortletResponse.getNamespace() %>-questions-root">

	<%
	QuestionsConfiguration questionsConfiguration = ConfigurationProviderUtil.getPortletInstanceConfiguration(QuestionsConfiguration.class, themeDisplay);
	%>

	<react:component
		module="js/index.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"companyName", renderRequest.getAttribute(QuestionsWebKeys.COMPANY_NAME)
			).put(
				"defaultRank", renderRequest.getAttribute(QuestionsWebKeys.DEFAULT_RANK)
			).put(
				"flagsProperties", renderRequest.getAttribute(QuestionsWebKeys.FLAGS_PROPERTIES)
			).put(
				"historyRouterBasePath", questionsConfiguration.historyRouterBasePath()
			).put(
				"i18nPath", renderRequest.getAttribute(WebKeys.I18N_PATH)
			).put(
				"imageBrowseURL", renderRequest.getAttribute(QuestionsWebKeys.IMAGE_BROWSE_URL)
			).put(
				"includeContextPath", renderRequest.getAttribute("javax.servlet.include.context_path")
			).put(
				"isOmniAdmin", permissionChecker.isOmniadmin()
			).put(
				"npmResolvedPackageName", npmResolvedPackageName
			).put(
				"redirectToLogin", questionsConfiguration.enableRedirectToLogin()
			).put(
				"rootTopicId", questionsConfiguration.rootTopicId()
			).put(
				"showCardsForTopicNavigation", questionsConfiguration.showCardsForTopicNavigation()
			).put(
				"siteKey", String.valueOf(themeDisplay.getScopeGroupId())
			).put(
				"tagSelectorURL", renderRequest.getAttribute(QuestionsWebKeys.TAG_SELECTOR_URL)
			).put(
				"trustedUser", renderRequest.getAttribute(QuestionsWebKeys.TRUSTED_USER)
			).put(
				"userId", String.valueOf(themeDisplay.getUserId())
			).put(
				"useTopicNamesInURL", questionsConfiguration.useTopicNamesInURL()
			).build()
		%>'
	/>
</div>