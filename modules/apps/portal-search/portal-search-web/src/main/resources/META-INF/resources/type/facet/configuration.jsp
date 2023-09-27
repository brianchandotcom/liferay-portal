<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/template" prefix="liferay-template" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.object.service.ObjectDefinitionLocalService" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.asset.SearchableAssetClassNamesProvider" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.type.facet.configuration.TypeFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortlet" %><%@
page import="com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
AssetEntriesSearchFacetDisplayContext assetEntriesSearchFacetDisplayContext = (AssetEntriesSearchFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

TypeFacetPortletInstanceConfiguration typeFacetPortletInstanceConfiguration = assetEntriesSearchFacetDisplayContext.getTypeFacetPortletInstanceConfiguration();

ObjectDefinitionLocalService objectDefinitionLocalService = (ObjectDefinitionLocalService)request.getAttribute(ObjectDefinitionLocalService.class.getName());

SearchableAssetClassNamesProvider searchableAssetClassNamesProvider = (SearchableAssetClassNamesProvider)request.getAttribute(SearchableAssetClassNamesProvider.class.getName());

TypeFacetPortletPreferences typeFacetPortletPreferences = new com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortletPreferencesImpl(objectDefinitionLocalService, portletPreferences, searchableAssetClassNamesProvider);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="display-settings"
		>
			<div class="display-template">
				<liferay-template:template-selector
					className="<%= TypeFacetPortlet.class.getName() %>"
					displayStyle="<%= typeFacetPortletInstanceConfiguration.displayStyle() %>"
					displayStyleGroupId="<%= assetEntriesSearchFacetDisplayContext.getDisplayStyleGroupId() %>"
					refreshURL="<%= configurationRenderURL %>"
					showEmptyOption="<%= true %>"
				/>
			</div>
		</liferay-frontend:fieldset>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="advanced-configuration"
		>
			<aui:input label="type-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME) %>" value="<%= typeFacetPortletPreferences.getParameterName() %>" />

			<aui:input label="frequency-threshold" name="<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD) %>" value="<%= typeFacetPortletPreferences.getFrequencyThreshold() %>" />

			<aui:select label="order-terms-by" name="<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_ORDER) %>" value="<%= typeFacetPortletPreferences.getOrder() %>">
				<aui:option label="term-frequency-descending" value="count:desc" />
				<aui:option label="term-frequency-ascending" value="count:asc" />
				<aui:option label="term-value-ascending" value="key:asc" />
				<aui:option label="term-value-descending" value="key:desc" />
			</aui:select>

			<aui:input label="display-frequencies" name="<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE) %>" type="checkbox" value="<%= typeFacetPortletPreferences.isFrequenciesVisible() %>" />

			<aui:input name="<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_ASSET_TYPES) %>" type="hidden" value="<%= typeFacetPortletPreferences.getAssetTypes() %>" />

			<liferay-ui:input-move-boxes
				leftBoxName="currentAssetTypes"
				leftList="<%= typeFacetPortletPreferences.getCurrentAssetTypes(themeDisplay.getCompanyId(), themeDisplay.getLocale()) %>"
				leftTitle="current"
				rightBoxName="availableAssetTypes"
				rightList="<%= typeFacetPortletPreferences.getAvailableAssetTypes(themeDisplay.getCompanyId(), themeDisplay.getLocale()) %>"
				rightTitle="available"
			/>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		var currentAssetTypes = Liferay.Util.getFormElement(
			form,
			'currentAssetTypes'
		);

		var data = {};

		if (currentAssetTypes) {
			data[
				'<%= PortletPreferencesJspUtil.getInputName(TypeFacetPortletPreferences.PREFERENCE_KEY_ASSET_TYPES) %>'
			] = Liferay.Util.getSelectedOptionValues(currentAssetTypes);
		}

		Liferay.Util.postForm(form, {data: data});
	}
</script>