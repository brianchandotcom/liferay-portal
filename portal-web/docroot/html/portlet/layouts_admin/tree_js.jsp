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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%@ include file="/html/portlet/layouts_admin/init_attributes.jspf" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

boolean incomplete = ParamUtil.getBoolean(request, "incomplete", true);

String treeLoading = PortalUtil.generateRandomKey(request, "treeLoading");

String treeId = ParamUtil.getString(request, "treeId");
boolean checkContentDisplayPage = ParamUtil.getBoolean(request, "checkContentDisplayPage", false);
boolean defaultStateChecked = ParamUtil.getBoolean(request, "defaultStateChecked", false);
boolean draggableTree = ParamUtil.getBoolean(request, "draggableTree", true);
boolean expandFirstNode = ParamUtil.getBoolean(request, "expandFirstNode", true);
boolean saveState = ParamUtil.getBoolean(request, "saveState", true);
boolean selectableTree = ParamUtil.getBoolean(request, "selectableTree");

String modules = "liferay-layouts-tree";

if (selectableTree) {
	modules += ",liferay-layouts-tree-selectable";
}

if (checkContentDisplayPage) {
	modules += ",liferay-layouts-tree-check-content-display-page";
}

if (saveState) {
	modules += ",liferay-layouts-tree-state";
}
%>

<aui:script use="<%= modules %>">
	var plugins = [];

	<c:if test="<%= selectableTree %>">
		plugins.push(
			{
				cfg: {
					default: <%= defaultStateChecked %>
				},
				fn: A.Plugin.LayoutsTreeSelectable
			}
		);
	</c:if>

	<c:if test="<%= checkContentDisplayPage %>">
		plugins.push(A.Plugin.LayoutsTreeCheckContentDisplayPage);
	</c:if>

	<c:if test="<%= saveState %>">
		<%
		JSONArray checkedNodesJSONArray = JSONFactoryUtil.createJSONArray();

		String checkedLayoutIds = SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode");

		if (cmd.equals(Constants.UPDATE)) {
			checkedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");
		}

		if (Validator.isNotNull(checkedLayoutIds)) {
			for (long checkedLayoutId : StringUtil.split(checkedLayoutIds, 0L)) {
				try {
					Layout checkedLayout = LayoutLocalServiceUtil.getLayout(groupId, privateLayout, checkedLayoutId);

					checkedNodesJSONArray.put(String.valueOf(checkedLayout.getPlid()));
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}
		%>

		plugins.push(
			{
				cfg: {
					checkedNodes: <%= checkedNodesJSONArray.toString() %>,
					rootNodeExpanded: <%= GetterUtil.getBoolean(SessionClicks.get(request, treeId + "RootNode", null), true) %>
				},
				fn: A.Plugin.LayoutsTreeState
			}
		);
	</c:if>

	<%
	long[] openNodes = StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeId), 0L);

	JSONObject layoutsJSON = JSONFactoryUtil.createJSONObject(LayoutsTreeUtil.getLayoutsJSON(request, groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, openNodes, true));
	%>

	var TreeViewType = Liferay.LayoutsTree;

	<c:choose>
		<c:when test="<%= draggableTree %>">
			TreeViewType = Liferay.LayoutsTreeDD;
		</c:when>
	</c:choose>

	var layoutsTree = new TreeViewType(
		{
			boundingBox: '#<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output',
			layouts: <%= layoutsJSON %>,
			layoutURL: '<%= portletURL + StringPool.AMPERSAND + portletDisplay.getNamespace() + "selPlid={selPlid}" + StringPool.AMPERSAND + portletDisplay.getNamespace() %>',
			maxChildren: <%= PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN %>,
			plugins: plugins,
			root: {
				defaultParentLayoutId: <%= LayoutConstants.DEFAULT_PARENT_LAYOUT_ID %>,
				expand: <%= expandFirstNode %>,
				groupId: <%= groupId %>,
				label: '<%= HtmlUtil.escapeJS(rootNodeName) %>',
				privateLayout: <%= privateLayout %>
			},
			selPlid: '<%= selPlid %>'
		}
	).render();
</aui:script>

<div class="lfr-tree" data-treeid="<%= HtmlUtil.escapeAttribute(treeId) %>" id="<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output"></div>