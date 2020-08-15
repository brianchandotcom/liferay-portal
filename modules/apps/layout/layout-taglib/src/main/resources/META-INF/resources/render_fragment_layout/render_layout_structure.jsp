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
LayoutStructure layoutStructure = (LayoutStructure)request.getAttribute("liferay-layout:render-fragment-layout:layoutStructure");
String mode = (String)request.getAttribute("liferay-layout:render-fragment-layout:mode");
long previewClassNameId = (long)request.getAttribute("liferay-layout:render-fragment-layout:previewClassNameId");
long previewClassPK = (long)request.getAttribute("liferay-layout:render-fragment-layout:previewClassPK");
int previewType = (int)request.getAttribute("liferay-layout:render-fragment-layout:previewType");
String previewVersion = (String)request.getAttribute("liferay-layout:render-fragment-layout:previewVersion");
RenderFragmentLayoutDisplayContext renderFragmentLayoutDisplayContext = (RenderFragmentLayoutDisplayContext)request.getAttribute("liferay-layout:render-fragment-layout:renderFragmentLayoutDisplayContext");
long[] segmentsExperienceIds = (long[])request.getAttribute("liferay-layout:render-fragment-layout:segmentsExperienceIds");

List<String> childrenItemIds = (List<String>)request.getAttribute("render_layout_structure.jsp-childrenItemIds");

for (String childrenItemId : childrenItemIds) {
	LayoutStructureItem layoutStructureItem = layoutStructure.getLayoutStructureItem(childrenItemId);
%>

	<c:if test="<%= layoutStructureItem instanceof StyledLayoutStructureItem %>">
		<div class="<%= renderFragmentLayoutDisplayContext.getCssClass((StyledLayoutStructureItem)layoutStructureItem) %>" style="<%= renderFragmentLayoutDisplayContext.getStyle((StyledLayoutStructureItem)layoutStructureItem) %>">
	</c:if>

	<c:choose>
		<c:when test="<%= layoutStructureItem instanceof CollectionStyledLayoutStructureItem %>">

			<%
			CollectionStyledLayoutStructureItem collectionStyledLayoutStructureItem = (CollectionStyledLayoutStructureItem)layoutStructureItem;

			InfoListRenderer<Object> infoListRenderer = (InfoListRenderer<Object>)renderFragmentLayoutDisplayContext.getInfoListRenderer(collectionStyledLayoutStructureItem);
			%>

			<c:choose>
				<c:when test="<%= infoListRenderer != null %>">

					<%
					infoListRenderer.render(renderFragmentLayoutDisplayContext.getCollection(collectionStyledLayoutStructureItem, segmentsExperienceIds), renderFragmentLayoutDisplayContext.getInfoListRendererContext(collectionStyledLayoutStructureItem.getListItemStyle(), collectionStyledLayoutStructureItem.getTemplateKey()));
					%>

				</c:when>
				<c:otherwise>
					<clay:row>

						<%
						LayoutDisplayPageProvider<?> currentLayoutDisplayPageProvider = (LayoutDisplayPageProvider<?>)request.getAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER);

						try {
							request.setAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER, renderFragmentLayoutDisplayContext.getLayoutDisplayPageProvider(collectionStyledLayoutStructureItem));

							for (Object collectionObject : renderFragmentLayoutDisplayContext.getCollection(collectionStyledLayoutStructureItem, segmentsExperienceIds)) {
								request.setAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT, collectionObject);
								request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
						%>

								<clay:col
									md="<%= String.valueOf(12 / collectionStyledLayoutStructureItem.getNumberOfColumns()) %>"
								>
									<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
								</clay:col>

						<%
							}
						}
						finally {
							request.removeAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT);

							request.setAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER, currentLayoutDisplayPageProvider);
						}
						%>

					</clay:row>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof CollectionItemLayoutStructureItem %>">

			<%
			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof ColumnLayoutStructureItem %>">

			<%
			ColumnLayoutStructureItem columnLayoutStructureItem = (ColumnLayoutStructureItem)layoutStructureItem;

			RowStyledLayoutStructureItem rowStyledLayoutStructureItem = (RowStyledLayoutStructureItem)layoutStructure.getLayoutStructureItem(columnLayoutStructureItem.getParentItemId());
			%>

			<clay:col
				cssClass="<%= ResponsiveLayoutStructureUtil.getColumnCssClass(rowStyledLayoutStructureItem, columnLayoutStructureItem) %>"
			>

				<%
				request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
			</clay:col>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof ContainerStyledLayoutStructureItem %>">

			<%
			ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem = (ContainerStyledLayoutStructureItem)layoutStructureItem;

			String containerLinkHref = renderFragmentLayoutDisplayContext.getContainerLinkHref(containerStyledLayoutStructureItem, request.getAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT));
			%>

			<c:choose>
				<c:when test="<%= Validator.isNotNull(containerLinkHref) %>">
					<a href="<%= containerLinkHref %>" style="color: inherit; text-decoration: none;" target="<%= renderFragmentLayoutDisplayContext.getContainerLinkTarget(containerStyledLayoutStructureItem) %>">
				</c:when>
			</c:choose>

			<%
			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />

			<c:choose>
				<c:when test="<%= Validator.isNotNull(containerLinkHref) %>">
					</a>
				</c:when>
			</c:choose>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof DropZoneLayoutStructureItem %>">

			<%
			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof FragmentDropZoneLayoutStructureItem %>">

			<%
			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof FragmentStyledLayoutStructureItem %>">

			<%
			FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem = (FragmentStyledLayoutStructureItem)layoutStructureItem;

			if (fragmentStyledLayoutStructureItem.getFragmentEntryLinkId() <= 0) {
				continue;
			}

			FragmentEntryLink fragmentEntryLink = FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink == null) {
				continue;
			}

			FragmentRendererController fragmentRendererController = (FragmentRendererController)request.getAttribute(FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER);

			DefaultFragmentRendererContext defaultFragmentRendererContext = new DefaultFragmentRendererContext(fragmentEntryLink);

			defaultFragmentRendererContext.setDisplayObject(request.getAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT));
			defaultFragmentRendererContext.setFieldValues(fieldValues);
			defaultFragmentRendererContext.setLocale(locale);
			defaultFragmentRendererContext.setMode(mode);
			defaultFragmentRendererContext.setPreviewClassNameId(previewClassNameId);
			defaultFragmentRendererContext.setPreviewClassPK(previewClassPK);
			defaultFragmentRendererContext.setPreviewType(previewType);
			defaultFragmentRendererContext.setPreviewVersion(previewVersion);
			defaultFragmentRendererContext.setSegmentsExperienceIds(segmentsExperienceIds);

			if (LayoutStructureItemUtil.hasAncestor(fragmentStyledLayoutStructureItem.getItemId(), LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM, layoutStructure)) {
				defaultFragmentRendererContext.setUseCachedContent(false);
			}
			%>

			<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof RootLayoutStructureItem %>">

			<%
			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof RowStyledLayoutStructureItem %>">

			<%
			RowStyledLayoutStructureItem rowStyledLayoutStructureItem = (RowStyledLayoutStructureItem)layoutStructureItem;

			LayoutStructureItem parentLayoutStructureItem = layoutStructure.getLayoutStructureItem(rowStyledLayoutStructureItem.getParentItemId());

			boolean includeContainer = false;

			if (parentLayoutStructureItem instanceof RootLayoutStructureItem) {
				LayoutStructureItem rootParentLayoutStructureItem = layoutStructure.getLayoutStructureItem(parentLayoutStructureItem.getParentItemId());

				if (rootParentLayoutStructureItem == null) {
					includeContainer = true;
				}
				else if (rootParentLayoutStructureItem instanceof DropZoneLayoutStructureItem) {
					LayoutStructureItem dropZoneParentLayoutStructureItem = layoutStructure.getLayoutStructureItem(rootParentLayoutStructureItem.getParentItemId());

					if (dropZoneParentLayoutStructureItem instanceof RootLayoutStructureItem) {
						includeContainer = true;
					}
				}
			}
			%>

			<c:choose>
				<c:when test="<%= includeContainer %>">
					<clay:container
						cssClass="overflow-hidden p-0"
						fluid="<%= true %>"
					>
						<clay:row
							cssClass="<%= ResponsiveLayoutStructureUtil.getRowCssClass(rowStyledLayoutStructureItem) %>"
						>

							<%
							request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
							%>

							<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
						</clay:row>
					</clay:container>
				</c:when>
				<c:otherwise>
					<clay:row
						cssClass="<%= ResponsiveLayoutStructureUtil.getRowCssClass(rowStyledLayoutStructureItem) %>"
					>

						<%
						request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
						%>

						<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
					</clay:row>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>

	<c:if test="<%= layoutStructureItem instanceof StyledLayoutStructureItem %>">
		</div>
	</c:if>

<%
}
%>