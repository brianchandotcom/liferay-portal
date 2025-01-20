/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createResourceURL, fetch} from 'frontend-js-web';
import {useEffect, useState} from 'react';

import {MarketplaceAuthorization} from '../types';

type Authorization = {
	data: null | MarketplaceAuthorization;
	loading: boolean;
};

export function useMarketplaceAuthorization(baseResourceURL: string) {
	const [authorization, setAuthorization] = useState<Authorization>({
		data: null,
		loading: false,
	});

	useEffect(() => {
		const getAuthorization = async () => {
			const response = await fetch(
				createResourceURL(baseResourceURL, {
					p_p_resource_id: '/marketplace_settings/get_authorization',
				}).toString()
			);

			if (response.ok) {
				const data =
					(await response.json()) as MarketplaceAuthorization;

				return setAuthorization({
					data,
					loading: false,
				});
			}

			setAuthorization((prevAuthorization) => ({
				...prevAuthorization,
				loading: false,
			}));
		};

		getAuthorization();
	}, [baseResourceURL]);

	return authorization;
}
