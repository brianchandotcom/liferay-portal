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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%@ include file="/html/portlet/layouts_admin/init_attributes.jspf" %>

<%
boolean incomplete = ParamUtil.getBoolean(request, "incomplete", true);

String treeLoading = PortalUtil.generateRandomKey(request, "treeLoading");

String treeId = ParamUtil.getString(request, "treeId");
boolean checkContentDisplayPage = ParamUtil.getBoolean(request, "checkContentDisplayPage", false);
boolean defaultStateChecked = ParamUtil.getBoolean(request, "defaultStateChecked", false);
boolean draggableTree = ParamUtil.getBoolean(request, "draggableTree", true);
boolean expandFirstNode = ParamUtil.getBoolean(request, "expandFirstNode", true);
boolean saveState = ParamUtil.getBoolean(request, "saveState", true);
boolean selectableTree = ParamUtil.getBoolean(request, "selectableTree");

String modules = "aui-io-request,aui-tree-view,dataschema-xml,datatype-xml,liferay-store";

if (!selectableTree) {
	modules += ",liferay-history-manager";
}
%>

<aui:script use="<%= modules %>">
	var Lang = A.Lang;
	var AArray = A.Array;

	var Util = Liferay.Util;

	var GET_LAYOUTS_URL = themeDisplay.getPathMain() + '/layouts_admin/get_layouts';

	var LAYOUT_URL = '<%= portletURL + StringPool.AMPERSAND + portletDisplay.getNamespace() + "selPlid={selPlid}" + StringPool.AMPERSAND + portletDisplay.getNamespace() + "historyKey={historyKey}" %>';

	var STR_CHILDREN = 'children';

	var TREE_CSS_CLASSES = {
		iconCheck: 'tree-icon icon-check',
		iconCollapsed: 'icon-file',
		iconExpanded: 'icon-file',
		iconHitAreaCollapsed: 'tree-hitarea icon-plus',
		iconHitAreaExpanded: 'tree-hitarea icon-minus',
		iconLeaf: 'icon-leaf',
		iconLoading: 'icon-refresh',
		iconUncheck: 'icon-check'
	};

	<%
	JSONArray checkedNodesJSONArray = JSONFactoryUtil.createJSONArray();

	String checkedLayoutIds = SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode");

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

	var TreeUtil = {
		CHECKED_NODES: <%= checkedNodesJSONArray.toString() %>,
		DEFAULT_PARENT_LAYOUT_ID: <%= LayoutConstants.DEFAULT_PARENT_LAYOUT_ID %>,
		PAGINATION_LIMIT: <%= PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN %>,
		PREFIX_GROUP_ID: '_groupId_',
		PREFIX_LAYOUT: '_layout_',
		PREFIX_LAYOUT_ID: '_layoutId_',
		PREFIX_PLID: '_plid_',

		localCheckedNodes: [],
		localUncheckedNodes: [],

		afterRenderTree: function(event) {
			var rootNode = event.target.item(0);

			var loadingEl = A.one('#<portlet:namespace />treeLoading<%= treeLoading %>');

			loadingEl.hide();

			<c:choose>
				<c:when test="<%= saveState && selectableTree %>">
					TreeUtil.restoreCheckedNode(rootNode);
				</c:when>
				<c:when test="<%= expandFirstNode %>">
					rootNode.expand();
				</c:when>
			</c:choose>

			TreeUtil.restoreSelectedNode(rootNode);

			rootNode.eachChildren(TreeUtil.restoreSelectedNode);
		},

		checkNodeState: function(node) {
			var plid = TreeUtil.extractPlid(node);

			var checked;

			if (AArray.indexOf(TreeUtil.localCheckedNodes, plid) > -1) {
				checked = true;
			}
			else if (AArray.indexOf(TreeUtil.localUncheckedNodes, plid) > -1) {
				checked = false;
			}

			if (!Lang.isUndefined(checked)) {
				TreeUtil.updateCheckedNodes(node, checked, true);
			}
		},

		createLabel: function(data) {
			return '<span class="' + data.cssClass + '" title="' + data.title + '">' + data.label + '</span>';
		},

		createListItemId: function(groupId, layoutId, plid) {
			return '<%= HtmlUtil.escape(treeId) %>' + TreeUtil.PREFIX_LAYOUT_ID + layoutId + TreeUtil.PREFIX_PLID + plid + TreeUtil.PREFIX_GROUP_ID + groupId;
		},

		createLinkId: function(friendlyURL) {
			return '<%= HtmlUtil.escape(treeId) %>' + TreeUtil.PREFIX_LAYOUT + friendlyURL.substring(1);
		},

		createLink: function(data) {
			var className = 'layout-tree';

			if (data.cssClass) {
				className += ' ' + data.cssClass;
			}

			if (!data.uuid) {
				data.uuid = "";
			}

			if (!data.id) {
				data.id = "";
			}

			if (<%= checkContentDisplayPage %> && !data.contentDisplayPage) {
				className += ' layout-page-invalid';
			}

			var href = Lang.sub(
				LAYOUT_URL,
				{
					historyKey: data.historyKey,
					selPlid: data.plid
				}
			);

			return '<a class="' + className + '" data-uuid="' + Util.escapeHTML(data.uuid) + '" href="' + href + '" id="' + Util.escapeHTML(data.id) + '" title="' + data.title + '">' + data.label + '</a>';
		},

		displayNotice: function(message, type, timeout, useAnimation) {
			new Liferay.Notice(
				{
					closeText: false,
					content: message + '<button type="button" class="close">&times;</button>',
					noticeClass: 'hide',
					timeout: timeout || 10000,
					toggleText: false,
					type: type || 'warning',
					useAnimation: Lang.isValue(useAnimation) ? useAnimation : true
				}
			).show();
		},

		extractGroupId: function(node) {
			return node.get('id').match(/groupId_(\d+)/)[1];
		},

		extractLayoutId: function(node) {
			return node.get('id').match(/layoutId_(\d+)/)[1];
		},

		extractPlid: function(node) {
			return node.get('id').match(/plid_(\d+)/)[1];
		},

		formatJSONResults: function(json) {
			var output = [];

			A.each(
				json.layouts,
				function(node) {
					var childLayouts = [];
					var cssIcons = {};
					var total = 0;

					var iconCssClassName = 'icon-link';

					var hasChildren = node.hasChildren;
					var nodeChildren = node.children;
					var nodeType = node.type;

					if ((nodeType === 'embedded') ||
						(nodeType === 'link_to_layout') ||
						(nodeType === 'url')) {

						cssIcons = {
							iconCollapsed: iconCssClassName,
							iconExpanded: iconCssClassName,
							iconLeaf: iconCssClassName
						};
					}

					if (nodeChildren) {
						childLayouts = nodeChildren.layouts;
						total = nodeChildren.total;
					}

					var expanded = (childLayouts.length > 0);

					var type = 'task';

					<c:if test="<%= !selectableTree %>">
						type = (total > 0) ? 'io' : 'node';
					</c:if>

					var newNode = {
						<c:if test="<%= saveState %>">
							after: {
								<c:if test="<%= selectableTree %>">
									checkedChange: function(event) {
										if (this === event.originalTarget) {
											var newVal = event.newVal;
											var target = event.target;

											var plid = TreeUtil.extractPlid(target);

											TreeUtil.updateSessionTreeCheckedState('<%= HtmlUtil.escape(treeId) %>SelectedNode', plid, newVal);

											TreeUtil.updateCheckedNodes(target, newVal, true);
										}
									},
								</c:if>

								childrenChange: function(event) {
									var target = event.target;

									target.set('alwaysShowHitArea', (event.newVal.length > 0));

									target.eachChildren(TreeUtil.restoreSelectedNode);

									<c:if test="<%= selectableTree %>">
										if (target.get('checked')) {
											TreeUtil.updateCheckedNodes(target, true, false);
										}

										TreeUtil.restoreCheckedNode(target);
									</c:if>
								},

								expandedChange: function(event) {
									var target = event.target;

									var layoutId = TreeUtil.extractLayoutId(target);

									TreeUtil.updateSessionTreeOpenedState('<%= HtmlUtil.escape(treeId) %>', layoutId, event.newVal);

									<c:if test="<%= selectableTree %>">
										TreeUtil.checkNodeState(target);
									</c:if>
								}
							},
						</c:if>

						alwaysShowHitArea: hasChildren,

						<c:choose>
							<c:when test="<%= !saveState && defaultStateChecked %>">
								checked: true,
							</c:when>
							<c:when test="<%= saveState && selectableTree %>">
								checked: (AArray.indexOf(TreeUtil.CHECKED_NODES, String(node.plid)) > -1) ? true : false,
							</c:when>
						</c:choose>

						cssClasses: {
							pages: A.merge(TREE_CSS_CLASSES, cssIcons)
						},
						draggable: node.sortable,
						expanded: expanded,
						id: TreeUtil.createListItemId(node.groupId, node.layoutId, node.plid),
						io: {
							cfg: {
								data: function(node) {
									return {
										cmd: 'get',
										controlPanelCategory: 'current_site.pages',
										doAsGroupId: themeDisplay.getScopeGroupId(),
										groupId: TreeUtil.extractGroupId(node),
										incomplete: <%= incomplete %>,
										p_auth: Liferay.authToken,
										p_l_id: themeDisplay.getPlid(),
										p_p_id: '88',
										parentLayoutId: TreeUtil.extractLayoutId(node),
										privateLayout: <%= privateLayout %>,
										selPlid: '<%= selPlid %>',
										treeId: '<%= HtmlUtil.escape(treeId) %>'
									};
								},
								method: A.config.io.method,
								on: {
									success: function(event, id, xhr) {
										var instance = this;

										var response;

										try {
											response = A.JSON.parse(xhr.responseText);
										}
										catch (e) {
										}

										if (response) {
											instance.get('paginator').total = response.total;

											instance.syncUI();
										}

										<c:if test="<%= saveState %>">
											TreeUtil.updatePagination(instance);

											<c:if test="<%= selectableTree %>">
												TreeUtil.checkNodeState(instance);
											</c:if>
										</c:if>
									}
								}
							},
							formatter: TreeUtil.formatJSONResults,
							url: GET_LAYOUTS_URL
						},
						leaf: !node.parentable,
						paginator: {
							limit: TreeUtil.PAGINATION_LIMIT,
							offsetParam: 'start',
							start: Math.max(childLayouts.length - TreeUtil.PAGINATION_LIMIT, 0),
							total: total
						},
						type: type
					};

					if (nodeChildren && expanded) {
						newNode.children = TreeUtil.formatJSONResults(nodeChildren);
					}

					var cssClass = '';
					var title = '';

					newNode.label = Util.escapeHTML(node.name);

					if (node.layoutRevisionId) {
						if (!node.layoutRevisionHead) {
							title = '<%= UnicodeLanguageUtil.get(pageContext, "there-is-not-a-version-of-this-page-marked-as-ready-for-publication") %>';
						}
						else if (node.layoutBranchName) {
							node.layoutBranchName = Util.escapeHTML(node.layoutBranchName);

							newNode.label += Lang.sub(' <span class="layout-branch-name" title="<%= UnicodeLanguageUtil.get(pageContext, "this-is-the-page-variation-that-is-marked-as-ready-for-publication") %>">[{layoutBranchName}]</span>', node);
						}

						if (node.incomplete) {
							cssClass = 'incomplete-layout';

							title = '<%= UnicodeLanguageUtil.get(pageContext, "this-page-is-not-enabled-in-this-site-pages-variation,-but-is-available-in-other-variations") %>';
						}
					}

					if (!node.sortable) {
						newNode.cssClass = 'lfr-page-locked';
					}

					if (!<%= selectableTree %>) {
						newNode.label = TreeUtil.createLink(
							{
								contentDisplayPage: node.contentDisplayPage,
								cssClass: cssClass,
								id: TreeUtil.createLinkId(node.friendlyURL),
								label: newNode.label,
								plid: node.plid,
								title: title,
								uuid: node.uuid
							}
						);
					}
					else {
						newNode.label = TreeUtil.createLabel(
							{
								cssClass: cssClass,
								label: newNode.label,
								title: title
							}
						);
					}

					output.push(newNode);
				}
			);

			return output;
		},

		restoreCheckedNode: function(node) {
			var instance = this;

			var plid = TreeUtil.extractPlid(node);

			var tree = node.get('ownerTree');

			var treeNodeTaskSuperClass = A.TreeNodeTask.superclass;

			if (AArray.indexOf(TreeUtil.CHECKED_NODES, plid) > -1) {
				treeNodeTaskSuperClass.check.call(node, tree);
			}
			else {
				treeNodeTaskSuperClass.uncheck.call(node, tree);
			}

			AArray.each(node.get(STR_CHILDREN), TreeUtil.restoreCheckedNode);
		},

		restoreNodePosition: function(response) {
			TreeUtil.displayNotice(response.message, 'warning', 10000, true);

			var nodeId = TreeUtil.createListItemId(response.groupId, response.layoutId, response.plid);
			var parentNodeId = TreeUtil.createListItemId(response.groupId, response.originalParentLayoutId, response.originalParentPlid);

			var action = 'append';

			var index = response.originalPriority;

			var node = treeview.getNodeById(nodeId);
			var parentNode = treeview.getNodeById(parentNodeId);

			var sibling;

			if (index > 0) {
				if (index === parentNode.childrenLength) {
					action = 'append';
				}
				else {
					var siblingIndex = index;

					if (node.get('parentNode').get('id') !== parentNodeId) {
						siblingIndex -= 1;
					}

					sibling = parentNode.item(siblingIndex);

					action = 'after';
				}
			}

			if (sibling) {
				treeview.insert(node, sibling, action);
			}
			else {
				parentNode.appendChild(node);
			}
		},

		restoreSelectedNode: function(node) {
			var plid = TreeUtil.extractPlid(node);

			if (plid == '<%= selPlid %>') {
				node.select();
			}
			else {
				node.unselect();
			}
		},

		updateLayout: function(data) {
			A.io.request(
				themeDisplay.getPathMain() + '/layouts_admin/update_page',
				{
					data: A.mix(
						data,
						{
							controlPanelCategory: 'current_site.pages',
							doAsGroupId: themeDisplay.getScopeGroupId(),
							p_auth: Liferay.authToken,
							p_l_id: themeDisplay.getPlid(),
							p_p_id: '88'
						}
					),
					dataType: 'JSON',
					on: {
						success: function(event, id, xhr) {
							var response;

							try {
								response = A.JSON.parse(xhr.responseText);

								if (response.status === Liferay.STATUS_CODE.BAD_REQUEST) {
									TreeUtil.restoreNodePosition(response);
								}
							}
							catch (e) {
							}
						}
					}
				}
			);
		},

		updateLayoutParent: function(dragPlid, dropPlid, index) {
			TreeUtil.updateLayout(
				{
					cmd: 'parent_layout_id',
					parentPlid: dropPlid,
					plid: dragPlid,
					priority: index
				}
			);
		}

		<c:if test="<%= saveState %>">
			, invokeSessionClick: function(data, callback) {
				A.mix(
					data,
					{
						p_auth: Liferay.authToken,
						useHttpSession: true
					}
				);

				A.io.request(
					themeDisplay.getPathMain() + '/portal/session_click',
					{
						after: {
							success: function(event) {
								var responseData = this.get('responseData');

								if (callback && responseData) {
									callback(responseData);
								}
							}
						},
						data: data
					}
				);
			},

			updatePagination: function(node) {
				var paginationMap = {};

				var updatePaginationMap = function(map, curNode) {
					if (A.instanceOf(curNode, A.TreeNodeIO)) {
						var paginationLimit = TreeUtil.PAGINATION_LIMIT;

						var layoutId = TreeUtil.extractLayoutId(curNode);

						var children = curNode.get(STR_CHILDREN);

						map[layoutId] = Math.ceil(children.length / paginationLimit) * paginationLimit;
					}
				};

				TreeUtil.invokeSessionClick(
					{
						cmd: 'get',
						key: '<%= HtmlUtil.escape(treeId) %>:<%= groupId %>:<%= privateLayout %>:Pagination'
					},
					function(responseData) {
						try {
							paginationMap = A.JSON.parse(responseData);
						}
						catch (e) {
						}

						updatePaginationMap(paginationMap, node);

						node.eachParent(
							function(parent) {
								updatePaginationMap(paginationMap, parent);
							}
						);

						TreeUtil.invokeSessionClick(
							{
								'<%= HtmlUtil.escape(treeId) %>:<%= groupId %>:<%= privateLayout %>:Pagination': A.JSON.stringify(paginationMap)
							}
						);
					}
				);
			},

			updateCheckedNodes: function(node, state, recursive) {
				var plid = TreeUtil.extractPlid(node);

				var checkedNodes = TreeUtil.CHECKED_NODES;
				var localCheckedNodes = TreeUtil.localCheckedNodes;
				var localUncheckedNodes = TreeUtil.localUncheckedNodes;

				var checkedIndex = AArray.indexOf(checkedNodes, plid);
				var localCheckedIndex = AArray.indexOf(localCheckedNodes, plid);
				var localUncheckedIndex = AArray.indexOf(localUncheckedNodes, plid);

				if (state) {
					if (checkedIndex === -1) {
						checkedNodes.push(plid);
					}

					if ((localCheckedIndex == -1) && recursive) {
						localCheckedNodes.push(plid);
					}

					if (localUncheckedIndex > -1) {
						AArray.remove(localUncheckedNodes, localUncheckedIndex);
					}
				}
				else if (checkedIndex > -1) {
					AArray.remove(checkedNodes, checkedIndex);

					localUncheckedNodes.push(plid);

					if (localCheckedIndex > -1) {
						AArray.remove(localCheckedNodes, localCheckedIndex);
					}
				}

				node.set('checked', state);

				var children = node.get(STR_CHILDREN);

				if (children.length && recursive) {
					A.each(
						children,
						function(child) {
							TreeUtil.updateCheckedNodes(child, state, true);
						}
					);
				}
			},

			updateSessionTreeCheckedState: function(treeId, nodeId, state) {
				var data = {
					cmd: state ? 'layoutCheck' : 'layoutUncheck',
					plid: nodeId
				};

				TreeUtil.updateSessionTreeClick(treeId, data);
			},

			updateSessionTreeClick: function(treeId, data) {
				data = A.merge(
					{
						groupId: <%= groupId %>,
						privateLayout: <%= privateLayout %>,
						recursive: true,
						treeId: treeId
					},
					data
				);

				A.io.request(
					themeDisplay.getPathMain() + '/portal/session_tree_js_click',
					{
						data: data
					}
				);
			},

			updateSessionTreeOpenedState: function(treeId, nodeId, state) {
				var data = {
					nodeId: nodeId,
					openNode: state
				};

				TreeUtil.updateSessionTreeClick(treeId, data);
			}
		</c:if>
	};

	var rootLabel = '<%= HtmlUtil.escapeJS(rootNodeName) %>';
	var treeElId = '<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output';

	var RootNodeType = A.TreeNodeTask;
	var TreeViewType = A.TreeView;

	<c:if test="<%= !selectableTree %>">
		RootNodeType = A.TreeNodeIO;

		<c:if test="<%= draggableTree %>">
			TreeViewType = A.TreeViewDD;
		</c:if>

		<c:if test="<%= !checkContentDisplayPage %>">
			rootLabel = TreeUtil.createLink(
				{
					label: Util.escapeHTML(rootLabel),
					plid: TreeUtil.DEFAULT_PARENT_LAYOUT_ID
				}
			);
		</c:if>
	</c:if>

	var rootNode = new RootNodeType(
		{
			<c:if test="<%= saveState %>">
				after: {
					<c:if test="<%= selectableTree %>">
						checkedChange: function(event) {
							var newVal = event.newVal;

							TreeUtil.updateSessionTreeCheckedState('<%= HtmlUtil.escape(treeId) %>SelectedNode', <%= LayoutConstants.DEFAULT_PLID %>, newVal);

							TreeUtil.updateCheckedNodes(event.target, newVal, true);
						},
					</c:if>

					expandedChange: function(event) {
						Liferay.Store('<%= HtmlUtil.escape(treeId) %>RootNode', event.newVal);
					}
				},
			</c:if>

			alwaysShowHitArea: true,

			<c:if test="<%= !saveState && defaultStateChecked %>">
				checked: true,
			</c:if>

			<%
			long[] openNodes = StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeId), 0L);

			JSONObject layoutsJSON = JSONFactoryUtil.createJSONObject(LayoutsTreeUtil.getLayoutsJSON(request, groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, openNodes, true, treeId));
			%>

			children: TreeUtil.formatJSONResults(<%= layoutsJSON %>),
			cssClasses: {
				pages: TREE_CSS_CLASSES
			},
			draggable: false,

			<c:choose>
				<c:when test="<%= saveState %>">

					<%
					boolean rootNodeExpanded = GetterUtil.getBoolean(SessionClicks.get(request, treeId + "RootNode", null), true);
					%>

					expanded: <%= rootNodeExpanded %>,
				</c:when>
				<c:otherwise>
					expanded: <%= expandFirstNode %>,
				</c:otherwise>
			</c:choose>

			id: TreeUtil.createListItemId(<%= groupId %>, TreeUtil.DEFAULT_PARENT_LAYOUT_ID, 0),
			label: rootLabel,
			leaf: false,
			paginator: {
				limit: TreeUtil.PAGINATION_LIMIT,
				offsetParam: 'start',
				start: Math.max(<%= layoutsJSON.getJSONArray("layouts").length() %> - TreeUtil.PAGINATION_LIMIT, 0),
				total: <%= layoutsJSON.getInt("total") %>
			}
		}
	);

	rootNode.get('contentBox').addClass('lfr-root-node');

	var treeview = new TreeViewType(
		{
			after: {
				render: TreeUtil.afterRenderTree
			},
			boundingBox: '#' + treeElId,
			children: [rootNode],
			io: {
				cfg: {
					data: function(node) {
						return {
							cmd: 'get',
							controlPanelCategory: 'current_site.pages',
							doAsGroupId: themeDisplay.getScopeGroupId(),
							groupId: TreeUtil.extractGroupId(node),
							incomplete: <%= incomplete %>,
							p_auth: Liferay.authToken,
							p_l_id: themeDisplay.getPlid(),
							p_p_id: '88',
							parentLayoutId: TreeUtil.extractLayoutId(node),
							privateLayout: <%= privateLayout %>,
							selPlid: '<%= selPlid %>',
							treeId: '<%= HtmlUtil.escape(treeId) %>'
						};
					},
					method: A.config.io.method,
					on: {
						success: function(event, id, xhr) {
							var instance = this;

							var response;

							try {
								response = A.JSON.parse(xhr.responseText);
							}
							catch (e) {
							}

							if (response) {
								instance.get('paginator').total = response.total;

								instance.syncUI();
							}

							<c:if test="<%= saveState %>">
								TreeUtil.updatePagination(instance);

								<c:if test="<%= selectableTree %>">
									TreeUtil.checkNodeState(instance);
								</c:if>
							</c:if>
						}
					}
				},
				formatter: TreeUtil.formatJSONResults,
				url: GET_LAYOUTS_URL
			},

			<c:if test="<%= draggableTree %>">
				lazyLoad: false,
			</c:if>

			on: {
				<c:if test="<%= saveState && selectableTree %>">
					append: function(event) {
						TreeUtil.restoreCheckedNode(event.tree.node);
					},
				</c:if>

				'drop:hit': function(event) {
					var dropNode = event.drop.get('node').get('parentNode');

					var dropTreeNode = dropNode.getData('tree-node');

					if (!dropTreeNode.get('draggable')) {
						event.halt();
					}
				},
				dropAppend: function(event) {
					var tree = event.tree;

					var index = tree.dragNode.get('parentNode').getChildrenLength() - 1;

					TreeUtil.updateLayoutParent(
						TreeUtil.extractPlid(tree.dragNode),
						TreeUtil.extractPlid(tree.dropNode),
						index
					);
				},
				dropInsert: function(event) {
					var tree = event.tree;

					var index = tree.dragNode.get('parentNode').indexOf(tree.dragNode);

					TreeUtil.updateLayoutParent(
						TreeUtil.extractPlid(tree.dragNode),
						TreeUtil.extractPlid(tree.dropNode.get('parentNode')),
						index
					);
				}
			},
			type: 'pages'
		}
	).render();

	<c:if test="<%= !saveState && checkContentDisplayPage %>">
		treeview.on(
			'append',
			function(event) {
				var node = event.tree.node;

				TreeUtil.restoreSelectedNode(node);

				node.eachChildren(TreeUtil.restoreSelectedNode);
			}
		);
	</c:if>

	A.one('#' + treeElId).setData('treeInstance', treeview);

	<c:if test="<%= !selectableTree %>">
		var History = Liferay.HistoryManager;

		var DEFAULT_PLID = '0';

		var HISTORY_SELECTED_PLID = '<portlet:namespace />selPlid';

		var layoutsContainer = A.one('#<portlet:namespace />layoutsContainer');

		treeview.after(
			'lastSelectedChange',
			function(event) {
				var node = event.newVal;

				var plid = TreeUtil.extractPlid(node);

				var currentValue = History.get(HISTORY_SELECTED_PLID);

				if (plid != currentValue) {
					if ((plid == DEFAULT_PLID) && Lang.isValue(currentValue)) {
						plid = null;
					}

					History.add(
						{
							'<portlet:namespace />selPlid': plid
						}
					);
				}
			}
		);

		function compareItemId(item, id) {
			return (TreeUtil.extractPlid(item) == id);
		}

		function findNodeByPlid(node, plid) {
			var foundItem = null;

			if (node) {
				if (compareItemId(node, plid)) {
					foundItem = node;
				}
			}

			if (!foundItem) {
				var children = (node || treeview).get(STR_CHILDREN);

				var length = children.length;

				for (var i = 0; i < length; i++) {
					var item = children[i];

					if (item.isLeaf()) {
						if (compareItemId(item, plid)) {
							foundItem = item;
						}
					}
					else {
						foundItem = findNodeByPlid(item, plid);
					}

					if (foundItem) {
						break;
					}
				}
			}

			return foundItem;
		}

		History.after(
			'stateChange',
			function(event) {
				var nodePlid = event.newVal[HISTORY_SELECTED_PLID];

				if (Lang.isValue(nodePlid)) {
					var node = findNodeByPlid(null, nodePlid);

					if (node) {
						var lastSelected = treeview.get('lastSelected');

						if (lastSelected) {
							lastSelected.unselect();
						}

						node.select();

						var io = layoutsContainer.io;

						var uri = Lang.sub(
							LAYOUT_URL,
							{
								historyKey: '',
								selPlid: nodePlid
							}
						);

						io.set('uri', uri);

						io.start();
					}
				}
			}
		);
	</c:if>
</aui:script>

<div class="lfr-tree-loading" id="<portlet:namespace />treeLoading<%= treeLoading %>">
	<span class="icon icon-loading lfr-tree-loading-icon"></span>
</div>

<div class="lfr-tree" id="<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output"></div>