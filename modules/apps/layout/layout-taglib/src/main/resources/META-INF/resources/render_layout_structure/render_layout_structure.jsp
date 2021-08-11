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

<%@ include file="/render_layout_structure/init.jsp" %>

<%
RenderLayoutStructureDisplayContext renderLayoutStructureDisplayContext = (RenderLayoutStructureDisplayContext)request.getAttribute(RenderLayoutStructureDisplayContext.class.getName());

LayoutStructure layoutStructure = renderLayoutStructureDisplayContext.getLayoutStructure();

List<String> childrenItemIds = (List<String>)request.getAttribute("render_layout_structure.jsp-childrenItemIds");

for (String childrenItemId : childrenItemIds) {
	LayoutStructureItem layoutStructureItem = layoutStructure.getLayoutStructureItem(childrenItemId);
%>

	<c:choose>
		<c:when test="<%= layoutStructureItem instanceof CollectionStyledLayoutStructureItem %>">

			<%
			CollectionStyledLayoutStructureItem collectionStyledLayoutStructureItem = (CollectionStyledLayoutStructureItem)layoutStructureItem;

			RenderCollectionLayoutStructureItemDisplayContext renderCollectionLayoutStructureItemDisplayContext = new RenderCollectionLayoutStructureItemDisplayContext(collectionStyledLayoutStructureItem, request, response);

			InfoListRenderer<Object> infoListRenderer = (InfoListRenderer<Object>)renderCollectionLayoutStructureItemDisplayContext.getInfoListRenderer();
			%>

			<div class="<%= renderLayoutStructureDisplayContext.getCssClass(collectionStyledLayoutStructureItem) %>" style="<%= renderLayoutStructureDisplayContext.getStyle(collectionStyledLayoutStructureItem) %>">
				<c:choose>
					<c:when test="<%= infoListRenderer != null %>">

						<%
						infoListRenderer.render(renderCollectionLayoutStructureItemDisplayContext.getCollection(), renderCollectionLayoutStructureItemDisplayContext.getInfoListRendererContext());
						%>

					</c:when>
					<c:otherwise>

						<%
						LayoutDisplayPageProvider<?> currentLayoutDisplayPageProvider = (LayoutDisplayPageProvider<?>)request.getAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER);

						try {
							request.setAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER, renderCollectionLayoutStructureItemDisplayContext.getCollectionLayoutDisplayPageProvider());

							List<Object> collection = renderCollectionLayoutStructureItemDisplayContext.getCollection();

							for (int i = 0; i < renderCollectionLayoutStructureItemDisplayContext.getNumberOfRows(); i++) {
						%>

								<clay:row>

									<%
									for (int j = 0; j < collectionStyledLayoutStructureItem.getNumberOfColumns(); j++) {
										int index = (i * collectionStyledLayoutStructureItem.getNumberOfColumns()) + j;

										if ((index >= renderCollectionLayoutStructureItemDisplayContext.getNumberOfItemsToDisplay()) || (index >= collection.size())) {
											break;
										}

										request.setAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT, collection.get(index));
										request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
									%>

										<clay:col
											md="<%= String.valueOf(LayoutStructureConstants.COLUMN_SIZES[collectionStyledLayoutStructureItem.getNumberOfColumns() - 1][j]) %>"
										>
											<liferay-util:include page="/render_layout_structure/render_layout_structure.jsp" servletContext="<%= application %>" />
										</clay:col>

									<%
									}
									%>

								</clay:row>

						<%
							}
						}
						finally {
							request.removeAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT);

							request.setAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER, currentLayoutDisplayPageProvider);
						}
						%>

					</c:otherwise>
				</c:choose>

				<c:if test='<%= Objects.equals(collectionStyledLayoutStructureItem.getPaginationType(), "numeric") %>'>
					<clay:pagination-bar
						activeDelta="<%= collectionStyledLayoutStructureItem.getNumberOfItemsPerPage() %>"
						activePage="<%= renderCollectionLayoutStructureItemDisplayContext.getActivePage() %>"
						additionalProps="<%= renderCollectionLayoutStructureItemDisplayContext.getNumericCollectionPaginationAdditionalProps() %>"
						cssClass="pb-2 pt-3"
						propsTransformer="render_layout_structure/js/NumericCollectionPaginationPropsTransformer"
						totalItems="<%= collectionStyledLayoutStructureItem.getNumberOfItems() %>"
					/>
				</c:if>

				<c:if test='<%= Objects.equals(collectionStyledLayoutStructureItem.getPaginationType(), "simple") %>'>
					<div class="d-flex flex-grow-1 h-100 justify-content-center py-3" id="<%= "paginationButtons_" + collectionStyledLayoutStructureItem.getItemId() %>">
						<clay:button
							cssClass="font-weight-semi-bold mr-3 previous text-secondary"
							disabled="<%= Objects.equals(renderCollectionLayoutStructureItemDisplayContext.getActivePage(), 1) %>"
							displayType="unstyled"
							id='<%= "paginationPreviousButton_" + collectionStyledLayoutStructureItem.getItemId() %>'
							label='<%= LanguageUtil.get(request, "previous") %>'
						/>

						<clay:button
							cssClass="font-weight-semi-bold ml-3 next text-secondary"
							disabled="<%= Objects.equals(renderCollectionLayoutStructureItemDisplayContext.getActivePage(), renderCollectionLayoutStructureItemDisplayContext.getNumberOfPages()) %>"
							displayType="unstyled"
							id='<%= "paginationNextButton_" + collectionStyledLayoutStructureItem.getItemId() %>'
							label='<%= LanguageUtil.get(request, "next") %>'
						/>
					</div>

					<liferay-frontend:component
						componentId='<%= "paginationComponent" + collectionStyledLayoutStructureItem.getItemId() %>'
						context="<%= renderCollectionLayoutStructureItemDisplayContext.getSimpleCollectionPaginationContext() %>"
						module="render_layout_structure/js/SimpleCollectionPagination"
					/>
				</c:if>
			</div>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof DropZoneLayoutStructureItem %>">
			<c:choose>
				<c:when test="<%= Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET) %>">

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

					if (Validator.isNotNull(templateContent)) {
						HttpServletRequest originalServletRequest = (HttpServletRequest)request.getAttribute("ORIGINAL_HTTP_SERVLET_REQUEST");

						RuntimePageUtil.processTemplate(originalServletRequest, response, new StringTemplateResource(templateId, templateContent), LayoutTemplateLocalServiceUtil.getLangType(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId()));
					}
					%>

				</c:when>
				<c:otherwise>

					<%
					request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
					%>

					<liferay-util:include page="/render_layout_structure/render_layout_structure.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof RowStyledLayoutStructureItem %>">

			<%
			RowStyledLayoutStructureItem rowStyledLayoutStructureItem = (RowStyledLayoutStructureItem)layoutStructureItem;

			LayoutStructureItem parentLayoutStructureItem = layoutStructure.getLayoutStructureItem(rowStyledLayoutStructureItem.getParentItemId());

			boolean includeContainer = false;

			if (parentLayoutStructureItem instanceof RootLayoutStructureItem) {
				if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
					includeContainer = true;
				}
				else {
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
			}
			%>

			<div class="<%= renderLayoutStructureDisplayContext.getCssClass(rowStyledLayoutStructureItem) %>" style="<%= renderLayoutStructureDisplayContext.getStyle(rowStyledLayoutStructureItem) %>">
				<c:choose>
					<c:when test="<%= includeContainer %>">
						<clay:container
							cssClass="p-0"
							fluid="<%= true %>"
						>
							<clay:row
								cssClass="<%= ResponsiveLayoutStructureUtil.getRowCssClass(rowStyledLayoutStructureItem) %>"
							>

								<%
								request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
								%>

								<liferay-util:include page="/render_layout_structure/render_layout_structure.jsp" servletContext="<%= application %>" />
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

							<liferay-util:include page="/render_layout_structure/render_layout_structure.jsp" servletContext="<%= application %>" />
						</clay:row>
					</c:otherwise>
				</c:choose>
			</div>
		</c:when>
		<c:otherwise>

			<%
			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/render_layout_structure/render_layout_structure.jsp" servletContext="<%= application %>" />
		</c:otherwise>
	</c:choose>

<%
}
%>