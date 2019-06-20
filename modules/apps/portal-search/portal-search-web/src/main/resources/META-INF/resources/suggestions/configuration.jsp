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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.search.web.internal.suggestions.portlet.SuggestionsPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.suggestions.portlet.SuggestionsPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<portlet:defineObjects />

<%
SuggestionsPortletPreferences suggestionsPortletPreferences = new SuggestionsPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<aui:input helpMessage="collated-spell-check-result-enabled-help" id="spellCheckSuggestionEnabled" label="display-did-you-mean-if-the-number-of-search-results-does-not-meet-the-threshold" name="<%= PortletPreferencesJspUtil.getInputName(SuggestionsPortletPreferences.PREFERENCE_KEY_SPELL_CHECK_SUGGESTION_ENABLED) %>" type="checkbox" value="<%= suggestionsPortletPreferences.isSpellCheckSuggestionEnabled() %>" />

			<aui:input helpMessage="collated-spell-check-result-display-threshold-help" label="threshold-for-displaying-did-you-mean" name="<%= PortletPreferencesJspUtil.getInputName(SuggestionsPortletPreferences.PREFERENCE_KEY_SPELL_CHECK_SUGGESTION_DISPLAY_THRESHOLD) %>" size="10" type="text" value="<%= suggestionsPortletPreferences.getSpellCheckSuggestionDisplayThreshold() %>" />

			<hr />

			<aui:input helpMessage="query-suggestions-enabled-help" id="relatedSuggestionsEnabled" label="display-related-queries" name="<%= PortletPreferencesJspUtil.getInputName(SuggestionsPortletPreferences.PREFERENCE_KEY_RELATED_QUERIES_SUGGESTIONS_ENABLED) %>" type="checkbox" value="<%= suggestionsPortletPreferences.isRelatedQueriesSuggestionsEnabled() %>" />

			<aui:input helpMessage="query-suggestions-display-threshold-help" label="threshold-for-displaying-related-queries" name="<%= PortletPreferencesJspUtil.getInputName(SuggestionsPortletPreferences.PREFERENCE_KEY_RELATED_QUERIES_SUGGESTIONS_DISPLAY_THRESHOLD) %>" size="10" type="text" value="<%= suggestionsPortletPreferences.getRelatedQueriesSuggestionsDisplayThreshold() %>" />

			<aui:input label="maximum-number-of-related-queries" name="<%= PortletPreferencesJspUtil.getInputName(SuggestionsPortletPreferences.PREFERENCE_KEY_RELATED_QUERIES_SUGGESTIONS_MAX) %>" size="10" type="text" value="<%= suggestionsPortletPreferences.getRelatedQueriesSuggestionsMax() %>" />

			<hr />

			<aui:input helpMessage="query-indexing-enabled-help" label="add-new-related-queries-based-on-successful-queries" name="<%= PortletPreferencesJspUtil.getInputName(SuggestionsPortletPreferences.PREFERENCE_KEY_QUERY_INDEXING_ENABLED) %>" type="checkbox" value="<%= suggestionsPortletPreferences.isQueryIndexingEnabled() %>" />

			<aui:input helpMessage="query-indexing-threshold-help" label="query-indexing-threshold" name="<%= PortletPreferencesJspUtil.getInputName(SuggestionsPortletPreferences.PREFERENCE_KEY_QUERY_INDEXING_THRESHOLD) %>" size="10" type="text" value="<%= suggestionsPortletPreferences.getQueryIndexingThreshold() %>" />
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>