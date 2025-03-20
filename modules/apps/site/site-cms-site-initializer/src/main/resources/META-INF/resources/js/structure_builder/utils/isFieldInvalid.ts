/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Field} from './field';

export default function isFieldInvalid(field: Field) {
	if (!field.erc || !field.name) {
		return true;
	}

	if (Object.values(field.label).some((translation) => !translation)) {
		return true;
	}
}
