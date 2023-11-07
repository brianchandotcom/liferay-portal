/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import {useLiferayState} from '@liferay/frontend-js-state-web';
import {fetch, sub} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {CommerceContext} from '../../index';
import skuOptionsAtom from '../../utilities/atoms/skuOptionsAtom';
import {CHANNEL_RESOURCE_ENDPOINT, FIELD_TYPE} from '../../utilities/constants';
import {
	CP_INSTANCE_CHANGED,
	CURRENT_ORDER_UPDATED,
} from '../../utilities/eventsDefinitions';
import {formatCartItem} from '../add_to_cart/data';
import {adaptLegacyPriceModel, isNonnull} from '../price/util/index';
import ProductOptionCheckbox from '../product_options/ProductOptionCheckbox';
import ProductOptionCheckboxMultiple from '../product_options/ProductOptionCheckboxMultiple';
import ProductOptionDate from '../product_options/ProductOptionDate';
import ProductOptionNumeric from '../product_options/ProductOptionNumeric';
import ProductOptionRadio from '../product_options/ProductOptionRadio';
import ProductOptionSelect from '../product_options/ProductOptionSelect';
import ProductOptionText from '../product_options/ProductOptionText';
import MiniCartContext from './MiniCartContext';

const MINI_CART_NAMESPACE = 'minicart_';

const getProductOptionsURL = (channelId, productId) => {
	const url = new URL(
		`${themeDisplay.getPathContext()}${CHANNEL_RESOURCE_ENDPOINT}/${channelId}/products/${productId}/product-options`,
		themeDisplay.getPortalURL()
	);

	return url.toString();
};

function EditItem() {
	const [options, setOptions] = useState([]);
	const [skuOptionsAtomState, setSkuOptionsAtomState] = useLiferayState(
		skuOptionsAtom
	);
	const [cpInstance, setCPInstance] = useState({});

	const {miniCartErrors} = skuOptionsAtomState;

	const disabled = useMemo(() => miniCartErrors?.length, [miniCartErrors]);

	const {
		cartState: {
			cartItems,
			channel: {channel},
		},
		cartState,
		editedItem,
		setEditedItem,
	} = useContext(MiniCartContext);

	const backLabel = sub(
		Liferay.Language.get('go-to-x'),
		Liferay.Language.get('products')
	);

	const selectedItem = useMemo(
		() => cartItems.find((item) => item.id === editedItem.cartItemId) || {},
		[cartItems, editedItem]
	);

	useEffect(() => {
		setCPInstance({
			quantity: selectedItem.quantity,
			replacedSkuId: selectedItem.replacedSkuId,
			skuId: selectedItem.skuId,
		});
	}, [selectedItem]);

	const handleSave = () => {
		if (disabled) {
			return;
		}

		const {cartItems, id: cartId} = cartState;

		const formattedCartItem = formatCartItem(
			cpInstance,
			MINI_CART_NAMESPACE,
			skuOptionsAtomState.miniCartSkuOptions,
			MINI_CART_NAMESPACE
		);

		const updatedCartItems = cartItems.map((cartItem) =>
			cartItem.id === selectedItem.id
				? {
						...cartItem,
						options: formattedCartItem.options,
						replacedSkuId: formattedCartItem.replacedSkuId,
						skuId: formattedCartItem.skuId,
				  }
				: cartItem
		);

		ServiceProvider.DeliveryCartAPI('v1')
			.updateCartById(cartId, {
				cartItems: updatedCartItems,
			})
			.then((updatedCart) => {
				Liferay.fire(CURRENT_ORDER_UPDATED, {order: updatedCart});

				setEditedItem(null);
				setSkuOptionsAtomState({
					...skuOptionsAtomState,
					miniCartErrors: [],
					miniCartSkuOptions: [],
					updating: false,
				});
			})
			.catch((error) => {
				console.error(error);
			});
	};

	const [price, setPrice] = useState(
		selectedItem ? selectedItem.price : null
	);

	const handleCPInstanceChanged = ({cpInstance}) => {
		setCPInstance(cpInstance);
		setPrice(adaptLegacyPriceModel(cpInstance.price));
	};

	useEffect(() => {
		const productOptionsURL = getProductOptionsURL(
			channel.id,
			editedItem.productId
		);

		fetch(productOptionsURL)
			.then((response) => response.json())
			.then((data) => setOptions(data))
			.catch((error) => console.error(error));
	}, [channel.id, editedItem.productId]);

	useEffect(() => {
		Liferay.on(
			`${MINI_CART_NAMESPACE}${CP_INSTANCE_CHANGED}`,
			handleCPInstanceChanged
		);

		return () => {
			Liferay.detach(
				`${MINI_CART_NAMESPACE}${CP_INSTANCE_CHANGED}`,
				handleCPInstanceChanged
			);
		};
	}, []);

	const hasDiscount = isNonnull(price.discountPercentage);
	const hasPromoPrice = isNonnull(price.promoPrice);
	const priceOnApplication = price.priceOnApplication;

	return (
		<>
			<div className="d-flex flex-column h-100 mini-cart-edit-item overflow-hidden">
				<div className="align-items-center d-flex mini-cart-header px-4">
					<ClayButtonWithIcon
						aria-label={backLabel}
						displayType="unstyled"
						onClick={() => setEditedItem(null)}
						symbol="angle-left"
						title={backLabel}
					/>

					<span className="font-weight-bold ml-2 text-5">
						{sub(Liferay.Language.get('edit-x'), editedItem.name)}
					</span>
				</div>

				<div className="flex-grow-1 flex-shrink-1 overflow-auto p-4">
					{options?.items?.length > 0 ? (
						<ClayForm>
							<Options
								cartItemId={editedItem.cartItemId}
								channelId={channel.id}
								namespace={MINI_CART_NAMESPACE}
								productId={editedItem.productId}
								productOptions={options.items}
								selectedItem={selectedItem}
							/>
						</ClayForm>
					) : null}

					{priceOnApplication && (
						<div className="mini-cart-prices mt-4">
							<PriceRow
								priceName={Liferay.Language.get(
									'price-as-configured'
								)}
							>
								<span className="price-on-application price-value text-3">
									{Liferay.Language.get(
										'price-on-application'
									)}
								</span>
							</PriceRow>
						</div>
					)}
				</div>

				<div>
					{!priceOnApplication && (
						<div className="mini-cart-prices p-4">
							<PriceRow
								priceName={Liferay.Language.get('list-price')}
							>
								{hasPromoPrice || hasDiscount ? (
									<span className="price-line-through">
										{price.priceFormatted}
									</span>
								) : (
									<span>{price.priceFormatted}</span>
								)}
							</PriceRow>

							{hasPromoPrice ? (
								<PriceRow
									priceName={Liferay.Language.get(
										'promo-price'
									)}
								>
									{hasDiscount ? (
										<span className="price-line-through">
											{price.promoPriceFormatted}
										</span>
									) : (
										<span>{price.promoPriceFormatted}</span>
									)}
								</PriceRow>
							) : null}

							{hasDiscount ? (
								<PriceRow
									priceName={Liferay.Language.get('discount')}
								>
									<span className="price-discount">
										{`-${price.discountPercentage}%`}
									</span>
								</PriceRow>
							) : null}

							<PriceRow
								priceName={Liferay.Language.get(
									'price-as-configured'
								)}
							>
								<span className="text-7">
									{price.finalPriceFormatted}
								</span>
							</PriceRow>
						</div>
					)}
				</div>

				<div className="mini-cart-footer px-4 py-2 text-right">
					<ClayButton
						className="mr-3"
						displayType="secondary"
						onClick={() => setEditedItem(null)}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>

					<ClayButton disabled={disabled} onClick={handleSave}>
						{Liferay.Language.get('save')}
					</ClayButton>
				</div>
			</div>
		</>
	);
}

export default EditItem;

const Options = ({
	cartItemId,
	channelId,
	productId,
	productOptions,
	selectedItem,
}) =>
	productOptions.map((productOption) => {
		let Component = ProductOptionCheckbox;
		let props = {
			componentId: `${MINI_CART_NAMESPACE}${cartItemId}_${productOption.id}`,
			isFromMiniCart: true,
			json: selectedItem.options,
			namespace: MINI_CART_NAMESPACE,
			productOption,
		};

		if (productOption.fieldType === FIELD_TYPE.checkboxMultiple) {
			Component = ProductOptionCheckboxMultiple;
		}
		else if (productOption.fieldType === FIELD_TYPE.date) {
			Component = ProductOptionDate;
		}
		else if (productOption.fieldType === FIELD_TYPE.numeric) {
			Component = ProductOptionNumeric;
		}
		else if (productOption.fieldType === FIELD_TYPE.radio) {
			Component = ProductOptionRadio;
			props = {
				...props,
				accountId: CommerceContext.account.accountId,
				channelId,
				minQuantity: selectedItem.quantity,
				productId,
				sku: {skuId: selectedItem.skuId},
			};
		}
		else if (productOption.fieldType === FIELD_TYPE.select) {
			Component = ProductOptionSelect;
			props = {
				...props,
				accountId: CommerceContext.account.accountId,
				channelId,
				minQuantity: selectedItem.quantity,
				productId,
				sku: {skuId: selectedItem.skuId},
			};
		}
		else if (productOption.fieldType === FIELD_TYPE.text) {
			Component = ProductOptionText;
		}

		return <Component key={productOption.id} {...props} />;
	});

const PriceRow = ({children, priceName}) => {
	return (
		<div className="align-items-baseline d-flex justify-content-between mb-2">
			<span className="text-2">{priceName}</span>

			{children}
		</div>
	);
};
