/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type AccountBrief = {id: string; logoURL?: string; name: string};

export type Authorization = {
	authorized: boolean;
	data: null | {
		serviceURL: string;
		settings: any;
		url: string;
	};
	loading: boolean;
};

export type MyUserAccount = {
	accountBriefs: AccountBrief[];
	id: number;
	name: string;
};

export type MarketplaceSettingsProps = {
	baseResourceURL: string;
	clientId: string;
	learnResources: any;
	portletNamespace: string;
	redirect: string;
	url: string;
};
