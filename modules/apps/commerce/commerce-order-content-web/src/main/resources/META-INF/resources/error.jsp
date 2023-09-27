<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= CommerceOrderImporterTypeException.class %>">

	<%
	String commerceOrderImporterTypeKey = (String)SessionErrors.get(renderRequest, CommerceOrderImporterTypeException.class);
	%>

	<c:choose>
		<c:when test="<%= Validator.isNull(commerceOrderImporterTypeKey) %>">
			<liferay-ui:message key="the-import-process-failed" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments="<%= commerceOrderImporterTypeKey %>" key="the-x-could-not-be-imported" />
		</c:otherwise>
	</c:choose>
</liferay-ui:error>

<liferay-ui:error exception="<%= NoSuchEntryException.class %>" message="to-add-a-product-to-an-order,-first-select-an-account" />
<liferay-ui:error exception="<%= NoSuchOrderException.class %>" message="the-order-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchOrderNoteException.class %>" message="the-note-could-not-be-found" />

<liferay-ui:error-principal />