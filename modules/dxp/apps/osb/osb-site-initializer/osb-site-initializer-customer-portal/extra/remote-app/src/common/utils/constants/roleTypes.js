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

import i18n from '../../I18n';

export const ROLE_TYPES = {
	admin: {
		key: 'Administrator',
		name: i18n.translate('administrator'),
		raysourceName: 'Support Administrator',
	},
	member: {
		key: 'User',
		name: i18n.translate('user'),
		raysourceName: 'Support User',
	},
	partnerManager: {
		key: 'Partner Manager',
		name: i18n.translate('partner-manager'),
		raysourceName: 'Partner Manager',
	},
	partnerMember: {
		key: 'Partner Member',
		name: i18n.translate('partner-member'),
		raysourceName: 'Partner Member',
	},
	requester: {
		key: 'Requester',
		name: i18n.translate('requester'),
		raysourceName: 'Support Requester',
	},
};
