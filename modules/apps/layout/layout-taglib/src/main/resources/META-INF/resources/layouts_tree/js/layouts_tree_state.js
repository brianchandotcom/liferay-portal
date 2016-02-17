AUI.add(
	'liferay-layouts-tree-state',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CHECKED_NODES = 'checkedNodes';

		var STR_HOST = 'host';

		var STR_LOCAL_CHECKED_NODES = 'localCheckedNodes';

		var STR_LOCAL_UNCHECKED_NODES = 'localUncheckedNodes';

		var LayoutsTreeState = A.Component.create(
			{
				ATTRS: {
					checkedNodes: {
						validator: Lang.isObject
					},

					localCheckedNodes: {
						validator: Lang.isArray,
						value: []
					},

					localUncheckedNodes: {
						validator: Lang.isArray,
						value: []
					},

					rootNodeExpanded: {
						validator: Lang.isBoolean,
						value: true
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: 'layoutstreestate',

				NS: 'state',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._eventHandles = [
							instance.afterHostEvent('*:childrenChange', instance._onNodeChildrenChange, instance),
							instance.afterHostEvent('*:expandedChange', instance._onNodeExpandedChange, instance),
							instance.afterHostEvent('*:ioSuccess', instance._onNodeIOSuccess, instance),
							instance.afterHostEvent('checkContentDisplayTreeAppend', instance._onCheckContentDisplayTreeAppend, instance),
							instance.afterHostEvent('selectableNodeCheckedChange', instance._onSelectableNodeCheckedChange, instance),
							instance.afterHostEvent('selectableNodeChildrenChange', instance._onSelectableNodeChildrenChange, instance),
							instance.afterHostEvent('selectableTreeAppend', instance._onSelectableTreeAppend, instance),
							instance.afterHostEvent('selectableTreeRender', instance._onSelectableTreeRender, instance)
						];
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_invokeSessionClick: function(data, callback) {
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
										var instance = this;

										var responseData = instance.get('responseData');

										if (callback && responseData) {
											callback(responseData);
										}
									}
								},
								data: data
							}
						);
					},

					_matchParentNode: function(node) {
						var instance = this;

						var host = instance.get(STR_HOST);
						var localCheckedNodes = instance.get(STR_LOCAL_CHECKED_NODES);
						var localUncheckedNodes = instance.get(STR_LOCAL_UNCHECKED_NODES);

						var plid = host.extractPlid(node);

						var checked;

						if (localCheckedNodes.indexOf(plid) > -1) {
							checked = true;
						}
						else if (localUncheckedNodes.indexOf(plid) > -1) {
							checked = false;
						}

						if (!Lang.isUndefined(checked)) {
							instance._updateCheckedNodes(node, checked);
						}
					},

					_onCheckContentDisplayTreeAppend: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						host.restoreSelectedNode();
					},

					_onNodeChildrenChange: function(event) {
						var instance = this;

						var target = event.target;

						target.set('alwaysShowHitArea', event.newVal.length > 0);
					},

					_onNodeExpandedChange: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

						var expanded = event.newVal;
						var target = event.target;

						if (target === host.getChildren()[0]) {
							Liferay.Store('com.liferay.frontend.js.web_' + treeId + 'RootNode', expanded);
						}
						else {
							var layoutId = host.extractLayoutId(target);

							instance._updateSessionTreeOpenedState(treeId, layoutId, expanded);
						}

						instance._matchParentNode(target);
					},

					_onNodeIOSuccess: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var paginationMap = {};

						var updatePaginationMap = function(map, curNode) {
							if (A.instanceOf(curNode, A.TreeNodeIO)) {
								var paginationLimit = host.get('maxChildren');

								var layoutId = host.extractLayoutId(curNode);

								var children = curNode.get('children');

								map[layoutId] = Math.ceil(children.length / paginationLimit) * paginationLimit;
							}
						};

						var target = event.target;

						instance._matchParentNode(target);

						var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

						var root = host.get('root');

						var key = treeId + ':' + root.groupId + ':' + root.privateLayout + ':Pagination';

						instance._invokeSessionClick(
							{
								cmd: 'get',
								key: key
							},
							function(responseData) {
								try {
									paginationMap = JSON.parse(responseData);
								}
								catch (e) {
								}

								updatePaginationMap(paginationMap, target);

								target.eachParent(
									function(parent) {
										updatePaginationMap(paginationMap, parent);
									}
								);

								var sessionClickData = {};

								sessionClickData[key] = JSON.stringify(paginationMap);

								instance._invokeSessionClick(sessionClickData);
							}
						);

						host.restoreSelectedNode();
					},

					_onSelectableNodeCheckedChange: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

						var newVal = event.checked;
						var target = event.node;

						var plid = host.extractPlid(target);

						instance._updateSessionTreeCheckedState(treeId + 'SelectedNode', plid, newVal);

						instance._updateCheckedNodes(target, newVal);
					},

					_onSelectableNodeChildrenChange: function(event) {
						var instance = this;

						var node = event.node;

						if (node.get('checked')) {
							instance._updateCheckedNodes(node, true);
						}

						instance._restoreCheckedNode(node);
					},

					_onSelectableTreeAppend: function(event) {
						var instance = this;

						instance._restoreCheckedNode(event.node);
					},

					_onSelectableTreeRender: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var rootNode = host.getChildren()[0];

						rootNode.set('checked', undefined);
						rootNode.set('expanded', instance.get('rootNodeExpanded'));

						instance._restoreCheckedNode(rootNode);
					},

					_restoreCheckedNode: function(node) {
						var instance = this;

						var plid = instance.get(STR_HOST).extractPlid(node);

						var tree = node.get('ownerTree');

						var treeNodeTaskSuperClass = A.TreeNodeTask.superclass;

						if (instance.get(STR_CHECKED_NODES).indexOf(plid) > -1) {
							treeNodeTaskSuperClass.check.call(node, tree);
						}
						else {
							treeNodeTaskSuperClass.uncheck.call(node, tree);
						}

						node.get('children').forEach(A.bind(instance._restoreCheckedNode, instance));
					},

					_updateCheckedNodes: function(node, state) {
						var instance = this;

						var plid = instance.get(STR_HOST).extractPlid(node);

						var checkedNodes = instance.get(STR_CHECKED_NODES);
						var localCheckedNodes = instance.get(STR_LOCAL_CHECKED_NODES);
						var localUncheckedNodes = instance.get(STR_LOCAL_UNCHECKED_NODES);

						var checkedIndex = checkedNodes.indexOf(plid);
						var localCheckedIndex = localCheckedNodes.indexOf(plid);
						var localUncheckedIndex = localUncheckedNodes.indexOf(plid);

						if (state) {
							if (checkedIndex === -1) {
								checkedNodes.push(plid);
							}

							if (localCheckedIndex == -1) {
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

						var children = node.get('children');

						if (children.length) {
							A.each(
								children,
								function(child) {
									instance._updateCheckedNodes(child, state);
								}
							);
						}
					},

					_updateSessionTreeCheckedState: function(treeId, nodeId, state) {
						var instance = this;

						var data = {
							cmd: state ? 'layoutCheck' : 'layoutUncheck',
							plid: nodeId
						};

						instance._updateSessionTreeClick(treeId, data);
					},

					_updateSessionTreeClick: function(treeId, data) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var root = host.get('root');

						data = A.merge(
							{
								groupId: root.groupId,
								privateLayout: root.privateLayout,
								recursive: true,
								treeId: treeId
							},
							data
						);

						A.io.request(
							themeDisplay.getPathMain() + '/portal/session_tree_js_click',
							{
								data: data,
								dataType: 'json',
								on: {
									success: function() {
										var checkedNodes = this.get('responseData');

										if (checkedNodes) {
											instance.set(STR_CHECKED_NODES, checkedNodes);
										}
									}
								}
							}
						);
					},

					_updateSessionTreeOpenedState: function(treeId, nodeId, state) {
						var instance = this;

						var data = {
							nodeId: nodeId,
							openNode: state
						};

						instance._updateSessionTreeClick(treeId, data);
					}
				}
			}
		);

		A.Plugin.LayoutsTreeState = LayoutsTreeState;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-request', 'liferay-store']
	}
);