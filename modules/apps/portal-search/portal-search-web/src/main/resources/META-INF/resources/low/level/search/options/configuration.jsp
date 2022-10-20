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
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.low.level.search.options.portlet.action.ConfigurationDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.low.level.search.options.portlet.preferences.LowLevelSearchOptionsPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.low.level.search.options.portlet.preferences.LowLevelSearchOptionsPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<portlet:defineObjects />

<%
ConfigurationDisplayContext configurationDisplayContext = (ConfigurationDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

LowLevelSearchOptionsPortletPreferences lowLevelSearchOptionsPortletPreferences = new LowLevelSearchOptionsPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
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
				<aui:select helpMessage="connection-id-help[web]" label="connection-id" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_CONNECTION_ID) %>">

					<%
					for (String connectionId : configurationDisplayContext.getConnectionIds()) {
					%>

						<aui:option label="<%= HtmlUtil.escape(connectionId) %>" selected="<%= connectionId.equals(lowLevelSearchOptionsPortletPreferences.getConnectionIdString()) %>" value="<%= connectionId %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input helpMessage="indexes-help" label="indexes" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_INDEXES) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getIndexesString() %>" />

				<aui:input helpMessage="fields-to-return-help" label="fields-to-return" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_FIELDS_TO_RETURN) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getFieldsToReturnString() %>" />

				<aui:input helpMessage="contributors-to-include-help" label="contributors-to-include" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_CONTRIBUTORS_TO_INCLUDE) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getContributorsToIncludeString() %>" />

				<aui:input helpMessage="contributors-to-exclude-help" label="contributors-to-exclude" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_CONTRIBUTORS_TO_EXCLUDE) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getContributorsToExcludeString() %>" />

				<aui:input helpMessage="enter-the-key-of-an-alternate-search-this-widget-is-participating-on-if-not-set-widget-participates-on-default-search" label="federated-search-key" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getFederatedSearchKeyString() %>" />
			</aui:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id='<%= liferayPortletResponse.getNamespace() + "attributesId" %>'
				label="attributes"
			>

				<%
				JSONArray attributesJSONArray = lowLevelSearchOptionsPortletPreferences.getAttributesJSONArray();

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

				<aui:input cssClass="fields-input" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_ATTRIBUTES) %>" type="hidden" value="<%= lowLevelSearchOptionsPortletPreferences.getAttributesString() %>" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
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
				(item) => {
					return !item.getAttribute('hidden');
				}
			);

			fieldFormRows.forEach((item) => {
				fields.push({
					key: item.querySelector('.key-input').value,
					value: item.querySelector('.value-input').value,
				});
			});

			document.getElementById(
				'<%= liferayPortletResponse.getNamespace() + LowLevelSearchOptionsPortletPreferences.PREFERENCE_ATTRIBUTES %>'
			).value = JSON.stringify(fields);

			submitForm(form);
		}
	}
</aui:script>