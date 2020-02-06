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

<%@ include file="/render_fragment_layout/init.jsp" %>

<%
Map<String, Object> fieldValues = (Map<String, Object>)request.getAttribute("liferay-layout:render-fragment-layout:fieldValues");
String mode = (String)request.getAttribute("liferay-layout:render-fragment-layout:mode");
long previewClassNameId = (long)request.getAttribute("liferay-layout:render-fragment-layout:previewClassNameId");
long previewClassPK = (long)request.getAttribute("liferay-layout:render-fragment-layout:previewClassPK");
int previewType = (int)request.getAttribute("liferay-layout:render-fragment-layout:previewType");
long[] segmentsExperienceIds = (long[])request.getAttribute("liferay-layout:render-fragment-layout:segmentsExperienceIds");

RenderFragmentLayoutDisplayContext renderFragmentLayoutDisplayContext = (RenderFragmentLayoutDisplayContext)request.getAttribute("render_layout_data_structure.jsp-renderFragmentLayoutDisplayContext");

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

				<liferay-util:include page="/render_fragment_layout/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
			</div>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof ContainerLayoutStructureItem %>">

			<%
			ContainerLayoutStructureItem containerLayoutStructureItem = (ContainerLayoutStructureItem)layoutStructureItem;

			String backgroundImage = renderFragmentLayoutDisplayContext.getBackgroundImage(containerLayoutStructureItem.getBackgroundImageJSONObject());

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

				<liferay-util:include page="/render_fragment_layout/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
			</div>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof DropZoneLayoutStructureItem %>">

			<%
			request.setAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/render_fragment_layout/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
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

			defaultFragmentRendererContext.setFieldValues(fieldValues);
			defaultFragmentRendererContext.setLocale(locale);
			defaultFragmentRendererContext.setMode(mode);
			defaultFragmentRendererContext.setPreviewClassNameId(previewClassNameId);
			defaultFragmentRendererContext.setPreviewClassPK(previewClassPK);
			defaultFragmentRendererContext.setPreviewType(previewType);
			defaultFragmentRendererContext.setSegmentsExperienceIds(segmentsExperienceIds);
			%>

			<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof RootLayoutStructureItem %>">

			<%
			request.setAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/render_fragment_layout/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof RowLayoutStructureItem %>">

			<%
			RowLayoutStructureItem rowLayoutStructureItem = (RowLayoutStructureItem)layoutStructureItem;
			%>

			<div class="row <%= !rowLayoutStructureItem.isGutters() ? "no-gutters" : StringPool.BLANK %>">

				<%
				request.setAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/render_fragment_layout/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
			</div>
		</c:when>
	</c:choose>

<%
}
%>