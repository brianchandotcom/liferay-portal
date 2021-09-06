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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/template" prefix="liferay-template" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.search.results.configuration.SearchResultsPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<portlet:defineObjects />

<%
SearchResultsPortletDisplayContext searchResultsPortletDisplayContext = new SearchResultsPortletDisplayContext(request);

SearchResultsPortletInstanceConfiguration searchResultsPortletInstanceConfiguration = searchResultsPortletDisplayContext.getSearchResultsPortletInstanceConfiguration();

SearchResultsPortletPreferences searchResultsPortletPreferences = new SearchResultsPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
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
						className="<%= SearchResultSummaryDisplayContext.class.getName() %>"
						displayStyle="<%= searchResultsPortletInstanceConfiguration.displayStyle() %>"
						displayStyleGroupId="<%= searchResultsPortletDisplayContext.getDisplayStyleGroupId() %>"
						refreshURL="<%= configurationRenderURL %>"
					/>
				</div>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				label="advanced-configuration"
			>
				<aui:input label="enable-highlighting" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_KEY_HIGHLIGHT_ENABLED) %>" type="checkbox" value="<%= searchResultsPortletPreferences.isHighlightEnabled() %>" />

				<aui:input helpMessage="display-selected-result-in-context-help" label="display-selected-result-in-context" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_KEY_VIEW_IN_CONTEXT) %>" type="checkbox" value="<%= searchResultsPortletPreferences.isViewInContext() %>" />

				<aui:input helpMessage="display-results-in-document-form-help" label="display-results-in-document-form" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_KEY_DISPLAY_IN_DOCUMENT_FORM) %>" type="checkbox" value="<%= searchResultsPortletPreferences.isDisplayInDocumentForm() %>" />

				<aui:input label="pagination-start-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_KEY_PAGINATION_START_PARAMETER_NAME) %>" value="<%= searchResultsPortletPreferences.getPaginationStartParameterName() %>" />

				<aui:input label="pagination-delta" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_KEY_PAGINATION_DELTA) %>" type="text" value="<%= searchResultsPortletPreferences.getPaginationDelta() %>" />

				<aui:input label="pagination-delta-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_KEY_PAGINATION_DELTA_PARAMETER_NAME) %>" type="text" value="<%= searchResultsPortletPreferences.getPaginationDeltaParameterName() %>" />

				<aui:input helpMessage="fields-to-display-help" label="fields-to-display" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_KEY_FIELDS_TO_DISPLAY) %>" type="text" value="<%= searchResultsPortletPreferences.getFieldsToDisplayString() %>" />

				<aui:input helpMessage="enter-the-key-of-an-alternate-search-this-widget-is-participating-on-if-not-set-widget-participates-on-default-search" label="federated-search-key" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY) %>" type="text" value="<%= searchResultsPortletPreferences.getFederatedSearchKeyString() %>" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>