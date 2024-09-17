/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {CommerceServiceProvider} from 'commerce-frontend-js';
import React, {useEffect, useState} from 'react';

const InfoBoxModalShippingMethodInput = ({
	inputValue,
	orderId,
	setInputValue,
	setIsValid,
	setParseRequest,
	setParseResponse,
	spritemap,
}) => {
	const [hasShippingMethods, setHasShippingMethods] = useState(false);
	const [shippingMethods, setShippingMethods] = useState([]);

	useEffect(() => {
		setParseRequest(() => (field, inputValue) => {
			const keys = inputValue.split('#');

			return {
				[field]: keys[0],
				shippingOption: keys[1],
			};
		});
		setParseResponse(() => (field, response) => {
			return response[field] + ' - ' + response['shippingOption'];
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		CommerceServiceProvider.DeliveryCartAPI('v1')
			.getCartShippingMethodsPage(orderId)
			.then(({items}) => {
				const shippingMethodsAvailable = items.find(
					(shippingMethod) => shippingMethod.shippingOptions.length
				);

				setHasShippingMethods(shippingMethodsAvailable !== undefined);
				setIsValid(shippingMethodsAvailable !== undefined);
				setShippingMethods(items);
			})
			.catch((error) => {
				setHasShippingMethods(false);
				setIsValid(false);
				setShippingMethods([]);

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
	}, [orderId, setIsValid]);

	return (
		<>
			{hasShippingMethods ? (
				<ClayRadioGroup
					defaultValue={inputValue}
					id="infoBoxModalShippingMethodInput"
					onChange={(value) => {
						setInputValue(value);
					}}
				>
					{shippingMethods.map((shippingMethod) => {
						return shippingMethod.shippingOptions.map(
							(shippingOption) => (
								<ClayRadio
									key={
										shippingMethod.id + shippingOption.name
									}
									label={
										shippingOption.label +
										' (' +
										shippingOption.amountFormatted +
										')'
									}
									value={
										shippingMethod.engineKey +
										'#' +
										shippingOption.name
									}
								/>
							)
						);
					})}
				</ClayRadioGroup>
			) : (
				<ClayAlert displayType="info" spritemap={spritemap}>
					{Liferay.Language.get(
						'there-are-no-available-shipping-methods'
					)}
				</ClayAlert>
			)}
		</>
	);
};

export default InfoBoxModalShippingMethodInput;
