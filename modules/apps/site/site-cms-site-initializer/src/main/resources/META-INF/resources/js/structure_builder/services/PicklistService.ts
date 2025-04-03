/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../contexts/PicklistBuilderContext';
import {Picklist} from '../types/Picklist';
import ApiHelper from './ApiHelper';

async function createPicklist({
	erc,
	name: name_i18n,
}: {
	erc: State['erc'];
	name: State['name'];
}): Promise<Picklist> {
	return await ApiHelper.post(
		`/o/headless-admin-list-type/v1.0/list-type-definitions`,
		{
			externalReferenceCode: erc,
			name_i18n,
		}
	);
}

async function getPicklists(): Promise<Picklist[]> {
	const {items} = await ApiHelper.get(
		'/o/headless-admin-list-type/v1.0/list-type-definitions'
	);

	return items;
}

async function updatePicklist({
	erc,
	id,
	name: name_i18n,
}: {
	erc?: State['erc'];
	id: State['id'];
	name?: State['name'];
}) {
	await ApiHelper.put(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${id}`,
		{
			externalReferenceCode: erc,
			name_i18n,
		}
	);
}

export default {
	createPicklist,
	getPicklists,
	updatePicklist,
};
