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
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

long powwowServerId = ParamUtil.getLong(request, "powwowServerId");

PowwowServer powwowServer = PowwowServerLocalServiceUtil.fetchPowwowServer(powwowServerId);
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= (powwowServer == null) %>"
	title='<%= (powwowServer != null) ? powwowServer.getName() : "new-server" %>'
/>

<liferay-portlet:actionURL name="updatePowwowServer" var="editURL" />

<aui:form action="<%= editURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="powwowServerId" type="hidden" value="<%= String.valueOf(powwowServerId) %>" />

	<aui:model-context bean="<%= powwowServer %>" model="<%= PowwowServer.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:select label="provider-type" name="providerType">

			<%
			String defaultProviderType = StringPool.BLANK;

			if (powwowServer != null) {
				if (ArrayUtil.contains(PortletPropsValues.POWWOW_PROVIDER_TYPES, powwowServer.getProviderType())) {
					defaultProviderType = powwowServer.getProviderType();
				}
			}
			else if (PortletPropsValues.POWWOW_PROVIDER_TYPES.length != 0) {
				defaultProviderType = PortletPropsValues.POWWOW_PROVIDER_TYPES[0];
			}

			for (String providerType : PortletPropsValues.POWWOW_PROVIDER_TYPES) {
			%>

				<aui:option selected="<%= defaultProviderType.equals(providerType) %>" value="<%= providerType %>"><%= PowwowServiceProviderUtil.getPowwowServiceProviderName(providerType) %></aui:option>

			<%
			}
			%>

		</aui:select>

		<aui:input cssClass="optional-field" label="api-url" name="url" />

		<aui:input cssClass="optional-field" label="api-key" name="apiKey" />

		<aui:input cssClass="optional-field" name="secret" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	function showProviderTypeFields() {
		A.all('.optional-field').get('parentNode').hide();

		var selectedProviderType = A.one('#<portlet:namespace />providerType option:selected').val();

		<%
		for (String providerType : PortletPropsValues.POWWOW_PROVIDER_TYPES) {
		%>

			if (selectedProviderType == '<%= providerType %>') {
				if (<%= PowwowServiceProviderUtil.isFieldURLRequired(providerType) %>) {
					A.one('#<portlet:namespace />url').get('parentNode').show();
				}

				if (<%= PowwowServiceProviderUtil.isFieldAPIKeyRequired(providerType) %>) {
					A.one('#<portlet:namespace />apiKey').get('parentNode').show();
				}

				if (<%= PowwowServiceProviderUtil.isFieldSecretRequired(providerType) %>) {
					A.one('#<portlet:namespace />secret').get('parentNode').show();
				}
			}

		<%
		}
		%>

	}

	var providerType = A.one('#<portlet:namespace />providerType');

	if (providerType) {
		providerType.on(
			'change',
			function(event) {
				A.all('.optional-field').val('');

				showProviderTypeFields();
			}
		);
	}

	showProviderTypeFields();
</aui:script>