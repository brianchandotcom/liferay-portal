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

const CHANNEL_RESOURCE_ENDPOINT =
	'/o/headless-commerce-delivery-catalog/v1.0/channels';

const formattedProducts = [];

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

	const [productsQuery, setProductsQuery] = useState('');
	const [quickAddToCartError, setQuickAddToCartError] = useState(false);
	const [selectedProducts, setSelectedProducts] = useState([]);
	const [products, setProducts] = useState();

	const {cartItems = [], channel} = cartState;

	const channelId = channel.channel.id;

	useEffect(() => {
		const productsApiURL = new URL(
			`${themeDisplay.getPathContext()}${CHANNEL_RESOURCE_ENDPOINT}/${channelId}/products?nestedFields=skus`,
			themeDisplay.getPortalURL()
		);

		fetch(productsApiURL.toString())
			.then((response) => response.json())
			.then((availableProducts) => {
				availableProducts.items.forEach((product) => {
					const {name, skus} = product;

					formattedProducts.push({
						...product,
						label: name,
						value: skus[0].sku,
					});
				});

				setProducts(availableProducts.items);
			});
	}, [channelId]);

	const handleAddToCartClick = () => {
		const itemSKUs = selectedProducts.map((item) => item.value);
		const readyProducts = [];

		products.forEach((product) => {
			if (itemSKUs.includes(product.skus[0].sku)) {
				const {productConfiguration, skus, urls} = product;

				readyProducts.push({
					...product,
					price: skus[0].price,
					productURLs: urls,
					quantity: productConfiguration.minOrderQuantity,
					settings: productConfiguration,
					sku: skus[0].sku,
					skuId: skus[0].id,
				});
			}
		});

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
	};

	return (
		<ClayForm.Group
			className={classNames('p-3', {'has-error': quickAddToCartError})}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayMultiSelect
						className="p3"
						inputName="searchProducts"
						items={selectedProducts}
						menuRenderer={ProductAutocompleteList}
						onChange={setProductsQuery}
						onItemsChange={(newItems) => {
							setQuickAddToCartError(false);

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
						placeholder={Liferay.Language.get('search-products')}
						size="sm"
						sourceItems={formattedProducts.filter((product) => {
							const {label, value} = product;
							const lowerCaseValue = productsQuery.toLowerCase();

							return (
								label.toLowerCase().match(lowerCaseValue) ||
								value.toLowerCase().match(lowerCaseValue)
							);
						})}
						value={productsQuery}
					/>

					{quickAddToCartError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="info-circle" />

								{Liferay.Language.get('select-from-list')}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					<ClayButtonWithIcon
						disabled={!selectedProducts.length}
						onClick={handleAddToCartClick}
						symbol="shopping-cart"
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
