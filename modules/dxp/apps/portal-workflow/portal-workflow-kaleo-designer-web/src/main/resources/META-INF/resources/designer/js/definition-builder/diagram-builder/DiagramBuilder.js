/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import PropTypes from 'prop-types';
import React, {useCallback, useRef, useState} from 'react';
import ReactFlow, {
	Background,
	Controls,
	ReactFlowProvider,
} from 'react-flow-renderer';

import {DiagramBuilderContextProvider} from './DiagramBuilderContext';
import {nodeTypes} from './components/nodes/utils';
import Sidebar from './components/sidebar/Sidebar';

let id = 0;
const getId = () => `dndnode_${id++}`;

const isOverlapping = (elementPosition, newElementPosition) => {
	const isInHorizontalBounds =
		newElementPosition.x < elementPosition.x + 280 &&
		newElementPosition.x + 280 > elementPosition.x;

	const isInVerticalBounds =
		newElementPosition.y < elementPosition.y + 100 &&
		newElementPosition.y + 100 > elementPosition.y;

	const isOverlapping = isInHorizontalBounds && isInVerticalBounds;

	return isOverlapping;
};

const isPositionAvailable = (elements, newElementPosition) => {
	let available = true;

	elements.forEach((element) => {
		if (isOverlapping(element.position, newElementPosition)) {
			available = false;
		}
	});

	return available;
};

export default function DiagramBuilder({version}) {
	const reactFlowWrapperRef = useRef(null);
	const [availableArea, setAvailableArea] = useState(null);
	const [reactFlowInstance, setReactFlowInstance] = useState(null);
	const startNode = {
		id: '0',
		position: {x: 300, y: 100},
		type: 'start',
	};
	const endNode = {
		id: '1',
		position: {x: 300, y: 400},
		type: 'end',
	};

	const [elements, setElements] = useState([startNode, endNode]);

	const onDragOver = (event) => {
		const reactFlowBounds = reactFlowWrapperRef.current.getBoundingClientRect();

		const position = reactFlowInstance.project({
			x: event.clientX - reactFlowBounds.left,
			y: event.clientY - reactFlowBounds.top,
		});

		if (isPositionAvailable(elements, position)) {
			setAvailableArea(true);

			event.preventDefault();

			event.dataTransfer.dropEffect = 'move';
		}
		else {
			setAvailableArea(false);
		}
	};

	const onDrop = useCallback(
		(event) => {
			setAvailableArea(null);

			const reactFlowBounds = reactFlowWrapperRef.current.getBoundingClientRect();

			const position = reactFlowInstance.project({
				x: event.clientX - reactFlowBounds.left,
				y: event.clientY - reactFlowBounds.top,
			});

			if (isPositionAvailable(elements, position)) {
				event.preventDefault();

				const type = event.dataTransfer.getData(
					'application/reactflow'
				);

				const newNode = {
					id: getId(),
					position,
					type,
				};

				setElements((es) => es.concat(newNode));
			}
		},
		[elements, reactFlowInstance]
	);

	const onLoad = (reactFlowInstance) => {
		if (version !== '0') {
			reactFlowInstance.fitView();
		}

		setReactFlowInstance(reactFlowInstance);
	};

	return (
		<DiagramBuilderContextProvider availableArea={availableArea}>
			<div className="diagram-builder">
				<div className="diagram-area" ref={reactFlowWrapperRef}>
					<ReactFlowProvider>
						<ReactFlow
							elements={elements}
							minZoom="0.1"
							nodeTypes={nodeTypes}
							onDragOver={onDragOver}
							onDrop={onDrop}
							onLoad={onLoad}
						/>

						<Controls showInteractive={false} />

						<Background size={1} />
					</ReactFlowProvider>
				</div>

				<Sidebar />
			</div>
		</DiagramBuilderContextProvider>
	);
}

DiagramBuilder.propTypes = {
	version: PropTypes.string.isRequired,
};
