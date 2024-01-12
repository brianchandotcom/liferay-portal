<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryDisplayContext<CommerceCheckoutStepCET> editClientExtensionEntryDisplayContext = (EditClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

CommerceCheckoutStepCET commerceCheckoutStepCET = editClientExtensionEntryDisplayContext.getCET();
%>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="action-url" name="actionURL" required="<%= true %>" type="text" value="<%= commerceCheckoutStepCET.getActionURL() %>" />

	<div class="form-text">
		<liferay-ui:message key="this-action-url-executes-the-checkout-step-logic" />
	</div>
</aui:field-wrapper>

<aui:input label="active" name="active" type="checkbox" value="<%= commerceCheckoutStepCET.getActive() %>" />

<aui:input label="checkout-step-label" name="checkoutStepLabel" required="<%= true %>" type="text" value="<%= commerceCheckoutStepCET.getCheckoutStepLabel() %>" />

<aui:input label="checkout-step-name" name="checkoutStepName" required="<%= true %>" type="text" value="<%= commerceCheckoutStepCET.getCheckoutStepName() %>" />

<aui:input label="checkout-step-order" name="checkoutStepOrder" required="<%= true %>" type="text" value="<%= commerceCheckoutStepCET.getCheckoutStepOrder() %>" />

<aui:input label="oauth2-application-external-reference-code" name="oAuth2ApplicationExternalReferenceCode" required="<%= true %>" type="text" value="<%= commerceCheckoutStepCET.getOAuth2ApplicationExternalReferenceCode() %>" />

<aui:input label="order" name="order" type="checkbox" value="<%= commerceCheckoutStepCET.getOrder() %>" />

<aui:field-wrapper cssClass="form-group">
	<aui:input label="ready-url" name="readyURL" required="<%= true %>" type="text" value="<%= commerceCheckoutStepCET.getReadyURL() %>" />

	<div class="form-text">
		<liferay-ui:message key="this-url-checks-the-checkout-step-logic-is-available" />
	</div>
</aui:field-wrapper>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="render-url" name="renderURL" required="<%= true %>" type="text" value="<%= commerceCheckoutStepCET.getRenderURL() %>" />

	<div class="form-text">
		<liferay-ui:message key="this-render-url-renders-the-ui-for-this-checkout-step" />
	</div>
</aui:field-wrapper>

<aui:input label="senna-diabled" name="sennaDisabled" type="checkbox" value="<%= commerceCheckoutStepCET.getSennaDisabled() %>" />

<aui:input label="show-controls" name="showControls" type="checkbox" value="<%= commerceCheckoutStepCET.getShowControls() %>" />

<aui:input label="visible" name="visible" type="checkbox" value="<%= commerceCheckoutStepCET.getVisible() %>" />