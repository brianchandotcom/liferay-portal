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

import ClayEmptyState from '@clayui/empty-state';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import AddToCartButton from 'commerce-frontend-js/components/add_to_cart/AddToCartButton';
import InfiniteScroller from 'commerce-frontend-js/components/infinite_scroller/InfiniteScroller';
import {
	useCommerceAccount,
	useCommerceCart,
} from 'commerce-frontend-js/utilities/hooks';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {DIAGRAM_EVENTS, DIAGRAM_TABLE_EVENTS} from '../utilities/constants';
import {deleteMappedProduct, getMappedProducts} from '../utilities/data';
import {
	formatInitialQuantities,
	formatProductOptions,
} from '../utilities/index';
import ManagementBar from './ManagementBar';
import MappedProductRow from './MappedProductRow';
import TableHead from './TableHead';

const PAGE_SIZE = 15;

function formatCpInstances(skusId, products, quantities) {
	return skusId.map((skuId) => {
		const product = products.find((product) => product.skuId === skuId);

		const options = formatProductOptions(
			product.options,
			product.productOptions
		);

		return {
			inCart: false,
			options,
			quantity: quantities[skuId] || product.initialQuantity,
			skuId,
		};
	});
}

function DiagramTable({
	cartId: initialCartId,
	channelGroupId,
	channelId,
	commerceAccountId: initialAccountId,
	commerceCurrencyCode,
	isAdmin,
	orderUUID,
	productId,
}) {
	const [currentPage, setCurrentPage] = useState(1);
	const [loaderActive, setLoaderActive] = useState(true);
	const [lastPage, setLastPage] = useState(null);
	const [query, setQuery] = useState('');
	const [mappedProducts, setMappedProducts] = useState(null);
	const [refreshTrigger, setRefreshTrigger] = useState(false);
	const [selectedSkusId, setSelectedSkusId] = useState([]);
	const [newQuantities, setNewQuantities] = useState({});
	const commerceCart = useCommerceCart({id: initialCartId});
	const commerceAccount = useCommerceAccount({id: initialAccountId});
	const wrapperRef = useRef();

	const handleDiagramUpdated = useCallback(
		({diagramProductId}) => {
			if (diagramProductId === productId) {
				setRefreshTrigger((trigger) => !trigger);

				setCurrentPage(1);
			}
		},
		[productId]
	);

	useEffect(() => {
		Liferay.on(DIAGRAM_EVENTS.DIAGRAM_UPDATED, handleDiagramUpdated);

		return () => {
			Liferay.detach(
				DIAGRAM_EVENTS.DIAGRAM_UPDATED,
				handleDiagramUpdated
			);
		};
	}, [handleDiagramUpdated]);

	useEffect(() => {
		getMappedProducts(
			productId,
			!isAdmin && channelId,
			query,
			currentPage,
			PAGE_SIZE
		).then((data) => {
			setLoaderActive(false);

			const fetchedProducts = formatInitialQuantities(data.items);

			setMappedProducts((mappedProducts) =>
				mappedProducts && currentPage > 1
					? [...mappedProducts, ...fetchedProducts]
					: fetchedProducts
			);

			setLastPage(data.lastPage);
		});
	}, [channelId, currentPage, isAdmin, productId, query, refreshTrigger]);

	const selectableSkusId = (mappedProducts || []).reduce(
		(skusId, product) =>
			product.type === 'sku' &&
			product.availability?.label === 'available'
				? [...skusId, product.skuId]
				: skusId,
		[]
	);

	function handleTitleClicked(product) {
		Liferay.fire(DIAGRAM_TABLE_EVENTS.SELECT_PIN, {
			diagramProductId: productId,
			product,
		});
	}

	function handleMouseEnter(product) {
		Liferay.fire(DIAGRAM_TABLE_EVENTS.HIGHLIGHT_PIN, {
			diagramProductId: productId,
			sequence: product.sequence,
		});
	}

	function handleMouseLeave(product) {
		Liferay.fire(DIAGRAM_TABLE_EVENTS.REMOVE_PIN_HIGHLIGHT, {
			diagramProductId: productId,
			sequence: product.sequence,
		});
	}

	const handleMappedProductDelete = (mappedProductId) => {
		deleteMappedProduct(mappedProductId).then(() => {
			setMappedProducts((mappedProducts) =>
				mappedProducts.filter(
					(mappedProduct) => mappedProduct.id !== mappedProductId
				)
			);

			Liferay.fire(DIAGRAM_TABLE_EVENTS.TABLE_UPDATED, {
				diagramProductId: productId,
			});
		});
	};

	return (
		<div className="shop-by-diagram-table" ref={wrapperRef}>
			<ManagementBar
				updateQuery={(query) => {
					setCurrentPage(1);
					setLoaderActive(true);
					setQuery(query);
					setMappedProducts(null);
				}}
			/>

			{mappedProducts && !mappedProducts.length && !loaderActive && (
				<ClayEmptyState
					className="full-height-content"
					title={Liferay.Language.get('there-are-no-results')}
				/>
			)}

			{loaderActive && (
				<div className="full-height-content">
					<ClayLoadingIndicator />
				</div>
			)}

			{!loaderActive && mappedProducts && !!mappedProducts.length && (
				<InfiniteScroller
					onBottomTouched={() => setCurrentPage(currentPage + 1)}
					scrollCompleted={currentPage >= lastPage}
				>
					<ClayTable borderless>
						<TableHead
							isAdmin={isAdmin}
							selectableSkusId={selectableSkusId}
							selectedSkusId={selectedSkusId}
							setSelectedSkusId={setSelectedSkusId}
						/>

						<ClayTable.Body>
							{Boolean(mappedProducts?.length) &&
								mappedProducts.map((product) => (
									<MappedProductRow
										handleMouseEnter={handleMouseEnter}
										handleMouseLeave={handleMouseLeave}
										handleTitleClicked={handleTitleClicked}
										isAdmin={isAdmin}
										key={product.id}
										onDelete={handleMappedProductDelete}
										product={product}
										quantity={
											newQuantities[product.skuId] ||
											product.initialQuantity
										}
										selectedSkusId={selectedSkusId}
										setNewQuantity={(newQuantity) => {
											setNewQuantities({
												...newQuantities,
												[product.skuId]: newQuantity,
											});
										}}
										setSelectedSkusId={setSelectedSkusId}
									/>
								))}
						</ClayTable.Body>
					</ClayTable>
				</InfiniteScroller>
			)}

			{!isAdmin && (
				<AddToCartButton
					accountId={commerceAccount.id}
					cartId={commerceCart.id}
					cartUUID={orderUUID}
					channel={{
						currencyCode: commerceCurrencyCode,
						groupId: channelGroupId,
						id: channelId,
					}}
					cpInstances={formatCpInstances(
						selectedSkusId,
						mappedProducts,
						newQuantities
					)}
					disabled={!commerceAccount.id || !selectedSkusId.length}
					hideIcon={true}
					onAdd={() => {
						const message =
							selectedSkusId.length === 1
								? Liferay.Language.get(
										'the-product-was-successfully-added-to-the-cart'
								  )
								: Liferay.Util.sub(
										Liferay.Language.get(
											'x-products-were-successfully-added-to-the-cart'
										),
										selectedSkusId.length
								  );

						openToast({
							message,
							type: 'success',
						});
					}}
					settings={{
						alignment: 'full-width',
						buttonText: Liferay.Language.get(
							'add-selected-products-to-the-order'
						),
					}}
				/>
			)}
		</div>
	);
}

DiagramTable.propTypes = {
	cartId: PropTypes.string,
	channelGroupId: PropTypes.string,
	channelId: PropTypes.string,
	commerceAccountId: PropTypes.string,
	commerceCurrencyCode: PropTypes.string,
	isAdmin: PropTypes.bool,
	orderUUID: PropTypes.string,
	productId: PropTypes.string.isRequired,
};

export default DiagramTable;
