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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useLayoutEffect, useRef, useState} from 'react';

import AutomappingHandler from './AutomappingHandler';
import DiagramFooter from './components/DiagramFooter';
import Tooltip from './components/Tooltip';
import {loadPins} from './utilities/data';

import '../../css/diagram.scss';
import Sequence from './components/Sequence';

function DiagramWithAutomapping({imageURL, pinsCSSSelectors, productId}) {
	const svgRef = useRef(null);
	const zoomHandlerRef = useRef(null);
	const chartInstance = useRef(null);
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
		function handleClickOnLabel(event) {
			const sequence = event.target.textContent;

			const selectedPin = pins.find((pin) => pin.sequence === sequence);

			setTooltipData({selectedPin, sequence, x: event.x, y: event.y});
			updateSelectedText(event.target);
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
			<div className="bg-white border-bottom border-top p-2 view-wrapper">
				<svg className="svg-wrapper" ref={svgRef}>
					<g className="zoom-handler" ref={zoomHandlerRef} />
				</svg>
			</div>

			<DiagramFooter
				chartInstance={chartInstance}
				currentZoom={currentZoom}
				expanded={expanded}
				updateCurrentZoom={updateCurrentZoom}
				updateExpanded={updateExpanded}
			/>

			{highlightedText && (
				<Sequence highlighted={true} source={highlightedText} />
			)}

			{selectedText && <Sequence source={selectedText} />}

			{tooltipData && (
				<Tooltip
					closeTooltip={() => setTooltipData(null)}
					expanded={expanded}
					productId={productId}
					readOnlySequence={false}
					updatePins={updatePins}
					{...tooltipData}
				/>
			)}
		</div>
	);
}

DiagramWithAutomapping.propTypes = {
	diagramId: PropTypes.string.isRequired,
	imageURL: PropTypes.string.isRequired,
	isAdmin: PropTypes.bool.isRequired,
	pinsCSSSelectors: PropTypes.array.isRequired,
	productId: PropTypes.string.isRequired,
};

export default DiagramWithAutomapping;
