/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from './ApiHelper';

const PATH = '/o/headless-digital-sales-room/v1.0/digital-sales-rooms';
const TEMPLATE_PATH =
	'/o/headless-digital-sales-room/v1.0/digital-sales-room-templates';

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

export type TCommentDTO = {
	category: string;
	creator: {
		id: string;
		image: string;
		name: string;
	};
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	numberOfComments: number;
	parentCommentId: number;
	text: string;
};

export type TCommentsDTO = {
	items: Array<TCommentDTO>;
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

export type TDSRDTO = {
	accountId: number;
	accountName?: string;
	banner?: {
		fileBase64?: string;
		fileURL: string;
		id: number;
	};
	channelId: number;
	channelName?: string;
	clientLogo?: {
		fileBase64?: string;
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
	userAccountBriefs: Array<{
		emailAddress: string;
		roleId?: number;
	}>;
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
	userAccountBriefs?: Array<{
		emailAddress: string;
		roleKey?: string;
	}>;
};

export type TDSRTemplateDTO = {
	banner?: {
		fileBase64?: string;
		fileURL: string;
		id: number;
	};
	clientLogo?: {
		fileBase64?: string;
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
	usages?: number;
};

export type TDSRTemplatesDTO = {
	items: Array<TDSRTemplateDTO>;
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

export type TDSRTemplatePayload = {
	banner?: {
		fileBase64: string;
	};
	clientLogo?: {
		fileBase64: string;
	};
	clientName: string;
	description?: string;
	name: string;
	primaryColor?: string;
	secondaryColor?: string;
};

async function deleteDigitalSalesRoom(groupId: number) {
	return await ApiHelper.delete(`${PATH}/${groupId}`);
}

async function deleteDigitalSalesRoomTemplate(groupId: number) {
	return await ApiHelper.delete(`${TEMPLATE_PATH}/${groupId}`);
}

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

async function getDigitalSalesRoomComments(
	digitalSalesRoomId: number,
	page: number = 1
): Promise<TCommentsDTO> {
	const {data, error} = await ApiHelper.get(
		`${PATH}/${digitalSalesRoomId}/comments?page=${page}`
	);

	if (data) {
		return data as TCommentsDTO;
	}

	throw new Error(error);
}

async function getDigitalSalesRoom(
	digitalSalesRoomId: number
): Promise<TDSRDTO> {
	const {data, error} = await ApiHelper.get(
		`${PATH}/${digitalSalesRoomId}?nestedFields=fileBase64`
	);

	if (data) {
		return data as TDSRDTO;
	}

	throw new Error(error);
}

async function getDigitalSalesRoomTemplate(
	digitalSalesRoomTemplateId: number
): Promise<TDSRTemplateDTO> {
	const {data, error} = await ApiHelper.get(
		`${TEMPLATE_PATH}/${digitalSalesRoomTemplateId}?nestedFields=fileBase64`
	);

	if (data) {
		return data as TDSRTemplateDTO;
	}

	throw new Error(error);
}

async function getDigitalSalesRoomTemplates(): Promise<TDSRTemplatesDTO> {
	const {data, error} = await ApiHelper.get(
		`${TEMPLATE_PATH}?nestedFields=fileBase64`
	);

	if (data) {
		return data as TDSRTemplatesDTO;
	}

	throw new Error(error);
}

async function patchDigitalSalesRoom(
	digitalSalesRoomId: number,
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
		userAccountBriefs,
	}: TDSRPayload
): Promise<TDSRDTO> {
	const {data, error} = await ApiHelper.patch(
		`${PATH}/${digitalSalesRoomId}`,
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
			userAccountBriefs,
		}
	);

	if (data) {
		return data as TDSRDTO;
	}

	throw new Error(error);
}

async function patchDigitalSalesRoomTemplate(
	digitalSalesRoomTemplateId: number,
	{
		banner,
		clientLogo,
		clientName,
		description,
		name,
		primaryColor,
		secondaryColor,
	}: TDSRTemplatePayload
): Promise<TDSRTemplateDTO> {
	const {data, error} = await ApiHelper.patch(
		`${TEMPLATE_PATH}/${digitalSalesRoomTemplateId}`,
		{
			banner,
			clientLogo,
			clientName,
			description,
			name,
			primaryColor,
			secondaryColor,
		}
	);

	if (data) {
		return data as TDSRTemplateDTO;
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
	userAccountBriefs,
}: TDSRPayload): Promise<TDSRDTO> {
	const {data, error} = await ApiHelper.post(PATH, {
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
		userAccountBriefs,
	});

	if (data) {
		return data as TDSRDTO;
	}

	throw new Error(error);
}

async function postDigitalSalesRoomDigitalSalesRoomTemplate(
	digitalSalesRoomId: number,
	{description, name}: Partial<TDSRPayload>
): Promise<TDSRDTO> {
	const {data, error} = await ApiHelper.post(
		`${PATH}/${digitalSalesRoomId}/digital-sales-room-templates`,
		{
			description,
			name,
		}
	);

	if (data) {
		return data as TDSRDTO;
	}

	throw new Error(error);
}

async function postDigitalSalesRoomTemplate({
	banner,
	clientLogo,
	clientName,
	description,
	name,
	primaryColor,
	secondaryColor,
}: TDSRTemplatePayload): Promise<TDSRTemplateDTO> {
	const {data, error} = await ApiHelper.post(TEMPLATE_PATH, {
		banner,
		clientLogo,
		clientName,
		description,
		name,
		primaryColor,
		secondaryColor,
	});

	if (data) {
		return data as TDSRTemplateDTO;
	}

	throw new Error(error);
}

async function postDigitalSalesRoomTemplateDigitalSalesRoom(
	digitalSalesRoomTemplateId: number,
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
		userAccountBriefs,
	}: TDSRPayload
): Promise<TDSRDTO> {
	const {data, error} = await ApiHelper.post(
		`${TEMPLATE_PATH}/${digitalSalesRoomTemplateId}/digital-sales-rooms`,
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
			userAccountBriefs,
		}
	);

	if (data) {
		return data as TDSRDTO;
	}

	throw new Error(error);
}

async function postDigitalSalesRoomTemplateDigitalSalesRoomTemplate(
	digitalSalesRoomTemplateId: number
): Promise<TDSRDTO> {
	const {data, error} = await ApiHelper.post(
		`${TEMPLATE_PATH}/${digitalSalesRoomTemplateId}/digital-sales-room-templates`,
		{}
	);

	if (data) {
		return data as TDSRDTO;
	}

	throw new Error(error);
}

export default {
	deleteDigitalSalesRoom,
	deleteDigitalSalesRoomTemplate,
	getAccounts,
	getChannels,
	getComments: getDigitalSalesRoomComments,
	getDigitalSalesRoom,
	getDigitalSalesRoomTemplate,
	getDigitalSalesRoomTemplates,
	patchDigitalSalesRoom,
	patchDigitalSalesRoomTemplate,
	postDigitalSalesRoom,
	postDigitalSalesRoomDigitalSalesRoomTemplate,
	postDigitalSalesRoomTemplate,
	postDigitalSalesRoomTemplateDigitalSalesRoom,
	postDigitalSalesRoomTemplateDigitalSalesRoomTemplate,
};
