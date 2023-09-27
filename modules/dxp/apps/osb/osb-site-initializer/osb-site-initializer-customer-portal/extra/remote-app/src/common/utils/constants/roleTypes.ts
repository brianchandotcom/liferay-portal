/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
