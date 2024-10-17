/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayRadio, ClayRadioGroup, ClaySelect} from '@clayui/form';
import {CommerceServiceProvider} from 'commerce-frontend-js';
import React, {useEffect, useState} from 'react';

import '../../../css/index.scss';

import ClayIcon from '@clayui/icon';

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
	const [loading, setLoading] = useState(true);
	const [selectedShippingMethod, setSelectedShippingMethod] = useState(null);
	const [shippingMethodEngine, setShippingMethodEngine] = useState(
		inputValue ? inputValue.split('#').shift() : null
	);
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
		setSelectedShippingMethod(
			shippingMethods.find(
				(shippingMethod) =>
					shippingMethod.engineKey === shippingMethodEngine
			)
		);
	}, [shippingMethodEngine, shippingMethods]);

	useEffect(() => {
		setIsValid(inputValue && inputValue !== '#');
	}, [inputValue, setIsValid]);

	useEffect(() => {
		CommerceServiceProvider.DeliveryCartAPI('v1')
			.getCartShippingMethodsPage(orderId)
			.then(({items}) => {
				const shippingMethodsAvailable = items.find(
					(shippingMethod) => shippingMethod.shippingOptions.length
				);

				setHasShippingMethods(shippingMethodsAvailable !== undefined);
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
			})
			.finally(() => {
				setLoading(false);
			});
	}, [orderId, setIsValid]);

	return (
		<>
			{!loading && (
				<>
					{hasShippingMethods ? (
						<>
							<label htmlFor="infoBoxModalShippingMethodInput">
								{Liferay.Language.get('choose-carrier')}{' '}

								<span className="ml-1 reference-mark text-warning">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<ClaySelect
								data-qa-id="infoBoxModalShippingMethodInput"
								id="infoBoxModalShippingMethodInput"
								onChange={(event) => {
									setInputValue('#');
									setShippingMethodEngine(event.target.value);
								}}
								value={shippingMethodEngine || ''}
							>
								<ClaySelect.Option label="" value="" />

								{shippingMethods
									.filter(
										(shippingMethod) =>
											shippingMethod.shippingOptions
												.length
									)
									.map((shippingMethod) => (
										<ClaySelect.Option
											key={shippingMethod.id}
											label={shippingMethod.name}
											value={shippingMethod.engineKey}
										/>
									))}
							</ClaySelect>

							{selectedShippingMethod ? (
								<>
									<label
										className="mt-4"
										htmlFor="infoBoxModalShippingOptionInput"
									>
										{Liferay.Language.get(
											'carrier-options'
										)}{' '}

										<span className="ml-1 reference-mark text-warning">
											<ClayIcon symbol="asterisk" />
										</span>
									</label>

									<ClayRadioGroup
										defaultValue={inputValue}
										id="infoBoxModalShippingOptionInput"
										onChange={(value) => {
											setInputValue(value);
										}}
									>
										{selectedShippingMethod.shippingOptions.map(
											(shippingOption) => (
												<ClayRadio
													containerProps={{
														className:
															'shippingOptionItem',
													}}
													key={
														selectedShippingMethod.id +
														shippingOption.name
													}
													label={
														shippingOption.label +
														' (' +
														shippingOption.amountFormatted +
														')'
													}
													value={
														selectedShippingMethod.engineKey +
														'#' +
														shippingOption.name
													}
												/>
											)
										)}
									</ClayRadioGroup>
								</>
							) : (
								<></>
							)}
						</>
					) : (
						<ClayAlert displayType="info" spritemap={spritemap}>
							{Liferay.Language.get(
								'there-are-no-available-shipping-methods'
							)}
						</ClayAlert>
					)}
				</>
			)}
		</>
	);
};

export default InfoBoxModalShippingMethodInput;
