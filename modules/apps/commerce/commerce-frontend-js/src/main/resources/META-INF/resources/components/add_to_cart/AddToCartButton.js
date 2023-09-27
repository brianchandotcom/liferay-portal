/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useLiferayState} from '@liferay/frontend-js-state-web';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import cartAtom from '../../utilities/atoms/cartAtom';
import skuOptionsAtom from '../../utilities/atoms/skuOptionsAtom';
import {ADD_ITEM_TO_CART, OPEN_MODAL} from '../../utilities/eventsDefinitions';
import {showErrorNotification} from '../../utilities/notifications';
import {addToCart} from './data';

import './add_to_cart.scss';
import ServiceProvider from '../../ServiceProvider/index';
import {getRandomId} from '../../utilities/index';
import {MEDIUM_MODAL_SIZE} from '../../utilities/modals/constants';
import Modal from '../modal/Modal';

function AddToCartButton({
	accountId,
	cartId,
	channel,
	className,
	cpInstances,
	disabled,
	hideIcon,
	notAllowed,
	onAdd,
	onClick,
	onError,
	settings,
	showOrderTypeModal,
	showOrderTypeModalURL,
}) {
	const [cartAtomState, setCartAtomState] = useLiferayState(cartAtom);
	const [skuOptionsAtomState] = useLiferayState(skuOptionsAtom);
	const [isTriggeringCartUpdate, setIsTriggeringCartUpdate] = useState(false);
	const isMounted = useIsMounted();
	const [event, setEvent] = useState(null);
	const randomNamespace = getRandomId();

	const buttonDisabled = useMemo(
		() => skuOptionsAtomState.errors.length || disabled,
		[disabled, skuOptionsAtomState.errors]
	);

	const handleClickAddToCart = useCallback(
		(event, orderTypeId) => {
			if (cartAtomState.updating) {
				return;
			}

			if (onClick) {
				return onClick(event, cpInstances, cartId, channel, accountId);
			}

			setIsTriggeringCartUpdate(true);

			setCartAtomState({updating: true});

			return addToCart(
				cpInstances,
				cartId,
				channel,
				accountId,
				orderTypeId,
				settings.namespace,
				skuOptionsAtomState.skuOptions,
				skuOptionsAtomState.namespace
			)
				.then(onAdd)
				.catch((error) => {
					console.error(error);

					let errorMessage;

					if (error.message) {
						errorMessage = error.message;
					}
					else if (error.detail) {
						errorMessage = error.detail;
					}
					else {
						errorMessage =
							cpInstances.length > 1
								? Liferay.Language.get(
										'unable-to-add-products-to-the-cart'
								  )
								: Liferay.Language.get(
										'unable-to-add-product-to-the-cart'
								  );
					}

					showErrorNotification(errorMessage);

					onError(error);
				})
				.finally(() => {
					if (isMounted()) {
						setCartAtomState({updating: false});

						setIsTriggeringCartUpdate(false);
					}
				});
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[
			accountId,
			cartAtomState.updating,
			cartId,
			channel,
			cpInstances,
			isMounted,
			onAdd,
			onClick,
			onError,
			setCartAtomState,
			skuOptionsAtomState,
		]
	);

	useEffect(() => {
		function handleAddItemToCart({orderTypeId}) {
			if (event) {
				handleClickAddToCart(event, orderTypeId);
			}
		}

		Liferay.on(ADD_ITEM_TO_CART, handleAddItemToCart);

		return () => {
			Liferay.detach(ADD_ITEM_TO_CART, handleAddItemToCart);
		};
	}, [event, handleClickAddToCart]);

	return (
		<ClayButton
			block={settings.alignment === 'full-width'}
			className={classnames(className, {
				[`btn-${settings.size}`]: settings.size,
				'btn-add-to-cart': true,
				'icon-only': settings.iconOnly,
				'is-added': cpInstances.length === 1 && cpInstances[0].inCart,
				'not-allowed':
					notAllowed ||
					(cartAtomState.updating && !isTriggeringCartUpdate),
			})}
			disabled={buttonDisabled}
			displayType="primary"
			monospaced={settings.iconOnly && settings.inline}
			onClick={async (event) => {
				if (accountId > 0) {
					const CartResource = ServiceProvider.DeliveryCartAPI('v1');
					const order = await CartResource.getCartsByAccountIdAndChannelId(
						accountId,
						channel.id
					);

					if (showOrderTypeModal && !order.items.length) {
						setEvent(event);
						Liferay.fire(OPEN_MODAL, {
							addToCart: true,
							id: `${randomNamespace}add-order-modal`,
							size: MEDIUM_MODAL_SIZE,
						});

						return;
					}
				}

				handleClickAddToCart(event);
			}}
		>
			{showOrderTypeModal ? (
				<Modal
					id={`${randomNamespace}add-order-modal`}
					url={showOrderTypeModalURL}
				/>
			) : null}

			{!settings.iconOnly && (
				<span className="text-truncate-inline">
					<span className="text-truncate">
						{settings.buttonText ||
							Liferay.Language.get('add-to-cart')}
					</span>
				</span>
			)}

			{!hideIcon && (
				<span className="cart-icon">
					<ClayIcon symbol="shopping-cart" />
				</span>
			)}
		</ClayButton>
	);
}

AddToCartButton.defaultProps = {
	accountId: null,
	cartId: 0,
	cpInstances: [
		{
			inCart: false,
			skuOptions: '[]',
		},
	],
	hideIcon: false,
	onAdd: () => {},
	onError: () => {},
	settings: {
		iconOnly: false,
		inline: false,
	},
	showOrderTypeModal: false,
};

AddToCartButton.propTypes = {
	accountId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
	cartId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
	channel: PropTypes.shape({

		/**
		 * The currency is currently always
		 * one and the same per single channel
		 */
		currencyCode: PropTypes.string.isRequired,
		id: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
			.isRequired,
	}),
	cpInstances: PropTypes.arrayOf(
		PropTypes.shape({
			inCart: PropTypes.bool,
			quantity: PropTypes.number,
			skuId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
			skuOptions: PropTypes.oneOfType([
				PropTypes.string,
				PropTypes.array,
			]),
		})
	).isRequired,
	disabled: PropTypes.bool,
	hideIcon: PropTypes.bool,
	notAllowed: PropTypes.bool,
	onAdd: PropTypes.func.isRequired,
	onError: PropTypes.func.isRequired,
	settings: PropTypes.shape({
		alignment: PropTypes.oneOf(['center', 'left', 'right', 'full-width']),
		buttonText: PropTypes.string,
		iconOnly: PropTypes.bool,
		inline: PropTypes.bool,
	}),
	showOrderTypeModal: PropTypes.bool,
	showOrderTypeModalURL: PropTypes.string,
};

export default AddToCartButton;
