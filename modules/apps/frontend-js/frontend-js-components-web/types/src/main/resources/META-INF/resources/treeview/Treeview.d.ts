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
import TreeviewCard from './TreeviewCard';
import {Node, NodeFilter} from './TreeviewContext';
import TreeviewLabel from './TreeviewLabel';
import './Treeview.scss';
declare function Treeview({
	NodeComponent,
	filter,
	inheritSelection,
	initialSelectedNodeIds,
	multiSelection,
	nodes: initialNodes,
	onSelectedNodesChange,
}: IProps): JSX.Element;
declare namespace Treeview {
	var defaultProps: {
		NodeComponent: typeof TreeviewLabel;
		multiSelection: boolean;
	};
	var Card: typeof TreeviewCard;
	var Label: typeof TreeviewLabel;
}
export default Treeview;
interface IProps {
	NodeComponent: React.ComponentType<{
		node: Node;
	}>;
	filter?: NodeFilter | string;
	inheritSelection: boolean;
	initialSelectedNodeIds: string[];
	multiSelection: boolean;
	nodes: Node[];
	onSelectedNodesChange: (selectedNodeIds?: Set<string>) => void;
}
