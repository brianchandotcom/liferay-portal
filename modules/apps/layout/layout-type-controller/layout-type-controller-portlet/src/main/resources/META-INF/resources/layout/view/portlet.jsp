<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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