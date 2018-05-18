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

<liferay-ui:error-header />

<liferay-ui:error exception="<%= CPDefinitionProductTypeNameException.class %>" message="please-select-a-valid-type" />
<liferay-ui:error exception="<%= NoSuchCPAttachmentFileEntryException.class %>" message="the-attachment-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchCPDefinitionException.class %>" message="the-product-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchCPDefinitionLinkException.class %>" message="the-product-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchCPDefinitionOptionRelException.class %>" message="the-option-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchCPDefinitionOptionValueRelException.class %>" message="the-option-value-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchCPInstanceException.class %>" message="the-sku-could-not-be-found" />

<liferay-ui:error-principal />