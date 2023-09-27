<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= NoSuchCPOptionCategoryException.class %>" message="the-specification-group-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchCPOptionException.class %>" message="the-option-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchCPOptionValueException.class %>" message="the-option-value-could-not-be-found" />

<liferay-ui:error-principal />