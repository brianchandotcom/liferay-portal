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

import React from 'react';
declare const _default: React.Context<TreeviewContext>;
export default _default;
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
export declare type NodeFilter = (node: LinkedNode) => boolean;
export interface NodeMap {
	[key: string]: LinkedNode;
}
export declare type TreeviewAction =
	| {
			mouseNavigation?: true;
			type: 'ACTIVATE';
	  }
	| {
			nodeId: string;
			type: 'COLLAPSE';
	  }
	| {
			nodeId: string;
			type: 'COLLAPSE_PARENT';
	  }
	| {
			type: 'DEACTIVATE';
	  }
	| {
			type: 'EXIT';
	  }
	| {
			type: 'EXPAND_ALL';
	  }
	| {
			nodeId: string;
			type: 'EXPAND_AND_ENTER';
	  }
	| {
			filter?: NodeFilter;
			type: 'FILTER';
	  }
	| {
			nodeId: string;
			type: 'FOCUS';
	  }
	| {
			type: 'SELECT_LAST_VISIBLE';
	  }
	| {
			nodeId: string;
			type: 'SELECT_NEXT_VISIBLE';
	  }
	| {
			nodeId: string;
			type: 'SELECT_PREVIOUS_VISIBLE';
	  }
	| {
			type: 'SELECT_ROOT';
	  }
	| {
			nodeId: string;
			type: 'TOGGLE_EXPANDED';
	  }
	| {
			nodeId: string;
			type: 'TOGGLE_SELECT';
	  }
	| {
			newNodes: Node[];
			type: 'UPDATE_NODES';
	  };
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
