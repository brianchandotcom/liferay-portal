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
CPOptionsSearchFacetDisplayContext cpOptionsSearchFacetDisplayContext = new CPOptionsSearchFacetDisplayContext(request);
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
		<liferay-ui:error key="exceededMaxTermsLimit" message="maximum-terms-cannot-exceed-100" />

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				label="display-settings"
			>
				<div class="display-template">
					<liferay-template:template-selector
						className="<%= CPOptionsSearchFacetDisplayContext.class.getName() %>"
						displayStyle='<%= portletPreferences.getValue("displayStyle", "") %>'
						displayStyleGroupId="<%= cpOptionsSearchFacetDisplayContext.getDisplayStyleGroupId() %>"
						refreshURL="<%= configurationRenderURL %>"
						showEmptyOption="<%= true %>"
					/>
				</div>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				label="advanced-configuration"
			>
				<aui:input label="max-terms" name="preferences--maxTerms--" value='<%= GetterUtil.getInteger(portletPreferences.getValue("maxTerms", null), 10) %>' />
				<aui:input label="frequency-threshold" name="preferences--frequencyThreshold--" value='<%= GetterUtil.getInteger(portletPreferences.getValue("frequencyThreshold", null), 1) %>' />
				<aui:input label="display-frequencies" name="preferences--frequenciesVisible--" type="checkbox" value='<%= GetterUtil.getBoolean(portletPreferences.getValue("frequenciesVisible", null), true) %>' />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>