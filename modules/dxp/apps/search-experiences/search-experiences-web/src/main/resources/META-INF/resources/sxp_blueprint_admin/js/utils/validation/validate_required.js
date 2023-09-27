/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ASSET_CATEGORY_ID} from '../constants';
import {ERROR_MESSAGES} from '../errorMessages';
import isEmpty from '../functions/is_empty';

export default function validateRequired(
	configValue,
	name,
	nullable = false,
	required = true,
	type
) {
	if (!required || nullable) {
		return;
	}

	if (isEmpty(configValue, type)) {
		if (name.includes(ASSET_CATEGORY_ID)) {
			return ERROR_MESSAGES.REQUIRED_CATEGORY_SELECTOR;
		}

		return ERROR_MESSAGES.REQUIRED;
	}
}
