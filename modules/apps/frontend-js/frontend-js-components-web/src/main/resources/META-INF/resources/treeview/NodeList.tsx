/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import NodeListItem from './NodeListItem';
import {Node} from './TreeviewContext';

export default function NodeList({
	NodeComponent,
	nodes,
	onBlur,
	onFocus,
	onMouseDown,
	role = 'group',
	tabIndex = -1,
}: IProps) {
	const rootNodeId = nodes[0] && nodes[0].id;

	if (!rootNodeId) {

		// All nodes have been filtered.

		return null;
	}

	return (
		<div
			className="lfr-treeview-node-list"
			onBlur={() => {
				if (onBlur) {
					onBlur();
				}
			}}
			onFocus={(event) => {
				if (onFocus) {
					onFocus(event);
				}
			}}
			onMouseDown={(event) => {
				if (onMouseDown) {
					onMouseDown(event);
				}
			}}
			role={role}
			tabIndex={tabIndex}
		>
			{nodes.map((node) => (
				<NodeListItem
					NodeComponent={NodeComponent}
					key={node.id}
					node={node}
				/>
			))}
		</div>
	);
}

interface IProps {
	NodeComponent: React.ComponentType<{node: Node}>;
	nodes: Node[];
	onBlur?: () => void;
	onFocus?: (event: React.FocusEvent<HTMLDivElement>) => void;
	onMouseDown?: (event: React.MouseEvent<HTMLDivElement>) => void;
	role?: string;
	tabIndex?: number;
}
