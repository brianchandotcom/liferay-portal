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

export const userAccountsTypePolicy = {
	AccountBrief: {
		keyFields: ['externalReferenceCode'],
	},
	OrganizationBrief: {
		keyFields: ['id'],
	},
	RoleBrief: {
		keyFields: ['id'],
	},
	UserAccount: {
		fields: {
			hasProvisioningRole: {
				read(_, {readField}) {
					return !!readField('roleBriefs')?.some(
						(roleBrief) =>
							readField('name', roleBrief) === 'Provisioning'
					);
				},
			},
			isLiferayStaff: {
				read(_, {readField}) {
					return !!readField('organizationBriefs')?.some(
						(organizationBrief) =>
							readField('name', organizationBrief) ===
							'Liferay Staff'
					);
				},
			},
		},
		keyFields: ['id'],
	},
};
