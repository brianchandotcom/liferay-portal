<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:header
	showBackURL="<%= false %>"
	title="error"
/>

<liferay-ui:error exception="<%= NoSuchRecordException.class %>" message="the-record-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchRecordSetException.class %>" message="the-recordSet-could-not-be-found" />
<liferay-ui:error exception="<%= PortletPreferencesException.MustBeStrict.class %>" message="portlet-preferences-are-not-configured-properly" />

<liferay-ui:error-principal />