/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {CommerceServiceProvider} from 'commerce-frontend-js';
import React, {useEffect, useState} from 'react';

const InfoBoxModalPaymentMethodInput = ({
	inputValue,
	orderId,
	setInputValue,
	setIsValid,
	setParseResponse,
	spritemap,
}) => {
	const [hasPaymentMethods, setHasPaymentMethods] = useState(false);
	const [paymentMethods, setPaymentMethods] = useState([]);

	useEffect(() => {
		setParseResponse(() => (field, response) => {
			return response['paymentMethodLabel'];
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		CommerceServiceProvider.DeliveryCartAPI('v1')
			.getCartPaymentMethodsPage(orderId)
			.then(({items}) => {
				const paymentMethodsAvailable = !!items.length;

				setHasPaymentMethods(paymentMethodsAvailable);
				setIsValid(paymentMethodsAvailable);
				setPaymentMethods(items);
			})
			.catch((error) => {
				setHasPaymentMethods(false);
				setIsValid(false);
				setPaymentMethods([]);

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
			{hasPaymentMethods ? (
				<ClayRadioGroup
					defaultValue={inputValue}
					id="infoBoxModalPaymentMethodInput"
					onChange={(value) => {
						setInputValue(value);
					}}
				>
					{paymentMethods.map((paymentMethod) => (
						<ClayRadio
							key={paymentMethod.key}
							label={paymentMethod.name}
							value={paymentMethod.key}
						/>
					))}
				</ClayRadioGroup>
			) : (
				<ClayAlert displayType="info" spritemap={spritemap}>
					{Liferay.Language.get(
						'there-are-no-available-payment-methods'
					)}
				</ClayAlert>
			)}
		</>
	);
};

export default InfoBoxModalPaymentMethodInput;
