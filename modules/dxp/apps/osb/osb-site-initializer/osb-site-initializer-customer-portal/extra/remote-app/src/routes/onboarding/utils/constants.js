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

const steps = {
	dxpCloud: 2,
	invites: 1,
	successDxpCloud: 3,
	welcome: 0,
};

const roles = {
	ADMIN: {
		key: 'Account Administrator',
		name: 'Administrator',
	},
	MEMBER: {
		key: 'Account Member',
		name: 'User',
	},
	PARTNER_MANAGER: {
		key: 'Partner Manager',
		name: 'Partner Manager',
	},
	PARTNER_MEMBER: {
		key: 'Partner Member',
		name: 'Partner Member',
	},
	REQUESTOR: {
		key: 'Requestor',
		name: 'Requestor',
	},
};

const getInitialDxpAdmin = () => ({
	email: '',
	firstName: '',
	github: '',
	lastName: '',
});

const getInitialInvite = (roleId = '') => ({
	email: '',
	roleId,
});

export {steps, getInitialInvite, getInitialDxpAdmin, roles};
