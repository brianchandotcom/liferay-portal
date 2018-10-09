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
AssetListUsagesDisplayContext assetListUsagesDisplayContext = new AssetListUsagesDisplayContext(renderRequest, renderResponse);
%>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<div class="row">
		<div class="col-lg-3">
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<strong class="text-uppercase">
							<liferay-ui:message key="usages" />
						</strong>

						<ul class="nav nav-stacked">
							<li class="nav-item">

								<%
								PortletURL allNavigationURL = assetListUsagesDisplayContext.getPortletURL();

								allNavigationURL.setParameter("navigation", "all");
								%>

								<a class="nav-link <%= Objects.equals(assetListUsagesDisplayContext.getNavigation(), "all") ? "active" : StringPool.BLANK %>" href="<%= allNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= assetListUsagesDisplayContext.getAllUsageCount() %>" key="all-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL pagesNavigationURL = assetListUsagesDisplayContext.getPortletURL();

								pagesNavigationURL.setParameter("navigation", "pages");
								%>

								<a class="nav-link <%= Objects.equals(assetListUsagesDisplayContext.getNavigation(), "pages") ? "active" : StringPool.BLANK %>" href="<%= pagesNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= assetListUsagesDisplayContext.getPagesUsageCount() %>" key="pages-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL pageTemplatesNavigationURL = assetListUsagesDisplayContext.getPortletURL();

								pageTemplatesNavigationURL.setParameter("navigation", "page-templates");
								%>

								<a class="nav-link <%= Objects.equals(assetListUsagesDisplayContext.getNavigation(), "page-templates") ? "active" : StringPool.BLANK %>" href="<%= pageTemplatesNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= assetListUsagesDisplayContext.getPageTemplatesUsageCount() %>" key="page-templates-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL displayPagesNavigationURL = assetListUsagesDisplayContext.getPortletURL();

								displayPagesNavigationURL.setParameter("navigation", "display-pages");
								%>

								<a class="nav-link <%= Objects.equals(assetListUsagesDisplayContext.getNavigation(), "display-pages") ? "active" : StringPool.BLANK %>" href="<%= displayPagesNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= assetListUsagesDisplayContext.getDisplayPagesUsageCount() %>" key="display-pages-x" />
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>

		<div class="col-lg-9">
			<div class="sheet">
				<h3 class="sheet-title">
					<c:choose>
						<c:when test='<%= Objects.equals(assetListUsagesDisplayContext.getNavigation(), "pages") %>'>
							<liferay-ui:message arguments="<%= assetListUsagesDisplayContext.getPagesUsageCount() %>" key="pages-x" />
						</c:when>
						<c:when test='<%= Objects.equals(assetListUsagesDisplayContext.getNavigation(), "page-templates") %>'>
							<liferay-ui:message arguments="<%= assetListUsagesDisplayContext.getPageTemplatesUsageCount() %>" key="page-templates-x" />
						</c:when>
						<c:when test='<%= Objects.equals(assetListUsagesDisplayContext.getNavigation(), "display-pages") %>'>
							<liferay-ui:message arguments="<%= assetListUsagesDisplayContext.getDisplayPagesUsageCount() %>" key="display-pages-x" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments="<%= assetListUsagesDisplayContext.getAllUsageCount() %>" key="all-x" />
						</c:otherwise>
					</c:choose>
				</h3>
			</div>
		</div>
	</div>
</div>