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

import React, {useState, useCallback, useEffect, useRef} from 'react';
import PropTypes from 'prop-types';

import NodeList from './NodeList.es';
import TreeviewLabel from './TreeviewLabel.es';
import TreeviewCard from './TreeviewCard.es';

const flattenNodes = (nodes, nodeList = []) => {
	nodes.forEach(node => {
		nodeList.push(node);

		if (node.children) {
			flattenNodes(node.children, nodeList);
		}
	});

	return nodeList;
};

function Treeview({
	NodeComponent,
	initialSelectedNodesIds,
	multiSelection,
	onSelectedNodesChange,
	nodes
}) {
	const nodeList = useRef([]);
	const [selectedNodesIds, setSelectedNodesIds] = useState(
		initialSelectedNodesIds
	);

	const handleNodeSelected = useCallback(
		id => {
			if (selectedNodesIds.includes(id)) {
				setSelectedNodesIds(
					selectedNodesIds.filter(selectedId => selectedId !== id)
				);
			} else if (multiSelection) {
				setSelectedNodesIds([...selectedNodesIds, id]);
			} else {
				setSelectedNodesIds([id]);
			}
		},
		[multiSelection, selectedNodesIds]
	);

	useEffect(() => {
		nodeList.current = flattenNodes(nodes);
	}, [nodes]);

	useEffect(() => {
		if (selectedNodesIds !== initialSelectedNodesIds) {
			onSelectedNodesChange(
				selectedNodesIds.map(nodeId =>
					nodeList.current.find(node => node.id === nodeId)
				)
			);
		}
	}, [
		initialSelectedNodesIds,
		nodes,
		onSelectedNodesChange,
		selectedNodesIds
	]);

	return (
		<NodeList
			NodeComponent={NodeComponent}
			initialSelectedNodesIds={initialSelectedNodesIds}
			nodes={nodes}
			onNodeSelected={handleNodeSelected}
			selectedNodesIds={selectedNodesIds}
		/>
	);
}

Treeview.defaultProps = {
	NodeComponent: TreeviewLabel,
	initialSelectedNodesIds: [],
	multiSelection: true,
	onSelectedNodesChange: () => {}
};

Treeview.propTypes = {
	NodeComponent: PropTypes.func,
	initialSelectedNodesIds: PropTypes.arrayOf(PropTypes.string),
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
