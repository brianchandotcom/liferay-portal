/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SELECT_SEGMENTS_EXPERIENCE} from '../actions';

export default function selectExperience({
	fragmentEntryLinks = {},
	portletIds = [],
	segmentsExperienceId,
}) {
	return {
		payload: {
			fragmentEntryLinks,
			portletIds,
			segmentsExperienceId,
		},
		type: SELECT_SEGMENTS_EXPERIENCE,
	};
}
