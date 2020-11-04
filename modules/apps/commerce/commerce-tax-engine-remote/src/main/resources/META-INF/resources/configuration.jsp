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

RemoteCommerceTaxConfiguration remoteCommerceTaxConfiguration = (RemoteCommerceTaxConfiguration)request.getAttribute(RemoteCommerceTaxConfiguration.class.getName());
%>

<portlet:actionURL name="editRemoteCommerceTaxConfiguration" var="editRemoteCommerceTaxConfigurationURL" />

<aui:form action="<%= editRemoteCommerceTaxConfigurationURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<div class="lfr-form-content">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input id="tax-value-endpoint-url" label="tax-value-endpoint-url" name="settings--taxValueEndpointURL--" required="<%= true %>" type="url" value="<%= remoteCommerceTaxConfiguration.taxValueEndpointURL() %>" />

				<aui:input id="tax-value-endpoint-authorization-token" label="tax-value-endpoint-authorization-token" name="settings--taxValueEndpointAuthorizationToken--" type="input" value="<%= remoteCommerceTaxConfiguration.taxValueEndpointAuthorizationToken() %>" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>