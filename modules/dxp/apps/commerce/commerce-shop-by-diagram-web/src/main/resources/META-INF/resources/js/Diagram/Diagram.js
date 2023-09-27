/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {
	useCommerceAccount,
	useCommerceCart,
} from 'commerce-frontend-js/utilities/hooks';
import {debounce, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useLayoutEffect, useRef, useState} from 'react';

import AdminTooltipContent from '../components/AdminTooltipContent';
import DiagramFooter from '../components/DiagramFooter';
import DiagramHeader from '../components/DiagramHeader';
import StorefrontTooltipContent from '../components/StorefrontTooltipContent';
import TooltipProvider from '../components/TooltipProvider';
import {PINS_RADIUS} from '../utilities/constants';
import {
	deletePin,
	loadPins,
	savePin,
	updateGlobalPinsRadius,
} from '../utilities/data';
import {formatMappedProduct} from '../utilities/index';
import D3Handler from './D3Handler';
import useTableHandlers from './useTableHandlers';

import '../../css/diagram.scss';
import {useEscapeKeyHandler} from '../utilities/hooks';

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
	const isMounted = useIsMounted();

	useEscapeKeyHandler(
		expanded,
		tooltipData,
		() => setExpanded(false),
		() => {
			setTooltipData(null);

			chartInstanceRef.current?.resetActivePinsState();
		}
	);

	useTableHandlers(chartInstanceRef, productId, () =>
		loadPins(productId, !isAdmin && channelId, commerceAccount.id).then(
			setPins
		)
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
		loadPins(productId, !isAdmin && channelId, commerceAccount.id).then(
			setPins
		);
	}, [channelId, isAdmin, productId, commerceAccount]);

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

	const handlePinDelete = () => {
		return deletePin(tooltipData.selectedPin.id)
			.then(() => {
				if (!isMounted()) {
					return;
				}

				setPins((pins) =>
					pins.filter((pin) => pin.id !== tooltipData.selectedPin.id)
				);

				openToast({
					message: Liferay.Language.get('pin-deleted'),
					type: 'success',
				});
			})
			.catch((error) => {
				openToast({
					message: error.message || error,
					type: 'danger',
				});

				throw error;
			});
	};

	const handlePinSave = (type, quantity, sequence, linkedProduct) => {
		const update = Boolean(tooltipData.selectedPin?.id);

		const linkedProductDetails = formatMappedProduct(
			type,
			quantity,
			sequence,
			linkedProduct
		);

		return savePin(
			update ? tooltipData.selectedPin.id : null,
			linkedProductDetails,
			sequence,
			tooltipData.x,
			tooltipData.y,
			productId
		)
			.then((newPin) => {
				if (!isMounted()) {
					return;
				}

				setPins((pins) => {
					const updatedPins = pins.map((pin) =>
						pin.sequence === newPin.sequence
							? {
									...pin,
									mappedProduct: newPin.mappedProduct,
									quantity: newPin.quantity,
							  }
							: pin
					);

					return update
						? updatedPins.map((updatedPin) =>
								updatedPin.id === newPin.id
									? newPin
									: updatedPin
						  )
						: [...updatedPins, newPin];
				});

				openToast({
					message: update
						? Liferay.Language.get('pin-updated')
						: Liferay.Language.get('pin-created'),
					type: 'success',
				});
			})
			.catch((error) => {
				openToast({
					message: error.message || error,
					type: 'danger',
				});

				throw error;
			});
	};

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
							onDelete={handlePinDelete}
							onSave={handlePinSave}
							productId={productId}
							readOnlySequence={false}
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
