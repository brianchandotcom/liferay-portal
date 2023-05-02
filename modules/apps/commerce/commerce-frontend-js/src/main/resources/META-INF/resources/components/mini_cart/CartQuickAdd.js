/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {addToCart} from '../add_to_cart/data';
import MiniCartContext from './MiniCartContext';
import {getCorrectedQuantity} from './util/index';

const CHANNEL_RESOURCE_ENDPOINT =
	'/o/headless-commerce-delivery-catalog/v1.0/channels';

const ProductAutocompleteList = ({onItemClick, sourceItems}) => (
	<ClayDropDown.ItemList>
		{sourceItems.map((product) => {
			const {id, label, value} = product;

			return (
				<ClayDropDown.Item
					key={id}
					onClick={() => onItemClick(product)}
				>
					<div className="autofit-row autofit-row-center">
						<div className="autofit-col mr-3 w-25">{value}</div>

						<span className="ml-2 text-truncate-inline">
							<span className="text-truncate">{label}</span>
						</span>
					</div>
				</ClayDropDown.Item>
			);
		})}
	</ClayDropDown.ItemList>
);

export default function CartQuickAdd() {
	const {cartState, setCartState} = useContext(MiniCartContext);

	const [formattedProducts, setFormattedProducts] = useState([]);
	const [productsQuery, setProductsQuery] = useState('');
	const [quantityError, setQuantityError] = useState(false);
	const [quickAddToCartError, setQuickAddToCartError] = useState(false);
	const [selectedProducts, setSelectedProducts] = useState([]);
	const [productsWithOptions, setProductsWithOptions] = useState([]);

	const {cartItems = [], channel} = cartState;
	const accountId = cartState.accountId;
	const channelId = channel.channel.id;

	useEffect(() => {
		const productsApiURL = new URL(
			`${themeDisplay.getPathContext()}${CHANNEL_RESOURCE_ENDPOINT}/${channelId}/products?accountId=${accountId}&nestedFields=skus&pageSize=-1&skus.accountId=${accountId}`,
			themeDisplay.getPortalURL()
		);

		fetch(productsApiURL.toString())
			.then((response) => response.json())
			.then((availableProducts) => {
				const formattedProducts = [];

				availableProducts.items.forEach((product) => {
					const {name, skus} = product;

					if (product.skus.length > 1) {
						product.skus.forEach((sku) =>
							formattedProducts.push({
								...sku,
								chipLabel: sku.sku,
								label: name,
								value: sku.sku,
							})
						);
					}
					else if (product.skus.length === 1) {
						formattedProducts.push({
							...product,
							chipLabel: skus[0].sku,
							label: name,
							value: skus[0].sku,
						});
					}
				});

				setFormattedProducts(formattedProducts);

				setProductsWithOptions(
					availableProducts.items.filter(
						(product) => product.skus.length > 1
					)
				);
			});
	}, [accountId, channelId]);

	const handleAddToCartClick = () => {
		const readyProducts = selectedProducts.map((product) => {
			if (product.sku) {
				const parentProduct = productsWithOptions.find((item) =>
					item.skus.find((childSku) => childSku.sku === product.sku)
				);

				const {name, productConfiguration, urls} = parentProduct;

				const adjustedQuantity = getCorrectedQuantity(
					product,
					product.sku,
					cartItems,
					parentProduct
				);

				return {
					...product,
					name,
					price: product.price,
					productURLs: urls,
					quantity: adjustedQuantity,
					settings: productConfiguration,
					sku: product.sku,
					skuId: product.id,
					skuOptions: product.DDMOptions,
				};
			}
			else {
				const {productConfiguration, skus, urls} = product;

				const adjustedQuantity = getCorrectedQuantity(
					product,
					skus[0].sku,
					cartItems,
					false
				);

				return {
					...product,
					price: skus[0].price,
					productURLs: urls,
					quantity: adjustedQuantity,
					settings: productConfiguration,
					sku: skus[0].sku,
					skuId: skus[0].id,
				};
			}
		});

		const productWithoutQuantity = readyProducts.find(
			(product) => product.quantity === 0
		);

		if (!productWithoutQuantity) {
			setCartState((cartState) => ({
				...cartState,
				cartItems: cartItems.concat(readyProducts),
			}));

			addToCart(
				readyProducts,
				cartState.id,
				channel.channel.id,
				cartState.accountId
			);

			setSelectedProducts([]);
		}
		else {
			setQuickAddToCartError(true);

			setQuantityError(true);
		}
	};

	return (
		<ClayForm.Group
			className={classNames('p-3', {'has-error': quickAddToCartError})}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayMultiSelect
						allowsCustomLabel={false}
						className="p3"
						inputName="searchProducts"
						items={selectedProducts}
						locator={{
							label: 'chipLabel',
							value: 'value',
						}}
						menuRenderer={ProductAutocompleteList}
						onChange={setProductsQuery}
						onItemsChange={(newItems) => {
							setQuickAddToCartError(false);

							setQuantityError(false);

							newItems = newItems.filter((item) => {
								if (item.id) {
									return item;
								}
								else {
									setQuickAddToCartError(true);
								}
							});

							setSelectedProducts(newItems);
						}}
						onPaste={(event) => {
							const pastedText = event.clipboardData.getData(
								'Text'
							);

							event.preventDefault();

							setProductsQuery(productsQuery.concat(pastedText));
						}}
						placeholder={Liferay.Language.get('search-products')}
						size="sm"
						sourceItems={formattedProducts.filter((product) => {
							const {label, value} = product;
							const lowerCaseValue = productsQuery.toLowerCase();
							const purchasableProduct = product.sku
								? product.purchasable
								: product.skus[0].purchasable;

							if (
								!selectedProducts.includes(product) &&
								purchasableProduct
							) {
								return (
									label
										.toLowerCase()
										.includes(lowerCaseValue) ||
									value.toLowerCase().includes(lowerCaseValue)
								);
							}
						})}
						value={productsQuery}
					/>

					{quickAddToCartError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="info-circle" />

								{`${Liferay.Language.get('error-colon')} `}

								{quantityError
									? Liferay.Language.get(
											'please-enter-a-valid-quantity'
									  )
									: Liferay.Language.get('select-from-list')}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					<ClayButtonWithIcon
						disabled={
							!selectedProducts.length || quickAddToCartError
						}
						onClick={handleAddToCartClick}
						symbol="shopping-cart"
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
