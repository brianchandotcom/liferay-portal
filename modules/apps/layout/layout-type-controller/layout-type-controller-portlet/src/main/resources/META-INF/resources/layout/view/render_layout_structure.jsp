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
		<c:when test="<%= layoutStructureItem instanceof CollectionLayoutStructureItem %>">

			<%
			CollectionLayoutStructureItem collectionLayoutStructureItem = (CollectionLayoutStructureItem)layoutStructureItem;

			InfoListRenderer<Object> infoListRenderer = (InfoListRenderer<Object>)portletLayoutDisplayContext.getInfoListRenderer(collectionLayoutStructureItem);
			%>

			<c:choose>
				<c:when test="<%= infoListRenderer != null %>">

					<%
					infoListRenderer.render(portletLayoutDisplayContext.getCollection(collectionLayoutStructureItem), portletLayoutDisplayContext.getInfoListRendererContext(collectionLayoutStructureItem.getListItemStyle(), collectionLayoutStructureItem.getTemplateKey()));
					%>

				</c:when>
				<c:otherwise>
					<clay:row>

						<%
						InfoDisplayContributor<?> currentInfoDisplayContributor = (InfoDisplayContributor)request.getAttribute(InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR);

						try {
							request.setAttribute(InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR, portletLayoutDisplayContext.getCollectionInfoDisplayContributor(collectionLayoutStructureItem));

							for (Object collectionObject : portletLayoutDisplayContext.getCollection(collectionLayoutStructureItem)) {
								request.setAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT, collectionObject);
								request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
						%>

								<clay:col
									md="<%= String.valueOf(12 / collectionLayoutStructureItem.getNumberOfColumns()) %>"
								>
									<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
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

			<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
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

				<liferay-util:include page="/layout/view/render_layout_structure.jsp" servletContext="<%= application %>" />
			</clay:col>
		</c:when>
		<c:when test="<%= layoutStructureItem instanceof ContainerLayoutStructureItem %>">

			<%
			ContainerLayoutStructureItem containerLayoutStructureItem = (ContainerLayoutStructureItem)layoutStructureItem;

			String containerLinkHref = portletLayoutDisplayContext.getContainerLinkHref(containerLayoutStructureItem, request.getAttribute(InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT));
			%>

			<c:choose>
				<c:when test="<%= Validator.isNotNull(containerLinkHref) %>">
					<a href="<%= containerLinkHref %>" style="color: inherit; text-decoration: none;" target="<%= portletLayoutDisplayContext.getContainerLinkTarget(containerLayoutStructureItem) %>">
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
		<c:when test="<%= layoutStructureItem instanceof FragmentLayoutStructureItem %>">
			<div class="master-layout-fragment">

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

				defaultFragmentRendererContext.setDisplayObject(request.getAttribute("render_layout_structure.jsp-collectionObject"));
				defaultFragmentRendererContext.setLocale(locale);

				if (LayoutStructureItemUtil.hasAncestor(fragmentLayoutStructureItem.getItemId(), LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM, layoutStructure)) {
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
		<c:when test="<%= layoutStructureItem instanceof RowLayoutStructureItem %>">

			<%
			RowLayoutStructureItem rowLayoutStructureItem = (RowLayoutStructureItem)layoutStructureItem;

			LayoutStructureItem parentLayoutStructureItem = layoutStructure.getLayoutStructureItem(rowLayoutStructureItem.getParentItemId());
			%>

			<c:choose>
				<c:when test="<%= parentLayoutStructureItem instanceof RootLayoutStructureItem %>">
					<clay:container
						cssClass="overflow-hidden p-0"
						fluid="<%= true %>"
					>
						<clay:row
							cssClass="<%= ResponsiveLayoutStructureUtil.getRowCssClass(rowLayoutStructureItem) %>"
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
						cssClass="<%= ResponsiveLayoutStructureUtil.getRowCssClass(rowLayoutStructureItem) %>"
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