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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
TypeFacetPortletPreferences typeFacetPortletPreferences = new com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortletPreferencesImpl(java.util.Optional.of(portletPreferences));
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
			<aui:input label="type-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME) %>" value="<%= typeFacetPortletPreferences.getParameterName() %>" />

			<aui:input label="frequency-threshold" name="<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD) %>" value="<%= typeFacetPortletPreferences.getFrequencyThreshold() %>" />

			<aui:input label="display-frequencies" name="<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE) %>" type="checkbox" value="<%= typeFacetPortletPreferences.isFrequenciesVisible() %>" />

			<aui:input name="<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_ASSET_TYPES) %>" type="hidden" value="<%= typeFacetPortletPreferences.getAssetTypesString() %>" />

			<liferay-ui:input-move-boxes
				leftBoxName="currentAssetTypes"
				leftList="<%= typeFacetPortletPreferences.getCurrentAssetTypes(themeDisplay.getCompanyId(), themeDisplay.getLocale()) %>"
				leftTitle="current"
				rightBoxName="availableAssetTypes"
				rightList="<%= typeFacetPortletPreferences.getAvailableAssetTypes(themeDisplay.getCompanyId(), themeDisplay.getLocale()) %>"
				rightTitle="available"
			/>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	var form = document.<portlet:namespace />fm;

	var currentAssetTypes = Liferay.Util.getFormElement(form, 'currentAssetTypes');

	if (currentAssetTypes) {
		form.addEventListener('submit', function(event) {
			event.preventDefault();

			var data = {};

			data[
				'<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_ASSET_TYPES) %>'
			] = Liferay.Util.listSelect(currentAssetTypes);

			Liferay.Util.postForm(form, {data: data});
		});
	}
</script>