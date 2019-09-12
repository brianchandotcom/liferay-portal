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

<%@ include file="/tree_menu/init.jsp" %>

<%
String title = ParamUtil.getString(request, "title", wikiGroupServiceConfiguration.frontPageName());

String portletId = PortletProviderUtil.getPortletId(WikiPage.class.getName(), PortletProvider.Action.VIEW);

PortletURL viewURL = liferayPortletResponse.createRenderURL(portletId);

viewURL.setParameter("mvcRenderCommandName", "/wiki/view_page");

List<MenuItem> menuItems = MenuItem.fromWikiNode(selNodeId, depth, viewURL);
%>

<c:choose>
	<c:when test="<%= !menuItems.isEmpty() %>">
		<%= _buildTreeMenuHTML(menuItems, title, true) %>

		<aui:script use="aui-tree-view">
			var wikiPageList = A.one('.wiki-navigation-portlet-tree-menu .tree-menu');

			var treeView = new A.TreeView({
				contentBox: wikiPageList
			}).render();

			var selected = wikiPageList.one('.tree-node .tag-selected');

			if (selected) {
				var selectedChild = treeView.getNodeByChild(selected);

				selectedChild.expand();

				selectedChild.eachParent(function(node) {
					if (node instanceof A.TreeNode) {
						node.expand();
					}
				});
			}
		</aui:script>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="no-wiki-pages-were-found" />
	</c:otherwise>
</c:choose>

<%!
private String _buildTreeMenuHTML(List<MenuItem> menuItems, String curTitle, boolean isRoot) {
	StringBuilder sb = new StringBuilder();

	if (isRoot) {
		sb.append("<ul class=\"tree-menu\">");
	}

	for (MenuItem menuItem : menuItems) {
		String label = menuItem.getLabel();
		String url = menuItem.getURL();

		sb.append("<li class=\"tree-node\">");

		if (Validator.isNotNull(url)) {
			sb.append("<a ");

			if (label.equals(curTitle)) {
				sb.append("class=\"tag-selected\" ");
			}

			sb.append("href=\"");
			sb.append(url);
			sb.append("\">");
			sb.append(label);
			sb.append("</a>");
		}
		else {
			sb.append(label);
		}

		if (!menuItem.getChildren().isEmpty()) {
			sb.append("<ul>");
			sb.append(_buildTreeMenuHTML(menuItem.getChildren(), curTitle, false));
			sb.append("</ul>");
		}

		sb.append("</li>");
	}

	if (isRoot) {
		sb.append("</ul>");
	}

	return sb.toString();
}
%>