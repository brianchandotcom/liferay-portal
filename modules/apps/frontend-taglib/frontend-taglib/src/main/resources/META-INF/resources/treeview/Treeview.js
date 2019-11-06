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

import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import NodeList from './NodeList';
import TreeviewCard from './TreeviewCard';
import TreeviewContext from './TreeviewContext';
import TreeviewLabel from './TreeviewLabel';

const flattenNodes = (nodes, nodeMap = {}) => {
	nodes.forEach(node => {
		nodeMap[node.id] = node;

		if (node.children) {
			flattenNodes(node.children, nodeMap);
		}
	});

	return nodeMap;
};

function Treeview({
	NodeComponent,
	filterQuery,
	initialSelectedNodeIds,
	multiSelection,
	nodes,
	onSelectedNodesChange
}) {
	const nodeMap = useMemo(() => flattenNodes(nodes), [nodes]);

	const [selectedNodeIds, setSelectedNodesIds] = useState(
		initialSelectedNodeIds
	);

	const handleNodeSelected = useCallback(
		id => {
			if (selectedNodeIds.includes(id)) {
				setSelectedNodesIds(
					selectedNodeIds.filter(selectedId => selectedId !== id)
				);
			} else if (multiSelection) {
				setSelectedNodesIds([...selectedNodeIds, id]);
			} else {
				setSelectedNodesIds([id]);
			}
		},
		[multiSelection, selectedNodeIds]
	);

	useEffect(() => {
		if (selectedNodeIds !== initialSelectedNodeIds) {
			onSelectedNodesChange(
				selectedNodeIds.map(nodeId => nodeMap[nodeId])
			);
		}
	}, [
		initialSelectedNodeIds,
		nodeMap,
		onSelectedNodesChange,
		selectedNodeIds
	]);

	return (
		<TreeviewContext.Provider value={{filterQuery}}>
			<NodeList
				NodeComponent={NodeComponent}
				initialSelectedNodeIds={initialSelectedNodeIds}
				nodes={nodes}
				onNodeSelected={handleNodeSelected}
				selectedNodeIds={selectedNodeIds}
			/>
		</TreeviewContext.Provider>
	);
}

Treeview.defaultProps = {
	NodeComponent: TreeviewLabel,
	initialSelectedNodeIds: [],
	multiSelection: true,
	onSelectedNodesChange: () => {}
};

Treeview.propTypes = {
	NodeComponent: PropTypes.func,
	initialSelectedNodeIds: PropTypes.arrayOf(PropTypes.string),
	multiSelection: PropTypes.bool,
	nodes: PropTypes.arrayOf(
		PropTypes.shape({
			children: PropTypes.array,
			id: PropTypes.string.isRequired
		})
	).isRequired,
	onSelectedNodesChange: PropTypes.func
};

Treeview.Card = TreeviewCard;
Treeview.Label = TreeviewLabel;

export default Treeview;
