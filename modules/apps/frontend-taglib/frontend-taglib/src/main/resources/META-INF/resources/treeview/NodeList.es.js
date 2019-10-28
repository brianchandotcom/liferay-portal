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
import React from 'react';

import NodeListItem from './NodeListItem.es';

export default function NodeList({
	NodeComponent,
	initialSelectedNodeIds,
	nodes,
	onNodeSelected,
	selectedNodeIds
}) {
	return (
		<div className="lfr-treeview-node-list">
			{nodes.map(node => (
				<NodeListItem
					NodeComponent={NodeComponent}
					initialSelectedNodeIds={initialSelectedNodeIds}
					key={node.id}
					node={node}
					onNodeSelected={onNodeSelected}
					selectedNodeIds={selectedNodeIds}
				/>
			))}
		</div>
	);
}

NodeList.propTypes = {
	NodeComponent: PropTypes.func.isRequired,
	nodes: PropTypes.arrayOf(
		PropTypes.shape({
			children: PropTypes.array,
			id: PropTypes.string.isRequired,
			name: PropTypes.string.isRequired
		})
	).isRequired,
	onNodeSelected: PropTypes.func.isRequired,
	selectedNodeIds: PropTypes.arrayOf(PropTypes.string).isRequired
};
