/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getAccountFlags} from '../services/liferay/graphql/queries';
import getLiferaySiteName from '../utils/getLiferaySiteName';
import {API_BASE_URL, PAGE_ROUTER_TYPES, ROUTE_TYPES} from './constants';

const BASE_API = `${API_BASE_URL}/${getLiferaySiteName()}`;

const getHomeLocation = () => BASE_API;

const getOnboardingLocation = (externalReferenceCode) =>
	PAGE_ROUTER_TYPES.onboarding(externalReferenceCode);

const getOverviewLocation = (externalReferenceCode) => {
	return PAGE_ROUTER_TYPES.project(externalReferenceCode);
};

const isValidPage = async (
	client,
	userAccount,
	externalReferenceCode,
	pageKey
) => {
	const {data} = await client.query({
		fetchPolicy: 'network-only',
		query: getAccountFlags,
		variables: {
			filter: `accountKey eq '${externalReferenceCode}' and name eq '${ROUTE_TYPES.onboarding}' and finished eq true`,
		},
	});

	if (data) {
		const hasAccountFlags = !!data.c?.accountFlags?.items?.length;
		const isAccountAdministrator = userAccount.isAdmin;

		if (pageKey === ROUTE_TYPES.onboarding) {
			if (!(isAccountAdministrator && !hasAccountFlags)) {
				window.location.href =
					userAccount.accountBriefs.length === 1
						? getOverviewLocation(externalReferenceCode)
						: getHomeLocation();

				return false;
			}

			return true;
		}

		if (pageKey === ROUTE_TYPES.project) {
			if (isAccountAdministrator && !hasAccountFlags) {
				window.location.href = getOnboardingLocation(
					externalReferenceCode
				);

				return false;
			}

			return true;
		}
	}
};

export {isValidPage};
