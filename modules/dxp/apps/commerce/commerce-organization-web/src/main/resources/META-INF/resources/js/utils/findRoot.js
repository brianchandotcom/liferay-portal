/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getAccount} from '../data/accounts';
import {getOrganization} from '../data/organizations';
import {getUser} from '../data/users';
import {MODEL_TYPE_MAP} from './constants';

async function _doFindRoot(id = 0, type = '') {
	if (!id || !type) {
		return Promise.reject();
	}

	if (type === MODEL_TYPE_MAP.organization) {
		return getOrganization(id).then(({parentOrganization = {}}) =>
			parentOrganization?.id
				? _doFindRoot(
						parentOrganization.id,
						MODEL_TYPE_MAP.organization
					)
				: Promise.resolve(Number(id))
		);
	}

	if (type === MODEL_TYPE_MAP.account) {
		return getAccount(id).then(({organizationIds: parentOrganizations}) =>
			Promise.all(
				parentOrganizations.map((id) =>
					_doFindRoot(id, MODEL_TYPE_MAP.organization)
				)
			)
		);
	}

	if (type === MODEL_TYPE_MAP.user) {
		return getUser(id).then(
			({
				accountBriefs: parentAccounts,
				organizationBriefs: parentOrganizations,
			}) => {
				const parents = [
					...parentOrganizations.map(({id}) => ({
						id,
						type: MODEL_TYPE_MAP.organization,
					})),
					...parentAccounts.map(({id}) => ({
						id,
						type: MODEL_TYPE_MAP.account,
					})),
				];

				return Promise.all(
					parents.map(({id, type}) => _doFindRoot(id, type))
				).then((roots) =>
					Promise.resolve(
						(Array.isArray(roots[0]) ? roots[0] : roots).map(Number)
					)
				);
			}
		);
	}
}

export async function findRoot(id, type) {
	return _doFindRoot(id, type).then((response) =>
		Promise.resolve(Array.isArray(response) ? response : [response])
	);
}
