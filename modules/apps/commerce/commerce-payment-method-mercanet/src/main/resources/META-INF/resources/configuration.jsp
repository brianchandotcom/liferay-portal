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
MercanetGroupServiceConfiguration mercanetCommercePaymentEngineGroupServiceConfiguration = (MercanetGroupServiceConfiguration)request.getAttribute(MercanetGroupServiceConfiguration.class.getName());
%>

<portlet:actionURL name="editMercanetCommercePaymentMethodConfiguration" var="editCommercePaymentMethodActionURL" />

<aui:form action="<%= editCommercePaymentMethodActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="commerceChannelId" type="hidden" value='<%= ParamUtil.getLong(request, "commerceChannelId") %>' />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<div class="alert alert-info">
		<liferay-ui:message key="mercanet-configuration-help" />
	</div>

	<commerce-ui:panel>
		<commerce-ui:info-box
			title="authentication"
		>
			<aui:input label="merchant-id" name="settings--merchantId--" value="<%= mercanetCommercePaymentEngineGroupServiceConfiguration.merchantId() %>" />

			<aui:input label="secret-key" name="settings--secretKey--" value="<%= mercanetCommercePaymentEngineGroupServiceConfiguration.secretKey() %>" />

			<aui:input label="key-version" name="settings--keyVersion--" value="<%= mercanetCommercePaymentEngineGroupServiceConfiguration.keyVersion() %>" />

			<aui:select name="settings--environment--">

				<%
				for (String environment : MercanetCommercePaymentMethodConstants.ENVIRONMENTS) {
				%>

					<aui:option label="<%= environment %>" selected="<%= environment.equals(mercanetCommercePaymentEngineGroupServiceConfiguration.environment()) %>" value="<%= environment %>" />

				<%
				}
				%>

			</aui:select>
		</commerce-ui:info-box>
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>