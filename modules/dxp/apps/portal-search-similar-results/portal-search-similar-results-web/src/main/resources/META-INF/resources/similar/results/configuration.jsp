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
taglib uri="http://liferay.com/tld/template" prefix="liferay-template" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.search.similar.results.web.internal.configuration.SimilarResultsPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.similar.results.web.internal.display.context.SimilarResultsDisplayContext" %><%@
page import="com.liferay.portal.search.similar.results.web.internal.display.context.SimilarResultsDocumentDisplayContext" %><%@
page import="com.liferay.portal.search.similar.results.web.internal.portlet.SimilarResultsPortletPreferences" %><%@
page import="com.liferay.portal.search.similar.results.web.internal.portlet.SimilarResultsPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.similar.results.web.internal.util.PortletPreferencesJspUtil" %>

<portlet:defineObjects />

<%
SimilarResultsDisplayContext similarResultsDisplayContext = new SimilarResultsDisplayContext(request);

SimilarResultsPortletInstanceConfiguration similarResultsPortletInstanceConfiguration = similarResultsDisplayContext.getSimilarResultsPortletInstanceConfiguration();

SimilarResultsPortletPreferences similarResultsPortletPreferences = new SimilarResultsPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
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
			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				label="display-settings"
			>
				<div class="display-template">
					<liferay-template:template-selector
						className="<%= SimilarResultsDocumentDisplayContext.class.getName() %>"
						displayStyle="<%= similarResultsPortletInstanceConfiguration.displayStyle() %>"
						displayStyleGroupId="<%= similarResultsDisplayContext.getDisplayStyleGroupId() %>"
						refreshURL="<%= configurationRenderURL %>"
					/>
				</div>

				<aui:input helpMessage="max-item-display-help" label="max-item-display" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_MAX_ITEM_DISPLAY) %>" type="number" value="<%= similarResultsPortletPreferences.getMaxItemDisplay() %>">
					<aui:validator name="digits" />
					<aui:validator name="min">0</aui:validator>
				</aui:input>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				label="advanced-configuration"
			>
				<aui:select label="scope" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_SEARCH_SCOPE) %>" value="<%= similarResultsPortletPreferences.getSearchScope() %>">
					<aui:option label="this-site" />
					<aui:option label="everything" />
				</aui:select>

				<aui:select label="link-behavior" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_LINK_BEHAVIOR) %>" value="<%= similarResultsPortletPreferences.getLinkBehavior() %>">
					<aui:option label="show-content" />
					<aui:option label="view-in-context" />
				</aui:select>

				<aui:input helpMessage="fields-help" label="fields" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_FIELDS) %>" type="text" value="<%= similarResultsPortletPreferences.getFields() %>" />

				<aui:input helpMessage="max-query-terms-help" label="max-query-terms" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_MAX_QUERY_TERMS) %>" type="number" value="<%= similarResultsPortletPreferences.getMaxQueryTerms() %>">
					<aui:validator name="digits" />
					<aui:validator name="min">1</aui:validator>
				</aui:input>

				<aui:input helpMessage="min-term-freq-help" label="min-term-freq" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_MIN_TERM_FREQUENCY) %>" type="number" value="<%= similarResultsPortletPreferences.getMinTermFrequency() %>">
					<aui:validator name="digits" />
					<aui:validator name="min">1</aui:validator>
				</aui:input>

				<aui:input helpMessage="min-doc-freq-help" label="min-doc-freq" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_MIN_DOC_FREQUENCY) %>" type="number" value="<%= similarResultsPortletPreferences.getMinDocFrequency() %>">
					<aui:validator name="digits" />
					<aui:validator name="min">1</aui:validator>
				</aui:input>

				<aui:input helpMessage="max-doc-freq-help" label="max-doc-freq" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_MAX_DOC_FREQUENCY) %>" type="number" value="<%= similarResultsPortletPreferences.getMaxDocFrequency() %>">
					<aui:validator name="digits" />
					<aui:validator name="min">1</aui:validator>
				</aui:input>

				<aui:input helpMessage="min-word-length-help" label="min-word-length" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_MIN_WORD_LENGTH) %>" type="number" value="<%= similarResultsPortletPreferences.getMinWordLength() %>">
					<aui:validator name="digits" />
					<aui:validator name="min">0</aui:validator>
				</aui:input>

				<aui:input helpMessage="max-word-length-help" label="max-word-length" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_MAX_WORD_LENGTH) %>" type="number" value="<%= similarResultsPortletPreferences.getMaxWordLength() %>">
					<aui:validator name="digits" />
					<aui:validator name="min">1</aui:validator>
				</aui:input>

				<aui:input helpMessage="stop-words-help" label="stop-words" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_STOP_WORDS) %>" type="text" value="<%= similarResultsPortletPreferences.getStopWords() %>" />

				<aui:input helpMessage="min-should-match-help" label="min-should-match" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_MIN_SHOULD_MATCH) %>" type="text" value="<%= similarResultsPortletPreferences.getMinShouldMatch() %>" />

				<aui:input helpMessage="term-boost-help" label="term-boost" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_TERM_BOOST) %>" type="number" value="<%= similarResultsPortletPreferences.getTermBoost() %>">
					<aui:validator name="min">1</aui:validator>
				</aui:input>

				<aui:input helpMessage="enter-the-key-of-an-alternate-search-this-widget-is-participating-on" label="federated-search-key" name="<%= PortletPreferencesJspUtil.getInputName(similarResultsPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY) %>" type="text" value="<%= similarResultsPortletPreferences.getFederatedSearchKey() %>" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>