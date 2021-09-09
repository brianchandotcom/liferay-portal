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

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import {useStore, useZoomPanHelper} from 'react-flow-renderer';

const MoreNodes = ({focusNode, nodes}) => {
	const [active, setActive] = useState(false);

	const onClickFocus = (node) => () => {
		setActive(false);
		focusNode(node)();
	};

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={7}
			onActiveChange={setActive}
			trigger={
				<a className="current-node-link more-link">
					{Liferay.Language.get('more').toLowerCase()}...
				</a>
			}
		>
			<ClayDropDown.ItemList>
				{nodes.map((node) => (
					<ClayDropDown.Item key={node} onClick={onClickFocus(node)}>
						{node}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default function CurrentNodes({nodesNames = []}) {
	const store = useStore();
	const {setCenter} = useZoomPanHelper();

	if (!nodesNames?.length) {
		return null;
	}

	const {nodes} = store.getState();

	const currentNodes = nodes.filter((node) => nodesNames.includes(node.id));

	const nodesLabels = currentNodes.map(
		(currentNode) => currentNode.data.label
	);

	const focusNode = (nodeLabel) => () => {
		const node = currentNodes.find(
			(currentNode) => currentNode.data.label === nodeLabel
		);

		if (node) {
			const x = node.__rf.position.x + node.__rf.width / 2;
			const y = node.__rf.position.y + node.__rf.height / 2;
			const zoom = 1.85;

			setCenter(x, y, zoom);
		}
	};

	let firstNodes = nodesLabels;
	let moreNodes = [];

	if (nodesLabels.length > 4) {
		firstNodes = nodesLabels.slice(0, 3);
		moreNodes = nodesLabels.slice(3);
	}

	return (
		<div className="current-node">
			<ClayIcon className="current-icon ml-2" symbol="live" />
			<span className="current-node-text">
				{Liferay.Language.get('current-node')}:
			</span>
			{firstNodes.map((node, index) => (
				<a
					className="current-node-link"
					key={node}
					onClick={focusNode(node)}
				>
					{node}
					{index !== nodesLabels.length - 1 && ','}
				</a>
			))}
			{!!moreNodes.length && (
				<MoreNodes focusNode={focusNode} nodes={moreNodes} />
			)}
		</div>
	);
}
