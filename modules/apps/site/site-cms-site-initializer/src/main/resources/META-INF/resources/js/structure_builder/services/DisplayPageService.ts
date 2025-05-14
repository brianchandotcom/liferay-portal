/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams} from 'frontend-js-web';

import {config} from '../config';
import {State} from '../contexts/StateContext';
import ApiHelper from './ApiHelper';

async function resetDisplayPage({id}: {id: State['id']}) {
	const resetStructureDisplayPageURL = addParams(
		{
			objectDefinitionId: id,
		},
		config.resetStructureDisplayPageURL
	);

	return await ApiHelper.post(resetStructureDisplayPageURL);
}

export default {
	resetDisplayPage,
};
