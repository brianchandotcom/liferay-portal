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
import React, {useContext} from 'react';

import NodeList from './NodeList';
import TreeviewContext, {Node} from './TreeviewContext';
import useFocus from './useFocus';
import useKeyboardNavigation from './useKeyboardNavigation';

export default function NodeListItem({NodeComponent, node}: IProps) {
	const {dispatch} = useContext(TreeviewContext);

	const focusable = useFocus<HTMLDivElement>(node.id);

	const handleKeyDown = useKeyboardNavigation(node.id);

	const children = node.children || [];

	const nodeListItemClassNames = classNames('lfr-treeview-node-list-item', {
		'with-children': !!children.length,
	});

	const childrenId = `node-list-item-${node.id}-children`;

	const symbol = node.expanded ? 'hr' : 'plus';

	const toggleExpanded = (
		event: React.MouseEvent<HTMLButtonElement | HTMLDivElement>
	) => {
		if (node.children.length) {
			event.stopPropagation();

			dispatch({nodeId: node.id, type: 'TOGGLE_EXPANDED'});
		}
	};

	const toggleSelected = () => {
		dispatch({nodeId: node.id, type: 'TOGGLE_SELECT'});
	};

	return (
		<>
			<div
				className={nodeListItemClassNames}
				onBlur={() => {
					return;
				}}
				onClick={toggleSelected}
				onDoubleClick={toggleExpanded}
				onFocusCapture={() =>
					dispatch({nodeId: node.id, type: 'FOCUS'})
				}
				onKeyDown={handleKeyDown}
				ref={focusable}
				role="treeitem"
				tabIndex={-1}
			>
				{children.length ? (
					<button
						aria-controls={childrenId}
						aria-expanded={node.expanded}
						aria-label={`${node.expanded ? 'Collapse' : 'Expand'} ${
							node.name
						}`}
						className="lfr-treeview-node-list-item__button"
						onClick={toggleExpanded}
						tabIndex={-1}
						type="button"
					>
						<ClayIcon
							className="lfr-treeview-node-list-item__button-icon"
							key={symbol}
							symbol={symbol}
						/>
					</button>
				) : null}

				<div className="lfr-treeview-node-list-item__node">
					<NodeComponent node={node} />
				</div>
			</div>

			{node.expanded && (
				<div
					className="lfr-treeview-node-list-item__children"
					id={childrenId}
				>
					<NodeList NodeComponent={NodeComponent} nodes={children} />
				</div>
			)}
		</>
	);
}

interface IProps {
	NodeComponent: React.ComponentType<{node: Node}>;
	node: Node;
}
