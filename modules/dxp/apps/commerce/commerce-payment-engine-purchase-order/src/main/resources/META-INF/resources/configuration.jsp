<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
PurchaseOrderCommercePaymentEngineGroupServiceConfiguration purchaseOrderCommercePaymentEngineGroupServiceConfiguration = (PurchaseOrderCommercePaymentEngineGroupServiceConfiguration)request.getAttribute(PurchaseOrderCommercePaymentEngineGroupServiceConfiguration.class.getName());

String messageXml = null;

LocalizedValuesMap messageLocalizedValuesMap = purchaseOrderCommercePaymentEngineGroupServiceConfiguration.message();

if (messageLocalizedValuesMap != null) {
	messageXml = LocalizationUtil.getXml(messageLocalizedValuesMap, "message");
}
%>

<aui:fieldset>
	<aui:field-wrapper label="message">
		<liferay-ui:input-localized
			editorName="alloyeditor"
			fieldPrefix="settings"
			fieldPrefixSeparator="--"
			name="message"
			type="editor"
			xml="<%= messageXml %>"
		/>
	</aui:field-wrapper>
</aui:fieldset>