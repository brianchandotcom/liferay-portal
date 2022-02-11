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

<%@ include file="/init.jsp" %>

<%
CPSpecificationOptionFacetsDisplayContext cpSpecificationOptionFacetsDisplayContext = (CPSpecificationOptionFacetsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPSpecificationOptionFacetPortletInstanceConfiguration cpSpecificationOptionFacetPortletInstanceConfiguration = cpSpecificationOptionFacetsDisplayContext.getCPSpecificationOptionFacetPortletInstanceConfiguration();

CPSpecificationOptionFacetPortletPreferences cpSpecificationOptionFacetPortletPreferences = new CPSpecificationOptionFacetPortletPreferences(java.util.Optional.ofNullable(portletPreferences));
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
		<liferay-ui:error key="exceededMaxTermsLimit" message="cp-specification-option-facet-portlet-instance-configuration-exceeded-max-terms-limit" />

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				label="display-settings"
			>
				<div class="display-template">
					<liferay-template:template-selector
						className="<%= CPSpecificationOptionsSearchFacetTermDisplayContext.class.getName() %>"
						displayStyle="<%= cpSpecificationOptionFacetPortletInstanceConfiguration.displayStyle() %>"
						displayStyleGroupId="<%= cpSpecificationOptionFacetsDisplayContext.getDisplayStyleGroupId() %>"
						refreshURL="<%= configurationRenderURL %>"
						showEmptyOption="<%= true %>"
					/>
				</div>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				label="advanced-configuration"
			>
				<aui:input label="max-terms" name="<%= PortletPreferencesJspUtil.getInputName(CPSpecificationOptionFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS) %>" value="<%= cpSpecificationOptionFacetPortletPreferences.getMaxTerms() %>" />
				<aui:input label="frequency-threshold" name="<%= PortletPreferencesJspUtil.getInputName(CPSpecificationOptionFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD) %>" value="<%= cpSpecificationOptionFacetPortletPreferences.getFrequencyThreshold() %>" />
				<aui:input label="display-frequencies" name="<%= PortletPreferencesJspUtil.getInputName(CPSpecificationOptionFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE) %>" type="checkbox" value="<%= cpSpecificationOptionFacetPortletPreferences.isFrequenciesVisible() %>" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>