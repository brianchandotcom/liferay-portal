<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationDisplayContext commerceOrganizationDisplayContext = (CommerceOrganizationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div id="<%= liferayPortletResponse.getNamespace() + "org-chart-root" %>">
	<span aria-hidden="true" class="loading-animation loading-animation-sm"></span>

	<react:component
		module="js/OrganizationChart"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"rootOrganizationId", commerceOrganizationDisplayContext.getRootOrganizationId()
			).put(
				"spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
			).build()
		%>'
	/>
</div>