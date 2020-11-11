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

import {ClayIconSpriteContext} from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {CURRENT_ACCOUNT_UPDATED} from '../../utilities/eventsDefinitions';
import showNotification from '../../utilities/notifications';
import QuantitySelector from '../quantity_selector/QuantitySelector';
import {generateOptions} from '../quantity_selector/utils/index';
import AddToCartButton from './AddToCartButton';

const AddToCartWrapper = (props) => {
	const CartResource = ServiceProvider.DeliveryCartAPI('v1');
	const [accountId, setAccountId] = useState(props.accountId);
	const [orderQuantity, setOrderQuantity] = useState(props.orderQuantity);
	const [orderId, setOrderId] = useState(props.orderId);
	const [multipleQuantity, setMultipleQuantity] = useState(
		props.settings.multipleQuantity
	);
	const [selectedQuantity, setSelectedQuantity] = useState();
	const [disabled, setDisabled] = useState();
	const [skuId, setSkuId] = useState(props.skuId);

	useEffect(() => {
		setMultipleQuantity(props.settings.multipleQuantity);
		if (props.settings.allowedQuantity?.length > 0) {
			setOrderQuantity(props.settings.allowedQuantity);
			setMultipleQuantity(1);
		}
		else {
			const quantities = generateOptions(props.settings);
			setOrderQuantity(quantities);
		}
		if (props.settings.multipleQuantity === undefined) {
			setMultipleQuantity(1);
		}
		if (props.preSelectedQuantity) {
			setSelectedQuantity(props.preSelectedQuantity);
		}
	}, [props.settings, props.orderQuantity, props.preSelectedQuantity]);

	useEffect(() => {
		if (props.skuId !== skuId) {
			setSkuId(props.skuId);
		}
		function handleAccountUpdated({id}) {
			setAccountId(id);
		}
		if (props.accountId !== accountId) {
			setAccountId(props.accountId);
		}
		if (accountId === undefined || skuId === undefined) {
			setDisabled(true);
		}
		else {
			setDisabled(false);
		}
		Liferay.on(CURRENT_ACCOUNT_UPDATED, handleAccountUpdated);

		return () => {
			Liferay.detach(CURRENT_ACCOUNT_UPDATED, handleAccountUpdated);
		};
	}, [accountId, props.accountId, props.skuId, skuId]);

	useEffect(() => {
		if (!accountId || !props.skuId || !orderId) {
			setDisabled(true);
			showNotification('no-account-selected', 'danger', true, 500);
		}
		else {
			setDisabled(false);
		}
	}, [accountId, props.skuId, orderId]);

	const updateQuantity = (who, value) => {
		if (!value && !selectedQuantity) {
			value = 1;
			setSelectedQuantity(1);
		}
		else if (!value && selectedQuantity) {
			value = selectedQuantity;
		}
		else {
			setSelectedQuantity(value);
		}

		const quantity = multipleQuantity * value;
		const itemPayload = {
			quantity,
			skuId,
		};

		if (
			who === 'button' ||
			(who === 'select' && props.disableAddToCartButton)
		) {
			if (!orderId) {
				CartResource.createCartByChannelId(props.channelId, {
					accountId: props.accountId,
					cartItems: [itemPayload],
					currencyCode: props.currencyCode,
				}).then((data) => {
					if (orderId !== data.id) {
						setOrderId(data.id);
					}
				});
			}
			else {
				CartResource.createItemByCartId(orderId, {
					...itemPayload,
					productId: props.productId,
				});
			}
		}
	};

	if (!props.disableAddToCartButton || !props.disableQuantitySelector) {
		return (
			<ClayIconSpriteContext.Provider value={props.spritemap}>
				{props.settings.allowedQuantity?.length > 0 && (
					<span data-testid="allowed-quantity-alert">
						{Liferay.Language.get(
							'select-the-product-quantity-allowed-per-order'
						)}
					</span>
				)}
				{props.settings.minQuantity > 1 && (
					<span data-testid="minimum-quantity-alert">
						{Liferay.Language.get('minimum-quantity-per-order-is')}
						:&nbsp;{props.settings.minQuantity}
					</span>
				)}
				{props.settings.multipleQuantity > 1 && (
					<span data-testid="multiple-quantity-alert">
						{Liferay.Language.get('multiple-quantity-per-order')}
						:&nbsp;{props.settings.multipleQuantity}
					</span>
				)}

				{!props.disableQuantitySelector &&
					!props.customQuantitySelector && (
						<QuantitySelector
							disabled={disabled}
							isQuantityEmpty={
								selectedQuantity > 0 ? false : true
							}
							orderQuantity={orderQuantity}
							settings={props.settings}
							size={'small'}
							spritemap={props.spritemap}
							style={props.quantitySelectorStyle}
							updateQuantity={updateQuantity}
						/>
					)}

				{props.customQuantitySelector && props.customQuantitySelector()}

				{!props.disableAddToCartButton &&
					!props.customAddToCartButton && (
						<AddToCartButton
							block={props.addToCartButton.block}
							disabled={disabled}
							iconOnly={props.iconOnly}
							orderQuantity={orderQuantity}
							quantity={selectedQuantity}
							rtl={props.addToCartButton.rtl}
							size={'small'}
							spritemap={props.spritemap}
							symbol={props.addToCartButton.symbol}
							updateQuantity={updateQuantity}
						/>
					)}

				{props.customAddToCartButton && props.customAddToCartButton()}
			</ClayIconSpriteContext.Provider>
		);
	}
};

AddToCartWrapper.defaultProps = {
	addToCartButton: {
		block: true,
	},
	disableAddToCartButton: false,
	disableOptionsSelector: false,
	disableQuantitySelector: false,
	quantitySelector: {
		disabled: false,
	},
	settings: {
		maxQuantity: 100,
		minQuantity: 1,
		multipleQuantity: 1,
	},
};

AddToCartWrapper.propTypes = {
	accountId: PropTypes.number.isRequired,
	addToCartButton: PropTypes.shape({
		block: PropTypes.bool,
		buttonTextContent: PropTypes.string,
		disabled: PropTypes.bool,
		iconOnly: PropTypes.bool,
		rtl: PropTypes.bool,
		size: PropTypes.oneOf(['large', 'medium', 'small']),
		symbol: PropTypes.string,
	}),
	channelId: PropTypes.number.isRequired,
	currencyCode: PropTypes.string,
	customAddToCartButton: PropTypes.func,
	customOptionsSelector: PropTypes.func,
	customQuantitySelector: PropTypes.func,
	disableAddToCartButton: PropTypes.bool,
	disableQuantitySelector: PropTypes.bool,
	orderId: PropTypes.number.isRequired,
	orderQuantity: PropTypes.arrayOf(PropTypes.number),
	preSelectedQuantity: PropTypes.number,
	productId: PropTypes.number.isRequired,
	quantitySelector: PropTypes.shape({
		disabled: PropTypes.bool,
	}),
	quantitySelectorStyle: PropTypes.oneOf(['select', 'datalist']),
	settings: PropTypes.shape({
		allowedQuantity: PropTypes.arrayOf(PropTypes.number),
		maxQuantity: PropTypes.number,
		minQuantity: PropTypes.number,
		multipleQuantity: PropTypes.number,
	}),
	skuId: PropTypes.number.isRequired,
	spritemap: PropTypes.string.isRequired,
};

export default AddToCartWrapper;
