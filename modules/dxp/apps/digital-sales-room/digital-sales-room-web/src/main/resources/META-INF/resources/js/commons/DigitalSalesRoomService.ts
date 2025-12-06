/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from './ApiHelper';

export type TAccountDTO = {
	externalReferenceCode: string;
	id: number;
	name: string;
	status: number;
	type: string;
};

export type TAccountsDTO = {
	items: Array<TAccountDTO>;
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

export type TChannelDTO = {
	externalReferenceCode: string;
	id: number;
	name: string;
};

export type TChannelsDTO = {
	items: Array<TChannelDTO>;
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

export type TDSRDTO = {
	accountId: number;
	accountName?: string;
	banner?: {
		fileURL: string;
		id: number;
	};
	channelId: number;
	clientLogo?: {
		fileURL: string;
		id: number;
	};
	clientName: string;
	createDate: string;
	description?: string;
	externalReferenceCode: string;
	friendlyUrlPath: string;
	id: number;
	modifiedDate: string;
	name: string;
	ownerId: number;
	ownerName: string;
	primaryColor?: string;
	secondaryColor?: string;
};

export type TDSRPayload = {
	accountId: number;
	banner?: {
		fileBase64: string;
	};
	channelId: number;
	channelName?: string;
	clientLogo?: {
		fileBase64: string;
	};
	clientName: string;
	description?: string;
	friendlyUrlPath: string;
	name: string;
	primaryColor?: string;
	secondaryColor?: string;
};

async function getAccounts(accountName = ''): Promise<TAccountsDTO> {
	const {data, error} = await ApiHelper.get(
		`/o/headless-admin-user/v1.0/accounts?filter=contains(name, '${accountName}')`
	);

	if (data) {
		return data as TAccountsDTO;
	}

	throw new Error(error);
}

async function getChannels(channelName = ''): Promise<TChannelsDTO> {
	const {data, error} = await ApiHelper.get(
		`/o/headless-commerce-delivery-catalog/v1.0/channels?filter=contains(name,'${channelName}')`
	);

	if (data) {
		return data as TChannelsDTO;
	}

	throw new Error(error);
}

async function postDigitalSalesRoom({
	accountId,
	banner,
	channelId,
	channelName,
	clientLogo,
	clientName,
	description,
	friendlyUrlPath,
	name,
	primaryColor,
	secondaryColor,
}: TDSRPayload): Promise<TDSRDTO> {
	const {data, error} = await ApiHelper.post(
		`/o/headless-digital-sales-room/v1.0/digital-sales-rooms`,
		{
			accountId,
			banner,
			channelId,
			channelName,
			clientLogo,
			clientName,
			description,
			friendlyUrlPath,
			name,
			primaryColor,
			secondaryColor,
		}
	);

	if (data) {
		return data as TDSRDTO;
	}

	throw new Error(error);
}

export default {
	getAccounts,
	getChannels,
	postDigitalSalesRoom,
};
