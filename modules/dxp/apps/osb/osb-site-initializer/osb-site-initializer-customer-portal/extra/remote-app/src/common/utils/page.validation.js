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

import {API_BASE_URL} from '.';
import client from '../../apolloClient';
import {getAccountFlags} from '../services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../services/liferay/search-params';
import {getLiferaySiteName} from '../services/liferay/utils';
import {ROUTES} from './constants';

const {PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE} = PARAMS_KEYS;

const BASE_API = `${API_BASE_URL}/${getLiferaySiteName()}`;

const getHomeLocation = () => BASE_API;

const getOnboardingLocation = (externalReferenceCode) =>
	`${BASE_API}/onboarding?${PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${externalReferenceCode}`;

const getOverviewLocation = (externalReferenceCode) => {
	return `${BASE_API}/overview?${PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${externalReferenceCode}`;
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
			filter: `accountKey eq '${externalReferenceCode}' and name eq '${ROUTES.ONBOARDING}' and finished eq true`,
		},
	});

	if (data) {
		const isValidExternalReferenceCode = validateExternalReferenceCode(
			userAccount.accountBriefs,
			externalReferenceCode
		);
		const hasAccountFlags = !!data.c?.accountFlags?.items?.length;

		if (pageKey === ROUTES.ONBOARDING) {
			const isAccountAdministrator = userAccount.isAdmin;

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

		if (pageKey === ROUTES.OVERVIEW) {
			if (!isValidExternalReferenceCode) {
				window.location.href = getHomeLocation();
			}
			else if (!hasAccountFlags) {
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
