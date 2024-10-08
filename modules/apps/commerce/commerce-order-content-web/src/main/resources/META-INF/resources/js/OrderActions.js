/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import {handleOrderActionRedirect} from './orderActionRedirectHelper';

function OrderActions({checkoutURL, isOpen, orderId, reorderURL}) {
	const [open, setOpen] = useState(isOpen);
	const [actions, setActions] = useState([]);

	const getActions = useCallback(() => {
		const getTransitions = open
			? CommerceServiceProvider.DeliveryCartAPI('v1')
					.getCartTransitionsById
			: CommerceServiceProvider.DeliveryOrderAPI('v1')
					.getOrderTransitionsById;

		getTransitions(orderId)
			.then((response) => {
				setActions(response.items);
			})
			.catch((error) => {
				openToast({
					message:
						error.message ||
						Liferay.Language.get('an-unexpected-error-occurred'),
					type: 'danger',
				});
			});
	}, [orderId, open]);

	useEffect(() => getActions(), [getActions]);

	const onClick = (event, action) => {
		event.preventDefault();

		if (action.name === 'checkout') {
			handleOrderActionRedirect({
				checkoutURL,
				id: action.name,
				reorderURL,
			});
		}
		else {
			const executeTransitions = open
				? CommerceServiceProvider.DeliveryCartAPI('v1')
						.executeCartTransitionsById
				: CommerceServiceProvider.DeliveryOrderAPI('v1')
						.executeOrderTransitionsById;

			executeTransitions(orderId, action)
				.then((response) => {
					Liferay.fire('order-information-altered');

					if (open !== response?.open) {
						setOpen(response.open);
					}
					else {
						getActions();
					}

					if (action.name === 'reorder') {
						handleOrderActionRedirect({
							checkoutURL,
							id: response.name,
							orderId: response.orderId,
							reorderURL,
						});
					}
				})
				.catch((error) => {
					openToast({
						message:
							error.message ||
							Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						type: 'danger',
					});
				});
		}
	};

	return (
		<div className="align-items-center d-flex">
			{actions.map((action) => (
				<div key={action.name}>
					<ClayButton
						aria-label={action.label}
						className="mx-1"
						displayType="primary"
						onClick={(event) => onClick(event, action)}
					>
						{action.label}
					</ClayButton>
				</div>
			))}
		</div>
	);
}

export default OrderActions;
