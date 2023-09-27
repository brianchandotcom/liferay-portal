<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= NoSuchOrderException.class %>" message="the-order-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchOrderNoteException.class %>" message="the-note-could-not-be-found" />

<liferay-ui:error-principal />