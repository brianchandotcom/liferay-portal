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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useLayoutEffect, useRef, useState} from 'react';

import DiagramHandler from './DiagramHandler';
import DiagramFooter from './components/DiagramFooter';
import DiagramHeader from './components/DiagramHeader';
import Tooltip from './components/Tooltip';
import {DEFAULT_PINS_RADIUS} from './utilities/constants';
import {loadPins, updateGlobalPinsRadius} from './utilities/data';

import '../../css/diagram.scss';

const debouncedUpdatePinsRadius = debounce(updateGlobalPinsRadius, 800);

function Diagram({
	datasetDisplayId,
	diagramId,
	imageURL,
	namespace,
	pinsRadius: initialPinsRadius,
	productId,
}) {
	const svgRef = useRef(null);
	const zoomHandlerRef = useRef(null);
	const wrapperRef = useRef(null);
	const chartInstance = useRef(null);
	const [pins, updatePins] = useState(null);
	const [pinsRadius, updatePinsRadius] = useState(initialPinsRadius);
	const [tooltipData, setTooltipData] = useState(false);
	const [currentZoom, updateCurrentZoom] = useState(1);
	const [expanded, updateExpanded] = useState(false);
	const pinsRadiusInitialized = useRef(false);

	useEffect(() => {
		if (pinsRadiusInitialized.current) {
			debouncedUpdatePinsRadius(diagramId, pinsRadius, namespace);
		}
		else {
			pinsRadiusInitialized.current = true;
		}
	}, [pinsRadius, diagramId, namespace]);

	useEffect(() => {
		loadPins(productId).then(updatePins);
	}, [productId]);

	useEffect(() => {
		if (pins) {
			chartInstance.current?.updatePins(pins);
		}
	}, [pins]);

	useEffect(() => {
		chartInstance.current?.updatePinsRadius(pinsRadius);
	}, [pinsRadius]);

	useLayoutEffect(() => {
		chartInstance.current = new DiagramHandler(
			svgRef.current,
			zoomHandlerRef.current,
			imageURL,
			updateCurrentZoom,
			setTooltipData
		);

		return () => {
			chartInstance.current.cleanUp();
		};
	}, [imageURL]);

	return (
		<div className={classNames('shop-by-diagram', {expanded})}>
			<DiagramHeader
				pinsRadius={pinsRadius}
				updatePinsRadius={updatePinsRadius}
			/>

			<div
				className="bg-white border-bottom border-top view-wrapper"
				ref={wrapperRef}
			>
				<ClayLoadingIndicator className="svg-loader" />

				<svg className="svg-wrapper" ref={svgRef}>
					<g className="zoom-handler" ref={zoomHandlerRef} />
				</svg>

				{tooltipData && (
					<Tooltip
						closeTooltip={() => setTooltipData(null)}
						containerRef={wrapperRef}
						datasetDisplayId={datasetDisplayId}
						productId={productId}
						readOnlySequence={false}
						updatePins={updatePins}
						{...tooltipData}
					/>
				)}
			</div>

			<DiagramFooter
				chartInstance={chartInstance}
				currentZoom={currentZoom}
				expanded={expanded}
				updateCurrentZoom={updateCurrentZoom}
				updateExpanded={updateExpanded}
			/>
		</div>
	);
}

Diagram.defaultProps = {
	pinsRadius: DEFAULT_PINS_RADIUS,
};

Diagram.propTypes = {
	datasetDisplayId: PropTypes.string,
	diagramId: PropTypes.string.isRequired,
	imageURL: PropTypes.string.isRequired,
	isAdmin: PropTypes.bool.isRequired,
	pinsRadius: PropTypes.number,
	productId: PropTypes.string.isRequired,
};

export default Diagram;
