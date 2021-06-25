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

import { event, select, zoom, zoomIdentity, zoomTransform } from 'd3';
import PropTypes from 'prop-types';
import React, { useLayoutEffect, useRef } from 'react';

import NavigationButtons from './NavigationButtons';
import { moveController, zoomIn, zoomOut } from './NavigationsUtils';
import ZoomController from './ZoomController';

const ImagePins = ({
	changedScale,
	enablePanZoom,
	execZoomIn,
	imageSettings,
	imageURL,
	namespace,
	navigationController,
	resetZoom,
	selectedOption,
	setChangedScale,
	setResetZoom,
	setSelectedOption,
	setZoomInHandler,
	setZoomOutHandler,
	zoomController,
	zoomInHandler,
	zoomOutHandler,
}) => {
	const handlers = useRef();
	const containerRef = useRef();
	const panZoomRef = useRef();

	const svgRef = useRef(null);

	useLayoutEffect(() => {
		containerRef.current = select(`#${namespace}container`);
		panZoomRef.current = zoom()
			.scaleExtent([0.5, 40])
			.on('zoom', () => {
				containerRef.current.attr('transform', event.transform);
			});

		if (enablePanZoom) {
			containerRef.current.call(panZoomRef.current);
		}

		if (resetZoom) {
			setResetZoom(false);
			containerRef.current
				.transition()
				.duration(700)
				.call(
					panZoomRef.current.transform,
					zoomIdentity,
					zoomTransform(containerRef.current.node()).invert([
						imageSettings.width,
						imageSettings.height,
					])
				);

			setSelectedOption(1);
		}

		if (changedScale) {
			setChangedScale(false);
			const imageInfos = containerRef.current.node().getBBox();
			containerRef.current
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

		if (execZoomIn) {
			handlers.current?.zoomIn();
		}

		if (zoomOutHandler) {
			setZoomOutHandler(false);
			zoomOut(containerRef.current, panZoomRef.current);
		}

		if (zoomInHandler) {
			setZoomInHandler(false);
			zoomIn(containerRef.current, panZoomRef.current);
		}

		handlers.current = {
			moveController: (where) =>
				moveController(
					containerRef.current,
					navigationController,
					where
				),
			zoomIn: () => zoomIn(containerRef.current, panZoomRef.current),
			zoomOut: () => zoomOut(containerRef.current, panZoomRef.current),
		}

		const clickAction = (updatedPin) =>
			setShowTooltip({
				details: {
					cx: updatedPin.cx,
					cy: updatedPin.cy,
					id: updatedPin.id,
					label: updatedPin.label,
					linked_to_sku: updatedPin.linked_to_sku,
					quantity: updatedPin.quantity,
					sku: updatedPin.sku,
				},
				tooltip: true,
			});

		function dragstarted() {
			select(this).raise().classed('active', true);
		}

		function dragged() {
			select(this).attr(
				'transform',
				'translate(' + event.x + ',' + event.y + ')'
			);
		}

		function dragended() {
			const current = select(this);
			const newPos = current._groups[0][0].attributes;
			const beSure = [...newPos];
			const updatedPin = {};

			select(this).classed('active', false);
			PIN_ATTRIBUTES.map((element) => {
				beSure.filter((attr) => {
					if (attr.name === element) {
						if (element === 'cx') {
							updatedPin[`${attr.name}`] = parseFloat(event.x);
						}
						else if (element === 'cy') {
							updatedPin[`${attr.name}`] = parseFloat(event.y);
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
						Math.abs(element.cx - updatedPin.cx) < 15 &&
						Math.abs(element.cy - updatedPin.cy) < 15
					) {
						clickAction(updatedPin);
					}

					return updatedPin;
				}
				else {
					return element;
				}
			});
			setCpins(newState);
		}

		const dragHandler = drag()
			.on('start', dragstarted)
			.on('drag', dragged)
			.on('end', dragended);

		const addPin = () => {
			setCpins(
				cPins.concat({
					cx: 50,
					cy: 50,
					draggable: true,
					fill: '#' + addNewPinState.fill,
					id: cPins.length,
					label: 'new' + cPins.length,
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
				containerRef.current.selectAll('.circle_pin').remove();
			}
			catch (error) {
				return;
			}

			const cont = containerRef.current
				.selectAll('g')
				.data(cPins)
				.enter()
				.append('g')
				.attr(
					'transform',
					(attr) => 'translate(' + attr.cx + ',' + attr.cy + ')'
				)
				.attr('cx', (attr) => attr.cx)
				.attr('cy', (attr) => attr.cy)
				.attr('id', (attr) => attr.id)
				.attr('label', (attr) => attr.label)
				.attr('fill', (attr) => attr.fill)
				.attr('linked_to_sku', (attr) => attr.linked_to_sku)
				.attr('quantity', (attr) => attr.quantity)
				.attr('r', (attr) => attr.r)
				.attr('sku', (attr) => attr.sku)
				.attr('id', (attr) => attr.id)
				.attr('class', 'circle_pin')
				.attr('draggable', (attr) => (attr.draggable ? true : false))
				.call(dragHandler);

			cont.append('circle')
				.attr('r', (attr) => attr.r)
				.attr('fill', () => '#ffffff')
				.attr('r', (attr) => attr.r)
				.attr('stroke', (attr) => attr.fill)
				.attr('stroke-width', 2);

			cont.append('text')
				.text((attr) => attr.label)
				.attr('font-size', (attr) => attr.r)
				.attr('text-anchor', 'middle')
				.attr('fill', '#000000')
				.attr('alignment-baseline', 'central');
		}

		select('#newPin').on('click', handleAddPin);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		resetZoom,
		selectedOption,
		setResetZoom,
		changedScale,
		zoomOutHandler,
		zoomInHandler,
		enablePanZoom,
		execZoomIn,
		imageSettings,
		navigationController,
		setChangedScale,
		setSelectedOption,
		setZoomInHandler,
		setZoomOutHandler,
	]);

	const diagramStyle = {
		height: `${imageSettings.height}`,
		width: `${imageSettings.width}`,
	};

	return (
		<div className="diagram-pins-container" style={diagramStyle}>
			<svg
				height={imageSettings.height}
				ref={svgRef}
				width={imageSettings.width}
			>
				<g
					data-testid={`${namespace}container`}
					id={`${namespace}container`}
					transform="translate(0,0) scale(1)"
				>
					<image
						height={imageSettings.height}
						href={imageURL}
					></image>
				</g>
			</svg>

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
	setZoomInHandler: PropTypes.func,
	setZoomOutHandler: PropTypes.func,
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