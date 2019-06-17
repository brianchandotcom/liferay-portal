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

<%@ include file="/tag/facet/init.jsp" %>

<%
TagFacetPortletPreferences tagFacetPortletPreferences = new TagFacetPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
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
			<aui:input label="tag-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(TagFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME) %>" value="<%= tagFacetPortletPreferences.getParameterName() %>" />

			<aui:select label="display-style" name="<%= PortletPreferencesJspUtil.getInputName(TagFacetPortletPreferences.PREFERENCE_KEY_DISPLAY_STYLE) %>">
				<aui:option label="cloud" selected="<%= tagFacetPortletPreferences.isDisplayStyleCloud() %>" />
				<aui:option label="list" selected="<%= tagFacetPortletPreferences.isDisplayStyleList() %>" />
			</aui:select>

			<aui:input label="max-terms" name="<%= PortletPreferencesJspUtil.getInputName(TagFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS) %>" value="<%= tagFacetPortletPreferences.getMaxTerms() %>" />

			<aui:input label="frequency-threshold" name="<%= PortletPreferencesJspUtil.getInputName(TagFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD) %>" value="<%= tagFacetPortletPreferences.getFrequencyThreshold() %>" />

			<aui:input label="display-frequencies" name="<%= PortletPreferencesJspUtil.getInputName(TagFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE) %>" type="checkbox" value="<%= tagFacetPortletPreferences.isFrequenciesVisible() %>" />
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>