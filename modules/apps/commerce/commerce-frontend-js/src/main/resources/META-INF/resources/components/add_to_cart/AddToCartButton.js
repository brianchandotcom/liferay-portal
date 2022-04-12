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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useIsMounted, useLiferayState} from '@liferay/frontend-js-react-web';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import cartAtom from '../../utilities/atoms/cartAtom';
import {showErrorNotification} from '../../utilities/notifications';
import {addToCart} from './data';

import './add_to_cart.scss';

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
}) {
	const [cartAtomState, setCartAtomState] = useLiferayState(cartAtom);
	const [isTriggeringCartUpdate, setIsTriggeringCartUpdate] = useState(false);
	const isMounted = useIsMounted();

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
			disabled={disabled}
			displayType="primary"
			monospaced={settings.iconOnly && settings.inline}
			onClick={(event) => {
				if (cartAtomState.updating) {
					return;
				}

				if (onClick) {
					return onClick(
						event,
						cpInstances,
						cartId,
						channel,
						accountId
					);
				}

				setIsTriggeringCartUpdate(true);

				setCartAtomState({updating: true});

				return addToCart(cpInstances, cartId, channel, accountId)
					.then(onAdd)
					.catch((error) => {
						console.error(error);

						let errorMessage;

						if (error.message) {
							errorMessage = error.message;
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
			}}
		>
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
};

export default AddToCartButton;
