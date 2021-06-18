<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:renderURL var="basePortletURL" />

<div id="<%= liferayPortletResponse.getNamespace() + "-questions-root" %>">

	<%
	QuestionsConfiguration questionsConfiguration = portletDisplay.getPortletInstanceConfiguration(QuestionsConfiguration.class);
	%>

	<react:component
		module="js/index.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"defaultRank", renderRequest.getAttribute(QuestionsWebKeys.DEFAULT_RANK)
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