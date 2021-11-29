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
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useLayoutEffect, useRef, useState} from 'react';

import AdminTooltipContent from '../components/AdminTooltipContent';
import DiagramFooter from '../components/DiagramFooter';
import DiagramHeader from '../components/DiagramHeader';
import StorefrontTooltipContent from '../components/StorefrontTooltipContent';
import TooltipProvider from '../components/TooltipProvider';
import {PINS_RADIUS} from '../utilities/constants';
import {loadPins, updateGlobalPinsRadius} from '../utilities/data';
import D3Handler from './D3Handler';
import useTableHandlers from './useTableHandlers';

import '../../css/diagram.scss';

const debouncedUpdatePinsRadius = debounce(updateGlobalPinsRadius, 800);

function Diagram({
	cartId: initialCartId,
	channelGroupId,
	channelId,
	commerceAccountId: initialAccountId,
	commerceCurrencyCode,
	datasetDisplayId,
	diagramId,
	imageURL,
	isAdmin,
	namespace,
	orderUUID,
	pinsRadius: initialPinsRadius,
	productBaseURL,
	productId,
}) {
	const commerceCart = useCommerceCart({id: initialCartId});
	const commerceAccount = useCommerceAccount({id: initialAccountId});
	const chartInstanceRef = useRef(null);
	const pinsRadiusInitializedRef = useRef(false);
	const svgRef = useRef(null);
	const zoomHandlerRef = useRef(null);
	const [currentZoom, setCurrentZoom] = useState(1);
	const [expanded, setExpanded] = useState(false);
	const [pins, setPins] = useState(null);
	const [dropdownActive, setDropdownActive] = useState(false);
	const [pinsRadius, setPinsRadius] = useState(initialPinsRadius);
	const [tooltipData, setTooltipData] = useState(false);

	useTableHandlers(chartInstanceRef, productId, () =>
		loadPins(productId, !isAdmin && channelId).then(setPins)
	);

	useEffect(() => {
		if (pinsRadiusInitializedRef.current) {
			debouncedUpdatePinsRadius(diagramId, pinsRadius, namespace);
		}
		else {
			pinsRadiusInitializedRef.current = true;
		}
	}, [pinsRadius, diagramId, namespace]);

	useEffect(() => {
		loadPins(productId, !isAdmin && channelId).then(setPins);
	}, [channelId, isAdmin, productId]);

	useEffect(() => {
		if (pins) {
			chartInstanceRef.current?.updatePins(pins);
		}
	}, [pins]);

	useEffect(() => {
		chartInstanceRef.current?.updatePinsRadius(pinsRadius);
	}, [pinsRadius]);

	useLayoutEffect(() => {
		chartInstanceRef.current = new D3Handler(
			isAdmin,
			() => setDropdownActive(false),
			svgRef.current,
			imageURL,
			setTooltipData,
			setCurrentZoom,
			zoomHandlerRef.current
		);

		return () => {
			chartInstanceRef.current.cleanUp();
		};
	}, [imageURL, isAdmin]);

	return (
		<div className={classNames('shop-by-diagram', {expanded})}>
			{isAdmin && (
				<DiagramHeader
					dropdownActive={dropdownActive}
					pinsRadius={pinsRadius}
					setDropdownActive={setDropdownActive}
					updatePinsRadius={setPinsRadius}
				/>
			)}

			<div className="bg-white border-bottom border-top view-wrapper">
				<ClayLoadingIndicator className="svg-loader" />

				<svg className="svg-wrapper" ref={svgRef}>
					<title>{Liferay.Language.get('diagram')}</title>

					<g className="zoom-handler" ref={zoomHandlerRef} />
				</svg>
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
							updatePins={setPins}
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
							productBaseURL={productBaseURL}
							{...tooltipData}
						/>
					)}
				</TooltipProvider>
			)}

			<DiagramFooter
				chartInstance={chartInstanceRef}
				currentZoom={currentZoom}
				expanded={expanded}
				updateCurrentZoom={setCurrentZoom}
				updateExpanded={setExpanded}
			/>
		</div>
	);
}

Diagram.defaultProps = {
	pinsRadius: PINS_RADIUS.DEFAULT,
};

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
	pinsRadius: PropTypes.number,
	productBaseURL: PropTypes.string,
	productId: PropTypes.string.isRequired,
};

export default Diagram;
