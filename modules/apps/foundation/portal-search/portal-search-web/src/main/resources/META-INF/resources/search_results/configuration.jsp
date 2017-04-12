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

<%@ include file="/search_results/init.jsp" %>

<portlet:defineObjects />

<%
SearchResultsPortletPreferences searchResultsPortletPreferences = new com.liferay.portal.search.web.internal.searchresults.portlet.SearchResultsPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<aui:input label="enable-highlighting" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_HIGHLIGHT_ENABLED) %>" type="checkbox" value="<%= searchResultsPortletPreferences.isHighlightEnabled() %>" />

			<aui:input helpMessage="display-selected-result-in-context-help" label="display-selected-result-in-context" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_VIEW_IN_CONTEXT) %>" type="checkbox" value="<%= searchResultsPortletPreferences.isViewInContext() %>" />

			<aui:input helpMessage="display-results-in-document-form-help" label="display-results-in-document-form" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_DISPLAY_IN_DOCUMENT_FORM) %>" type="checkbox" value="<%= searchResultsPortletPreferences.isDisplayInDocumentForm() %>" />

			<aui:input label="pagination-start-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_PAGINATION_START_PARAMETER_NAME) %>" value="<%= searchResultsPortletPreferences.getPaginationStartParameterName() %>" />

			<aui:input label="pagination-delta" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_PAGINATION_DELTA) %>" type="text" value="<%= searchResultsPortletPreferences.getPaginationDelta() %>" />

			<aui:input label="pagination-delta-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(SearchResultsPortletPreferences.PREFERENCE_PAGINATION_DELTA_PARAMETER_NAME) %>" type="text" value="<%= searchResultsPortletPreferences.getPaginationDeltaParameterName() %>" />
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>