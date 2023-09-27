<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationDisplayContext commerceOrganizationDisplayContext = (CommerceOrganizationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div id="<portlet:namespace />org-chart-root">
	<span aria-hidden="true" class="loading-animation loading-animation-sm"></span>

	<react:component
		module="js/OrganizationChart"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"rootOrganizationId", commerceOrganizationDisplayContext.getRootOrganizationId()
			).put(
				"spritemap", themeDisplay.getPathThemeSpritemap()
			).build()
		%>'
	/>
</div>