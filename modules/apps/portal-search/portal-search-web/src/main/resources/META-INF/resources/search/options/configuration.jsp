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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<%@ page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.search.web.internal.search.options.portlet.SearchOptionsPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<portlet:defineObjects />

<%
SearchOptionsPortletPreferences searchOptionsPortletPreferences = new com.liferay.portal.search.web.internal.search.options.portlet.SearchOptionsPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
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
		<liferay-frontend:fieldset-group>
			<aui:fieldset>
				<aui:input helpMessage="allow-empty-searches-help" label="allow-empty-searches" name="<%= PortletPreferencesJspUtil.getInputName(SearchOptionsPortletPreferences.PREFERENCE_KEY_ALLOW_EMPTY_SEARCHES) %>" type="checkbox" value="<%= searchOptionsPortletPreferences.isAllowEmptySearches() %>" />

				<aui:input helpMessage="basic-facet-selection-help" label="basic-facet-selection" name="<%= PortletPreferencesJspUtil.getInputName(SearchOptionsPortletPreferences.PREFERENCE_KEY_BASIC_FACET_SELECTION) %>" type="checkbox" value="<%= searchOptionsPortletPreferences.isBasicFacetSelection() %>" />

				<aui:input helpMessage="federated-search-key-help" label="federated-search-key" name="<%= PortletPreferencesJspUtil.getInputName(SearchOptionsPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY) %>" type="text" value="<%= searchOptionsPortletPreferences.getFederatedSearchKeyString() %>" />
			</aui:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id='<%= liferayPortletResponse.getNamespace() + "attributesId" %>'
				label="attributes"
			>

				<%
				JSONArray attributesJSONArray = searchOptionsPortletPreferences.getAttributesJSONArray();

				for (int i = 0; i < attributesJSONArray.length(); i++) {
					JSONObject jsonObject = attributesJSONArray.getJSONObject(i);
				%>

					<div class="field-form-row lfr-form-row lfr-form-row-inline">
						<div class="row-fields">
							<aui:input cssClass="key-input" label="key" name='<%= "key_" + i %>' value='<%= jsonObject.getString("key") %>' />

							<aui:input cssClass="value-input" label="value" name='<%= "value_" + i %>' value='<%= jsonObject.getString("value") %>' />
						</div>
					</div>

				<%
				}
				%>

				<aui:input cssClass="fields-input" name="<%= PortletPreferencesJspUtil.getInputName(SearchOptionsPortletPreferences.PREFERENCE_ATTRIBUTES) %>" type="hidden" value="<%= searchOptionsPortletPreferences.getAttributesString() %>" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields({
		contentBox: 'fieldset#<portlet:namespace />attributesId',
		namespace: '<portlet:namespace />',
	}).render();
</aui:script>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var fields = [];

			var fieldFormRows = Array.prototype.filter.call(
				document.getElementsByClassName('field-form-row'),
				function (item) {
					return !item.getAttribute('hidden');
				}
			);

			fieldFormRows.forEach(function (item) {
				fields.push({
					key: item.querySelector('.key-input').value,
					value: item.querySelector('.value-input').value,
				});
			});

			document.getElementById(
				'<%= liferayPortletResponse.getNamespace() + SearchOptionsPortletPreferences.PREFERENCE_ATTRIBUTES %>'
			).value = JSON.stringify(fields);

			submitForm(form);
		}
	}
</aui:script>