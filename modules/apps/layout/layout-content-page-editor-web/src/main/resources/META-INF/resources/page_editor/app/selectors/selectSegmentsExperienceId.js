/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';

/**
 * Returns the selected segmentsExperienceId or the default one.
 * Warning: this function may return an empty string if the editor is
 * being used in an environment without experiences (ex. Page Templates).
 *
 * @param {object} state
 * @return {string}
 */
export default function selectSegmentsExperienceId(state) {
	const segmentsExperienceId =
		state.segmentsExperienceId || config.defaultSegmentsExperienceId;

	return segmentsExperienceId ? `${segmentsExperienceId}` : null;
}
