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

			InfoListRenderer<Object> infoListRenderer = (InfoListRenderer<Object>)renderLayoutStructureDisplayContext.getInfoListRenderer(collectionStyledLayoutStructureItem);
			%>

			<div class="<%= renderLayoutStructureDisplayContext.getCssClass(collectionStyledLayoutStructureItem) %>" style="<%= renderLayoutStructureDisplayContext.getStyle(collectionStyledLayoutStructureItem) %>">
				<c:choose>
					<c:when test="<%= infoListRenderer != null %>">

						<%
						infoListRenderer.render(renderLayoutStructureDisplayContext.getCollection(collectionStyledLayoutStructureItem), renderLayoutStructureDisplayContext.getInfoListRendererContext(collectionStyledLayoutStructureItem.getListItemStyle(), collectionStyledLayoutStructureItem.getTemplateKey()));
						%>

					</c:when>
					<c:otherwise>

						<%
						LayoutDisplayPageProvider<?> currentLayoutDisplayPageProvider = (LayoutDisplayPageProvider<?>)request.getAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER);

						try {
							request.setAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER, renderLayoutStructureDisplayContext.getCollectionLayoutDisplayPageProvider(collectionStyledLayoutStructureItem));

							List<Object> collection = renderLayoutStructureDisplayContext.getCollection(collectionStyledLayoutStructureItem);

							int maxNumberOfItems = Math.min(collection.size(), collectionStyledLayoutStructureItem.getNumberOfItems());

							int numberOfRows = (int)Math.ceil((double)maxNumberOfItems / collectionStyledLayoutStructureItem.getNumberOfColumns());

							for (int i = 0; i < numberOfRows; i++) {
						%>

							<clay:row>

								<%
								for (int j = 0; j < collectionStyledLayoutStructureItem.getNumberOfColumns(); j++) {
									int index = (i * collectionStyledLayoutStructureItem.getNumberOfColumns()) + j;

									if (index >= maxNumberOfItems) {
										break;
									}

									request.setAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT, collection.get(index));
									request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
								%>

									<clay:col
										md="<%= String.valueOf(layoutStructure.getColumnSize(collectionStyledLayoutStructureItem.getNumberOfColumns() - 1, j)) %>"
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
			</div>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof ColumnLayoutStructureItem %>">

			<%
			ColumnLayoutStructureItem columnLayoutStructureItem = (ColumnLayoutStructureItem)layoutStructureItem;

			RowStyledLayoutStructureItem rowStyledLayoutStructureItem = (RowStyledLayoutStructureItem)layoutStructure.getLayoutStructureItem(columnLayoutStructureItem.getParentItemId());
			%>

			<clay:col
				cssClass="<%= ResponsiveLayoutStructureUtil.getColumnCssClass(columnLayoutStructureItem, rowStyledLayoutStructureItem) %>"
			>

				<%
				request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/render_layout_structure/render_layout_structure.jsp" servletContext="<%= application %>" />
			</clay:col>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof ContainerStyledLayoutStructureItem %>">

			<%
			ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem = (ContainerStyledLayoutStructureItem)layoutStructureItem;

			String containerLinkHref = renderLayoutStructureDisplayContext.getContainerLinkHref(containerStyledLayoutStructureItem, request.getAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT), locale);
			%>

			<c:if test="<%= Validator.isNotNull(containerLinkHref) %>">
				<a href="<%= containerLinkHref %>" style="color: inherit; text-decoration: none;" target="<%= renderLayoutStructureDisplayContext.getContainerLinkTarget(containerStyledLayoutStructureItem, locale) %>">
			</c:if>

			<div class="<%= renderLayoutStructureDisplayContext.getCssClass(containerStyledLayoutStructureItem) %>" style="<%= renderLayoutStructureDisplayContext.getStyle(containerStyledLayoutStructureItem) %>">

				<%
				request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
				%>

				<liferay-util:include page="/render_layout_structure/render_layout_structure.jsp" servletContext="<%= application %>" />
			</div>

			<c:if test="<%= Validator.isNotNull(containerLinkHref) %>">
				</a>
			</c:if>
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
		<c:when test="<%= layoutStructureItem instanceof FragmentStyledLayoutStructureItem %>">
			<c:if test="<%= Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET) %>">
				<div class="master-layout-fragment">
			</c:if>

			<%
			FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem = (FragmentStyledLayoutStructureItem)layoutStructureItem;
			%>

			<c:if test="<%= fragmentStyledLayoutStructureItem.getFragmentEntryLinkId() > 0 %>">

				<%
				FragmentEntryLink fragmentEntryLink = FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());
				%>

				<c:if test="<%= fragmentEntryLink != null %>">

					<%
					FragmentRendererController fragmentRendererController = (FragmentRendererController)request.getAttribute(FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER);

					DefaultFragmentRendererContext defaultFragmentRendererContext = renderLayoutStructureDisplayContext.getDefaultFragmentRendererContext(fragmentEntryLink, fragmentStyledLayoutStructureItem.getItemId());
					%>

					<div class="<%= renderLayoutStructureDisplayContext.getCssClass(fragmentStyledLayoutStructureItem) %>" style="<%= renderLayoutStructureDisplayContext.getStyle(fragmentStyledLayoutStructureItem) %>">
						<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>
					</div>
				</c:if>
			</c:if>

			<c:if test="<%= Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET) %>">
				</div>
			</c:if>
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