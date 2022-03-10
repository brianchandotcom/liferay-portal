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
import {getAccountRoles} from '../services/liferay/graphql/queries';
import {ROLE_TYPES, SLA_TYPES} from './constants';

export default async function getProjectRoles(project) {
	const projectHasSLAGoldPlatinum =
		project?.slaCurrent?.includes(SLA_TYPES.gold) ||
		project?.slaCurrent?.includes(SLA_TYPES.platinum);

	const getCurrentRoleType = (roleKey) => {
		const roleValues = Object.values(ROLE_TYPES);

		return roleValues.find((roleType) => roleType.key === roleKey);
	};

	const isProjectPartner = project.partner;
	const {data} = await client.query({
		query: getAccountRoles,
		variables: {
			accountId: project.id,
		},
	});

	if (data) {
		const roles = data.accountAccountRoles?.items?.reduce(
			(rolesAccumulator, role) => {
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
			},
			[]
		);

		return roles;
	}
}
