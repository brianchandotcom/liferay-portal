/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
