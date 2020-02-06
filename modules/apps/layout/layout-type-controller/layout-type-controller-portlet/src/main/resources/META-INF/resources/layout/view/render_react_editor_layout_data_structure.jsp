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

PortletLayoutDisplayContext portletLayoutDisplayContext = (PortletLayoutDisplayContext)request.getAttribute("render_layout_data_structure.jsp-portletLayoutDisplayContext");

LayoutStructure layoutStructure = (LayoutStructure)request.getAttribute("render_react_editor_layout_data_structure.jsp-layoutStructure");

List<String> childrenItemIds = (List<String>)request.getAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds");

for (String childrenItemId : childrenItemIds) {
	LayoutStructureItem layoutStructureItem = layoutStructure.getLayoutStructureItem(childrenItemId);
%>

	<c:choose>
		<c:when test="<%= layoutStructureItem instanceof ColumnLayoutStructureItem %>">

			<%
			ColumnLayoutStructureItem columnLayoutStructureItem = (ColumnLayoutStructureItem)layoutStructureItem;
			%>

			<div class="<%= (columnLayoutStructureItem.getSize() > 0) ? "col-md-" + columnLayoutStructureItem.getSize() : "col-md" %>">

				<%
				request.setAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/layout/view/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
			</div>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof ContainerLayoutStructureItem %>">

			<%
			ContainerLayoutStructureItem containerLayoutStructureItem = (ContainerLayoutStructureItem)layoutStructureItem;

			String backgroundImage = portletLayoutDisplayContext.getBackgroundImage(containerLayoutStructureItem.getBackgroundImageJSONObject());

			StringBundler sb = new StringBundler();

			if (Validator.isNotNull(containerLayoutStructureItem.getBackgroundColorCssClass())) {
				sb.append("bg-");
				sb.append(containerLayoutStructureItem.getBackgroundColorCssClass());
			}

			if (Objects.equals(containerLayoutStructureItem.getContainerType(), "fluid")) {
				sb.append(" container-fluid");
			}
			else {
				sb.append(" container");
			}

			if (containerLayoutStructureItem.getPaddingBottom() != -1L) {
				sb.append(" pb-");
				sb.append(containerLayoutStructureItem.getPaddingBottom());
			}

			if (containerLayoutStructureItem.getPaddingHorizontal() != -1L) {
				sb.append(" px-");
				sb.append(containerLayoutStructureItem.getPaddingHorizontal());
			}

			if (containerLayoutStructureItem.getPaddingTop() != -1L) {
				sb.append(" pt-");
				sb.append(containerLayoutStructureItem.getPaddingTop());
			}
			%>

			<div class="<%= sb.toString() %>" style="<%= Validator.isNotNull(backgroundImage) ? "background-image: url(" + backgroundImage + "); background-position: 50% 50%; background-repeat: no-repeat; background-size: cover;" : "" %>">

				<%
				request.setAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/layout/view/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
			</div>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof DropZoneLayoutStructureItem %>">

			<%
			String themeId = theme.getThemeId();

			String layoutTemplateId = layoutTypePortlet.getLayoutTemplateId();

			if (Validator.isNull(layoutTemplateId)) {
				layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;
			}

			LayoutTemplate layoutTemplate = LayoutTemplateLocalServiceUtil.getLayoutTemplate(layoutTemplateId, false, theme.getThemeId());

			if (layoutTemplate != null) {
				themeId = layoutTemplate.getThemeId();
			}

			String templateId = themeId + LayoutTemplateConstants.CUSTOM_SEPARATOR + layoutTypePortlet.getLayoutTemplateId();
			String templateContent = LayoutTemplateLocalServiceUtil.getContent(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());
			String langType = LayoutTemplateLocalServiceUtil.getLangType(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());

			if (Validator.isNotNull(templateContent)) {
				RuntimePageUtil.processTemplate(originalServletRequest, response, new StringTemplateResource(templateId, templateContent), langType);
			}
			%>

		</c:when>
		<c:when test="<%= layoutStructureItem instanceof FragmentLayoutStructureItem %>">

			<%
			FragmentLayoutStructureItem fragmentLayoutStructureItem = (FragmentLayoutStructureItem)layoutStructureItem;

			if (fragmentLayoutStructureItem.getFragmentEntryLinkId() <= 0) {
				continue;
			}

			FragmentEntryLink fragmentEntryLink = FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(fragmentLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink == null) {
				continue;
			}

			FragmentRendererController fragmentRendererController = (FragmentRendererController)request.getAttribute(FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER);

			DefaultFragmentRendererContext defaultFragmentRendererContext = new DefaultFragmentRendererContext(fragmentEntryLink);

			defaultFragmentRendererContext.setLocale(locale);
			%>

			<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof RootLayoutStructureItem %>">

			<%
			request.setAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/layout/view/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof RowLayoutStructureItem %>">

			<%
			RowLayoutStructureItem rowLayoutStructureItem = (RowLayoutStructureItem)layoutStructureItem;
			%>

			<div class="row <%= !rowLayoutStructureItem.isGutters() ? "no-gutters" : StringPool.BLANK %>">

				<%
				request.setAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/layout/view/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
			</div>
		</c:when>
	</c:choose>

<%
}
%>