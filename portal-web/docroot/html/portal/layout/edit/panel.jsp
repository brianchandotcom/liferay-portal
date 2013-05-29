<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String description = StringPool.BLANK;
String panelSelectedPortlets = StringPool.BLANK;

if (Validator.isNotNull(selLayout)) {
	UnicodeProperties typeSettingsProperties = selLayout.getTypeSettingsProperties();

	description = typeSettingsProperties.getProperty("description", StringPool.BLANK);
	panelSelectedPortlets = typeSettingsProperties.getProperty("panelSelectedPortlets", StringPool.BLANK);
}
%>

<aui:input cssClass="layout-description" label="description" name="TypeSettingsProperties--description--" type="textarea" value="<%= description %>" wrap="soft" />

<div class="alert alert-info">
	<liferay-ui:message key="select-the-applications-that-will-be-available-in-the-panel" />
</div>

<aui:input id="panelSelectedPortlets" name="TypeSettingsProperties--panelSelectedPortlets--" type="hidden" value="<%= panelSelectedPortlets %>" />

<%
String panelTreeKey = "panelSelectedPortletsPanelTree";
%>

<div class="lfr-tree-loading" id="<portlet:namespace />selectPortletsTreeLoading">
	<span class="icon icon-loading lfr-tree-loading-icon"></span>
</div>

<div id="<portlet:namespace />selectPortletsTree" style="margin: 4px;"></div>

<aui:script use="aui-tree-view">
	var panelSelectedPortletsEl = A.one('#<portlet:namespace />panelSelectedPortlets');

	var selectedPortlets = A.Array.hash(panelSelectedPortletsEl.val().split(','));

	var TreeUtil = {
		formatJSONResults: function(json) {
			var output = [];

			A.each(
				json.children.list,
				function(item, index, collection) {
					var childPortlets = [];
					var total = 0;

					var nodeChildren = item.children;
					var plid = item.objId;

					var checked = plid && (plid in selectedPortlets);

					if (nodeChildren) {
						childPortlets = nodeChildren.list;
						total = childPortlets.length;
					}

					var newNode = {
						after: {
							checkedChange: function(event) {
								if (plid) {
									if (event.newVal) {
										selectedPortlets[plid] = true;
									}
									else if (selectedPortlets[plid]) {
										delete selectedPortlets[plid];
									}

									panelSelectedPortletsEl.val(A.Object.keys(selectedPortlets));
								}
							}
						},
						alwaysShowHitArea: total,
						checked: checked,
						draggable: false,
						expanded: false,
						id: item.id,
						label: item.name,
						leaf: item.leaf,
						type: 'task'
					}

					if (nodeChildren) {
						newNode.children = TreeUtil.formatJSONResults(item);
					}

					output.push(newNode);
				}
			);

			return output;
		}
	};

	var initPanelSelectPortlets = function(event) {

		<%
		PortletLister portletLister = PortletListerFactoryUtil.getPortletLister();

		portletLister.setHierarchicalTree(true);
		portletLister.setIncludeInstanceablePortlets(false);
		portletLister.setIteratePortlets(true);
		portletLister.setLayoutTypePortlet(layoutTypePortlet);
		portletLister.setRootNodeName(LanguageUtil.get(pageContext, "application"));
		portletLister.setServletContext(application);
		portletLister.setThemeDisplay(themeDisplay);
		portletLister.setUser(user);

		JSONObject portletsJSON = JSONFactoryUtil.createJSONObject(JSONFactoryUtil.serialize(portletLister.getTreeView()));
		%>

		var portletList = <%= portletsJSON %>.serializable.list.list[0];

		var rootNode = new A.TreeNodeTask(
			{
				alwaysShowHitArea: true,
				children: TreeUtil.formatJSONResults(portletList),
				draggable: false,
				expanded: true,
				id: '<portlet:namespace />selectPortletsRootNode',
				label: portletList.name,
				leaf: false
			}
		);

		var treeview = new A.TreeView(
			{
				after: {
					render: function() {
						A.one('#<portlet:namespace />selectPortletsTreeLoading').hide();
					}
				},
				boundingBox: '#<portlet:namespace />selectPortletsTree',
				children: [rootNode],
				type: 'file'
			}
		).render();

		initPanelSelectPortlets = A.Lang.emptyFn;
	};

	if (<%= Validator.isNull(selLayout) || selLayout.isTypePanel() %>) {
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