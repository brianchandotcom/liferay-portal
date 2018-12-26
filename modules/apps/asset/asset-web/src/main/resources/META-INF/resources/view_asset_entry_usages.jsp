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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

AssetEntryUsagesDisplayContext assetEntryUsagesDisplayContext = new AssetEntryUsagesDisplayContext(renderRequest, renderResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(assetEntryUsagesDisplayContext.getAssetEntryTitle());
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
								PortletURL allNavigationURL = assetEntryUsagesDisplayContext.getPortletURL();

								allNavigationURL.setParameter("navigation", "all");
								%>

								<a class="nav-link <%= Objects.equals(assetEntryUsagesDisplayContext.getNavigation(), "all") ? "active" : StringPool.BLANK %>" href="<%= allNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getAllUsageCount() %>" key="all-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL pagesNavigationURL = assetEntryUsagesDisplayContext.getPortletURL();

								pagesNavigationURL.setParameter("navigation", "pages");
								%>

								<a class="nav-link <%= Objects.equals(assetEntryUsagesDisplayContext.getNavigation(), "pages") ? "active" : StringPool.BLANK %>" href="<%= pagesNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getPagesUsageCount() %>" key="pages-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL pageTemplatesNavigationURL = assetEntryUsagesDisplayContext.getPortletURL();

								pageTemplatesNavigationURL.setParameter("navigation", "page-templates");
								%>

								<a class="nav-link <%= Objects.equals(assetEntryUsagesDisplayContext.getNavigation(), "page-templates") ? "active" : StringPool.BLANK %>" href="<%= pageTemplatesNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getPageTemplatesUsageCount() %>" key="page-templates-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL displayPagesNavigationURL = assetEntryUsagesDisplayContext.getPortletURL();

								displayPagesNavigationURL.setParameter("navigation", "display-pages");
								%>

								<a class="nav-link <%= Objects.equals(assetEntryUsagesDisplayContext.getNavigation(), "display-pages") ? "active" : StringPool.BLANK %>" href="<%= displayPagesNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getDisplayPagesUsageCount() %>" key="display-pages-x" />
								</a>
							</li>
							<li class="nav-item">

								<%
								PortletURL fragmentsNavigationURL = assetEntryUsagesDisplayContext.getPortletURL();

								displayPagesNavigationURL.setParameter("navigation", "fragments");
								%>

								<a class="nav-link <%= Objects.equals(assetEntryUsagesDisplayContext.getNavigation(), "fragments") ? "active" : StringPool.BLANK %>" href="<%= fragmentsNavigationURL.toString() %>">
									<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getFragmentsUsageCount() %>" key="fragments-x" />
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
						<c:when test='<%= Objects.equals(assetEntryUsagesDisplayContext.getNavigation(), "pages") %>'>
							<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getPagesUsageCount() %>" key="pages-x" />
						</c:when>
						<c:when test='<%= Objects.equals(assetEntryUsagesDisplayContext.getNavigation(), "page-templates") %>'>
							<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getPageTemplatesUsageCount() %>" key="page-templates-x" />
						</c:when>
						<c:when test='<%= Objects.equals(assetEntryUsagesDisplayContext.getNavigation(), "display-pages") %>'>
							<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getDisplayPagesUsageCount() %>" key="display-pages-x" />
						</c:when>
						<c:when test='<%= Objects.equals(assetEntryUsagesDisplayContext.getNavigation(), "fragments") %>'>
							<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getFragmentsUsageCount() %>" key="fragments-x" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments="<%= assetEntryUsagesDisplayContext.getAllUsageCount() %>" key="all-x" />
						</c:otherwise>
					</c:choose>
				</h3>

				<%
				AssetEntryUsagesManagementToolbarDisplayContext assetEntryUsagesManagementToolbarDisplayContext = new AssetEntryUsagesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, assetEntryUsagesDisplayContext.getSearchContainer());
				%>

				<clay:management-toolbar
					displayContext="<%= assetEntryUsagesManagementToolbarDisplayContext %>"
				/>

				<liferay-ui:search-container
					id="assetEntryUsages"
					searchContainer="<%= assetEntryUsagesDisplayContext.getSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.asset.model.AssetEntryUsage"
						keyProperty="assetEntryUsageId"
						modelVar="assetEntryUsage"
					>
						<liferay-ui:search-container-column-text
							name="name"
							value="<%= assetEntryUsagesDisplayContext.getAssetEntryUsageName(assetEntryUsage) %>"
						/>

						<liferay-ui:search-container-column-text
							name="type"
							translate="<%= true %>"
							value="<%= assetEntryUsagesDisplayContext.getAssetEntryUsageTypeLabel(assetEntryUsage) %>"
						/>

						<liferay-ui:search-container-column-text
							name="portlet"
							translate="<%= true %>"
							value="<%= PortalUtil.getPortletTitle(assetEntryUsage.getPortletId(), locale) %>"
						/>

						<liferay-ui:search-container-column-date
							name="modified-date"
							value="<%= assetEntryUsage.getModifiedDate() %>"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="list"
						markupView="lexicon"
						searchResultCssClass="show-quick-actions-on-hover table table-autofit"
					/>
				</liferay-ui:search-container>
			</div>
		</div>
	</div>
</div>