<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/learn" prefix="liferay-learn" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/template" prefix="liferay-template" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.date.facet.configuration.DateFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.date.facet.display.context.DateFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.date.facet.portlet.DateFacetPortlet" %><%@
page import="com.liferay.portal.search.web.internal.date.facet.portlet.DateFacetPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.date.facet.portlet.DateFacetPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<portlet:defineObjects />

<%
DateFacetDisplayContext dateFacetDisplayContext = (DateFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

DateFacetPortletInstanceConfiguration dateFacetPortletInstanceConfiguration = dateFacetDisplayContext.getDateFacetPortletInstanceConfiguration();

DateFacetPortletPreferences dateFacetPortletPreferences = new DateFacetPortletPreferencesImpl(portletPreferences);

JSONArray rangesJSONArray = dateFacetPortletPreferences.getRangesJSONArray();
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
		<liferay-ui:error key="unparsableDate" message="unparsable-date" />

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="display-settings"
		>
			<div class="display-template">
				<liferay-template:template-selector
					className="<%= DateFacetPortlet.class.getName() %>"
					displayStyle="<%= dateFacetPortletInstanceConfiguration.displayStyle() %>"
					displayStyleGroupId="<%= dateFacetDisplayContext.getDisplayStyleGroupId() %>"
					refreshURL="<%= configurationRenderURL %>"
				/>
			</div>
		</liferay-frontend:fieldset>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="advanced-configuration"
		>
			<div class="form-group">
				<aui:input helpMessage="aggregation-field-help" label="aggregation-field" name="<%= PortletPreferencesJspUtil.getInputName(DateFacetPortletPreferences.PREFERENCE_KEY_AGGREGATION_FIELD) %>" value="<%= dateFacetPortletPreferences.getAggregationField() %>" wrapperCssClass="c-mb-0" />

				<div class="form-feedback-group">
					<div class="form-text">
						<liferay-ui:message key="aggregation-field-input-help" />

						<liferay-learn:message
							key="date-facet"
							resource="portal-search-web"
						/>
					</div>
				</div>
			</div>

			<aui:input helpMessage="custom-heading-help" label="custom-heading" name="<%= PortletPreferencesJspUtil.getInputName(DateFacetPortletPreferences.PREFERENCE_KEY_CUSTOM_HEADING) %>" value="<%= dateFacetPortletPreferences.getCustomHeading() %>" />

			<aui:input helpMessage="custom-parameter-name-help" label="custom-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(DateFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME) %>" value="<%= dateFacetPortletPreferences.getParameterName() %>" />

			<aui:input label="frequency-threshold" name="<%= PortletPreferencesJspUtil.getInputName(DateFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD) %>" value="<%= dateFacetPortletPreferences.getFrequencyThreshold() %>" />

			<aui:select label="order-terms-by" name="<%= PortletPreferencesJspUtil.getInputName(DateFacetPortletPreferences.PREFERENCE_KEY_ORDER) %>" value="<%= dateFacetPortletPreferences.getOrder() %>">
				<aui:option label="ranges-configuration" value="" />
				<aui:option label="term-frequency-descending" value="count:desc" />
				<aui:option label="term-frequency-ascending" value="count:asc" />
			</aui:select>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				label="ranges-configuration"
			>
				<aui:fieldset id='<%= liferayPortletResponse.getNamespace() + "rangesId" %>'>

					<%
					int[] rangesIndexes = new int[rangesJSONArray.length()];

					for (int i = 0; i < rangesJSONArray.length(); i++) {
						rangesIndexes[i] = i;

						JSONObject jsonObject = rangesJSONArray.getJSONObject(i);
					%>

						<div class="lfr-form-row lfr-form-row-inline range-form-row">
							<div class="row-fields">
								<aui:input cssClass="label-input" label="label" name='<%= "label_" + i %>' required="<%= true %>" value='<%= jsonObject.getString("label") %>' wrapperCssClass="c-mb-2" />

								<aui:input cssClass="range-input" label="range" name='<%= "range_" + i %>' required="<%= true %>" value='<%= jsonObject.getString("range") %>' wrapperCssClass="c-mb-3" />
							</div>
						</div>

					<%
					}
					%>

					<aui:input cssClass="ranges-input" name="<%= PortletPreferencesJspUtil.getInputName(DateFacetPortletPreferences.PREFERENCE_KEY_RANGES) %>" type="hidden" value="<%= dateFacetPortletPreferences.getRangesString() %>" />

					<aui:input name="rangesIndexes" type="hidden" value="<%= StringUtil.merge(rangesIndexes) %>" />
				</aui:fieldset>

				<aui:input label="display-frequencies" name="<%= PortletPreferencesJspUtil.getInputName(DateFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE) %>" type="checkbox" value="<%= dateFacetPortletPreferences.isFrequenciesVisible() %>" />

				<aui:input helpMessage="enter-the-key-of-an-alternate-search-this-widget-is-participating-on-if-not-set-widget-participates-on-default-search" label="federated-search-key" name="<%= PortletPreferencesJspUtil.getInputName(DateFacetPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY) %>" type="text" value="<%= dateFacetPortletPreferences.getFederatedSearchKey() %>" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields({
		contentBox: 'fieldset#<portlet:namespace />rangesId',
		fieldIndexes: '<portlet:namespace />rangesIndexes',
		namespace: '<portlet:namespace />',
	}).render();
</aui:script>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"namespace", liferayPortletResponse.getNamespace()
		).build()
	%>'
	module="js/shared/DateFacetConfiguration"
/>