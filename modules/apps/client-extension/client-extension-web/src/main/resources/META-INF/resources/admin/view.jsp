<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
ClientExtensionAdminDisplayContext clientExtensionAdminDisplayContext = (ClientExtensionAdminDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.CLIENT_EXTENSION_ADMIN_DISPLAY_CONTEXT);
%>

<frontend-data-set:classic-display
	actionParameterName="externalReferenceCode"
	creationMenu="<%= clientExtensionAdminDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= ClientExtensionAdminFDSNames.CLIENT_EXTENSION_TYPES %>"
	id="<%= ClientExtensionAdminFDSNames.CLIENT_EXTENSION_TYPES %>"
	itemsPerPage="<%= 10 %>"
	selectedItemsKey="externalReferenceCode"
	style="fluid"
	uniformActionsDisplay="<%= true %>"
/>