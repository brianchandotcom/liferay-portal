/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {useTimeout} from '@liferay/frontend-js-react-web';
import React, {useEffect, useReducer, useRef, useState} from 'react';

import NodeList from './NodeList';
import TreeviewCard from './TreeviewCard';
import TreeviewContext, {
	LinkedNode,
	Node,
	NodeFilter,
	NodeMap,
	TreeviewAction,
	TreeviewState,
} from './TreeviewContext';
import TreeviewLabel from './TreeviewLabel';

import './Treeview.scss';

/**
 * Adds parent, sibling and child links to make tree traversal in any
 * direction easy.
 */
function addLinks(nodes: Node[], parentId: string | null = null): LinkedNode[] {
	return nodes.map((node, i) => {
		const [previous, next] = [nodes[i - 1], nodes[i + 1]];

		const children = node.children ? addLinks(node.children, node.id) : [];

		return {
			...node,
			children,
			nextSiblingId: next !== null && next !== undefined ? next.id : null,
			parentId,
			previousSiblingId:
				previous !== null && previous !== undefined
					? previous.id
					: null,
		};
	});
}

/**
 * Updates the selection status of the node based on its children.
 * Having all the children selected will mark the item as selected.
 */
function computeParentSelection(
	nodeId: string | null,
	selectedNodeIds: Set<string>,
	nodes: NodeMap
): Set<string> {
	if (!nodeId) {
		return selectedNodeIds;
	}

	const node = nodes[nodeId];

	if (!node) {
		return selectedNodeIds;
	}

	const allChildrenSelected = node.children.every((children) =>
		selectedNodeIds.has(children.id)
	);

	let nextSelectedNodeIds;

	if (allChildrenSelected) {
		nextSelectedNodeIds = selectedNodeIds.has(nodeId)
			? selectedNodeIds
			: new Set([...selectedNodeIds, nodeId]);
	}
	else {
		nextSelectedNodeIds = selectedNodeIds.has(nodeId)
			? new Set([...selectedNodeIds].filter((id) => id !== nodeId))
			: selectedNodeIds;
	}

	return computeParentSelection(node.parentId, nextSelectedNodeIds, nodes);
}

function getFilterFn(filter?: NodeFilter | string) {
	if (!filter) {
		return;
	}

	if (typeof filter === 'function') {
		return filter;
	}

	const filterLowerCase = filter.toString().toLowerCase();

	return (node: LinkedNode) =>
		node.name.toLowerCase().includes(filterLowerCase);
}

function filterNodes(nodes: LinkedNode[], filter?: NodeFilter) {
	if (!filter) {
		return null;
	}

	const filteredNodes: LinkedNode[] = [];

	nodes.forEach((node) => {
		if (filter(node)) {
			filteredNodes.push({
				...node,
				children: [],
			});
		}

		const filtered = filterNodes(node.children, filter);

		if (filtered) {
			filteredNodes.push(...filtered);
		}
	});

	return filteredNodes;
}

/**
 * Recursively get all the children of a parent.
 */
function getChildrenIds(node: LinkedNode, childrenIds: string[] = []) {
	node.children.forEach((children) => {
		childrenIds.push(children.id);

		getChildrenIds(children, childrenIds);
	});

	return childrenIds;
}

/**
 * Finds the deepest visible node in the subtree rooted at `node`.
 */
function getLastVisible(node: LinkedNode): LinkedNode {
	const childCount = node.children.length;

	if (!node.expanded || !childCount) {
		return node;
	}
	else {
		return getLastVisible(node.children[childCount - 1]);
	}
}

/**
 * Prepares the initial reducer state given the supplied props.
 */
function init({
	filter,
	inheritSelection,
	initialNodes,
	initialSelectedNodeIds,
	multiSelection,
}: {
	filter?: NodeFilter | string;
	inheritSelection: boolean;
	initialNodes: Node[];
	initialSelectedNodeIds: string[];
	multiSelection?: boolean;
}) {
	const selectedNodeIds = new Set(initialSelectedNodeIds);

	const nodeMap = {};

	const nodes = addLinks(initialNodes).map((node) => {
		return visit(
			node,
			(node) => {
				const expanded =
					node.expanded ||
					node.children.some((child) => {
						return child.expanded || child.selected;
					});

				const selected = selectedNodeIds.has(node.id);

				return {
					...node,
					expanded,
					selected,
				};
			},
			nodeMap
		);
	});

	const filterFn = getFilterFn(filter);

	return {
		active: false,
		filter: filterFn,
		filteredNodes: filterNodes(nodes, filterFn),
		focusedNodeId: null,
		inheritSelection,
		multiSelection,
		nodeMap,
		nodes,
		selectedNodeIds,
	};
}

/**
 * Given a `state` object, updates the node with `id` by passing it to `callback`.
 *
 * Returns an updated (non-destructive) copy of the `state.nodes`.
 */
function updateNode(
	state: TreeviewState,
	id: string,
	callback: (node: LinkedNode) => LinkedNode
) {
	const {nodeMap} = state;

	if (!nodeMap[id]) {
		throw new Error(`Could not get node with id ${id}`);
	}

	let node = callback(nodeMap[id]);

	if (node === nodeMap[id]) {

		// Node didn't change, so leave state as-is.

		return state.nodes;
	}

	nodeMap[id] = node;

	// Walk back to root updating subtrees.

	while (node.parentId) {
		const parent = nodeMap[node.parentId];

		node = {
			...parent,
			children: parent.children.map((child) => {
				return child.id === node.id ? node : child;
			}),
		};

		nodeMap[node.id] = node;
	}

	return state.nodes.map((child) => {
		return child.id === node.id ? node : child;
	});
}

/**
 * Reducer function for use with `useReducer`.
 */
function reducer(state: TreeviewState, action: TreeviewAction) {
	const {filteredNodes, nodeMap} = state;

	const nodes = filteredNodes || state.nodes;

	switch (action.type) {
		case 'ACTIVATE': {
			if (state.active) {
				return state;
			}

			const focusedNodeId = action.mouseNavigation
				? state.focusedNodeId
				: state.focusedNodeId || (nodes[0] && nodes[0].id);

			return {
				...state,
				active: true,
				focusedNodeId,
			};
		}

		case 'DEACTIVATE':
		case 'EXIT': {
			if (!state.active) {
				return state;
			}

			return {
				...state,
				active: false,
				focusedNodeId: null,
			};
		}

		case 'COLLAPSE':

			// eg double click

			if (!filteredNodes) {
				return {
					...state,
					nodes: updateNode(state, action.nodeId, (node) => {
						return node.expanded
							? {
									...node,
									expanded: false,
							  }
							: node;
					}),
				};
			}
			break;

		case 'SELECT_ROOT':
			{
				const rootId = nodes[0] && nodes[0].id;

				if (state.focusedNodeId !== rootId) {
					return {
						...state,
						focusedNodeId: rootId,
					};
				}
			}
			break;

		case 'SELECT_NEXT_VISIBLE':
			{
				let node: LinkedNode | null = nodeMap[action.nodeId];

				if (filteredNodes) {
					for (let i = 0; i < filteredNodes.length - 1; i++) {
						if (filteredNodes[i].id === state.focusedNodeId) {
							node = nodes[i + 1];
							break;
						}
					}
				}
				else {
					while (node) {
						if (node.id !== action.nodeId) {

							// Not the first iteration and we found a match: done.

							break;
						}

						if (node.expanded && node.children.length) {

							// Expanded, so go to first visible child.

							node = node.children[0];
							break;
						}

						// No visible children, so go to first visible sibling.

						if (node.nextSiblingId) {
							node = nodeMap[node.nextSiblingId];
							continue;
						}

						// As last resort, go to parent's sibling.

						if (node.parentId) {
							const nextId: string | null =
								nodeMap[node.parentId].nextSiblingId;

							if (nextId) {
								node = nodeMap[nextId];
								continue;
							}
						}

						// Give up.

						node = null;
						break;
					}
				}

				if (node) {
					return {
						...state,
						focusedNodeId: node.id,
					};
				}
			}
			break;

		case 'SELECT_PREVIOUS_VISIBLE':
			{
				let node: LinkedNode | null = nodeMap[action.nodeId];

				if (filteredNodes) {
					for (let i = 1; i < filteredNodes.length; i++) {
						if (filteredNodes[i].id === state.focusedNodeId) {
							node = nodes[i - 1];
							break;
						}
					}
				}
				else {
					while (node) {
						if (node.id !== action.nodeId) {
							break;
						}

						if (node.previousSiblingId) {
							node = getLastVisible(
								nodeMap[node.previousSiblingId]
							);

							break;
						}
						else {

							// Go to parent.

							node = node.parentId
								? nodeMap[node.parentId]
								: null;
							break;
						}
					}
				}

				if (node) {
					return {
						...state,
						focusedNodeId: node.id,
					};
				}
			}
			break;

		case 'SELECT_LAST_VISIBLE':
			{
				const lastIndex = nodes.length - 1;

				let lastId;

				if (filteredNodes) {
					lastId = nodes[lastIndex] && nodes[lastIndex].id;
				}
				else {
					let node = nodes[lastIndex];

					while (node) {
						if (node.children.length && node.expanded) {
							node = node.children[node.children.length - 1];
						}
						else {
							break;
						}
					}

					lastId = node && node.id;
				}

				if (lastId && state.focusedNodeId !== lastId) {
					return {
						...state,
						focusedNodeId: lastId,
					};
				}
			}
			break;

		case 'TOGGLE_EXPANDED':

			// Toggles the expanded or collapsed state of the selected
			// parent node. eg. by double clicking; doesn't select a child.

			if (!filteredNodes) {
				return {
					...state,
					nodes: updateNode(state, action.nodeId, (node) => {
						return {
							...node,
							expanded: !node.expanded,
						};
					}),
				};
			}
			break;

		case 'EXPAND_ALL':
			{
				if (!filteredNodes) {
					const nodes = state.nodes.map((node) =>
						visit(
							node,
							(node) =>
								!node.expanded
									? {
											...node,
											expanded: true,
									  }
									: node,
							nodeMap
						)
					);

					return {
						...state,
						nodes,
					};
				}
			}
			break;

		case 'FILTER':
			return {
				...state,
				filter: action.filter,
				filteredNodes: filterNodes(state.nodes, action.filter),
				focusedNodeId: null,
			};

		case 'FOCUS':
			if (action.nodeId !== state.focusedNodeId) {
				return {
					...state,
					focusedNodeId: action.nodeId,
				};
			}
			break;

		case 'COLLAPSE_PARENT':
			{

				// Collapse the currently selected parent node if it is
				// expanded; otherwise move to the previous parent node
				// (if possible).

				if (!filteredNodes) {
					const node = nodeMap[action.nodeId];

					if (node.expanded) {
						return {
							...state,
							nodes: updateNode(state, action.nodeId, (node) => {
								return {
									...node,
									expanded: false,
								};
							}),
						};
					}
					else if (node.parentId) {
						return {
							...state,
							focusedNodeId: node.parentId,
						};
					}
				}
			}
			break;

		case 'EXPAND_AND_ENTER':
			{

				// Expand the currently selected parent node if it is closed;
				// move to the first child list item if it was already expanded.

				if (!filteredNodes) {
					const node = nodeMap[action.nodeId];

					if (!node.expanded) {
						return {
							...state,
							nodes: updateNode(state, action.nodeId, (node) => {
								return {
									...node,
									expanded: true,
								};
							}),
						};
					}
					else if (node.children.length) {
						return {
							...state,
							focusedNodeId: node.children[0].id,
						};
					}
				}
			}
			break;

		case 'TOGGLE_SELECT': {
			const id = action.nodeId;

			if (!nodeMap[id].disabled) {
				const {inheritSelection, multiSelection} = state;

				let {selectedNodeIds} = state;

				if (inheritSelection) {
					const selectedNode = nodeMap[id];

					const parentAndChildrenIds = [
						id,
						...getChildrenIds(selectedNode),
					];

					let nextSelectedNodeIds;

					if (selectedNodeIds.has(id)) {
						nextSelectedNodeIds = new Set(
							[...selectedNodeIds].filter(
								(selectedId) =>
									!parentAndChildrenIds.includes(selectedId)
							)
						);
					}
					else {
						nextSelectedNodeIds = new Set([
							...selectedNodeIds,
							...parentAndChildrenIds,
						]);
					}

					selectedNodeIds = computeParentSelection(
						selectedNode.parentId,
						nextSelectedNodeIds,
						nodeMap
					);
				}
				else {
					if (selectedNodeIds.has(id)) {
						selectedNodeIds = new Set(
							[...selectedNodeIds].filter(
								(selectedId) => selectedId !== id
							)
						);
					}
					else if (multiSelection) {
						selectedNodeIds = new Set([...selectedNodeIds, id]);
					}
					else {
						selectedNodeIds = new Set([id]);
					}
				}

				return {
					...state,
					filteredNodes:
						filteredNodes &&
						filteredNodes.map((node) =>
							toggleNode(node, selectedNodeIds)
						),
					focusedNodeId: id,
					nodes: state.nodes.map((node) =>
						visit(
							node,
							(node) => toggleNode(node, selectedNodeIds),
							nodeMap
						)
					),
					selectedNodeIds,
				};
			}
			break;
		}

		case 'UPDATE_NODES': {
			const nodes = addLinks(action.newNodes).map((node) => {
				return visit(
					node,
					(node) => {
						const {selectedNodeIds} = state;
						const oldNode = nodeMap[node.id] || {};

						const expanded =
							oldNode.expanded ||
							node.expanded ||
							node.children.some((child) => {
								return child.expanded || child.selected;
							});

						const selected =
							oldNode.selected || selectedNodeIds.has(node.id);

						return {
							...node,
							expanded,
							selected,
						};
					},
					nodeMap
				);
			});

			return {
				...state,
				nodes,
			};
		}

		default:
			break;
	}

	return state;
}

/**
 * Toggles the `selected` property of `node` based on its membership
 * within `selectedNodeIds`.
 *
 * Returns the original node if it was already in the desired state;
 * otherwise returns a copy.
 */
function toggleNode(node: LinkedNode, selectedNodeIds: Set<String>) {
	if (node.selected !== selectedNodeIds.has(node.id)) {
		return {
			...node,
			selected: !node.selected,
		};
	}
	else {
		return node;
	}
}

/**
 * Visits the tree represented by `node`, passing each visited node to
 * `callback`, which should return an updated node and update the `nodeMap`
 * cache.
 *
 * If `callback` returns the same node, no actual copies are made.
 */
function visit(
	node: LinkedNode,
	callback: (node: LinkedNode) => LinkedNode,
	nodeMap: NodeMap
) {
	const {children} = node;

	let nextChildren;

	for (let i = 0; i < children.length; i++) {
		const child = children[i];
		const updated = visit(child, callback, nodeMap);

		if (nextChildren) {
			nextChildren.push(updated);
		}
		else if (updated !== child) {
			nextChildren = children.slice(0, i).concat([updated]);
		}
	}

	const nextNode = callback(
		nextChildren ? {...node, children: nextChildren} : node
	);

	if (nextNode !== node) {
		nodeMap[nextNode.id] = nextNode;
	}

	return nextNode;
}

function Treeview({
	NodeComponent,
	filter,
	inheritSelection,
	initialSelectedNodeIds,
	multiSelection,
	nodes: initialNodes,
	onSelectedNodesChange,
}: IProps) {
	const delay = useTimeout();

	const focusTimerRef = useRef<(() => void) | null>();

	const [state, dispatch] = useReducer<
		React.Reducer<TreeviewState, TreeviewAction>,
		{
			filter?: NodeFilter | string;
			inheritSelection: boolean;
			initialNodes: Node[];
			initialSelectedNodeIds: string[];
			multiSelection: boolean;
		}
	>(
		reducer,
		{
			filter,
			inheritSelection,
			initialNodes,
			initialSelectedNodeIds,
			multiSelection,
		},
		init
	);

	const {filteredNodes, nodes, selectedNodeIds} = state;

	const [shouldBeFocusable, setShouldBeFocusable] = useState(true);

	useEffect(() => {
		const filterFn = getFilterFn(filter);

		dispatch({filter: filterFn, type: 'FILTER'});
	}, [filter]);

	useEffect(() => {
		dispatch({newNodes: initialNodes, type: 'UPDATE_NODES'});
	}, [initialNodes]);

	useEffect(() => {
		if (onSelectedNodesChange) {
			onSelectedNodesChange(selectedNodeIds);
		}
	}, [onSelectedNodesChange, selectedNodeIds]);

	const cancelTimer = () => {
		if (focusTimerRef.current) {
			focusTimerRef.current();
			focusTimerRef.current = null;
		}
	};

	const handleMouseDown = () => {
		cancelTimer();

		dispatch({
			mouseNavigation: true,
			type: 'ACTIVATE',
		});
	};

	const handleFocus = () => {
		cancelTimer();
		setShouldBeFocusable(false);

		dispatch({type: 'ACTIVATE'});
	};

	const handleBlur = () => {
		cancelTimer();

		setShouldBeFocusable(true);

		// Due to React's event bubbling, we may get a "focus" event
		// immediately after this "blur" (eg. when moving around inside
		// the treeview); so, we defer this state update until the next
		// tick, giving us a chance to cancel it if needed.

		focusTimerRef.current = delay(() => {
			dispatch({type: 'DEACTIVATE'});
		}, 0);
	};

	return (
		<TreeviewContext.Provider value={{dispatch, state}}>
			<NodeList
				NodeComponent={NodeComponent}
				nodes={filteredNodes || nodes}
				onBlur={handleBlur}
				onFocus={handleFocus}
				onMouseDown={handleMouseDown}
				role="tree"
				tabIndex={shouldBeFocusable ? 0 : undefined}
			/>
		</TreeviewContext.Provider>
	);
}

Treeview.defaultProps = {
	NodeComponent: TreeviewLabel,
	multiSelection: true,
};

Treeview.Card = TreeviewCard;
Treeview.Label = TreeviewLabel;

export default Treeview;

interface IProps {
	NodeComponent: React.ComponentType<{node: Node}>;
	filter?: NodeFilter | string;
	inheritSelection: boolean;
	initialSelectedNodeIds: string[];
	multiSelection: boolean;
	nodes: Node[];
	onSelectedNodesChange: (selectedNodeIds?: Set<string>) => void;
}
