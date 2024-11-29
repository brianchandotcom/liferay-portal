/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataApiHelpers} from '../helpers/ApiHelpers';

export function pushToApiHelpersData(
	apiHelpers: DataApiHelpers,
	ids: number[],
	type: string
) {
	for (const id of ids) {
		apiHelpers.data.push({id, type});
	}
}
