/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {deleteExperienceById, removeLayoutDataItemById} from './utils';

function deleteExperienceReducer(state, payload) {
	let nextState = state;
	const {segmentsExperienceId} = payload;

	nextState = removeLayoutDataItemById(nextState, segmentsExperienceId);
	nextState = deleteExperienceById(nextState, segmentsExperienceId);

	return nextState;
}

export default deleteExperienceReducer;
