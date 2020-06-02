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
LayoutsTreeDisplayContext layoutsTreeDisplayContext = new LayoutsTreeDisplayContext(liferayPortletRequest);
%>

<div id='<%= renderResponse.getNamespace() + "-layout-finder" %>'>
	<react:component
		data="<%= layoutsTreeDisplayContext.getLayoutFinderData() %>"
		module="js/LayoutFinder.es"
		servletContext="<%= application %>"
	/>
</div>

<div id='<%= renderResponse.getNamespace() + "layoutsTree" %>'>
	<div id='<%= renderResponse.getNamespace() + "-page-type" %>'>
		<react:component
			data="<%= layoutsTreeDisplayContext.getPageTypeSelectorData() %>"
			module="js/PageTypeSelector.es"
			servletContext="<%= application %>"
		/>
	</div>

	<liferay-util:buffer
		var="linkTemplate"
	>
		<clay:content-row
			containerElement="span"
		>
			<clay:content-col
				containerElement="span"
			>
				<a class="{cssClass}" data-regular-url="{regularURL}" data-url="{url}" data-uuid="{uuid}" href="{url}" id="{id}" title="{title}">
					{label}
				</a>
			</clay:content-col>

			<clay:content-col
				containerElement="span"
				cssClass="pages-tree-dropdown"
				expand="true"
			>
				<span class="d-sm-block dropdown text-right">
					<button class="btn btn-unstyled dropdown-toggle ml-1 taglib-icon" data-toggle="liferay-dropdown">
						<aui:icon image="ellipsis-v" markupView="lexicon" />

						<span class="sr-only">
							<liferay-ui:message key="options" />
						</span>
					</button>

					<ul class="dropdown-menu dropdown-menu-left" role="menu">
						<c:if test="<%= (stagingGroup == null) || Objects.equals(scopeGroupId, stagingGroupId) %>">
							<li>
								<clay:content-row
									containerElement="a"
									cssClass="dropdown-item layout-action"
									href="<%= layoutsTreeDisplayContext.getAddChildURLTemplate() %>"
								>
									<clay:content-col
										containerElement="span"
										expand="true"
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
						</c:if>

						<li>
							<clay:content-row
								containerElement="a"
								cssClass="dropdown-item layout-action"
								href="<%= layoutsTreeDisplayContext.getConfigureLayoutURLTemplate() %>"
							>
								<clay:content-col
									containerElement="span"
									expand="true"
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
					</ul>
				</span>
			</clay:content-col>
		</clay:content-row>
	</liferay-util:buffer>

	<%
	Group siteGroup = themeDisplay.getSiteGroup();
	%>

	<liferay-layout:layouts-tree
		groupId="<%= layoutsTreeDisplayContext.getGroupId() %>"
		linkTemplate="<%= linkTemplate %>"
		privateLayout="<%= layoutsTreeDisplayContext.isPrivateLayout() %>"
		rootLinkTemplate='<a class="{cssClass}" href="javascript:void(0);" id="{id}" title="{title}">{label}</a>'
		rootNodeName="<%= siteGroup.getLayoutRootNodeName(layoutsTreeDisplayContext.isPrivateLayout(), locale) %>"
		selPlid="<%= plid %>"
		treeId="pagesTree"
	/>

	<div class="pages-administration-link">
		<aui:a cssClass="ml-2" href="<%= layoutsTreeDisplayContext.getAdministrationPortletURL() %>"><%= LanguageUtil.get(request, "go-to-pages-administration") %></aui:a>
	</div>
</div>

<liferay-frontend:component
	componentId="<%= ProductNavigationProductMenuWebKeys.PAGES_TREE_EVENT_HANDLER %>"
	module="js/PagesTreeEventHandler.es"
/>

<aui:script>
	function handleDestroyPortlet() {
		Liferay.destroyComponent('<%= renderResponse.getNamespace() %>pagesTree');
		Liferay.destroyComponent(
			'<%= ProductNavigationProductMenuWebKeys.PAGES_TREE_EVENT_HANDLER %>'
		);

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>