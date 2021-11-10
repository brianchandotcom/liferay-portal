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
import {
	useCommerceAccount,
	useCommerceCart,
} from 'commerce-frontend-js/utilities/hooks';
import PropTypes from 'prop-types';
import React, {
	useCallback,
	useEffect,
	useLayoutEffect,
	useRef,
	useState,
} from 'react';

import AdminTooltipContent from '../components/AdminTooltipContent';
import DiagramFooter from '../components/DiagramFooter';
import Sequence from '../components/Sequence';
import {DIAGRAM_TABLE_EVENTS} from '../utilities/constants';
import {loadPins} from '../utilities/data';
import D3Handler from './D3Handler';

import '../../css/diagram.scss';
import StorefrontTooltipContent from '../components/StorefrontTooltipContent';
import TooltipProvider from '../components/TooltipProvider';

function Diagram({
	cartId: initialCartId,
	channelGroupId,
	channelId,
	commerceAccountId: initialAccountId,
	commerceCurrencyCode,
	datasetDisplayId,
	imageURL,
	isAdmin,
	orderUUID,
	pinsCSSSelectors,
	productId,
}) {
	const commerceCart = useCommerceCart({id: initialCartId});
	const commerceAccount = useCommerceAccount({id: initialAccountId});
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
	const [highlightedTexts, updateHighlightedTexts] = useState([]);

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

	const handleClickOnLabel = useCallback(
		({target}) => {
			const sequence = target.textContent;

			const selectedPin = pins.find((pin) => pin.sequence === sequence);

			setTooltipData({selectedPin, sequence, target});
			updateSelectedText(target);
		},
		[pins]
	);

	const handleMouseEnterOnLabel = useCallback(
		({target}) => {
			const hightlightedLabels = labels.filter(
				(label) => label.textContent === target.textContent
			);

			updateHighlightedTexts(hightlightedLabels);
		},
		[labels]
	);

	const handleMouseLeaveOnLabel = () => {
		updateHighlightedTexts([]);
	};

	useEffect(() => {
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
	}, [handleClickOnLabel, handleMouseEnterOnLabel, labels, pins]);

	useEffect(() => {
		function handleSelectPinByTable({diagramProductId, product}) {
			if (diagramProductId === productId) {
				const pinNode = labels.find(
					(label) => label.textContent === product.sequence
				);
				const selectedPin = pins.find(
					(pin) => pin.sequence === product.sequence
				);

				if (selectedPin) {
					updateHighlightedTexts([]);

					chartInstance.current.recenterOnPin(pinNode).then(() => {
						setTooltipData({
							selectedPin,
							sequence: pinNode.textContent,
							target: pinNode,
						});
						updateSelectedText(pinNode);
					});
				}
			}
		}

		function handlePinHighlightByTable({diagramProductId, sequence}) {
			if (diagramProductId === productId) {
				handleMouseEnterOnLabel({target: {textContent: sequence}});
			}
		}

		function handleRemovePinHighlightByTable({diagramProductId}) {
			if (diagramProductId === productId) {
				updateHighlightedTexts([]);
			}
		}

		function handlePinsUpdatedByTable() {
			loadPins(productId).then(updatePins);
		}

		Liferay.on(DIAGRAM_TABLE_EVENTS.SELECT_PIN, handleSelectPinByTable);
		Liferay.on(
			DIAGRAM_TABLE_EVENTS.HIGHLIGHT_PIN,
			handlePinHighlightByTable
		);
		Liferay.on(
			DIAGRAM_TABLE_EVENTS.REMOVE_PIN_HIGHLIGHT,
			handleRemovePinHighlightByTable
		);
		Liferay.on(DIAGRAM_TABLE_EVENTS.PINS_UPDATED, handlePinsUpdatedByTable);

		return () => {
			Liferay.detach(
				DIAGRAM_TABLE_EVENTS.SELECT_PIN,
				handleSelectPinByTable
			);
			Liferay.detach(
				DIAGRAM_TABLE_EVENTS.HIGHLIGHT_PIN,
				handlePinHighlightByTable
			);
			Liferay.detach(
				DIAGRAM_TABLE_EVENTS.REMOVE_PIN_HIGHLIGHT,
				handleRemovePinHighlightByTable
			);
			Liferay.detach(
				DIAGRAM_TABLE_EVENTS.PINS_UPDATED,
				handlePinsUpdatedByTable
			);
		};
	}, [handleMouseEnterOnLabel, labels, pins, productId]);

	useLayoutEffect(() => {
		chartInstance.current = new D3Handler(
			svgRef.current,
			imageURL,
			pinsCSSSelectors,
			updateLabels,
			(scale) => {
				setTooltipData(null);

				updateCurrentZoom(scale);
			},
			zoomHandlerRef.current
		);
	}, [imageURL, pinsCSSSelectors]);

	return (
		<div className={classNames('shop-by-diagram', {expanded})}>
			<div
				className="bg-white border-bottom view-wrapper"
				ref={wrapperRef}
			>
				<ClayLoadingIndicator className="svg-loader" />

				<svg className="svg-wrapper" ref={svgRef}>
					<g className="zoom-handler" ref={zoomHandlerRef} />
				</svg>

				<div className="sequences-wrapper">
					{highlightedTexts.map((highlightedText) => (
						<Sequence
							containerRef={wrapperRef}
							highlighted={true}
							key={Array.from(
								highlightedText.parentNode.children
							).indexOf(highlightedText)}
							target={highlightedText}
						/>
					))}

					{selectedText && (
						<Sequence
							containerRef={wrapperRef}
							target={selectedText}
						/>
					)}
				</div>
			</div>

			{tooltipData && (
				<TooltipProvider
					closeTooltip={() => setTooltipData(null)}
					target={tooltipData.target}
				>
					{isAdmin ? (
						<AdminTooltipContent
							closeTooltip={() => setTooltipData(null)}
							datasetDisplayId={datasetDisplayId}
							productId={productId}
							readOnlySequence={false}
							updatePins={updatePins}
							{...tooltipData}
						/>
					) : (
						<StorefrontTooltipContent
							accountId={commerceAccount.id}
							cartId={commerceCart.id}
							channelGroupId={channelGroupId}
							channelId={channelId}
							currencyCode={commerceCurrencyCode}
							orderUUID={orderUUID}
							{...tooltipData}
						/>
					)}
				</TooltipProvider>
			)}

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

Diagram.propTypes = {
	cartId: PropTypes.string,
	channelGroupId: PropTypes.string,
	channelId: PropTypes.string,
	commerceAccountId: PropTypes.string,
	commerceCurrencyCode: PropTypes.string,
	datasetDisplayId: PropTypes.string,
	diagramId: PropTypes.string.isRequired,
	imageURL: PropTypes.string.isRequired,
	isAdmin: PropTypes.bool.isRequired,
	orderUUID: PropTypes.string,
	pinsCSSSelectors: PropTypes.array.isRequired,
	productId: PropTypes.string.isRequired,
};

export default Diagram;
