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
page import="com.liferay.search.experiences.web.internal.sentence.transformer.configuration.display.context.SentenceTransformerCompanyConfigurationDisplayContext" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Map.Entry" %>

<%
SentenceTransformerCompanyConfigurationDisplayContext sentenceTransformerCompanyConfigurationDisplayContext = (SentenceTransformerCompanyConfigurationDisplayContext)request.getAttribute(SentenceTransformerCompanyConfigurationDisplayContext.class.getName());

List<String> availableEmbeddingVectorDimensions = sentenceTransformerCompanyConfigurationDisplayContext.getAvailableEmbeddingVectorDimensions();

Map<String, String> availableEntryClassNames = sentenceTransformerCompanyConfigurationDisplayContext.getAvailableEntryClassNames();

Map<String, String> availableLanguages = sentenceTransformerCompanyConfigurationDisplayContext.getAvailableLanguages();

Map<String, String> availableSentenceTransformerProviders = sentenceTransformerCompanyConfigurationDisplayContext.getAvailableSentenceTranformerProviders();

Map<String, String> availableTextTruncationStrategies = sentenceTransformerCompanyConfigurationDisplayContext.getAvailableTextTruncationStrategies();

List<String> currentEntryClassNames = sentenceTransformerCompanyConfigurationDisplayContext.getEntryClassNames();

List<String> currentLanguageIds = sentenceTransformerCompanyConfigurationDisplayContext.getLanguageIds();

String currentSentenceTransformerProvider = sentenceTransformerCompanyConfigurationDisplayContext.getSentenceTransformerProvider();
%>

<aui:input name="enabled" type="checkbox" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.isEnabled() %>" />

<aui:select id="sentenceTransformerProvider" name="sentenceTransformerProvider" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getSentenceTransformerProvider() %>">

	<%
	for (Entry<String, String> entry : availableSentenceTransformerProviders.entrySet()) {
	%>

		<aui:option label="<%= entry.getValue() %>" value="<%= entry.getKey() %>" />

	<%
	}
	%>

</aui:select>

<div class="options-container <%= !currentSentenceTransformerProvider.equals("huggingFace") ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />huggingFaceOptionsContainer">
	<aui:input name="huggingFaceAccessToken" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getHuggingFaceAccessToken() %>" />

	<aui:input helpMessage="sentence-transformer-model-timeout-help" name="modelTimeout" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getModelTimeout() %>">
		<aui:validator name="required" />
		<aui:validator name="number" />
		<aui:validator name="range">[0,60]</aui:validator>
	</aui:input>

	<aui:input name="enableGPU" type="checkbox" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.isEnableGPU() %>" />
</div>

<div class="options-container <%= !currentSentenceTransformerProvider.equals("txtai") ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />txtAiOptionsContainer">
	<aui:input helpMessage="sentence-transformer-txtai-host-help" label="txtai-host" name="txtAihost" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getTxtAihost() %>" />
</div>

<%-- TODO: use SXP REST endpoint to query ML models --%>

<aui:input helpMessage="sentence-transformer-model-help" name="model" required="<%= true %>" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getModel() %>" />

<aui:select name="embeddingVectorDimensions" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getEmbeddingVectorDimensions() %>">

	<%
	for (String embeddingVectorDimensions : availableEmbeddingVectorDimensions) {
	%>

		<aui:option label="<%= embeddingVectorDimensions %>" value="<%= embeddingVectorDimensions %>" />

	<%
	}
	%>

</aui:select>

<aui:input helpMessage="sentence-transformer-max-character-count-help" name="maxCharacterCount" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getMaxCharacterCount() %>">
	<aui:validator name="required" />
	<aui:validator name="number" />
	<aui:validator name="range">[50,2500]</aui:validator>
</aui:input>

<aui:select helpMessage="sentence-transformer-text-truncation-strategy-help" name="textTruncationStrategy" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getTextTruncationStrategy() %>">

	<%
	for (Entry<String, String> entry : availableTextTruncationStrategies.entrySet()) {
	%>

		<aui:option label="<%= entry.getValue() %>" value="<%= entry.getKey() %>" />

	<%
	}
	%>

</aui:select>

<aui:select helpMessage="sentence-transformer-entry-class-names-help" multiple="<%= true %>" name="entryClassNames" required="<%= true %>">

	<%
	for (Entry<String, String> entry : availableEntryClassNames.entrySet()) {
	%>

		<aui:option label="<%= entry.getValue() %>" selected="<%= currentEntryClassNames.contains(entry.getKey()) %>" value="<%= entry.getKey() %>" />

	<%
	}
	%>

</aui:select>

<aui:select helpMessage="sentence-transformer-language-ids-help" multiple="<%= true %>" name="languageIds" required="<%= true %>">

	<%
	for (Entry<String, String> entry : availableLanguages.entrySet()) {
	%>

		<aui:option label="<%= entry.getValue() %>" selected="<%= currentLanguageIds.contains(entry.getKey()) %>" value="<%= entry.getKey() %>" />

	<%
	}
	%>

</aui:select>

<aui:input name="cacheTimeout" value="<%= sentenceTransformerCompanyConfigurationDisplayContext.getCacheTimeout() %>">
	<aui:validator name="required" />
	<aui:validator name="number" />
	<aui:validator name="min">0</aui:validator>
</aui:input>

<aui:script>
	AUI().ready((A) => {
		A.one('#<portlet:namespace />sentenceTransformerProvider').on(
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