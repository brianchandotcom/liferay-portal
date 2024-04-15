<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

PortalDefaultPermissionsSearchEntry portalDefaultPermissionsSearchEntry = (PortalDefaultPermissionsSearchEntry)row.getObject();
%>

<button class="btn btn-secondary btn-sm" data-qa-id="edit-<%= portalDefaultPermissionsSearchEntry.getLabel() %>" data-url="<%= portalDefaultPermissionsCompanyConfigurationDisplayContext.getEditURL(portalDefaultPermissionsSearchEntry.getClassName()) %>" type="button"><liferay-ui:message key="edit" /></button>