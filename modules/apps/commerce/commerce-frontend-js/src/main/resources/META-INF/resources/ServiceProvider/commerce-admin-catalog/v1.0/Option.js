/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const OPTIONS_PATH = '/options';

const OPTION_VALUES_PATH = '/optionValues';

const VERSION = 'v1.0';

function resolveOptionsPath(basePath = '', optionId = '') {
	return `${basePath}${VERSION}${OPTIONS_PATH}/${optionId}`;
}

function resolveOptionValuesPath(basePath = '', optionId = '') {
	return `${basePath}${VERSION}${OPTIONS_PATH}/${optionId}${OPTION_VALUES_PATH}`;
}

export default function Option(basePath) {
	return {
		createOption: (json) => AJAX.POST(resolveOptionsPath(basePath), json),
		createOptionValue: (optionId, json) =>
			AJAX.POST(resolveOptionValuesPath(basePath, optionId), json),
	};
}
