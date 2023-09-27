/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import {useCallback, useState} from 'react';

import {headers} from '../rest/fetch.es';

const useIsAdmin = () => {
	const [isAdmin, setAdmin] = useState();

	const fetchData = useCallback(async () => {
		const fetchURL = new URL(
			'/o/headless-admin-user/v1.0/my-user-account',
			Liferay.ThemeDisplay.getPortalURL()
		);

		const response = await fetch(fetchURL, {
			headers,
			method: 'GET',
		});

		const data = await response.json();

		if (response.ok) {
			setAdmin(data);

			const callback = (currentUserAccount) => {
				setAdmin(
					currentUserAccount?.roleBriefs?.some(
						(role) => role.name === 'Administrator'
					)
				);
			};

			return callback(data);
		}

		throw data;
	}, []);

	return {
		fetchData,
		isAdmin,
	};
};

export {useIsAdmin};
