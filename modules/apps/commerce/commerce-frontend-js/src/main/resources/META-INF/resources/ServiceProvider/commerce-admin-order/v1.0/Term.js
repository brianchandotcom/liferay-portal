/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const TERMS_PATH = '/terms';

const VERSION = 'v1.0';

function resolvePath(basePath = '', termId = '') {
	return `${basePath}${VERSION}${TERMS_PATH}/${termId}`;
}

export default function Term(basePath) {
	return {
		addTerm: (json) => AJAX.POST(resolvePath(basePath), json),
	};
}
