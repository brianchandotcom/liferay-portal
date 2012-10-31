<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<%
UnicodeProperties typeSettingsProperties = selLayout.getTypeSettingsProperties();

String description = typeSettingsProperties.getProperty("description", StringPool.BLANK);
String panelSelectedPortlets = typeSettingsProperties.getProperty("panelSelectedPortlets", StringPool.BLANK);
%>

<aui:input cssClass="layout-description" label="description" name="TypeSettingsProperties--description--" type="textarea" value="<%= description %>" wrap="soft" />

<div class="portlet-msg-info">
	<liferay-ui:message key="select-the-applications-that-will-be-available-in-the-panel" />
</div>

<aui:input id="panelSelectedPortlets" name="TypeSettingsProperties--panelSelectedPortlets--" type="hidden" value="<%= panelSelectedPortlets %>" />

<%
String panelTreeKey = "panelSelectedPortletsPanelTree";
%>

<div id="<portlet:namespace />panelSelectPortletsOutput" style="margin: 4px;"></div>

<aui:script use="aui-tree-view">
	var initPanelSelectPortlets = function(event) {
		var panelSelectedPortletsEl = A.one('#<portlet:namespace />panelSelectedPortlets');
		var selectedPortlets = panelSelectedPortletsEl.val().split(',');

		var onCheck = function(event, plid) {
			var node = event.target;
			var add = A.Array.indexOf(selectedPortlets, plid) == -1;

			if (plid && add) {
				selectedPortlets.push(plid);

				panelSelectedPortletsEl.val(selectedPortlets.join(','));
			}
		};

		var onUncheck = function(event, plid) {
			var node = event.target;

			if (plid) {
				if (selectedPortlets.length) {
					A.Array.removeItem(selectedPortlets, plid);
				}

				panelSelectedPortletsEl.val(selectedPortlets.join(','));
			}
		};

		var treeView = new A.TreeView(
			{
				boundingBox: '#<portlet:namespace />panelSelectPortletsOutput'
			}
		).render();

		<%
		PortletLister portletLister = PortletListerFactoryUtil.getPortletLister();

		portletLister.setIncludeInstanceablePortlets(false);
		portletLister.setIteratePortlets(true);
		portletLister.setLayoutTypePortlet(layoutTypePortlet);
		portletLister.setRootNodeName(LanguageUtil.get(pageContext, "application"));
		portletLister.setServletContext(application);
		portletLister.setThemeDisplay(themeDisplay);
		portletLister.setUser(user);

		TreeView treeView = portletLister.getTreeView();

		List<TreeNodeView> treeNodeViews = treeView.getList();

		for (int i = 0; i < treeNodeViews.size(); i++) {
			TreeNodeView treeNodeView = treeNodeViews.get(i);
		%>

			var parentNode<%= i %> = treeView.getNodeById('treePanel<%= treeNodeView.getParentId() %>') || treeView;
			var objId<%= i %> = '<%= treeNodeView.getObjId() %>';
			var checked<%= i %> = objId<%= i %> ? (A.Array.indexOf(selectedPortlets, objId<%= i %>) > -1) : false;
			var label<%= i %> = '<%= UnicodeFormatter.toString(LanguageUtil.get(user.getLocale(), treeNodeView.getName())) %>';

			parentNode<%= i %>.appendChild(
				new A.TreeNodeTask(
					{
						after: {
							checkedChange: function(event) {
								if (event.newVal) {
									onCheck(event, objId<%= i %>);
								}
								else {
									onUncheck(event, objId<%= i %>);
								}
							}
						},
						checked: checked<%= i %>,
						expanded: <%= treeNodeView.getDepth() == 0 %>,
						id: 'treePanel<%= treeNodeView.getId() %>',
						label: label<%= i %>,
						leaf: <%= treeNodeView.isLeaf() %>
					}
				)
			);

		<%
		}
		%>

		initPanelSelectPortlets = A.Lang.emptyFn;
	};

	if (<%= selLayout.isTypePanel() %>) {
		initPanelSelectPortlets();
	}

	Liferay.on(
		'<portlet:namespace />toggleLayoutTypeFields',
		function(event) {
			if (event.type == 'panel') {
				initPanelSelectPortlets();
			}
		}
	);
</aui:script>