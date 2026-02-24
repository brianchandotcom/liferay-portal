/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelper} from '@liferay/site-cms-site-initializer';

import {IAccount, IRoomObjectEntry} from '../utils/types';

const BASE_PATH = '/o/digital-sales-room/rooms';

async function addRoom({
	accountEntryId,
	friendlyURL,
	name,
	siteTemplateKey,
}: {
	accountEntryId: number;
	friendlyURL: string;
	name: string;
	siteTemplateKey?: string;
}): Promise<IRoomObjectEntry> {
	const {data, error} = await ApiHelper.post<IRoomObjectEntry>(BASE_PATH, {
		friendlyURL,
		name,
		r_accountToDSRRooms_accountEntryId: accountEntryId,
		siteTemplateKey: siteTemplateKey || '',
	});

	if (data) {
		return data;
	}

	throw new Error(error);
}

async function getAccounts(
	accountName?: string
): Promise<{items: Array<IAccount>}> {
	let url = '/o/headless-admin-user/v1.0/accounts?sort=name:asc';

	if (accountName) {
		url += `&search=${encodeURIComponent(accountName)}`;
	}

	const {data, error} = await ApiHelper.get<{items: Array<IAccount>}>(url);

	if (data) {
		return data || {items: []};
	}

	throw new Error(error);
}

async function getRoom(id: number): Promise<IRoomObjectEntry> {
	const {data, error} = await ApiHelper.get<IRoomObjectEntry>(
		`${BASE_PATH}/${id}`
	);

	if (data) {
		return data;
	}

	throw new Error(error);
}

export default {
	addRoom,
	getAccounts,
	getRoom,
};
