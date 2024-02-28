<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= DuplicateCommerceInventoryWarehouseExternalReferenceCodeException.class %>" message="please-enter-a-unique-external-reference-code" />
<liferay-ui:error exception="<%= NoSuchInventoryWarehouseException.class %>" message="the-warehouse-could-not-be-found" />

<liferay-ui:error-principal />