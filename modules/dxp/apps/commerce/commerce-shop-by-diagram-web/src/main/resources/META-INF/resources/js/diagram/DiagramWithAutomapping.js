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
import PropTypes from 'prop-types';
import React, {useEffect, useLayoutEffect, useRef, useState} from 'react';

import AutomappingHandler from './AutomappingHandler';
import DiagramFooter from './components/DiagramFooter';
import Sequence from './components/Sequence';
import Tooltip from './components/Tooltip';
import {loadPins} from './utilities/data';

import '../../css/diagram.scss';
function DiagramWithAutomapping({
	datasetDisplayId,
	imageURL,
	pinsCSSSelectors,
	productId,
}) {
	const chartInstance = useRef(null);
	const svgRef = useRef(null);
	const wrapperRef = useRef(null);
	const zoomHandlerRef = useRef(null);
	const [pins, updatePins] = useState(null);
	const [tooltipData, setTooltipData] = useState(false);
	const [currentZoom, updateCurrentZoom] = useState(1);
	const [expanded, updateExpanded] = useState(false);
	const [labels, updateLabels] = useState([]);
	const [selectedText, updateSelectedText] = useState(null);
	const [highlightedText, updateHighlightedText] = useState(null);

	useEffect(() => {
		loadPins(productId).then(updatePins);
	}, [productId]);

	useEffect(() => {
		chartInstance.current?.updatePins(pins);
	}, [pins]);

	useEffect(() => {
		if (!tooltipData) {
			updateSelectedText(null);
		}
	}, [tooltipData]);

	useEffect(() => {
		function handleClickOnLabel({target}) {
			const sequence = target.textContent;

			const selectedPin = pins.find((pin) => pin.sequence === sequence);

			setTooltipData({selectedPin, sequence, target});
			updateSelectedText(target);
		}

		function handleMouseEnterOnLabel(event) {
			updateHighlightedText(event.target);
		}

		function handleMouseLeaveOnLabel() {
			updateHighlightedText(null);
		}

		labels.forEach((label) => {
			label.addEventListener('click', handleClickOnLabel);
			label.addEventListener('mouseenter', handleMouseEnterOnLabel);
			label.addEventListener('mouseleave', handleMouseLeaveOnLabel);
		});

		return () => {
			labels.forEach((label) => {
				label.removeEventListener('click', handleClickOnLabel);
				label.removeEventListener(
					'mouseenter',
					handleMouseEnterOnLabel
				);
				label.removeEventListener(
					'mouseleave',
					handleMouseLeaveOnLabel
				);
			});
		};
	}, [labels, pins]);

	useLayoutEffect(() => {
		chartInstance.current = new AutomappingHandler(
			svgRef.current,
			zoomHandlerRef.current,
			imageURL,
			pinsCSSSelectors,
			updateLabels,
			(scale) => {
				setTooltipData(null);

				updateCurrentZoom(scale);
			}
		);
	}, [imageURL, pinsCSSSelectors]);

	return (
		<div className={classNames('shop-by-diagram', {expanded})}>
			<div
				className="bg-white border-bottom border-top view-wrapper"
				ref={wrapperRef}
			>
				<ClayLoadingIndicator className="svg-loader" />

				<svg className="svg-wrapper" ref={svgRef}>
					<g className="zoom-handler" ref={zoomHandlerRef} />
				</svg>

				{highlightedText && (
					<Sequence
						containerRef={wrapperRef}
						highlighted={true}
						target={highlightedText}
					/>
				)}

				{selectedText && (
					<Sequence containerRef={wrapperRef} target={selectedText} />
				)}

				{tooltipData && (
					<Tooltip
						closeTooltip={() => setTooltipData(null)}
						containerRef={wrapperRef}
						datasetDisplayId={datasetDisplayId}
						productId={productId}
						readOnlySequence={true}
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

DiagramWithAutomapping.propTypes = {
	datasetDisplayId: PropTypes.string,
	diagramId: PropTypes.string.isRequired,
	imageURL: PropTypes.string.isRequired,
	isAdmin: PropTypes.bool.isRequired,
	pinsCSSSelectors: PropTypes.array.isRequired,
	productId: PropTypes.string.isRequired,
};

export default DiagramWithAutomapping;
