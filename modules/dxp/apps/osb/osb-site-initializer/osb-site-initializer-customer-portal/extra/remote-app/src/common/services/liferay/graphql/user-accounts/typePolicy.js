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

export const userAccountsTypePolicy = {
	AccountBrief: {
		keyFields: false,
	},
	OrganizationBrief: {
		keyFields: false,
	},
	RoleBrief: {
		keyFields: ['id'],
	},
	UserAccount: {
		fields: {
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
		},
		keyFields: ['id'],
	},
};
