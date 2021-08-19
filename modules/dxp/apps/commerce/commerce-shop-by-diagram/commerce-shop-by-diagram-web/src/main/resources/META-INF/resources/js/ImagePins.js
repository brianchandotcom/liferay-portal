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

import {drag, event as d3event, select, zoom as d3zoom, zoomIdentity} from 'd3';
import PropTypes from 'prop-types';
import React, {useLayoutEffect, useRef} from 'react';

import NavigationButtons from './NavigationButtons';
import {moveController, zoomIn, zoomOut} from './NavigationsUtils';
import ZoomController from './ZoomController';

const PIN_ATTRIBUTES = [
	'cx',
	'cy',
	'draggable',
	'fill',
	'id',
	'label',
	'linked_to_sku',
	'quantity',
	'r',
	'sku',
];

const ImagePins = ({
	addNewPinState,
	addPinHandler,
	cPins,
	changedScale,
	children,
	enablePanZoom,
	execZoomIn,
	handleAddPin,
	imageSettings,
	imageURL,
	isAdmin,
	namespace,
	navigationController,
	pinClickAction,
	removePinHandler,
	resetZoom,
	selectedOption,
	setAddPinHandler,
	setChangedScale,
	setCpins,
	setPinClickHandler,
	setRemovePinHandler,
	setResetZoom,
	setSelectedOption,
	setShowTooltip,
	setZoomInHandler,
	setZoomOutHandler,
	zoomController,
	zoomInHandler,
	zoomOutHandler,
}) => {
	const handlers = useRef();
	const containerRef = useRef();
	const svgRef = useRef(null);
	const transform = useRef({k: 1, x: 0, y: 0});

	useLayoutEffect(() => {
		const container = select(containerRef.current);
		const svg = select(svgRef.current);

		const zoom = d3zoom()
			.scaleExtent([0.5, 40])
			.on('zoom', () => {
				transform.current = d3event.transform;
				container.attr('transform', transform.current);
			});

		svg.call(zoom);

		if (isAdmin) {
			svg.on('dblclick.zoom', () => {
				const x =
					(d3event.offsetX - transform.current.x) /
					transform.current.k;
				const y =
					(d3event.offsetY - transform.current.y) /
					transform.current.k;
				setCpins(
					cPins.concat({
						cx: x,
						cy: y,
						draggable: true,
						fill: `#${addNewPinState.fill}`,
						label: '',
						linked_to_sku: 'sku',
						quantity: 0,
						r: addNewPinState.radius,
						sku: addNewPinState.sku,
					})
				);
			});
		}

		if (resetZoom) {
			setResetZoom(false);

			svg.transition().duration(700).call(zoom.transform, zoomIdentity);
		}

		if (changedScale) {
			setChangedScale(false);

			const imageInfos = container.node().getBBox();

			container
				.transition()
				.duration(700)
				.attr(
					'transform',
					`translate(${imageInfos.width / 2.0},${
						imageInfos.height / 2.0
					}) scale(${selectedOption}) translate(-${
						imageInfos.width / 2.0
					},-${imageInfos.height / 2.0})`
				);
		}

		handlers.current = {
			moveController: (direction) =>
				moveController(svg, navigationController, direction, zoom),
			zoomIn: () => zoomIn(svg, zoom),
			zoomOut: () => zoomOut(svg, zoom),
		};

		if (execZoomIn) {
			handlers.current?.zoomIn();
		}

		if (zoomOutHandler) {
			setZoomOutHandler(false);
			zoomOut(container, zoom);
		}

		if (zoomInHandler) {
			setZoomInHandler(false);
			zoomIn(container, zoom);
		}

		function dragStarted() {
			select(this).raise().classed('active', true);
		}

		function dragged() {
			select(this).attr(
				'transform',
				`translate(${d3event.x},${d3event.y})`
			);
		}

		function dragEnded() {
			const current = select(this);
			const newPos = current._groups[0][0].attributes;
			const beSure = [...newPos];
			const updatedPin = {};
			select(this).classed('active', false);

			PIN_ATTRIBUTES.map((element) => {
				beSure.filter((attr) => {
					if (attr.name === element) {
						if (element === 'cx') {
							updatedPin[`${attr.name}`] = parseFloat(d3event.x);
						}
						else if (element === 'cy') {
							updatedPin[`${attr.name}`] = parseFloat(d3event.y);
						}
						else if (
							element === 'quantity' ||
							element === 'r' ||
							element === 'id'
						) {
							updatedPin[`${attr.name}`] = parseInt(
								attr.value,
								10
							);
						}
						else if (element === 'draggable') {
							updatedPin[`${attr.name}`] = attr.value
								? true
								: false;
						}
						else {
							updatedPin[`${attr.name}`] = attr.value;
						}
					}
				});
			});

			const newState = cPins.map((element) => {
				if (element.id === updatedPin.id) {
					if (
						Math.abs(element.cx - updatedPin.cx) < 10 &&
						Math.abs(element.cy - updatedPin.cy) < 10
					) {
						pinClickAction(updatedPin);
					}

					return updatedPin;
				}
				else {
					return element;
				}
			});

			setCpins(newState);
		}

		const dragHandler = isAdmin
			? drag()
					.on('start', dragStarted)
					.on('drag', dragged)
					.on('end', dragEnded)
			: drag();

		const addPin = () => {
			setCpins(
				cPins.concat({
					cx: 50,
					cy: 50,
					draggable: true,
					fill: '#' + addNewPinState.fill,
					label: '',
					linked_to_sku: 'sku',
					quantity: 0,
					r: addNewPinState.radius,
					sku: addNewPinState.sku,
				})
			);
		};

		const removePin = (id) => {
			const currentPins = cPins.filter((element) => element.id !== id);

			const newState = currentPins.map((pin, i) => {
				return {
					cx: pin.cx,
					cy: pin.cy,
					draggable: pin.draggable,
					fill: pin.fill,
					id: i,
					label: pin.label,
					linked_to_sku: pin.linked_to_sku,
					quantity: pin.quantity,
					r: pin.r,
					sku: pin.sku,
				};
			});

			setCpins(newState);
		};

		if (removePinHandler.handler) {
			removePin(removePinHandler.pin);
			setRemovePinHandler({
				handler: false,
				pin: null,
			});
		}

		if (addPinHandler) {
			setAddPinHandler(false);
			addPin();
		}

		if (!removePinHandler.handler && !addPinHandler) {
			try {
				container.selectAll('.circle_pin').remove();
			}
			catch (error) {
				return;
			}

			const cont = container
				.selectAll('g')
				.data(cPins)
				.enter()
				.append('g')
				.attr('transform', (attr) => `translate(${attr.cx},${attr.cy})`)
				.attr('cx', (attr) => attr.cx)
				.attr('cy', (attr) => attr.cy)
				.attr('id', (attr) => attr.id)
				.attr('label', (attr) => attr.label)
				.attr('fill', () => `#${addNewPinState.fill}`)
				.attr('linked_to_sku', (attr) => attr.linked_to_sku)
				.attr('quantity', (attr) => attr.quantity)
				.attr('r', () => addNewPinState.radius)
				.attr('sku', (attr) => attr.sku)
				.attr('id', (attr) => attr.id)
				.attr('class', 'circle_pin')
				.attr('draggable', (attr) => (attr.draggable ? true : false))
				.call(dragHandler);

			cont.append('circle')
				.attr('fill', () => '#ffffff')
				.attr('r', () => addNewPinState.radius)
				.attr('stroke', () => `#${addNewPinState.fill}`)
				.attr('stroke-width', 2);

			cont.append('text')
				.text((attr) => attr.label)
				.attr('font-size', (attr) => attr.r)
				.attr('text-anchor', 'middle')
				.attr('fill', '#000000')
				.attr('alignment-baseline', 'central');
		}

		if (isAdmin) {
			select('#newPin').on('click', handleAddPin);
		}
	}, [
		addNewPinState,
		addPinHandler,
		changedScale,
		cPins,
		execZoomIn,
		selectedOption,
		isAdmin,
		enablePanZoom,
		imageSettings,
		navigationController,
		removePinHandler,
		handleAddPin,
		resetZoom,
		setAddPinHandler,
		setChangedScale,
		setCpins,
		setPinClickHandler,
		setResetZoom,
		setRemovePinHandler,
		setShowTooltip,
		setSelectedOption,
		setZoomInHandler,
		setZoomOutHandler,
		zoomOutHandler,
		zoomInHandler,
		pinClickAction,
	]);

	return (
		<div
			className="diagram-pins-container"
			style={{
				height: `${imageSettings.height}`,
				width: `${imageSettings.width}`,
			}}
		>
			<svg
				height={imageSettings.height}
				ref={svgRef}
				width={imageSettings.width}
			>
				<g data-testid={`${namespace}container`} ref={containerRef}>
					<image
						height={imageSettings.height}
						href={imageURL}
					></image>
				</g>
			</svg>

			{children}

			{navigationController.enable && (
				<NavigationButtons
					moveController={(where) =>
						handlers.current?.moveController(where)
					}
					position={navigationController.position}
				/>
			)}

			{zoomController.enable && (
				<ZoomController
					position={zoomController.position}
					zoomIn={() => handlers.current?.zoomIn()}
					zoomOut={() => handlers.current?.zoomOut()}
				/>
			)}
		</div>
	);
};

export default ImagePins;

ImagePins.default = {
	scale: 1,
};

ImagePins.propTypes = {
	addPinHandler: PropTypes.bool,
	cPins: PropTypes.arrayOf(
		PropTypes.shape({
			cx: PropTypes.double,
			cy: PropTypes.double,
			draggable: PropTypes.bool,
			fill: PropTypes.string,
			id: PropTypes.number,
			label: PropTypes.string,
			linked_to_sku: PropTypes.oneOf(['sku', 'diagram']),
			quantity: PropTypes.number,
			r: PropTypes.number,
			sku: PropTypes.string,
		})
	),
	enableResetZoom: PropTypes.bool,
	execResetZoom: PropTypes.bool,
	handleZoomIn: PropTypes.func,
	handleZoomOut: PropTypes.func,
	image: PropTypes.string,
	imageSettings: PropTypes.shape({
		height: PropTypes.string,
		width: PropTypes.string,
	}),
	namespace: PropTypes.string.isRequired,
	navigationController: PropTypes.shape({
		dragStep: PropTypes.number,
		enable: PropTypes.bool,
		enableDrag: PropTypes.bool,
		position: PropTypes.shape({
			bottom: PropTypes.string,
			left: PropTypes.string,
			right: PropTypes.string,
			top: PropTypes.string,
		}),
	}),
	removePinHandler: PropTypes.shape({
		handler: PropTypes.bool,
		pin: PropTypes.number,
	}),
	setAddPinHandler: PropTypes.func,
	setCpins: PropTypes.func,
	setImageState: PropTypes.func,
	setShowTooltip: PropTypes.func,
	setZoomInHandler: PropTypes.func,
	setZoomOutHandler: PropTypes.func,
	showTooltip: PropTypes.shape({
		details: PropTypes.shape({
			cx: PropTypes.double,
			cy: PropTypes.double,
			id: PropTypes.number,
			label: PropTypes.string,
			linked_to_sku: PropTypes.oneOf(['sku', 'diagram']),
			quantity: PropTypes.number,
			sku: PropTypes.string,
		}),
		tooltip: PropTypes.bool,
	}),
	zoomController: PropTypes.shape({
		enable: PropTypes.bool,
		position: PropTypes.shape({
			bottom: PropTypes.string,
			left: PropTypes.string,
			right: PropTypes.string,
			top: PropTypes.string,
		}),
	}),
	zoomIn: PropTypes.func,
	zoomInHandler: PropTypes.bool,
	zoomOut: PropTypes.func,
	zoomOutHandler: PropTypes.bool,
};
