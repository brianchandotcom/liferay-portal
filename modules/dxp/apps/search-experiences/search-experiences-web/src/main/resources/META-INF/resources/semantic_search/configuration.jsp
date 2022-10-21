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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.search.experiences.web.internal.display.context.SemanticSearchCompanyConfigurationDisplayContext" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Map.Entry" %>

<%
SemanticSearchCompanyConfigurationDisplayContext sentenceTransformerCompanyConfigurationDisplayContext = (SemanticSearchCompanyConfigurationDisplayContext)request.getAttribute(SemanticSearchCompanyConfigurationDisplayContext.class.getName());
%>

<aui:input name="enabled" type="checkbox" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.isEnabled() %>" />

<aui:fieldset label="transform-provider-settings">

	<%
	String sentenceTransformProvider = sentenceTransformerCompanyConfigurationDisplayContext.getSentenceTransformProvider();
	%>

	<aui:select id="sentenceTransformProvider" name="sentenceTransformProvider" value="<%= sentenceTransformProvider %>">

		<%
		Map<String, String> availableSentenceTransformProviders = sentenceTransformerCompanyConfigurationDisplayContext.getAvailableSentenceTranformProviders();

		for (Entry<String, String> entry : availableSentenceTransformProviders.entrySet()) {
		%>

			<aui:option label="<%= entry.getValue() %>" value="<%= entry.getKey() %>" />

		<%
		}
		%>

	</aui:select>

	<div class="options-container <%= !sentenceTransformProvider.equals("huggingFace") ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />huggingFaceOptionsContainer">
		<aui:input name="huggingFaceAccessToken" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getHuggingFaceAccessToken() %>" />

		<%--
		TODO Use REST to query ML models
		--%>

		<aui:input helpMessage="sentence-transformer-model-help" name="model" value='<%= (sentenceTransformerCompanyConfigurationDisplayContext.getModel() != null) ? sentenceTransformerCompanyConfigurationDisplayContext.getModel() : "facebook/contriever-msmarco" %>' />

		<aui:input helpMessage="sentence-transformer-model-timeout-help" name="modelTimeout" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getModelTimeout() %>">
			<aui:validator name="required" />
			<aui:validator name="number" />
			<aui:validator name="range">[0,60]</aui:validator>
		</aui:input>

		<aui:input name="enableGPU" type="checkbox" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.isEnableGPU() %>" />
	</div>

	<div class="options-container <%= !sentenceTransformProvider.equals("txtai") ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />txtAiOptionsContainer">
		<aui:input helpMessage="sentence-transformer-txtai-host-address-help" label="txtai-host-address" name="txtaiHostAddress" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getTxtaiHostAddress() %>" />
	</div>

	<aui:select name="embeddingVectorDimensions" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getEmbeddingVectorDimensions() %>">

		<%
		for (String availableEmbeddingVectorDimensions : sentenceTransformerCompanyConfigurationDisplayContext.getAvailableEmbeddingVectorDimensions()) {
		%>

			<aui:option label="<%= availableEmbeddingVectorDimensions %>" value="<%= availableEmbeddingVectorDimensions %>" />

		<%
		}
		%>

	</aui:select>
</aui:fieldset>

<aui:fieldset label="indexing-settings">
	<aui:input helpMessage="sentence-transformer-max-character-count-help" name="maxCharacterCount" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getMaxCharacterCount() %>">
		<aui:validator name="required" />
		<aui:validator name="number" />
		<aui:validator name="range">[50,10000]</aui:validator>
	</aui:input>

	<aui:select helpMessage="sentence-transformer-text-truncation-strategy-help" name="textTruncationStrategy" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getTextTruncationStrategy() %>">

		<%
		Map<String, String> availableTextTruncationStrategies = sentenceTransformerCompanyConfigurationDisplayContext.getAvailableTextTruncationStrategies();

		for (Entry<String, String> entry : availableTextTruncationStrategies.entrySet()) {
		%>

			<aui:option label="<%= entry.getValue() %>" value="<%= entry.getKey() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:select helpMessage="sentence-transformer-asset-entry-class-names-help" multiple="<%= true %>" name="assetEntryClassNames" required="<%= true %>">

		<%
		Map<String, String> availableAssetEntryClassNames = sentenceTransformerCompanyConfigurationDisplayContext.getAvailableAssetEntryClassNames();

		for (Entry<String, String> entry : availableAssetEntryClassNames.entrySet()) {
			List<String> assetEntryClassNames = sentenceTransformerCompanyConfigurationDisplayContext.getAssetEntryClassNames();
		%>

			<aui:option label="<%= entry.getValue() %>" selected="<%= assetEntryClassNames.contains(entry.getKey()) %>" value="<%= entry.getKey() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:select helpMessage="sentence-transformer-language-ids-help" multiple="<%= true %>" name="languageIds" required="<%= true %>">

		<%
		Map<String, String> availableLanguageDisplayNames = sentenceTransformerCompanyConfigurationDisplayContext.getAvailableLanguageDisplayNames();

		for (Entry<String, String> entry : availableLanguageDisplayNames.entrySet()) {
			List<String> languageIds = sentenceTransformerCompanyConfigurationDisplayContext.getLanguageIds();
		%>

			<aui:option label="<%= entry.getValue() %>" selected="<%= languageIds.contains(entry.getKey()) %>" value="<%= entry.getKey() %>" />

		<%
		}
		%>

	</aui:select>
</aui:fieldset>

<aui:input name="cacheTimeout" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getCacheTimeout() %>">
	<aui:validator name="required" />
	<aui:validator name="number" />
	<aui:validator name="min">0</aui:validator>
</aui:input>

<aui:script>
	Liferay.Portlet.ready(() => {
		let A = AUI();

		A.one('#<portlet:namespace />sentenceTransformProvider').on(
			'change',
			() => {
				A.one(
					'#<portlet:namespace />huggingFaceOptionsContainer'
				).toggleClass('hide');

				A.one('#<portlet:namespace />txtAiOptionsContainer').toggleClass(
					'hide'
				);
			}
		);
	});
</aui:script>