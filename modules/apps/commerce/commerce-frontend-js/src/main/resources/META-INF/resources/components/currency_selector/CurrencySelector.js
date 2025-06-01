/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import {openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import ServiceProvider from '../../ServiceProvider';
import {CURRENT_ORDER_UPDATED} from '../../utilities/eventsDefinitions';
import {confirmCurrencyChange as confirmChangeAndRedirect} from '../../utilities/modals/confirmCurrencyChange';
import {retrieveCommerceCurrency, storeCommerceCurrency} from './util';

function CurrencySelector({
	commerceChannelId,
	commerceOrderDetailBaseURL,
	commerceOrderId,
}) {
	const DeliveryCatalogResource = useMemo(
		() => ServiceProvider.DeliveryCatalogAPI('v1'),
		[]
	);

	const [activeOrderId, setActiveOrderId] = useState(
		parseInt(commerceOrderId, 10)
	);
	const [availableCurrencies, setAvailableCurrencies] = useState(null);
	const [selectedCurrency, setSelectedCurrency] = useState(null);

	const setCurrencyCookie = useCallback(() => {
		const currentCommerceCurrencyCode = retrieveCommerceCurrency();

		if (!currentCommerceCurrencyCode) {
			storeCommerceCurrency(selectedCurrency.code);

			return;
		}

		const hasCurrencyChanged =
			currentCommerceCurrencyCode !== selectedCurrency.code;

		if (hasCurrencyChanged && activeOrderId) {
			const {accountId} = Liferay.CommerceContext.account;

			confirmChangeAndRedirect({
				accountId,
				commerceChannelId,
				currencyCode: selectedCurrency.code,
				onCancel: () =>
					setSelectedCurrency(
						availableCurrencies.find(
							({code}) => code === currentCommerceCurrencyCode
						)
					),
				orderDetailURL: commerceOrderDetailBaseURL,
			});
		}
		else if (hasCurrencyChanged && !activeOrderId) {
			storeCommerceCurrency(selectedCurrency.code);

			window.location.reload();
		}
	}, [
		activeOrderId,
		availableCurrencies,
		commerceChannelId,
		commerceOrderDetailBaseURL,
		selectedCurrency,
	]);

	useEffect(() => {
		if (availableCurrencies === null) {
			DeliveryCatalogResource.getCurrenciesByChannelId(commerceChannelId)
				.then(({items: currencies}) => {
					if (currencies.length) {
						const currencyCode =
							retrieveCommerceCurrency() ||
							Liferay.CommerceContext.currency.currencyCode;

						setSelectedCurrency(
							currencies.find(({code}) => code === currencyCode)
						);
						setAvailableCurrencies(currencies);
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

		return () => {};
	}, [availableCurrencies, commerceChannelId, DeliveryCatalogResource]);

	useEffect(() => {
		if (selectedCurrency?.id) {
			setCurrencyCookie();
		}

		return () => {};
	}, [selectedCurrency, setCurrencyCookie]);

	useEffect(() => {
		function onOrderUpdate({order: {currencyCode, id}}) {
			if (id !== activeOrderId) {
				setActiveOrderId(id);

				setSelectedCurrency(
					availableCurrencies.find(({code}) => code === currencyCode)
				);
			}
		}

		Liferay.on(CURRENT_ORDER_UPDATED, onOrderUpdate);

		return () => {
			Liferay.detach(CURRENT_ORDER_UPDATED, onOrderUpdate);
		};
	}, [
		activeOrderId,
		availableCurrencies,
		setActiveOrderId,
		setSelectedCurrency,
	]);

	return availableCurrencies?.length ? (
		<>
			<DropDown
				items={availableCurrencies.filter(({active}) => active)}
				trigger={
					<ClayButton
						className="border-0 btn-sm"
						displayType="secondary"
					>
						{selectedCurrency.symbol} {selectedCurrency.code}
					</ClayButton>
				}
			>
				{(currency) => (
					<DropDown.Item
						active={currency.id === selectedCurrency.id}
						data-testid={currency.code}
						key={currency.id}
						onClick={() => setSelectedCurrency(currency)}
					>
						{currency.symbol} {currency.code}
					</DropDown.Item>
				)}
			</DropDown>
		</>
	) : null;
}

export default CurrencySelector;
