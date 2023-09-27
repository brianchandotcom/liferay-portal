/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LEGAL_ENTITIES, US_STATES} from './data';

/**
 * @returns {Promise<{
 * name: string
 * abbreviation: string
 * }[]>} Array with all US states
 */
const getUSStates = () =>
	new Promise((resolve) => {
		resolve(US_STATES);
	});

const getLegalEntities = () =>
	new Promise((resolve) => {
		resolve(LEGAL_ENTITIES);
	});

export const MockService = {
	getLegalEntities,
	getUSStates,
};
