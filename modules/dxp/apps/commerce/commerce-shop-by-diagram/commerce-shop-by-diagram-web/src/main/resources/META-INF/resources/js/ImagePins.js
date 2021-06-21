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

import {drag, event, select, zoom, zoomIdentity, zoomTransform} from 'd3';
import PropTypes from 'prop-types';
import React, { useLayoutEffect, useRef, useState} from 'react';
import NavigationButtons from './NavigationButtons';
import {moveController, namespace, zoomIn, zoomOut} from './NavigationsUtils';
import ZoomController from './ZoomController';
import AdminTooltip from './AdminTooltip';

const ImagePins = ({
	addNewPinState,
	addPinHandler,
	cPins,
	changedScale,
	enablePanZoom,
	execZoomIn,
	handleAddPin,
	imageSettings,
	imageURL,
	navigationController,
	removePinHandler,
	resetZoom,
	selectedOption,
	setAddPinHandler,
	setChangedScale,
	setCpins,
	setRemovePinHandler,
	setResetZoom,
	setSelectedOption,
	setShowTooltip,
	setZoomInHandler,
	setZoomOutHandler,
	showTooltip,
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

		handlers.current = {
			moveController: (where) =>
				moveController(
					containerRef.current,
					navigationController,
					where
				),
			zoomIn: () => zoomIn(containerRef.current, panZoomRef.current),
			zoomOut: () => zoomOut(containerRef.current, panZoomRef.current),
		};

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

		function dragended(d) {
			const current = select(this);
			const newPos = current._groups[0][0].attributes;
			const beSure = [...newPos];
			const updatedPin = {};
			const arr = [
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
			select(this).classed('active', false);
			arr.map((el) => {
				beSure.filter((d) => {
					if (d.name === el) {
						if (el === 'cx') {
							updatedPin[`${d.name}`] = parseFloat(event.x);
						}
						else if (el === 'cy') {
							updatedPin[`${d.name}`] = parseFloat(event.y);
						}
						else if (
							el === 'quantity' ||
							el === 'r' ||
							el === 'id'
						) {
							updatedPin[`${d.name}`] = parseInt(d.value, 10);
						}
						else if (el === 'draggable') {
							updatedPin[`${d.name}`] = d.value ? true : false;
						}
						else {
							updatedPin[`${d.name}`] = d.value;
						}
					}
				});
			});
			const newState = cPins.map((element) => {
				if (element.id === updatedPin.id) {
					if (Math.abs(element.cx - updatedPin.cx) < 15 && Math.abs(element.cy - updatedPin.cy) < 15) {
						clickAction(updatedPin)
					}
 
					return updatedPin;
				}
				else {
					return element;
				}
			});
			setCpins(newState);

			// current.attr(
			// 	'transform',
			// 	'translate(' + (d.cx = event.x) + ',' + (d.cy = event.y) + ')'
			// );
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
			setCpins(cPins.filter((el) => el.id !== id));
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
				container.current.selectAll('.circle_pin').remove();
			}
			catch (err) {
				console.log(err);
			}


			const cont = container.current
				.selectAll('g')
				.data(cPins)
				.enter()
				.append('g')
				.attr(
					'transform',
					(d) => 'translate(' + d.cx + ',' + d.cy + ')'
				)
				.attr('cx', (d) => d.cx)
				.attr('cy', (d) => d.cy)
				.attr('id', (d) => d.id)
				.attr('label', (d) => d.label)
				.attr('fill', (d) => d.fill)
				.attr('linked_to_sku', (d) => d.linked_to_sku)
				.attr('quantity', (d) => d.quantity)
				.attr('r', (d) => d.r)
				.attr('sku', (d) => d.sku)
				.attr('id', (d) => d.id)
				.attr('class', 'circle_pin')
				.attr('draggable', (d) => (d.draggable ? true : false))
				.call(dragHandler);

			cont.append('circle')
				.attr('r', (d) => d.r)
				.attr('fill', (d) => '#ffffff')
				.attr('r', (d) => d.r)
				.attr('stroke', (d) => d.fill)
				.attr('stroke-width', 2);

			cont.append('text')
				.text((d) => d.label)
				.attr('font-size', (d) => d.r)
				.attr('text-anchor', 'middle')
				.attr('fill', '#000000')
				.attr('alignment-baseline', 'central');
		}

		select('#newPin').on('click', handleAddPin);

	}, [
		addPinHandler,
		removePinHandler,
		changedScale, 
		cPins, 
		execZoomIn,
		resetZoom, 
		selectedOption, 
		setResetZoom, 
		zoomOutHandler,
		zoomInHandler, 
		enablePanZoom, 
		imageSettings, 
		navigationController, 
		setChangedScale, 
		setSelectedOption, 
		setZoomInHandler, 
		setZoomOutHandler,
		handleAddPin, 
		setShowTooltip, 
		setCpins, 
		addNewPinState, 
		setRemovePinHandler, 
		setAddPinHandler
	]);

	return (
		<div className="diagram-pins-container" style={{
			height: `${imageSettings.height}`,
			width: `${imageSettings.width}`,
		}}>
			<svg
				height={imageSettings.height}
				ref={svgRef}
				width={imageSettings.width}
			>
				<g
					data-testid={namespace + 'container'}
					id={namespace + 'container'}
					transform="translate(0,0) scale(1)"
				>
					<image
						height={imageSettings.height}
						href={imageURL}
					></image>
				</g>
			</svg>

			{showTooltip.tooltip && (
				<AdminTooltip
					removePinHandler={removePinHandler}
					setRemovePinHandler={setRemovePinHandler}
					setShowTooltip={setShowTooltip}
					showTooltip={showTooltip}
				/>
			)}

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
