/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_DRAFT} from './types';

export default function updateDraft({draft}: {draft: boolean}) {
	return {
		draft,
		type: UPDATE_DRAFT,
	} as const;
}
