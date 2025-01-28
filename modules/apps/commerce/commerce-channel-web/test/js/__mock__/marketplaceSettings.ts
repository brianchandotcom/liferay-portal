/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const marketplaceSettingsMock = {
	authorized: true,
	data: {
		serviceURL: 'https://backend.marketplace.liferay.com',
		settings: {
			account: {id: 123, name: 'Liferay Labs'},
			channelId: 123,
			cloudProject: 'exte5a2marketplace-extuat',
			references: {fragmentsFilter: '', paymentMethodFilter: ''},
			siteId: 123,
			userAccount: {id: 123, name: 'Ray'},
		},
		url: 'https://marketplace.liferay.com',
	},
	hasAuthorization: true,
};
