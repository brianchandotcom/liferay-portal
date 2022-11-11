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

<%@ include file="/portlet/init.jsp" %>

<%
LayoutsTreeDisplayContext layoutsTreeDisplayContext = (LayoutsTreeDisplayContext)request.getAttribute(ProductNavigationProductMenuWebKeys.LAYOUTS_TREE_DISPLAY_CONTEXT);
%>

<div id="<%= liferayPortletResponse.getNamespace() %>-layout-finder">
	<react:component
		module="js/LayoutFinder"
		props="<%= layoutsTreeDisplayContext.getLayoutFinderData() %>"
		servletContext="<%= application %>"
	/>
</div>

<div id="<portlet:namespace />layoutsTree">
	<div id="<%= liferayPortletResponse.getNamespace() %>-page-type">
		<react:component
			module="js/PageTypeSelector"
			props="<%= layoutsTreeDisplayContext.getPageTypeSelectorData() %>"
			servletContext="<%= application %>"
		/>
	</div>

	<c:choose>
		<c:when test="<%= layoutsTreeDisplayContext.isSiteNavigationMenu() %>">
			<div>
				<react:component
					module="js/NavigationMenuItemsTree"
					props="<%= layoutsTreeDisplayContext.getSiteNavigationMenuData() %>"
					servletContext="<%= application %>"
				/>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<react:component
					module="js/PagesTree"
					props="<%= layoutsTreeDisplayContext.getPagesTreeData() %>"
					servletContext="<%= application %>"
				/>
			</div>

			<c:if test="<%= layoutsTreeDisplayContext.hasAdministrationPortletPermission() %>">
				<div class="pages-administration-link">
					<aui:a cssClass="ml-2" href="<%= layoutsTreeDisplayContext.getAdministrationPortletURL() %>">
						<liferay-ui:message key="go-to-pages-administration" />
					</aui:a>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>