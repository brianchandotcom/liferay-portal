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