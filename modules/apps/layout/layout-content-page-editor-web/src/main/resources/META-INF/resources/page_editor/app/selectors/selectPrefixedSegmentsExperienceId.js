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

import {SEGMENTS_EXPERIENCE_ID_PREFIX} from '../config/constants/segmentsExperienceIdPrefix';
import {config} from '../config/index';

/**
 * Returns the selected segmentsExperienceId or the default one.
 * Warning: this function may return an empty string if the editor is
 * being used in an environment without experiences (ex. Page Templates).
 *
 * @param {object} state
 * @return {string}
 */
export default function selectPrefixedSegmentsExperienceId(state) {
	const segmentsExperienceId =
		state.segmentsExperienceId || config.defaultSegmentsExperienceId;

	return segmentsExperienceId
		? `${SEGMENTS_EXPERIENCE_ID_PREFIX}${segmentsExperienceId}`
		: '';
}
