/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {uuidv4} from 'frontend-js-web';
import {useMemo} from 'react';

/**
 * Hook to generate unique IDs.
 */

export default function useId() {
	return useMemo(() => `lfr-${uuidv4()}`, []);
}
