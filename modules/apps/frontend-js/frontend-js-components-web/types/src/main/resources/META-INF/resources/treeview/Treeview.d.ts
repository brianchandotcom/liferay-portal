/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
