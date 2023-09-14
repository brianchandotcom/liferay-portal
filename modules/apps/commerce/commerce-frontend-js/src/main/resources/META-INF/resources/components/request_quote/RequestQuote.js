/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {
	CP_INSTANCE_CHANGED,
	CP_QUANTITY_SELECTOR_CHANGED,
	CP_UNIT_OF_MEASURE_SELECTOR_CHANGED,
} from '../../utilities/eventsDefinitions';
import {liferayNavigate} from '../../utilities/index';
import {showErrorNotification} from '../../utilities/notifications';

import './request_quote.scss';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

function RequestQuote({
	accountId,
	channel,
	cpDefinitionId,
	cpInstance,
	disabled,
	namespace,
	onClick,
	onError,
	orderDetailURL,
	settings,
}) {
	const isMounted = useIsMounted();
	const [isTriggeringSubmit, setIsTriggeringSubmit] = useState(false);
	const [isVisible, setIsVisible] = useState(
		channel.requestQuoteEnabled || cpInstance.priceOnApplication
	);

	const updateCPInstance = useCallback(
		(incomingCPInstance) => {
			if (incomingCPInstance) {
				cpInstance.priceOnApplication =
					incomingCPInstance.price &&
					incomingCPInstance.price.priceOnApplication;
				cpInstance.skuId = incomingCPInstance.skuId;
				cpInstance.skuOptions = incomingCPInstance.skuOptions;

				setIsVisible(
					channel.requestQuoteEnabled || cpInstance.priceOnApplication
				);
			}
		},
		[channel, cpInstance]
	);

	const handleQuantityChanged = useCallback(
		({errors, quantity}) => {
			if (!(errors && errors.length)) {
				cpInstance.quantity = quantity;
			}
		},
		[cpInstance]
	);

	const handleCPInstanceReplaced = useCallback(
		({cpInstance}) => {
			updateCPInstance(cpInstance);
		},
		[updateCPInstance]
	);

	const handleUOMChanged = useCallback(
		({unitOfMeasure}) => {
			cpInstance.skuUnitOfMeasure = unitOfMeasure;
		},
		[cpInstance]
	);

	useEffect(() => {
		if (cpDefinitionId) {
			Liferay.on(
				`${namespace}${CP_QUANTITY_SELECTOR_CHANGED}`,
				handleQuantityChanged
			);

			Liferay.on(
				`${namespace}${CP_INSTANCE_CHANGED}`,
				handleCPInstanceReplaced
			);

			Liferay.on(
				`${namespace}${CP_UNIT_OF_MEASURE_SELECTOR_CHANGED}`,
				handleUOMChanged
			);

			return () => {
				Liferay.detach(
					`${namespace}${CP_QUANTITY_SELECTOR_CHANGED}`,
					handleQuantityChanged
				);

				Liferay.detach(
					`${namespace}${CP_INSTANCE_CHANGED}`,
					handleCPInstanceReplaced
				);

				Liferay.detach(
					`${namespace}${CP_UNIT_OF_MEASURE_SELECTOR_CHANGED}`,
					handleCPInstanceReplaced
				);
			};
		}
	}, [
		cpDefinitionId,
		handleCPInstanceReplaced,
		handleQuantityChanged,
		handleUOMChanged,
		namespace,
	]);

	return (
		isVisible && (
			<ClayButton
				block={settings.alignment === 'full-width'}
				className={classnames(settings.className, {
					[`btn-${settings.size}`]: settings.size,
				})}
				disabled={disabled}
				displayType="secondary"
				monospaced={settings.inline}
				onClick={(event) => {
					if (isTriggeringSubmit) {
						return;
					}

					setIsTriggeringSubmit(true);

					if (onClick) {
						setIsTriggeringSubmit(false);

						return onClick(event);
					}

					return CartResource.createCartByChannelId(channel.id, {
						accountId,
						cartItems: [
							{
								options: JSON.stringify(
									cpInstance.skuOptions || JSON.stringify([])
								),
								quantity: cpInstance.quantity || 1,
								skuId: cpInstance.skuId,
								skuUnitOfMeasure: cpInstance.skuUnitOfMeasure,
							},
						],
						currencyCode: channel.currencyCode,
					})
						.then((order) => {
							liferayNavigate(
								orderDetailURL.replace(escape('{id}'), order.id)
							);
						})
						.catch((error) => {
							let errorMessage;

							if (error.message) {
								errorMessage = error.message;
							}
							else if (error.detail) {
								errorMessage = error.detail;
							}

							showErrorNotification(errorMessage);

							if (onError) {
								onError(error);
							}
						})
						.finally(() => {
							if (isMounted()) {
								setIsTriggeringSubmit(false);
							}
						});
				}}
			>
				<span className="text-truncate-inline">
					<span className="text-truncate">
						{settings.buttonText ||
							Liferay.Language.get('request-a-quote')}
					</span>
				</span>
			</ClayButton>
		)
	);
}

RequestQuote.defaultProps = {
	settings: {
		alignment: 'center',
		className: 'request-quote',
		inline: false,
		size: 'lg',
	},
};

RequestQuote.propTypes = {
	accountId: PropTypes.number,
	channel: PropTypes.shape({
		currencyCode: PropTypes.string.isRequired,
		id: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
			.isRequired,
		requestQuoteEnabled: PropTypes.bool.isRequired,
	}),
	cpDefinitionId: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
		.isRequired,
	cpInstance: PropTypes.arrayOf(
		PropTypes.shape({
			priceOnApplication: PropTypes.bool,
			quantity: PropTypes.number,
			skuId: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
				.isRequired,
			skuOptions: PropTypes.array,
		})
	),
	disabled: PropTypes.bool,
	namespace: PropTypes.string,
	onClick: PropTypes.func,
	onError: PropTypes.func,
	orderDetailURL: PropTypes.string.isRequired,
	settings: PropTypes.shape({
		alignment: PropTypes.oneOf(['center', 'left', 'right', 'full-width']),
		buttonText: PropTypes.string,
		className: PropTypes.string,
		inline: PropTypes.bool,
		size: PropTypes.oneOf(['lg', 'md', 'sm']),
	}),
};

export default RequestQuote;
