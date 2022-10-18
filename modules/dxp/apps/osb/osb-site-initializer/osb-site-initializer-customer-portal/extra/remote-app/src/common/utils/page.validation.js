/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
