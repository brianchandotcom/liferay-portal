/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../contexts/StateContext';
import {Space} from '../types/Space';
import ApiHelper from './ApiHelper';

async function getSpace(externalReferenceCode: string): Promise<Space> {
	return await ApiHelper.get(
		`/o/headless-asset-library/v1.0/asset-libraries/by-external-reference-code/${externalReferenceCode}`
	);
}

async function getSpaces(): Promise<Space[]> {
	const {items} = await ApiHelper.get(
		'/o/headless-asset-library/v1.0/asset-libraries'
	);

	return items;
}

async function addSpace({name}: {name: State['name']}) {
	return await ApiHelper.post(
		'/o/headless-asset-library/v1.0/asset-libraries',
		{
			name,
		}
	);
}

export default {
	addSpace,
	getSpace,
	getSpaces,
};
