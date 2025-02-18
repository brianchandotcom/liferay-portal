/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

const DeliveryCatalogResource =
	CommerceServiceProvider.DeliveryCatalogAPI('v1');

function CurrencySelector({commerceChannelId}) {
	const [availableCurrencies, setAvailableCurrencies] = useState(null);

	const getCurrencyFromURL = useCallback(() => {
		if (availableCurrencies !== null) {
			const currentURL = new URL(window.location.href);

			const currentCurrencyCode =
				currentURL.searchParams.get('currency-code');

			return {
				code: currentCurrencyCode,
				symbol: availableCurrencies.find(
					(item) => item.code === currentCurrencyCode
				)?.symbol,
			};
		}

		return {
			code: '',
			symbol: '',
		};
	}, [availableCurrencies]);

	const setCurrentCurrency = (item) => {
		const currentCurrency = getCurrencyFromURL();

		if (currentCurrency.code !== item.code) {
			const currentURL = new URL(window.location.href);

			currentURL.searchParams.set('currency-code', item.code);

			window.location.href = currentURL.toString();
		}
	};

	useEffect(() => {
		if (availableCurrencies === null) {
			DeliveryCatalogResource.getCurrenciesByChannelId(commerceChannelId)
				.then((response) => {
					if (response.items.length) {
						setAvailableCurrencies(response.items);
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
	}, [availableCurrencies, commerceChannelId]);

	const {code, symbol} = getCurrencyFromURL();

	return (
		availableCurrencies?.length && (
			<>
				<DropDown
					items={availableCurrencies}
					trigger={
						<ClayButton>
							{symbol} {code}
						</ClayButton>
					}
				>
					{(item) => (
						<DropDown.Item
							active={item.active}
							key={item.id}
							onClick={() => {
								setCurrentCurrency(item);
							}}
						>
							{item.symbol} {item.code}
						</DropDown.Item>
					)}
				</DropDown>
			</>
		)
	);
}

export default CurrencySelector;
