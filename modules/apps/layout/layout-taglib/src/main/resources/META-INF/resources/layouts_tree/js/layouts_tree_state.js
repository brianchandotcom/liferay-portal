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

AUI.add(
	'liferay-layouts-tree-state',
	A => {
		var AArray = A.Array;

		var Lang = A.Lang;

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CHECKED_NODES = 'checkedNodes';

		var STR_HOST = 'host';

		var STR_LOCAL_CHECKED_NODES = 'localCheckedNodes';

		var STR_LOCAL_UNCHECKED_NODES = 'localUncheckedNodes';

		var LayoutsTreeState = A.Component.create({
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
				_invokeSessionClick(data, callback) {
					A.mix(data, {
						p_auth: Liferay.authToken,
						useHttpSession: true
					});

					Liferay.Util.fetch(
						themeDisplay.getPathMain() + '/portal/session_click',
						{
							body: Liferay.Util.objectToFormData(data),
							method: 'POST'
						}
					)
						.then(response => response.text())
						.then(text => {
							if (callback && text) {
								callback(text);
							}
						})
						.catch(() => {});
				},

				_matchParentNode(node) {
					var instance = this;

					var host = instance.get(STR_HOST);
					var localCheckedNodes = instance.get(
						STR_LOCAL_CHECKED_NODES
					);
					var localUncheckedNodes = instance.get(
						STR_LOCAL_UNCHECKED_NODES
					);

					var plid = host.extractPlid(node);

					var checked;

					if (localCheckedNodes.indexOf(plid) > -1) {
						checked = true;
					} else if (localUncheckedNodes.indexOf(plid) > -1) {
						checked = false;
					}

					if (!Lang.isUndefined(checked)) {
						instance._updateCheckedNodes({
							checked,
							forceChildrenState: true,
							node
						});
					}
				},

				_onCheckContentDisplayTreeAppend() {
					var instance = this;

					var host = instance.get(STR_HOST);

					host.restoreSelectedNode();
				},

				_onNodeChildrenChange(event) {
					var target = event.target;

					target.set('alwaysShowHitArea', event.newVal.length > 0);
				},

				_onNodeExpandedChange(event) {
					var instance = this;

					var host = instance.get(STR_HOST);

					var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

					var expanded = event.newVal;
					var target = event.target;

					if (target === host.getChildren()[0]) {
						Liferay.Util.Session.set(
							'com.liferay.frontend.js.web_' +
								treeId +
								'RootNode',
							expanded
						);
					} else {
						var layoutId = host.extractLayoutId(target);

						instance._updateSessionTreeOpenedState(
							treeId,
							layoutId,
							expanded
						);
					}
				},

				_onNodeIOSuccess(event) {
					var instance = this;

					var host = instance.get(STR_HOST);

					var paginationMap = {};

					var updatePaginationMap = function(map, curNode) {
						if (A.instanceOf(curNode, A.TreeNodeIO)) {
							var paginationLimit = host.get('maxChildren');

							var layoutId = host.extractLayoutId(curNode);

							var children = curNode.get('children');

							map[layoutId] =
								Math.ceil(children.length / paginationLimit) *
								paginationLimit;
						}
					};

					var target = event.target;

					instance._matchParentNode(target);

					var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

					var root = host.get('root');

					var key =
						treeId +
						':' +
						root.groupId +
						':' +
						root.privateLayout +
						':Pagination';

					instance._invokeSessionClick(
						{
							cmd: 'get',
							key
						},
						responseData => {
							try {
								paginationMap = JSON.parse(responseData);
							} catch (e) {}

							updatePaginationMap(paginationMap, target);

							target.eachParent(parent => {
								updatePaginationMap(paginationMap, parent);
							});

							var sessionClickData = {};

							sessionClickData[key] = JSON.stringify(
								paginationMap
							);

							instance._invokeSessionClick(sessionClickData);
						}
					);

					host.restoreSelectedNode();
				},

				_onSelectableNodeCheckedChange(event) {
					var instance = this;

					var host = instance.get(STR_HOST);

					var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

					var newVal = event.checked;
					var target = event.node;

					var plid = host.extractPlid(target);

					instance._updateSessionTreeCheckedState(
						treeId + 'SelectedNode',
						plid,
						newVal
					);

					instance._updateCheckedNodes({
						checked: newVal,
						node: target
					});
				},

				_onSelectableNodeChildrenChange(event) {
					var instance = this;

					var node = event.node;

					if (node.get('checked')) {
						instance._updateCheckedNodes({
							checked: true,
							forceChildrenState: true,
							node
						});
					}

					instance._restoreCheckedNode(node);
				},

				_onSelectableTreeAppend(event) {
					var instance = this;

					instance._restoreCheckedNode(event.node);
				},

				_onSelectableTreeRender() {
					var instance = this;

					var host = instance.get(STR_HOST);

					var rootNode = host.getChildren()[0];

					rootNode.set('checked', undefined);
					rootNode.set('expanded', instance.get('rootNodeExpanded'));

					instance._restoreCheckedNode(rootNode);
				},

				_restoreCheckedNode(node) {
					var instance = this;

					var plid = instance.get(STR_HOST).extractPlid(node);

					var tree = node.get('ownerTree');

					var treeNodeTaskSuperClass = A.TreeNodeTask.superclass;

					if (instance.get(STR_CHECKED_NODES).indexOf(plid) > -1) {
						treeNodeTaskSuperClass.check.call(node, tree);
					} else {
						treeNodeTaskSuperClass.uncheck.call(node, tree);
					}

					node.get('children').forEach(
						A.bind(instance._restoreCheckedNode, instance)
					);
				},

				_updateCheckedNodes(nodeConfig) {
					var instance = this;

					var checked = nodeConfig.checked;
					var forceChildrenState = nodeConfig.forceChildrenState;
					var node = nodeConfig.node;

					var plid = instance.get(STR_HOST).extractPlid(node);

					var checkedNodes = instance.get(STR_CHECKED_NODES);
					var localCheckedNodes = instance.get(
						STR_LOCAL_CHECKED_NODES
					);
					var localUncheckedNodes = instance.get(
						STR_LOCAL_UNCHECKED_NODES
					);

					var checkedIndex = checkedNodes.indexOf(plid);
					var localCheckedIndex = localCheckedNodes.indexOf(plid);
					var localUncheckedIndex = localUncheckedNodes.indexOf(plid);

					if (checked === undefined) {
						checked = checkedIndex > -1;
					}

					if (checked) {
						if (checkedIndex === -1) {
							checkedNodes.push(plid);
						}

						if (localCheckedIndex == -1) {
							localCheckedNodes.push(plid);
						}

						if (localUncheckedIndex > -1) {
							AArray.remove(
								localUncheckedNodes,
								localUncheckedIndex
							);
						}
					} else if (checkedIndex > -1) {
						AArray.remove(checkedNodes, checkedIndex);

						localUncheckedNodes.push(plid);

						if (localCheckedIndex > -1) {
							AArray.remove(localCheckedNodes, localCheckedIndex);
						}
					}

					node.set('checked', checked);

					var children = node.get('children');

					if (children.length) {
						var childrenChecked = forceChildrenState
							? undefined
							: checked;

						A.each(children, child => {
							instance._updateCheckedNodes({
								checked: childrenChecked,
								forceChildrenState,
								node: child
							});
						});
					}
				},

				_updateSessionTreeCheckedState(treeId, nodeId, state) {
					var instance = this;

					var data = {
						cmd: state ? 'layoutCheck' : 'layoutUncheck',
						plid: nodeId
					};

					instance._updateSessionTreeClick(treeId, data);
				},

				_updateSessionTreeClick(treeId, data) {
					var instance = this;

					var host = instance.get(STR_HOST);

					var root = host.get('root');

					data = A.merge(
						{
							groupId: root.groupId,
							privateLayout: root.privateLayout,
							recursive: true,
							treeId
						},
						data
					);

					Liferay.Util.fetch(
						themeDisplay.getPathMain() +
							'/portal/session_tree_js_click',
						{
							body: Liferay.Util.objectToFormData(data),
							method: 'POST'
						}
					)
						.then(response => response.json())
						.then(checkedNodes => {
							if (checkedNodes) {
								instance.set(STR_CHECKED_NODES, checkedNodes);
							}
						})
						.catch(() => {});
				},

				_updateSessionTreeOpenedState(treeId, nodeId, state) {
					var instance = this;

					var data = {
						nodeId,
						openNode: state
					};

					instance._updateSessionTreeClick(treeId, data);
				},

				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					instance._eventHandles = [
						instance.afterHostEvent(
							'*:childrenChange',
							instance._onNodeChildrenChange,
							instance
						),
						instance.afterHostEvent(
							'*:expandedChange',
							instance._onNodeExpandedChange,
							instance
						),
						instance.afterHostEvent(
							'*:ioSuccess',
							instance._onNodeIOSuccess,
							instance
						),
						instance.afterHostEvent(
							'checkContentDisplayTreeAppend',
							instance._onCheckContentDisplayTreeAppend,
							instance
						),
						instance.afterHostEvent(
							'selectableNodeCheckedChange',
							instance._onSelectableNodeCheckedChange,
							instance
						),
						instance.afterHostEvent(
							'selectableNodeChildrenChange',
							instance._onSelectableNodeChildrenChange,
							instance
						),
						instance.afterHostEvent(
							'selectableTreeAppend',
							instance._onSelectableTreeAppend,
							instance
						),
						instance.afterHostEvent(
							'selectableTreeRender',
							instance._onSelectableTreeRender,
							instance
						)
					];
				}
			}
		});

		A.Plugin.LayoutsTreeState = LayoutsTreeState;
	},
	'',
	{
		requires: ['aui-base']
	}
);
