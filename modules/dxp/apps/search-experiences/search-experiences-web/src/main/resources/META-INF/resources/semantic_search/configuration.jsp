<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %>

<%@ page import="com.liferay.learn.LearnMessageUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.search.experiences.web.internal.display.context.SemanticSearchCompanyConfigurationDisplayContext" %>

<portlet:defineObjects />

<%
SemanticSearchCompanyConfigurationDisplayContext semanticSearchCompanyConfigurationDisplayContext = (SemanticSearchCompanyConfigurationDisplayContext)request.getAttribute(SemanticSearchCompanyConfigurationDisplayContext.class.getName());
%>

<div>
	<span aria-hidden="true" class="loading-animation"></span>

	<react:component
		module="semantic_search/js/configuration/index"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"availableEmbeddingVectorDimensions", semanticSearchCompanyConfigurationDisplayContext.getAvailableEmbeddingVectorDimensions()
			).put(
				"availableLanguageDisplayNames", semanticSearchCompanyConfigurationDisplayContext.getAvailableLanguageDisplayNames()
			).put(
				"availableModelClassNames", semanticSearchCompanyConfigurationDisplayContext.getAvailableModelClassNames()
			).put(
				"availableTextEmbeddingProviders", semanticSearchCompanyConfigurationDisplayContext.getAvailableTextEmbeddingProviders()
			).put(
				"availableTextTruncationStrategies", semanticSearchCompanyConfigurationDisplayContext.getAvailableTextTruncationStrategies()
			).put(
				"formName", liferayPortletResponse.getNamespace() + "fm"
			).put(
				"initialTextEmbeddingCacheTimeout", semanticSearchCompanyConfigurationDisplayContext.getTextEmbeddingCacheTimeout()
			).put(
				"initialTextEmbeddingProviderConfigurationJSONs", semanticSearchCompanyConfigurationDisplayContext.getTextEmbeddingProviderConfigurationJSONs()
			).put(
				"initialTextEmbeddingsEnabled", semanticSearchCompanyConfigurationDisplayContext.isTextEmbeddingsEnabled()
			).put(
				"learnMessages", LearnMessageUtil.getJSONObject("search-experiences-web")
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"redirectURL", String.valueOf(liferayPortletResponse.createRenderURL())
			).build()
		%>'
	/>
</div>

<aui:script>
	function <portlet:namespace />removeExistingFormSubmitButtons() {
		const formElement = document.getElementById('<portlet:namespace />fm');

		if (formElement) {
			const submitButtonGroupElement = formElement.querySelector(
				'.button-holder'
			);

			if (submitButtonGroupElement) {
				submitButtonGroupElement.remove();
			}
		}
	}

	<portlet:namespace />removeExistingFormSubmitButtons();
</aui:script>