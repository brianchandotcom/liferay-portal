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

<div id="<%= liferayPortletResponse.getNamespace() + "-layout-finder" %>">
	<react:component
		module="js/LayoutFinder.es"
		props="<%= layoutsTreeDisplayContext.getLayoutFinderData() %>"
		servletContext="<%= application %>"
	/>
</div>

<div id="<portlet:namespace />layoutsTree">
	<div id="<%= liferayPortletResponse.getNamespace() + "-page-type" %>">
		<react:component
			module="js/PageTypeSelector.es"
			props="<%= layoutsTreeDisplayContext.getPageTypeSelectorData() %>"
			servletContext="<%= application %>"
		/>
	</div>

	<c:choose>
		<c:when test="<%= layoutsTreeDisplayContext.isSiteNavigationMenu() %>">
			<div>
				<react:component
					module="js/NavigationMenuItemsTree.es"
					props="<%= layoutsTreeDisplayContext.getSiteNavigationMenuData() %>"
					servletContext="<%= application %>"
				/>
			</div>
		</c:when>
		<c:otherwise>
			<liferay-util:buffer
				var="linkTemplate"
			>
				<clay:content-row
					containerElement="span"
				>
					<a class="{cssClass}" data-regular-url="{regularURL}" data-url="{url}" data-uuid="{uuid}" href="{url}" id="{id}" title="{title}">
						<span class="c-inner" tabindex="-1">
							<span class="mr-1 list-icon {type}-layout">
								<aui:icon image="list" markupView="lexicon" />
							</span>
							<span class="mr-1 page-icon {type}-layout">
								<aui:icon image="page" markupView="lexicon" />
							</span>
							{label}
						</span>
					</a>

					<c:if test="<%= (layoutsTreeDisplayContext.hasAddLayoutPermission() && ((stagingGroup == null) || Objects.equals(scopeGroupId, stagingGroupId))) || layoutsTreeDisplayContext.hasConfigureLayoutPermission() %>">
						<span class="d-sm-block dropdown pages-tree-dropdown text-right">
							<button class="btn btn-unstyled dropdown-toggle ml-1 taglib-icon" data-toggle="liferay-dropdown">
								<aui:icon image="ellipsis-v" markupView="lexicon" />

								<span class="sr-only">
									<liferay-ui:message key="options" />
								</span>
							</button>

							<ul class="dropdown-menu dropdown-menu-left" role="menu">
								<li class="preview-draft-action-option {draftStatus}">
									<clay:content-row
										containerElement="a"
										cssClass="dropdown-item layout-action"
										href="{draftURL}"
										target="_blank"
									>
										<clay:content-col
											containerElement="span"
											expand="<%= true %>"
										>
											<clay:content-section
												containerElement="span"
												cssClass="text-left"
											>
												<liferay-ui:message key="preview-draft" />
											</clay:content-section>
										</clay:content-col>
									</clay:content-row>
								</li>

								<c:if test="<%= layoutsTreeDisplayContext.hasAddLayoutPermission() && ((stagingGroup == null) || Objects.equals(scopeGroupId, stagingGroupId)) %>">
									<li class="child-page-action-option type-{parentable}">
										<clay:content-row
											containerElement="a"
											cssClass="dropdown-item layout-action"
											href="<%= layoutsTreeDisplayContext.getAddChildURLTemplate() %>"
										>
											<clay:content-col
												containerElement="span"
												expand="<%= true %>"
											>
												<clay:content-section
													containerElement="span"
													cssClass="text-left"
												>
													<liferay-ui:message key="add-child-page" />
												</clay:content-section>
											</clay:content-col>
										</clay:content-row>
									</li>
									<li class="child-page-action-option type-{parentable}">
										<clay:content-row
											containerElement="a"
											cssClass="dropdown-item layout-action"
											href="<%= layoutsTreeDisplayContext.getAddChildCollectionURLTemplate() %>"
										>
											<clay:content-col
												containerElement="span"
												expand="<%= true %>"
											>
												<clay:content-section
													containerElement="span"
													cssClass="text-left"
												>
													<liferay-ui:message key="add-child-collection-page" />
												</clay:content-section>
											</clay:content-col>
										</clay:content-row>
									</li>
								</c:if>

								<c:if test="<%= layoutsTreeDisplayContext.hasConfigureLayoutPermission() %>">
									<li>
										<clay:content-row
											containerElement="a"
											cssClass="dropdown-item layout-action"
											href="<%= layoutsTreeDisplayContext.getConfigureLayoutURLTemplate() %>"
										>
											<clay:content-col
												containerElement="span"
												expand="<%= true %>"
											>
												<clay:content-section
													containerElement="span"
													cssClass="text-left"
												>
													<liferay-ui:message key="configure" />
												</clay:content-section>
											</clay:content-col>
										</clay:content-row>
									</li>
								</c:if>

								<li class="view-collection-items-action-option {type}" data-view-collection-items-url="<%= layoutsTreeDisplayContext.getViewCollectionItemsURL() %>">
									<clay:content-row
										containerElement="a"
										cssClass="dropdown-item layout-action"
										href="javascript:;"
									>
										<clay:content-col
											containerElement="span"
											expand="<%= true %>"
										>
											<clay:content-section
												containerElement="span"
												cssClass="text-left"
											>
												<liferay-ui:message key="view-collection-items" />
											</clay:content-section>
										</clay:content-col>
									</clay:content-row>
								</li>
							</ul>
						</c:if>
					</span>
				</clay:content-row>
			</liferay-util:buffer>

			<%
			Group siteGroup = themeDisplay.getSiteGroup();
			%>

			<liferay-layout:layouts-tree
				groupId="<%= layoutsTreeDisplayContext.getGroupId() %>"
				linkTemplate="<%= linkTemplate %>"
				privateLayout="<%= layoutsTreeDisplayContext.isPrivateLayout() %>"
				rootLinkTemplate='<a tabindex="-1" class="{cssClass}ml-1" href="javascript:void(0);" id="{id}" title="{title}">{label}</a>'
				rootNodeName="<%= siteGroup.getLayoutRootNodeName(layoutsTreeDisplayContext.isPrivateLayout(), locale) %>"
				selPlid="<%= layoutsTreeDisplayContext.getSelPlid() %>"
				treeId="pagesTree"
			/>

			<c:if test="<%= layoutsTreeDisplayContext.hasAdministrationPortletPermission() %>">
				<div class="pages-administration-link">
					<aui:a cssClass="ml-2" href="<%= layoutsTreeDisplayContext.getAdministrationPortletURL() %>"><%= LanguageUtil.get(request, "go-to-pages-administration") %></aui:a>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>

<liferay-frontend:component
	componentId="<%= ProductNavigationProductMenuWebKeys.PAGES_TREE %>"
	module="js/PagesTree.es"
/>