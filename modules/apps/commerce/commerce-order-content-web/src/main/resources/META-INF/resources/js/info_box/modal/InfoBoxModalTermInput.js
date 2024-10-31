/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClaySelect} from '@clayui/form';
import {CommerceServiceProvider} from 'commerce-frontend-js';
import React, {useCallback, useEffect, useState} from 'react';

import '../../../css/index.scss';

const InfoBoxModalTermInput = ({
	additionalProps: {termDescription},
	field,
	inputValue,
	isOpen,
	orderId,
	setInputValue,
	setIsValid,
	setParseResponse,
	spritemap,
}) => {
	const [selectedTermDescription, setSelectedTermDescription] =
		useState(termDescription);
	const [terms, setTerms] = useState([]);

	const getTermsPage = useCallback(
		(orderId) => {
			if (field === 'deliveryTermId') {
				return CommerceServiceProvider.DeliveryCartAPI(
					'v1'
				).getCartDeliveryTermsPage(orderId);
			}
			else if (field === 'paymentTermId') {
				return CommerceServiceProvider.DeliveryCartAPI(
					'v1'
				).getCartPaymentTermsPage(orderId);
			}

			return Promise.resolve({terms: []});
		},
		[field]
	);

	const handleTermChange = (event) => {
		const selectedId = Number(event.target.value);

		setInputValue(selectedId);

		const selectedTerm = terms.find((term) => term.id === selectedId);

		setSelectedTermDescription(
			selectedTerm ? selectedTerm.description : ''
		);
	};

	useEffect(() => {
		setParseResponse(() => (field, response) => {
			if (field === 'deliveryTermId') {
				return response['deliveryTermLabel'];
			}
			else if (field === 'paymentTermId') {
				return response['paymentTermLabel'];
			}

			return '';
		});
	}, [field, setParseResponse]);

	useEffect(() => {
		if (terms.length === 1 && !inputValue) {
			setInputValue(terms[0].id);
		}
		if (terms.length && inputValue) {
			const selectedTerm = terms.find(
				(term) => term.id === Number(inputValue)
			);

			setSelectedTermDescription(
				selectedTerm ? selectedTerm.description : ''
			);
		}
	}, [terms, inputValue, setInputValue]);

	useEffect(() => {
		getTermsPage(orderId)
			.then((response) => {
				const terms = response.items || [];

				if (terms.length === 1) {
					setSelectedTermDescription(terms[0].description);
				}

				setIsValid(!!terms.length);
				setTerms(terms);
			})
			.catch((error) => {
				setIsValid(false);
				setTerms([]);

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
	}, [getTermsPage, field, orderId, setIsValid]);

	return (
		<>
			{!!terms.length && isOpen && (
				<>
					<ClaySelect
						className="mb-3"
						id={`${field}_infoBoxModalTermInput`}
						name={orderId}
						onChange={handleTermChange}
					>
						{terms.length > 1 && (
							<ClaySelect.Option label="" value="" />
						)}

						{terms.map((term) => (
							<ClaySelect.Option
								aria-label={term.label}
								key={term.id}
								label={term.label}
								selected={term.id === Number(inputValue)}
								value={term.id}
							/>
						))}
					</ClaySelect>
					{selectedTermDescription && (
						<div
							className="term-description"
							dangerouslySetInnerHTML={{
								__html: selectedTermDescription,
							}}
						/>
					)}
				</>
			)}
			{!!terms.length && !isOpen && (
				<div
					className="term-description"
					dangerouslySetInnerHTML={{
						__html: selectedTermDescription,
					}}
				/>
			)}
			{!terms.length && (
				<ClayAlert displayType="info" spritemap={spritemap}>
					{field === 'deliveryTermId'
						? Liferay.Language.get(
								'there-are-no-available-delivery-terms'
							)
						: Liferay.Language.get(
								'there-are-no-available-payment-terms'
							)}
				</ClayAlert>
			)}
		</>
	);
};

export default InfoBoxModalTermInput;
