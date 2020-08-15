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

PortletLayoutDisplayContext portletLayoutDisplayContext = (PortletLayoutDisplayContext)request.getAttribute(PortletLayoutDisplayContext.class.getName());

LayoutStructure layoutStructure = portletLayoutDisplayContext.getLayoutStructure();

List<String> childrenItemIds = (List<String>)request.getAttribute("render_layout_structure.jsp-childrenItemIds");

for (String childrenItemId : childrenItemIds) {
	LayoutStructureItem layoutStructureItem = layoutStructure.getLayoutStructureItem(childrenItemId);
%>

	<c:if test="<%= layoutStructureItem instanceof StyledLayoutStructureItem %>">
		<div class="<%= portletLayoutDisplayContext.getCssClass((StyledLayoutStructureItem)layoutStructureItem) %>" style="<%= portletLayoutDisplayContext.getStyle((StyledLayoutStructureItem)layoutStructureItem) %>">
	</c:if>

	<c:choose>
		<c:when test="<%= layoutStructureItem instanceof CollectionStyledLayoutStructureItem %>">

			<%
			CollectionStyledLayoutStructureItem collectionStyledLayoutStructureItem = (CollectionStyledLayoutStructureItem)layoutStructureItem;

			InfoListRenderer<Object> infoListRenderer = (InfoListRenderer<Object>)portletLayoutDisplayContext.getInfoListRenderer(collectionStyledLayoutStructureItem);
			%>

			<c:choose>
				<c:when test="<%= infoListRenderer != null %>">

					<%
					infoListRenderer.render(portletLayoutDisplayContext.getCollection(collectionStyledLayoutStructureItem), portletLayoutDisplayContext.getInfoListRendererContext(collectionStyledLayoutStructureItem.getListItemStyle(), collectionStyledLayoutStructureItem.getTemplateKey()));
					%>

				</c:when>
				<c:otherwise>
					<clay:row>

						<%
						LayoutDisplayPageProvider<?> currentLayoutDisplayPageProvider = (LayoutDisplayPageProvider)request.getAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER);

						try {
							request.setAttribute(LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER, portletLayoutDisplayContext.getCollectionLayoutDisplayPageProvider(collectionStyledLayoutStructureItem));

							for (Object collectionObject : portletLayoutDisplayContext.getCollection(collectionStyledLayoutStructureItem)) {
								request.setAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT, collectionObject);
								request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
						%>

								<clay:col
									md="<%= String.valueOf(12 / collectionStyledLayoutStructureItem.getNumberOfColumns()) %>"
								>
									<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
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

			<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
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

				<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
			</clay:col>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof ContainerStyledLayoutStructureItem %>">

			<%
			ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem = (ContainerStyledLayoutStructureItem)layoutStructureItem;

			String containerLinkHref = portletLayoutDisplayContext.getContainerLinkHref(containerStyledLayoutStructureItem, request.getAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT));
			%>

			<c:choose>
				<c:when test="<%= Validator.isNotNull(containerLinkHref) %>">
					<a href="<%= containerLinkHref %>" style="color: inherit; text-decoration: none;" target="<%= portletLayoutDisplayContext.getContainerLinkTarget(containerStyledLayoutStructureItem) %>">
				</c:when>
			</c:choose>

			<%
			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />

			<c:choose>
				<c:when test="<%= Validator.isNotNull(containerLinkHref) %>">
					</a>
				</c:when>
			</c:choose>
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
		<c:when test="<%= layoutStructureItem instanceof FragmentDropZoneLayoutStructureItem %>">

			<%
			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof FragmentStyledLayoutStructureItem %>">
			<div class="master-layout-fragment">

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

				defaultFragmentRendererContext.setDisplayObject(request.getAttribute("render_layout_structure.jsp-collectionObject"));
				defaultFragmentRendererContext.setLocale(locale);

				if (LayoutStructureItemUtil.hasAncestor(fragmentStyledLayoutStructureItem.getItemId(), LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM, layoutStructure)) {
					defaultFragmentRendererContext.setUseCachedContent(false);
				}
				%>

				<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>
			</div>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof RootLayoutStructureItem %>">

			<%
			request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
			%>

			<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof RowStyledLayoutStructureItem %>">

			<%
			RowStyledLayoutStructureItem rowStyledLayoutStructureItem = (RowStyledLayoutStructureItem)layoutStructureItem;

			LayoutStructureItem parentLayoutStructureItem = layoutStructure.getLayoutStructureItem(rowStyledLayoutStructureItem.getParentItemId());
			%>

			<c:choose>
				<c:when test="<%= parentLayoutStructureItem instanceof RootLayoutStructureItem %>">
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

							<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
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

						<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
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