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

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %>

<%@ page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
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
				"assetEntryClassNames", semanticSearchCompanyConfigurationDisplayContext.getAssetEntryClassNames()
			).put(
				"availableAssetEntryClassNames", semanticSearchCompanyConfigurationDisplayContext.getAvailableAssetEntryClassNames()
			).put(
				"availableEmbeddingVectorDimensions", semanticSearchCompanyConfigurationDisplayContext.getAvailableEmbeddingVectorDimensions()
			).put(
				"availableLanguageDisplayNames", semanticSearchCompanyConfigurationDisplayContext.getAvailableLanguageDisplayNames()
			).put(
				"availableSentenceTransformProviders", semanticSearchCompanyConfigurationDisplayContext.getAvailableSentenceTranformProviders()
			).put(
				"availableTextTruncationStrategies", semanticSearchCompanyConfigurationDisplayContext.getAvailableTextTruncationStrategies()
			).put(
				"cacheTimeout", semanticSearchCompanyConfigurationDisplayContext.getCacheTimeout()
			).put(
				"embeddingVectorDimensions", semanticSearchCompanyConfigurationDisplayContext.getEmbeddingVectorDimensions()
			).put(
				"enableGPU", semanticSearchCompanyConfigurationDisplayContext.isEnableGPU()
			).put(
				"huggingFaceAccessToken", semanticSearchCompanyConfigurationDisplayContext.getHuggingFaceAccessToken()
			).put(
				"languageIds", semanticSearchCompanyConfigurationDisplayContext.getLanguageIds()
			).put(
				"maxCharacterCount", semanticSearchCompanyConfigurationDisplayContext.getMaxCharacterCount()
			).put(
				"model", semanticSearchCompanyConfigurationDisplayContext.getModel()
			).put(
				"modelTimeout", semanticSearchCompanyConfigurationDisplayContext.getModelTimeout()
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"sentenceTransformProvider", semanticSearchCompanyConfigurationDisplayContext.getSentenceTransformProvider()
			).put(
				"sentenceTransformerEnabled", semanticSearchCompanyConfigurationDisplayContext.isSentenceTransformerEnabled()
			).put(
				"textTruncationStrategy", semanticSearchCompanyConfigurationDisplayContext.getTextTruncationStrategy()
			).put(
				"txtaiHostAddress", semanticSearchCompanyConfigurationDisplayContext.getTxtaiHostAddress()
			).build()
		%>'
	/>
</div>
