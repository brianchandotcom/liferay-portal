<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);
%>

<liferay-expando:custom-attribute-list
	className="<%= Organization.class.getName() %>"
	classPK="<%= (organization != null) ? organization.getOrganizationId() : 0 %>"
	editable="<%= true %>"
	label="<%= true %>"
/>