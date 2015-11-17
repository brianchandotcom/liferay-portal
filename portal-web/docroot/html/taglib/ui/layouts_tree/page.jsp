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

<%@ include file="/html/taglib/ui/layouts_tree/init.jsp" %>

<%
boolean checkContentDisplayPage = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:checkContentDisplayPage"));
String checkedNodes = (String)request.getAttribute("liferay-ui:layouts-tree:checkedNodes");
boolean defaultStateChecked = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:defaultStateChecked"));
boolean draggableTree = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:draggableTree"));
boolean expandFirstNode = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:expandFirstNode"));
long groupId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:layouts-tree:groupId"));
boolean incomplete = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:incomplete"));
String linkTemplate = (String)request.getAttribute("liferay-ui:layouts-tree:linkTemplate");
String modules = (String)request.getAttribute("liferay-ui:layouts-tree:modules");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:layouts-tree:portletURL");
boolean privateLayout = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:privateLayout"));
String rootLinkTemplate = (String)request.getAttribute("liferay-ui:layouts-tree:rootLinkTemplate");
String rootNodeName = (String)request.getAttribute("liferay-ui:layouts-tree:rootNodeName");
boolean saveState = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:saveState"));
boolean selectableTree = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:selectableTree"));
Long selPlid = (Long)request.getAttribute("liferay-ui:layouts-tree:selPlid");
String treeId = (String)request.getAttribute("liferay-ui:layouts-tree:treeId");
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
		plugins.push(
			{
				cfg: {
					checkedNodes: <%= checkedNodes %>,
					rootNodeExpanded: <%= GetterUtil.getBoolean(SessionClicks.get(request, "com.liferay.frontend.js.web_" + treeId + "RootNode", null), true) %>
				},
				fn: A.Plugin.LayoutsTreeState
			}
		);
	</c:if>

	var TreeViewType = Liferay.LayoutsTree;

	<c:if test="<%= draggableTree %>">
		TreeViewType = Liferay.LayoutsTreeDD;
	</c:if>

	new TreeViewType(
		{
			after: {
				'*:expandedChange': function() {
					if (Liferay.Surface) {
						Liferay.Surface.clearCache();
					}
				}
			},
			boundingBox: '#<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output',
			incomplete: <%= incomplete %>,

			<%
			long[] openNodes = StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeId), 0L);

			JSONObject layoutsJSONObject = JSONFactoryUtil.createJSONObject(LayoutsTreeUtil.getLayoutsJSON(request, groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, openNodes, true, treeId));
			%>

			layouts: <%= layoutsJSONObject %>,

			<%
			portletURL.setParameter("selPlid", "{selPlid}");
			%>

			layoutURL: '<%= StringUtil.replace(portletURL.toString(), HttpUtil.encodePath("{selPlid}"), "{selPlid}") %>',

			<c:if test="<%= Validator.isNotNull(linkTemplate) %>">
				linkTemplate: '<%= HtmlUtil.escapeJS(linkTemplate) %>',
			</c:if>

			<c:if test="<%= draggableTree %>">
				lazyLoad: false,
			</c:if>

			maxChildren: <%= PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN %>,
			on: {
				'*:select': function(event) {
					Liferay.fire(
						'<%= namespace + treeId %>:selectedNode',
						{
							selectedNode: event.target
						}
					);
				}
			},
			plugins: plugins,
			root: {
				defaultParentLayoutId: <%= LayoutConstants.DEFAULT_PARENT_LAYOUT_ID %>,
				expand: <%= expandFirstNode %>,
				groupId: <%= groupId %>,
				label: '<%= HtmlUtil.escapeJS(rootNodeName) %>',

				<c:if test="<%= Validator.isNotNull(rootLinkTemplate) %>">
					linkTemplate: '<%= HtmlUtil.escapeJS(rootLinkTemplate) %>',
				</c:if>

				privateLayout: <%= privateLayout %>
			}

			<c:if test="<%= selPlid != null %>">
				,
				selPlid: '<%= selPlid %>'
			</c:if>

		}
	).render();
</aui:script>

<div class="lfr-tree" data-treeid="<%= HtmlUtil.escapeAttribute(treeId) %>" id="<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output"></div>