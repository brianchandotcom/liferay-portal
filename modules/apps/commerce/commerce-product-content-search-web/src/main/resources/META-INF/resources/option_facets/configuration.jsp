<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPOptionsSearchFacetDisplayContext cpOptionsSearchFacetDisplayContext = (CPOptionsSearchFacetDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
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
		<liferay-ui:error key="exceededMaxOptionsLimit" message='<%= LanguageUtil.format(request, "maximum-options-cannot-exceed-x", 100) %>' />
		<liferay-ui:error key="exceededMaxTermsLimit" message='<%= LanguageUtil.format(request, "maximum-terms-cannot-exceed-x", 100) %>' />

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="display-settings"
		>
			<div class="display-template">
				<liferay-template:template-selector
					className="<%= CPOptionsSearchFacetDisplayContext.class.getName() %>"
					displayStyle="<%= cpOptionsSearchFacetDisplayContext.getDisplayStyle() %>"
					displayStyleGroupKey="<%= cpOptionsSearchFacetDisplayContext.getDisplayStyleGroupKey() %>"
					refreshURL="<%= configurationRenderURL %>"
					showEmptyOption="<%= true %>"
				/>
			</div>
		</liferay-frontend:fieldset>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="advanced-configuration"
		>
			<aui:input label="max-options" name="preferences--maxOptions--" value="<%= cpOptionsSearchFacetDisplayContext.getMaxOptions() %>" />
			<aui:input label="max-terms" name="preferences--maxTerms--" value="<%= cpOptionsSearchFacetDisplayContext.getMaxTerms() %>" />
			<aui:input label="frequency-threshold" name="preferences--frequencyThreshold--" value="<%= cpOptionsSearchFacetDisplayContext.getFrequencyThreshold() %>" />
			<aui:input label="display-frequencies" name="preferences--frequenciesVisible--" type="checkbox" value="<%= cpOptionsSearchFacetDisplayContext.isFrequenciesVisible() %>" />
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>