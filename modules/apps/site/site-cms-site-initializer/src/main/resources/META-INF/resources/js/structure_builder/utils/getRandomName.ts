/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getUuid from './getUuid';
import normalizeName from './normalizeName';

export default function getRandomName({
	capitalize,
}: {capitalize?: boolean} = {}) {
	const prefix = capitalize ? 'Name' : 'name';

	return normalizeName(`${prefix}${getUuid()}`, {limit: 30});
}
