/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DEFAULT_ERROR} from '../errorMessages';

/**
 * Used for formatting a search response's error message.
 * @param {object} error Information about the error.
 * @returns {object}
 */
export default function getResultsError({
	exceptionClass,
	exceptionTrace,
	msg,
	severity,
}) {
	return {
		errors: [
			{
				exceptionClass,
				exceptionTrace,
				msg: msg || DEFAULT_ERROR,
				severity: severity || Liferay.Language.get('error'),
			},
		],
	};
}
