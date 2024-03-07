<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPSpecificationOptionFacetsDisplayContext cpSpecificationOptionFacetsDisplayContext = (CPSpecificationOptionFacetsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
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
		<liferay-ui:error key="exceededMaxSpecificationsLimit" message='<%= LanguageUtil.format(request, "maximum-specifications-cannot-exceed-x", 100) %>' />
		<liferay-ui:error key="exceededMaxTermsLimit" message='<%= LanguageUtil.format(request, "maximum-terms-cannot-exceed-x", 100) %>' />

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="display-settings"
		>
			<div class="display-template">
				<liferay-template:template-selector
					className="<%= CPSpecificationOptionsSearchFacetTermDisplayContext.class.getName() %>"
					displayStyle='<%= portletPreferences.getValue("displayStyle", "") %>'
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
			<aui:input label="max-specifications" name="preferences--maxSpecifications--" value='<%= GetterUtil.getInteger(portletPreferences.getValue("maxSpecifications", null), 10) %>' />
			<aui:input label="max-terms" name="preferences--maxTerms--" value='<%= GetterUtil.getInteger(portletPreferences.getValue("maxTerms", null), 10) %>' />
			<aui:input label="frequency-threshold" name="preferences--frequencyThreshold--" value='<%= GetterUtil.getInteger(portletPreferences.getValue("frequencyThreshold", null), 1) %>' />

			<aui:select label="order-specifications-by" name="preferences--specificationsOrder--" value='<%= portletPreferences.getValue("specificationsOrder", "priority:asc") %>'>
				<aui:option label="specification-group-priority-ascending" value="priority:asc" />
				<aui:option label="specification-group-priority-descending" value="priority:desc" />
				<aui:option label="specification-label-priority-ascending" value="label-priority:asc" />
				<aui:option label="specification-label-priority-descending" value="label-priority:desc" />
			</aui:select>

			<aui:input label="display-frequencies" name="preferences--frequenciesVisible--" type="checkbox" value='<%= GetterUtil.getBoolean(portletPreferences.getValue("frequenciesVisible", null), true) %>' />
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>