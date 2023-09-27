<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
<liferay-ui:error exception="<%= NoSuchCProductException.class %>" message="the-product-could-not-be-found" />

<liferay-ui:error-principal />