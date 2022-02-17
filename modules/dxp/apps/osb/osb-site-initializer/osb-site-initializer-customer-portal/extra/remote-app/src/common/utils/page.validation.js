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

import client from '../../apolloClient';
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

const isValidPage = async (userAccount, externalReferenceCode, pageKey) => {
	const validateExternalReferenceCode = (
		accountBriefs,
		externalReferenceCode
	) => {
		const hasAccountBrief = !!accountBriefs.find(
			(accountBrief) =>
				accountBrief.externalReferenceCode === externalReferenceCode
		);

		return hasAccountBrief;
	};

	const {data} = await client.query({
		query: getAccountFlags,
		variables: {
			filter: `accountKey eq '${externalReferenceCode}' and name eq '${ROUTE_TYPES.onboarding}' and finished eq true`,
		},
	});

	if (data) {
		const isValidExternalReferenceCode = validateExternalReferenceCode(
			userAccount.accountBriefs,
			externalReferenceCode
		);
		const hasAccountFlags = !!data.c?.accountFlags?.items?.length;
		const isAccountAdministrator = userAccount.isAdmin;
		const hasRoleBriefAdministrator = userAccount?.roleBriefs?.some(
			(role) => role.name === 'Administrator'
		);

		if (pageKey === ROUTE_TYPES.onboarding) {
			if (
				!(
					isValidExternalReferenceCode &&
					isAccountAdministrator &&
					!hasAccountFlags
				)
			) {
				window.location.href =
					userAccount.accountBriefs.length === 1
						? getOverviewLocation(
								isValidExternalReferenceCode
									? externalReferenceCode
									: userAccount.accountBriefs[0]
						  )
						: getHomeLocation();

				return false;
			}

			return true;
		}

		if (pageKey === ROUTE_TYPES.project) {
			if (!isValidExternalReferenceCode && !hasRoleBriefAdministrator) {
				window.location.href = getHomeLocation();

				return false;
			}
			else if (!hasAccountFlags && isAccountAdministrator) {
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
