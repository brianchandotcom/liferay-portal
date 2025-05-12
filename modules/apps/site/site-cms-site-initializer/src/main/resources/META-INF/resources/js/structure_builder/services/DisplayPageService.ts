/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config';
import {State} from '../contexts/StateContext';
import ApiHelper from './ApiHelper';

async function resetDisplayPage({id}: {id: State['id']}) {
	const url = new URL(config.resetStructureDisplayPageURL);

	url.searchParams.set('objectDefinitionId', String(id));

	return await ApiHelper.post(url.toString());
}

export default {
	resetDisplayPage,
};
