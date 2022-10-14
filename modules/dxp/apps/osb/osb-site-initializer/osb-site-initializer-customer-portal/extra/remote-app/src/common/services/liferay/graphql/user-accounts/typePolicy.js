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

import {Liferay} from '../..';
import isSupportSeatRole from '../../../../utils/isSupportSeatRole';

export const userAccountsTypePolicy = {
	AccountBrief: {
		keyFields: false,
	},
	OrganizationBrief: {
		keyFields: false,
	},
	RoleBrief: {
		fields: {
			name: {
				read(name) {
					if (name === 'Account Member') {
						return 'User';
					}

					if (name === 'Account Administrator') {
						return 'Administrator';
					}

					return name;
				},
			},
		},
		keyFields: ['id'],
	},
	UserAccount: {
		fields: {
			dateCreated: {
				read(dateCreated) {
					return new Date(dateCreated);
				},
			},
			isLiferayStaff: {
				read(_, {readField}) {
					return !!readField('organizationBriefs').some(
						(organizationBrief) =>
							readField('name', organizationBrief) ===
							'Liferay Staff'
					);
				},
			},
			isLoggedUser: {
				read(_, {readField}) {
					return (
						readField('id') === +Liferay.ThemeDisplay.getUserId()
					);
				},
			},
			isProvisioning: {
				read(_, {readField}) {
					return !!readField('roleBriefs')?.some(
						(roleBrief) =>
							readField('name', roleBrief) === 'Provisioning'
					);
				},
			},
			selectedAccountSummary: {
				read(_, {readField, variables: {externalReferenceCode}}) {
					const accountBriefRef = readField('accountBriefs')?.find(
						(accountBrief) =>
							readField('externalReferenceCode', accountBrief) ===
							externalReferenceCode
					);

					const roleBriefs = readField(
						'roleBriefs',
						accountBriefRef
					).map((roleBrief) => ({
						id: readField('id', roleBrief),
						name: readField('name', roleBrief),
					}));

					const hasAdministratorRole = roleBriefs.some(
						({name}) =>
							name === 'Administrator' ||
							name === 'Partner Manager'
					);

					const hasSupportSeatRole = roleBriefs.some(({name}) =>
						isSupportSeatRole(name)
					);

					return {
						hasAdministratorRole,
						hasSupportSeatRole,
						roleBriefs,
					};
				},
			},
		},
		keyFields: ['id'],
	},
};
