/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import memoize from './memoize';

const toCharCode = memoize((name) =>
	name
		.split('')
		.map((val) => val.charCodeAt())
		.join('')
);

export default toCharCode;
