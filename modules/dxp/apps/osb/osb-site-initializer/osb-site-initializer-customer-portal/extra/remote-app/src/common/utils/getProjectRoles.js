/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getAccountRoles} from '../services/liferay/graphql/queries';
import {ROLE_TYPES, SLA_TYPES} from './constants';

const getCurrentRoleType = (roleKey) => {
	const roleValues = Object.values(ROLE_TYPES);

	return roleValues.find((roleType) => roleType.key === roleKey);
};

export function getRolesFiltered(items, project) {
	const projectHasSLAGoldPlatinum =
		project?.slaCurrent?.includes(SLA_TYPES.gold) ||
		project?.slaCurrent?.includes(SLA_TYPES.platinum);

	const isProjectPartner = project?.partner;

	if (items) {
		const roles = items?.reduce((rolesAccumulator, role) => {
			let isValidRole = true;

			const roleType = getCurrentRoleType(role.name);

			if (roleType?.raysourceName) {
				if (!projectHasSLAGoldPlatinum) {
					isValidRole = role.name !== ROLE_TYPES.requester.key;
				}

				if (!isProjectPartner) {
					isValidRole =
						role.name !== ROLE_TYPES.partnerManager.key &&
						role.name !== ROLE_TYPES.partnerMember.key;
				}

				if (isValidRole) {
					rolesAccumulator.push({
						...role,
						key: roleType?.key,
						name: roleType?.name,
						raysourceName: roleType?.raysourceName,
					});
				}
			}

			return rolesAccumulator;
		}, []);

		return roles;
	}
}

export default async function getProjectRoles(client, project) {
	const {data} = await client.query({
		fetchPolicy: 'network-only',
		query: getAccountRoles,
		variables: {
			accountId: project.id,
		},
	});

	if (data) {
		return getRolesFiltered(data.accountAccountRoles.items ?? [], project);
	}
}
