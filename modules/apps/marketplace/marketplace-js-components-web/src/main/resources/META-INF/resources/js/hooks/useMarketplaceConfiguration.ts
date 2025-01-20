/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createResourceURL, fetch} from 'frontend-js-web';
import {useEffect, useState} from 'react';

import {MarketplaceConfiguration} from '../types';

type MarketplaceConfigurationResponse = {
	authorized: boolean;
	data: null | MarketplaceConfiguration;
	loading: boolean;
};

export function useMarketplaceConfiguration(baseResourceURL: string) {
	const [configuration, setConfiguration] =
		useState<MarketplaceConfigurationResponse>({
			authorized: false,
			data: null,
			loading: false,
		});

	useEffect(() => {
		const getConfiguration = async () => {
			const response = await fetch(
				createResourceURL(baseResourceURL, {
					p_p_resource_id: '/marketplace_settings/get_configuration',
				}).toString()
			);

			if (response.ok) {
				const {authorized, data} = await response.json();

				return setConfiguration({
					authorized,
					data,
					loading: false,
				});
			}

			setConfiguration((prevAuthorization) => ({
				...prevAuthorization,
				loading: false,
			}));
		};

		getConfiguration();
	}, [baseResourceURL]);

	return configuration;
}
