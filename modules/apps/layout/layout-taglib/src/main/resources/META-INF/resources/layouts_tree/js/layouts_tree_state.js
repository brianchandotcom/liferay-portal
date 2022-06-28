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
	(A) => {
		const AArray = A.Array;

		const Lang = A.Lang;

		const STR_BOUNDING_BOX = 'boundingBox';

		const STR_CHECKED_NODES = 'checkedNodes';

		const STR_HOST = 'host';

		const STR_LOCAL_CHECKED_NODES = 'localCheckedNodes';

		const STR_LOCAL_UNCHECKED_NODES = 'localUncheckedNodes';

		const LayoutsTreeState = A.Component.create({
			ATTRS: {
				checkedNodes: {
					validator: Lang.isObject,
				},

				localCheckedNodes: {
					validator: Lang.isArray,
					value: [],
				},

				localUncheckedNodes: {
					validator: Lang.isArray,
					value: [],
				},

				rootNodeExpanded: {
					validator: Lang.isBoolean,
					value: true,
				},
			},

			EXTENDS: A.Plugin.Base,

			NAME: 'layoutstreestate',

			NS: 'state',

			prototype: {
				_invokeSessionClick(data, callback) {
					A.mix(data, {
						p_auth: Liferay.authToken,
						useHttpSession: true,
					});

					Liferay.Util.fetch(
						themeDisplay.getPathMain() + '/portal/session_click',
						{
							body: Liferay.Util.objectToFormData(data),
							method: 'POST',
						}
					)
						.then((response) => response.text())
						.then((text) => {
							if (callback && text) {
								callback(text);
							}
						})
						.catch(() => {});
				},

				_matchParentNode(node) {
					const instance = this;

					const host = instance.get(STR_HOST);
					const localCheckedNodes = instance.get(
						STR_LOCAL_CHECKED_NODES
					);
					const localUncheckedNodes = instance.get(
						STR_LOCAL_UNCHECKED_NODES
					);

					const plid = host.extractPlid(node);

					let checked;

					if (localCheckedNodes.indexOf(plid) > -1) {
						checked = true;
					}
					else if (localUncheckedNodes.indexOf(plid) > -1) {
						checked = false;
					}

					if (!Lang.isUndefined(checked)) {
						instance._updateCheckedNodes({
							checked,
							forceChildrenState: true,
							node,
						});
					}
				},

				_onCheckContentDisplayTreeAppend() {
					const instance = this;

					const host = instance.get(STR_HOST);

					host.restoreSelectedNode();
				},

				_onNodeChildrenChange(event) {
					const target = event.target;

					target.set('alwaysShowHitArea', !!event.newVal.length);
				},

				_onNodeExpandedChange(event) {
					const instance = this;

					const host = instance.get(STR_HOST);

					const treeId = host
						.get(STR_BOUNDING_BOX)
						.attr('data-treeid');

					const expanded = event.newVal;
					const target = event.target;

					if (target === host.getChildren()[0]) {
						Liferay.Util.Session.set(
							'com.liferay.frontend.js.web_' +
								treeId +
								'RootNode',
							expanded
						);
					}
					else {
						const layoutId = host.extractLayoutId(target);

						instance._updateSessionTreeOpenedState(
							treeId,
							layoutId,
							expanded
						);
					}
				},

				_onNodeIOSuccess(event) {
					const instance = this;

					const host = instance.get(STR_HOST);

					let paginationMap = {};

					const updatePaginationMap = function (map, curNode) {
						if (A.instanceOf(curNode, A.TreeNodeIO)) {
							const paginationLimit = host.get('maxChildren');

							const layoutId = host.extractLayoutId(curNode);

							const children = curNode.get('children');

							map[layoutId] =
								Math.ceil(children.length / paginationLimit) *
								paginationLimit;
						}
					};

					const target = event.target;

					instance._matchParentNode(target);

					const treeId = host
						.get(STR_BOUNDING_BOX)
						.attr('data-treeid');

					const root = host.get('root');

					const key =
						treeId +
						':' +
						root.groupId +
						':' +
						root.privateLayout +
						':Pagination';

					instance._invokeSessionClick(
						{
							cmd: 'get',
							key,
						},
						(responseData) => {
							try {
								paginationMap = JSON.parse(responseData);
							}
							catch (error) {}

							updatePaginationMap(paginationMap, target);

							target.eachParent((parent) => {
								updatePaginationMap(paginationMap, parent);
							});

							const sessionClickData = {};

							sessionClickData[key] = JSON.stringify(
								paginationMap
							);

							instance._invokeSessionClick(sessionClickData);
						}
					);

					host.restoreSelectedNode();
				},

				_onSelectableNodeCheckedChange(event) {
					const instance = this;

					const host = instance.get(STR_HOST);

					const treeId = host
						.get(STR_BOUNDING_BOX)
						.attr('data-treeid');

					const newVal = event.checked;
					const target = event.node;

					const plid = host.extractPlid(target);

					instance._updateSessionTreeCheckedState(
						treeId + 'SelectedNode',
						plid,
						newVal
					);

					instance._updateCheckedNodes({
						checked: newVal,
						node: target,
					});
				},

				_onSelectableNodeChildrenChange(event) {
					const instance = this;

					const node = event.node;

					if (node.get('checked')) {
						instance._updateCheckedNodes({
							checked: true,
							forceChildrenState: true,
							node,
						});
					}

					instance._restoreCheckedNode(node);
				},

				_onSelectableTreeAppend(event) {
					const instance = this;

					instance._restoreCheckedNode(event.node);
				},

				_onSelectableTreeRender() {
					const instance = this;

					const host = instance.get(STR_HOST);

					const rootNode = host.getChildren()[0];

					rootNode.set('checked', undefined);
					rootNode.set('expanded', instance.get('rootNodeExpanded'));

					instance._restoreCheckedNode(rootNode);
				},

				_restoreCheckedNode(node) {
					const instance = this;

					const plid = instance.get(STR_HOST).extractPlid(node);

					const tree = node.get('ownerTree');

					const treeNodeTaskSuperClass = A.TreeNodeTask.superclass;

					if (instance.get(STR_CHECKED_NODES).indexOf(plid) > -1) {
						treeNodeTaskSuperClass.check.call(node, tree);
					}
					else {
						treeNodeTaskSuperClass.uncheck.call(node, tree);
					}

					node.get('children').forEach(
						A.bind(instance._restoreCheckedNode, instance)
					);
				},

				_updateCheckedNodes(nodeConfig) {
					const instance = this;

					let checked = nodeConfig.checked;
					const forceChildrenState = nodeConfig.forceChildrenState;
					const node = nodeConfig.node;

					const plid = instance.get(STR_HOST).extractPlid(node);

					const checkedNodes = instance.get(STR_CHECKED_NODES);
					const localCheckedNodes = instance.get(
						STR_LOCAL_CHECKED_NODES
					);
					const localUncheckedNodes = instance.get(
						STR_LOCAL_UNCHECKED_NODES
					);

					const checkedIndex = checkedNodes.indexOf(plid);
					const localCheckedIndex = localCheckedNodes.indexOf(plid);
					const localUncheckedIndex = localUncheckedNodes.indexOf(
						plid
					);

					if (checked === undefined) {
						checked = checkedIndex > -1;
					}

					if (checked) {
						if (checkedIndex === -1) {
							checkedNodes.push(plid);
						}

						if (localCheckedIndex === -1) {
							localCheckedNodes.push(plid);
						}

						if (localUncheckedIndex > -1) {
							AArray.remove(
								localUncheckedNodes,
								localUncheckedIndex
							);
						}
					}
					else if (checkedIndex > -1) {
						AArray.remove(checkedNodes, checkedIndex);

						localUncheckedNodes.push(plid);

						if (localCheckedIndex > -1) {
							AArray.remove(localCheckedNodes, localCheckedIndex);
						}
					}

					node.set('checked', checked);

					const children = node.get('children');

					if (children.length) {
						const childrenChecked = forceChildrenState
							? undefined
							: checked;

						A.each(children, (child) => {
							instance._updateCheckedNodes({
								checked: childrenChecked,
								forceChildrenState,
								node: child,
							});
						});
					}
				},

				_updateSessionTreeCheckedState(treeId, nodeId, state) {
					const instance = this;

					const data = {
						cmd: state ? 'layoutCheck' : 'layoutUncheck',
						plid: nodeId,
					};

					instance._updateSessionTreeClick(treeId, data);
				},

				_updateSessionTreeClick(treeId, data) {
					const instance = this;

					const host = instance.get(STR_HOST);

					const root = host.get('root');

					data = {
						groupId: root.groupId,
						privateLayout: root.privateLayout,
						recursive: true,
						treeId,
						...data,
					};

					Liferay.Util.fetch(
						themeDisplay.getPathMain() +
							'/portal/session_tree_js_click',
						{
							body: Liferay.Util.objectToFormData(data),
							method: 'POST',
						}
					)
						.then((response) => response.json())
						.then((checkedNodes) => {
							if (checkedNodes) {
								instance.set(STR_CHECKED_NODES, checkedNodes);
							}
						})
						.catch(() => {});
				},

				_updateSessionTreeOpenedState(treeId, nodeId, state) {
					const instance = this;

					const data = {
						nodeId,
						openNode: state,
					};

					instance._updateSessionTreeClick(treeId, data);
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

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
						),
					];
				},
			},
		});

		A.Plugin.LayoutsTreeState = LayoutsTreeState;
	},
	'',
	{
		requires: ['aui-base'],
	}
);
