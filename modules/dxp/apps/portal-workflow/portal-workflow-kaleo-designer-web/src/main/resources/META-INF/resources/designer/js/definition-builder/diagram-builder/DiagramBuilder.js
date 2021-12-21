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

import {fetch} from 'frontend-js-web';
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
	isEdge,
	isNode,
} from 'react-flow-renderer';

import {DefinitionBuilderContext} from '../DefinitionBuilderContext';
import {defaultLanguageId} from '../constants';
import DefinitionDiagramController from '../source-builder/definitionDiagramController';
import {singleEventObserver} from '../util/EventObserver';
import {baseURL, headers} from '../util/fetchUtil';
import {getCollidingElements} from '../util/utils';
import {DiagramBuilderContextProvider} from './DiagramBuilderContext';
import {nodeTypes} from './components/nodes/utils';
import Sidebar from './components/sidebar/Sidebar';
import {isIdDuplicated} from './components/sidebar/utils';
import edgeTypes from './components/transitions/Edge';
import FloatingConnectionLine from './components/transitions/FloatingConnectionLine';

let id = 2;
const getId = () => `item_${id++}`;

const definitionDiagramController = new DefinitionDiagramController();

export default function DiagramBuilder({version}) {
	const {
		currentEditor,
		definitionTitle,
		deserialize,
		elements,
		selectedLanguageId,
		setDeserialize,
		setElements,
	} = useContext(DefinitionBuilderContext);
	const reactFlowWrapperRef = useRef(null);
	const [collidingElements, setCollidingElements] = useState(null);
	const [reactFlowInstance, setReactFlowInstance] = useState(null);
	const [selectedItem, setSelectedItem] = useState(null);
	const [selectedItemNewId, setSelectedItemNewId] = useState(null);

	const onConnect = (params) => {
		const defaultEdge = !elements.filter(
			(element) =>
				isEdge(element) &&
				element.source === params.source &&
				element.data.defaultEdge
		).length;

		const newEdge = {
			...params,
			arrowHeadType: 'arrowclosed',
			data: {
				defaultEdge,
				label: {
					[defaultLanguageId]: Liferay.Language.get(
						'transition-label'
					),
				},
			},
			id: getId(),
			type: 'transition',
		};

		setElements((previousElements) => addEdge(newEdge, previousElements));
		setSelectedItem(newEdge);
	};

	const onConnectEnd = () => {
		singleEventObserver.notify('handle-connect-end', true);
	};

	const onConnectStart = (_, {nodeId}) => {
		singleEventObserver.notify('handle-connect-start', nodeId);
	};

	const onDragOver = (event) => {
		const reactFlowBounds = reactFlowWrapperRef.current.getBoundingClientRect();

		const position = reactFlowInstance.project({
			x: event.clientX - reactFlowBounds.left,
			y: event.clientY - reactFlowBounds.top,
		});

		setCollidingElements(getCollidingElements(elements, position));

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

			if (getCollidingElements(elements, position).length === 0) {
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
			setCollidingElements(null);
		},
		[elements, reactFlowInstance, setElements]
	);

	const onLoad = (reactFlowInstance) => {
		setReactFlowInstance(reactFlowInstance);
	};

	useEffect(() => {
		if (
			selectedItem &&
			(selectedLanguageId
				? selectedItem.data.label[selectedLanguageId] !== ''
				: selectedItem.data.label[defaultLanguageId] !== '')
		) {
			setElements((elements) =>
				elements.map((element) => {
					if (element.id === selectedItem.id) {
						element = {
							...element,
							data: {
								...element.data,
								...selectedItem.data,
							},
						};
					}

					return element;
				})
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedItem]);

	useEffect(() => {
		if (
			selectedItemNewId &&
			selectedItemNewId.trim() !== '' &&
			!isIdDuplicated(elements, selectedItemNewId.trim())
		) {
			setElements((elements) =>
				elements.map((element) => {
					if (element.id === selectedItem.id) {
						element = {
							...element,
							id: selectedItemNewId,
						};

						setSelectedItemNewId(null);

						setSelectedItem(element);
					}

					return element;
				})
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedItem, selectedItemNewId]);

	useEffect(() => {
		if (deserialize && currentEditor) {
			const xmlDefinition = currentEditor.getData();

			definitionDiagramController.updateXMLDefinition(xmlDefinition);

			const nodes = definitionDiagramController.getNodes();

			setElements(nodes);

			setDeserialize(false);
		}
	}, [
		currentEditor,
		definitionTitle,
		deserialize,
		setDeserialize,
		setElements,
		version,
	]);

	useEffect(() => {
		if (version !== '0' && !deserialize) {
			fetch(
				`${baseURL}/workflow-definitions/by-name/${definitionTitle}`,
				{headers, method: 'GET'}
			)
				.then((response) => response.json())
				.then(({content}) => {
					definitionDiagramController.updateXMLDefinition(content);

					const nodes = definitionDiagramController.getNodes();

					setElements(nodes);
				});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [version]);

	const contextProps = {
		collidingElements,
		elements,
		selectedItem,
		selectedItemNewId,
		setCollidingElements,
		setElements,
		setSelectedItem,
		setSelectedItemNewId,
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
