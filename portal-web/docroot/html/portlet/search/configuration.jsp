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

<%@ include file="/html/portlet/search/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<aui:fieldset label="display-settings">
		<div class="configuration-style" id="<portlet:namespace />configurationStyle">
			<aui:field-wrapper>
				<aui:input checked="<%= !advancedConfiguration %>" id="basic" label="basic" name="preferences--advancedConfiguration--" type="radio" value="false" />
				<aui:input checked="<%= advancedConfiguration %>" id="advanced" label="advanced" name="preferences--advancedConfiguration--" type="radio" value="true" />
			</aui:field-wrapper>
		</div>

		<div class="basic-configuration <%= (advancedConfiguration ? "hide" : StringPool.BLANK) %>" id="<portlet:namespace />basicConfiguration">
			<aui:input name="preferences--displayScopeFacet--" type="checkbox" value="<%= displayScopeFacet %>" />

			<aui:input name="preferences--displayAssetTypeFacet--" type="checkbox" value="<%= displayAssetTypeFacet %>" />

			<aui:input name="preferences--displayAssetTagsFacet--" type="checkbox" value="<%= displayAssetTagsFacet %>" />

			<aui:input name="preferences--displayAssetCategoriesFacet--" type="checkbox" value="<%= displayAssetCategoriesFacet %>" />

			<aui:input name="preferences--displayFolderFacet--" type="checkbox" value="<%= displayFolderFacet %>" />

			<aui:input name="preferences--displayUserFacet--" type="checkbox" value="<%= displayUserFacet %>" />

			<aui:input name="preferences--displayModifiedRangeFacet--" type="checkbox" value="<%= displayModifiedRangeFacet %>" />
		</div>

		<div class="advanced-configuration <%= (!advancedConfiguration ? "hide" : StringPool.BLANK) %>" id="<portlet:namespace />advancedConfiguration">

			<%
			JSONObject searchConfigurationJSONObject = JSONFactoryUtil.createJSONObject(searchConfiguration);
			%>

			<aui:input cssClass="search-configuration-text" helpMessage="search-configuration-help" name="preferences--searchConfiguration--" type="textarea" value="<%= searchConfigurationJSONObject.toString(4) %>" />
		</div>
	</aui:fieldset>

	<br />

	<aui:fieldset label="spell-check-settings">
		<aui:input helpMessage="collated-spell-check-result-enabled-help" id="collatedSpellCheckResultEnabled" label="display-did-you-mean-if-the-number-of-search-results-does-not-meet-the-threshold" name="preferences--collatedSpellCheckResultEnabled--" type="checkbox" value="<%= collatedSpellCheckResultEnabled %>" />

		<div class="options-container <%= !collatedSpellCheckResultEnabled ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />collatedSpellCheckResultOptionsContainer">
			<liferay-ui:toggle-area align="none" defaultShowContent="<%= collatedSpellCheckResultEnabled %>" hideMessage='<%= "&laquo; " + LanguageUtil.get(request, "hide-options") %>' id="toggle_id_search_configuration_collated_spell_check_result" showMessage='<%= LanguageUtil.get(request, "show-options") + " &raquo;" %>'>
				<aui:input disabled="<%= !collatedSpellCheckResultEnabled %>" helpMessage="collated-spell-check-result-display-threshold-help" label="threshold-for-displaying-did-you-mean" name="preferences--collatedSpellCheckResultDisplayThreshold--" size="10" type="text" value="<%= collatedSpellCheckResultDisplayThreshold %>" />
			</liferay-ui:toggle-area>
		</div>

		<aui:input helpMessage="query-suggestions-enabled-help" id="querySuggestionsEnabled" label="display-related-queries" name="preferences--querySuggestionsEnabled--" type="checkbox" value="<%= querySuggestionsEnabled %>" />

		<div class="options-container <%= !querySuggestionsEnabled ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />querySuggestionsOptionsContainer">
			<liferay-ui:toggle-area align="none" defaultShowContent="<%= querySuggestionsEnabled %>" hideMessage='<%= "&laquo; " + LanguageUtil.get(request, "hide-options") %>' id="toggle_id_search_configuration_query_suggestions" showMessage='<%= LanguageUtil.get(request, "show-options") + " &raquo;" %>'>
				<aui:input disabled="<%= !querySuggestionsEnabled %>" label="maximum-number-of-related-queries" name="preferences--querySuggestionsMax--" size="10" type="text" value="<%= querySuggestionsMax %>" />

				<aui:input disabled="<%= !querySuggestionsEnabled %>" helpMessage="query-suggestions-display-threshold-help" label="threshold-for-displaying-related-queries" name="preferences--querySuggestionsDisplayThreshold--" size="10" type="text" value="<%= querySuggestionsDisplayThreshold %>" />
			</liferay-ui:toggle-area>
		</div>

		<aui:input helpMessage="query-indexing-enabled-help" id="queryIndexingEnabled" label="add-new-related-queries-based-on-successful-queries" name="preferences--queryIndexingEnabled--" type="checkbox" value="<%= queryIndexingEnabled %>" />

		<div class="options-container <%= !queryIndexingEnabled ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />queryIndexingOptionsContainer">
			<liferay-ui:toggle-area align="none" defaultShowContent="<%= queryIndexingEnabled %>" hideMessage='<%= "&laquo; " + LanguageUtil.get(request, "hide-options") %>' id="toggle_id_search_configuration_query_indexing" showMessage='<%= LanguageUtil.get(request, "show-options") + " &raquo;" %>'>
				<aui:input disabled="<%= !queryIndexingEnabled %>" helpMessage="query-indexing-threshold-help" name="preferences--queryIndexingThreshold--" size="10" type="text" value="<%= queryIndexingThreshold %>" />
			</liferay-ui:toggle-area>
		</div>
	</aui:fieldset>

	<br />

	<aui:fieldset label="other-settings">
		<c:if test="<%= permissionChecker.isCompanyAdmin() %>">
			<aui:input helpMessage="display-results-in-document-form-help" name="preferences--displayResultsInDocumentForm--" type="checkbox" value="<%= displayResultsInDocumentForm %>" />
		</c:if>

		<aui:input name="preferences--viewInContext--" type="checkbox" value="<%= viewInContext %>" />

		<aui:input helpMessage="display-main-query-help" name="preferences--displayMainQuery--" type="checkbox" value="<%= displayMainQuery %>" />

		<aui:input helpMessage="display-open-search-results-help" name="preferences--displayOpenSearchResults--" type="checkbox" value="<%= displayOpenSearchResults %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.toggleRadio('<portlet:namespace />advanced', '<portlet:namespace />advancedConfiguration', '<portlet:namespace />basicConfiguration');
	Liferay.Util.toggleRadio('<portlet:namespace />basic', '<portlet:namespace />basicConfiguration', '<portlet:namespace />advancedConfiguration');
	Liferay.Util.toggleBoxes('<portlet:namespace />collatedSpellCheckResultEnabled', '<portlet:namespace />collatedSpellCheckResultOptionsContainer');
	Liferay.Util.toggleBoxes('<portlet:namespace />queryIndexingEnabled', '<portlet:namespace />queryIndexingOptionsContainer');
	Liferay.Util.toggleBoxes('<portlet:namespace />querySuggestionsEnabled', '<portlet:namespace />querySuggestionsOptionsContainer');
</aui:script>