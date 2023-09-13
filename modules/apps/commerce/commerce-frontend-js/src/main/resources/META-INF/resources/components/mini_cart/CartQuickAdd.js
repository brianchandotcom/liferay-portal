/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useContext, useRef, useState} from 'react';

import {addToCart} from '../add_to_cart/data';
import InfiniteScroller from '../infinite_scroller/InfiniteScroller';
import MiniCartContext from './MiniCartContext';
import {getCorrectedQuantity} from './util/index';

const CHANNEL_RESOURCE_ENDPOINT =
	'/o/headless-commerce-delivery-catalog/v1.0/channels';

const getSearchSKUsURL = (page, search, accountId, channelId) => {
	const url = new URL(
		`${themeDisplay.getPathContext()}${CHANNEL_RESOURCE_ENDPOINT}/${channelId}/products`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append('accountId', accountId);
	url.searchParams.append('nestedFields', 'skus');
	url.searchParams.append('page', page);
	url.searchParams.append('pageSize', '100');
	url.searchParams.append('search', search);
	url.searchParams.append('skus.accountId', accountId);

	return url.toString();
};

export default function CartQuickAdd() {
	const {cartState} = useContext(MiniCartContext);

	const keypressTimoutRef = useRef(null);
	const paginatorCurrentPageRef = useRef(1);
	const paginatorIsLoadingRef = useRef(false);
	const paginatorItemLengthRef = useRef(0);
	const paginatorLastPageRef = useRef(1);
	const paginatorTotalCountRef = useRef(0);
	const requestAbortControllerRef = useRef(new AbortController());

	const [formattedSKUs, setFormattedSKUs] = useState([]);
	const [quantityError, setQuantityError] = useState(false);
	const [quickAddToCartError, setQuickAddToCartError] = useState(false);
	const [searchInputValue, setSearchInputValue] = useState('');
	const [selectedSKUs, setSelectedSKUs] = useState([]);

	const {
		accountId,
		cartItems = [],
		channel: {channel},
		id: cartId,
	} = cartState;
	const channelId = channel.id;

	const ProductAutocompleteList = ({onItemClick, sourceItems}) => {
		return (
			<InfiniteScroller
				maxHeight="145px"
				onBottomTouched={() => {
					if (!paginatorIsLoadingRef.current) {
						paginatorIsLoadingRef.current = true;
						paginatorCurrentPageRef.current =
							paginatorCurrentPageRef.current <
							paginatorLastPageRef.current
								? paginatorCurrentPageRef.current + 1
								: paginatorCurrentPageRef.current;

						searchSKUs(
							searchInputValue,
							paginatorCurrentPageRef.current,
							true
						);
					}
				}}
				scrollCompleted={
					paginatorItemLengthRef.current >=
					paginatorTotalCountRef.current
				}
			>
				<ClayDropDown.ItemList>
					{sourceItems
						.filter(
							(sku) =>
								!selectedSKUs.includes(sku) && sku.purchasable
						)
						.map((sku) => {
							const {id, label, value} = sku;

							return (
								<ClayDropDown.Item
									key={id}
									onClick={() => onItemClick(sku)}
								>
									<div className="autofit-row autofit-row-center">
										<div className="autofit-col mr-3 w-25">
											{value}
										</div>

										<span className="ml-2 text-truncate">
											{label}
										</span>
									</div>
								</ClayDropDown.Item>
							);
						})}
				</ClayDropDown.ItemList>
			</InfiniteScroller>
		);
	};

	const handleAddToCartClick = () => {
		const readySKUs = selectedSKUs.map((selectedSKUData) => {
			const {
				id: selectedId,
				productConfiguration: selectedConfiguration,
				replacementSku: replacementSKUData,
				sku: selectedSKU,
				skuUnitOfMeasures,
				urls,
			} = selectedSKUData;

			if (skuUnitOfMeasures && skuUnitOfMeasures.length) {
				selectedSKUData.skuUnitOfMeasure = skuUnitOfMeasures[0];
			}

			if (
				selectedSKUData.availability?.label !== 'available' &&
				!selectedConfiguration.allowBackOrder &&
				replacementSKUData
			) {
				const {
					price,
					productConfiguration: replacementConfiguration,
					sku: replacementSKU,
					urls: productURLs,
				} = replacementSKUData;

				return {
					...replacementSKUData,
					price,
					productURLs,
					quantity: getCorrectedQuantity(
						replacementConfiguration,
						replacementSKU,
						cartItems
					),
					replacedSkuId: selectedId,
					settings: replacementConfiguration,
				};
			}

			return {
				...selectedSKUData,
				productURLs: urls,
				quantity: getCorrectedQuantity(
					{
						...selectedConfiguration,
						multipleOrderQuantity:
							selectedSKUData.skuUnitOfMeasure
								?.incrementalOrderQuantity ||
							selectedConfiguration.multipleOrderQuantity,
					},
					selectedSKU,
					cartItems,
					selectedSKUData.skuUnitOfMeasure?.precision || 1
				),
				settings: selectedConfiguration,
				skuId: selectedId,
			};
		});

		const unavailableSKU = readySKUs.find(
			(readySKU) => readySKU.quantity === 0
		);

		if (!unavailableSKU) {
			addToCart(readySKUs, cartId, channel, accountId)
				.then(() => {})
				.catch((error) => {
					Liferay.Util.openToast({
						message:
							error.detail ||
							Liferay.Language.get(
								'an-unexpected-system-error-occurred'
							),
						type: 'danger',
					});
				});

			setSelectedSKUs([]);
		}
		else {
			setQuickAddToCartError(true);

			setQuantityError(true);
		}
	};

	const handleProductQueryInput = (productQueryString) => {
		clearTimeout(keypressTimoutRef.current);

		requestAbortControllerRef.current.abort();

		paginatorCurrentPageRef.current = 1;

		setSearchInputValue(productQueryString);

		keypressTimoutRef.current = setTimeout(() => {
			searchSKUs(productQueryString, 1, false);
		}, 500);
	};

	const searchSKUs = (queryString, page, appendData) => {
		requestAbortControllerRef.current = new AbortController();

		const {signal} = requestAbortControllerRef.current;

		if (!queryString.length) {
			paginatorIsLoadingRef.current = false;

			setFormattedSKUs([]);

			return;
		}

		paginatorIsLoadingRef.current = true;

		const searchSKUsURL = getSearchSKUsURL(
			page,
			queryString,
			accountId,
			channelId
		);

		fetch(searchSKUsURL, {
			signal,
		})
			.then((response) => response.json())
			.then((availableSKUs) => {
				paginatorItemLengthRef.current =
					availableSKUs.page * availableSKUs.pageSize;
				paginatorLastPageRef.current = availableSKUs.lastPage;
				paginatorTotalCountRef.current = availableSKUs.totalCount;

				const responseSKUs = [];

				availableSKUs.items.forEach((availableSKU) => {
					availableSKU.skus.forEach((sku) => {
						responseSKUs.push({
							...sku,
							chipLabel: sku.sku,
							label: availableSKU.name,
							productConfiguration:
								availableSKU.productConfiguration,
							urls: availableSKU.urls,
							value: sku.sku,
						});
					});
				});

				setFormattedSKUs(responseSKUs);

				if (appendData) {
					setFormattedSKUs(formattedSKUs.concat(responseSKUs));
				}

				paginatorIsLoadingRef.current = false;
			});
	};

	return (
		<ClayForm.Group
			className={classNames('m-3', {'has-error': quickAddToCartError})}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayMultiSelect
						allowsCustomLabel={false}
						className="p3"
						inputName="searchSKUs"
						items={selectedSKUs}
						loadingState={4}
						locator={{
							label: 'chipLabel',
							value: 'value',
						}}
						menuRenderer={ProductAutocompleteList}
						onChange={handleProductQueryInput}
						onItemsChange={(newSKUs) => {
							setQuickAddToCartError(false);

							setQuantityError(false);

							newSKUs = newSKUs.filter((item) => {
								if (item.id) {
									return item;
								}
								else {
									setQuickAddToCartError(true);
								}
							});

							setSelectedSKUs(newSKUs);
						}}
						onPaste={(event) => {
							const pastedText = event.clipboardData.getData(
								'Text'
							);

							event.preventDefault();

							handleProductQueryInput(
								searchInputValue.concat(pastedText)
							);
						}}
						placeholder={Liferay.Language.get('search-products')}
						size="sm"
						sourceItems={formattedSKUs}
						value={searchInputValue}
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
						disabled={!selectedSKUs.length || quickAddToCartError}
						onClick={handleAddToCartClick}
						symbol="shopping-cart"
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
