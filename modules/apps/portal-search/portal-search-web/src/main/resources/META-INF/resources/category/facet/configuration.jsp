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

<%@ include file="/category/facet/init.jsp" %>

<%
CategoryFacetPortletPreferences categoryFacetPortletPreferences = new CategoryFacetPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
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
			<aui:input label="category-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME) %>" value="<%= categoryFacetPortletPreferences.getParameterName() %>" />

			<aui:select label="display-style" name="<%= PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_KEY_DISPLAY_STYLE) %>">
				<aui:option label="cloud" selected="<%= categoryFacetPortletPreferences.isDisplayStyleCloud() %>" />
				<aui:option label="list" selected="<%= categoryFacetPortletPreferences.isDisplayStyleList() %>" />
			</aui:select>

			<aui:input label="max-terms" name="<%= PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS) %>" value="<%= categoryFacetPortletPreferences.getMaxTerms() %>" />

			<aui:input label="frequency-threshold" name="<%= PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD) %>" value="<%= categoryFacetPortletPreferences.getFrequencyThreshold() %>" />

			<aui:input label="display-frequencies" name="<%= PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE) %>" type="checkbox" value="<%= categoryFacetPortletPreferences.isFrequenciesVisible() %>" />
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>