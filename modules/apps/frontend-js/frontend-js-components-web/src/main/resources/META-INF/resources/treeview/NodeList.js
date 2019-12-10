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
import React, {useContext} from 'react';

import NodeListItem from './NodeListItem';
import TreeviewContext from './TreeviewContext';
import useKeyboardNavigation from './useKeyboardNavigation';

export default function NodeList({
	NodeComponent,
	nodes,
	role = 'group',
	tabIndex = -1
}) {
	const {dispatch} = useContext(TreeviewContext);

	const rootNodeId = nodes[0] && nodes[0].id;

	const handleKeyDown = useKeyboardNavigation(rootNodeId);

	if (!rootNodeId) {
		// All nodes have been filtered.
		return null;
	}

	return (
		<div
			className="lfr-treeview-node-list"
			onDoubleClick={() => {
				dispatch({nodeId: rootNodeId, type: 'TOGGLE_EXPANDED'});
			}}
			onKeyDown={handleKeyDown}
			role={role}
			tabIndex={tabIndex}
		>
			{nodes.map(node => (
				<NodeListItem
					key={node.id}
					node={node}
					NodeComponent={NodeComponent}
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
	tabIndex: PropTypes.number
};
