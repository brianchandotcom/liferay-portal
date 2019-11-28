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

Group group = themeDisplay.getSiteGroup();
%>

<div id="<%= renderResponse.getNamespace() + "-layout-finder" %>">
	<react:component
		data="<%= layoutsTreeDisplayContext.getLayoutFinderData() %>"
		module="js/LayoutFinder.es"
		servletContext="<%= application %>"
	/>
</div>

<div id="<%= renderResponse.getNamespace() + "layoutsTree" %>">
	<div id="<%= renderResponse.getNamespace() + "-page-type" %>">
		<react:component
			data="<%= layoutsTreeDisplayContext.getPageTypeSelectorData() %>"
			module="js/PageTypeSelector.es"
			servletContext="<%= application %>"
		/>
	</div>

	<liferay-layout:layouts-tree
		groupId="<%= themeDisplay.getSiteGroupId() %>"
		linkTemplate='<a class="{cssClass}" data-regular-url="{regularURL}" data-url="{url}" data-uuid="{uuid}" href="{url}" id="{id}" title="{title}">{label}</a>'
		privateLayout="<%= layoutsTreeDisplayContext.isPrivateLayout() %>"
		rootLinkTemplate='<a class="{cssClass}" href="javascript:void(0);" id="{id}" title="{title}">{label}</a>'
		rootNodeName="<%= group.getLayoutRootNodeName(layoutsTreeDisplayContext.isPrivateLayout(), locale) %>"
		selPlid="<%= plid %>"
		treeId="pagesTree"
	/>

	<div class="pages-administration-link">
		<aui:a cssClass="ml-2" href="<%= layoutsTreeDisplayContext.getAdministrationPortletURL() %>"><%= LanguageUtil.get(request, "go-to-pages-administration") %></aui:a>
	</div>
</div>

<aui:script>
	function destroyTreeComponent() {
		Liferay.destroyComponent(props.namespace + 'pagesTree');
		Liferay.detach('destroyPortlet', destroyTreeComponent);
	}

	Liferay.on('destroyPortlet', destroyTreeComponent);
</aui:script>