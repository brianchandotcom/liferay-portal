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
import {openToast} from 'frontend-js-web';
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
import StorefrontTooltipContent from '../components/StorefrontTooltipContent';
import TooltipProvider from '../components/TooltipProvider';
import {DIAGRAM_TABLE_EVENTS} from '../utilities/constants';
import {
	deleteMappedProduct,
	getMappedProducts,
	saveMappedProduct,
} from '../utilities/data';
import {formatMappedProduct} from '../utilities/index';
import D3Handler from './D3Handler';

import '../../css/diagram.scss';
import {useEscapeKeyHandler} from '../utilities/hooks';

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
	productBaseURL,
	productId,
}) {
	const [mappedProducts, setMappedProducts] = useState(null);
	const [tooltipData, setTooltipData] = useState(false);
	const [currentZoom, setCurrentZoom] = useState(1);
	const [expanded, setExpanded] = useState(false);
	const [labels, setLabels] = useState([]);
	const [selectedText, setSelectedText] = useState(null);
	const [highlightedTexts, setHighlightedTexts] = useState([]);
	const chartInstanceRef = useRef(null);
	const commerceAccount = useCommerceAccount({id: initialAccountId});
	const commerceCart = useCommerceCart({id: initialCartId});
	const isMounted = useIsMounted();
	const svgRef = useRef(null);
	const wrapperRef = useRef(null);
	const zoomHandlerRef = useRef(null);

	useEscapeKeyHandler(
		expanded,
		!!tooltipData,
		() => setExpanded(false),
		() => setTooltipData(null)
	);

	useEffect(() => {
		getMappedProducts(
			productId,
			!isAdmin && channelId,
			'',
			1,
			200,
			commerceAccount.id
		).then(({items}) => setMappedProducts(items));
	}, [channelId, isAdmin, productId, commerceAccount]);

	useEffect(() => {
		chartInstanceRef.current?.updatePins(mappedProducts);
	}, [mappedProducts]);

	useEffect(() => {
		if (!tooltipData) {
			setSelectedText(null);
		}
	}, [tooltipData]);

	const handleClickOnLabel = useCallback(
		({target}) => {
			const sequence = target.textContent;

			const mappedProduct = mappedProducts.find(
				(mappedProduct) => mappedProduct.sequence === sequence
			);

			const selectedPin = mappedProduct ? {mappedProduct} : null;

			setTooltipData({selectedPin, sequence, target});
			setSelectedText(target);
		},
		[mappedProducts]
	);

	const handleMouseEnterOnLabel = useCallback(
		({target}) => {
			const hightlightedLabels = labels.filter(
				(label) => label.textContent === target.textContent
			);

			setHighlightedTexts(hightlightedLabels);
		},
		[labels]
	);

	const handleMouseLeaveOnLabel = () => {
		setHighlightedTexts([]);
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
	}, [handleClickOnLabel, handleMouseEnterOnLabel, labels]);

	useEffect(() => {
		function handleSelectPinByTable({diagramProductId, product}) {
			if (diagramProductId === productId) {
				const pinNode = labels.find(
					(label) => label.textContent === product.sequence
				);
				const selectedProduct = mappedProducts.find(
					(mappedProduct) =>
						mappedProduct.sequence === product.sequence
				);

				if (selectedProduct) {
					setHighlightedTexts([]);

					chartInstanceRef.current.recenterOnPin(pinNode).then(() => {
						setTooltipData({
							selectedPin: {mappedProduct: selectedProduct},
							sequence: pinNode.textContent,
							target: pinNode,
						});
						setSelectedText(pinNode);
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
				setHighlightedTexts([]);
			}
		}

		function handlePinsUpdatedByTable() {
			getMappedProducts(
				productId,
				!isAdmin && channelId,
				'',
				1,
				200,
				commerceAccount.id
			).then(({items}) => setMappedProducts(items));
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
		Liferay.on(
			DIAGRAM_TABLE_EVENTS.TABLE_UPDATED,
			handlePinsUpdatedByTable
		);

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
				DIAGRAM_TABLE_EVENTS.TABLE_UPDATED,
				handlePinsUpdatedByTable
			);
		};
	}, [
		channelId,
		commerceAccount,
		isAdmin,
		handleMouseEnterOnLabel,
		labels,
		mappedProducts,
		productId,
	]);

	useLayoutEffect(() => {
		chartInstanceRef.current = new D3Handler(
			svgRef.current,
			imageURL,
			isAdmin,
			pinsCSSSelectors,
			setLabels,
			(scale) => {
				setTooltipData(null);

				setCurrentZoom(scale);
			},
			zoomHandlerRef.current
		);
	}, [imageURL, isAdmin, pinsCSSSelectors]);

	const handleMappedProductSave = (
		type,
		quantity,
		sequence,
		linkedProduct
	) => {
		const linkedProductDetails = formatMappedProduct(
			type,
			quantity,
			sequence,
			linkedProduct
		);

		const update = Boolean(tooltipData.selectedPin?.mappedProduct.id);

		return saveMappedProduct(
			update ? tooltipData.selectedPin.mappedProduct.id : null,
			linkedProductDetails,
			sequence,
			productId
		)
			.then((newMappedProduct) => {
				if (!isMounted()) {
					return;
				}

				setMappedProducts((mappedProducts) => {
					const updatedMappedProducts = mappedProducts.map(
						(mappedProduct) =>
							mappedProduct.sequence === newMappedProduct.sequence
								? {
										...mappedProduct,
										mappedProduct:
											newMappedProduct.mappedProduct,
										quantity: newMappedProduct.quantity,
								  }
								: mappedProduct
					);

					return update
						? updatedMappedProducts.map((updatedMappedProduct) =>
								updatedMappedProduct.id === newMappedProduct.id
									? newMappedProduct
									: updatedMappedProduct
						  )
						: [...updatedMappedProducts, newMappedProduct];
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

	const handleMappedProductDelete = () => {
		return deleteMappedProduct(tooltipData.selectedPin.mappedProduct.id)
			.then(() => {
				if (!isMounted()) {
					return;
				}

				setMappedProducts((mappedProducts) =>
					mappedProducts.filter(
						(mappedProduct) =>
							mappedProduct.id !==
							tooltipData.selectedPin.mappedProduct.id
					)
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
							onDelete={handleMappedProductDelete}
							onSave={handleMappedProductSave}
							productId={productId}
							readOnlySequence={true}
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
	productBaseURL: PropTypes.string,
	productId: PropTypes.string.isRequired,
};

export default Diagram;
