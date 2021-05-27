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

import {event, select, zoom, zoomIdentity, zoomTransform} from 'd3';
import PropTypes from 'prop-types';
import React, {useLayoutEffect, useRef} from 'react';

import NavigationButtons from './NavigationButtons';
import {
	moveDown,
	moveLeft,
	moveRight,
	moveUp,
	zoomIn,
	zoomOut,
} from './NavigationsUtils';
import ZoomController from './ZoomController';

const ImagePins = ({
	changedScale,
	enablePanZoom,
	execZoomIn,
	image,
	imageSettings,
	navigationController,
	resetZoom,
	selectedOption,
	setChangedScale,
	setResetZoom,
	setSelectedOption,
	setZoomInHandler,
	setZoomOutHandler,
	spritemap,
	zoomController,
	zoomInHandler,
	zoomOutHandler,
}) => {
	const handlers = useRef();
	const container = useRef();
	const panZoom = useRef();

	const svg = useRef(null);

	useLayoutEffect(() => {
		container.current = select('g#container');
		panZoom.current = zoom()
			.scaleExtent([0.5, 40])
			.on('zoom', () => {
				container.current.attr('transform', event.transform);
			});

		if (enablePanZoom) {
			container.current.call(panZoom.current);
		}

		if (resetZoom) {
			setResetZoom(false);
			container.current
				.transition()
				.duration(700)
				.call(
					panZoom.current.transform,
					zoomIdentity,
					zoomTransform(container.current.node()).invert([
						imageSettings.width,
						imageSettings.height,
					])
				);

			setSelectedOption(1);
		}

		if (changedScale) {
			setChangedScale(false);
			const imageInfos = container.current.node().getBBox();
			container.current
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
			zoomOut(container.current, panZoom.current);
		}

		if (zoomInHandler) {
			setZoomInHandler(false);
			zoomIn(container.current, panZoom.current);
		}

		handlers.current = {
			moveDown: () => moveDown(container.current, navigationController),
			moveLeft: () => moveLeft(container.current, navigationController),
			moveRight: () => moveRight(container.current, navigationController),
			moveUp: () => moveUp(container.current, navigationController),
			zoomIn: () => zoomIn(container.current, panZoom.current),
			zoomOut: () => zoomOut(container.current, panZoom.current),
		};
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
				ref={svg}
				width={imageSettings.width}
			>
				<g
					data-testid="container"
					id="container"
					transform="translate(0,0) scale(1)"
				>
					<image height={imageSettings.height} href={image}></image>
				</g>
			</svg>

			{navigationController.enable && (
				<NavigationButtons
					moveDown={() => handlers.current?.moveDown()}
					moveLeft={() => handlers.current?.moveLeft()}
					moveRight={() => handlers.current?.moveRight()}
					moveUp={() => handlers.current?.moveUp()}
					position={navigationController.position}
					spritemap={spritemap}
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
