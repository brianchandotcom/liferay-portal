/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default React.createContext<TreeviewContext>({
	dispatch: () => {},
	state: {
		filteredNodes: null,
		focusedNodeId: null,
		nodeMap: {},
		nodes: [],
		selectedNodeIds: new Set<string>(),
	},
});

export interface LinkedNode extends Node {
	children: LinkedNode[];
	nextSiblingId: string | null;
	parentId: string | null;
	previousSiblingId: string | null;
}

export interface Node {
	children: Node[];
	disabled?: boolean;
	expanded: boolean;
	icon?: string;
	iconCssClass?: string;
	id: string;
	name: string;
	nodePath?: string;
	selected?: boolean;
}

export type NodeFilter = (node: LinkedNode) => boolean;

export interface NodeMap {
	[key: string]: LinkedNode;
}

export type TreeviewAction =
	| {mouseNavigation?: true; type: 'ACTIVATE'}
	| {nodeId: string; type: 'COLLAPSE'}
	| {nodeId: string; type: 'COLLAPSE_PARENT'}
	| {type: 'DEACTIVATE'}
	| {type: 'EXIT'}
	| {type: 'EXPAND_ALL'}
	| {nodeId: string; type: 'EXPAND_AND_ENTER'}
	| {filter?: NodeFilter; type: 'FILTER'}
	| {nodeId: string; type: 'FOCUS'}
	| {type: 'SELECT_LAST_VISIBLE'}
	| {nodeId: string; type: 'SELECT_NEXT_VISIBLE'}
	| {nodeId: string; type: 'SELECT_PREVIOUS_VISIBLE'}
	| {type: 'SELECT_ROOT'}
	| {nodeId: string; type: 'TOGGLE_EXPANDED'}
	| {nodeId: string; type: 'TOGGLE_SELECT'}
	| {newNodes: Node[]; type: 'UPDATE_NODES'};

interface TreeviewContext {
	dispatch: React.Dispatch<TreeviewAction>;
	state: TreeviewState;
}

export interface TreeviewState {
	active?: boolean;
	filter?: NodeFilter;
	filteredNodes: LinkedNode[] | null;
	focusedNodeId: null | string;
	inheritSelection?: boolean;
	multiSelection?: boolean;
	nodeMap: NodeMap;
	nodes: LinkedNode[];
	selectedNodeIds: Set<string>;
}
