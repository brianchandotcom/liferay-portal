<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= DuplicateCommerceDiscountExternalReferenceCodeException.class %>" message="please-enter-a-unique-external-reference-code" />
<liferay-ui:error exception="<%= DuplicateCommercePriceEntryExternalReferenceCodeException.class %>" message="please-enter-a-unique-external-reference-code" />
<liferay-ui:error exception="<%= DuplicateCommercePriceListExternalReferenceCodeException.class %>" message="please-enter-a-unique-external-reference-code" />
<liferay-ui:error exception="<%= DuplicateCommercePricingClassExternalReferenceCodeException.class %>" message="please-enter-a-unique-external-reference-code" />
<liferay-ui:error exception="<%= DuplicateCommerceTierPriceEntryExternalReferenceCodeException.class %>" message="please-enter-a-unique-external-reference-code" />
<liferay-ui:error exception="<%= NoSuchPricingClassException.class %>" message="the-product-group-could-not-be-found" />

<liferay-ui:error-principal />