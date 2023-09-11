/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {
	CART_PRODUCT_QUANTITY_CHANGED,
	CP_INSTANCE_CHANGED,
	CP_QUANTITY_SELECTOR_CHANGED,
	CP_UNIT_OF_MEASURE_SELECTOR_CHANGED,
} from '../../utilities/eventsDefinitions';
import {useCommerceAccount, useCommerceCart} from '../../utilities/hooks';
import {getMinQuantity} from '../../utilities/quantities';
import QuantitySelector from '../quantity_selector/QuantitySelector';
import UnitOfMeasureSelector from '../unit_of_measure_selector/UnitOfMeasureSelector';
import AddToCartButton from './AddToCartButton';
import {ALL} from './constants';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

function getQuantity(settings) {
	if (settings?.productConfiguration?.allowedOrderQuantities?.length) {
		return Math.min(
			...settings.productConfiguration.allowedOrderQuantities
		);
	}

	return getMinQuantity(
		settings?.productConfiguration?.minOrderQuantity,
		settings?.productConfiguration?.multipleOrderQuantity
	);
}

function AddToCart({
	accountId: initialAccountId,
	cartId: initialCartId,
	cartUUID: initialCartUUID,
	channel,
	cpInstance: initialCpInstance,
	disabled: initialDisabled,
	productId,
	settings,
	showOrderTypeModal,
	showOrderTypeModalURL,
}) {
	const account = useCommerceAccount({id: initialAccountId});
	const cart = useCommerceCart(
		{
			UUID: initialCartUUID,
			id: initialCartId,
		},
		channel.groupId
	);
	const [cpInstance, setCpInstance] = useState({
		...initialCpInstance,
		quantity: getQuantity(settings),
		validQuantity: true,
	});
	const inputRef = useRef(null);

	const buttonDisabled = useMemo(() => {
		if (
			initialDisabled ||
			!account?.id ||
			cpInstance.disabled ||
			cpInstance.purchasable === false ||
			!cpInstance.quantity
		) {
			return true;
		}

		return false;
	}, [account, cpInstance, initialDisabled]);

	useEffect(() => {
		setCpInstance({
			...initialCpInstance,
			quantity: getQuantity(settings),
			validQuantity: true,
		});
	}, [initialCpInstance, settings]);

	const handleCPInstanceReplaced = useCallback(
		({cpInstance: incomingCpInstance}) => {
			function updateInCartState(inCart) {
				setCpInstance((cpInstance) => ({
					...cpInstance,
					backOrderAllowed: incomingCpInstance.backOrderAllowed,
					disabled: incomingCpInstance.disabled,
					inCart,
					purchasable: incomingCpInstance.purchasable,
					skuId: incomingCpInstance.skuId,
					skuOptions: Array.isArray(incomingCpInstance.skuOptions)
						? incomingCpInstance.skuOptions
						: JSON.parse(incomingCpInstance.skuOptions),
					stockQuantity:
						incomingCpInstance.availability.stockQuantity,
				}));
			}

			if (cart.id) {
				CartResource.getItemsByCartId(cart.id).then(({items}) => {
					const inCart = items.some(
						({skuId, unitOfMeasureKey}) =>
							incomingCpInstance.skuId === skuId &&
							incomingCpInstance.unitOfMeasureKey ===
								unitOfMeasureKey
					);

					updateInCartState(inCart);
				});
			}
			else {
				updateInCartState(false);
			}
		},
		[cart.id]
	);

	useEffect(() => {
		function handleQuantityChanged({quantity, skuId}) {
			setCpInstance((cpInstance) => ({
				...cpInstance,
				inCart:
					skuId === cpInstance.skuId || skuId === ALL
						? Boolean(quantity)
						: cpInstance.inCart,
			}));
		}

		function handleUOMChanged({unitOfMeasure}) {
			setCpInstance((cpInstance) => ({
				...cpInstance,
				unitOfMeasureKey: unitOfMeasure ? unitOfMeasure.key : null,
			}));
		}

		Liferay.on(CART_PRODUCT_QUANTITY_CHANGED, handleQuantityChanged);

		Liferay.on(
			`${settings.namespace}${CP_INSTANCE_CHANGED}`,
			handleCPInstanceReplaced
		);

		Liferay.on(
			`${settings.namespace}${CP_UNIT_OF_MEASURE_SELECTOR_CHANGED}`,
			handleUOMChanged
		);

		return () => {
			Liferay.detach(
				CART_PRODUCT_QUANTITY_CHANGED,
				handleQuantityChanged
			);

			Liferay.detach(
				`${settings.namespace}${CP_INSTANCE_CHANGED}`,
				handleCPInstanceReplaced
			);

			Liferay.detach(
				`${settings.namespace}${CP_UNIT_OF_MEASURE_SELECTOR_CHANGED}`,
				handleUOMChanged
			);
		};
	}, [handleCPInstanceReplaced, settings]);

	const spaceDirection = settings.inline ? 'ml' : 'mt';
	let spacer = settings.size === 'sm' ? 1 : 3;

	if (Liferay.FeatureFlags['COMMERCE-11287']) {
		spacer = 0;
	}

	return (
		<div
			className={classnames({
				'add-to-cart-wrapper': true,
				'align-items-center':
					(settings.alignment === 'full-width' ||
						settings.alignment === 'center') &&
					!Liferay.FeatureFlags['COMMERCE-11287'],
				'align-items-end': Liferay.FeatureFlags['COMMERCE-11287'],
				'd-flex': !Liferay.FeatureFlags['COMMERCE-11287'],
				'flex-column': !settings.inline,
			})}
		>
			<div
				className={classnames({
					'd-flex': true,
					'justify-content-center': !settings.showUnitOfMeasureSelector,
					'mb-3': Liferay.FeatureFlags['COMMERCE-11287'],
				})}
			>
				<QuantitySelector
					allowedQuantities={
						settings.productConfiguration?.allowedOrderQuantities
					}
					disabled={initialDisabled || !account?.id}
					max={settings.productConfiguration?.maxOrderQuantity}
					min={settings.productConfiguration?.minOrderQuantity}
					namespace={settings.namespace}
					onUpdate={({errors, value: quantity}) => {
						setCpInstance((cpInstance) => ({
							...cpInstance,
							quantity,
							validQuantity: !errors.length,
						}));
						Liferay.fire(
							`${settings.namespace}${CP_QUANTITY_SELECTOR_CHANGED}`,
							{errors, quantity}
						);
					}}
					quantity={cpInstance.quantity}
					ref={inputRef}
					size={settings.size}
					step={settings.productConfiguration?.multipleOrderQuantity}
				/>

				{Liferay.FeatureFlags['COMMERCE-11287'] &&
					settings.showUnitOfMeasureSelector && (
						<UnitOfMeasureSelector
							accountId={account.id}
							channelId={channel.id}
							cpInstanceId={cpInstance.skuId}
							namespace={settings.namespace}
							productConfiguration={settings.productConfiguration}
							productId={productId}
							size={settings.size}
						/>
					)}
			</div>

			<AddToCartButton
				accountId={account.id}
				cartId={cart.id}
				channel={channel}
				className={`${spaceDirection}-${spacer}`}
				cpInstances={[cpInstance]}
				disabled={buttonDisabled}
				notAllowed={!cpInstance.validQuantity}
				onAdd={() => {
					setCpInstance({...cpInstance, inCart: true});
				}}
				onClick={
					cpInstance.validQuantity
						? null
						: (event) => {
								event.preventDefault();

								inputRef.current?.focus();
						  }
				}
				settings={settings}
				showOrderTypeModal={showOrderTypeModal}
				showOrderTypeModalURL={showOrderTypeModalURL}
			/>
		</div>
	);
}

AddToCart.propTypes = {
	accountId: PropTypes.number.isRequired,
	cartId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
	cpInstance: PropTypes.shape({
		skuId: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
			.isRequired,
		skuOptions: PropTypes.array,
		unitOfMeasureKey: PropTypes.string,
	}),
	disabled: PropTypes.bool,
	productId: PropTypes.number,
	settings: PropTypes.shape({
		alignment: PropTypes.oneOf(['center', 'left', 'right', 'full-width']),
		inline: PropTypes.bool,
		namespace: PropTypes.string,
		productConfiguration: PropTypes.shape({
			allowedOrderQuantities: PropTypes.arrayOf(PropTypes.number),
			maxOrderQuantity: PropTypes.number,
			minOrderQuantity: PropTypes.number,
			multipleOrderQuantity: PropTypes.number,
		}),
		showUnitOfMeasureSelector: PropTypes.bool,
		size: PropTypes.oneOf(['lg', 'md', 'sm']),
	}),
	showOrderTypeModal: PropTypes.bool,
	showOrderTypeModalURL: PropTypes.string,
};

export default AddToCart;
