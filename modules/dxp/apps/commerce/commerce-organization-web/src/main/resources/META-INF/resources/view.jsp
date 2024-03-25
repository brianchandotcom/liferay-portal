<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationDisplayContext commerceOrganizationDisplayContext = (CommerceOrganizationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="org-chart-root<%= commerceOrganizationDisplayContext.isAdminPortlet() ? " admin-portlet" : "" %>" id="<portlet:namespace />org-chart-root">
	<span aria-hidden="true" class="loading-animation loading-animation-sm"></span>

	<react:component
		module="{OrganizationChart} from commerce-organization-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"namespace", randomNamespace
			).put(
				"pathImage", themeDisplay.getPathImage()
			).put(
				"rootOrganizationId", commerceOrganizationDisplayContext.getRootOrganizationId()
			).put(
				"selectLogoURL", commerceOrganizationDisplayContext.getSelectLogoURL()
			).put(
				"spritemap", themeDisplay.getPathThemeSpritemap()
			).build()
		%>'
	/>
</div>