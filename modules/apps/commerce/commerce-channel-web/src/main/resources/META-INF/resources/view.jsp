<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannelDisplayContext commerceChannelDisplayContext = (CommerceChannelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<frontend-data-set:classic-display
	contextParams="<%= new HashMap<>() %>"
	creationMenu="<%= commerceChannelDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= CommerceChannelFDSNames.CHANNEL %>"
	id="<%= CommerceChannelFDSNames.CHANNEL %>"
	itemsPerPage="<%= 10 %>"
	style="fluid"
/>