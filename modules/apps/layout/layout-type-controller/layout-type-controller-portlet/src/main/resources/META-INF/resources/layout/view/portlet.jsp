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
HttpServletRequest originalServletRequest = (HttpServletRequest)request.getAttribute(PortletLayoutTypeControllerWebKeys.ORIGINAL_HTTP_SERVLET_REQUEST);
%>

<c:choose>
	<c:when test="<%= themeDisplay.isStatePopUp() || themeDisplay.isWidget() || layoutTypePortlet.hasStateMax() %>">

		<%
		String ppid = ParamUtil.getString(request, "p_p_id");

		String templateId = null;
		String templateContent = null;
		String langType = null;

		if (themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
			templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";
			templateContent = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
			langType = LayoutTemplateLocalServiceUtil.getLangType("pop_up", true, theme.getThemeId());
		}
		else {
			ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

			templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
			templateContent = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
			langType = LayoutTemplateLocalServiceUtil.getLangType("max", true, theme.getThemeId());
		}

		if (Validator.isNotNull(templateContent)) {
			RuntimePageUtil.processTemplate(originalServletRequest, response, ppid, new StringTemplateResource(templateId, templateContent), langType);
		}
		%>

	</c:when>
	<c:otherwise>

		<%
		PortletLayoutDisplayContext portletLayoutDisplayContext = new PortletLayoutDisplayContext(request);

		request.setAttribute("render_layout_data_structure.jsp-portletLayoutDisplayContext", portletLayoutDisplayContext);

		JSONObject dataJSONObject = portletLayoutDisplayContext.getDataJSONObject();
		%>

		<c:choose>
			<c:when test="<%= LayoutDataConverter.isLatestVersion(dataJSONObject) %>">

				<%
				LayoutStructure layoutStructure = LayoutStructure.of(dataJSONObject.toString());

				request.setAttribute("render_react_editor_layout_data_structure.jsp-layoutStructure", layoutStructure);

				LayoutStructureItem layoutStructureItem = layoutStructure.getMainLayoutStructureItem();

				request.setAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/layout/view/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>

				<%
				request.setAttribute("render_layout_data_structure.jsp-dataJSONObject", dataJSONObject);
				%>

				<liferay-util:include page="/layout/view/render_layout_data_structure.jsp" servletContext="<%= application %>" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<liferay-ui:layout-common />