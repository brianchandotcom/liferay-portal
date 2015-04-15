AUI.add(
	'liferay-layouts-tree-state',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CHECKED_NODES = 'checkedNodes';

		var STR_HOST = 'host';

		var LayoutsTreeState = A.Component.create(
			{
				ATTRS: {
					checkedNodes: {
						validator: Lang.isObject
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

					_onCheckContentDisplayTreeAppend: function(event) {
						var instance = this;

						var node = event.node;

						var host = instance.get(STR_HOST);

						host.restoreSelectedNode(node);

						node.eachChildren(A.bind(host.restoreSelectedNode, host));
					},

					_onNodeChildrenChange: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var target = event.target;

						target.set('alwaysShowHitArea', (event.newVal.length > 0));

						target.eachChildren(A.bind(host.restoreSelectedNode, host));
					},

					_onNodeExpandedChange: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

						var expanded = event.newVal;

						if (event.target === host.getChildren()[0]) {
							Liferay.Store(treeId + 'RootNode', expanded);
						}
						else {
							var layoutId = host.extractLayoutId(event.target);

							instance._updateSessionTreeOpenedState(treeId, layoutId, expanded);
						}
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

						var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

						var root = host.get('root');

						var key = treeId + ':' + root.groupId + ':Pagination';

						instance._invokeSessionClick(
							{
								cmd: 'get',
								key: key
							},
							function(responseData) {
								try {
									paginationMap = A.JSON.parse(responseData);
								}
								catch (e) {
								}

								updatePaginationMap(paginationMap, event.target);

								event.target.eachParent(
									function(parent) {
										updatePaginationMap(paginationMap, parent);
									}
								);

								var sessionClickData = {};

								sessionClickData[key] = A.JSON.stringify(paginationMap);

								instance._invokeSessionClick(sessionClickData);
							}
						);
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

						if (AArray.indexOf(instance.get(STR_CHECKED_NODES), plid) > -1) {
							treeNodeTaskSuperClass.check.call(node, tree);
						}
						else {
							treeNodeTaskSuperClass.uncheck.call(node, tree);
						}

						AArray.each(node.get('children'), A.bind(instance._restoreCheckedNode, instance));
					},

					_updateCheckedNodes: function(node, state) {
						var instance = this;

						var plid = instance.get(STR_HOST).extractPlid(node);

						var checkedNodes = instance.get(STR_CHECKED_NODES);

						var index = AArray.indexOf(checkedNodes, plid);

						if (state) {
							if (index === -1) {
								checkedNodes.push(plid);
							}
						}
						else if (index > -1) {
							AArray.remove(checkedNodes, index);
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