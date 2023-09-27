<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long organizationId = ParamUtil.getLong(request, "organizationId");
%>

<aui:model-context bean="<%= OrganizationServiceUtil.fetchOrganization(organizationId) %>" model="<%= Organization.class %>" />

<aui:input label="comments" name="comments" />