<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String ppid = ParamUtil.getString(request, "p_p_id");
%>

<c:choose>
	<c:when test="<%= themeDisplay.isStatePopUp() || themeDisplay.isWidget() %>">

		<%
		String templateContent = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());

		if (Validator.isNotNull(templateContent)) {
			HttpServletRequest originalHttpServletRequest = (HttpServletRequest)request.getAttribute(PortletLayoutTypeControllerWebKeys.ORIGINAL_HTTP_SERVLET_REQUEST);

			String templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";

			RuntimePageUtil.processTemplate(originalHttpServletRequest, response, ppid, templateId, templateContent, LayoutTemplateLocalServiceUtil.getLangType("pop_up", true, theme.getThemeId()));
		}
		%>

	</c:when>
	<c:when test="<%= layoutTypePortlet.hasStateMax() && Validator.isNotNull(ppid) %>">
		<liferay-layout:render-state-max-layout-structure />
	</c:when>
	<c:otherwise>
		<style type="text/css">
			.master-layout-fragment .portlet-header {
				display: none;
			}
		</style>

		<%
		PortletLayoutDisplayContext portletLayoutDisplayContext = (PortletLayoutDisplayContext)request.getAttribute(PortletLayoutTypeControllerWebKeys.PORTLET_LAYOUT_DISPLAY_CONTEXT);
		%>

		<liferay-layout:render-layout-structure
			layoutStructure="<%= portletLayoutDisplayContext.getLayoutStructure(themeDisplay.getLayout()) %>"
		/>
	</c:otherwise>
</c:choose>

<liferay-layout:layout-common
	displaySessionMessages="<%= true %>"
/>