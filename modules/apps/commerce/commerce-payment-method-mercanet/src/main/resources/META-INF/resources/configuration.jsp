<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
MercanetGroupServiceConfiguration mercanetGroupServiceConfiguration = (MercanetGroupServiceConfiguration)request.getAttribute(MercanetGroupServiceConfiguration.class.getName());
%>

<portlet:actionURL name="/commerce_payment_methods/edit_mercanet_commerce_payment_method_configuration" var="editCommercePaymentMethodActionURL" />

<aui:form action="<%= editCommercePaymentMethodActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="commerceChannelId" type="hidden" value='<%= ParamUtil.getLong(request, "commerceChannelId") %>' />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<div class="alert alert-info">
		<liferay-ui:message key="mercanet-configuration-help" />
	</div>

	<commerce-ui:panel>
		<commerce-ui:info-box
			title='<%= LanguageUtil.get(request, "authentication") %>'
		>
			<aui:input label="merchant-id" name="settings--merchantId--" value="<%= mercanetGroupServiceConfiguration.merchantId() %>" />

			<%
			String secretKey = mercanetGroupServiceConfiguration.secretKey();

			if (Validator.isNotNull(secretKey)) {
				secretKey = Portal.TEMP_OBFUSCATION_VALUE;
			}
			%>

			<aui:input label="secret-key" name="settings--secretKey--" type="password" value="<%= secretKey %>" />

			<aui:input label="key-version" name="settings--keyVersion--" value="<%= mercanetGroupServiceConfiguration.keyVersion() %>" />

			<aui:select name="settings--environment--">

				<%
				for (String environment : MercanetCommercePaymentMethodConstants.ENVIRONMENTS) {
				%>

					<aui:option label="<%= environment %>" selected="<%= environment.equals(mercanetGroupServiceConfiguration.environment()) %>" value="<%= environment %>" />

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