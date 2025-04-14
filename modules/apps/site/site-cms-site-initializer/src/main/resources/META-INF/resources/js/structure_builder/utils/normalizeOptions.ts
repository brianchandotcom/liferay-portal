/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Options} from '../contexts/PicklistBuilderContext';

export default function normalizeOptions(options: Options) {
	return [...options].map(([erc, value]) => ({
		externalReferenceCode: erc,
		key: value.key,
		name_i18n: value.name,
	}));
}
