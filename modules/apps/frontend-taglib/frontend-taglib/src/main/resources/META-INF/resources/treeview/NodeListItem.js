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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState, useEffect} from 'react';

import NodeList from './NodeList';

function hasToBeExpanded(selectedNodeIds, node) {
	if (!node.children || node.children.length === 0) {
		return false;
	}

	if (node.children.some(child => selectedNodeIds.includes(child.id))) {
		return true;
	}

	return node.children.some(child => hasToBeExpanded(selectedNodeIds, child));
}

export default function NodeListItem({
	NodeComponent,
	initialSelectedNodeIds,
	node,
	onNodeSelected,
	selectedNodeIds
}) {
	const [expanded, setExpanded] = useState(false);

	useEffect(() => {
		setExpanded(hasToBeExpanded(initialSelectedNodeIds, node));
	}, [initialSelectedNodeIds, node]);

	const children = node.children || [];

	const nodeListItemClassNames = classNames('lfr-treeview-node-list-item', {
		'with-children': children.length > 0
	});

	const childrenId = `node-list-item-${node.id}-children`;

	const symbol = expanded ? 'hr' : 'plus';

	return (
		<>
			<div className={nodeListItemClassNames}>
				{children.length > 0 && (
					<button
						aria-controls={childrenId}
						aria-expanded={expanded}
						aria-label={`${expanded ? 'Collapse' : 'Expand'} ${
							node.name
						}`}
						className="lfr-treeview-node-list-item__button"
						onClick={() => setExpanded(expanded => !expanded)}
						type="button"
					>
						<ClayIcon
							className="lfr-treeview-node-list-item__button-icon"
							key={symbol}
							symbol={symbol}
						/>
					</button>
				)}

				<div className="lfr-treeview-node-list-item__node">
					<NodeComponent
						node={node}
						onNodeSelected={onNodeSelected}
						selectedNodeIds={selectedNodeIds}
					/>
				</div>
			</div>

			{expanded && (
				<div
					className="lfr-treeview-node-list-item__children"
					id={childrenId}
				>
					<NodeList
						NodeComponent={NodeComponent}
						initialSelectedNodeIds={initialSelectedNodeIds}
						nodes={children}
						onNodeSelected={onNodeSelected}
						selectedNodeIds={selectedNodeIds}
					/>
				</div>
			)}
		</>
	);
}

NodeListItem.propTypes = {
	NodeComponent: PropTypes.func.isRequired,
	node: PropTypes.shape({children: PropTypes.array}),
	onNodeSelected: PropTypes.func.isRequired,
	selectedNodeIds: PropTypes.arrayOf(PropTypes.string).isRequired
};
