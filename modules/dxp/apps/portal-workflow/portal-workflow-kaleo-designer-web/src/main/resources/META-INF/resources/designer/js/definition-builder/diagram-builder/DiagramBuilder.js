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
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';
import ReactFlow, {
	Background,
	Controls,
	ReactFlowProvider,
	addEdge,
	isNode,
} from 'react-flow-renderer';

import {DefinitionBuilderContext} from '../DefinitionBuilderContext';
import {singleEventObserver} from '../util/EventObserver';
import {DiagramBuilderContextProvider} from './DiagramBuilderContext';
import {nodeTypes} from './components/nodes/utils';
import Sidebar from './components/sidebar/Sidebar';
import {isIdDuplicated} from './components/sidebar/utils';
import edgeTypes from './components/transitions/Edge';
import FloatingConnectionLine from './components/transitions/FloatingConnectionLine';

let id = 2;
const getId = () => `node_${id++}`;

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
		if (
			isNode(element) &&
			isOverlapping(element.position, newElementPosition)
		) {
			available = false;
		}
	});

	return available;
};

export default function DiagramBuilder({version}) {
	const {
		defaultLanguageId,
		elements,
		selectedLanguageId,
		setElements,
	} = useContext(DefinitionBuilderContext);
	const reactFlowWrapperRef = useRef(null);
	const [availableArea, setAvailableArea] = useState(null);
	const [reactFlowInstance, setReactFlowInstance] = useState(null);
	const [selectedNode, setSelectedNode] = useState(null);
	const [selectedNodeNewId, setSelectedNodeNewId] = useState(null);

	const onConnect = (params) => {
		setElements((els) =>
			addEdge(
				{
					...params,
					arrowHeadType: 'arrowclosed',
					data: {
						label: `transition label`,
					},
					type: 'transition',
				},
				els
			)
		);
	};

	const onConnectEnd = () => {
		singleEventObserver.notify('handle-connect-end', true);
	};

	const onConnectStart = (event, {nodeId}) => {
		singleEventObserver.notify('handle-connect-start', nodeId);
	};

	const onDragOver = (event) => {
		const reactFlowBounds = reactFlowWrapperRef.current.getBoundingClientRect();

		const position = reactFlowInstance.project({
			x: event.clientX - reactFlowBounds.left,
			y: event.clientY - reactFlowBounds.top,
		});

		if (isPositionAvailable(elements, position)) {
			setAvailableArea(true);
		}
		else {
			setAvailableArea(false);
		}

		event.preventDefault();

		event.dataTransfer.dropEffect = 'move';
	};

	const onDrop = useCallback(
		(event) => {
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
					data: {
						newNode: true,
					},
					id: getId(),
					position,
					type,
				};

				setElements((elements) => elements.concat(newNode));
			}

			setAvailableArea(null);
		},
		[elements, reactFlowInstance, setElements]
	);

	const onLoad = (reactFlowInstance) => {
		if (version !== '0') {
			reactFlowInstance.fitView();
		}

		setReactFlowInstance(reactFlowInstance);
	};

	useEffect(() => {
		if (
			selectedNode &&
			(selectedLanguageId
				? selectedNode.data.label[selectedLanguageId] !== ''
				: selectedNode.data.label[defaultLanguageId] !== '')
		) {
			setElements((elements) =>
				elements.map((element) => {
					if (isNode(element) && element.id === selectedNode.id) {
						element = {
							...element,
							data: {
								...element.data,
								...selectedNode.data,
							},
						};
					}

					return element;
				})
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedNode]);

	useEffect(() => {
		if (
			selectedNodeNewId &&
			selectedNodeNewId.trim() !== '' &&
			!isIdDuplicated(elements, selectedNodeNewId.trim())
		) {
			setElements((elements) =>
				elements.map((element) => {
					if (isNode(element) && element.id === selectedNode.id) {
						element = {
							...element,
							id: selectedNodeNewId,
						};

						setSelectedNodeNewId(null);

						setSelectedNode(element);
					}

					return element;
				})
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedNode, selectedNodeNewId]);

	const contextProps = {
		availableArea,
		elements,
		selectedNode,
		selectedNodeNewId,
		setAvailableArea,
		setElements,
		setSelectedNode,
		setSelectedNodeNewId,
	};

	return (
		<DiagramBuilderContextProvider {...contextProps}>
			<div className="diagram-builder">
				<div className="diagram-area" ref={reactFlowWrapperRef}>
					<ReactFlowProvider>
						<ReactFlow
							connectionLineComponent={FloatingConnectionLine}
							edgeTypes={edgeTypes}
							elements={elements}
							minZoom="0.1"
							nodeTypes={nodeTypes}
							onConnect={onConnect}
							onConnectEnd={onConnectEnd}
							onConnectStart={onConnectStart}
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
