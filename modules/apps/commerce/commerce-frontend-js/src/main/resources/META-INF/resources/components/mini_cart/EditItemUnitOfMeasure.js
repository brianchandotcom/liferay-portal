/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import classnames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {
	CP_INSTANCE_CHANGED,
	CP_QUANTITY_SELECTOR_CHANGED,
	CP_UNIT_OF_MEASURE_SELECTOR_CHANGED,
	CURRENT_ORDER_UPDATED,
} from '../../utilities/eventsDefinitions';
import {formatCartItem} from '../add_to_cart/data';
import {adaptLegacyPriceModel, isNonnull} from '../price/util/index';
import Asterisk from '../product_options/Asterisk';
import QuantitySelector from '../quantity_selector/QuantitySelector';
import TierPrice from '../tier_price/TierPrice';
import UnitOfMeasureSelector from '../unit_of_measure_selector/UnitOfMeasureSelector';
import MiniCartContext from './MiniCartContext';

function EditItemUnitOfMeasure({cartItemId, namespace, onBack}) {
	const {
		cartState: {
			cartItems,
			channel: {channel},
			id: cartId,
		},
		cartState,
	} = useContext(MiniCartContext);

	const [cpInstance, setCPInstance] = useState({});
	const [disabled, setDisabled] = useState(false);
	const [price, setPrice] = useState(null);
	const [quantity, setQuantity] = useState(1);
	const [skuUnitOfMeasure, setSkuUnitOfMeasure] = useState(null);

	const backLabelRef = useRef(
		sub(Liferay.Language.get('go-to-x'), Liferay.Language.get('products'))
	);
	const cartItemRef = useRef(null);
	const productNameRef = useRef('');

	useEffect(() => {
		cartItemRef.current =
			cartItems?.find((item) => item.id === cartItemId) || {};

		if (cartItemRef.current.id) {
			productNameRef.current = cartItemRef.current.name;

			setCPInstance({
				id: cartItemRef.current.skuId,
				productId: cartItemRef.current.productId,
				quantity: cartItemRef.current.quantity,
				replacedSkuId: cartItemRef.current.replacedSkuId,
				settings: cartItemRef.current.settings,
				skuId: cartItemRef.current.skuId,
				skuUnitOfMeasure: cartItemRef.current.skuUnitOfMeasure,
			});
			setPrice(adaptLegacyPriceModel(cartItemRef.current.price));
			setSkuUnitOfMeasure(cartItemRef.current.skuUnitOfMeasure);
			setQuantity(cartItemRef.current.quantity);
		}
	}, [cartItemId, cartItems]);

	const handleSave = () => {
		if (disabled) {
			return;
		}

		const updatedCartItems = cartItems.map((cartItem) => {
			if (cartItem.id === cartItemId) {
				const formattedCartItem = formatCartItem(
					cpInstance,
					namespace,
					JSON.parse(cartItem?.options) || [],
					namespace
				);

				return {
					...cartItem,
					...formattedCartItem,
				};
			}

			return cartItem;
		});

		ServiceProvider.DeliveryCartAPI('v1')
			.updateCartById(cartId, {
				cartItems: updatedCartItems,
			})
			.then((updatedCart) => {
				Liferay.fire(CURRENT_ORDER_UPDATED, {order: updatedCart});

				if (onBack) {
					onBack();
				}
			})
			.catch((error) => {
				Liferay.Util.openToast({
					message:
						error.detail ||
						error.errorDescription ||
						Liferay.Language.get(
							'an-unexpected-system-error-occurred'
						),
					type: 'danger',
				});
			});
	};

	useEffect(() => {
		const handleCPInstanceChanged = ({cpInstance}) => {
			setCPInstance((prevState) => ({
				...cpInstance,
				quantity: prevState?.quantity,
				settings: prevState?.settings,
				skuUnitOfMeasure: prevState?.skuUnitOfMeasure,
			}));
			setPrice(adaptLegacyPriceModel(cpInstance.price));
		};

		function handleUOMChanged({unitOfMeasure}) {
			setCPInstance((cpInstance) => ({
				...cpInstance,
				skuUnitOfMeasure: unitOfMeasure,
			}));
			setSkuUnitOfMeasure(unitOfMeasure);
		}

		Liferay.on(
			`${namespace}${CP_INSTANCE_CHANGED}`,
			handleCPInstanceChanged
		);

		Liferay.on(
			`${namespace}${CP_UNIT_OF_MEASURE_SELECTOR_CHANGED}`,
			handleUOMChanged
		);

		return () => {
			Liferay.detach(
				`${namespace}${CP_INSTANCE_CHANGED}`,
				handleCPInstanceChanged
			);
			Liferay.detach(
				`${namespace}${CP_UNIT_OF_MEASURE_SELECTOR_CHANGED}`,
				handleUOMChanged
			);
		};
	}, [namespace]);

	const postChannelProductSkuBySkuOption = useCallback(
		({
			accountId,
			channelId,
			options,
			productId,
			quantity,
			unitOfMeasureKey,
		}) => {
			ServiceProvider.DeliveryCatalogAPI('v1')
				.postChannelProductSkuBySkuOption(
					channelId,
					productId,
					accountId,
					quantity,
					unitOfMeasureKey,
					options
				)
				.then((cpInstance) => {
					cpInstance.skuId = parseInt(cpInstance.id, 10);

					const dispatchedPayload = {
						cpInstance,
						namespace,
					};

					Liferay.fire(
						`${namespace}${CP_INSTANCE_CHANGED}`,
						dispatchedPayload
					);
				});
		},
		[namespace]
	);

	return (
		<>
			<div className="d-flex flex-column h-100 mini-cart-edit-item overflow-hidden">
				<div className="align-items-center d-flex mini-cart-header px-4">
					{onBack ? (
						<ClayButtonWithIcon
							aria-label={backLabelRef.current}
							displayType="unstyled"
							onClick={onBack}
							symbol="angle-left"
							title={backLabelRef.current}
						/>
					) : (
						<></>
					)}

					<span className="font-weight-bold ml-2 text-5">
						{sub(
							Liferay.Language.get('edit-x'),
							productNameRef.current
						)}
					</span>
				</div>

				<div className="flex-grow-1 flex-shrink-1 overflow-auto p-4">
					{cpInstance.id ? (
						<>
							<div>
								<label htmlFor="minicart-quantity-selector">
									{Liferay.Language.get('quantity')}

									<Asterisk required={true} />
								</label>

								<QuantitySelector
									alignment="bottom"
									allowedQuantities={
										cpInstance.settings?.allowedQuantities
									}
									max={cpInstance.settings?.maxQuantity}
									min={cpInstance.settings?.minQuantity}
									name="minicart-quantity-selector"
									namespace={namespace}
									onUpdate={({
										errors,
										unitOfMeasure,
										value: newQuantity,
									}) => {
										setCPInstance((cpInstance) => ({
											...cpInstance,
											quantity: newQuantity,
										}));
										setDisabled(errors && !!errors.length);
										setQuantity(newQuantity);

										if (!(errors && !!errors.length)) {
											postChannelProductSkuBySkuOption({
												accountId: cartState.accountId,
												channelId: channel.id,
												options:
													JSON.parse(
														cartItemRef.current
															?.options
													) || [],
												productId: cpInstance.productId,
												quantity: newQuantity,
												unitOfMeasureKey:
													unitOfMeasure?.key ||
													skuUnitOfMeasure?.key,
											});
										}

										Liferay.fire(
											`${namespace}${CP_QUANTITY_SELECTOR_CHANGED}`,
											{quantity: newQuantity}
										);
									}}
									quantity={quantity}
									step={
										skuUnitOfMeasure?.incrementalOrderQuantity ||
										cpInstance.settings?.multipleQuantity
									}
									{...cpInstance.settings}
									unitOfMeasure={skuUnitOfMeasure}
								/>
							</div>

							<div className="mt-4">
								<label htmlFor="minicart-uom-selector">
									{Liferay.Language.get('unit-of-measure')}

									<Asterisk required={true} />
								</label>

								<UnitOfMeasureSelector
									accountId={cartState.accountId}
									channelId={channel.id}
									cpInstanceId={cpInstance.id}
									loadFinalPrice={true}
									name="minicart-uom-selector"
									namespace={namespace}
									options={
										JSON.parse(
											cartItemRef.current?.options
										) || []
									}
									productId={cpInstance.productId}
									resetQuantity={false}
									value={cpInstance.skuUnitOfMeasure?.key}
								/>
							</div>

							<div className="mt-4 tier-price-table">
								<label>
									{Liferay.Language.get(
										'unit-of-measure-table'
									)}
								</label>

								<TierPrice
									accountId={cartState.accountId}
									alwaysVisible={true}
									autoload={false}
									channelId={channel.id}
									cpInstanceId={cpInstance.id}
									namespace={namespace}
									productId={cpInstance.productId}
								/>
							</div>

							<div>
								<PriceRows price={price} />
							</div>
						</>
					) : (
						<></>
					)}
				</div>

				<div className="mini-cart-footer px-4 py-2 text-right">
					{onBack ? (
						<ClayButton
							className="mr-3"
							displayType="secondary"
							onClick={onBack}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					) : (
						<></>
					)}

					<ClayButton disabled={disabled} onClick={handleSave}>
						{Liferay.Language.get('save')}
					</ClayButton>
				</div>
			</div>
		</>
	);
}

EditItemUnitOfMeasure.propTypes = {
	cartItemId: PropTypes.number.isRequired,
	namespace: PropTypes.string,
	onBack: PropTypes.func,
};

export default EditItemUnitOfMeasure;

const PriceRow = ({children, priceName}) => {
	return (
		<div className="align-items-baseline d-flex justify-content-between mb-2">
			<span className="text-2">{priceName}</span>

			{children}
		</div>
	);
};

const PriceRows = ({price}) => {
	const hasPromoPrice = isNonnull(price?.promoPrice);
	const hasDiscountPercentage = isNonnull(price?.discountPercentage);

	return (
		<>
			{price ? (
				<div className="mini-cart-prices mt-4">
					<PriceRow priceName={Liferay.Language.get('price-list')}>
						<span
							className={classnames({
								'price-line-through':
									hasPromoPrice || hasDiscountPercentage,
							})}
						>
							{price.priceFormatted}
						</span>
					</PriceRow>

					{hasPromoPrice ? (
						<PriceRow
							priceName={Liferay.Language.get('promo-price')}
						>
							<span
								className={classnames({
									'price-line-through': hasDiscountPercentage,
								})}
							>
								{price.promoPriceFormatted}
							</span>
						</PriceRow>
					) : null}

					{hasDiscountPercentage ? (
						<PriceRow priceName={Liferay.Language.get('discount')}>
							<span className="price-discount">
								{`-${price.discountPercentage}%`}
							</span>
						</PriceRow>
					) : null}

					<PriceRow
						priceName={Liferay.Language.get('price-as-configured')}
					>
						<span className="text-7">
							{hasDiscountPercentage
								? price.finalPriceFormatted
								: hasPromoPrice
								? price.promoPriceFormatted
								: price.priceFormatted}
						</span>
					</PriceRow>
				</div>
			) : (
				<></>
			)}
		</>
	);
};
