<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DepotApplicationDisplayContext depotApplicationDisplayContext = (DepotApplicationDisplayContext)request.getAttribute(DepotAdminWebKeys.DEPOT_APPLICATION_DISPLAY_CONTEXT);
%>

<clay:container-fluid
	cssClass="pt-4"
>
	<div class="alert alert-info">
		<span class="alert-indicator">
			<svg class="lexicon-icon lexicon-icon-info-circle" focusable="false" role="presentation">
				<use xlink:href="<%= themeDisplay.getPathThemeSpritemap() %>#info-circle" />
			</svg>
		</span>

		<strong class="lead">Info:</strong><%= depotApplicationDisplayContext.getMessage() %>
	</div>
</clay:container-fluid>