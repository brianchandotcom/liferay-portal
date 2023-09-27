/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_SEGMENTS_EXPERIENCES_LIST} from '../actions';

export default function updateExperiencesList(availableSegmentsExperiences) {
	return {
		payload: {
			availableSegmentsExperiences,
		},
		type: UPDATE_SEGMENTS_EXPERIENCES_LIST,
	};
}
