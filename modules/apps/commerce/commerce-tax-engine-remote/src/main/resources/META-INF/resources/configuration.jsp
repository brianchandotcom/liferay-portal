<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<aui:input id="tax-value-endpoint-url" label="tax-value-endpoint-url" name="settings--taxValueEndpointURL--" required="<%= true %>" type="url" value="<%= remoteCommerceTaxConfiguration.taxValueEndpointURL() %>" />

					<aui:input id="tax-value-endpoint-authorization-token" label="tax-value-endpoint-authorization-token" name="settings--taxValueEndpointAuthorizationToken--" type="input" value="<%= remoteCommerceTaxConfiguration.taxValueEndpointAuthorizationToken() %>" />
				</aui:fieldset>
			</div>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>