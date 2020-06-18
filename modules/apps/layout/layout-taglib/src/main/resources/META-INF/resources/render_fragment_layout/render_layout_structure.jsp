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
RenderFragmentLayoutDisplayContext renderFragmentLayoutDisplayContext = (RenderFragmentLayoutDisplayContext)request.getAttribute("liferay-layout:render-fragment-layout:renderFragmentLayoutDisplayContext");
long[] segmentsExperienceIds = (long[])request.getAttribute("liferay-layout:render-fragment-layout:segmentsExperienceIds");

List<String> childrenItemIds = (List<String>)request.getAttribute("render_layout_structure.jsp-childrenItemIds");

for (String childrenItemId : childrenItemIds) {
	LayoutStructureItem layoutStructureItem = layoutStructure.getLayoutStructureItem(childrenItemId);
%>

	<c:choose>
		<c:when test="<%= layoutStructureItem instanceof CollectionLayoutStructureItem %>">

			<%
			CollectionLayoutStructureItem collectionLayoutStructureItem = (CollectionLayoutStructureItem)layoutStructureItem;

			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());

			InfoListRenderer<Object> infoListRenderer = (InfoListRenderer<Object>)renderFragmentLayoutDisplayContext.getInfoListRenderer(collectionLayoutStructureItem);
			%>

			<c:choose>
				<c:when test="<%= infoListRenderer != null %>">

					<%
					infoListRenderer.render(renderFragmentLayoutDisplayContext.getCollection(collectionLayoutStructureItem, segmentsExperienceIds), renderFragmentLayoutDisplayContext.getInfoListRendererContext(collectionLayoutStructureItem.getListItemStyle(), collectionLayoutStructureItem.getTemplateKey()));
					%>

				</c:when>
				<c:otherwise>
					<clay:row>

						<%
						InfoDisplayContributor<?> currentInfoDisplayContributor = (InfoDisplayContributor<?>)request.getAttribute(InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR);

						try {
							request.setAttribute(InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR, renderFragmentLayoutDisplayContext.getCollectionInfoDisplayContributor(collectionLayoutStructureItem));

							for (Object collectionObject : renderFragmentLayoutDisplayContext.getCollection(collectionLayoutStructureItem, segmentsExperienceIds)) {
								request.setAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT, collectionObject);
						%>

								<clay:col
									md="<%= String.valueOf(12 / collectionLayoutStructureItem.getNumberOfColumns()) %>"
								>
									<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
								</clay:col>

						<%
							}
						}
						finally {
							request.removeAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT);

							request.setAttribute(InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR, currentInfoDisplayContributor);
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

			RowLayoutStructureItem rowLayoutStructureItem = (RowLayoutStructureItem)layoutStructure.getLayoutStructureItem(columnLayoutStructureItem.getParentItemId());
			%>

			<clay:col
				cssClass="<%= ResponsiveLayoutStructureUtil.getColumnCssClass(rowLayoutStructureItem, columnLayoutStructureItem) %>"
			>

				<%
				request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
			</clay:col>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof ContainerLayoutStructureItem %>">

			<%
			ContainerLayoutStructureItem containerLayoutStructureItem = (ContainerLayoutStructureItem)layoutStructureItem;

			String backgroundImage = renderFragmentLayoutDisplayContext.getBackgroundImage(containerLayoutStructureItem.getBackgroundImageJSONObject());

			StringBundler cssClassSB = new StringBundler();
			StringBundler styleSB = new StringBundler();

			styleSB.append("box-sizing: border-box;");

			if (Validator.isNotNull(containerLayoutStructureItem.getAlign())) {
				cssClassSB.append(" ");
				cssClassSB.append(containerLayoutStructureItem.getAlign());
			}

			if (Validator.isNotNull(containerLayoutStructureItem.getBackgroundColorCssClass())) {
				cssClassSB.append(" bg-");
				cssClassSB.append(containerLayoutStructureItem.getBackgroundColorCssClass());
			}

			if (Validator.isNotNull(backgroundImage)) {
				styleSB.append("background-position: 50% 50%; background-repeat: no-repeat; background-size: cover; background-image: ");
				styleSB.append(backgroundImage);
				styleSB.append(";");
			}

			if (Validator.isNotNull(containerLayoutStructureItem.getBorderColor())) {
				cssClassSB.append(" border-");
				cssClassSB.append(containerLayoutStructureItem.getBorderColor());
			}

			if (Validator.isNotNull(containerLayoutStructureItem.getBorderRadius())) {
				cssClassSB.append(" ");
				cssClassSB.append(containerLayoutStructureItem.getBorderRadius());
			}

			if (containerLayoutStructureItem.getBorderWidth() != -1L) {
				styleSB.append("border-style: solid; border-width: ");
				styleSB.append(containerLayoutStructureItem.getBorderWidth());
				styleSB.append("px;");
			}

			if (Objects.equals(containerLayoutStructureItem.getContentDisplay(), "block")) {
				cssClassSB.append(" d-block");
			}

			if (Objects.equals(containerLayoutStructureItem.getContentDisplay(), "flex")) {
				cssClassSB.append(" d-flex");
			}

			if (Validator.isNotNull(containerLayoutStructureItem.getJustify())) {
				cssClassSB.append(" ");
				cssClassSB.append(containerLayoutStructureItem.getJustify());
			}

			if (containerLayoutStructureItem.getMarginBottom() != -1L) {
				cssClassSB.append(" mb-");
				cssClassSB.append(containerLayoutStructureItem.getMarginBottom());
			}

			if (!Objects.equals(containerLayoutStructureItem.getWidthType(), "fixed")) {
				if (containerLayoutStructureItem.getMarginLeft() != -1L) {
					cssClassSB.append(" ml-");
					cssClassSB.append(containerLayoutStructureItem.getMarginLeft());
				}

				if (containerLayoutStructureItem.getMarginRight() != -1L) {
					cssClassSB.append(" mr-");
					cssClassSB.append(containerLayoutStructureItem.getMarginRight());
				}
			}

			if (containerLayoutStructureItem.getMarginTop() != -1L) {
				cssClassSB.append(" mt-");
				cssClassSB.append(containerLayoutStructureItem.getMarginTop());
			}

			if (containerLayoutStructureItem.getOpacity() != -1L) {
				styleSB.append("opacity: ");
				styleSB.append(containerLayoutStructureItem.getOpacity() / 100.0);
				styleSB.append(";");
			}

			if (containerLayoutStructureItem.getPaddingBottom() != -1L) {
				cssClassSB.append(" pb-");
				cssClassSB.append(containerLayoutStructureItem.getPaddingBottom());
			}

			if (containerLayoutStructureItem.getPaddingLeft() != -1L) {
				cssClassSB.append(" pl-");
				cssClassSB.append(containerLayoutStructureItem.getPaddingLeft());
			}

			if (containerLayoutStructureItem.getPaddingRight() != -1L) {
				cssClassSB.append(" pr-");
				cssClassSB.append(containerLayoutStructureItem.getPaddingRight());
			}

			if (containerLayoutStructureItem.getPaddingTop() != -1L) {
				cssClassSB.append(" pt-");
				cssClassSB.append(containerLayoutStructureItem.getPaddingTop());
			}

			if (Validator.isNotNull(containerLayoutStructureItem.getShadow())) {
				cssClassSB.append(" ");
				cssClassSB.append(containerLayoutStructureItem.getShadow());
			}

			if (Objects.equals(containerLayoutStructureItem.getWidthType(), "fixed")) {
				cssClassSB.append(" container");
			}
			%>

			<div class="<%= cssClassSB.toString() %>" style="<%= styleSB.toString() %>">

				<%
				request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
			</div>
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

			Object displayObject = request.getAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT);

			defaultFragmentRendererContext.setDisplayObject(displayObject);

			defaultFragmentRendererContext.setFieldValues(fieldValues);
			defaultFragmentRendererContext.setLocale(locale);
			defaultFragmentRendererContext.setMode(mode);
			defaultFragmentRendererContext.setPreviewClassNameId(previewClassNameId);
			defaultFragmentRendererContext.setPreviewClassPK(previewClassPK);
			defaultFragmentRendererContext.setPreviewType(previewType);
			defaultFragmentRendererContext.setSegmentsExperienceIds(segmentsExperienceIds);

			LayoutStructureItem parentLayoutStructureItem = layoutStructure.getLayoutStructureItem(fragmentLayoutStructureItem.getParentItemId());

			if (parentLayoutStructureItem instanceof CollectionItemLayoutStructureItem) {
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
		<c:when test="<%= layoutStructureItem instanceof RowLayoutStructureItem %>">

			<%
			RowLayoutStructureItem rowLayoutStructureItem = (RowLayoutStructureItem)layoutStructureItem;

			LayoutStructureItem parentLayoutStructureItem = layoutStructure.getLayoutStructureItem(rowLayoutStructureItem.getParentItemId());

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
					<clay:container-fluid
						cssClass="p-0"
					>
						<clay:row
							cssClass="<%= ResponsiveLayoutStructureUtil.getRowCssClass(rowLayoutStructureItem) %>"
						>

							<%
							request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
							%>

							<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />
						</clay:row>
					</clay:container-fluid>
				</c:when>
				<c:otherwise>
					<clay:row
						cssClass="<%= ResponsiveLayoutStructureUtil.getRowCssClass(rowLayoutStructureItem) %>"
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

<%
}
%>