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
	moveController,
	namespace,
	zoomIn,
	zoomOut,
} from './NavigationsUtils';
import ZoomController from './ZoomController';

const ImagePins = ({
	changedScale,
	enablePanZoom,
	execZoomIn,
	imageURL,
	imageSettings,
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
			moveController: (where) => moveController(containerRef.current, navigationController, where),
			zoomIn: () => zoomIn(containerRef.current, panZoomRef.current),
			zoomOut: () => zoomOut(containerRef.current, panZoomRef.current),
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
				ref={svgRef}
				width={imageSettings.width}
			>
				<g
					data-testid={namespace + "container"}
					id={namespace + "container"}
					transform="translate(0,0) scale(1)"
				>
					<image height={imageSettings.height} href={imageURL}></image>
				</g>
			</svg>

			{navigationController.enable && (
				<NavigationButtons
					moveController={(where) => handlers.current?.moveController(where)}
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
